package com.jonghyun.hud.networking;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.nio.charset.StandardCharsets;

public class PacketMessage implements IMessage {

    public String data;

    public PacketMessage() {

    }

    public PacketMessage(String data) {
        this.data = data;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        int len = buf.readInt();
        data = buf.toString(buf.readerIndex(), len, StandardCharsets.UTF_8);
        buf.readerIndex(buf.readerIndex() + len);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);
    }


    public static class Handle implements IMessageHandler<PacketMessage, IMessage> {
        @Override
        public IMessage onMessage(PacketMessage message, MessageContext ctx) {
            PacketControl.getInstance().handle(message);
            return null;
        }
    }
}
