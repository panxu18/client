package fileregion;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.RandomAccessFile;

public class FileServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        RandomAccessFile file =
                new RandomAccessFile("/home/xupan/Documents/doc/notes/Todo.md", "r");
        ctx.write("OK: " + file.length() + "\n");
        ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
        ctx.writeAndFlush("\n");
    }
}
