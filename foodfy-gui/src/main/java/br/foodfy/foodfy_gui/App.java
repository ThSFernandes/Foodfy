package br.foodfy.foodfy_gui;

import java.io.IOException;

import br.foodfy.foodfy_gui.fxmlcontroller.FoodfyController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
	
	private final static String FXML_URL = "FoodfyScreen.fxml";
	private final static double WIDTH = 640;
	private final static double HEIGTH = 480;

    @Override
    public void start(Stage stage) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_URL));
    	loader.setController(new FoodfyController());
    	Parent root = loader.load();
    	Scene scene = new Scene(root, WIDTH, HEIGTH);
        stage.setScene(scene);
        stage.show();
    }
}