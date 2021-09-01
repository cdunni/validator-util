package com.cdunni.validator.stub.generator;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;


public class WireMockRunner implements CommandLineRunner {

    private static final String WIREMOCK_MAPPINGS_PACKAGED_PREFIX = "BOOT-INF/classes/stubs/";
    private static final String WIREMOCK_MAPPINGS_LOCAL_PREFIX = "stubs/";

    @Value("${wiremock.run.env")
    private String wireMockRunEnv;

    private WireMockServer wireMockServer;

    @Override
    public void run(String... args) throws Exception {
        WireMockConfiguration wireMockConfiguration = getWireMockConfig();
        wireMockServer = new WireMockServer(wireMockConfiguration);
        wireMockServer.start();
    }

    private WireMockConfiguration getWireMockConfig() {
        WireMockConfiguration wireMockConfiguration
                = WireMockConfiguration.options()
                    .port(8889)
                    .usingFilesUnderClasspath(getMappingsBasePath())
                    .asynchronousResponseEnabled(true)
                    .containerThreads(10)
                    .asynchronousResponseThreads(10);
        return wireMockConfiguration;
    }

    private String getMappingsBasePath() {
        return wireMockRunEnv.equalsIgnoreCase("local") ?
                WIREMOCK_MAPPINGS_LOCAL_PREFIX : WIREMOCK_MAPPINGS_PACKAGED_PREFIX;
    }
}
