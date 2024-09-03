package br.foodfy.foodfy_gui;

import java.io.IOException;

import br.foodfy.foodfy_gui.backend.CoreLogic;
import br.foodfy.foodfy_gui.backend.RequestDataLoader;
import br.foodfy.foodfy_gui.controller.MainController;
import br.foodfy.foodfy_gui.fxmlcontroller.FoodfyView;
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

    	MainController controller = MainController.getInstance();
    	controller.setRequestDataLoader(new RequestDataLoader());
    	
    	FoodfyView foodfyView = new FoodfyView();
    	loader.setController(foodfyView);
    	Parent root = loader.load();
    	
    	controller.setFoodfyView(foodfyView);
    	controller.setCoreLogic(new CoreLogic());
    	
    	Scene scene = new Scene(root, WIDTH, HEIGTH);
        stage.setScene(scene);
        stage.show();
    }
}