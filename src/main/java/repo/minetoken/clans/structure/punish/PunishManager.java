
package repo.minetoken.clans.structure.punish;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import repo.minetoken.clans.structure.punish.commands.PunishCommand;
import repo.minetoken.clans.structure.rank.enums.Ranks;
import repo.minetoken.clans.structure.rank.RankManager;
import repo.minetoken.clans.utilities.Format;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PunishManager implements Listener {

    public Punish punish;
    public RankManager rankManager;

    private HashMap<Player, PunishPlayer> punishMap = new HashMap<Player, PunishPlayer> ();
    private HashMap<Player, RemovePunish> removepunishMap = new HashMap<Player, RemovePunish> ();
    private HashMap<Player, String> open = new HashMap<Player, String> ();

    public PunishManager(Punish punish, RankManager rankManager) {
        this.punish = punish;
        this.rankManager = rankManager;
        punish.plugin.getServer().getPluginManager().registerEvents(this, punish.plugin);
        punish.plugin.getCommand("punish").setExecutor(new PunishCommand(this));
    }

    public void Help(Player player, String message) {
        if(player.hasPermission("minetoken.helper")) {
            player.sendMessage(Format.main("Punish", "Command List:"));
            player.sendMessage(Format.help("/p <player> <reason>", "Give or Remove a punishment to a player."));
            player.sendMessage(ChatColor.GRAY + "Reason is why you are either removing or adding a punishment.");

            if (message != null) {
                player.sendMessage(ChatColor.RED + message);
            }
        } else {
            player.sendMessage("Unknown command. Type " + '"' + "/help" + '"' + " for help.");
        }
    }

    public void punishInventory(Player player, PunishPlayer punishPlayer) throws ParseException {
        player.closeInventory();
        Inventory inv = Bukkit.createInventory(null, 54, "Punish");

        //PLAYER HEAD

        ItemStack playerhead = new ItemStack(Material.SKULL_ITEM);
        playerhead.setDurability((short) 3);
        SkullMeta playerheadMeta = (SkullMeta) playerhead.getItemMeta();
        playerheadMeta.setDisplayName(punishPlayer.getPlayerName());
        playerheadMeta.setOwner(punishPlayer.getPlayerName());
        playerhead.setItemMeta(playerheadMeta);
        inv.setItem(4, playerhead);

        //SIGNS

        ItemStack adverting = new ItemStack(Material.SIGN);
        String advertingname = ChatColor.GREEN + "" + ChatColor.BOLD + "Advertising Offence";
        ItemMeta advertingmeta = adverting.getItemMeta();
        advertingmeta.setDisplayName(advertingname);
        adverting.setItemMeta(advertingmeta);
        inv.setItem(0, adverting);

        ItemStack gameplay = new ItemStack(Material.SIGN);
        String gameplayname = ChatColor.GREEN + "" + ChatColor.BOLD + "Gameplay Offence";
        ItemMeta gameplaymeta = gameplay.getItemMeta();
        gameplaymeta.setDisplayName(gameplayname);
        gameplay.setItemMeta(gameplaymeta);
        inv.setItem(2, gameplay);

        ItemStack chat = new ItemStack(Material.SIGN);
        String chatname = ChatColor.GREEN + "" + ChatColor.BOLD + "Chat Offence";
        ItemMeta chatmeta = chat.getItemMeta();
        chatmeta.setDisplayName(chatname);
        chat.setItemMeta(chatmeta);
        inv.setItem(6, chat);

        ItemStack hack = new ItemStack(Material.SIGN);
        String hackname = ChatColor.GREEN + "" + ChatColor.BOLD + "Hack Offence";
        ItemMeta hackmeta = hack.getItemMeta();
        hackmeta.setDisplayName(hackname);
        hack.setItemMeta(hackmeta);
        inv.setItem(8, hack);

        //IRON BLOCKS

        ItemStack ad1 = new ItemStack(Material.IRON_BLOCK);
        String ad1name = ChatColor.YELLOW + "Advertising Serverity 1";
        ItemMeta ad1meta = ad1.getItemMeta();
        ad1meta.setDisplayName(ad1name);
        ad1.setItemMeta(ad1meta);
        inv.setItem(9, ad1);

        ItemStack gameplay1 = new ItemStack(Material.IRON_BLOCK);
        String gameplay1name = ChatColor.YELLOW + "Gameplay Serverity 1";
        ItemMeta gameplay1meta = gameplay1.getItemMeta();
        gameplay1meta.setDisplayName(gameplay1name);
        gameplay1.setItemMeta(gameplay1meta);
        inv.setItem(11, gameplay1);

        ItemStack chat1 = new ItemStack(Material.IRON_BLOCK);
        String chat1name = ChatColor.YELLOW +  "Chat Serverity 1";
        ItemMeta chat1meta = chat1.getItemMeta();
        chat1meta.setDisplayName(chat1name);
        chat1.setItemMeta(chat1meta);
        inv.setItem(15, chat1);

        ItemStack hack1 = new ItemStack(Material.IRON_BLOCK);
        String hack1name = ChatColor.YELLOW +  "Hacking Serverity 1";
        ItemMeta hack1meta = hack1.getItemMeta();
        hack1meta.setDisplayName(hack1name);
        hack1.setItemMeta(hack1meta);
        inv.setItem(17, hack1);

        //GOLD BLOCKS

        ItemStack ad2 = new ItemStack(Material.GOLD_BLOCK);
        String ad2name = ChatColor.RED + "Advertising Serverity 2";
        ItemMeta ad2meta = ad2.getItemMeta();
        ad2meta.setDisplayName(ad2name);
        ad2.setItemMeta(ad2meta);
        inv.setItem(18, ad2);

        ItemStack gameplay2 = new ItemStack(Material.GOLD_BLOCK);
        String gameplay2name = ChatColor.RED + "Gameplay Serverity 2";
        ItemMeta gameplay2meta = gameplay2.getItemMeta();
        gameplay2meta.setDisplayName(gameplay2name);
        gameplay2.setItemMeta(gameplay2meta);
        inv.setItem(20, gameplay2);

        ItemStack chat2 = new ItemStack(Material.GOLD_BLOCK);
        String chat2name = ChatColor.RED +  "Chat Serverity 2";
        ItemMeta chat2meta = chat2.getItemMeta();
        chat2meta.setDisplayName(chat2name);
        chat2.setItemMeta(chat2meta);
        inv.setItem(24, chat2);

        ItemStack hack2 = new ItemStack(Material.GOLD_BLOCK);
        String hack2name = ChatColor.RED +  "Hacking Serverity 2";
        ItemMeta hack2meta = hack2.getItemMeta();
        hack2meta.setDisplayName(hack2name);
        hack2.setItemMeta(hack2meta);
        inv.setItem(26, hack2);

        //DIAMOND BLOCKS

        ItemStack ad3 = new ItemStack(Material.DIAMOND_BLOCK);
        String ad3name = ChatColor.DARK_RED + "Advertising Serverity 3";
        ItemMeta ad3meta = ad3.getItemMeta();
        ad3meta.setDisplayName(ad3name);
        ad3.setItemMeta(ad3meta);
        inv.setItem(27, ad3);

        ItemStack gameplay3 = new ItemStack(Material.DIAMOND_BLOCK);
        String gameplay3name = ChatColor.DARK_RED + "Gameplay Serverity 3";
        ItemMeta gameplay3meta = gameplay3.getItemMeta();
        gameplay3meta.setDisplayName(gameplay3name);
        gameplay3.setItemMeta(gameplay3meta);
        inv.setItem(29, gameplay3);

        ItemStack chat3 = new ItemStack(Material.DIAMOND_BLOCK);
        String chat3name = ChatColor.DARK_RED +  "Chat Serverity 3";
        ItemMeta chat3meta = chat3.getItemMeta();
        chat3meta.setDisplayName(chat3name);
        chat3.setItemMeta(chat3meta);
        inv.setItem(33, chat3);

        ItemStack hack3 = new ItemStack(Material.DIAMOND_BLOCK);
        String hack3name = ChatColor.DARK_RED +  "Hacking Serverity 3";
        ItemMeta hack3meta = hack3.getItemMeta();
        hack3meta.setDisplayName(hack3name);
        hack3.setItemMeta(hack3meta);
        inv.setItem(35, hack3);

        //ENCHANTED BOOKS

        int index = 45;
        String type = "";
        String Active = "";
        String Severity = "";
        String Reason = "";
        String Staff = "";
        String Date = "";
        String Duration = "";
        String Expires = "";
        String RemovedBy = "";
        String RemoveReason = "";
        String ID = "";
        ArrayList<String[]> punishmentArrayList;

        UUID asdf = null;
        asdf = punish.rankManager.cPlayer.getUUID(punishPlayer.getPlayerName());

        punishmentArrayList = punish.getPunishments(asdf);
        if (punishmentArrayList != null) {
            if (punishmentArrayList.size() > 9){
                ListIterator listIterator = punishmentArrayList.listIterator();
                int amount = punishmentArrayList.size() -9;
                for (int i = 0; i < amount; i++){
                    listIterator.next();
                    listIterator.remove();
                    System.out.println("Removed 1");
                }
            }
            for (String[] playerPunishment : punishmentArrayList) {
                type = playerPunishment[1];
                Severity = ChatColor.WHITE + "Severity: " + ChatColor.YELLOW + playerPunishment[2];
                Reason = ChatColor.WHITE + "Reason: " + ChatColor.YELLOW + playerPunishment[3];
                Staff = ChatColor.WHITE + "Staff Member: " + ChatColor.YELLOW + punish.rankManager.cPlayer.getName(UUID.fromString(playerPunishment[4]));
                java.util.Date unformattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(playerPunishment[5]);
                String formattedDate = new SimpleDateFormat("MM-dd-yy h:mm a").format(unformattedDate) + " EST";
                Date = ChatColor.WHITE + "Date Issued: " + ChatColor.YELLOW + formattedDate;
                ID = ChatColor.DARK_AQUA + "ID: " + ChatColor.AQUA + playerPunishment[0];
                if (playerPunishment[6].equals("-1")) {
                    Duration = "Permanent";
                    Expires = ChatColor.WHITE + "Expires: " + ChatColor.YELLOW + "Permanent";
                    Active = ChatColor.GOLD + "" + ChatColor.BOLD + type + ChatColor.DARK_GREEN + " Active";
                } else {
                    Duration = playerPunishment[6] + " Hours";

                    long miliseconds = unformattedDate.getTime() - new Date().getTime() + Integer.valueOf(playerPunishment[6]) * 60 * 60 * 1000;

                    long diffMinutes = miliseconds / (60 * 1000) % 60;
                    long diffHours = miliseconds / (60 * 60 * 1000) % 60;
                    long diffDays = miliseconds / (60 * 60 * 24 * 1000) % 60;

                    Expires = ChatColor.WHITE + "Expires: " + ChatColor.YELLOW + diffDays + " Days " + diffHours + " Hours " + diffMinutes + " Minutes";

                    if (miliseconds <= 0) {
                        Expires = ChatColor.WHITE + "Expires: " + ChatColor.YELLOW + "Expired";
                        Active = ChatColor.GOLD + "" + ChatColor.BOLD + type + ChatColor.DARK_RED + " Not Active";
                    } else {
                        Active = ChatColor.GOLD + "" + ChatColor.BOLD + type + ChatColor.DARK_GREEN + " Active";
                    }

                }
                Duration = ChatColor.WHITE + "Duration: " + ChatColor.YELLOW + Duration;

                if (playerPunishment[7].equals("")) {
                    RemovedBy = ChatColor.WHITE + "Removed By: " + ChatColor.RED + "Not Removed";
                    RemoveReason = ChatColor.WHITE + "Remove Reason: " + ChatColor.RED + "Not Removed";
                } else {
                    RemovedBy = ChatColor.WHITE + "Removed By: " + ChatColor.RED + punish.rankManager.cPlayer.getName(UUID.fromString(playerPunishment[7]));
                    RemoveReason = ChatColor.WHITE + "Remove Reason: " + ChatColor.RED + playerPunishment[8];
                    Active = ChatColor.GOLD + "" + ChatColor.BOLD + type + ChatColor.DARK_RED + " Not Active";
                }

                ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
                ItemMeta bookMeta = book.getItemMeta();
                bookMeta.setDisplayName(Active);
                List<String> lore = new ArrayList<String> ();
                lore.add(ID);
                lore.add(Staff);
                lore.add(Date);
                lore.add(Reason);
                lore.add(Severity);
                lore.add(Duration);
                lore.add(Expires);
                lore.add(RemovedBy);
                lore.add(RemoveReason);
                bookMeta.setLore(lore);
                book.setItemMeta(bookMeta);

                if(!Active.contains("Not Active")) {
                    inv.setItem(index, book);

                    index++;
                }
            }
        }

        punishMap.put(player, punishPlayer);
        open.put(player, "Punish Inventory");
        player.openInventory(inv);
    }

    public void checkInventory(Player player, String punishPlayer) {
        player.closeInventory();
        Inventory inv = Bukkit.createInventory(null, 54, "Punish Check");
        //BOOKS

        int index = 0;
        String type = "";
        String Active = "";
        String Severity = "";
        String Reason = "";
        String Staff = "";
        String Date = "";
        String Duration = "";
        String Expires = "";
        String RemovedBy = "";
        String RemoveReason = "";
        String ID = "";
        ArrayList<String[]> punishmentArrayList;

        UUID asdf = punish.rankManager.cPlayer.getUUID (punishPlayer);

        punishmentArrayList = punish.getPunishments(asdf);
        if (punishmentArrayList != null) {
            if (punishmentArrayList.size() > 53) {
                ListIterator listIterator = punishmentArrayList.listIterator();
                listIterator.next ();
                listIterator.remove ();
                System.out.println ("Removed 1");
            }
            for (String[] playerPunishment : punishmentArrayList) {
                type = playerPunishment[1];
                Severity = ChatColor.WHITE + "Severity: " + ChatColor.YELLOW + playerPunishment[2];
                Reason = ChatColor.WHITE + "Reason: " + ChatColor.YELLOW + playerPunishment[3];
                Staff = ChatColor.WHITE + "Staff Member: " + ChatColor.YELLOW + punish.rankManager.cPlayer.getName(UUID.fromString(playerPunishment[4]));
                java.util.Date unformattedDate = null;
                try {
                    unformattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(playerPunishment[5]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String formattedDate = new SimpleDateFormat("MM-dd-yy h:mm a").format(unformattedDate) + " EST";
                Date = ChatColor.WHITE + "Date Issued: " + ChatColor.YELLOW + formattedDate;
                ID = ChatColor.DARK_AQUA + "ID: " + ChatColor.AQUA + playerPunishment[0];
                if (playerPunishment[6].equals("-1")) {
                    Duration = "Permanent";
                    Expires = ChatColor.WHITE + "Expires: " + ChatColor.YELLOW + "Permanent";
                    Active = ChatColor.GOLD + "" + ChatColor.BOLD + type + ChatColor.DARK_GREEN + " Active";
                } else {
                    Duration = playerPunishment[6] + " Hours";

                    long miliseconds = unformattedDate.getTime() - new Date().getTime() + Integer.valueOf(playerPunishment[6]) * 60 * 60 * 1000;

                    long diffMinutes = miliseconds / (60 * 1000) % 60;
                    long diffHours = miliseconds / (60 * 60 * 1000) % 60;
                    long diffDays = miliseconds / (60 * 60 * 24 * 1000) % 60;

                    Expires = ChatColor.WHITE + "Expires: " + ChatColor.YELLOW + diffDays + " Days " + diffHours + " Hours " + diffMinutes + " Minutes";

                    if (miliseconds <= 0) {
                        Expires = ChatColor.WHITE + "Expires: " + ChatColor.YELLOW + "Expired";
                        Active = ChatColor.GOLD + "" + ChatColor.BOLD + type + ChatColor.DARK_RED + " Not Active";
                    } else {
                        Active = ChatColor.GOLD + "" + ChatColor.BOLD + type + ChatColor.DARK_GREEN + " Active";
                    }

                }
                Duration = ChatColor.WHITE + "Duration: " + ChatColor.YELLOW + Duration;

                if (playerPunishment[7].equals("")) {
                    RemovedBy = ChatColor.WHITE + "Removed By: " + ChatColor.RED + "Not Removed";
                    RemoveReason = ChatColor.WHITE + "Remove Reason: " + ChatColor.RED + "Not Removed";
                } else {
                    RemovedBy = ChatColor.WHITE + "Removed By: " + ChatColor.RED + punish.rankManager.cPlayer.getName(UUID.fromString(playerPunishment[7]));
                    RemoveReason = ChatColor.WHITE + "Remove Reason: " + ChatColor.RED + playerPunishment[8];
                    Active = ChatColor.GOLD + "" + ChatColor.BOLD + type + ChatColor.DARK_RED + " Not Active";
                }

                ItemStack book = new ItemStack(Material.BOOK);
                ItemMeta bookMeta = book.getItemMeta();
                bookMeta.setDisplayName(Active);
                List<String> lore = new ArrayList<String> ();
                lore.add(ID);
                lore.add(Staff);
                lore.add(Date);
                lore.add(Reason);
                lore.add(Severity);
                lore.add(Duration);
                lore.add(Expires);
                lore.add(RemovedBy);
                lore.add(RemoveReason);
                bookMeta.setLore(lore);
                book.setItemMeta(bookMeta);

                if (Active.contains("Not Active")) {
                    inv.setItem(index, book);
                    index++;
                }
            }
        }
        open.put(player, "Punish Check Inventory");
        player.openInventory(inv);
    }

    public void confirmRemoval(RemovePunish removePunish) {
        removePunish.getAdmin().closeInventory();
        Inventory inv = Bukkit.createInventory(null, 54, "Are you sure?");

        //YES

        ItemStack yes = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta yesMeta = yes.getItemMeta();
        yesMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "YES");
        yes.setItemMeta(yesMeta);
        inv.setItem(19, yes);
        inv.setItem(20, yes);
        inv.setItem(21, yes);
        inv.setItem(28, yes);
        inv.setItem(29, yes);
        inv.setItem(30, yes);
        inv.setItem(37, yes);
        inv.setItem(38, yes);
        inv.setItem(39, yes);

        //NO

        ItemStack no = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta noMeta = no.getItemMeta();
        noMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "NO");
        no.setItemMeta(noMeta);
        inv.setItem(23, no);
        inv.setItem(24, no);
        inv.setItem(25, no);
        inv.setItem(32, no);
        inv.setItem(33, no);
        inv.setItem(34, no);
        inv.setItem(41, no);
        inv.setItem(42, no);
        inv.setItem(43, no);

        open.put(removePunish.getAdmin(), "Confirm Remove");
        removepunishMap.put(removePunish.getAdmin(), removePunish);
        removePunish.getAdmin().openInventory(inv);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if(open.containsKey(event.getPlayer())) {
            open.remove(event.getPlayer());
        }
        if(punishMap.containsKey(event.getPlayer())) {
            punishMap.remove(event.getPlayer());
        }
        if(removepunishMap.containsKey(event.getPlayer())) {
            removepunishMap.remove(event.getPlayer());
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) throws SQLException {
        Player player = (Player) event.getWhoClicked();

        if (event.getCurrentItem() == null) {
            return;
        }

        if (!event.isCancelled()) {
            event.setCancelled(true);
        }

        if (!open.containsKey(player)) {
            return;
        }

        String punished = null;

        if (punishMap.containsKey(player)) {
            punished = punishMap.get(player).getPlayerName();
        }

        UUID uuid = punish.rankManager.cPlayer.getUUID(punished);

        if (punish.rankManager.cRank.getRank(player.getUniqueId()).getPermLevel() >= Ranks.ASSISTANT.getPermLevel()) {
            if(event.getCurrentItem().getType() == Material.SKULL_ITEM) {
                checkInventory(player, punished);
            }
            if (event.getCurrentItem().getType() == Material.IRON_BLOCK) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Advertising Serverity 1")) {
                    punish.addPunishment(uuid, punishMap.get(player).getAdmin().getUniqueId(), punishMap.get(player).getReason(), Punishments.ADVERTISING1);
                    punish.mutedPlayers.add(uuid);
                    for(Player players : Bukkit.getOnlinePlayers()) {
                        if (players.getName().equalsIgnoreCase(punished)) {
                            players.sendMessage(Format.main("Punish", "You have been muted for " + Format.highlight("6 hours") + " by, " + Format.highlight(player.getName()) + ", because " + Format.highlight(punishMap.get(player).getReason())));
                        }
                    }
                    Bukkit.broadcastMessage(Format.main("Punish", Format.highlight(player.getName()) + " has muted " + Format.highlight(punished) + " for " + Format.highlight("6 hours") + "."));
                    player.closeInventory();
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Gameplay Serverity 1")) {
                    punish.addPunishment(uuid, punishMap.get(player).getAdmin().getUniqueId(), punishMap.get(player).getReason(), Punishments.GAMEPLAY1);
                    for(Player players : Bukkit.getOnlinePlayers()) {
                        if (players.getName().equalsIgnoreCase(punished)) {
                            players.kickPlayer(Format.main("Punish", "You have been banned for " + Format.highlight("6 hours") + " by, " + Format.highlight(player.getName()) + ", because " + Format.highlight(punishMap.get(player).getReason())));
                        }
                    }
                    Bukkit.broadcastMessage(Format.main("Punish", Format.highlight(player.getName()) + " has banned " + Format.highlight(punished) + " for " + Format.highlight("6 hours")) + ".");
                    player.closeInventory();
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Chat Serverity 1")) {
                    punish.addPunishment(uuid, punishMap.get(player).getAdmin().getUniqueId(), punishMap.get(player).getReason(), Punishments.CHAT1);
                    punish.mutedPlayers.add(uuid);
                    for(Player players : Bukkit.getOnlinePlayers()) {
                        if (players.getName().equalsIgnoreCase(punished)) {
                            players.sendMessage(Format.main("Punish", "You have been muted for " + Format.highlight("2 hours") + " by, " + Format.highlight(player.getName()) + ", because " + Format.highlight(punishMap.get(player).getReason())));
                        }
                    }
                    Bukkit.broadcastMessage(Format.main("Punish", Format.highlight(player.getName()) + " has muted " + Format.highlight(punished) + " for " + Format.highlight("2 hours") + "."));
                    player.closeInventory();
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Hacking Serverity 1")) {
                    punish.addPunishment(uuid, punishMap.get(player).getAdmin().getUniqueId(), punishMap.get(player).getReason(), Punishments.HACKING1);
                    for(Player players : Bukkit.getOnlinePlayers()) {
                        if (players.getName().equalsIgnoreCase(punished)) {
                            players.kickPlayer(Format.main("Punish", "You have been banned for " + Format.highlight("12 hours") + " by, " + Format.highlight(player.getName()) + ", because " + Format.highlight(punishMap.get(player).getReason())));
                        }
                    }
                    Bukkit.broadcastMessage(Format.main("Punish", Format.highlight(player.getName()) + " has banned " + Format.highlight(punished) + " for " + Format.highlight("12 hours")) + ".");
                    player.closeInventory();
                }
            }
        } else {
            player.closeInventory();
            player.sendMessage(Format.main("Punish", "Sorry you don't have perrmission to use these severities."));
        }

        if (punish.rankManager.cRank.getRank(player.getUniqueId()).getPermLevel() >= Ranks.MOD.getPermLevel()) {
            if (event.getCurrentItem().getType() == Material.GOLD_BLOCK) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Advertising Serverity 2")) {
                    punish.addPunishment(uuid, punishMap.get(player).getAdmin().getUniqueId(), punishMap.get(player).getReason(), Punishments.ADVERTISING2);
                    punish.mutedPlayers.add(uuid);
                    for(Player players : Bukkit.getOnlinePlayers()) {
                        if (players.getName().equalsIgnoreCase(punished)) {
                            players.sendMessage(Format.main("Punish", "You have been muted for " + Format.highlight("24 hours") + " by, " + Format.highlight(player.getName()) + ", because " + Format.highlight(punishMap.get(player).getReason())));
                        }
                    }
                    Bukkit.broadcastMessage(Format.main("Punish", Format.highlight(player.getName()) + " has muted " + Format.highlight(punished) + " for " + Format.highlight("24 hours") + "."));
                    player.closeInventory();
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Gameplay Serverity 2")) {
                    punish.addPunishment(uuid, punishMap.get(player).getAdmin().getUniqueId(), punishMap.get(player).getReason(), Punishments.GAMEPLAY2);
                    for(Player players : Bukkit.getOnlinePlayers()) {
                        if (players.getName().equalsIgnoreCase(punished)) {
                            players.kickPlayer(Format.main("Punish", "You have been banned for " + Format.highlight("24 hours") + " by, " + Format.highlight(player.getName()) + ", because " + Format.highlight(punishMap.get(player).getReason())));
                        }
                    }
                    Bukkit.broadcastMessage(Format.main("Punish", Format.highlight(player.getName()) + " has banned " + Format.highlight(punished) + " for " + Format.highlight("24 hours")) + ".");
                    player.closeInventory();
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Chat Serverity 2")) {
                    punish.addPunishment(uuid, punishMap.get(player).getAdmin().getUniqueId(), punishMap.get(player).getReason(), Punishments.CHAT2);
                    punish.mutedPlayers.add(uuid);
                    for(Player players : Bukkit.getOnlinePlayers()) {
                        if (players.getName().equalsIgnoreCase(punished)) {
                            players.sendMessage(Format.main("Punish", "You have been muted for " + Format.highlight("12 hours") + " by, " + Format.highlight(player.getName()) + ", because " + Format.highlight(punishMap.get(player).getReason())));
                        }
                    }
                    Bukkit.broadcastMessage(Format.main("Punish", Format.highlight(player.getName()) + " has muted " + Format.highlight(punished) + " for " + Format.highlight("12 hours") + "."));
                    player.closeInventory();
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Hacking Serverity 2")) {
                    punish.addPunishment(uuid, punishMap.get(player).getAdmin().getUniqueId(), punishMap.get(player).getReason(), Punishments.HACKING2);
                    for(Player players : Bukkit.getOnlinePlayers()) {
                        if (players.getName().equalsIgnoreCase(punished)) {
                            players.kickPlayer(Format.main("Punish", "You have been banned for " + Format.highlight("72 hours") + " by, " + Format.highlight(player.getName()) + ", because " + Format.highlight(punishMap.get(player).getReason())));
                        }
                    }
                    Bukkit.broadcastMessage(Format.main("Punish", Format.highlight(player.getName()) + " has banned " + Format.highlight(punished) + " for " + Format.highlight("72 hours")) + ".");
                    player.closeInventory();
                }
            }
        } else {
            player.closeInventory();
            player.sendMessage(Format.main("Punish", "Sorry you don't have perrmission to use these severities."));
        }

        if (punish.rankManager.cRank.getRank(player.getUniqueId()).getPermLevel() >= Ranks.MOD.getPermLevel()) {
            if (event.getCurrentItem().getType() == Material.DIAMOND_BLOCK) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Advertising Serverity 3")) {
                    punish.addPunishment(uuid, punishMap.get(player).getAdmin().getUniqueId(), punishMap.get(player).getReason(), Punishments.ADVERTISING3);
                    punish.mutedPlayers.add(uuid);
                    for(Player players : Bukkit.getOnlinePlayers()) {
                        if (players.getName().equalsIgnoreCase(punished)) {
                            players.sendMessage(Format.main("Punish", "You have been permanently muted by, " + Format.highlight(player.getName()) + ", because " + Format.highlight(punishMap.get(player).getReason()) + "."));
                        }
                    }
                    Bukkit.broadcastMessage(Format.main("Punish", Format.highlight(player.getName()) + " has permanently muted " + Format.highlight(punished) + "."));
                    player.closeInventory();
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Gameplay Serverity 3")) {
                    punish.addPunishment(uuid, punishMap.get(player).getAdmin().getUniqueId(), punishMap.get(player).getReason(), Punishments.GAMEPLAY3);
                    for(Player players : Bukkit.getOnlinePlayers()) {
                        if (players.getName().equalsIgnoreCase(punished)) {
                            players.kickPlayer(Format.main("Punish", "You have been permanently banned by, " + Format.highlight(player.getName()) + ", because " + Format.highlight(punishMap.get(player).getReason()) + "."));
                        }
                    }
                    Bukkit.broadcastMessage(Format.main("Punish", Format.highlight(player.getName()) + " has permanently banned " + Format.highlight(punished) + "."));
                    player.closeInventory();
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Chat Serverity 3")) {
                    punish.addPunishment(uuid, punishMap.get(player).getAdmin().getUniqueId(), punishMap.get(player).getReason(), Punishments.CHAT3);
                    punish.mutedPlayers.add(uuid);
                    for(Player players : Bukkit.getOnlinePlayers()) {
                        if (players.getName().equalsIgnoreCase(punished)) {
                            players.sendMessage(Format.main("Punish", "You have been permanently muted by, " + Format.highlight(player.getName()) + ", because " + Format.highlight(punishMap.get(player).getReason()) + "."));
                        }
                    }
                    Bukkit.broadcastMessage(Format.main("Punish", Format.highlight(player.getName()) + " has permanently muted " + Format.highlight(punished) + "."));
                    player.closeInventory();
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Hacking Serverity 3")) {
                    punish.addPunishment(uuid, punishMap.get(player).getAdmin().getUniqueId(), punishMap.get(player).getReason(), Punishments.HACKING3);
                    for(Player players : Bukkit.getOnlinePlayers()) {
                        if (players.getName().equalsIgnoreCase(punished)) {
                            players.kickPlayer(Format.main("Punish", "You have been permanently banned by, " + Format.highlight(player.getName()) + ", because " + Format.highlight(punishMap.get(player).getReason()) + "."));
                        }
                    }
                    Bukkit.broadcastMessage(Format.main("Punish", Format.highlight(player.getName()) + " has permanently banned " + Format.highlight(punished) + "."));
                    player.closeInventory();
                }
            }

            if (event.getCurrentItem().getType() == Material.ENCHANTED_BOOK) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Not Active")) {
                    player.closeInventory();
                    player.sendMessage(Format.main("Punish", "This punishment is not active."));
                    return;
                }
                String reason = punishMap.get(player).getReason();
                String string = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getLore().get(0).replace("ID: ", ""));
                int id = Integer.parseInt(string);
                confirmRemoval(new RemovePunish(id, reason, player));
            }

            if (event.getCurrentItem().getType() == Material.BOOK) {
                player.closeInventory();
                player.sendMessage(Format.main("Punish", "This punishment is not active."));
            }
            if (event.getCurrentItem().getType() == Material.EMERALD_BLOCK) {
                event.setCancelled(true);
                punish.removePunishment(removepunishMap.get(player).getID(), removepunishMap.get(player).getAdmin().getUniqueId(), removepunishMap.get(player).getReason());
                player.closeInventory();
            }
            if (event.getCurrentItem().getType() == Material.REDSTONE_BLOCK) {
                player.closeInventory();
            }
        } else {
            player.closeInventory();
            player.sendMessage(Format.main("Punish", "Sorry you don't have perrmission to use these severities."));
        }
    }

}
