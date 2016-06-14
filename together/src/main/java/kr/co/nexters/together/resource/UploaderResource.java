package kr.co.nexters.together.resource;

import com.google.inject.Inject;
import com.sun.jersey.multipart.FormDataParam;
import kr.co.nexters.together.common.TogetherResponse;
import kr.co.nexters.together.common.TogetherResponses;
import kr.co.nexters.together.common.resource.AbstractResource;
import kr.co.nexters.together.protocol.dto.response.UploadImageResult;
import kr.co.nexters.together.protocol.models.MUser;
import kr.co.nexters.together.service.UploaderService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.File;

@Path("/upload")
public class UploaderResource extends AbstractResource {
    private UploaderService uploaderService;

    @Inject
    public UploaderResource(UploaderService uploaderService) {
        this.uploaderService = uploaderService;
    }

    @POST
    @Path("/image")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public TogetherResponse<UploadImageResult> uploadImage(
            @Context HttpServletRequest request,
            @FormDataParam("file") File file) throws Exception {
        try {
            MUser user = getPermissionManager().checkPermission(request);
            UploadImageResult result = uploaderService.uploadImage(user, file);
            return TogetherResponses.success(result);
        } catch (Exception exception) {
            throw exception;
        }
    }
}
