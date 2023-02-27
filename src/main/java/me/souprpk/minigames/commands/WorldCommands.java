package me.souprpk.minigames.commands;

import me.souprpk.gameapi.utils.FileUtils;
import me.souprpk.minigames.utils.EmptyChunkGenerator;
import me.souprpk.minigames.utils.WorldUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class WorldCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player))
            return false;

        Player player = (Player) sender;
        if(label.equalsIgnoreCase("game")){
            if(args.length >= 1){
                if(args[0].equalsIgnoreCase("load")){
                    if(args[1] != null){
                        try {
                            FileUtils.copy(WorldUtils.findWorldFile(args[1]), Bukkit.getWorldContainer());
                            Bukkit.createWorld(new WorldCreator(args[1]));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                }else if(args[0].equalsIgnoreCase("create")){
                    if(args[1] != null){

                        WorldCreator creator = new WorldCreator(args[1]);
                        creator.generator(new EmptyChunkGenerator());
                        World world = Bukkit.createWorld(creator);
                        WorldUtils.createBedrockPlatform(world.getChunkAt(0, 0));
                    }
                }

            }
        }
        return false;
    }
}
