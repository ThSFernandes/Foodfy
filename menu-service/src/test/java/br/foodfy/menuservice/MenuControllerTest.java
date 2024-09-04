package br.foodfy.menuservice;

import br.foodfy.menuservice.controller.MenuController;
import br.foodfy.menuservice.models.Menu;
import br.foodfy.menuservice.models.MenuItem;
import br.foodfy.menuservice.service.MenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MenuControllerTest {

    @Mock
    private MenuService menuService;

    @InjectMocks
    private MenuController menuController;

    private Menu menu;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        menu = new Menu();
        menu.setId(1L);
        menu.setName("Test Menu");
        menu.setItems(new ArrayList<>());
    }

    @Test
    void testCreateMenu_Success() {
        when(menuService.createMenu(any(Menu.class))).thenReturn(menu);

        ResponseEntity<Menu> response = menuController.createMenu(menu);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(menu, response.getBody());
        verify(menuService, times(1)).createMenu(menu);
    }

    @Test
    void testCreateMenu_InvalidMenu_ReturnsBadRequest() {
        when(menuService.createMenu(any(Menu.class))).thenThrow(IllegalArgumentException.class);

        ResponseEntity<Menu> response = menuController.createMenu(menu);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testGetMenuById_Success() {
        when(menuService.getMenu(menu.getId())).thenReturn(menu);

        ResponseEntity<Menu> response = menuController.getMenuById(menu.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(menu, response.getBody());
    }

    @Test
    void testGetMenuById_NotFound() {
        when(menuService.getMenu(menu.getId())).thenThrow(NoSuchElementException.class);

        ResponseEntity<Menu> response = menuController.getMenuById(menu.getId());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testValidateMenuItem_Success() {
        MenuItem menuItem = new MenuItem();
        menuItem.setId(1L);
        menuItem.setName("Test Item");
        menuItem.setPrice(10.0);

        when(menuService.validateMenuItem(menu.getId(), menuItem.getId())).thenReturn(menuItem);

        ResponseEntity<MenuItem> response = menuController.validateMenuItem(menu.getId(), menuItem.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(menuItem, response.getBody());
    }

    @Test
    void testValidateMenuItem_NotFound() {
        when(menuService.validateMenuItem(menu.getId(), 2L)).thenThrow(NoSuchElementException.class);

        ResponseEntity<MenuItem> response = menuController.validateMenuItem(menu.getId(), 2L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // Outros testes para update, delete e getAll...
}
