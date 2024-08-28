package ericsson.graduates.uimetrics.uimetricsapplication;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class ApplicationContextLoadsTest {
    @Test
     void applicationContextTest() {
      assertAll(()->UiMetricsApplication.main(new String[]{}));
    }
}
