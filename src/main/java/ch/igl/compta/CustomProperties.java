package ch.igl.compta;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix="ch.igl.compta")
@ConfigurationPropertiesScan
public class CustomProperties {
    private String apiTest;
}
