package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.model.request.UserDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.RequestOperationName;
import com.driver.model.response.RequestOperationStatus;
import com.driver.model.response.UserResponse;
import com.driver.service.UserService;
import com.driver.service.impl.UserServiceImpl;
import com.driver.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserServiceImpl userService;

	@GetMapping(path = "/{id}")
	public UserResponse getUser(@PathVariable String id) throws Exception{
		UserDto userDto = userService.getUserByUserId(id);
		UserResponse userResponse = UserResponse.builder().
				userId(userDto.getUserId()).
				firstName(userDto.getFirstName()).
				lastName(userDto.getLastName()).
				email(userDto.getEmail()).
				build();
		return userResponse;
	}

	@PostMapping()
	public UserResponse createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception{

		UserDto userDto = UserDto.builder().
				firstName(userDetails.getFirstName()).
				lastName(userDetails.getLastName()).
				email(userDetails.getEmail()).
				build();

		userDto = userService.createUser(userDto);

		UserResponse userResponse = UserResponse.builder().
				userId(userDto.getUserId()).
				firstName(userDto.getFirstName()).
				lastName(userDto.getLastName()).
				email(userDto.getEmail()).
				build();

		return userResponse;
	}

	@PutMapping(path = "/{id}")
	public UserResponse updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) throws Exception{
		UserDto userDto = UserDto.builder().
				firstName(userDetails.getFirstName()).
				lastName(userDetails.getLastName()).
				email(userDetails.getEmail()).
				build();

		userDto = userService.updateUser(id, userDto);

		UserResponse userResponse = UserResponse.builder().
				userId(userDto.getUserId()).
				firstName(userDto.getFirstName()).
				lastName(userDto.getLastName()).
				email(userDto.getEmail()).
				build();

		return userResponse;
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteUser(@PathVariable String id) throws Exception{
		userService.deleteUser(id);
		OperationStatusModel operationStatusModel = OperationStatusModel.builder().operationName(RequestOperationName.DELETE.name()).operationResult(RequestOperationStatus.SUCCESS.name()).build();
		return operationStatusModel;
	}
	
	@GetMapping()
	public List<UserResponse> getUsers(){
		List<UserDto> userDtoList = userService.getUsers();
		List<UserResponse> userResponseList = new ArrayList<>();
		for(UserDto userDto: userDtoList){
			UserResponse userResponse = UserResponse.builder().
					userId(userDto.getUserId()).
					firstName(userDto.getFirstName()).
					lastName(userDto.getLastName()).
					email(userDto.getEmail()).
					build();
			userResponseList.add(userResponse);
		}
		return userResponseList;
	}
	
}
