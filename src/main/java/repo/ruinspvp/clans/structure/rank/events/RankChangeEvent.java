package repo.ruinspvp.clans.structure.rank.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import repo.ruinspvp.clans.structure.rank.enums.Ranks;

public class RankChangeEvent extends Event{
	
	private static final HandlerList handlers = new HandlerList();
	private Player player;
	private Ranks ranks;

	public RankChangeEvent(Player player, Ranks rank) {
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

	public Ranks getRank() {
		return this.ranks;
	}
}
