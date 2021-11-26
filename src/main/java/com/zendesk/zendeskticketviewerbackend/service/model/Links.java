package com.zendesk.zendeskticketviewerbackend.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Links {

    @JsonProperty("prev")
    String prev;

    @JsonProperty("next")
    String next;
}
