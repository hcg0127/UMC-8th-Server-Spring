package umc.spring.converter;

import umc.spring.domain.Store;
import umc.spring.web.dto.StoreRequestDTO;
import umc.spring.web.dto.StoreResponseDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class StoreConverter {

    public static Store toStore(StoreRequestDTO.JoinDTO request) {
        return Store.builder()
                .name(request.getName())
                .address(request.getAddress())
                .reviewList(new ArrayList<>())
                .missionList(new ArrayList<>())
                .foodCategoryList(new ArrayList<>())
                .build();
    }

    public static StoreResponseDTO.JoinResultDTO toJoinResultDTO(Store store) {
        return StoreResponseDTO.JoinResultDTO.builder()
                .storeId(store.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
