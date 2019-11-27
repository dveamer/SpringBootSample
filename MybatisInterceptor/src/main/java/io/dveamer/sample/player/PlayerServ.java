package io.dveamer.sample.player;

public interface PlayerServ {

    Player register(Player player);

    public Player register1(Player player) ;

    public Player register2(Player player) ;

    Player load(long id);

    Player load2(long id);

}
