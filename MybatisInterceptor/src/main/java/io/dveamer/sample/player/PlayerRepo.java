package io.dveamer.sample.player;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PlayerRepo {

    long newSeq();

    Player findById(long id);

    Player findByPlayerId(Player player);

    long findCreatorOf(long id);

    void insert(@Param("player") Player player);

    void insertTwoParam(Player player, String playerName);

    void insertTwoPrimitive(long id, String playerName);

}
