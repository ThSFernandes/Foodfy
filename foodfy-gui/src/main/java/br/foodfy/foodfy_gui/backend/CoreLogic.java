package br.foodfy.foodfy_gui.backend;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CoreLogic {

    private static final Logger LOGGER = Logger.getLogger(CoreLogic.class.getName());

    public String executeHttpRequest(String url, String type) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url));

        // Configura o tipo de requisição
        switch (type.toUpperCase()) {
            case "POST":
                requestBuilder.POST(HttpRequest.BodyPublishers.noBody());
                break;
            case "PUT":
                requestBuilder.PUT(HttpRequest.BodyPublishers.noBody());
                break;
            case "DELETE":
                requestBuilder.DELETE();
                break;
            case "GET":
            default:
                requestBuilder.GET();
                break;
        }

        try {
            HttpRequest request = requestBuilder.build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao executar requisição HTTP: " + e.getMessage(), e);
            return "Erro de I/O ao executar a requisição: " + e.getMessage();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
            LOGGER.log(Level.SEVERE, "Requisição HTTP interrompida: " + e.getMessage(), e);
            return "Requisição HTTP interrompida: " + e.getMessage();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro inesperado ao executar requisição HTTP: " + e.getMessage(), e);
            return "Erro inesperado: " + e.getMessage();
        }
    }
}