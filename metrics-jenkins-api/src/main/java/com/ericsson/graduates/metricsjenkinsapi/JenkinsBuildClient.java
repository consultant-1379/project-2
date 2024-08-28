package com.ericsson.graduates.metricsjenkinsapi;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class JenkinsBuildClient {
    private JenkinsServer jenkins;

    public JenkinsBuildClient(String url) throws URISyntaxException {
        jenkins = new JenkinsServer(new URI(url));
    }

    public Map<String, Job> getJobs() throws IOException {
        return jenkins.getJobs();
    }

}
