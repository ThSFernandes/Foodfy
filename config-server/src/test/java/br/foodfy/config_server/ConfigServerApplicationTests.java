package br.foodfy.config_server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ConfigServerApplicationTests {

    @Test
    void contextLoads() {
        // Teste básico para garantir que o contexto do Config Server carrega corretamente.
        assertThat(true).isTrue();
    }
}
