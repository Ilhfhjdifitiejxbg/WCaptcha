package wtf.n1zamu.utils;

import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import wtf.n1zamu.WCaptcha;

import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class VerifyUtil {
    private static FileConfiguration config = WCaptcha.getInstance().getConfig();
    public static void verification(Player player) {
        int randomNumeric = ThreadLocalRandom.current().nextInt(66666);
        new AnvilGUI.Builder()
                .onClose(stateSnapshot -> {
                    if (!NCaptcha.verifiedPlayers.contains(player)) {
                        player.kickPlayer(ChatColor.translateAlternateColorCodes('&', config.getString("messages.kickMessage")));
                    } else {
                        NCaptcha.verifiedPlayers.remove(player);
                    }
                }).onClick((slot, stateSnapshot) -> {
                    if (slot == AnvilGUI.Slot.OUTPUT) {
                        ItemStack result = stateSnapshot.getOutputItem();
                        ItemMeta resultMeta = result.getItemMeta();
                        if (resultMeta != null
                                && resultMeta.hasDisplayName()
                                && resultMeta.getDisplayName().equals(String.valueOf(randomNumeric))) {
                            successfullyCaptcha(player);
                            return Collections.singletonList(AnvilGUI.ResponseAction.close());
                        } else {
                            if (NCaptcha.verifiedPlayers.contains(player)) {
                                NCaptcha.verifiedPlayers.remove(player);
                            }
                            stateSnapshot.getPlayer().kickPlayer(ChatColor.translateAlternateColorCodes('&', config.getString("messages.kickMessage")));
                        }
                    }
                    return Collections.singletonList(AnvilGUI.ResponseAction.replaceInputText(config.getString("anvilName").replace("%number%",SuperscriptNumbers.convertNumber(randomNumeric))));
                })
                .title(ChatColor.translateAlternateColorCodes('&', config.getString("anvilName").replace("%number%",SuperscriptNumbers.convertNumber(randomNumeric))))
                .plugin(NCaptcha.getInstance())
                .itemLeft(new ItemStack(Material.PAPER))
                .open(player);
    }

    private static void successfullyCaptcha(Player player) {
        NCaptcha.verifiedPlayers.add(player);
        NCaptcha.handledPlayers.get(player).reset();
        if (config.getBoolean("notification.message")) {
            player.sendMessage(WCaptcha.logotype + ChatColor.translateAlternateColorCodes('&', config.getString("messages.successfullyMessage")));
        }
        if (config.getBoolean("notification.title")) {
            player.sendTitle(ChatColor.BLUE + "WCaptcha", ChatColor.translateAlternateColorCodes('&', config.getString("messages.successfullyMessage")));
        }
        if (config.getBoolean("notification.message")) {
            player.playSound(player.getLocation(), Sound.valueOf(config.getString("notification.soundType")), 1, 1);
        }
    }
}
