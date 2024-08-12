package wtf.n1zamu.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import wtf.n1zamu.WCaptcha;
import wtf.n1zamu.utils.TimerUtil;
import wtf.n1zamu.utils.VerifyUtil;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        VerifyUtil.verification(e.getPlayer());
        NCaptcha.handledPlayers.put(e.getPlayer(), new TimerUtil(e.getPlayer(), WCaptcha.getInstance()));
        NCaptcha.handledPlayers.get(e.getPlayer()).startTimer();
    }
}
