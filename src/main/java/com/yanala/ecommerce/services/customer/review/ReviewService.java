package com.yanala.ecommerce.services.customer.review;

import com.yanala.ecommerce.dto.OrderedProductsResponseDto;
import com.yanala.ecommerce.dto.ReviewDto;

import java.io.IOException;

public interface ReviewService {
    OrderedProductsResponseDto getOrderedProductsDetailsByOrderId(Long orderId);

    ReviewDto giveReview(ReviewDto reviewDto) throws IOException;
}
