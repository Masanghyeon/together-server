package kr.co.nexters.together.service.mapper;

import kr.co.nexters.together.protocol.dto.CompactUserDTO;
import kr.co.nexters.together.protocol.dto.UserDTO;
import kr.co.nexters.together.protocol.models.MUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {TravelPreferenceMapper.class, ProfilePhotoMapper.class} )
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(MUser user);

    CompactUserDTO toCompactDTO(MUser user);
}
