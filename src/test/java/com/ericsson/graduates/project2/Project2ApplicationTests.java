package com.ericsson.graduates.project2;

import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Project2ApplicationTests {
    @Test
    void contextLoads() {
        assert true;
        //assertAll(() -> Project2Application.main(new String[]{})); TODO
    }

}
