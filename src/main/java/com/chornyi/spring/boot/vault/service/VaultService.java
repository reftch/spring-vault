package com.chornyi.spring.boot.vault.service;

import com.chornyi.spring.boot.vault.config.VaultConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultKeyValueOperationsSupport;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;

import java.util.Map;

@Service
public class VaultService implements SecretService {

    @Autowired
    private VaultConfiguration vaultConfiguration;

    @Autowired
    private VaultTemplate vaultTemplate;

    @Override
    public boolean isEnable() {
        return vaultConfiguration.isEnable();
    }

    @Override
    public String getValue(String key) {
        VaultResponse response = vaultTemplate
                .opsForKeyValue("secret", VaultKeyValueOperationsSupport.KeyValueBackend.KV_2).get("chornyi");
        if (response != null) {
            Map<String, Object> data = response.getData();
            if (data != null) {
                return (String) data.get(key);
            }
        }
        return null;
    }

    @Override
    public String getValue(String key, String defaultValue) {
        String value = getValue(key);
        if (value == null) {
            value = defaultValue;
        }
        return value;
    }
}
