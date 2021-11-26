package com.zendesk.zendeskticketviewerbackend.controller;

import com.zendesk.zendeskticketviewerbackend.exception.UnauthorizedException;
import com.zendesk.zendeskticketviewerbackend.model.TicketDTO;
import com.zendesk.zendeskticketviewerbackend.service.ZendeskTicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TicketController {

    private static final String AUTH_HEADER_PARAMETER_AUTHORIZATION = "authorization";

    private final ZendeskTicketService zendeskTicketService;

    public TicketController(ZendeskTicketService zendeskTicketService) {
        this.zendeskTicketService = zendeskTicketService;
    }

    @CrossOrigin
    @GetMapping("/tickets/{subdomain}")
    public ResponseEntity<List<TicketDTO>> getTickets(@PathVariable String subdomain, HttpServletRequest request) {
        try {
            List<TicketDTO> tickets = zendeskTicketService.getAllTickets(subdomain, getBasicAuth(request));
            return ResponseEntity.ok(tickets);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    private String getBasicAuth(HttpServletRequest request) {
        return request.getHeader(AUTH_HEADER_PARAMETER_AUTHORIZATION);
    }
}
