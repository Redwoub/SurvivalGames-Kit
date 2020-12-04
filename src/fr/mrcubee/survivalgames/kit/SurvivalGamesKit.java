package fr.mrcubee.survivalgames.kit;

import org.bukkit.plugin.java.JavaPlugin;
import fr.mrcubee.survivalgames.kit.list.RegisterKit;

public class SurvivalGamesKit extends JavaPlugin {

	private static SurvivalGamesKit instance;

	@Override
	public void onLoad() {
		SurvivalGamesKit.instance = this;
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

	public static SurvivalGamesKit getInstance() {
		return SurvivalGamesKit.instance;
	}
}
