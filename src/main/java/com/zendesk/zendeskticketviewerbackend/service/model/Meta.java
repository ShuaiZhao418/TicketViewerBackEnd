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
public class Meta {
    // we only write "hasMore" priority to check if there is next page
    @JsonProperty("has_more")
    boolean hasMore;
}
