package us.compressions.twitchy;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Stream implements CommandExecutor {
    
    public Twitchy plugin;
    public Stream(Twitchy instance) {
        plugin = instance;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to execute this command!");
        } else {
            if(label.equalsIgnoreCase("stream")) {
                Player player = (Player) sender;
                String channel = plugin.getConfig().getString("channel");
                if(plugin.isOnline()) {
                    String online = plugin.getConfig().getString("onlineStatus");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', online));
                    player.sendMessage(ChatColor.GREEN + "Channel Link: " + ChatColor.DARK_AQUA + "http://twitch.tv/" + channel);
                } else {
                    String offline = plugin.getConfig().getString("offlineStatus");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', offline));
                    player.sendMessage(ChatColor.GREEN + "Channel Link: " + ChatColor.DARK_AQUA + "http://twitch.tv/" + channel);
                }
            }
        }
        return true;
    }

}
