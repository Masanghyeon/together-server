package kr.co.nexters.together.service.mapper;

import kr.co.nexters.together.protocol.TravelPreference;
import kr.co.nexters.together.protocol.models.MTravelPreference;

public class TravelPreferenceMapper {

    public TravelPreference asTravelPreference(MTravelPreference travelPreference) {
        return travelPreference.getValue();
    }
}
