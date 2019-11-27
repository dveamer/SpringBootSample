package io.dveamer.sample.mybatisinterceptor;

import io.dveamer.sample.player.Player;

public class Fixture {

    static Player defaultPlayer(){

        Player player = new Player();
        player.setId(3L);
        player.setPlayerName("player03");

        return player;
    }
}
