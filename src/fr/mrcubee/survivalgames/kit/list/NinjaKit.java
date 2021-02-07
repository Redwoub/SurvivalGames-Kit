package fr.mrcubee.survivalgames.kit.list;

import fr.mrcubee.langlib.Lang;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.mrcubee.survivalgames.GameStats;
import fr.mrcubee.survivalgames.SurvivalGamesAPI;
import fr.mrcubee.survivalgames.kit.Kit;

public class NinjaKit extends Kit {

	public NinjaKit() {
		super("Ninja", new ItemStack(Material.STICK));
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
		return Lang.getMessage(player, "kit.ninja.name", "&cERROR", true);
	}

	@Override
	public String getDescription(Player player) {
		if (player == null)
			return null;
		return Lang.getMessage(player, "kit.ninja.description", "&cERROR", true);
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

	@Override
	public void update() {
		if (SurvivalGamesAPI.getGame().getGameStats() != GameStats.DURING)
			return;
		for (Player player : getPlayers()) {
			updateInvisible(player);
			updateNightVision(player);
		}
	}
}
