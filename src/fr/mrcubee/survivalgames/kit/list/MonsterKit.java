package fr.mrcubee.survivalgames.kit.list;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.inventory.ItemStack;

import fr.mrcubee.survivalgames.GameStats;
import fr.mrcubee.survivalgames.SurvivalGamesAPI;
import fr.mrcubee.survivalgames.kit.Kit;

public class MonsterKit extends Kit {

	public MonsterKit() {
		super("Monster", "Monsters take you for one of their\n" + "They will not attack you.",
				new ItemStack(Material.ROTTEN_FLESH));
	}

	@Override
	public boolean canTakeKit(Player player) {
		return true;
	}

	@Override
	public void givePlayerKit(Player player) {
		player.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
	}

	@Override
	public void removePlayerKit(Player player) {
		player.getInventory().setHelmet(null);
	}

	@Override
	public boolean canLostItem(ItemStack itemStack) {
		return true;
	}

	@Override
	public void update() {
		Location playerLoc;

		if (SurvivalGamesAPI.getGame().getGameStats() != GameStats.DURING)
			return;
		for (Player player : getPlayers()) {
			if (player.isOnline() && player.getInventory().getHelmet() == null) {
				playerLoc = player.getLocation();
				if (player.getWorld().getHighestBlockYAt(playerLoc.getBlockX(), playerLoc.getBlockZ()) <= playerLoc.getBlockY())
					player.setFireTicks(20);
			}
		}
	}

	@EventHandler
	public void targetEvent(EntityTargetEvent event) {
		if (!(event.getTarget() instanceof Player))
			return;
		if (containsPlayer((Player) event.getTarget()) && (event.getEntity() instanceof Monster))
			event.setCancelled(true);
	}
}
