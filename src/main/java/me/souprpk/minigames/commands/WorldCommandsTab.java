package me.souprpk.minigames.commands;

import me.souprpk.gameapi.enums.GameType;
import me.souprpk.minigames.Minigames;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorldCommandsTab implements TabCompleter {

    private Minigames main;
    List<String> arguments = new ArrayList<String>();

    public WorldCommandsTab(Minigames main){
        this.main = main;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        // load
        // unload
        if (arguments.isEmpty() && sender.isOp()){
            arguments.add("load");
            arguments.add("unload");
            arguments.add("spawn");
            arguments.add("lobby");
            arguments.add("hub");
            arguments.add("create");
        }

        List<String> result = new ArrayList<String>();
        if (args.length == 1 && sender.isOp()) {
            for (String a : arguments) {
                if (a.toLowerCase().startsWith(args[0].toLowerCase()))
                    result.add(a);
            }
            return result;
        }
        else if (args.length == 2 && sender.isOp()){
            if(args[0].equalsIgnoreCase("spawn") || args[0].equalsIgnoreCase("lobby") || args[0].equalsIgnoreCase("hub")){
                result.add("set");
                return result;
            }
            else if(args[0].equalsIgnoreCase("load")){
                File worldsFolder = new File(main.getDataFolder() + "/arenas");
                String[] files = worldsFolder.list();

                if(files != null)
                    for (String file : files){
                        //Bukkit.getConsoleSender().sendMessage(file);
                        File folder = new File(worldsFolder, "/" + file);
                        String[] minigamesFolder = folder.list();
                        if(minigamesFolder != null)
                            result.addAll(Arrays.asList(minigamesFolder));
                    }
                return result;
            }
        }

        if(args.length == 3){

        }
        return null;
    }
}
