package com.intuit.playerassignment;

import com.intuit.playerassignment.controllers.PlayerController;
import com.intuit.playerassignment.models.Player;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;

@SpringBootTest
public class PlayerAssignmentApplicationTests {
    @Test
    public void isMatchByNameTest() {
        PlayerController playerController = new PlayerController();
        Player p = (Player) playerController.getPlayerByPlayerId("aardsda01").getBody();
        Assert.assertEquals(p.getNameFirst(), "David");
    }

    @Test
    public void missingPlayerTest() {
        PlayerController playerController = new PlayerController();
        HttpStatus statusCode = playerController.getPlayerByPlayerId("missingPlayer").getStatusCode();
        Assert.assertEquals(statusCode, HttpStatus.NOT_FOUND);
    }

    @Test
    public void igetAllPlayerseTest() {
        PlayerController playerController = new PlayerController();
        List<Player> players = (List<Player>) playerController.getPlayers().getBody();
        Assert.assertEquals(players.size(), 19370);
    }
}
