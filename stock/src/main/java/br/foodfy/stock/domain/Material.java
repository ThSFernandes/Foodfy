package br.foodfy.stock.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Material {
    
    @Id
    @SequenceGenerator(
        name = "material_sequence",
        sequenceName = "material_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "material_sequence"
    )
    private Long materialId;

    private String name;
    private String description;
    private String barcode;
    private String category;
    private Integer quantity;
    private BigDecimal unitPrice;
    private LocalDate acquisitionDate;
    private LocalDate expiryDate;
    private String supplier;
    private String status;
    private LocalDateTime lastUpdated;
    private String notes;

    public Material(Long materialId, String name, String barcode, String category, Integer quantity,
                    BigDecimal unitPrice) {
        this.materialId = materialId;
        this.name = name;
        this.barcode = barcode;
        this.category = category;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
}