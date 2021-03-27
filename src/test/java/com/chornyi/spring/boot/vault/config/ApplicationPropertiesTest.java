package com.chornyi.spring.boot.vault.config;

import com.chornyi.spring.boot.vault.TestApplication;
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
public class ApplicationPropertiesTest extends AbstractVaultTests {

    @Autowired
    private ApplicationProperties properties;

    @Test
    public void test() {
        assertThat(properties).isNotNull();
    }
}
