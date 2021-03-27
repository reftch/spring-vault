package com.chornyi.spring.boot.vault.config;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class VaultInitializer implements Closeable {

    private static final String UNSEAL_KEY = "Unseal Key:";
    private static final String ROOT_TOKEN = "Root Token:";

    private Process vaultProcess;
    private String unSealKey;
    private String rootToken;

    public VaultInitializer(String rootToken) {
        this.rootToken = rootToken;
    }

    public void initializeVault() {
        start();
        // Secrets is by default enabled.
        enableSecrets();
    }

    @SuppressWarnings("unused")
    private void enableSecrets() {
        System.out.println("Enabling Secrets at database credentials and LDAP server...");
        ProcessBuilder pb = new ProcessBuilder("src/test/resources/vault", "kv", "put", "secret/chornyi",
                "key1=value1",
                "key2=value2",
                "key3=value3");

        Map<String, String> map = pb.environment();
        map.put("VAULT_ADDR", "http://127.0.0.1:8200");
        map.put("VAULT_TOKEN", rootToken);
        try {
            Process p = pb.inheritIO()
                    .start();
            p.waitFor();
        } catch (IOException e) {
            System.out.println("unable to enableSecrets" + e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        System.out.println("starting vault server...");
        // This starts the vault server.
        ProcessBuilder pb = new ProcessBuilder("src/test/resources/vault", "server", "--dev", "--dev-root-token-id=" + rootToken);

        try {
            vaultProcess = pb.start();
            // wait for initialization to complete.
            Thread.sleep(5000);
        } catch (IOException e) {
            System.out.println("unable to start vault in new process" + e);
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted " + e);
        }
        extractUnsealKeyAndToken();
    }


    /**
     * To get the root token which is generated every time server is initialized.
     */
    private void extractUnsealKeyAndToken() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(vaultProcess.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String line = null;
        boolean tokenExtracted = false;
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
                if (line.contains(UNSEAL_KEY)) {
                    String tmp = line.replace(UNSEAL_KEY, "");
                    unSealKey = tmp.trim();
                } else if (line.contains(ROOT_TOKEN)) {
                    String tmp = line.replace(ROOT_TOKEN, "");
                    rootToken = tmp.trim();
                    tokenExtracted = true;
                }
                if (tokenExtracted)
                    break;
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("unable to read vault output" + e);
        }

        String result = builder.toString();

        System.out.println("Unseal Key {}" + unSealKey);
        System.out.println("Root Token {}" + rootToken);
        System.out.println(result);
    }

    @Override
    public void close() throws IOException {
        System.out.println("stoping vault");
        vaultProcess.destroy();
    }

}
