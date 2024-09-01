package br.foodfy.order.model;


import java.util.List;

public class MenuResponse {
    private Long id;
    private String name;
    private List<MenuItemResponse> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MenuItemResponse> getItems() {
        return items;
    }

    public void setItems(List<MenuItemResponse> items) {
        this.items = items;
    }
}

