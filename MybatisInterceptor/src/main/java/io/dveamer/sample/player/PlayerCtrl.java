package io.dveamer.sample.player;

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


    @GetMapping("/players2/{id}")
    public Player load2(@PathVariable("id") long id){
        return playerServ.load2(id);
    }

    @PostMapping("/players")
    public Player save(@RequestBody Player player){
        return playerServ.register(player);
    }


    @PostMapping("/players1")
    public Player save1(@RequestBody Player player){
        return playerServ.register1(player);
    }

    @PostMapping("/players2")
    public Player save2(@RequestBody Player player){
        return playerServ.register2(player);
    }


}
