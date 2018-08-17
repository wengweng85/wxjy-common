package com.insigma.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by wengsh on 2018/7/10.
 */
public class DiscardServerHandler extends  ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
        // ĬĬ�ض����յ�������
        ((ByteBuf) msg).release(); // (3)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // �������쳣�͹ر�����
        cause.printStackTrace();
        ctx.close();
    }

}

