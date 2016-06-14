package kr.co.nexters.together.service.mapper;

import kr.co.nexters.together.protocol.dto.RegionDTO;
import kr.co.nexters.together.protocol.models.MRegion;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RegionMapper {
    RegionMapper INSTANCE = Mappers.getMapper(RegionMapper.class);

    RegionDTO toDTO(MRegion region);
}
