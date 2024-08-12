package wtf.n1zamu.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import wtf.n1zamu.NCaptcha;

public class PlayerLeaveListener implements Listener {
    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        if (NCaptcha.handledPlayers.containsKey(e.getPlayer())) {
            NCaptcha.handledPlayers.get(e.getPlayer()).reset();
            NCaptcha.handledPlayers.remove(e.getPlayer());
        }
    }
}
