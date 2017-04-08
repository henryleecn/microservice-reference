package in.zaidi.engineering.auctions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BidResourceTest.class, value= "local.server.port=8888")
@WebAppConfiguration
public class BidResourceTest {

	@Test
	public void test() {
		//fail("Not yet implemented");
	}

}
