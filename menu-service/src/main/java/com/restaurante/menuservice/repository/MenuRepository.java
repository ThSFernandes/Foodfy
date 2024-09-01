package com.restaurante.menuservice.repository;

import com.restaurante.menuservice.models.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
