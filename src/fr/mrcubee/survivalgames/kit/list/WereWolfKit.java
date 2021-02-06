package fr.mrcubee.survivalgames.kit.list;

import fr.mrcubee.langlib.Lang;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.mrcubee.survivalgames.GameStats;
import fr.mrcubee.survivalgames.SurvivalGamesAPI;
import fr.mrcubee.survivalgames.kit.Kit;

public class WereWolfKit extends Kit {

	public WereWolfKit() {
		super( "WereWolf", "kit.wereWolf.name", "kit.wereWolf.description", new ItemStack(Material.WATCH));
	}

	@Override
	public boolean canTakeKit(Player player) {
		return true;
	}

	@Override
	public void givePlayerKit(Player player) {

	}

	@Override
	public void removePlayerKit(Player player) {

	}

	@Override
	public boolean canLostItem(ItemStack itemStack) {
		return true;
	}

	@Override
	public String getDisplayName(Player player) {
		if (player == null)
			return null;
		return Lang.getMessage(player, getNameId(), "&cERROR", true);
	}

	@Override
	public String getDescription(Player player) {
		if (player == null)
			return null;
		return Lang.getMessage(player, getDescriptionId(), "&cERROR", true);
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

	public void updateDamageResistance(Player player) {
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

	@Override
	public void update() {
		if (SurvivalGamesAPI.getGame().getGameStats() != GameStats.DURING)
			return;
		for (Player player : getPlayers()) {
			updateDamage(player);
			updateDamageResistance(player);
		}
	}
}
