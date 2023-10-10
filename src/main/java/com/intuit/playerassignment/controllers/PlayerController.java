package com.intuit.playerassignment.controllers;

import com.intuit.playerassignment.annotations.ProjectAuthentication;
import com.intuit.playerassignment.models.Player;
import com.intuit.playerassignment.services.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/api")
@ProjectAuthentication
public class PlayerController {
    public static final String NO_PLAYER_FOUND_FOR_PLAYER_ID = "No player found for playerId: ";
    public static final String DATA_RELOADED_SUCCESSFULLY = "Data reloaded successfully.";
    PlayerService playerService = new PlayerService();

    @RequestMapping(method = RequestMethod.GET, value = "/players")
    public ResponseEntity getPlayers() {
        List<Player> players;

        try {
            players = playerService.getPlayers();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (players.isEmpty()) {
            return ResponseEntity.noContent().header("Content-Length", "0").build();
        }
        return ResponseEntity.ok(players);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/players/{playerID}")
    public ResponseEntity getPlayerByPlayerId(@PathVariable String playerID) {
        Player player;

        try {
            player = playerService.getPlayerByPlayerId(playerID);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (player == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NO_PLAYER_FOUND_FOR_PLAYER_ID + playerID);
        }
        return ResponseEntity.ok(player);
    }

//    Additional EP for reloading data from the CSV file.
    @RequestMapping(method = RequestMethod.GET, value = "/players/reload")
    public ResponseEntity<?> reloadData() {
        try {
            playerService.reloadData();
            return ResponseEntity.status(HttpStatus.OK).body(DATA_RELOADED_SUCCESSFULLY);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
