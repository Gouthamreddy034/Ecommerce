package com.yanala.ecommerce.services.customer.cart;

import com.yanala.ecommerce.dto.AddProductInCartDto;
import com.yanala.ecommerce.dto.OrderDto;
import com.yanala.ecommerce.dto.PlaceOrderDto;
import org.springframework.http.ResponseEntity;

public interface CartService {
    ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto);

    OrderDto getCartByUserId(Long userId);

    OrderDto applyCoupon(Long userId, String code);

    OrderDto increaseProductQuantity(AddProductInCartDto addProductInCartDto);

    OrderDto decreaseProductQuantity(AddProductInCartDto addProductInCartDto);
    OrderDto placeOrder(PlaceOrderDto placeOrderDto);
}
