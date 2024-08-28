package com.ericsson.graduates.metricsjenkinsapi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;


class MetricsJenkinsApiApplicationTest {

    @Test
    void applicationContextTest() {
        assertAll(()-> MetricsJenkinsApiApplication.main(new String[]{}));
    }
}
