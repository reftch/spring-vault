package com.chornyi.spring.boot.vault.service;

import com.chornyi.spring.boot.vault.TestApplication;
import com.chornyi.spring.boot.vault.config.AbstractVaultTests;
import com.chornyi.spring.boot.vault.config.ApplicationProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
@TestPropertySource(locations = {"classpath:application.yml"})
public class VaultServiceTest extends AbstractVaultTests {

    @Autowired
    private ApplicationProperties properties;

    @Autowired
    private VaultService service;

    @Test
    public void isEnable() {
        assertThat(service).isNotNull();
        assertThat(service.isEnable()).isEqualTo(true);
    }

    @Test
    public void getValue() {
        assertThat(service).isNotNull();
        assertThat(service.getValue("key1")).isEqualTo("value1");
        assertThat(service.getValue("key2")).isEqualTo("value2");
        assertThat(service.getValue("key3")).isEqualTo("value3");
    }

}
