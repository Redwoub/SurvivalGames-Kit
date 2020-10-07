package fr.mrcubee.survivalgames.kit.list;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.mrcubee.survivalgames.GameStats;
import fr.mrcubee.survivalgames.SurvivalGamesAPI;
import fr.mrcubee.survivalgames.kit.Kit;

public class NinjaKit extends Kit {

	private JavaPlugin javaPlugin;
	private boolean scheduler;

	public NinjaKit(JavaPlugin javaPlugin) {
		super("Ninja", "In complete darkness\n" + "you become invisible !", new ItemStack(Material.STICK));
		this.javaPlugin = javaPlugin;
		this.scheduler = false;
	}

	@Override
	public void givePlayerKit(Player player) {
		startScheduler();
	}

	public void updateInvisible(Player player) {
		if ((player == null) || (!player.isOnline()))
			return;
		Block boots = player.getLocation().getBlock();
		Block head = player.getLocation().clone().add(0, 1, 0).getBlock();
		if ((boots.getLightLevel() < 5) && (head.getLightLevel() < 5)
				&& (!player.hasPotionEffect(PotionEffectType.INVISIBILITY))) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 5), true);
		} else if (player.hasPotionEffect(PotionEffectType.INVISIBILITY) && (boots.getLightLevel() >= 5)
				&& (head.getLightLevel() >= 5)) {
			for (PotionEffect potion : player.getActivePotionEffects()) {
				if (potion.getType().equals(PotionEffectType.INVISIBILITY) && (potion.getAmplifier() == 5)) {
					player.removePotionEffect(PotionEffectType.INVISIBILITY);
				}
			}
		}
	}

	public void updateNightVision(Player player) {
		if ((player == null) || (!player.isOnline()))
			return;
		Block boots = player.getLocation().getBlock();
		Block head = player.getLocation().clone().add(0, 1, 0).getBlock();
		if ((boots.getLightLevel() < 5) && (head.getLightLevel() < 5)
				&& (!player.hasPotionEffect(PotionEffectType.NIGHT_VISION))) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 5), true);
		} else if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION) && (boots.getLightLevel() >= 5)
				&& (head.getLightLevel() >= 5)) {
			for (PotionEffect potion : player.getActivePotionEffects()) {
				if (potion.getType().equals(PotionEffectType.NIGHT_VISION) && (potion.getAmplifier() == 5)) {
					player.removePotionEffect(PotionEffectType.NIGHT_VISION);
				}
			}
		}
	}

	private void startScheduler() {
		if (scheduler)
			return;
		this.scheduler = !scheduler;
		javaPlugin.getServer().getScheduler().scheduleSyncRepeatingTask(javaPlugin, new Runnable() {
			public void run() {
				try {
					if (SurvivalGamesAPI.getGame().getGameStats() != GameStats.DURING)
						return;
					for (Player player : getPlayers()) {
						updateInvisible(player);
						updateNightVision(player);
					}
				} catch (Exception e) {
				}
			}
		}, 0L, 10L);
	}

}
