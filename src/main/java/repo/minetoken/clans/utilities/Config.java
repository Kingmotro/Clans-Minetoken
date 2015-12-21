package repo.minetoken.clans.utilities;

import java.io.File;
import java.io.InputStream;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
 
public class Config extends YamlConfiguration{
	private File configFile;
    private JavaPlugin plugin;
    private String fileName;
    private FileConfiguration fileConfiguration;
    public Config(JavaPlugin plugin, String fileName){
        this.plugin = plugin;
        this.fileName = fileName + (fileName.endsWith(".yml") ? "" : ".yml");
 
        createFile();
    }
 
    private void createFile() {
        try {
            File file = new File(plugin.getDataFolder(), fileName);
            if (!file.exists()){
                if (plugin.getResource(fileName) != null){
                    plugin.saveResource(fileName, false);
                }else{
                    save(file);
                }
            }else{
                load(file);
                save(file);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
 
    public void save(){
        try {
            save(new File(plugin.getDataFolder(), fileName));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public FileConfiguration getConfig()
    {
        if (fileConfiguration == null)
        {
            reloadConfig();
        }
        return fileConfiguration;
    }
    
    public void reloadConfig()
    {
        fileConfiguration = YamlConfiguration.loadConfiguration(configFile);
        InputStream defConfigStream = plugin.getResource(fileName);
        if (defConfigStream != null)
        {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            fileConfiguration.setDefaults(defConfig);
        }
    }
}