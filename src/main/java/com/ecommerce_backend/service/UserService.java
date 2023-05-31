package com.ecommerce_backend.service;

import com.ecommerce_backend.payload.UserDto;

import java.util.List;

public interface UserService {
    public UserDto createUser(UserDto userDto);

    public UserDto getUserByUserId(int userId);

    public void deleteUserByUserId(int userId);

    public List<UserDto> getAllUsers();

    public UserDto updateUser(UserDto userDto, int userId);
}