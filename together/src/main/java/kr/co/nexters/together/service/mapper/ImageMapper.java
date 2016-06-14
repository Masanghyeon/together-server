package kr.co.nexters.together.service.mapper;

import kr.co.nexters.together.protocol.dto.ImageDTO;
import kr.co.nexters.together.protocol.models.MImage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ImageMapper {
    ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);

    ImageDTO toDTO(MImage image);
}
