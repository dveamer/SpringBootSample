package io.dveamer.sample.Player;

import org.springframework.web.bind.annotation.*;


@RestController
public class PlayerCtrl {

    private final PlayerServ playerServ;

    public PlayerCtrl(PlayerServ playerServ) {
        this.playerServ = playerServ;
    }

    @GetMapping("/players/{id}")
    public Player load(@PathVariable("id") long id){
        return playerServ.load(id);
    }

    @PostMapping("/players")
    public Player save(@RequestBody Player player){
        return playerServ.register(player);
    }

}
