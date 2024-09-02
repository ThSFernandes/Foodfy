package br.foodfy.menuservice.models;

import java.io.Serializable;

import lombok.Data;

@Data
public class MenuId implements Serializable {
    private Long id;
    private String name;

    public MenuId() {}
}