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

package org.apenk.carefree.rabbitmq.archetype;

import org.apenk.carefree.archetype.CarefreeArchetype;
import org.apenk.carefree.helper.CarefreeClassDeclaration;

import java.util.Map;

/**
 * TODO-Kweny CarefreeRabbitArchetype
 * com.rabbitmq.client.ConnectionFactory
 *
 * @author Kweny
 * @since 0.0.1
 */
public class CarefreeRabbitArchetype extends CarefreeArchetype {
    private String virtualHost;
    private String host;
    private Integer port;
    private Integer requestedChannelMax;
    private Integer requestedFrameMax;
    private Integer requestedHeartbeat;
    private Integer connectionTimeout;
    private Integer handshakeTimeout;
    private Integer shutdownTimeout;
    private Map<String, Object> clientProperties;
    private CarefreeClassDeclaration socketFactory;
    private CarefreeClassDeclaration saslConfig;

    private CarefreeClassDeclaration sharedExecutor;
    private CarefreeClassDeclaration threadFactory;
    private CarefreeClassDeclaration shutdownExecutor;
    private CarefreeClassDeclaration heartbeatExecutor;
    private CarefreeClassDeclaration socketConf;
    private CarefreeClassDeclaration credentialsProvider;

    private Boolean automaticRecovery;
    private Boolean topologyRecovery;
    private CarefreeClassDeclaration topologyRecoveryExecutor;

    private Long networkRecoveryInterval;
    private CarefreeClassDeclaration recoveryDelayHandler;

    private CarefreeClassDeclaration metricsCollector;

    private Boolean nio;
    private CarefreeClassDeclaration frameHandlerFactory;

}
