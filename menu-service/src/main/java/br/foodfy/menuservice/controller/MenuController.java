package br.foodfy.menuservice.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.foodfy.menuservice.models.Menu;
import br.foodfy.menuservice.models.MenuItem;
import br.foodfy.menuservice.service.MenuService;

@RestController
@RequestMapping("/api/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @PostMapping
    public ResponseEntity<Menu> createMenu(@RequestBody Menu menu) {
        try {
            Menu createdMenu = menuService.createMenu(menu);
            return ResponseEntity.ok(createdMenu);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Menu> getMenuById(@PathVariable Long id) {
        try {
            Menu menu = menuService.getMenu(id);
            return ResponseEntity.ok(menu);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    
    @GetMapping("/{menuId}/items/{itemId}")
    public ResponseEntity<MenuItem> validateMenuItem(@PathVariable Long menuId, @PathVariable Long itemId) {
        try {
            MenuItem menuItem = menuService.validateMenuItem(menuId, itemId);
            return ResponseEntity.ok(menuItem);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @GetMapping("/name/{name}")
    public ResponseEntity<Menu> getMenuByName(@PathVariable String name) {
        try {
            Menu menu = menuService.getMenu(name);
            return ResponseEntity.ok(menu);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Menu>> getAllMenus() {
        try {
            List<Menu> menus = menuService.getAllMenus();
            return ResponseEntity.ok(menus);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Menu> updateMenuById(@PathVariable Long id, @RequestBody Menu menu) {
        try {
            Menu updatedMenu = menuService.updateMenu(id, menu);
            return ResponseEntity.ok(updatedMenu);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/name/{name}")
    public ResponseEntity<Menu> updateMenuByName(@PathVariable String name, @RequestBody Menu menu) {
        try {
            Menu updatedMenu = menuService.updateMenu(name, menu);
            return ResponseEntity.ok(updatedMenu);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuById(@PathVariable Long id) {
        try {
            menuService.deleteMenu(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/name/{name}")
    public ResponseEntity<Void> deleteMenuByName(@PathVariable String name) {
        try {
            menuService.deleteMenu(name);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}