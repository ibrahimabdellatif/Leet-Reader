package com.leetreader.leetReader.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ImageProfileDataDTO(@JsonProperty("data") UserImageProfileDTO data) {
}
