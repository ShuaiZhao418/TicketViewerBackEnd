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
    // We write out prev and next to continue request other information
    @JsonProperty("prev")
    String prev;

    @JsonProperty("next")
    String next;
}
