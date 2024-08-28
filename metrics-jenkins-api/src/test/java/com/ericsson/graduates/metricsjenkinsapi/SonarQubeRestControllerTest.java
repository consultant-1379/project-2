package com.ericsson.graduates.metricsjenkinsapi;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sonar.wsclient.SonarClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SonarQubeRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    SonarClient sonarClient;
    String sonarQubeUrl = "http://atvts3635.athtem.eei.ericsson.se:9000";
    String sonarQubeEndPoint = "com.ericsson.oss.services.sonom:eric-policy-engine-ax";

    @BeforeEach
    public void setUp() {
        sonarClient = SonarClient.create(sonarQubeUrl);
    }

    @Test
    void getAllSonarQubeJobs_sonarQubeJobsOnEricssonServers_returnSonarQubeJobs() throws Exception {
        MvcResult result = mockMvc.perform(get("/jobs/sonarqube")).andDo(print()).andExpect(status().isOk()).andReturn();
    }

    @Test
    void getSonarQubeMetricsFromJob_specifySonarQubeJob_returnMetrics() throws Exception {
        MvcResult getId = mockMvc.perform(get("/jobs/sonarqube")).andDo(print()).andExpect(status().isOk()).andReturn();
        StringBuilder jobId = new StringBuilder(getId.getResponse().getContentAsString().split(",")[4]);
        jobId.deleteCharAt(0);
        jobId.deleteCharAt(jobId.length()-1);
        String resultString = jobId.toString();

        MvcResult result = mockMvc.perform(get("/jobs/sonarqube/" + resultString)).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Code coverage"));
    }

    @Test
    void getSonarQubeMetricsFromJob_specifyIncorrectSonarQubeJob_returnNotFound() throws Exception {
        MvcResult result = mockMvc.perform(get("/jobs/sonarqube/dhjfiheriuhfehirheihfiuehfgeiuh")).andDo(print()).andExpect(status().isNotFound()).andReturn();
    }
}
