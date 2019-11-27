package io.dveamer.sample.mybatisinterceptor;

import io.dveamer.sample.player.Player;
import io.dveamer.sample.player.PlayerRepo;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@MybatisTest
@TestPropertySource("classpath:application_test.properties")
public class MybatisInterceptorTest {

	@Autowired
	PlayerRepo playerRepo;

	@Test
	public void findById_success() {
		Player player = playerRepo.findById(1);

		assertThat(player.getId(), equalTo(1L));
	}

	@Test
	public void insertOneParam_success() {
		playerRepo.insert(Fixture.defaultPlayer());
		long creatorId = playerRepo.findCreatorOf(Fixture.defaultPlayer().getId());
		assertThat(creatorId, equalTo(0L));
	}

	@Test
	public void insertTwoParam_success() throws Exception {
		playerRepo.insertTwoParam(Fixture.defaultPlayer(), Fixture.defaultPlayer().getPlayerName());
		long creatorId = playerRepo.findCreatorOf(Fixture.defaultPlayer().getId());
		assertThat(creatorId, equalTo(0L));
	}


	@Test
	public void insertTwoPrimitive_success() {
		playerRepo.insertTwoPrimitive(Fixture.defaultPlayer().getId(), Fixture.defaultPlayer().getPlayerName());
		long creatorId = playerRepo.findCreatorOf(Fixture.defaultPlayer().getId());
		assertThat(creatorId, equalTo(0L));
	}
}
