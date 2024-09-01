package com.restaurante.menuservice.service;

import com.restaurante.menuservice.exceptions.ResourceNotFoundException;
import com.restaurante.menuservice.models.Menu;
import com.restaurante.menuservice.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    // Implementação do método createMenu
    public Menu createMenu(Menu menu) {
        // Salva o menu e retorna o menu salvo
        return menuRepository.save(menu);
    }

    public Menu getMenu(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found with id: " + id));
    }

    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    public Menu updateMenu(Long id, Menu menuDetails) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found with id: " + id));
        menu.setName(menuDetails.getName());
        // Se desejar atualizar os itens do menu, adicione a lógica aqui
        // Exemplo: menu.setItems(menuDetails.getItems());
        return menuRepository.save(menu);
    }

    public void deleteMenu(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found with id: " + id));
        menuRepository.delete(menu);
    }
}
