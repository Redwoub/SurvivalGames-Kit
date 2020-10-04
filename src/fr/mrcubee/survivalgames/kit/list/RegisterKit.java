package fr.mrcubee.survivalgames.kit.list;

import org.bukkit.plugin.java.JavaPlugin;

import fr.mrcubee.survivalgames.SurvivalGamesAPI;
import fr.mrcubee.survivalgames.kit.KitManager;

public class RegisterKit {
	
	public static void register(JavaPlugin javaPlugin) {
		KitManager kitManager = SurvivalGamesAPI.getInstance().getGame().getKitManager();
		kitManager.registerKit(new ArcherKit());
		kitManager.registerKit(new MinerKit());
		kitManager.registerKit(new LumberJackKit());
		kitManager.registerKit(new MonsterKit(javaPlugin));
		kitManager.registerKit(new CreeperKit());
		kitManager.registerKit(new FarmerKit());
		kitManager.registerKit(new NinjaKit(javaPlugin));
		kitManager.registerKit(new ThiefKit(javaPlugin));
		//kitManager.registerKit(new MyopieKit(javaPlugin));
		kitManager.registerKit(new WereWolfKit(javaPlugin));
		kitManager.registerKit(new NoRadarKit(javaPlugin));
		//kitManager.registerKit(MrCubeeKit.generateKit());
	}

}
