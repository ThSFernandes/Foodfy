package br.foodfy.discovery_server;

import com.netflix.eureka.EurekaServerContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.DiscoveryClient;


import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DiscoveryServerApplicationTests {

    @Mock
    private DiscoveryClient discoveryClient;

    @Mock
    private EurekaServerContext eurekaServerContext;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Mockando uma resposta do DiscoveryClient
        when(discoveryClient.description()).thenReturn("Mocked Eureka Discovery Client");
    }

    @Test
    void contextLoads() {
        // Teste básico para garantir que o contexto do Discovery Server carrega corretamente.
        assertThat(discoveryClient).isNotNull();
        assertThat(eurekaServerContext).isNotNull();
        assertThat(discoveryClient.description()).isEqualTo("Mocked Eureka Discovery Client");
    }
}
