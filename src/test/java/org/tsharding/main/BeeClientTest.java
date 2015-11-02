package org.tsharding.main;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * @Author zhaoshb
 * @Since bee-im1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {BeeBootstrap.class})
public class BeeClientTest extends TestCase{

    @Test
    public void testConnect() {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
        bootstrap.handler(new BeeClientChannelInitializer());
        try {
            ChannelFuture sync = bootstrap.connect("127.0.0.1", 4456).sync();
            sync.channel().closeFuture().sync();
            Assert.isTrue(sync.isSuccess());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    @Test
    public void testSendMessage() {
    }

}
