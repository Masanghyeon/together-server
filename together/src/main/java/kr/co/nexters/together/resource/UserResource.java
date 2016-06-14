package kr.co.nexters.together.resource;

import com.google.inject.Inject;
import kr.co.nexters.together.common.PagingList;
import kr.co.nexters.together.common.TogetherResponse;
import kr.co.nexters.together.common.TogetherResponses;
import kr.co.nexters.together.common.jerseyextra.PATCH;
import kr.co.nexters.together.common.resource.AbstractResource;
import kr.co.nexters.together.protocol.dto.ArticleDTO;
import kr.co.nexters.together.protocol.dto.CompactUserDTO;
import kr.co.nexters.together.protocol.dto.UserDTO;
import kr.co.nexters.together.protocol.dto.UserReviewDTO;
import kr.co.nexters.together.protocol.dto.request.ProfilePhotoPostRequest;
import kr.co.nexters.together.protocol.dto.request.UserPatchRequest;
import kr.co.nexters.together.protocol.dto.request.UserReviewAddRequest;
import kr.co.nexters.together.protocol.models.MArticle;
import kr.co.nexters.together.protocol.models.MReview;
import kr.co.nexters.together.protocol.models.MUser;
import kr.co.nexters.together.service.ArticlesService;
import kr.co.nexters.together.service.ReviewsService;
import kr.co.nexters.together.service.UserService;
import kr.co.nexters.together.service.mapper.ArticleMapper;
import kr.co.nexters.together.service.mapper.ReviewMapper;
import kr.co.nexters.together.service.mapper.UserMapper;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/users/{userId}")
public class UserResource extends AbstractResource {
    private UserService userService;
    private ArticlesService articlesService;
    private ReviewsService reviewsService;

    @Inject
    public UserResource(UserService userService,
                        ArticlesService articlesService,
                        ReviewsService reviewsService) {
        this.userService = userService;
        this.articlesService = articlesService;
        this.reviewsService = reviewsService;
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TogetherResponse<UserDTO> get(
            @Context HttpServletRequest request,
            @PathParam("userId") Long userId) throws Exception {
        try {
            getPermissionManager().checkUser(request, userId);
            MUser user = userService.get(userId);
            UserDTO userDTO = UserMapper.INSTANCE.toDTO(user);
            return TogetherResponses.success(userDTO);
        } catch (Exception exception) {
            throw exception;
        }
    }

    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TogetherResponse<UserDTO> patch(
            @Context HttpServletRequest request,
            @PathParam("userId") Long userId,
            UserPatchRequest userPatchRequest) throws Exception {
        try {
            getPermissionManager().checkUser(request, userId);
            MUser pacthedUser = userService.patch(userId, userPatchRequest);
            UserDTO userDTO = UserMapper.INSTANCE.toDTO(pacthedUser);
            return TogetherResponses.success(userDTO);
        } catch (Exception exception) {
            throw exception;
        }
    }

    @POST
    @Path("/profilePhoto")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TogetherResponse<UserDTO> setProfilePhoto(
            @Context HttpServletRequest request,
            @PathParam("userId") Long userId,
            ProfilePhotoPostRequest profilePhotoPostRequest) throws Exception {
        try {
            getPermissionManager().checkUser(request, userId);
            MUser user = userService.setProfilePhoto(userId, profilePhotoPostRequest);
            UserDTO userDTO = UserMapper.INSTANCE.toDTO(user);
            return TogetherResponses.success(userDTO);
        } catch (Exception exception) {
            throw exception;
        }
    }

    /**
     * 다른 User 의 정보를 불러올 때 사용하는 API 입니다.
     * @param request
     * @param userId
     * @return CompactUserDTO
     * @throws Exception
     */
    @GET
    @Path("/profile")
    @Produces(MediaType.APPLICATION_JSON)
    public TogetherResponse<CompactUserDTO> getProfile(
            @Context HttpServletRequest request,
            @PathParam("userId") Long userId) throws Exception {
        try {
            getPermissionManager().checkPermission(request);
            MUser user = userService.get(userId);
            CompactUserDTO userDTO = UserMapper.INSTANCE.toCompactDTO(user);
            return TogetherResponses.success(userDTO);
        } catch (Exception exception) {
            throw exception;
        }
    }

    /**
     * 내가 쓴 글 불러오기 API 입니다.
     * @param request
     * @param userId
     * @return
     * @throws Exception
     */
    @GET
    @Path("/articles")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TogetherResponse<PagingList<ArticleDTO>> getArticlesWrittenByMe(
            @Context HttpServletRequest request,
            @PathParam("userId") Long userId,
            @QueryParam("pageToken") String pageToken) throws Exception {
        try {
            getPermissionManager().checkUser(request, userId);
            ArticlesService.GetArticleOptions options = new ArticlesService.GetArticleOptions()
                    .setUserId(userId)
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

    @GET
    @Path("/reviews")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TogetherResponse<PagingList<UserReviewDTO>> getReviews(
            @Context HttpServletRequest request,
            @PathParam("userId") Long userId,
            @QueryParam("pageToken") String pageToken) throws Exception {
        try {
            getPermissionManager().checkPermission(request);
            ReviewsService.GetReviewOptions options = new ReviewsService.GetReviewOptions()
                    .setToId(userId)
                    .setPageToken(pageToken);
            PagingList<MReview> reviews = reviewsService.get(options);
            PagingList<UserReviewDTO> reviewDTOs = new PagingList<UserReviewDTO>()
                    .setResults(reviews.getResults().stream().map(ReviewMapper.INSTANCE::toDTO).collect(Collectors.toList()))
                    .setPaging(reviews.getPaging());
            return TogetherResponses.success(reviewDTOs);
        } catch (Exception exception) {
            throw exception;
        }
    }

    /**
     * 다른 User의 동행 후기를 작성할 때 쓰는 API 입니다.
     * @param request
     * @param userId
     * @param userReviewAddRequest
     * @return
     * @throws Exception
     */
    @POST
    @Path("/reviews")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TogetherResponse<UserReviewDTO> addReview(
            @Context HttpServletRequest request,
            @PathParam("userId") long userId,
            UserReviewAddRequest userReviewAddRequest) throws Exception {
        try {
            MUser author = getPermissionManager().checkPermission(request);
            MReview addedReview = reviewsService.addUserReview(userId, author.getId(), userReviewAddRequest);
            UserReviewDTO reviewDTO = ReviewMapper.INSTANCE.toDTO(addedReview);
            return TogetherResponses.success(reviewDTO);
        } catch (Exception exception) {
            throw exception;
        }
    }

    @GET
    @Path("/reviewsWrittenByMe")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TogetherResponse<PagingList<UserReviewDTO>> getReviewsWrittenByMe(
            @Context HttpServletRequest request,
            @PathParam("userId") Long userId,
            @QueryParam("pageToken") String pageToken) throws Exception {
        try {
            getPermissionManager().checkUser(request, userId);
            ReviewsService.GetReviewOptions options = new ReviewsService.GetReviewOptions()
                    .setAuthorId(userId)
                    .setPageToken(pageToken);
            PagingList<MReview> reviews = reviewsService.get(options);
            PagingList<UserReviewDTO> reviewDTOs = new PagingList<UserReviewDTO>()
                    .setResults(reviews.getResults().stream().map(ReviewMapper.INSTANCE::toDTO).collect(Collectors.toList()))
                    .setPaging(reviews.getPaging());
            return TogetherResponses.success(reviewDTOs);
        } catch (Exception exception) {
            throw exception;
        }
    }
}

