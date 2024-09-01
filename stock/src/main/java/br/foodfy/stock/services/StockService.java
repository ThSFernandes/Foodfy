package br.foodfy.stock.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.foodfy.stock.domain.Material;
import br.foodfy.stock.repositories.MaterialRepository;
import jakarta.transaction.Transactional;

@Service
public class StockService {
	
	private final MaterialRepository materialRepository;
	
	public StockService(MaterialRepository materialRepository) {
		this.materialRepository = materialRepository;
	}

	public List<Material> retriveAllMaterial() {
		return materialRepository.findAll();
	}

    public Material getMaterialByName(String name) {
        Optional<Material> material = materialRepository.findMaterialByName(name);
        if (material.isPresent()) {
            return material.get();
        } else {
            throw new RuntimeException("Material not found");
        }
    }
	
	public void insertStock(Material material) {
		Optional<Material> searchForMaterialResult = materialRepository
				.findMaterialByName(material.getName());
		
		if(searchForMaterialResult.isPresent()) {
			throw new IllegalStateException("Name already registered!");
		}
		materialRepository.save(material);
		
		System.out.println("Name of the material: " + material.getName());
	}
	
    @Transactional
    public void updateMaterialQuantity(String name, Integer quantity) {
        Optional<Material> materialOpt = materialRepository.findMaterialByName(name);
        if (materialOpt.isPresent()) {
            Material material = materialOpt.get();
            material.setQuantity(quantity);
            material.setLastUpdated(LocalDateTime.now());
            materialRepository.save(material);
        } else {
            throw new RuntimeException("Material not found");
        }
    }

    public Map<String, Integer> checkMaterialsStock(Map<String, Integer> materialQuantities) {
        Map<String, Integer> outOfStockMaterials = new HashMap<>();
        
        for (var entry : materialQuantities.entrySet()) {
            String name = entry.getKey();
            Integer requiredQuantity = entry.getValue();

            Optional<Material> optionalMaterial = materialRepository.findMaterialByName(name);
            if (optionalMaterial.isPresent()) {
                Material material = optionalMaterial.get();
                Integer availableQuantity = material.getQuantity();
                if (availableQuantity < requiredQuantity) {
                    outOfStockMaterials.put(name, requiredQuantity - availableQuantity);
                }
            } else {
                outOfStockMaterials.put(name, requiredQuantity);
            }
        }

        return outOfStockMaterials;
    }
}
