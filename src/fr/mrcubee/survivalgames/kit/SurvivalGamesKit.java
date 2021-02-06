package fr.mrcubee.survivalgames.kit;

import fr.mrcubee.langlib.Lang;
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
		Lang.setDefaultLang(Lang.EN_US);
		RegisterKit.register();
	}

	public static SurvivalGamesKit getInstance() {
		return SurvivalGamesKit.instance;
	}
}
