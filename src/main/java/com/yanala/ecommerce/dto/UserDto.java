package com.yanala.ecommerce.dto;

import com.yanala.ecommerce.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private UserRole userRole;
}
