package br.foodfy.stock;
import br.foodfy.stock.controllers.StockController;
import br.foodfy.stock.domain.Material;
import br.foodfy.stock.services.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StockControllerTest {

	@Mock
	private StockService stockService;

	@InjectMocks
	private StockController stockController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testRetrieveByName_Success() {
		// Arrange
		Material material = new Material(1L, "Material1", "123456", "Category1", 10, new BigDecimal("5.00"));
		when(stockService.getMaterialByName("Material1")).thenReturn(material);

		// Act
		ResponseEntity<?> response = stockController.retrieveByName("Material1");

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(material, response.getBody());
		verify(stockService, times(1)).getMaterialByName("Material1");
	}

	@Test
	void testRetrieveByName_NotFound() {
		// Arrange
		when(stockService.getMaterialByName("NonExistent")).thenThrow(new RuntimeException("Material not found"));

		// Act
		ResponseEntity<?> response = stockController.retrieveByName("NonExistent");

		// Assert
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("Material with name NonExistent not found", response.getBody());
		verify(stockService, times(1)).getMaterialByName("NonExistent");
	}

	@Test
	void testInserterStock_Success() {
		// Arrange
		Material material = new Material(1L, "Material1", "123456", "Category1", 10, new BigDecimal("5.00"));
		doNothing().when(stockService).insertStock(material);

		// Act
		stockController.inserterStock(material);

		// Assert
		verify(stockService, times(1)).insertStock(material);
	}

	@Test
	void testInserterStock_AlreadyRegistered() {
		// Arrange
		Material material = new Material(1L, "Material1", "123456", "Category1", 10, new BigDecimal("5.00"));
		doThrow(new IllegalStateException("Name already registered!")).when(stockService).insertStock(material);

		// Act & Assert
		try {
			stockController.inserterStock(material);
		} catch (IllegalStateException e) {
			assertEquals("Name already registered!", e.getMessage());
		}

		verify(stockService, times(1)).insertStock(material);
	}
}
