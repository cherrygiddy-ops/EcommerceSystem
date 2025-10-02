package com.morrisco.net.eCommerceSystem.services;

import com.morrisco.net.eCommerceSystem.dtos.RegisterUserRequest;
import com.morrisco.net.eCommerceSystem.dtos.UserDto;
import com.morrisco.net.eCommerceSystem.entities.Role;
import com.morrisco.net.eCommerceSystem.exceptions.EmailExistsException;
import com.morrisco.net.eCommerceSystem.mappers.UserMapper;
import com.morrisco.net.eCommerceSystem.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class UserService  {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    public UserDto registerUser(RegisterUserRequest request) {
        var user= userMapper.toEntity(request);
        if (userRepository.existsByEmail(request.getEmail()))
            throw  new EmailExistsException();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);

        return userMapper.toDto(user);
    }

    public List<UserDto> getUserDtos(String sortBy) {
        if (!Set.of("name","email").contains(sortBy))
            sortBy = "name";

        return userRepository.findAll(Sort.by(sortBy)).stream()
                .map(userMapper::toDto)
                .toList();
    }




}
