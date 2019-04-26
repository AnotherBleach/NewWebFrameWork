package cn.edu.bupt.rpc;

import cn.edu.bupt.bean.RPCRequest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

public class RpcRequestEncoder extends MessageToByteEncoder<RPCRequest> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RPCRequest rpcRequest, ByteBuf byteBuf) throws Exception {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(rpcRequest);

        byteBuf.writeInt((bos.toByteArray().length));
        byteBuf.writeBytes(bos.toByteArray());

    }
}
