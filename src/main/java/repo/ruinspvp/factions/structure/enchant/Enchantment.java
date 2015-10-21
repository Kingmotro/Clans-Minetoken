package repo.ruinspvp.factions.structure.enchant;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Enchantment implements Listener {

    public EnchantManager enchantManager;
    public String name;
    public int level;
    public JavaPlugin plugin;
    public String[] itemType;
    public double chance;
    public int levelRequirement;

    String permission;

    public Enchantment(EnchantManager enchantManager, String name, int level, JavaPlugin plugin, String[] itemType, double chance, int levelRequirement, String permission) {
        this.enchantManager = enchantManager;
        this.name = name;
        this.level = level;
        this.plugin = plugin;
        this.itemType = itemType;
        this.chance = chance;
        this.levelRequirement = levelRequirement;
        this.permission = permission;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public String[] getItemType() {
        return itemType;
    }

    public double getChance() {
        return chance;
    }

    public int getLevelRequirement() {
        return levelRequirement;
    }

    public String getPermission() {
        return permission;
    }

    public String getRomanNumber(int level) {
        if(level==1)return "I";
        if(level==2)return "II";
        if(level==3)return "III";
        if(level==4)return "IV";
        if(level==5)return "V";
        if(level==6)return "VI";
        if(level==7)return "VII";
        if(level==8)return "VIII";
        if(level==9)return "IX";
        if(level==10)return "X";
        if(level==11)return "XI";
        if(level==12)return "XII";
        if(level==13)return "XIII";
        if(level==14)return "XIV";
        if(level==15)return "XV";
        if(level==16)return "XVI";
        if(level==17)return "XVII";
        if(level==18)return "XVIII";
        if(level==19)return "XIX";
        if(level==20)return "XX";
        if(level==21)return "XXI";
        if(level==22)return "XXII";
        if(level==23)return "XXIII";
        if(level==24)return "XXIV";
        if(level==25)return "XXV";
        if(level==26)return "XXVI";
        if(level==27)return "XXVII";
        if(level==28)return "XXVIII";
        if(level==29)return "XXIX";
        if(level==30)return "XXX";
        if(level==31)return "XXXI";
        if(level==32)return "XXXII";
        if(level==33)return "XXXIII";
        if(level==34)return "XXXIV";
        if(level==35)return "XXXV";
        if(level==36)return "XXXVI";
        if(level==37)return "XXXVII";
        if(level==38)return "XXXVIII";
        if(level==39)return "XXXIX";
        if(level==40)return "XL";
        if(level==41)return "XLI";
        if(level==42)return "XLII";
        if(level==43)return "XLIII";
        if(level==44)return "XLIV";
        if(level==45)return "XLV";
        if(level==46)return "XLVI";
        if(level==47)return "XLVII";
        if(level==48)return "XLVIII";
        if(level==49)return "XLIX";
        if(level==50)return "L";
        return null;
    }
}
