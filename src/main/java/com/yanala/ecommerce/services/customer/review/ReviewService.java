package com.yanala.ecommerce.services.customer.review;

import com.yanala.ecommerce.dto.OrderedProductsResponseDto;

public interface ReviewService {
    OrderedProductsResponseDto getOrderedProductsDetailsByOrderId(Long orderId);
}
