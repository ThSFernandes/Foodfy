package br.foodfy.menuservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.foodfy.menuservice.models.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
