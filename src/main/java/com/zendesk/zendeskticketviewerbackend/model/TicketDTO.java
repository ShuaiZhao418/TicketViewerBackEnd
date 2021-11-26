package com.zendesk.zendeskticketviewerbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketDTO {

    @JsonProperty("created_at")
    String createdAt;

    @JsonProperty("updated_at")
    String updatedAt;

    @JsonProperty("type")
    String type;

    @JsonProperty("subject")
    String subject;

    @JsonProperty("description")
    String description;

    @JsonProperty("priority")
    String priority;

    @JsonProperty("status")
    Status status;

    @JsonProperty("requester_id")
    String requesterId;

    @JsonProperty("assignee_id")
    String assigneeId;

    @JsonProperty("tags")
    List<String> tags;
}
