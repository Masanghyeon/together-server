package kr.co.nexters.together.resource;

import kr.co.nexters.together.common.TogetherResponse;
import kr.co.nexters.together.common.TogetherResponses;
import kr.co.nexters.together.common.jerseyextra.PATCH;
import kr.co.nexters.together.common.resource.AbstractResource;
import kr.co.nexters.together.protocol.dto.UserReviewDTO;
import kr.co.nexters.together.protocol.dto.request.UserReviewPatchRequest;
import kr.co.nexters.together.protocol.models.MReview;
import kr.co.nexters.together.service.ReviewService;
import kr.co.nexters.together.service.mapper.ReviewMapper;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/reviews/{reviewId}")
public class ReviewResource extends AbstractResource {
    private ReviewService reviewService;

    @Inject
    public ReviewResource(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TogetherResponse<UserReviewDTO> get(
            @Context HttpServletRequest request,
            @PathParam("reviewId") long reviewId) throws Exception {
        try {
            getPermissionManager().checkPermission(request);
            MReview review = reviewService.get(reviewId);
            UserReviewDTO reviewDTO = ReviewMapper.INSTANCE.toDTO(review);
            return TogetherResponses.success(reviewDTO);
        } catch (Exception exception) {
            throw exception;
        }
    }

    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TogetherResponse<UserReviewDTO> patch(
            @Context HttpServletRequest request,
            @PathParam("reviewID") long reviewID,
            UserReviewPatchRequest patchRequest) throws Exception {
        try {
            getPermissionManager().checkPermission(request);
            MReview pachedReview= reviewService.patch(reviewID, patchRequest);
            UserReviewDTO reviewDTO = ReviewMapper.INSTANCE.toDTO(pachedReview);
            return TogetherResponses.success(reviewDTO);
        } catch (Exception exception) {
            throw exception;
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public TogetherResponse<Boolean> delete(
            @Context HttpServletRequest request,
            @PathParam("reviewId") long reviewId) throws Exception {
        try {
            getPermissionManager().checkPermission(request);
            reviewService.delete(reviewId);
            return TogetherResponses.success(Boolean.TRUE);
        } catch (Exception exception) {
            throw exception;
        }
    }
}
