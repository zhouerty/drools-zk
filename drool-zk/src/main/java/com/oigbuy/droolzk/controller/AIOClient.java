package com.oigbuy.droolzk.controller;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author qiang.zhou
 * on 2018-05-16.
 */
public class AIOClient implements Runnable{

    public static void main(String[] args) throws IOException {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                //线程池维护线程的最少数量
                100,
                //线程池维护线程的最大数量
                120,
                //线程池维护线程所允许的空闲时间
                10,
                //线程池维护线程所允许的空闲时间的单位
                TimeUnit.SECONDS,
                //线程池所使用的缓冲队列
                new ArrayBlockingQueue<Runnable>(120),
                //线程池对拒绝任务的处理策略
                new ThreadPoolExecutor.DiscardOldestPolicy());
        for (int i = 0; i < 100; i++) {
            threadPool.execute(new AIOClient());
        }
    }

    @Override
    public void run(){
        InetSocketAddress serverAddress = new InetSocketAddress("127.0.0.1", 8001);
        try {
            final AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
            CompletionHandler<Void, ? super Object> handler = new CompletionHandler<Void, Object>() {

                @Override
                public void completed(Void result, Object attachment) {
                    client.write(ByteBuffer.wrap("Hello".getBytes()), null, new CompletionHandler<Integer, Object>() {
                        @Override
                        public void completed(Integer result, Object attachment) {
                            final ByteBuffer buffer = ByteBuffer.allocate(1024);
                            client.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                                @Override
                                public void completed(Integer result, ByteBuffer attachment) {
                                    buffer.flip();
                                    System.out.println(new String(buffer.array()));
                                    try {
                                        client.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void failed(Throwable exc, ByteBuffer attachment) {
                                }
                            });
                        }

                        @Override
                        public void failed(Throwable exc, Object attachment) {
                        }
                    });
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                }
            };
        client.connect(serverAddress, null, handler);
        }catch (IOException e){
            throw new RuntimeException("初始化客户端失败");
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
