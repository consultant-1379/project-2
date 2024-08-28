package com.ericsson.graduates.metricsjenkinsapi;

import com.offbytwo.jenkins.model.Job;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/jobs")
public class SonarQubeRestController {

    @Autowired
    private Map<String, Job> jobs;

    @GetMapping(value="/sonarqube", produces={"application/json", "application/xml"})
    public ResponseEntity<Collection<String>> getAllSonarQubeJobs() {
        Collection<String> jenkinsJobs = jobs.keySet();
        Collection<String> sonarQubeJobs = jenkinsJobs.stream().filter(job -> job.contains("SonarQube")).collect(Collectors.toList());
        return ResponseEntity.ok().body(sonarQubeJobs);
    }

    @GetMapping(value="/sonarqube/{jobName}", produces={"application/json", "application/xml"})
    public ResponseEntity<Map<String, String>> getSonarQubeMetricsFromJob(@PathVariable String jobName) throws IOException, JSONException {
        Collection<String> sonarQubeJobs = jobs.keySet().stream().filter(job -> job.contains("SonarQube")).collect(Collectors.toList());
        if(!sonarQubeJobs.contains(jobName)) {
            return ResponseEntity.notFound().build();
        } else {
            Map<String, String> metrics = new HashMap<>();
            URL url = new URL(parseLogsForUrl(jobName));
            String sonarUrl = url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() + "/sonar";
            String sonarEndPoint = url.getPath().split("/")[4];
            SonarQubeClient sonarQubeClient = new SonarQubeClient(sonarUrl, sonarEndPoint);
            metrics.put("Critical violations", sonarQubeClient.getMetric("critical_violations"));
            metrics.put("Major violations", sonarQubeClient.getMetric("violations"));
            metrics.put("Code coverage:", sonarQubeClient.getMetric("coverage"));
            metrics.put("Bugs:", sonarQubeClient.getMetric("bugs"));
            metrics.put("Code smells:", sonarQubeClient.getMetric("code_smells"));
            metrics.put("Maintainability rating", sonarQubeClient.convertMetricToGrade(sonarQubeClient.getMetric("sqale_rating")));
            metrics.put("Security rating", sonarQubeClient.convertMetricToGrade(sonarQubeClient.getMetric("security_rating")));

            return ResponseEntity.ok().body(metrics);
        }
    }

    private String parseLogsForUrl(String jobName) throws IOException {
        List<String> buildOutput = Arrays.asList(jobs.get(jobName).details().getLastCompletedBuild().details().getConsoleOutputText().split("\n"));
        String urlLine = buildOutput.stream().filter(line -> line.contains("ANALYSIS SUCCESSFUL, you can browse")).collect(Collectors.joining());
        String[] urlLineSplit = urlLine.split(" ");
        return urlLineSplit[urlLineSplit.length-1];
    }

}
