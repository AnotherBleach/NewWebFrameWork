package cn.edu.bupt.rpc;

import cn.edu.bupt.bean.RPCRequest;
import cn.edu.bupt.bean.RPCResponse;
import cn.edu.bupt.util.RPCUtil;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;
import java.util.Map;
public class ServerHandler extends SimpleChannelInboundHandler<RPCRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RPCRequest rpcRequest) throws Exception {

        String className = rpcRequest.getClassName();
        String methodName = rpcRequest.getMethodName();
        Class<?>[] parameterType = rpcRequest.getParameterType();
        Object[] parameterValue = rpcRequest.getParamterValue();


        Object object = RPCUtil.getRPCMap().get(className);
        System.out.println(className+" "+methodName+" "+parameterType+" "+parameterValue+" "+object);
        System.out.println("begin");
        for(Map.Entry<String,Object>entry: RPCUtil.getRPCMap().entrySet()){

            System.out.println(entry.getKey()+"==>"+entry.getValue());

        }
        System.out.println("stop");

        Method method = object.getClass().getMethod(methodName, parameterType);
        System.out.println(method.getName()+"!!!!!!!");
        for(int i=0;i<parameterValue.length;i++)
            System.out.println(parameterValue[i]);
        System.out.println("------------------------");
        RPCResponse rpcResponse = new RPCResponse();

        try {
            Object result = method.invoke(object, parameterValue);
            System.out.println("result = "+result);
            rpcResponse.setError(null);
            rpcResponse.setRequestId(rpcRequest.getRequestId());
            rpcResponse.setResult(result);
        } catch (Exception e) {
            rpcResponse.setError(e);
            rpcResponse.setResult(null);
            rpcResponse.setRequestId(rpcRequest.getRequestId());

        }
        System.out.println("服务端正在写数据给客户端");
        channelHandlerContext.writeAndFlush(rpcResponse).addListener(ChannelFutureListener.CLOSE);
        System.out.println("服务端写数据完成");


    }
}
