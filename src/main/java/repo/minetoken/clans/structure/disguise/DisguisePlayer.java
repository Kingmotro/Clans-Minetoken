package repo.minetoken.clans.structure.disguise;

import org.bukkit.entity.Player;
import repo.minetoken.clans.structure.disguise.packet.PacketEntity;
import repo.minetoken.clans.utilities.UtilHologram;

public class DisguisePlayer {

    private PacketEntity packetEntity;
    private Player player;

    public DisguisePlayer(PacketEntity packetEntity, Player player) {
        this.packetEntity = packetEntity;
        this.player = player;
    }

    public PacketEntity getPacketEntity() {
        return packetEntity;
    }

    public Player getPlayer() {
        return player;
    }
}
