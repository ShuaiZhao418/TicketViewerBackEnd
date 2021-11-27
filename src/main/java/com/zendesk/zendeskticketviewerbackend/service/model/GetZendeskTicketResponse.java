package com.zendesk.zendeskticketviewerbackend.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zendesk.zendeskticketviewerbackend.model.TicketDTO;
import lombok.*;

import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
// This is a model mapping to Zendesk API response
public class GetZendeskTicketResponse {

    @JsonProperty("tickets")
    List<TicketDTO> tickets;

    @JsonProperty("meta")
    Meta meta;

    @JsonProperty("links")
    Links links;
}
