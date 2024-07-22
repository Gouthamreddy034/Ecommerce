package com.yanala.ecommerce.services.admin.adminproduct;

import com.yanala.ecommerce.dto.ProductDto;

import java.io.IOException;
import java.util.List;

public interface AdminProductService {
    ProductDto addProduct(ProductDto productDto) throws IOException;
    List<ProductDto> getAllProducts();

    List<ProductDto> getAllProductsByName(String title);

    boolean deleteProduct(Long id);
}
