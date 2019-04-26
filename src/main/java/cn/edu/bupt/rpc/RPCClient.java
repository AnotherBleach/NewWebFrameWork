package cn.edu.bupt.rpc;

import cn.edu.bupt.aop.Proxy;
import cn.edu.bupt.bean.RPCRequest;
import cn.edu.bupt.bean.RPCResponse;
import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.model.health.Service;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Map;

public class RPCClient implements MethodInterceptor {

    /***
     *
     * @param obj
     * @param method
     * @param args
     * @param proxy
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

        Object result = null;
        /***
         *   服务发现，获取服务，TCP连接服务，等待返回服务结果
         *
         *
         */
        String serviceName = getServiceName(obj);
        Service service = serviceDiscovery(serviceName);
        if (service == null) {
            result = null;
            System.out.println("do not found service!");
        }
        String host = service.getAddress();
        int port = service.getPort();


        RPCRequest rpcRequest = new RPCRequest();
        rpcRequest.setClassName(serviceName);
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParamterValue(args);
        rpcRequest.setParameterType(method.getParameterTypes());

        RPCResponse rpcResponse = new RPCResponse();
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .remoteAddress("127.0.0.1", 9999)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new RpcResponseDecoder());
                        socketChannel.pipeline().addLast(new RpcRequestEncoder());
                        socketChannel.pipeline().addLast(new ClientHandler(rpcRequest, rpcResponse));
                    }
                });
        bootstrap.connect().sync().channel().closeFuture().sync();
        eventLoopGroup.shutdownGracefully();

        System.out.println("客户端关闭连接，开始返回数据给调用方 "+rpcResponse.getResult());
        return rpcResponse.getResult();

    }

    public String getServiceName(Object obj) {
        String name = obj.getClass().getName();
        System.out.println("name = "+name);
        name = name.substring(0, name.indexOf("$"));
        return name;

    }

    public Service serviceDiscovery(String serviceName) {

        System.out.println("serviceName = " + serviceName);
        Consul client = Consul.builder().build();
        AgentClient agentClient = client.agentClient();
        Map<String, Service> services = agentClient.getServices();
        for (Map.Entry<String, Service> entry : services.entrySet()) {
            if (serviceName.equals(entry.getValue().getService())) {
                return entry.getValue();

            }

        }

        return null;
    }

    public <T> T getProxyObject(Class<T> cls) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(cls);
        enhancer.setCallback(this);
        return (T) enhancer.create();

    }


}
