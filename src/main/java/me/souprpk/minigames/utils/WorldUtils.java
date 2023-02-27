package me.souprpk.minigames.utils;

import me.souprpk.gameapi.GameAPI;
import me.souprpk.gameapi.enums.GameType;
import me.souprpk.minigames.Minigames;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

public class WorldUtils {

    Minigames main;
    public WorldUtils(Minigames main){
        this.main = main;
    }
    public Location getLobbyLocation(String world){
        return new Location(Bukkit.getWorld(main.arenaDataFile.getConfig().getString(world + ".lobby.world"))
                ,main.arenaDataFile.getConfig().getDouble(world + ".lobby.x")
                ,main.arenaDataFile.getConfig().getDouble(world + ".lobby.y")
                ,main.arenaDataFile.getConfig().getDouble(world + ".lobby.z"));
    }

    public Location[] getSpawnLocations(String world, int size){
        Location[] locs = new Location[size];
        AtomicInteger i = new AtomicInteger(0);
        main.arenaDataFile.getConfig().getConfigurationSection(world + ".spawns").getKeys(false).forEach(number ->{
            locs[i.get()] = new Location(
                    Bukkit.getWorld(main.arenaDataFile.getConfig().getString(world + ".spawns." + number + ".world")),
                    main.arenaDataFile.getConfig().getDouble(world + ".spawns." + number + ".x"),
                    main.arenaDataFile.getConfig().getDouble(world + ".spawns." + number + ".y"),
                    main.arenaDataFile.getConfig().getDouble(world + ".spawns." + number + ".z")
            );
            i.getAndIncrement();
        });

        return locs;
    }

    public static File findWorldFile(String worldName){
        for(GameType gameMode : GameType.values()){
            String gameFolder = gameMode.toString();
            File folder = new File(GameAPI.getGameWorldsFolder(), gameFolder);

            String[] worlds = folder.list();
            for(String s: worlds)
                if(worldName.equals(s)){
                    Bukkit.getConsoleSender().sendMessage("Found file: " + s);
                    return new File(GameAPI.getGameWorldsFolder(), gameFolder + "/" + s);
                }

        }

        return null;
    }

    public static void createBedrockPlatform(Chunk chunk){
        for(int x = 0; x < 15; x++)
            for(int z = 0; z < 15; z++)
                chunk.getBlock(x, 60, z).setType(Material.BEDROCK);
    }
}
