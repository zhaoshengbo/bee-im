package org.tsharding.main;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;


/**
 * @Author zhaoshb
 * @Since bee-im1.0
 */
public class BeeClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        StringEncoder stringEncoder = new StringEncoder(Charset.forName("utf-8"));
        StringDecoder stringDecoder = new StringDecoder(Charset.forName("utf-8"));
        ch.pipeline().addLast(stringEncoder, stringDecoder , new BeeClientHandler());
    }
}
