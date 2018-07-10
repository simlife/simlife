/*
 * Copyright 2016-2017 the original author or authors from the Simlife project.
 *
 * This file is part of the Simlife project, see http://www.simlife.tech/
 * for more information.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.simlife.config.metrics;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;

import io.github.simlife.config.SimlifeProperties;

@Configuration
@ConditionalOnClass(Graphite.class)
public class GraphiteRegistry {

    public static final String INITIALIZING_MESSAGE = "Initializing Metrics Graphite reporting";
    public static final long REPORTER_PERIOD = 1L;

    private final Logger log = LoggerFactory.getLogger(GraphiteRegistry.class);

    private final SimlifeProperties simlifeProperties;

    public GraphiteRegistry(MetricRegistry metricRegistry, SimlifeProperties simlifeProperties) {
        this.simlifeProperties = simlifeProperties;
        if (this.simlifeProperties.getMetrics().getGraphite().isEnabled()) {
            log.info(INITIALIZING_MESSAGE);
            String graphiteHost = simlifeProperties.getMetrics().getGraphite().getHost();
            Integer graphitePort = simlifeProperties.getMetrics().getGraphite().getPort();
            String graphitePrefix = simlifeProperties.getMetrics().getGraphite().getPrefix();
            Graphite graphite = getGraphite(graphiteHost, graphitePort);
            GraphiteReporter graphiteReporter = getBuilder(metricRegistry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .prefixedWith(graphitePrefix)
                .build(graphite);
            graphiteReporter.start(REPORTER_PERIOD, TimeUnit.MINUTES);
        }
    }

    Graphite getGraphite(String graphiteHost, int graphitePort) {
        return new Graphite(new InetSocketAddress(graphiteHost, graphitePort));
    }

    GraphiteReporter.Builder getBuilder(MetricRegistry metricRegistry) {
        return GraphiteReporter.forRegistry(metricRegistry);
    }
}
