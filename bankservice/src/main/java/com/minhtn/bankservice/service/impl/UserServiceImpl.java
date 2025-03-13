package com.minhtn.bankservice.service.impl;

import com.minhtn.bankservice.entity.User;
import com.minhtn.bankservice.model.user.LoginDTO;
import com.minhtn.bankservice.model.user.RegUserDTO;
import com.minhtn.bankservice.model.user.UpdatePasswordDTO;
import com.minhtn.bankservice.model.user.UserDTO;
import com.minhtn.bankservice.security.JwtService;
import com.minhtn.bankservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends BaseService implements UserService {

    // inject bean
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public UserDTO getUserByUsername(String userName) {
        User user = userRepository.findById(userName).orElseThrow(() -> new ServiceException("User not found"));
        return UserDTO.from(user);
    }

    @Override
    public UserDTO register(RegUserDTO regUserDTO) {
        Optional<User> userOptional = userRepository.findByUsername(regUserDTO.getUsername());
        if (userOptional.isPresent()) {
            throw new ServiceException("User already exists");
        }
        User user = User.builder()
                .username(regUserDTO.getUsername())
                .password(passwordEncoder.encode(regUserDTO.getPassword()))
                .role("ADMIN")
                .build();
        return UserDTO.from(userRepository.save(user));
    }

    @Override
    public String login(LoginDTO loginDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        User user = userRepository.findById(loginDTO.getUsername()).orElseThrow(() -> new ServiceException("User not found"));
        return jwtService.generateToken(user);
    }

    @Override
    public void updatePassword(String userId, UpdatePasswordDTO updatePasswordDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ServiceException("User not found"));
        if (!passwordEncoder.matches(updatePasswordDTO.getOldPassword(), user.getPassword())) {
            throw new ServiceException("Old password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(updatePasswordDTO.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public String getUsernameLogin() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user.getUsername();
    }
}
