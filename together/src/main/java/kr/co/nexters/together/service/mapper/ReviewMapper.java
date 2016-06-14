package kr.co.nexters.together.service.mapper;

import kr.co.nexters.together.protocol.dto.UserReviewDTO;
import kr.co.nexters.together.protocol.models.MReview;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = UserMapper.class)
public interface ReviewMapper {
    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    UserReviewDTO toDTO(MReview review);
}
