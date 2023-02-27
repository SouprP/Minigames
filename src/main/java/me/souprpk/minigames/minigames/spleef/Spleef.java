package me.souprpk.minigames.minigames.spleef;

import me.souprpk.gameapi.api.core.Arena;
import me.souprpk.gameapi.api.core.ArenaSettings;
import me.souprpk.gameapi.api.core.Game;
import me.souprpk.gameapi.api.core.GameSettings;
import me.souprpk.gameapi.enums.GameType;
import me.souprpk.minigames.Minigames;
import org.bukkit.Location;

import java.io.File;

public class Spleef {

    private final Minigames main;
    public Spleef(Minigames main){
        this.main = main;
        createGame();
    }
    public void createGameSettings(GameSettings settings){
        settings.shouldUseTeams(false);
        settings.setMaximumPlayers(40);
        settings.setMinimumPlayers(4);

        settings.shouldLeavePlayerOnDisconnect(true);
        settings.setTeleportPlayersOnGameStart(false);
        settings.setAutomaticCountdown(true);
        settings.setCountdownTime(20);
        settings.shouldLeavePlayerOnDisconnect(true);
    }

    public void createArenaSettings(ArenaSettings settings){
        settings.setCanBuild(false);
        settings.setCanDestroy(false);
        settings.setCanPvP(false);
        settings.setAllowPlayerSleep(false);
        settings.setAllowPlayerInvincibility(false);
        settings.setAllowDurabilityChange(false);
        settings.setAllowFoodLevelChange(false);
        settings.setAllowMobSpawn(false);
    }

    private void createGame(){
        File worldsFolder = new File(main.getDataFolder() + "/arenas/" + GameType.SPLEEF.toString().toLowerCase());
        String[] files = worldsFolder.list();

        if(files != null)
            for (String file : files){
                Arena arena = new Arena(file);
                createArenaSettings(arena.getArenaSettings());
                arena.setLobbySpawn(main.worldUtils.getLobbyLocation(arena.getName()));

                Game game = new Game(file.toUpperCase(), arena, GameType.SPLEEF, main);
                createGameSettings(game.getGameSettings());

            }
    }
}
