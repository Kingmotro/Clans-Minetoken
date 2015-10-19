package repo.ruinspvp.factions.structure.database;

public abstract class DatabaseCall<PluginType extends Database> {

    protected PluginType plugin;

    public DatabaseCall(PluginType plugin) {
        this.plugin = plugin;
    }

}
