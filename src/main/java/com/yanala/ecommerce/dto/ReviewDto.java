package com.yanala.ecommerce.dto;

import com.yanala.ecommerce.entity.Product;
import com.yanala.ecommerce.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ReviewDto {
    private Long id;

    private Long rating;

    private String Description;

    private MultipartFile img;

    private byte[] returnedImg;

    private Long userId;

    private Long productId;

    private String username;
}
