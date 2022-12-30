package com.driver.service.impl;

import com.driver.io.entity.UserEntity;
import com.driver.io.repository.UserRepository;
import com.driver.service.UserService;
import com.driver.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    int generatedId = 1;

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto user) throws Exception {
        UserEntity userEntity = UserEntity.builder().
                firstName(user.getFirstName()).
                lastName(user.getLastName()).
                email(user.getEmail()).
                userId(Integer.toString(generatedId)).
                build();

        generatedId++;
        userRepository.save(userEntity);
        userEntity = userRepository.findByUserId(userEntity.getUserId());
        user.setUserId(userEntity.getUserId());
        return user;
    }

    @Override
    public UserDto getUser(String email) throws Exception {
        UserEntity userEntity = userRepository.findByEmail(email);
        UserDto userDto = convertEntityToDto(userEntity);
        return userDto;
    }

    @Override
    public UserDto getUserByUserId(String userId) throws Exception {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity == null){
            throw new Exception("Invalid User Id");
        }
        UserDto userDto = convertEntityToDto(userEntity);

        return userDto;
    }

    @Override
    public UserDto updateUser(String userId, UserDto user) throws Exception {
        UserEntity userEntity = UserEntity.builder().
                firstName(user.getFirstName()).
                lastName(user.getLastName()).
                email(user.getEmail()).
                userId(userId).
                build();

        userRepository.save(userEntity);

        UserDto userDto = convertEntityToDto(userEntity);

        return userDto;
    }

    @Override
    public void deleteUser(String userId) throws Exception {
        UserEntity user = userRepository.findByUserId(userId);
        userRepository.delete(user);
    }

    @Override
    public List<UserDto> getUsers() {
        Iterable<UserEntity> userEntityList = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for(UserEntity userEntity: userEntityList){
            UserDto userDto = convertEntityToDto(userEntity);
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    public UserDto convertEntityToDto(UserEntity userEntity){
        UserDto userDto = UserDto.builder().
                userId(userEntity.getUserId()).
                firstName(userEntity.getFirstName()).
                lastName(userEntity.getLastName()).
                email(userEntity.getEmail()).build();

        return userDto;
    }
}