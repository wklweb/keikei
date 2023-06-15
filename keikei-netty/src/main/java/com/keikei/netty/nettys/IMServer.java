package com.keikei.netty.nettys;

import java.net.InetSocketAddress;

public interface IMServer {
    public boolean isReady();
    public void start() throws InterruptedException;
    public void close();

}
