package us.compressions.twitchy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Twitchy extends JavaPlugin {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        this.getCommand("stream").setExecutor(new Stream(this));
        
        broadcast();

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new BukkitRunnable() {
            public void run() {
                refresh();
            }
        }, 20, 100);
    }

    private String channel;

    private URL url;
    private BufferedReader reader;

    private boolean online = false;

    public void refresh() {
        try {
            channel = this.getConfig().getString("channel");
            this.url = new URL("http://api.justin.tv/api/stream/list.json?jsonp=&channel=" + channel);
            this.reader = new BufferedReader(new InputStreamReader(url.openStream()));

            if (!(reader.readLine().equals("[]"))) {
                online = true;
            } else {
                online = false;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public URL getURL() {
        return this.url;
    }

    public boolean isOnline() {
        return this.online;
    }

    public void broadcast() {
        int minutes = this.getConfig().getInt("delayInMinutes");
        final String onlineMessage = this.getConfig().getString("onlineBroadcast");
        final String offlineMessage = this.getConfig().getString("offlineBroadcast");
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new BukkitRunnable() {
            public void run() {
                if (isOnline()) {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', onlineMessage));
                } else {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', offlineMessage));
                }
            }
        }, 60, minutes * 60 * 20);
    }

}
