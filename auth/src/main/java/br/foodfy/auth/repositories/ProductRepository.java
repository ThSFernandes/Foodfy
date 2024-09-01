package br.foodfy.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.foodfy.auth.domain.product.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
}
