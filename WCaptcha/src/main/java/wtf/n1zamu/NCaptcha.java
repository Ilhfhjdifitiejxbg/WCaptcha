package wtf.n1zamu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import wtf.n1zamu.listeners.*;

import wtf.n1zamu.utils.TimerUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public final class WCaptcha extends JavaPlugin implements Listener {
    public static HashMap<Player, TimerUtil> handledPlayers = new HashMap<>();
    public static List<Player> verifiedPlayers = new ArrayList<>();
    public static String logotype = ChatColor.BLUE + "WCaptcha" + ChatColor.GRAY + " » ";

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLeaveListener(), this);
        getLogger().info("=====WCaptcha was successfully loaded=====");
        getLogger().info("=====Author: Happy X Katara=====");
    }

    @Override
    public void onDisable() {
        getLogger().info("=====WCaptcha was successfully unloaded=====");
        getLogger().info("=====Author: Happy X Katara=====");
    }
    public static WCaptcha getInstance() {
        return WCaptcha.getPlugin(WCaptcha.class);
    }
}
