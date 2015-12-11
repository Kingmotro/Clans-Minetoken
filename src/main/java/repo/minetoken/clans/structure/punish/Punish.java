package repo.minetoken.clans.structure.punish;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import repo.minetoken.clans.structure.database.Database;
import repo.minetoken.clans.structure.rank.RankManager;
import repo.minetoken.clans.utilities.Format;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Punish extends Database {

	public RankManager rankManager;
	public JavaPlugin plugin;
	private Connection connection;
	
	public Punish(JavaPlugin plugin, RankManager rankManager) {
		super("root", "ThePyxel", "", "3306", "localhost");
		this.plugin = plugin;
		this.rankManager = rankManager;
		plugin.getServer().getPluginManager().registerEvents(new OffenceListener(this, plugin), plugin);
		connection = openConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `PUNISHMENTS` (`ID` int(32) NOT NULL AUTO_INCREMENT, `Player` varchar(36) NOT NULL, `Type` varchar(32) NOT NULL, `Severity` int(1) NOT NULL, `Reason` varchar(50) NOT NULL, `Admin` varchar(36) NOT NULL, `Date` varchar(25) NOT NULL, `Duration` int(8) NOT NULL, `RemovedBy` varchar(36) NOT NULL, `RemoveReason` varchar(50) NOT NULL, PRIMARY KEY (`ID`))");
			ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println("[Punish] [ERROR] Could not check if table exists");
			e.printStackTrace();
		}
	}

	ArrayList<UUID> mutedPlayers = new ArrayList<UUID> ();

	public boolean checkifMuted(UUID uuid) {
		if(mutedPlayers.contains(uuid)) {
			return true;
		} else {
			return false;
		}
	}

	public Player getPlayerByUuid(UUID uuid) {
		for (Player p : Bukkit.getServer().getOnlinePlayers())
			if (p.getUniqueId().equals(uuid))
				return p;

		throw new IllegalArgumentException();
	}

	public boolean addPunishment(UUID player, UUID admin, String reason, Punishments punishment) {
		if (reason.toLowerCase().contains("update") || reason.toLowerCase().contains("insert") || reason.toLowerCase().contains("select")) {
			getPlayerByUuid(admin).sendMessage(ChatColor.RED + "Update, Insert, and Select are not allowed in your punishment reason!");
			return false;
		}
		checkConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO `PUNISHMENTS` (Player,Type,Severity,Reason,Admin,Date,Duration,RemovedBy,RemoveReason) VALUES (?,?,?,?,?,?,?,?,?)");
			ps.setString(1, player.toString());
			ps.setString(2, punishment.getTitle());
			ps.setInt(3, punishment.getSeverity());
			ps.setString(4, reason);
			ps.setString(5, admin.toString());
			Date dt = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentTime = sdf.format(dt);
			ps.setString(6, currentTime);
			ps.setInt(7, punishment.getHours());
			ps.setString(8, "");
			ps.setString(9, "");
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public String getRemoveBy(int id) {
		checkConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT RemovedBy FROM PUNISHMENTS WHERE ID = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("RemovedBy");
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean removePunishment(int id, UUID RemovedBy, String RemoveReason) {
		checkConnection();
		PreparedStatement ps;
		if (getRemoveBy(id).equals("")) {
            try {
                ps = connection.prepareStatement("UPDATE `PUNISHMENTS` SET RemovedBy=?,RemoveReason=? WHERE id=?");
                ps.setString(1, RemovedBy.toString());
                ps.setString(2, RemoveReason);
                ps.setInt(3, id);
                ps.executeUpdate();
                getPlayerByUuid(RemovedBy).sendMessage(Format.main("Punish", "Successfully removed ID: " + ChatColor.GREEN + Integer.toString(id)));
                String[] result = getPunishment(id);
				for(Player players : Bukkit.getOnlinePlayers()) {
					if(players.getUniqueId().equals(result[1])) {
						if (result[0].toLowerCase().equals("chat offense") || result[0].toLowerCase().equals("advertising")) {
							mutedPlayers.remove(result[1]);
							players.sendMessage(Format.main("Punish", "You have been unmuted."));
						}
					}
				}
            } catch (SQLException e1) {
                e1.printStackTrace();
                return false;
            }
        } else {
            System.out.println("ERROR!!!!");
        }
		return true;
	}

	private ArrayList<String[]> punishmentList = new ArrayList<String[]> ();

	public ArrayList<String[]> getPunishments(UUID player) {
		punishmentList.clear();
		checkConnection();
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement("SELECT * FROM PUNISHMENTS WHERE player = ?");
		} catch (SQLException e1) {
			e1.printStackTrace();
			return null;
		}
		try {
			ps.setString(1, player.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		ResultSet rs;
		try {
			rs = ps.executeQuery();
		} catch (SQLException e) {

			e.printStackTrace();
			return null;
		}
		try {
			while (rs.next()) {
				/* --------------------------------- */
				String[] info = {Integer.toString(rs.getInt("ID")), rs.getString("Type"), Integer.toString(rs.getInt("Severity")), rs.getString("Reason"),rs.getString("Admin"), rs.getString("Date"), Integer.toString(rs.getInt("Duration")), rs.getString("RemovedBy"),rs.getString("RemoveReason")};

				punishmentList.add(info);
				/* --------------------------------- */
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		if (punishmentList.isEmpty()) {
			return null;
		}
		
		return punishmentList;
	}

	public String[] getPunishment(int id) {
		checkConnection();
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement("SELECT Type,Player FROM PUNISHMENTS WHERE ID = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new String[] {rs.getString("Type"), rs.getString("Player")};
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
}
