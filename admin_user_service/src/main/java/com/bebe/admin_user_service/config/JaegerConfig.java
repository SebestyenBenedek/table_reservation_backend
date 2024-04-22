package com.bebe.admin_user_service.config;

import io.jaegertracing.internal.JaegerTracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JaegerConfig {

    @Bean
    public JaegerTracer jaegerTracer() {
        io.jaegertracing.Configuration.SamplerConfiguration samplerConfiguration = io.jaegertracing.Configuration.SamplerConfiguration.fromEnv()
                .withType("const").withParam(1);
        io.jaegertracing.Configuration.ReporterConfiguration reporterConfig = io.jaegertracing.Configuration.ReporterConfiguration.fromEnv()
                .withLogSpans(true);
        io.jaegertracing.Configuration configuration = new io.jaegertracing.Configuration("admin-user-service")
                .withSampler(samplerConfiguration)
                .withReporter(reporterConfig);
        return configuration.getTracer();
    }
}
