package kr.co.nexters.together.service.mapper;

import kr.co.nexters.together.protocol.dto.ArticleDTO;
import kr.co.nexters.together.protocol.models.MArticle;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {TravelPreferenceMapper.class, UserMapper.class, RegionMapper.class})
public interface ArticleMapper {
    ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

    ArticleDTO toDTO(MArticle article);
}
