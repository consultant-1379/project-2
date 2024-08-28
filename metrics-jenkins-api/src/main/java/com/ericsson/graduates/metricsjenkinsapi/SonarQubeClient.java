package com.ericsson.graduates.metricsjenkinsapi;

import org.json.JSONException;
import org.sonar.wsclient.SonarClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SonarQubeClient {
    SonarClient sonarClient;
    String sonarQubeEndPoint;

    public SonarQubeClient(String url, String sonarClientEndPoint) {
        this.sonarClient = SonarClient.create(url);
        this.sonarQubeEndPoint = sonarClientEndPoint;
    }

    public String getMetric(String metric) throws JSONException {
        String metricResult = null;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("component", sonarQubeEndPoint);
        paramMap.put("metricKeys", metric);

        String result = sonarClient.get("/api/measures/component", paramMap);
        JSONObject obj = new JSONObject(result);
        JSONArray violations = obj.getJSONObject("component").getJSONArray("measures");


        for(int i = 0; i < violations.length(); i++) {
            metricResult = violations.getJSONObject(i).getString("value");
        }

        return metricResult;
    }

    public String convertMetricToGrade(String metric) {
        Map<Double, String> gradeMap = new HashMap<>();
        gradeMap.put(1.0, "A");
        gradeMap.put(2.0, "B");
        gradeMap.put(3.0, "C");
        gradeMap.put(4.0, "D");
        gradeMap.put(5.0, "E");

        return gradeMap.get(Double.parseDouble(metric));
    }
}
