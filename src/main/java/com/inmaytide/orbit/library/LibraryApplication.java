package com.inmaytide.orbit.library;

import com.inmaytide.orbit.commons.metrics.configuration.MetricsProperties;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.lionsoul.ip2region.xdb.Searcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;


@EnableConfigurationProperties(MetricsProperties.class)
@SpringBootApplication(scanBasePackages = {"com.inmaytide.orbit.commons", "com.inmaytide.orbit.library"})
public class LibraryApplication {

    private static final Logger LOG = LoggerFactory.getLogger(LibraryApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

    @Bean("ipRegionSearcher")
    public Searcher searcher() throws IOException {
        try {
            return Searcher.newWithBuffer(new ClassPathResource("ip2region.xdb").getContentAsByteArray());
        } catch (Exception e) {
            LOG.error("Failed to create content cached searcher, Cause by: \n", e);
            throw e;
        }
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .specVersion(SpecVersion.V30)
                .info(
                        new Info().title("Orbit Library API")
                                .version("1.0.0")
                                .license(new License().name("MIT").url("https://opensource.org/licenses/MIT"))
                )
                .externalDocs(
                        new ExternalDocumentation()
                                .description("Library Wiki Documentation")
                                .url("https://github.com/i-orbit/library")
                );
    }


}
