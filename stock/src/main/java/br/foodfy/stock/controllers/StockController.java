package br.foodfy.stock.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.foodfy.stock.domain.Material;
import br.foodfy.stock.services.StockService;

@RestController
@RequestMapping(path="stock")
public class StockController {

	private final StockService stockService;
	
	public StockController(StockService stockService) {
		this.stockService = stockService;
	}
	
	@GetMapping("/retrieveAll")
	public List<Material> retrieveAllMaterial() {
		return stockService.retriveAllMaterial();
	}
	
    @GetMapping("/retrieve")
    public ResponseEntity<?> retrieveByName(@RequestParam String name) {
        try {
            Material material = stockService.getMaterialByName(name);
            return ResponseEntity.ok(material);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Material with name " + name + " not found");
        }
    }
	
	@PostMapping("/insert")
	public void inserterStock(@RequestBody Material material) {
		stockService.insertStock(material);
	}
	
    @PutMapping("/updateQuantity/{name}")
    public ResponseEntity<?> updateQuantity(@PathVariable String name, @RequestParam Integer quantity) {
        try {
            stockService.updateMaterialQuantity(name, quantity);
            return ResponseEntity.ok("Quantity updated successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Material with name " + name + " not found");
        }
    }

    @PostMapping("/checkStock")
    public ResponseEntity<?> checkStock(@RequestBody Map<String, Integer> materialQuantities) {
        try {
            Map<String, Integer> outOfStockMaterials = stockService.checkMaterialsStock(materialQuantities);
            return ResponseEntity.ok(outOfStockMaterials);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error occurred while checking stock: " + e.getMessage());
        }
    }
}
