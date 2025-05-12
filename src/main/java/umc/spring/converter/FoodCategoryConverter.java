package umc.spring.converter;

import umc.spring.domain.Food;
import umc.spring.domain.mapping.FoodCategory;

import java.util.List;
import java.util.stream.Collectors;

public class FoodCategoryConverter {

    public static List<FoodCategory> toFoodCategoryList(List<Food> foodList) {
        return foodList.stream()
                .map(food ->
                        FoodCategory.builder()
                                .food(food)
                                .build()
                ).collect(Collectors.toList());
    }
}
