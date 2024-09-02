package br.foodfy.menuservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "menu_id", referencedColumnName = "id"),
        @JoinColumn(name = "menu_name", referencedColumnName = "name")
    })
    @JsonBackReference
    private Menu menu;
}