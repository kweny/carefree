/*
 * Copyright 2018-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apenk.carefree.rabbitmq;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultSaslConfig;
import org.apenk.carefree.helper.TempCarefreeAide;
import org.apenk.carefree.rabbitmq.archetype.CarefreeRabbitArchetype;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;

/**
 * TODO-Kweny CarefreeRabbitLathe
 *
 * @author Kweny
 * @since 0.0.1
 */
public class CarefreeRabbitLathe {

    public void test() throws Exception {
        RabbitConnectionFactoryBean factoryBean = new RabbitConnectionFactoryBean();
        factoryBean.setHost("");
        factoryBean.setPort(0);
        factoryBean.setUsername("");
        factoryBean.setPassword("");
        factoryBean.setVirtualHost("");
        factoryBean.setRequestedHeartbeat(0);
        boolean sslEnabled = true;
        if (sslEnabled) {
            factoryBean.setUseSSL(true);
            factoryBean.setSslAlgorithm("");
            factoryBean.setKeyStoreType("");
            factoryBean.setKeyStore("");
            factoryBean.setKeyStorePassphrase("");
            factoryBean.setTrustStoreType("");
            factoryBean.setTrustStore("");
            factoryBean.setTrustStorePassphrase("");
            factoryBean.setSkipServerCertificateValidation(true);
            factoryBean.setEnableHostnameVerification(true);
        }
        factoryBean.setConnectionTimeout(0);
        factoryBean.afterPropertiesSet();

        ConnectionFactory rabbitFactory = factoryBean.getObject();
        if (rabbitFactory == null) {
            throw new RuntimeException();
        }
        CachingConnectionFactory factory = new CachingConnectionFactory(rabbitFactory);
    }

    public void buildRabbitConnectionFactory(CarefreeRabbitArchetype archetype) throws Exception {

        ConnectionFactory connectionFactory = new ConnectionFactory();

        if (TempCarefreeAide.isNotNull(archetype.getHost())) {
            connectionFactory.setHost(archetype.getHost());
        }
        if (TempCarefreeAide.isNotNull(archetype.getPort())) {
            connectionFactory.setPort(archetype.getPort());
        }
        if (TempCarefreeAide.isNotNull(archetype.getVirtualHost())) {
            connectionFactory.setVirtualHost(archetype.getVirtualHost());
        }
        if (TempCarefreeAide.isNotNull(archetype.getUsername())) {
            connectionFactory.setUsername(archetype.getUsername());
        }
        if (TempCarefreeAide.isNotNull(archetype.getPassword())) {
            connectionFactory.setPassword(archetype.getPassword());
        }
        if (TempCarefreeAide.isNotNull(archetype.getCredentialsProvider())) {
            connectionFactory.setCredentialsProvider(archetype.getCredentialsProvider().instance());
        }
        if (TempCarefreeAide.isNotNull(archetype.getUri())) {
            connectionFactory.setUri(archetype.getUri());
        }

        if (TempCarefreeAide.isNotNull(archetype.getRequestedChannelMax())) {
            connectionFactory.setRequestedChannelMax(archetype.getRequestedChannelMax());
        }
        if (TempCarefreeAide.isNotNull(archetype.getRequestedFrameMax())) {
            connectionFactory.setRequestedFrameMax(archetype.getRequestedFrameMax());
        }
        if (TempCarefreeAide.isNotNull(archetype.getRequestedHeartbeat())) {
            connectionFactory.setRequestedHeartbeat(archetype.getRequestedHeartbeat());
        }
        if (TempCarefreeAide.isNotNull(archetype.getConnectionTimeout())) {
            connectionFactory.setConnectionTimeout(archetype.getConnectionTimeout());
        }
        if (TempCarefreeAide.isNotNull(archetype.getHandshakeTimeout())) {
            connectionFactory.setHandshakeTimeout(archetype.getHandshakeTimeout());
        }
        if (TempCarefreeAide.isNotNull(archetype.getShutdownTimeout())) {
            connectionFactory.setShutdownTimeout(archetype.getShutdownTimeout());
        }
        if (TempCarefreeAide.isNotNull(archetype.getClientProperties())) {
            connectionFactory.setClientProperties(archetype.getClientProperties());
        }
        if (TempCarefreeAide.isNotNull(archetype.getSocketFactory())) {
            connectionFactory.setSocketFactory(archetype.getSocketFactory().instance());
        }
        if (TempCarefreeAide.isNotNull(archetype.getSaslConfig())) {
            if (archetype.getSaslConfig().getDefinedValue() != null) {
                if ("external".equalsIgnoreCase(archetype.getSaslConfig().getDefinedValue())) {
                    connectionFactory.setSaslConfig(DefaultSaslConfig.EXTERNAL);
                } else {
                    connectionFactory.setSaslConfig(DefaultSaslConfig.PLAIN);
                }
            } else {
                connectionFactory.setSaslConfig(archetype.getSaslConfig().instance());
            }
        }
    }
}
