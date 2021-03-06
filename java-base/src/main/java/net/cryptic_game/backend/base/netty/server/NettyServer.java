package net.cryptic_game.backend.base.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.ServerChannel;
import io.netty.channel.epoll.Epoll;
import io.netty.handler.ssl.SslContext;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.cryptic_game.backend.base.netty.EventLoopGroupHandler;
import net.cryptic_game.backend.base.netty.NettyInitializer;
import net.cryptic_game.backend.base.netty.codec.NettyCodecHandler;

import java.net.SocketAddress;

@RequiredArgsConstructor
@EqualsAndHashCode
@Slf4j
public abstract class NettyServer implements AutoCloseable {

    private static final boolean EPOLL = Epoll.isAvailable();
    private final String id;
    private final SocketAddress address;
    private final SslContext sslContext;
    private final NettyCodecHandler codecHandler;
    private final EventLoopGroupHandler eventLoopGroupHandler;

    private Thread thread;
    private Channel channel;

    protected abstract Class<? extends ServerChannel> getServerChannelType(boolean epoll);

    /**
     * Starts the current server.
     */
    public void start() {
        this.close();
        this.thread = new Thread(() -> {
            try {
                this.channel = new ServerBootstrap()
                        .option(ChannelOption.SO_BACKLOG, 1024)
                        .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                        .group(this.eventLoopGroupHandler.getBossGroup(), this.eventLoopGroupHandler.getWorkGroup())
                        .channel(this.getServerChannelType(EPOLL))
                        .childHandler(new NettyInitializer(this.sslContext, this.codecHandler.getInitializers()))
                        .bind(this.address)
                        .sync()
                        .channel();

                log.info("Server {} is now listening on {}.", this.id, this.channel.localAddress());

                this.channel.closeFuture().sync();
            } catch (Exception e) {
                log.error("Server " + this.id + " crashed. Restarting in 20 seconds.", e);

                try {
                    Thread.sleep(20000);
                } catch (InterruptedException ignored) {
                }
                this.start();

            } finally {
                this.close();
            }
        }, "server-" + this.id);
        this.thread.start();
    }

    /**
     * Closes the current server.
     */
    @Override
    public void close() {
        if (this.channel != null) {
            if (this.channel.isOpen()) this.channel.close();
            this.channel = null;
        }
        if (this.thread != null) {
            this.thread.interrupt();
            this.thread = null;
        }
    }
}
