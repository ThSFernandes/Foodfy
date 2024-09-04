package br.foodfy.menuservice;

import br.foodfy.menuservice.models.Menu;
import br.foodfy.menuservice.models.MenuItem;
import br.foodfy.menuservice.repository.MenuRepository;
import br.foodfy.menuservice.service.MenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuServiceTest {

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private MenuService menuService;

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
        when(menuRepository.save(menu)).thenReturn(menu);

        Menu createdMenu = menuService.createMenu(menu);

        assertNotNull(createdMenu);
        assertEquals("Test Menu", createdMenu.getName());
        verify(menuRepository, times(1)).save(menu);
    }

    @Test
    void testCreateMenu_InvalidMenu_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> menuService.createMenu(null));
    }

    @Test
    void testGetMenuById_Success() {
        when(menuRepository.findById(menu.getId())).thenReturn(Optional.of(menu));

        Menu foundMenu = menuService.getMenu(menu.getId());

        assertNotNull(foundMenu);
        assertEquals("Test Menu", foundMenu.getName());
        verify(menuRepository, times(1)).findById(menu.getId());
    }

    @Test
    void testGetMenuById_NotFound_ThrowsException() {
        when(menuRepository.findById(menu.getId())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> menuService.getMenu(menu.getId()));
    }

    @Test
    void testValidateMenuItem_Success() {
        MenuItem menuItem = new MenuItem();
        menuItem.setId(1L);
        menuItem.setName("Test Item");
        menuItem.setPrice(10.0);
        menu.getItems().add(menuItem);

        when(menuRepository.findById(menu.getId())).thenReturn(Optional.of(menu));

        MenuItem validatedItem = menuService.validateMenuItem(menu.getId(), menuItem.getId());

        assertNotNull(validatedItem);
        assertEquals("Test Item", validatedItem.getName());
    }

    @Test
    void testValidateMenuItem_NotFound_ThrowsException() {
        when(menuRepository.findById(menu.getId())).thenReturn(Optional.of(menu));

        assertThrows(NoSuchElementException.class, () -> menuService.validateMenuItem(menu.getId(), 2L));
    }

    // Outros testes para update e delete...
}
