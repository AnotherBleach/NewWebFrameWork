package cn.edu.bupt.rpc;

import cn.edu.bupt.bean.RPCResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

public class RpcResponseEncoder extends MessageToByteEncoder<RPCResponse> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RPCResponse rpcResponse, ByteBuf byteBuf) throws Exception {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(rpcResponse);

        byteBuf.writeInt((bos.toByteArray().length));
        byteBuf.writeBytes(bos.toByteArray());

    }
}
