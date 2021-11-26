package com.zendesk.zendeskticketviewerbackend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zendesk.zendeskticketviewerbackend.exception.UnauthorizedException;
import com.zendesk.zendeskticketviewerbackend.service.model.GetZendeskTicketResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

@Service
public class ZendeskAPI {

    public static String basicUrl = "https://anySubdomain.zendesk.com/api/v2/tickets.json?page[size]=25";

    private final HttpClient httpClient;

    public ZendeskAPI(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public GetZendeskTicketResponse getTicketsWithCursorPagination(String subdomain, String authHeader, String url) {
        try {

            url = url.replaceFirst("anySubdomain", subdomain);
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Authorization",  authHeader);

            HttpResponse httpResponse = httpClient.execute(httpGet);

            if (HttpStatus.SC_UNAUTHORIZED == httpResponse.getStatusLine().getStatusCode()) {
                throw new UnauthorizedException("Unauthorized");
            }

            HttpEntity responseEntity = httpResponse.getEntity();
            String result = EntityUtils.toString(responseEntity);
            ObjectMapper objectMapper = new ObjectMapper();
            GetZendeskTicketResponse response = objectMapper.readValue(result, GetZendeskTicketResponse.class);
            return response;

        } catch (UnauthorizedException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
}
