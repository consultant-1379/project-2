package com.ericsson.graduates.metricsjenkinsapi;

import com.offbytwo.jenkins.model.Job;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

@SpringBootApplication
public class MetricsJenkinsApiApplication {
	JenkinsBuildClient jenkinsBuildClient = new JenkinsBuildClient("https://fem2s11-eiffel112.eiffel.gic.ericsson.se:8443/jenkins/");

	public MetricsJenkinsApiApplication() throws URISyntaxException {
		// Necessary to start the JenkinsBuildClient
	}

	public static void main(String[] args) {
		SpringApplication.run(MetricsJenkinsApiApplication.class, args);
	}

	@Bean
	public Map<String, Job> jobs() throws IOException {
		return jenkinsBuildClient.getJobs();
	}
}
