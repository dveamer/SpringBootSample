package io.dveamer.sample;

import io.dveamer.sample.player.Player;
import io.dveamer.sample.player.PlayerRepo;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@MybatisTest
public class MybatisInterceptorApplicationTests {

	@Autowired
	PlayerRepo playerRepo;

	@Test
	public void contextLoads() {
		Player player = playerRepo.findById(1);

		assertThat(player.getId(), equalTo(1));
	}

}
