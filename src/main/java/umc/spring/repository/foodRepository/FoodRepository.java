package umc.spring.repository.foodRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.domain.Food;

public interface FoodRepository extends JpaRepository<Food, Long> {
}
