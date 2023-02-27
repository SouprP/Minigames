package me.souprpk.minigames;

import me.souprpk.gameapi.GameAPI;
import me.souprpk.gameapi.enums.GameType;
import me.souprpk.minigames.commands.WorldCommands;
import me.souprpk.minigames.commands.WorldCommandsTab;
import me.souprpk.minigames.config.ArenaDataFile;
import me.souprpk.minigames.minigames.survival_games.SurvivalGames;
import me.souprpk.minigames.utils.WorldUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;

public final class Minigames extends JavaPlugin {

    GameAPI gameAPI;
    public ArenaDataFile arenaDataFile;
    public WorldUtils worldUtils;
    @Override
    public void onEnable() {
        // Plugin startup logic
        this.arenaDataFile = new ArenaDataFile(this);
        this.gameAPI = new GameAPI(this, new File(getDataFolder(), "/arenas"));

        // Save default configs / create config files
        this.saveDefaultConfig();
        this.arenaDataFile.saveDefaultConfig();

        // Creating minigames worlds folder
        for(GameType type : GameType.values()){
            File gameMapsFolder = new File(getDataFolder(), "/arenas/" + type.toString().toLowerCase());
            if(!gameMapsFolder.exists())
                gameMapsFolder.mkdirs();
        }

        this.worldUtils = new WorldUtils(this);
        //Arena arena = new Arena("Spleef");
        //Game spleef = new Game("Spleef", arena, GameType.SURVIVAL_GAMES, this);

        // Commands
        this.getCommand("game").setExecutor(new WorldCommands());
        this.getCommand("game").setTabCompleter(new WorldCommandsTab(this));
        getWorlds();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public GameAPI getGameAPI() {
        return gameAPI;
    }

    private void loadGames(){
        if(!this.getConfig().getBoolean("minigames.debug-mode")){
            SurvivalGames games = new SurvivalGames(this);
        }
    }

    private void getWorlds(){
        File worldsFolder = new File(getDataFolder() + "/arenas");
        String[] files = worldsFolder.list();

        if(files != null)
            for (String file : files){
                //Bukkit.getConsoleSender().sendMessage(file);
                File folder = new File(worldsFolder, "/" + file);
                String[] minigamesFolder = folder.list();
                if(minigamesFolder != null)
                    for(String s : minigamesFolder)
                        Bukkit.getConsoleSender().sendMessage(s);
            }
    }
}
