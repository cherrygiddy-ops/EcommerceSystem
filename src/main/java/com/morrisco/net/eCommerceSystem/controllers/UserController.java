package com.morrisco.net.eCommerceSystem.controllers;

import com.morrisco.net.eCommerceSystem.dtos.ChangePasswordRequest;
import com.morrisco.net.eCommerceSystem.dtos.RegisterUserRequest;
import com.morrisco.net.eCommerceSystem.dtos.UpdateUserRequest;
import com.morrisco.net.eCommerceSystem.dtos.UserDto;
import com.morrisco.net.eCommerceSystem.mappers.UserMapper;
import com.morrisco.net.eCommerceSystem.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserController(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @GetMapping()
    public Iterable<UserDto> getAllUsers (@RequestParam(required = false,defaultValue = "" ,name = "sort") String sortBy){

//        if (!Set.of("name","email").contains(sortBy))
//            sortBy= "name";
//
//        return userRepository.findAll(Sort.by(sortBy)).stream()
//                .map(userMapper::toDto)
//                .toList();
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable int id) {
//        var user = userRepository.findById(id).orElse(null);
//
//        if (user == null){
//            return ResponseEntity.notFound().build();
//          }
//        return ResponseEntity.ok(userMapper.toDto(user));
        return null;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(){
//            @RequestBody RegisterUserRequest request,
//            UriComponentsBuilder uriBuilder){
//       var user= userMapper.toEntity(request);
//
//       userRepository.save(user);
//
//        var userDto=userMapper.toDto(user);
//
//        var uri =uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();
//        return ResponseEntity.created(uri).body(userDto);
        return null;

    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UpdateUserRequest request, @PathVariable(name = "id") int id){
//        var user =userRepository.findById(id).orElse(null);
//
//        if (user == null)
//            return ResponseEntity.notFound().build();
//        userMapper.updateUser(request,user);
//
//        userRepository.save(user);
//
//        return ResponseEntity.ok(userMapper.toDto(user));
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "id") int id){
        var user =userRepository.findById(id).orElse(null);
        if (user == null)
            return ResponseEntity.notFound().build();

        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void>updatePassword(@PathVariable(name = "id") int id, @RequestBody ChangePasswordRequest request){
//        var user =userRepository.findById(id).orElse(null);
//        if (user == null)
//            return ResponseEntity.notFound().build();
//        if (!user.getPassword().equals(request.getOldPassword())){
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//        user.setPassword(request.getNewPassword());
//        userRepository.save(user);
//
//        return ResponseEntity.noContent().build();
        return null;
    }
}
