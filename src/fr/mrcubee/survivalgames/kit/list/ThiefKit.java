package fr.mrcubee.survivalgames.kit.list;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import fr.mrcubee.survivalgames.GameStats;
import fr.mrcubee.survivalgames.SurvivalGamesAPI;
import fr.mrcubee.survivalgames.kit.Kit;

public class ThiefKit extends Kit {
	
	private JavaPlugin javaPlugin;
	private Map<Player, Player> thiefTargets;
	private boolean scheduler;
	
	public ThiefKit(JavaPlugin javaPlugin) {
		super("Thief " + ChatColor.BOLD + ChatColor.RED + "[NEW]", "When you are within 4 blocks of another player\nyou can steal it.",
				new ItemStack(Material.EYE_OF_ENDER, 1));
		this.thiefTargets = new HashMap<Player, Player>();
		this.javaPlugin = javaPlugin;
		this.scheduler = false;
	}
	
	@Override
	public void givePlayerKit(Player player) {
		startScheduler();
	}

	@EventHandler
	public void playerInteract(PlayerInteractEntityEvent event) {
		Player thief;
		Player target;

		if ((!containsPlayer(event.getPlayer())) || (!(event.getRightClicked() instanceof Player)) || (!event.getPlayer().isSneaking()))
			return;
		thief = event.getPlayer();
		target = (Player) event.getRightClicked();
		thief.openInventory(target.getInventory());
		if (!thiefTargets.containsKey((Player) event.getPlayer()))
			thiefTargets.put(thief, target);
	}
	
	@EventHandler
	public void playerInteract(InventoryCloseEvent event) {
		if (thiefTargets.containsKey((Player) event.getPlayer()))
				thiefTargets.remove((Player) event.getPlayer());
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
					for (Entry<Player, Player> entry : thiefTargets.entrySet()) {
						if (entry.getKey().getLocation().distance(entry.getValue().getLocation()) > 4)
							entry.getKey().closeInventory();
					}
				} catch (Exception e) {
				}
			}
		}, 0L, 10L);
	}
}