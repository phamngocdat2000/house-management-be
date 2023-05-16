package com.student.searchroom.model.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class District {
    private String name;
    @JsonProperty("level3s")
    private List<Street> streets;
}
