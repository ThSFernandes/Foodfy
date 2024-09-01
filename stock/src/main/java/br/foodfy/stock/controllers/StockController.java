package br.foodfy.stock.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.foodfy.stock.domain.Stock;
import br.foodfy.stock.services.StockService;

@RestController
@RequestMapping(path="api/stock")
public class StockController {

	private final StockService stockService;
	
	public StockController(StockService stockService) {
		this.stockService = stockService;
	}
	
	@GetMapping
	public List<Stock> retrieveStock() {
		return stockService.retriveStock();
	}
	
	@PostMapping
	public void inserterStock(@RequestBody Stock stock) {
		stockService.insertStock(stock);
	}
}
