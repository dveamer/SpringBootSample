package io.dveamer.sample.player;

public interface PlayerServ {

    Player register(Player player);

    Player load(long id);

}
