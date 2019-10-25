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
        playerRepo.insert(player);
        return player;
    }

    @Override
    public Player load(long id) {
        return playerRepo.findById(id);
    }
}
