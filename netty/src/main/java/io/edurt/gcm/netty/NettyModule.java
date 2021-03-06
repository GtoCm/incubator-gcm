/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.edurt.gcm.netty;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.edurt.gcm.common.utils.PropertiesUtils;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Properties;

public class NettyModule
        extends AbstractModule
{
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyModule.class);

    private final String configuration;

    public NettyModule(String configuration)
    {
        this.configuration = configuration;
    }

    public NettyModule()
    {
        this.configuration = String.join(File.separator, System.getProperty("user.dir"),
                "conf",
                "catalog",
                "netty.properties");
    }

    @SneakyThrows
    @Override
    public void configure()
    {
        LOGGER.debug("Binding netty component information");
        Properties configuration = PropertiesUtils.loadProperties(this.configuration);
        LOGGER.info("Binding netty configuration information is completed, with a total of {} configurations", configuration.stringPropertyNames().size());
        GcmNettyApplication.binder(configuration);
    }

    @Provides
    public EventLoopGroup eventLoopGroup()
    {
        return new NioEventLoopGroup();
    }
}
