package fr.mrcubee.survivalgames.kit.list;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.mrcubee.survivalgames.GameStats;
import fr.mrcubee.survivalgames.SurvivalGamesAPI;
import fr.mrcubee.survivalgames.kit.Kit;

public class MyopieKit extends Kit {
	
	private JavaPlugin javaPlugin;
	private boolean scheduler;
	
	public MyopieKit(JavaPlugin javaPlugin) {
		super("MyopieKit",
				"Le kit Myopie c'est quoi?\n" + "Donc alors mon enfant tu obtiens un baton de KnockBack1\n"
						+ "Mes le probleme c'est que tu et Myop donc ta vue est moin bon",
				new ItemStack(Material.PUMPKIN, 1));
		this.javaPlugin = javaPlugin;
		this.scheduler = false;
	}
	
	@Override
	public void givePlayerKit(Player player) {
		startScheduler();
	}
	
	private void startScheduler() {
		if (scheduler)
			return;
		this.scheduler = !scheduler;
		javaPlugin.getServer().getScheduler().scheduleSyncRepeatingTask(javaPlugin, new Runnable() {
			public void run() {
				try {
					if (SurvivalGamesAPI.getInstance().getGame().getGameStats() != GameStats.DURING)
						return;
					for (Player player : getPlayers()) {
						if (player.hasPotionEffect(PotionEffectType.BLINDNESS))
							player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 999999, 1));
						if (player.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE))
							player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 999999, 3));
						if (player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE))
							player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999, 3));
					}
				} catch (Exception e) {
				}
			}
		}, 0L, 10L);
	}

}
