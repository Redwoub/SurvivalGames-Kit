package fr.mrcubee.survivalgames.kit.list;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.mrcubee.survivalgames.GameStats;
import fr.mrcubee.survivalgames.SurvivalGamesAPI;
import fr.mrcubee.survivalgames.kit.Kit;

public class WereWolfKit extends Kit {

	private JavaPlugin javaPlugin;
	private boolean scheduler;

	public WereWolfKit(JavaPlugin javaPlugin) {
		super("WereWolf " + ChatColor.YELLOW + ChatColor.BOLD + "[VIP]",
				"In the night you transform yourself.\n" + "You gain in strength and resistance.",
				new ItemStack(Material.WATCH));
		this.javaPlugin = javaPlugin;
		this.scheduler = false;
	}

	@Override
	public boolean canPlayerTakeKit(Player player) {
		if (player != null) {
			if (player.isOp())
				return true;
			if (player.hasPermission("mrcubee.hg.vip") || player.hasPermission("mrcubee.hg.winvip")
					|| player.hasPermission("mrcubee.hg.kit.noradar"))
				return true;
		}
		player.sendMessage(
				ChatColor.RED + "Sorry, you can't take the " + ChatColor.RESET + getName() + ChatColor.RED + " kit.");
		return false;
	}

	@Override
	public void givePlayerKit(Player player) {
		startScheduler();
	}

	public void updateDamage(Player player) {
		if ((player == null) || (!player.isOnline()))
			return;
		long time = player.getWorld().getTime();
		if ((time >= 12500) && (time <= 23000) && (!player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE))) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 999999, 2), true);
		} else if (player.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE) && ((time < 12500) || (time > 23000))) {
			for (PotionEffect potion : player.getActivePotionEffects()) {
				if (potion.getType().equals(PotionEffectType.INCREASE_DAMAGE) && (potion.getAmplifier() == 2)) {
					player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
				}
			}
		}
	}

	public void updateDamaResistance(Player player) {
		if ((player == null) || (!player.isOnline()))
			return;
		long time = player.getWorld().getTime();
		if ((time >= 12500) && (time <= 23000) && (!player.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE))) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999, 2), true);
		} else if (player.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE) && ((time < 12500) || (time > 23000))) {
			for (PotionEffect potion : player.getActivePotionEffects()) {
				if (potion.getType().equals(PotionEffectType.DAMAGE_RESISTANCE) && (potion.getAmplifier() == 2)) {
					player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
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
					if (SurvivalGamesAPI.getInstance().getGame().getGameStats() != GameStats.DURING)
						return;
					for (Player player : getPlayers()) {
						updateDamage(player);
						updateDamaResistance(player);
					}
				} catch (Exception e) {
				}
			}
		}, 10L, 10L);
	}

}
