package com.zendesk.zendeskticketviewerbackend.controller;

import com.zendesk.zendeskticketviewerbackend.model.TicketDTO;
import com.zendesk.zendeskticketviewerbackend.service.ZendeskAPI;
import com.zendesk.zendeskticketviewerbackend.service.ZendeskTicketService;
import com.zendesk.zendeskticketviewerbackend.service.model.GetZendeskTicketResponse;
import com.zendesk.zendeskticketviewerbackend.service.model.Links;
import com.zendesk.zendeskticketviewerbackend.service.model.Meta;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.zendesk.zendeskticketviewerbackend.service.ZendeskAPI.basicUrl;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class ZendeskTicketControllerTests {

    private static final String AUTH_HEADER = "auth-header";
    private static final String SUB_DOMAIN = "zccshuaizhelp";

    @Mock
    private ZendeskAPI zendeskAPI;

    private ZendeskTicketService zendeskTicketService;

    private TicketController ticketController;

    private HttpServletRequest request;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        zendeskTicketService = new ZendeskTicketService(zendeskAPI);
        ticketController = new TicketController(zendeskTicketService);
        request = new MockHttpServletRequest();
    }
    // we test if the getTickets() method in controller will return right status code, we set it request successfully
    @Test
    public void getTickets_success() {
        //Arrange
        GetZendeskTicketResponse getZendeskTicketResponse = GetZendeskTicketResponse.builder()
                .tickets(new ArrayList<>())
                .meta(Meta.builder().hasMore(false).build())
                .links(Links.builder().build())
                .build();
        Mockito.when(zendeskAPI.getTicketsWithCursorPagination(any(), any(), any()))
                .thenReturn(getZendeskTicketResponse);

        //Act
        List<TicketDTO> ticketsResult = zendeskTicketService.getAllTickets(SUB_DOMAIN, AUTH_HEADER);
        ResponseEntity<List<TicketDTO>> result = ticketController.getTickets(SUB_DOMAIN, request);

        //Assert
        assertEquals(200, result.getStatusCodeValue());
        Mockito.verify(zendeskAPI, Mockito.times(1)).getTicketsWithCursorPagination(SUB_DOMAIN, AUTH_HEADER, basicUrl);
    }
}
