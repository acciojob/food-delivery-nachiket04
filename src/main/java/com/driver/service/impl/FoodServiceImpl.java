package com.driver.service.impl;

import com.driver.io.entity.FoodEntity;
import com.driver.io.repository.FoodRepository;
import com.driver.service.FoodService;
import com.driver.shared.dto.FoodDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

    int generatedId = 1;

    @Autowired
    FoodRepository foodRepository;

    @Override
    public FoodDto createFood(FoodDto food) {
        FoodEntity foodEntity = FoodEntity.builder().
                foodCategory(food.getFoodCategory()).
                foodName(food.getFoodName()).
                foodPrice(food.getFoodPrice()).
                foodId(Integer.toString(generatedId)).
                build();

        generatedId++;
        foodRepository.save(foodEntity);
        foodEntity = foodRepository.findByFoodId(foodEntity.getFoodId());
        food.setId(foodEntity.getId());
        food.setFoodId(foodEntity.getFoodId());

        return food;
    }

    @Override
    public FoodDto getFoodById(String foodId) throws Exception {
        FoodEntity foodEntity = foodRepository.findByFoodId(foodId);
        if(foodEntity == null){
            throw new Exception("Food not found");
        }
        FoodDto foodDto = convertEntityToDto(foodEntity);
        return foodDto;
    }

    @Override
    public FoodDto updateFoodDetails(String foodId, FoodDto food) throws Exception {
        FoodEntity foodEntity = FoodEntity.builder().
                foodCategory(food.getFoodCategory()).
                foodName(food.getFoodName()).
                foodPrice(food.getFoodPrice()).
                foodId(foodId).
                id(foodRepository.findByFoodId(food.getFoodId()).getId()).
                build();

        foodRepository.save(foodEntity);
        food = convertEntityToDto(foodEntity);
        return food;
    }

    @Override
    public void deleteFoodItem(String id) throws Exception {
        FoodEntity foodEntity = foodRepository.findByFoodId(id);
        if(foodEntity == null){
            throw new Exception("Food not found");
        }
        foodRepository.delete(foodEntity);
    }

    @Override
    public List<FoodDto> getFoods() {
        List<FoodDto> foodDtoList = new ArrayList<>();
        Iterable<FoodEntity> foodEntityList = foodRepository.findAll();

        for(FoodEntity foodEntity: foodEntityList){
            FoodDto foodDto = convertEntityToDto(foodEntity);
            foodDtoList.add(foodDto);
        }
        return foodDtoList;
    }

    public FoodDto convertEntityToDto(FoodEntity foodEntity){
        FoodDto foodDto = FoodDto.builder().
                foodPrice(foodEntity.getFoodPrice()).
                foodId(foodEntity.getFoodId()).
                foodName(foodEntity.getFoodName()).
                foodCategory(foodEntity.getFoodCategory()).
                id(foodEntity.getId()).
                build();

        return foodDto;
    }
}