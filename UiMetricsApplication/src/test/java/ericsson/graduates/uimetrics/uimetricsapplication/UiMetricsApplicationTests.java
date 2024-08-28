package ericsson.graduates.uimetrics.uimetricsapplication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UiMetricsApplicationTests {

	@Value("${local.server.port}")
	int port;
	@Autowired
	private TestRestTemplate restTemplate;
	public String defUrl="http://localhost:";


	@Test
	void testThatMetricsCanBeGotten() {
		ResponseEntity<String> responseEntity=restTemplate.getForEntity(defUrl + port+ "/UnitIntegrationMetrics", String.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	void testThatNewMetricsCanBePut(){
		restTemplate.put(defUrl+port+"/add/?UnitPass=10&UnitFail=23",null);
		ResponseEntity<long[]> responseEntity=restTemplate.getForEntity(defUrl + port + "/basicMetrics", long[].class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		long[] results=responseEntity.getBody();
		assert results != null;
		assertEquals(10,results[0]);
		assertEquals(23,results[1]);

	}
	@Test
	void testThatMetricsCanBeReset()
	{
		ResponseEntity<long[]> responseEntity=restTemplate.exchange(defUrl+port+"/reset",HttpMethod.DELETE,
				null,long[].class);
		assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
		long[] results=responseEntity.getBody();
		assert results != null;
		assertEquals(0,results[0]);
		assertEquals(0,results[1]);
		assertEquals(0,results[2]);
	}


}
