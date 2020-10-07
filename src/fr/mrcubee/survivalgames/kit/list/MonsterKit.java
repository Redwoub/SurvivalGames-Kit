package fr.mrcubee.survivalgames.kit.list;

import org.bukkit.Material;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import fr.mrcubee.survivalgames.GameStats;
import fr.mrcubee.survivalgames.SurvivalGamesAPI;
import fr.mrcubee.survivalgames.kit.Kit;

public class MonsterKit extends Kit {

	private JavaPlugin javaPlugin;
	private boolean scheduler;

	public MonsterKit(JavaPlugin javaPlugin) {
		super("Monster", "Monsters take you for one of their\n" + "They will not attack you.",
				new ItemStack(Material.ROTTEN_FLESH));
		this.javaPlugin = javaPlugin;
		this.scheduler = false;
	}

	@Override
	public void givePlayerKit(Player player) {
		startScheduler();
		player.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
	}

	@EventHandler
	public void targetEvent(EntityTargetEvent event) {
		if (!(event.getTarget() instanceof Player))
			return;
		Player target = (Player) event.getTarget();

		if (!containsPlayer(target))
			return;

		if (event.getEntity() instanceof Monster)
			event.setCancelled(true);
	}

	public void update(Player player) {
		if ((player == null) || (!player.isOnline()))
			return;
		if (player.getLocation().clone().add(0, 1, 0).getBlock().getLightFromSky() <= 10)
			return;
		if (player.getInventory().getHelmet() != null)
			return;
		player.setFireTicks(20);
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
						update(player);
					}
				} catch (Exception e) {
				}
			}
		}, 10L, 10L);
	}

}
