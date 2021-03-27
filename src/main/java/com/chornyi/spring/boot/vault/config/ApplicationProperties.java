package com.chornyi.spring.boot.vault.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties("spring.cloud")
@Data
public class ApplicationProperties {

    private Vault vault;

    public void setVault(Vault vault) {
        this.vault = vault;
    }

    public Vault getVault() {
        return vault;
    }

    @Data
    @Validated
    public static class Vault {
        private Boolean enable;
        private String host;
        private Integer port;
        private String token;
        private String scheme;
    }

}
