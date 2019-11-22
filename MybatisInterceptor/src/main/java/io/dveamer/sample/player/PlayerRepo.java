package io.dveamer.sample.player;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlayerRepo {

    long newSeq();

    Player findById(long id);

    Player findByPlayerId(Player player, String temp);


    void insert(Player player, int createdBy);
}
