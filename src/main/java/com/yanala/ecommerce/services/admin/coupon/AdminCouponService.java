package com.yanala.ecommerce.services.admin.coupon;

import com.yanala.ecommerce.entity.Coupon;

import java.util.List;

public interface AdminCouponService {
    Coupon createCoupon(Coupon coupon);

    List<Coupon> getAllCoupons();
}
