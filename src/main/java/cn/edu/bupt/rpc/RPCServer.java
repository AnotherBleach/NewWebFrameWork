package cn.edu.bupt.rpc;

import cn.edu.bupt.annotation.RpcService;
import cn.edu.bupt.util.ClassParser;
import cn.edu.bupt.util.RPCUtil;
import cn.edu.bupt.util.XMLParser;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.dom4j.Element;

import java.io.IOException;

import java.util.Set;
import java.util.Map;

public class RPCServer {
    private int port;
    private String address;

    public RPCServer(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public String getAddress() {
        return address;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public void start() {

        Element rpc = XMLParser.getDocument("another.xml").getRootElement().element("rpc");
        if (rpc == null) throw new NullPointerException("rpc element is null!");
        String basePackages = rpc.elementText("basepackage");
        try {
            ClassParser.initClassSet(basePackages);
            Map<String, Object> RPCMap = RPCUtil.getRPCMap();
            Set<Class<?>> classSet = ClassParser.getRpcClassSet();
            System.out.println("一共有 "+classSet.size());
            for (Class<?> cls : classSet) {

                RpcService rpcService = cls.getAnnotation(RpcService.class);
                Class<?> interfaceName = rpcService.interfaceName();
                RPCMap.put(interfaceName.getName(), cls.newInstance());

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        try {
            startServer();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void startServer() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new RpcRequestDecoder());
                        socketChannel.pipeline().addLast(new RpcResponseEncoder());
                        socketChannel.pipeline().addLast(new ServerHandler());


                    }
                });
        ChannelFuture future = bootstrap.bind(9999).sync();
        future.channel().closeFuture().sync();


    }

}
