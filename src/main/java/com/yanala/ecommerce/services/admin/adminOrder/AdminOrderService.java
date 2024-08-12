package com.yanala.ecommerce.services.admin.adminOrder;

import com.yanala.ecommerce.dto.OrderDto;

import java.util.List;

public interface AdminOrderService {
    List<OrderDto> getAllPlacedOrders();
    OrderDto changeOrderStatus(Long orderId,String status);
}
