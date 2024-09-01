package br.foodfy.stock.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.foodfy.stock.domain.Stock;
import br.foodfy.stock.repositories.StockRepository;

@Service
public class StockService {
	
	private final StockRepository stockRepository;
	
	public StockService(StockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}

	public List<Stock> retriveStock() {
		return stockRepository.findAll();
	}
	
	public void insertStock(Stock stock) {
		Optional<Stock> searchForMaterialResult = stockRepository
				.findStockByName(stock.getName());
		
		if(searchForMaterialResult.isPresent()) {
			throw new IllegalStateException("Name already registered!");
		}
		stockRepository.save(stock);
		
		System.out.println("Name of the material: " + stock.getName());
	}
}
