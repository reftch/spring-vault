package com.chornyi.spring.boot.vault.service;

public interface SecretService {

    boolean isEnable();

    String getValue(String key);

    String getValue(String key, String defaultValue);

}
