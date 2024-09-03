package br.foodfy.foodfy_gui.fxmlcontroller;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.foodfy.foodfy_gui.controller.MainController;
import br.foodfy.foodfy_gui.value_objects.RequestData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class FoodfyView {
	
	private Map<String, List<RequestData>> requestByType = new HashMap<>();
	private List<TextField> placeholderFields = new ArrayList<>();

	@FXML
	private AnchorPane anchorPaneRoot;

	@FXML
	private Button buttonExecute;

	@FXML
	private ComboBox<String> comboBoxRequest;

	@FXML
	private ComboBox<String> comboBoxRequestType;

	@FXML
	private Label labelMensagens;

	@FXML
	private Label labelRequestType;

	@FXML
	private TextArea textAreaOutput;

	@FXML
	void handleComboBoxRequestNameSelection(ActionEvent event) {
		String selectedRequestName = comboBoxRequest.getValue();
		String selectedRequestType = comboBoxRequestType.getValue();

		if (selectedRequestName != null && selectedRequestType != null) {
			List<RequestData> requests = requestByType.get(selectedRequestType);

			if (requests != null) {
				for (RequestData requestData : requests) {
					if (requestData.name.equals(selectedRequestName)) {
						createPlaceholderFields(requestData.url);
						break;
					}
				}
			}
		}
	}

	@FXML
	void handleComboBoxRequestTypeSelection(ActionEvent event) {
		String selectedRequestType = comboBoxRequestType.getValue();

		if (selectedRequestType != null) {
			List<RequestData> requests = requestByType.get(selectedRequestType);

			if (requests != null) {
				comboBoxRequest.getItems().clear();

				for (RequestData requestData : requests) {
					comboBoxRequest.getItems().add(requestData.name);
				}

				if (!comboBoxRequest.getItems().isEmpty()) {
					comboBoxRequest.getSelectionModel().selectFirst();
					// Atualiza os campos de placeholder com base na seleção atual
					handleComboBoxRequestNameSelection(null);
				}
			}
		}
	}

	@FXML
	void handleButtonExecutePressed(ActionEvent event) {
		String selectedRequestType = comboBoxRequestType.getValue();
		String selectedRequestName = comboBoxRequest.getValue();

		if (selectedRequestType == null || selectedRequestName == null) {
			labelMensagens.setText("Selecione um tipo de requisição e um nome de requisição.");
			textAreaOutput.clear(); // Limpa o resultado da requisição
			return;
		}

		List<RequestData> requests = requestByType.get(selectedRequestType);

		if (requests == null) {
			labelMensagens.setText("Não existe request para o tipo indicado");
			textAreaOutput.clear(); // Limpa o resultado da requisição
			return;
		}

		for (RequestData requestData : requests) {
			if (requestData.name.equals(selectedRequestName)) {
				String result = MainController.getInstance().executeHttpRequest(selectedRequestType,
						replacePlaceholders(requestData.url));
				textAreaOutput.setText("Resultado da request: " + result);
				labelMensagens.setText(""); // Limpa mensagens de erro
				break;
			}
		}
	}

	private void createPlaceholderFields(String url) {
		// Limpa os campos existentes
		anchorPaneRoot.getChildren().removeAll(placeholderFields);
		placeholderFields.clear();

		Pattern pattern = Pattern.compile("\\$\\[(\\d+)]");
		Matcher matcher = pattern.matcher(url);

		int yOffset = 70; // Distância inicial entre os campos

		while (matcher.find()) {
			TextField textField = new TextField();
			textField.setPromptText("Valor para placeholder " + matcher.group(1));
			textField.setLayoutX(10); // Posição horizontal
			textField.setLayoutY(yOffset); // Posição vertical
			yOffset += 30; // Distância entre os campos
			placeholderFields.add(textField);
			anchorPaneRoot.getChildren().add(textField);
		}
	}

	private String replacePlaceholders(String url) {
		String modifiedUrl = url;
		for (int i = 0; i < placeholderFields.size(); i++) {
			String placeholder = "$[" + (i + 1) + "]";
			String value = placeholderFields.get(i).getText();
			modifiedUrl = modifiedUrl.replace(placeholder, value);
		}
		return modifiedUrl;
	}

    @FXML
    private void initialize() {
        URL resourceUrl = getClass().getClassLoader().getResource("br/foodfy/foodfy_gui/requests.txt");

        if (resourceUrl != null) {
            try {
                MainController.getInstance().loadRequestsFromFile(Paths.get(resourceUrl.toURI()).toString(), requestByType);
            } catch (IOException e) {
                e.printStackTrace();
                labelMensagens.setText("Erro ao carregar as requisições.");
            } catch (Exception e) {
                e.printStackTrace();
                labelMensagens.setText("Erro inesperado ao carregar as requisições.");
            }
        } else {
            System.out.println("Arquivo requests.txt não encontrado.");
            labelMensagens.setText("Arquivo requests.txt não encontrado.");
        }

        comboBoxRequestType.getItems().addAll(requestByType.keySet());

        if (!comboBoxRequestType.getItems().isEmpty()) {
            comboBoxRequestType.getSelectionModel().selectFirst();
            handleComboBoxRequestTypeSelection(null);
        }
    }
}