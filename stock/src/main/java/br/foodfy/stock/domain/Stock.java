package br.foodfy.stock.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table
public class Stock {
	
    @Id
    @SequenceGenerator(
        name = "stock_sequence",
        sequenceName = "stock_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "stock_sequence"
    )
    private Long stockId;

    private String name;
    private String description;
    private String barcode;
    private String category;
    private Integer stockQuantity;
    private BigDecimal unitPrice;
    private LocalDate acquisitionDate;
    private LocalDate expiryDate;
    private String supplier;
    private String status;
    private LocalDateTime lastUpdated;
    private String notes;
	
	public Stock() {
	}

	public Stock(Long stockId, String name, String barcode, String category, Integer stockQuantity,
			BigDecimal unitPrice) {
		super();
		this.stockId = stockId;
		this.name = name;
		this.barcode = barcode;
		this.category = category;
		this.stockQuantity = stockQuantity;
		this.unitPrice = unitPrice;
	}

	public Stock(Long stockId, String name, String description, String barcode, String category, Integer stockQuantity,
			BigDecimal unitPrice, LocalDate acquisitionDate, LocalDate expiryDate, String supplier,
			String status, LocalDateTime lastUpdated, String notes) {
		super();
		this.stockId = stockId;
		this.name = name;
		this.description = description;
		this.barcode = barcode;
		this.category = category;
		this.stockQuantity = stockQuantity;
		this.unitPrice = unitPrice;
		this.acquisitionDate = acquisitionDate;
		this.expiryDate = expiryDate;
		this.supplier = supplier;
		this.status = status;
		this.lastUpdated = lastUpdated;
		this.notes = notes;
	}

	public Long getStockId() {
		return stockId;
	}

	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public LocalDate getAcquisitionDate() {
		return acquisitionDate;
	}

	public void setAcquisitionDate(LocalDate acquisitionDate) {
		this.acquisitionDate = acquisitionDate;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(LocalDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}