package com.insigma.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by wengsh on 2018/7/10.
 */
public class Server {
    private int port;

    public Server(int port) {
        this.port = port;
    }

    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();//������group
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() { //���þ�������ݴ���ʽ
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new ServerHandler());
                }
            })
                /**
                 * ����ChannelOption.SO_BACKLOG�Ľ��ͣ�
                 * ��������TCP�ں�ά�����������У����ǳ�֮ΪA��B���С��ͻ������������connectʱ���ᷢ�ʹ���SYN��־�İ�����һ�����֣�����������
                 * ���յ��ͻ��˷��͵�SYNʱ����ͻ��˷���SYN ACKȷ�ϣ��ڶ������֣�����ʱTCP�ں�ģ��ѿͻ������Ӽ��뵽A�����У�Ȼ����������յ�
                 * �ͻ��˷��͵�ACKʱ�����������֣���TCP�ں�ģ��ѿͻ������Ӵ�A�����ƶ���B���У�������ɣ�Ӧ�ó����accept�᷵�ء�Ҳ����˵accept
                 * ��B������ȡ��������������ֵ����ӡ�
                 * A���к�B���еĳ���֮�;���backlog����A��B���еĳ���֮�ʹ���ChannelOption.SO_BACKLOGʱ���µ����ӽ��ᱻTCP�ں˾ܾ���
                 * ���ԣ����backlog��С�����ܻ����accept�ٶȸ����ϣ�A��B�������ˣ������µĿͻ����޷����ӡ�Ҫע����ǣ�backlog�Գ���֧�ֵ�
                 * ����������Ӱ�죬backlogӰ���ֻ�ǻ�û�б�acceptȡ��������
                 */
                .option(ChannelOption.SO_BACKLOG, 128) //����TCP������
                .option(ChannelOption.SO_SNDBUF, 32 * 1024) //���÷������ݻ����С
                .option(ChannelOption.SO_RCVBUF, 32 * 1024) //���ý������ݻ����С
                .childOption(ChannelOption.SO_KEEPALIVE, true); //��������
            ChannelFuture future = serverBootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new Server(8379).run();
    }

}