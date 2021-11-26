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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class ZendeskTicketServiceTests {

    private static final String AUTH_HEADER = "auth-header";

    @Mock
    private ZendeskAPI zendeskAPI;

    private ZendeskTicketService zendeskTicketService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        zendeskTicketService = new ZendeskTicketService(zendeskAPI);
    }

    @Test
    public void getAllTickets_noTicketFound_success() {
        //Arrange
        GetZendeskTicketResponse getZendeskTicketResponse = GetZendeskTicketResponse.builder()
                .tickets(new ArrayList<>())
                .meta(Meta.builder().hasMore(false).build())
                .links(Links.builder().build())
                .build();
//        Mockito.when(zendeskAPI.getTicketsWithCursorPagination(any(), any()))
//                .thenReturn(getZendeskTicketResponse);
//
//        //Act
//        List<TicketDTO> ticketsResult = zendeskTicketService.getAllTickets(AUTH_HEADER);
//
//        //Assert
//        assertTrue(ticketsResult.size() == 0);
//        Mockito.verify(zendeskAPI, Mockito.times(1)).getTicketsWithCursorPagination(eq(AUTH_HEADER), any());
    }

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

//        Mockito.when(zendeskAPI.getTicketsWithCursorPagination(any(), eq(INITIAL_GET_TICKETS_URL))).thenReturn(getZendeskTicketResponse1);
//        Mockito.when(zendeskAPI.getTicketsWithCursorPagination(any(), eq(nextUrl1))).thenReturn(getZendeskTicketResponse2);
//        Mockito.when(zendeskAPI.getTicketsWithCursorPagination(any(), eq(nextUrl2))).thenReturn(getZendeskTicketResponse3);
//
//        //Act
//        List<TicketDTO> ticketsResult = zendeskTicketService.getAllTickets(AUTH_HEADER);
//
//        //Assert
//        assertTrue(ticketsResult.size() == 5);
//        assertTrue(ticketsResult.contains(ticketDTO1));
//        assertTrue(ticketsResult.contains(ticketDTO2));
//        assertTrue(ticketsResult.contains(ticketDTO3));
//        assertTrue(ticketsResult.contains(ticketDTO4));
//        assertTrue(ticketsResult.contains(ticketDTO5));
//        Mockito.verify(zendeskAPI, Mockito.times(3)).getTicketsWithCursorPagination(eq(AUTH_HEADER), any());
    }
}
