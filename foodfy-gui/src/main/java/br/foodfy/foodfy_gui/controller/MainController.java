package br.foodfy.foodfy_gui.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import br.foodfy.foodfy_gui.backend.CoreLogic;
import br.foodfy.foodfy_gui.backend.RequestDataLoader;
import br.foodfy.foodfy_gui.fxmlcontroller.FoodfyView;
import br.foodfy.foodfy_gui.value_objects.RequestData;

public class MainController {
	
	private static MainController mainController;
	private CoreLogic coreLogic;
	private FoodfyView foodfyView;
	private RequestDataLoader requestDataLoader;
	
	public synchronized static MainController getInstance() {
		if(mainController == null) {
			mainController = new MainController();
		}
		
		return mainController;
	}
	
	private MainController() {
		
	}
	
	public void setCoreLogic(CoreLogic coreLogic) {
		this.coreLogic = coreLogic;
	}

	public void setFoodfyView(FoodfyView foodfyView) {
		this.foodfyView = foodfyView;
	}

	public void setRequestDataLoader(RequestDataLoader requestDataLoader) {
		this.requestDataLoader = requestDataLoader;
	}

	public String executeHttpRequest(String type, String url) {
		return coreLogic.executeHttpRequest(type, url);
	}
	
	public void loadRequestsFromFile(String filePath, Map<String, List<RequestData>> requestByType) throws IOException {
		requestDataLoader.loadRequestsFromFile(filePath, requestByType);
	}

	
}
