package com.ecommerce_backend.service.impl;

import com.ecommerce_backend.exception.ResourceNotFoundException;
import com.ecommerce_backend.model.User;
import com.ecommerce_backend.payload.UserDto;
import com.ecommerce_backend.repository.UserRepository;
import com.ecommerce_backend.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {

        /*Converting UserDto->User using modelMapper*/
        User user = modelMapper.map(userDto, User.class);

        /*Encoding password using BcryptPasswordEncoder and setting it back to user*/
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);

        User savedUser = userRepository.save(user);

        /*Converting User->UserDto* using modelMapper*/
        UserDto mapUserDto = modelMapper.map(savedUser, UserDto.class);

        return mapUserDto;
    }

    @Override
    public UserDto getUserByUserId(int userId) {

        User findById = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("No User found with userId : " + userId));

        UserDto userDto = modelMapper.map(findById, UserDto.class);

        return userDto;
    }

    @Override
    public void deleteUserByUserId(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with userId : " + userId));
        userRepository.delete(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> findAll = userRepository.findAll();

        List<UserDto> getAllDtos = findAll.stream().map((user) -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());

        return getAllDtos;
    }

    @Override
    public UserDto updateUser(UserDto userDto, int userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with userId  " + userId));

        user.setAbout(userDto.getAbout());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setActive(userDto.isActive());
        user.setAddress(userDto.getAddress());
        user.setGender(userDto.getGender());
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPhone(userDto.getPhone());

        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, UserDto.class);
    }
}