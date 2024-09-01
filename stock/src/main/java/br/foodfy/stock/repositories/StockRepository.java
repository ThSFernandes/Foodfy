package br.foodfy.stock.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.foodfy.stock.domain.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long>{
	
	Optional<Stock> findStockByName(String name);
	
}
