package org.tsharing.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tsharing.conf.BeeConfiguration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.nio.charset.Charset;

/**
 * netty bootstrap.
 *
 * @Author zhaoshb
 * @Since bee-im1.0
 */
@Component
public class NettyBootstrap {

    private static final Logger logger = LoggerFactory.getLogger(NettyBootstrap.class);

    @Autowired
    private BeeConfiguration beeConfiguration = null;

    private EventLoopGroup bossGroup = new NioEventLoopGroup();

    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    @PostConstruct
    public void start() {
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(this.getBossGroup(), this.getWorkerGroup());
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.childHandler(new BeeChannelInitializer());
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 128);
            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);

            Integer serverListenPort = this.getBeeConfiguration().getServerListenPort();
            serverBootstrap.bind(serverListenPort).sync();
        } catch (InterruptedException e) {
            logger.error("start netty failed.", e);
            throw new RuntimeException(e);
        }
    }

    @PreDestroy
    public void destroy() {
        this.getBossGroup().shutdownGracefully();
        this.getWorkerGroup().shutdownGracefully();
    }

    private EventLoopGroup getBossGroup() {
        return bossGroup;
    }

    private EventLoopGroup getWorkerGroup() {
        return workerGroup;
    }

    private BeeConfiguration getBeeConfiguration() {
        return beeConfiguration;
    }

    private class BeeChannelInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            StringEncoder stringEncoder = new StringEncoder(Charset.forName("utf-8"));
            StringDecoder stringDecoder = new StringDecoder(Charset.forName("utf-8"));
            ch.pipeline().addLast(stringEncoder,stringDecoder,new BeeChannelHandler());
        }
    }


}
