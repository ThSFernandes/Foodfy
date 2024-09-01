package br.foodfy.menuservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import br.foodfy.menuservice.models.Menu;
import br.foodfy.menuservice.models.MenuId;

public interface MenuRepository extends JpaRepository<Menu, MenuId> {
	
    Optional<Menu> findById(Long id);
    
    Optional<Menu> findByName(String name);
}
