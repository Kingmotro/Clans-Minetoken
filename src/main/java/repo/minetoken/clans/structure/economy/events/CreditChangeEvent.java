package repo.minetoken.clans.structure.economy.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CreditChangeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private int amount;

    public CreditChangeEvent(Player player, int amount) {
        this.player = player;
        this.amount = amount;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getPlayer() {
        return this.player;
    }

    public String getPlayerName() {
        return this.player.getName();
    }

    public int getAmount() {
        return this.amount;
    }
}