package kr.co.nexters.together.service.mapper;

import kr.co.nexters.together.protocol.dto.RegionGroupDTO;
import kr.co.nexters.together.protocol.models.MRegionGroup;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = RegionMapper.class)
public interface RegionGroupMapper {
    RegionGroupMapper INSTANCE = Mappers.getMapper(RegionGroupMapper.class);

    RegionGroupDTO toDTO(MRegionGroup regionGroup);
}
