package com.morrisco.net.eCommerceSystem.users;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public User getUserById (Long userId){

        var user = userRepository.findById(userId).orElse(null);

        if (user ==null)
            throw new UsernameNotFoundException("user Not Found");

        return user;
    }




}
