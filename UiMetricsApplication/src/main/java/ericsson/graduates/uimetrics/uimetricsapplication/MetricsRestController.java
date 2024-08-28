package ericsson.graduates.uimetrics.uimetricsapplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class MetricsRestController {
    @Autowired
    long[] results;
    @GetMapping(value = "/basicMetrics")
    public ResponseEntity<long[]> getBasicMetricData()
    {
        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/UnitIntegrationMetrics")
    public ResponseEntity<String> getUIMetricData()
    {
        return ResponseEntity.ok(new MetricFormatter(results).toString());
    }

    @PutMapping(value = "/add/", produces = {"application/json", "application/xml"})
    public ResponseEntity<Boolean> putUIMetricData(@RequestParam(value = "UnitPass", required = false, defaultValue = "0") long unitP
            , @RequestParam(value = "UnitFail", required = false, defaultValue = "0") long unitF
            , @RequestParam(value = "IntegratePass", required = false, defaultValue = "0") long integrationP
            , @RequestParam(value = "IntegrateFail", required = false, defaultValue = "0") long integrationF) {
        if (unitP < 0 || unitF < 0 || integrationP < 0 || integrationF < 0)    //ensures no negative values added to the log
            return ResponseEntity.unprocessableEntity().build();
        results[0] += unitP;
        results[1] += unitF;
        results[2] += integrationP;
        results[3] += integrationF;
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/reset")
    public ResponseEntity<long[]> resetMetricData() {
        results = new long[]{0, 0, 0, 0};
        return ResponseEntity.ok().body(results);
    }
}