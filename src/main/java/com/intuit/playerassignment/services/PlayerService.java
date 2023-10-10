package com.intuit.playerassignment.services;

import com.intuit.playerassignment.models.Player;
import com.intuit.playerassignment.utils.PlayersLoader;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerService {
    PlayersLoader playersLoader;
    public static final String FAILED_LOADING_DATA_FROM_CSV_FILE = "Failed loading data from CSV file.";

    public PlayerService() {
        playersLoader = PlayersLoader.getInstance();
    }

    public List<Player> getPlayers() {
        if (playersLoader.isDataLoadingFailed()) {
            throw new RuntimeException(FAILED_LOADING_DATA_FROM_CSV_FILE);
        }
        return playersLoader.getPlayers().values().stream().collect(Collectors.toList());
    }

    public Player getPlayerByPlayerId(String playerId) {
        if (playersLoader.isDataLoadingFailed()) {
            throw new RuntimeException(FAILED_LOADING_DATA_FROM_CSV_FILE);
        }
        return playersLoader.getPlayers().get(playerId);
    }

    public void reloadData() {
        playersLoader.loadPlayers();
        if (playersLoader.isDataLoadingFailed()) {
            throw new RuntimeException(FAILED_LOADING_DATA_FROM_CSV_FILE);
        }
    }
}