package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.io.entity.FoodEntity;
import com.driver.model.request.FoodDetailsRequestModel;
import com.driver.model.response.FoodDetailsResponse;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.RequestOperationName;
import com.driver.model.response.RequestOperationStatus;
import com.driver.service.impl.FoodServiceImpl;
import com.driver.shared.dto.FoodDto;
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
@RequestMapping("/foods")
public class FoodController {

	@Autowired
	FoodServiceImpl foodService;

	@GetMapping(path="/{id}")
	public FoodDetailsResponse getFood(@PathVariable String id) throws Exception{
		FoodDto foodDto = foodService.getFoodById(id);
		FoodDetailsResponse foodDetailsResponse = convertDtoToResponse(foodDto);
		return foodDetailsResponse;
	}

	@PostMapping("/create")
	public FoodDetailsResponse createFood(@RequestBody FoodDetailsRequestModel foodDetails) {
		FoodDto foodDto = FoodDto.builder().
				foodCategory(foodDetails.getFoodCategory()).
				foodName(foodDetails.getFoodName()).
				foodPrice(foodDetails.getFoodPrice()).
				build();

		foodDto = foodService.createFood(foodDto);
		FoodDetailsResponse foodDetailsResponse = convertDtoToResponse(foodDto);

		return foodDetailsResponse;
	}

	@PutMapping(path="/{id}")
	public FoodDetailsResponse updateFood(@PathVariable String id, @RequestBody FoodDetailsRequestModel foodDetails) throws Exception{
		FoodDto foodDto = FoodDto.builder().
				foodCategory(foodDetails.getFoodCategory()).
				foodName(foodDetails.getFoodName()).
				foodPrice(foodDetails.getFoodPrice()).
				build();
		foodDto = foodService.updateFoodDetails(id, foodDto);
		FoodDetailsResponse foodDetailsResponse = convertDtoToResponse(foodDto);
		return foodDetailsResponse;
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteFood(@PathVariable String id) throws Exception{
		foodService.deleteFoodItem(id);
		OperationStatusModel operationStatusModel = OperationStatusModel.builder().operationName(RequestOperationName.DELETE.name()).operationResult(RequestOperationStatus.SUCCESS.name()).build();
		return operationStatusModel;
	}
	
	@GetMapping()
	public List<FoodDetailsResponse> getFoods() {
		List<FoodDto> foodDtoList = foodService.getFoods();
		List<FoodDetailsResponse> foodDetailsResponseList = new ArrayList<>();

		for(FoodDto foodDto: foodDtoList){
			FoodDetailsResponse foodDetailsResponse = convertDtoToResponse(foodDto);
			foodDetailsResponseList.add(foodDetailsResponse);
		}

		return foodDetailsResponseList;
	}

	public FoodDetailsResponse convertDtoToResponse(FoodDto foodDto){
		FoodDetailsResponse foodDetailsResponse = FoodDetailsResponse.builder().
				foodCategory(foodDto.getFoodCategory()).
				foodName(foodDto.getFoodName()).
				foodPrice(foodDto.getFoodPrice()).
				foodId(foodDto.getFoodId()).
				build();

		return foodDetailsResponse;
	}
}
