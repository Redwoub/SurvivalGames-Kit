package fr.mrcubee.survivalgames.kit;

import org.bukkit.plugin.java.JavaPlugin;
import fr.mrcubee.survivalgames.kit.list.RegisterKit;

public class SurvivalGamesKit extends JavaPlugin {
	
	@Override
	public void onLoad() {
		if (this.getServer().getPluginManager().getPlugin("SurvivalGames") == null)
			this.getServer().getPluginManager().disablePlugin(this);
	}
	
	@Override
	public void onEnable() {
		if (this.getServer().getPluginManager().getPlugin("SurvivalGames") == null) {
			this.getServer().getPluginManager().disablePlugin(this);
			return;
		}
		RegisterKit.register();
	}

}
