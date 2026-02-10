package com.Skill.Marketplace.SM.DTO.SearchDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@
        AllArgsConstructor
public class SearchResultDTO {
    private Long id;
    private String username;
    private String skillName;
    private Double rate;
    private String description;
    private Integer experience;
    private String serviceMode;


}
