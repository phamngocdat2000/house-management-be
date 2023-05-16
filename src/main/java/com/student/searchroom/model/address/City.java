package com.student.searchroom.model.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class City {
    private String name;
    @JsonProperty("level2s")
    private List<District> districts;
}
