package com.yanala.ecommerce.controller.admin;

import com.yanala.ecommerce.dto.OrderDto;
import com.yanala.ecommerce.services.admin.adminOrder.AdminOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminOrderController {
    private final AdminOrderService adminOrderService;

    @GetMapping("/placedOrders")
    public ResponseEntity<List<OrderDto>> getAllPlacedOrders(){
        return ResponseEntity.ok(adminOrderService.getAllPlacedOrders());
    }
}
