package kr.co.nexters.together.protocol.dto;

import java.util.List;

public class RegionGroupDTO {
    private String name;
    private List<RegionDTO> regions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RegionDTO> getRegions() {
        return regions;
    }

    public void setRegions(List<RegionDTO> regions) {
        this.regions = regions;
    }
}
