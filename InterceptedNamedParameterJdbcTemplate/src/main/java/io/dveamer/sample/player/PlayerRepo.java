package io.dveamer.sample.player;

public interface PlayerRepo {

    long newSeq();

    Player findById(long id);

    void insert(Player player);
}
