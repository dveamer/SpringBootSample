package io.dveamer.sample.Player;

public interface PlayerRepo {

    long newSeq();

    Player findById(long id);

    void insert(Player player);
}
