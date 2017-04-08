package in.zaidi.engineering.reference;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import in.zaidi.auctions.AuctionappApplication;
import in.zaidi.auctions.repo.UserResource;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = AuctionappApplication.class)
public class AuctionappApplicationTests {

	@Autowired
	UserResource userResource;

	@Test
	public void contextLoads() {

	}

}
