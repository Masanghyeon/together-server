package kr.co.nexters.together.resource;

import com.google.inject.Inject;
import kr.co.nexters.together.common.TogetherResponse;
import kr.co.nexters.together.common.TogetherResponses;
import kr.co.nexters.together.common.resource.AbstractResource;
import kr.co.nexters.together.protocol.dto.request.IssueAccessTokenRequest;
import kr.co.nexters.together.protocol.dto.response.AccessTokenResult;
import kr.co.nexters.together.service.AccessTokenService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/accessToken")
public class AccessTokenResource extends AbstractResource {
    private AccessTokenService accessTokenService;

    @Inject
    public AccessTokenResource(AccessTokenService accessTokenService) {
        this.accessTokenService = accessTokenService;
    }

    @POST
    @Path("/issue")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public TogetherResponse<AccessTokenResult> issue(
            @Context HttpServletRequest request,
            IssueAccessTokenRequest issueAccessTokenRequest) throws Exception {
        try {
            AccessTokenResult result = accessTokenService.issue(issueAccessTokenRequest);
            return TogetherResponses.success(result);
        } catch (Exception exception) {
            throw exception;
        }
    }

}
