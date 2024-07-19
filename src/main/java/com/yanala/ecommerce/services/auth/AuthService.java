package com.yanala.ecommerce.services.auth;

import com.yanala.ecommerce.dto.SignupRequest;
import com.yanala.ecommerce.dto.UserDto;

public interface AuthService {
    UserDto createUser(SignupRequest signupRequest);

    boolean hasUserWithEmail(String email);
}
