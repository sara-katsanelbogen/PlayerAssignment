package com.intuit.playerassignment.utils;

import com.intuit.playerassignment.models.Player;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayersLoader {
    private final String CSV_FILE_PATH = "./src/main/resources/player.csv";
    private static PlayersLoader instance;
    private PlayerMapper playerMapper;
    private Map<String, Player> playerID2Player;
    private boolean isDataLoadingFailed;

    private PlayersLoader() {
        loadPlayers();
    }

    public static PlayersLoader getInstance() {
        if (instance == null) {
            instance = new PlayersLoader();
        }
        return instance;
    }

    private String[] getHeaders(String path) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return br.readLine().split(",");
        }
    }

    public void loadPlayers() {
        playerID2Player = new HashMap<>();
        try (CSVReader reader = new CSVReader(new FileReader(CSV_FILE_PATH))) {
            playerMapper = new PlayerMapper(getHeaders(CSV_FILE_PATH));
            List<String[]> rows = reader.readAll();
            for (String[] playerData: rows.subList(1, rows.size())) {
                if (!addPlayer(playerData)) {
                    return;
                }
            }
            isDataLoadingFailed = false;
        } catch (IOException e) {
            isDataLoadingFailed = true;
        }
    }

    private boolean addPlayer(String[] playerData) {
        try {
            Player player = playerMapper.getPlayerFromMapper(playerData);
            playerID2Player.put(player.getPlayerID(), player);
        } catch (IOException e) {
            isDataLoadingFailed = true;
            return false;
        }
        return true;
    }

    public Map<String, Player> getPlayers() {
        return playerID2Player;
    }

    public boolean isDataLoadingFailed() { return isDataLoadingFailed; }
}
