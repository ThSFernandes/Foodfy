package br.foodfy.foodfy_gui.value_objects;

public class RequestData {
	public String name;
	public String url;

	public RequestData(String name, String url) {
		this.name = name;
		this.url = url;
	}

	@Override
	public String toString() {
		return name;
	}
}