package repo.minetoken.clans.structure.clan.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import repo.minetoken.clans.structure.clan.enums.ClanRanks;

public class ClanRankChangeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private ClanRanks ranks;


    public ClanRankChangeEvent(Player player, ClanRanks rank) {
        this.player = player;
        this.ranks = rank;
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

    public ClanRanks getRank() {
        return this.ranks;
    }
}
