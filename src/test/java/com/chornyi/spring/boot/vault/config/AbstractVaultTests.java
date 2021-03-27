package com.chornyi.spring.boot.vault.config;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.IOException;

public abstract class AbstractVaultTests {

    private static VaultInitializer initializer;

    @BeforeClass
    public static void startServer() {
        initializer = new VaultInitializer("00000000-0000-0000-0000-000000000000");
        initializer.initializeVault();
    }

    @AfterClass
    public static void stopServer() throws IOException {
        initializer.close();
    }

}
