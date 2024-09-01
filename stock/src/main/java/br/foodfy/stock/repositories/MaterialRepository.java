package br.foodfy.stock.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.foodfy.stock.domain.Material;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long>{
	
	Optional<Material> findMaterialByName(String name);
	
}
