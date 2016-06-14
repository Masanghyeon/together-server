package kr.co.nexters.together.resource;

import com.google.inject.Inject;
import kr.co.nexters.together.common.TogetherResponse;
import kr.co.nexters.together.common.TogetherResponses;
import kr.co.nexters.together.common.resource.AbstractResource;
import kr.co.nexters.together.protocol.dto.request.SignUpRequest;
import kr.co.nexters.together.protocol.dto.response.SignUpResult;
import kr.co.nexters.together.service.RegistrationService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/registration")
public class RegistrationResource extends AbstractResource {
    RegistrationService registrationService;

    @Inject
    public RegistrationResource(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @POST
    @Path("/signUp")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TogetherResponse<SignUpResult> signUp(
            @Context HttpServletRequest request,
            SignUpRequest signUpRequest) throws Exception {
        try {
            SignUpResult result = registrationService.signUp(signUpRequest);
            return TogetherResponses.success(result);
        } catch (Exception exception) {
            throw exception;
        }
    }
}
