/*
 * Copyright 2017-2019 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.micronaut.configuration.acme.ssl;

import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.ssl.ApplicationProtocolNegotiator;
import io.netty.handler.ssl.SslContext;
import io.netty.util.internal.ObjectUtil;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSessionContext;
import java.util.List;

/**
 * Allows for netty SslContext to be delegated to another as well as switched out at runtime.
 */
public class DelegatedSslContext extends SslContext {

    private SslContext ctx;

    protected DelegatedSslContext(SslContext ctx) {
        this.ctx = ObjectUtil.checkNotNull(ctx, "ctx");
    }

    public void setNewSslContext(SslContext sslContext){
        this.ctx = sslContext;
    }

    @Override
    public final boolean isClient() {
        return ctx.isClient();
    }

    @Override
    public final List<String> cipherSuites() {
        return ctx.cipherSuites();
    }

    @Override
    public final long sessionCacheSize() {
        return ctx.sessionCacheSize();
    }

    @Override
    public final long sessionTimeout() {
        return ctx.sessionTimeout();
    }

    @Override
    public final ApplicationProtocolNegotiator applicationProtocolNegotiator() {
        return ctx.applicationProtocolNegotiator();
    }

    @Override
    public final SSLEngine newEngine(ByteBufAllocator alloc) {
        return ctx.newEngine(alloc);
    }

    @Override
    public final SSLEngine newEngine(ByteBufAllocator alloc, String peerHost, int peerPort) {
        return ctx.newEngine(alloc, peerHost, peerPort);
    }


    @Override
    public final SSLSessionContext sessionContext() {
        return ctx.sessionContext();
    }

}