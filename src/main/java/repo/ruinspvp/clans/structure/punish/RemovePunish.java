package repo.ruinspvp.clans.structure.punish;

import org.bukkit.entity.Player;

public class RemovePunish {

    private int id;
    private String reason;
    private Player admin;

    public RemovePunish(int id, String reason, Player admin) {
        this.id = id;
        this.reason = reason;
        this.admin = admin;
    }

    public int getID() {
        return this.id;
    }

    public String getReason() {
        return this.reason;
    }

    public Player getAdmin() {
        return this.admin;
    }
}