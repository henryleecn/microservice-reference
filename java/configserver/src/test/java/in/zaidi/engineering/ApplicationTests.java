package in.zaidi.engineering;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ConfigServerApplication.class, value= "local.server.port=8888")
@WebAppConfiguration
public class ApplicationTests {

	@Value("${local.server.port}")
	private int port = 0;

	@Test
	public void test() {
		assertTrue(true);
	}

	
	/*@Test
	public void configurationAvailable() {
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = new TestRestTemplate().getForEntity(
				"http://localhost:" + port + "/admin/health", Map.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());
	}

	@Test
	public void envPostAvailable() {
		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = new TestRestTemplate().postForEntity(
				"http://localhost:" + port + "/admin/env", form, Map.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());
	}*/

}
