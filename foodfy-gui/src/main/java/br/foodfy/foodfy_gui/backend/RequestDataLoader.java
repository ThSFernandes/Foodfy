package br.foodfy.foodfy_gui.backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.foodfy.foodfy_gui.value_objects.RequestData;

public class RequestDataLoader {

    /**
     * Carrega as requisições a partir de um arquivo de texto e preenche o mapa requestByType.
     *
     * @param filePath Caminho do arquivo de texto com as requisições.
     * @param requestByType Mapa a ser preenchido com as requisições.
     * @throws IOException Se ocorrer um erro ao ler o arquivo.
     */
    public void loadRequestsFromFile(String filePath, Map<String, List<RequestData>> requestByType) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || !line.contains(" ")) {
                    continue;
                }

                String[] parts = line.split(" ", 3);
                if (parts.length < 3) {
                    continue; // Linha inválida, ignore
                }

                String name = parts[0];
                String type = parts[1];
                String url = parts[2];

                requestByType.computeIfAbsent(type, k -> new ArrayList<>())
                             .add(new RequestData(name, url));
            }
        }
    }
}