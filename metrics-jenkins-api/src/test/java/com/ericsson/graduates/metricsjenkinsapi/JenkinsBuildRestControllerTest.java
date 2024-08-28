package com.ericsson.graduates.metricsjenkinsapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class JenkinsBuildRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

    @Test
    void getAllJobs_restApiWithEricssonJobs_returnsJobs() throws Exception {
        MvcResult result = mockMvc.perform(get("/jobs")).andDo(print()).andExpect(status().isOk()).andReturn();
    }

    @Test
    void getBuildsFromJob_jobWithBuilds_returnBuildsAndResults() throws Exception {
        MvcResult getId = mockMvc.perform(get("/jobs")).andDo(print()).andExpect(status().isOk()).andReturn();
        StringBuilder jobId = new StringBuilder(getId.getResponse().getContentAsString().split(",")[1]);
        jobId.deleteCharAt(0);
        jobId.deleteCharAt(jobId.length()-1);
        String resultString = jobId.toString();


        MvcResult result = mockMvc.perform(get("/jobs/" + resultString)).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue((result.getResponse().getContentAsString().contains("SUCCESS") || result.getResponse().getContentAsString().contains("FAIL")));
    }

    @Test
    void getBuildsFromJob_jobIdDoesNotExist_returnNotOk() throws Exception {
        MvcResult result = mockMvc.perform(get("/jobs/fghjhsdgfjhgewfgjgwjhfg")).andDo(print()).andExpect(status().isNotFound()).andReturn();
    }
}
