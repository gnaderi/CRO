package com.gnaderi.interview.cro.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CompanyRegOfficeControllerTest {
    TestRestTemplate restTemplate = new TestRestTemplate(TestRestTemplate.HttpClientOption.SSL);


    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void searchCompanies() {
    }

    @Test
    @Ignore
    public void registerCompany() throws Exception {

        String createRequestBody = "{\n" +
                "            \"Name\": \"Facebook.ie\",\n" +
                "            \"Address\": \"Hanover Reach 5/7 Hanover Quay\",\n" +
                "            \"City\": \"dublin\",\n" +
                "            \"Country\": \"ireland\",\n" +
                "            \"Email\": \"info@facebook.ie\",\n" +
                "            \"PhoneNumber\": \"0174320\",\n" +
                "            \"Stakeholders\": [1,2]\n" +
                "         }";
        HttpEntity<String> request = new HttpEntity<>(createRequestBody, getHttpHeaders());
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/companies"),
                HttpMethod.POST, request, String.class);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);

    }


    @Test
    public void registerBeneficialOwner() {
    }

    @Test
    public void updateCompany() {
    }

    @Test
    public void getCompanyById() {
    }

    private String createURLWithPort(String uri) {
        return "https://localhost:8443/cro" + uri;
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Cache-Control", "no-cache");
        return headers;
    }
}