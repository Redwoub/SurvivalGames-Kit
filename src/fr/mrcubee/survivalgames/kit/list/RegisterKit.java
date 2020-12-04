package fr.mrcubee.survivalgames.kit.list;

import org.bukkit.Bukkit;

import fr.mrcubee.survivalgames.SurvivalGames;
import fr.mrcubee.survivalgames.SurvivalGamesAPI;
import fr.mrcubee.survivalgames.kit.Kit;
import fr.mrcubee.survivalgames.kit.KitManager;
import fr.mrcubee.survivalgames.kit.list.illusion.IllusionKit;

public class RegisterKit {
	
	public static void register() {
		Kit[] kits = new Kit[] {
				new ArcherKit(),
				new MinerKit(),
				new LumberJackKit(),
				new MonsterKit(),
				new CreeperKit(),
				new FarmerKit(),
				new NinjaKit(),
				new ThiefKit(),
				new WereWolfKit(),
				new NoRadarKit(),
				new FakeRadarKit(),
				new DeathNote(),
				new IllusionKit()
				//MrCubeeKit.generateKit()
		};
		SurvivalGames survivalGames = SurvivalGamesAPI.getGame().getPlugin();
		KitManager kitManager = SurvivalGamesAPI.getGame().getKitManager();

		for (Kit kit : kits) {
			kitManager.registerKit(kit);
			Bukkit.getServer().getPluginManager().registerEvents(kit, survivalGames);
		}
	}

}
