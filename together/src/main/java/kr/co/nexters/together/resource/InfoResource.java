package kr.co.nexters.together.resource;

import kr.co.nexters.together.common.TogetherResponse;
import kr.co.nexters.together.common.TogetherResponses;
import kr.co.nexters.together.common.resource.AbstractResource;
import kr.co.nexters.together.protocol.TravelPreference;
import kr.co.nexters.together.protocol.dto.RegionGroupDTO;
import kr.co.nexters.together.protocol.models.MRegionGroup;
import kr.co.nexters.together.protocol.models.MTravelPreference;
import kr.co.nexters.together.service.mapper.RegionGroupMapper;
import org.hibernate.Session;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/info")
public class InfoResource extends AbstractResource {

    @GET
    @Path("/travelPreferences")
    @Produces(MediaType.APPLICATION_JSON)
    public TogetherResponse<List<TravelPreference>> getTravelPreferences(@Context HttpServletRequest request) throws Exception {
        try {
            Session session = getSessionFactory().openSession();
            List<MTravelPreference> travelPreferences = session.createCriteria(MTravelPreference.class).list();
            List<TravelPreference> preferences = travelPreferences.stream().map(mtp -> mtp.getValue()).collect(Collectors.toList());
            session.close();
            return TogetherResponses.success(preferences);
        } catch (Exception exception) {
            throw exception;
        }
    }

    @GET
    @Path("/regionGroups")
    @Produces(MediaType.APPLICATION_JSON)
    public TogetherResponse<List<RegionGroupDTO>> getRegions(@Context HttpServletRequest request) throws Exception {
        try {
            Session session = getSessionFactory().openSession();
            List<MRegionGroup> regionGroups = session.createCriteria(MRegionGroup.class).list();
            List<RegionGroupDTO> regionGroupDTOs = regionGroups.stream().map(rg -> RegionGroupMapper.INSTANCE.toDTO(rg)).collect(Collectors.toList());
            session.close();
            return TogetherResponses.success(regionGroupDTOs);
        } catch (Exception exception) {
            throw exception;
        }
    }
}
