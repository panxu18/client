package helloworld;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * channel 绑定时会进行handler的初始化，定义了一个http解码器，以及一个自定义的处理器
 */
public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("httpDecoderHandler", new HttpServerCodec());
        pipeline.addLast("helloHttpHandler", new HelloHttpHandler());
    }
}
