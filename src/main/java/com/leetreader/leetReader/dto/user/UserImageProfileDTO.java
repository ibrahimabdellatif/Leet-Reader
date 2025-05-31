package com.leetreader.leetReader.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserImageProfileDTO(
        @JsonProperty("display_url") String imageProfileUrl,@JsonProperty("time") String time
){}
