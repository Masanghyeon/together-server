package kr.co.nexters.together.service.mapper;

import kr.co.nexters.together.protocol.dto.ProfilePhotoDTO;
import kr.co.nexters.together.protocol.models.MProfilePhoto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = ImageMapper.class)
public interface ProfilePhotoMapper {
    ProfilePhotoMapper INSTANCE = Mappers.getMapper(ProfilePhotoMapper.class);

    ProfilePhotoDTO toDTO(MProfilePhoto profilePhoto);
}
