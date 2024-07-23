package com.yanala.ecommerce.services.admin.coupon;

import com.yanala.ecommerce.entity.Coupon;
import com.yanala.ecommerce.exceptions.ValidationException;
import com.yanala.ecommerce.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCouponServiceImpl implements AdminCouponService{
    private final CouponRepository couponRepository;

    @Override
    public Coupon createCoupon(Coupon coupon){
        if (couponRepository.existsByCode(coupon.getCode())){
            throw new ValidationException("Coupon code already exists!");
        }
        return couponRepository.save(coupon);
    }

    @Override
    public List<Coupon> getAllCoupons(){
        return couponRepository.findAll();
    }
}
