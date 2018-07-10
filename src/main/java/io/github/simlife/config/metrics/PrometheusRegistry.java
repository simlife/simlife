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

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

import com.codahale.metrics.MetricRegistry;

import io.github.simlife.config.SimlifeProperties;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.dropwizard.DropwizardExports;
import io.prometheus.client.exporter.MetricsServlet;

@Configuration
@ConditionalOnClass(CollectorRegistry.class)
public class PrometheusRegistry implements ServletContextInitializer {

    public static final String INITIALIZING_MESSAGE = "Initializing Metrics Prometheus endpoint at {}";
    public static final String SERVLET_NAME = "prometheusMetrics";

    private final Logger log = LoggerFactory.getLogger(PrometheusRegistry.class);

    private final MetricRegistry metricRegistry;

    private final SimlifeProperties simlifeProperties;

    public PrometheusRegistry(MetricRegistry metricRegistry, SimlifeProperties simlifeProperties) {
        this.metricRegistry = metricRegistry;
        this.simlifeProperties = simlifeProperties;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        if (simlifeProperties.getMetrics().getPrometheus().isEnabled()) {
            String endpoint = simlifeProperties.getMetrics().getPrometheus().getEndpoint();
            log.info(INITIALIZING_MESSAGE, endpoint);
            CollectorRegistry collectorRegistry = getCollector();
            collectorRegistry.register(new DropwizardExports(metricRegistry));
            servletContext
                .addServlet(SERVLET_NAME, new MetricsServlet(collectorRegistry))
                .addMapping(endpoint);
        }
    }

    CollectorRegistry getCollector() {
        return new CollectorRegistry();
    }
}
