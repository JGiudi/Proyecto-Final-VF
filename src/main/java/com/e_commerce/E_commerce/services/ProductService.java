package com.e_commerce.E_commerce.services;

import com.e_commerce.E_commerce.entities.Client;
import com.e_commerce.E_commerce.entities.Product;
import com.e_commerce.E_commerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product createProduct (Product product){
        return productRepository.save(product);
    }
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }
    public Product updateProduct(Long id, Product productDetail){
        Product product = productRepository.findById(id).orElseThrow();
        product.setName(productDetail.getName());
        product.setDescription(productDetail.getDescription());
        product.setPrice(productDetail.getPrice());
        product.setStock(productDetail.getStock());
        return productRepository.save(product);

    }
    public void deleteProduct (Long id){
        productRepository.deleteById(id);
    }
}
