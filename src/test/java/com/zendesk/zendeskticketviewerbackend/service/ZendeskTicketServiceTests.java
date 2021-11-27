package com.zendesk.zendeskticketviewerbackend.service;

import com.zendesk.zendeskticketviewerbackend.model.TicketDTO;
import com.zendesk.zendeskticketviewerbackend.service.model.GetZendeskTicketResponse;
import com.zendesk.zendeskticketviewerbackend.service.model.Links;
import com.zendesk.zendeskticketviewerbackend.service.model.Meta;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static com.zendesk.zendeskticketviewerbackend.service.ZendeskAPI.basicUrl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class ZendeskTicketServiceTests {

    private static final String AUTH_HEADER = "auth-header";
    private static final String SUB_DOMAIN = "zccshuaizhelp";

    @Mock
    private ZendeskAPI zendeskAPI;

    private ZendeskTicketService zendeskTicketService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        zendeskTicketService = new ZendeskTicketService(zendeskAPI);
    }

    // We test if the method getAllTickets() in service can return the tickets,
    // we assume it requests fail and get 0 tickets, request 1 time
    @Test
    public void getAllTickets_noTicketFound_success() {
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

        //Assert
        assertEquals(0, ticketsResult.size());
        Mockito.verify(zendeskAPI, Mockito.times(1)).getTicketsWithCursorPagination(SUB_DOMAIN, AUTH_HEADER, basicUrl);
    }

    // We test if the method getAllTickets() in service can return the tickets,
    // we assume it requests successfully and get 5 tickets, request 3 times
    @Test
    public void getAllTickets_5TicketFoundInThreePages_success() {
        //Arrange
        TicketDTO ticketDTO1 = TicketDTO.builder().subject("ticket1").build();
        TicketDTO ticketDTO2 = TicketDTO.builder().subject("ticket2").build();
        TicketDTO ticketDTO3 = TicketDTO.builder().subject("ticket3").build();
        TicketDTO ticketDTO4 = TicketDTO.builder().subject("ticket4").build();
        TicketDTO ticketDTO5 = TicketDTO.builder().subject("ticket5").build();
        String nextUrl1 = "nextUrl1";
        String nextUrl2 = "nextUrl2";

        GetZendeskTicketResponse getZendeskTicketResponse1 = GetZendeskTicketResponse.builder()
                .tickets(Lists.newArrayList(ticketDTO1, ticketDTO2))
                .meta(Meta.builder().hasMore(true).build())
                .links(Links.builder().next(nextUrl1).build())
                .build();
        GetZendeskTicketResponse getZendeskTicketResponse2 = GetZendeskTicketResponse.builder()
                .tickets(Lists.newArrayList(ticketDTO3, ticketDTO4))
                .meta(Meta.builder().hasMore(true).build())
                .links(Links.builder().next(nextUrl2).build())
                .build();
        GetZendeskTicketResponse getZendeskTicketResponse3 = GetZendeskTicketResponse.builder()
                .tickets(Lists.newArrayList(ticketDTO5))
                .meta(Meta.builder().hasMore(false).build())
                .links(Links.builder().build())
                .build();

        Mockito.when(zendeskAPI.getTicketsWithCursorPagination(SUB_DOMAIN, AUTH_HEADER, basicUrl)).thenReturn(getZendeskTicketResponse1);
        Mockito.when(zendeskAPI.getTicketsWithCursorPagination(SUB_DOMAIN, AUTH_HEADER, nextUrl1)).thenReturn(getZendeskTicketResponse2);
        Mockito.when(zendeskAPI.getTicketsWithCursorPagination(SUB_DOMAIN, AUTH_HEADER, nextUrl2)).thenReturn(getZendeskTicketResponse3);

        //Act
        List<TicketDTO> ticketsResult = zendeskTicketService.getAllTickets(SUB_DOMAIN, AUTH_HEADER);

        //Assert
        assertEquals(5, ticketsResult.size());
        assertTrue(ticketsResult.contains(ticketDTO1));
        assertTrue(ticketsResult.contains(ticketDTO2));
        assertTrue(ticketsResult.contains(ticketDTO3));
        assertTrue(ticketsResult.contains(ticketDTO4));
        assertTrue(ticketsResult.contains(ticketDTO5));
        Mockito.verify(zendeskAPI, Mockito.times(1)).getTicketsWithCursorPagination(SUB_DOMAIN, AUTH_HEADER, basicUrl);
        Mockito.verify(zendeskAPI, Mockito.times(1)).getTicketsWithCursorPagination(SUB_DOMAIN, AUTH_HEADER, basicUrl);
        Mockito.verify(zendeskAPI, Mockito.times(1)).getTicketsWithCursorPagination(SUB_DOMAIN, AUTH_HEADER, basicUrl);
    }
}
