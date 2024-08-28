package com.ericsson.graduates.metricsjenkinsapi;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JenkinsBuildClientTest {
    JenkinsServer jenkinsServer;
    String jenkinsBuildURL = "https://fem2s11-eiffel112.eiffel.gic.ericsson.se:8443/jenkins/";
    String jenkinsJobName = "eric-son-frequency-layer-manager";

    @BeforeEach
    public void setUp() throws URISyntaxException {
        jenkinsServer = new JenkinsServer(new URI(jenkinsBuildURL));
    }

    @Test
    void getJobs_runningJenkinsServer_returnsJobs() throws IOException, URISyntaxException {
        Map<String, Job> jobs = jenkinsServer.getJobs();

        assertTrue(jobs.size() >= 1);
    }

}
