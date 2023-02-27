package me.souprpk.minigames.minigames.survival_games;

import me.souprpk.gameapi.api.core.Arena;
import me.souprpk.gameapi.api.core.ArenaSettings;
import me.souprpk.gameapi.api.core.Game;
import me.souprpk.gameapi.api.core.GameSettings;
import me.souprpk.gameapi.api.managers.GameManager;
import me.souprpk.gameapi.enums.GameType;
import me.souprpk.minigames.Minigames;
import org.bukkit.Location;

import java.io.File;

public class SurvivalGames {

    private final Minigames main;
    public SurvivalGames(Minigames main){
        this.main = main;
        createGame();
    }
    public void createGameSettings(GameSettings settings){
        settings.shouldUseTeams(false);
        settings.setMaximumPlayers(32);
        settings.setMinimumPlayers(12);

        settings.shouldLeavePlayerOnDisconnect(true);
        settings.setTeleportPlayersOnGameStart(true);
        settings.setAutomaticCountdown(true);
        settings.setCountdownTime(20);
        settings.shouldLeavePlayerOnDisconnect(true);
        settings.shouldSpawnBodyOnDeath(true);
    }

    public void createArenaSettings(ArenaSettings settings){
        settings.setCanBuild(false);
        settings.setCanDestroy(false);
        settings.setCanPvP(false);
        settings.setAllowPlayerSleep(false);
        settings.setAllowPlayerInvincibility(true);
        settings.setAllowDurabilityChange(true);
        settings.setAllowFoodLevelChange(false);
        settings.setAllowMobSpawn(false);
    }

    private void createGame(){
        File worldsFolder = new File(main.getDataFolder() + "/arenas/" + GameType.SURVIVAL_GAMES.toString().toLowerCase());
        String[] files = worldsFolder.list();

        if(files != null)
            for (String file : files){
                Arena arena = new Arena(file);
                createArenaSettings(arena.getArenaSettings());
                arena.setLobbySpawn(main.worldUtils.getLobbyLocation(arena.getName()));

                Game game = new Game(file.toUpperCase(), arena, GameType.SURVIVAL_GAMES, main);
                GameManager.registerGame(game);
                createGameSettings(game.getGameSettings());

                for(Location loc : main.worldUtils.getSpawnLocations(arena.getName(), game.getGameSettings().getMaximumPlayers()))
                    game.addSpawn(loc);
            }
    }


}
