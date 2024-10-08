package br.foodfy.menuservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.foodfy.menuservice.models.Menu;
import br.foodfy.menuservice.models.MenuItem;
import br.foodfy.menuservice.repository.MenuRepository;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    // Método para criar um novo menu
    public Menu createMenu(Menu menu) throws IllegalArgumentException {
        if (menu == null || menu.getName() == null) {
            throw new IllegalArgumentException("Invalid menu data provided.");
        }

        // Associar itens ao menu
        if (menu.getItems() != null) {
            for (MenuItem item : menu.getItems()) {
                if (item != null) {
                    item.setMenu(menu); // Associar o item ao menu
                }
            }
        } else {
            menu.setItems(new ArrayList<>()); // Garantir que a lista de itens não seja null
        }

        return menuRepository.save(menu);
    }

    // Método para obter um menu pelo ID
    public Menu getMenu(Long id) throws NoSuchElementException, IllegalArgumentException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid menu ID provided.");
        }
        return menuRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Menu not found with id: " + id));
    }
    
    public MenuItem validateMenuItem(Long menuId, Long itemId) throws NoSuchElementException, IllegalArgumentException {
        if (menuId == null || menuId <= 0 || itemId == null || itemId <= 0) {
            throw new IllegalArgumentException("Invalid menu ID or item ID provided.");
        }

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new NoSuchElementException("Menu not found with id: " + menuId));

        return menu.getItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Item not found with id: " + itemId + " in menu: " + menuId));
    }

    // Sobrecarga: Método para obter um menu pelo nome
    public Menu getMenu(String name) throws NoSuchElementException, IllegalArgumentException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid menu name provided.");
        }
        return menuRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Menu not found with name: " + name));
    }

    // Método para obter todos os menus
    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    // Método para atualizar um menu existente pelo ID
    public Menu updateMenu(Long id, Menu menuDetails) throws NoSuchElementException, IllegalArgumentException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid menu ID provided.");
        }
        if (menuDetails == null || menuDetails.getName() == null) {
            throw new IllegalArgumentException("Invalid menu details provided.");
        }

        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Menu not found with id: " + id));

        // Atualizar o nome do menu
        menu.setName(menuDetails.getName());

        // Atualizar os itens do menu
        if (menuDetails.getItems() != null) {
            for (MenuItem item : menuDetails.getItems()) {
                if (item != null) {
                    item.setMenu(menu); // Associar o item ao menu
                }
            }
            menu.setItems(menuDetails.getItems());
        }

        return menuRepository.save(menu);
    }

    // Sobrecarga: Método para atualizar um menu existente pelo nome
    public Menu updateMenu(String name, Menu menuDetails) throws NoSuchElementException, IllegalArgumentException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid menu name provided.");
        }
        if (menuDetails == null || menuDetails.getName() == null) {
            throw new IllegalArgumentException("Invalid menu details provided.");
        }

        Menu menu = menuRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Menu not found with name: " + name));

        // Atualizar o nome do menu
        menu.setName(menuDetails.getName());

        // Atualizar os itens do menu
        if (menuDetails.getItems() != null) {
            for (MenuItem item : menuDetails.getItems()) {
                if (item != null) {
                    item.setMenu(menu); // Associar o item ao menu
                }
            }
            menu.setItems(menuDetails.getItems());
        }

        return menuRepository.save(menu);
    }

    // Método para deletar um menu pelo ID
    public void deleteMenu(Long id) throws NoSuchElementException, IllegalArgumentException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid menu ID provided.");
        }
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Menu not found with id: " + id));
        menuRepository.delete(menu);
    }

    // Sobrecarga: Método para deletar um menu pelo nome
    public void deleteMenu(String name) throws NoSuchElementException, IllegalArgumentException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid menu name provided.");
        }
        Menu menu = menuRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Menu not found with name: " + name));
        menuRepository.delete(menu);
    }
}