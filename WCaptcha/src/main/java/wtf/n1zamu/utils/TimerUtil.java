package wtf.n1zamu.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import wtf.n1zamu.WCaptcha;

public class TimerUtil {
    FileConfiguration config = NCaptcha.getInstance().getConfig();
    private int timeLeft;
    private final int timeOut = config.getInt("time");
    private BossBar busyBar;
    private final Player player;
    private final JavaPlugin plugin;
    private int tickTask = -1;

    public TimerUtil(Player player, JavaPlugin plugin) {
        this.player = player;
        this.plugin = plugin;

        busyBar = Bukkit.createBossBar((ChatColor.BLUE + "WCaptcha"),
                BarColor.RED,
                BarStyle.SOLID);
        busyBar.addPlayer(player);
        busyBar.setVisible(true);

    }

    public void startTimer() {
        timeLeft = timeOut;
        if (tickTask != -1)
            plugin.getServer().getScheduler().cancelTask(tickTask);

        tickTask = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if (timeLeft == 0) {
                TimerUtil.this.endTimer();
                player.kickPlayer(ChatColor.translateAlternateColorCodes('&', config.getString("messages.kickMessage")));
                return;
            }

            busyBar.setProgress((double) timeLeft / timeOut);
            busyBar.setTitle(ChatColor.BLUE + "WCaptcha"
                    + ChatColor.translateAlternateColorCodes('&', config.getString("bossBarName"))
                    .replace("%timeLeft%", String.valueOf(timeLeft)));
            timeLeft--;
        }, 0, 20);

    }

    public void endTimer() {
        busyBar.setVisible(false);

        plugin.getServer().getScheduler().cancelTask(tickTask);
        tickTask = -1;
    }

    public void reset() {
        endTimer();
        timeLeft = 0;
    }
}

