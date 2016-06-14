package kr.co.nexters.together.resource;

import com.google.inject.Inject;
import kr.co.nexters.together.common.PagingList;
import kr.co.nexters.together.common.TogetherResponse;
import kr.co.nexters.together.common.TogetherResponses;
import kr.co.nexters.together.common.resource.AbstractResource;
import kr.co.nexters.together.protocol.dto.ArticleDTO;
import kr.co.nexters.together.protocol.dto.request.ArticleAddRequest;
import kr.co.nexters.together.protocol.models.MArticle;
import kr.co.nexters.together.protocol.models.MUser;
import kr.co.nexters.together.service.ArticlesService;
import kr.co.nexters.together.service.mapper.ArticleMapper;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.stream.Collectors;

@Path("/articles")
public class ArticlesResource extends AbstractResource {
    private ArticlesService articlesService;

    @Inject
    public ArticlesResource(ArticlesService articlesService) {
        this.articlesService = articlesService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TogetherResponse<PagingList<ArticleDTO>> get(
            @Context HttpServletRequest request,
            @QueryParam("latitude") Double latitude,
            @QueryParam("longitude") Double longitude,
            @QueryParam("radius") Integer radius,
            @QueryParam("regionId") Long regionId,
            @QueryParam("pageToken") String pageToken) throws Exception {
        try {
            getPermissionManager().checkPermission(request);
            ArticlesService.GetArticleOptions options = new ArticlesService.GetArticleOptions()
                    .setLatitude(latitude)
                    .setLongitude(longitude)
                    .setRadius(radius)
                    .setRegionId(regionId)
                    .setPageToken(pageToken);
            PagingList<MArticle> articles = articlesService.get(options);
            PagingList<ArticleDTO> articleDTOs = new PagingList<ArticleDTO>()
                    .setResults(articles.getResults().stream().map(ArticleMapper.INSTANCE::toDTO).collect(Collectors.toList()))
                    .setPaging(articles.getPaging());
            return TogetherResponses.success(articleDTOs);
        } catch (Exception exception) {
            throw exception;
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TogetherResponse<ArticleDTO> post(
            @Context HttpServletRequest request,
            ArticleAddRequest articleAddRequest) throws Exception {
        try {
            MUser user = getPermissionManager().checkPermission(request);
            MArticle addedArticle = articlesService.add(user.getId(), articleAddRequest);
            ArticleDTO articleDTO = ArticleMapper.INSTANCE.toDTO(addedArticle);
            return TogetherResponses.success(articleDTO);
        } catch (Exception exception) {
            throw exception;
        }
    }
}
