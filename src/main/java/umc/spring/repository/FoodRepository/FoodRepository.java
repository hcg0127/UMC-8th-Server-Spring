package umc.spring.repository.FoodRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.domain.Food;

public interface FoodRepository extends JpaRepository<Food, Long> {
}
