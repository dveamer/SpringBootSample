package io.dveamer.sample.player;

import org.springframework.stereotype.Service;

@Service
public class PlayerServImpl implements PlayerServ {

    private final PlayerRepo playerRepo;

    public PlayerServImpl(PlayerRepo playerRepo) {
        this.playerRepo = playerRepo;
    }

    @Override
    public Player register(Player player) {
        long id = playerRepo.newSeq();
        player.setId(id);
        playerRepo.insert(player, 8);
        return player;
    }

    @Override
    public Player load(long id) {
        return playerRepo.findById(id);
    }

    public Player load2(long id) {
        Player player = new Player();
        player.setId(id);

        return playerRepo.findByPlayerId(player, "hello");
    }

}
