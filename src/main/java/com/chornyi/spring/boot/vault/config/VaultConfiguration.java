package com.chornyi.spring.boot.vault.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.config.AbstractVaultConfiguration;

import java.util.Optional;

@Configuration
public class VaultConfiguration extends AbstractVaultConfiguration {

    @Autowired
    private ApplicationProperties properties;

    public boolean isEnable() {
        return properties.getVault().getEnable();
    }
    
    @Override
    public VaultEndpoint vaultEndpoint() {
        VaultEndpoint vaultEndpoint = VaultEndpoint.create(properties.getVault().getHost(), properties.getVault().getPort());
        Optional.ofNullable(properties.getVault().getScheme()).ifPresent(vaultEndpoint::setScheme);
        return vaultEndpoint;
    }

    @Override
    public ClientAuthentication clientAuthentication() {
        return new TokenAuthentication(properties.getVault().getToken());
    }
    
}
