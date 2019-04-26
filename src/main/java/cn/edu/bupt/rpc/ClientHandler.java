package cn.edu.bupt.rpc;

import cn.edu.bupt.bean.RPCRequest;
import cn.edu.bupt.bean.RPCResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<RPCResponse> {

    RPCRequest rpcRequest;
    RPCResponse rpcResponse;

    public ClientHandler(RPCRequest rpcRequest, RPCResponse rpcResponse) {
        this.rpcRequest = rpcRequest;
        this.rpcResponse = rpcResponse;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("连接上了。。。");
        ctx.writeAndFlush(rpcRequest);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RPCResponse rpcResponse) throws Exception {
        this.rpcResponse.setRequestId(rpcResponse.getRequestId());
        this.rpcResponse.setResult(rpcResponse.getResult());
        this.rpcResponse.setError(rpcResponse.getError());
        System.out.println("客户端正在写数据回response "+this.rpcResponse.getResult());
        channelHandlerContext.close();

    }
}
