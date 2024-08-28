package com.ericsson.graduates.metricsjenkinsapi;

import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildResult;
import com.offbytwo.jenkins.model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/jobs")
public class JenkinsBuildRestController {

    @Autowired
    private Map<String, Job> jobs;

    @GetMapping(produces={"application/json", "application/xml"})
    public ResponseEntity<Collection<String>> getAllJobs() {
        Collection<String> jenkinsJobs = jobs.keySet();
        return ResponseEntity.ok().body(jenkinsJobs);
    }

    @GetMapping(value="/{jobName}", produces={"application/json", "application/xml"})
    public ResponseEntity<Map<Integer, BuildResult>> getBuildsFromJob(@PathVariable String jobName) throws IOException {
        if(!jobs.containsKey(jobName)) {
            return ResponseEntity.notFound().build();
        } else {
            Map<Integer, BuildResult> buildResultMap = new HashMap<>();
            List<Build> builds = jobs.get(jobName).details().getAllBuilds();

            for(Build build : builds) {
                buildResultMap.put(build.getNumber(), build.details().getResult());
            }

            return ResponseEntity.ok().body(buildResultMap);
        }
    }
}