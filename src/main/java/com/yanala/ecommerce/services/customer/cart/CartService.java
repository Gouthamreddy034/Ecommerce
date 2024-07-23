package com.yanala.ecommerce.services.customer.cart;

import com.yanala.ecommerce.dto.AddProductInCartDto;
import org.springframework.http.ResponseEntity;

public interface CartService {
    ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto);
}
