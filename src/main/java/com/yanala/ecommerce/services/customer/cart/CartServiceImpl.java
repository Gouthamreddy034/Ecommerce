package com.yanala.ecommerce.services.customer.cart;

import com.yanala.ecommerce.dto.AddProductInCartDto;
import com.yanala.ecommerce.dto.CartItemsDto;
import com.yanala.ecommerce.dto.OrderDto;
import com.yanala.ecommerce.dto.PlaceOrderDto;
import com.yanala.ecommerce.entity.*;
import com.yanala.ecommerce.enums.OrderStatus;
import com.yanala.ecommerce.exceptions.ValidationException;
import com.yanala.ecommerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Override
    public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto){
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
        Optional<CartItems> optionalCartItems = cartItemsRepository.findByProductIdAndOrderIdAndUserId
                (addProductInCartDto.getProductId(),activeOrder.getId(),addProductInCartDto.getUserId());

        if (optionalCartItems.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }else{
            Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
            Optional<User> optionalUser = userRepository.findById(addProductInCartDto.getUserId());

            if (optionalProduct.isPresent() && optionalUser.isPresent()){
                CartItems cart = new CartItems();
                cart.setProduct(optionalProduct.get());
                cart.setPrice(optionalProduct.get().getPrice());
                cart.setQuantity(1L);
                cart.setUser(optionalUser.get());
                cart.setOrder(activeOrder);

                CartItems updatedCart = cartItemsRepository.save(cart);

                activeOrder.setTotalAmount(activeOrder.getTotalAmount()+cart.getPrice());
                activeOrder.setAmount(activeOrder.getAmount()+cart.getPrice());
                activeOrder.getCartItems().add(cart);

                orderRepository.save(activeOrder);

                return ResponseEntity.status(HttpStatus.CREATED).body(cart);
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Product Not Found");
            }
        }
    }

    @Override
    public OrderDto getCartByUserId(Long userId){
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        List<CartItemsDto> cartItemsDtoList = activeOrder.getCartItems().stream().map(CartItems::getCartItemsDto).toList();

        OrderDto orderDto = new OrderDto();
        orderDto.setAmount(activeOrder.getAmount());
        orderDto.setId(activeOrder.getId());
        orderDto.setOrderStatus(activeOrder.getOrderStatus());
        orderDto.setDiscount(activeOrder.getDiscount());
        orderDto.setTotalAmount(activeOrder.getTotalAmount());
        orderDto.setCartItems(cartItemsDtoList);

        if(activeOrder.getCoupon()!=null){
            orderDto.setCouponName(activeOrder.getCoupon().getName());
        }

        return orderDto;
    }

    @Override
    public OrderDto applyCoupon(Long userId,String code){
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        Coupon coupon = couponRepository.findByCode(code).orElseThrow(()->new ValidationException("Coupon not found."));

        if(couponIsExpired(coupon)){
            throw new ValidationException("Coupon has Expired");
        }

        double discountAmount = ((coupon.getDiscount()/100.0)* activeOrder.getTotalAmount());
        double netAmount = activeOrder.getTotalAmount()-discountAmount;

        activeOrder.setAmount((long) netAmount);
        activeOrder.setDiscount((long) discountAmount);
        activeOrder.setCoupon(coupon);

        orderRepository.save(activeOrder);
        return activeOrder.getOrderDto();
    }

    private boolean couponIsExpired(Coupon coupon) {
        Date currentDate = new Date();
        Date expirationDate = coupon.getExpirationDate();

        return expirationDate!=null && currentDate.after(expirationDate);
    }

    @Override
    public OrderDto increaseProductQuantity(AddProductInCartDto addProductInCartDto){
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(),OrderStatus.Pending);
        Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
        Optional<CartItems> optionalCartItem = cartItemsRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDto.getProductId(),activeOrder.getId(),addProductInCartDto.getUserId()
        );

        if(optionalProduct.isPresent() && optionalCartItem.isPresent()){
            CartItems cartItem = optionalCartItem.get();
            Product product = optionalProduct.get();

            activeOrder.setAmount(activeOrder.getAmount()+product.getPrice());
            activeOrder.setTotalAmount(activeOrder.getTotalAmount()+ product.getPrice());

            cartItem.setQuantity(cartItem.getQuantity()+1);

            return getOrderDto(activeOrder, cartItem);
        }
        return null;
    }

    private OrderDto getOrderDto(Order activeOrder, CartItems cartItem) {
        if(activeOrder.getCoupon()!=null){
            double discountAmount = ((activeOrder.getCoupon().getDiscount()/100.0)* activeOrder.getTotalAmount());
            double netAmount = activeOrder.getTotalAmount()-discountAmount;

            activeOrder.setAmount((long) netAmount);
            activeOrder.setDiscount((long) discountAmount);
        }

        cartItemsRepository.save(cartItem);
        orderRepository.save(activeOrder);

        return activeOrder.getOrderDto();
    }

    @Override
    public OrderDto decreaseProductQuantity(AddProductInCartDto addProductInCartDto){
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(),OrderStatus.Pending);
        Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
        Optional<CartItems> optionalCartItem = cartItemsRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDto.getProductId(),activeOrder.getId(),addProductInCartDto.getUserId()
        );

        if(optionalProduct.isPresent() && optionalCartItem.isPresent()){
            CartItems cartItem = optionalCartItem.get();
            Product product = optionalProduct.get();

            activeOrder.setAmount(activeOrder.getAmount()-product.getPrice());
            activeOrder.setTotalAmount(activeOrder.getTotalAmount()- product.getPrice());

            cartItem.setQuantity(cartItem.getQuantity()-1);

            return getOrderDto(activeOrder, cartItem);
        }
        return null;
    }

    public OrderDto placeOrder(PlaceOrderDto placeOrderDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(placeOrderDto.getUserId(), OrderStatus.Pending);
        Optional<User> optionalUser = userRepository.findById(placeOrderDto.getUserId());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (activeOrder != null) {
                // Update the existing active order
                activeOrder.setOrderDescription(placeOrderDto.getOrderDescription());
                activeOrder.setAddress(placeOrderDto.getAddress());
                activeOrder.setOrderStatus(OrderStatus.Placed);
                activeOrder.setDate(new Date());
                activeOrder.setTrackingId(UUID.randomUUID());
                orderRepository.save(activeOrder);
            } else {
                // If no active order, create a new placed order
                activeOrder = new Order();
                activeOrder.setUser(user);
                activeOrder.setOrderDescription(placeOrderDto.getOrderDescription());
                activeOrder.setAddress(placeOrderDto.getAddress());
                activeOrder.setOrderStatus(OrderStatus.Pending);
                activeOrder.setDate(new Date());
                activeOrder.setTrackingId(UUID.randomUUID());
                activeOrder.setAmount(0L); // Initialize amount
                activeOrder.setTotalAmount(0L); // Initialize total amount
                activeOrder.setDiscount(0L); // Initialize discount
                orderRepository.save(activeOrder);
            }

            // Create a new pending order for the user
            Order newPendingOrder = new Order();
            newPendingOrder.setAmount(0L);
            newPendingOrder.setTotalAmount(0L);
            newPendingOrder.setDiscount(0L);
            newPendingOrder.setUser(user);
            newPendingOrder.setOrderStatus(OrderStatus.Pending);
            orderRepository.save(newPendingOrder);

            return activeOrder.getOrderDto();
        }
        return null;
    }

    public List<OrderDto> getMyPlacedOrders(Long userId){
        return orderRepository
                .findByUserIdAndOrderStatusIn(userId,List.of(OrderStatus.Pending,OrderStatus.Shipped,OrderStatus.Delivered))
                .stream()
                .map(Order::getOrderDto)
                .collect(Collectors.toList());
    }

    public OrderDto searchOrderByTrackingId(UUID trackingId){
        Optional<Order> optionalOrder = orderRepository.findByTrackingId(trackingId);
        return optionalOrder.map(Order::getOrderDto).orElse(null);
    }

}
