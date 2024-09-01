package com.restaurante.menuservice.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurante.menuservice.models.Menu;
import com.restaurante.menuservice.repository.MenuRepository;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public Menu createMenu(Menu menu) throws IllegalArgumentException {
        if (menu == null || menu.getName() == null) {
            throw new IllegalArgumentException("Invalid menu data provided.");
        }
        return menuRepository.save(menu);
    }

    public Menu getMenu(Long id) throws NoSuchElementException, IllegalArgumentException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid menu ID provided.");
        }
        return menuRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Menu not found with id: " + id));
    }

    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    public Menu updateMenu(Long id, Menu menuDetails) throws NoSuchElementException, IllegalArgumentException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid menu ID provided.");
        }
        if (menuDetails == null || menuDetails.getName() == null) {
            throw new IllegalArgumentException("Invalid menu details provided.");
        }
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Menu not found with id: " + id));
        menu.setName(menuDetails.getName());
        return menuRepository.save(menu);
    }

    public void deleteMenu(Long id) throws NoSuchElementException, IllegalArgumentException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid menu ID provided.");
        }
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Menu not found with id: " + id));
        menuRepository.delete(menu);
    }
}