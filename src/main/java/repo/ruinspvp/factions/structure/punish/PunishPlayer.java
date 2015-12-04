

package repo.ruinspvp.factions.structure.punish;

import org.bukkit.entity.Player;

public class PunishPlayer {

    private String player;
    private String reason;
    private Player admin;

    public PunishPlayer(String player, String reason, Player admin) {
        this.player = player;
        this.reason = reason;
        this.admin = admin;
    }

    public String getPlayerName() {
        return this.player;
    }

    public String getReason() {
        return this.reason;
    }

    public Player getAdmin() {
        return this.admin;
    }
}