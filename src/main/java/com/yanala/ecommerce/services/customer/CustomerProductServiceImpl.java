package com.yanala.ecommerce.services.customer;

import com.yanala.ecommerce.dto.ProductDto;
import com.yanala.ecommerce.entity.Product;
import com.yanala.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerProductServiceImpl implements CustomerProductService{

    private final ProductRepository productRepository;

    @Override
    public List<ProductDto> getAllProducts(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> searchProductByTitle(String title){
        List<Product> products = productRepository.findAllByNameContaining(title);
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }
}
