package com.yanala.ecommerce.services.customer;

import com.yanala.ecommerce.dto.ProductDetailDto;
import com.yanala.ecommerce.dto.ProductDto;

import java.util.List;

public interface CustomerProductService {
    List<ProductDto> getAllProducts();

    List<ProductDto> searchProductByTitle(String title);

    ProductDetailDto getProductDetailById(Long productId);
}
