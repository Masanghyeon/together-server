package kr.co.nexters.together.resource;

import kr.co.nexters.together.common.TogetherResponse;
import kr.co.nexters.together.common.TogetherResponses;
import kr.co.nexters.together.common.jerseyextra.PATCH;
import kr.co.nexters.together.common.resource.AbstractResource;
import kr.co.nexters.together.protocol.dto.ArticleDTO;
import kr.co.nexters.together.protocol.dto.request.ArticlePatchRequest;
import kr.co.nexters.together.protocol.models.MArticle;
import kr.co.nexters.together.service.ArticleService;
import kr.co.nexters.together.service.mapper.ArticleMapper;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/articles/{articleId}")
public class ArticleResource extends AbstractResource {
    private ArticleService articleService;

    @Inject
    public ArticleResource(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TogetherResponse<ArticleDTO> get(
            @Context HttpServletRequest request,
            @PathParam("articleId") long articleId) throws Exception {
        try {
            getPermissionManager().checkPermission(request);
            MArticle article = articleService.get(articleId);
            ArticleDTO articleDTO = ArticleMapper.INSTANCE.toDTO(article);
            return TogetherResponses.success(articleDTO);
        } catch (Exception exception) {
            throw exception;
        }
    }

    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TogetherResponse<ArticleDTO> patch(
            @Context HttpServletRequest request,
            @PathParam("articleId") long articleId,
            ArticlePatchRequest articlePatchRequest) throws Exception {
        try {
            getPermissionManager().checkPermission(request);
            MArticle patchedArticle = articleService.patch(articleId, articlePatchRequest);
            ArticleDTO articleDTO = ArticleMapper.INSTANCE.toDTO(patchedArticle);
            return TogetherResponses.success(articleDTO);
        } catch (Exception exception) {
            throw exception;
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public TogetherResponse<Boolean> delete(
            @Context HttpServletRequest request,
            @PathParam("articleId") long articleId) throws Exception {
        try {
            getPermissionManager().checkPermission(request);
            articleService.delete(articleId);
            return TogetherResponses.success(Boolean.TRUE);
        } catch (Exception exception) {
            throw exception;
        }
    }
}
