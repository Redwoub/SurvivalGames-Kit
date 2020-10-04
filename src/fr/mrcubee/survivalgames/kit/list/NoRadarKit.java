package fr.mrcubee.survivalgames.kit.list;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import fr.mrcubee.survivalgames.SurvivalGamesAPI;
import fr.mrcubee.survivalgames.api.event.PlayerRadarEvent;
import fr.mrcubee.survivalgames.kit.Kit;

public class NoRadarKit extends Kit {

	private JavaPlugin javaPlugin;
	private boolean enable;
	private boolean scheduler;

	public NoRadarKit(JavaPlugin javaPlugin) {
		super("NoRadar " + ChatColor.YELLOW + ChatColor.BOLD + "[VIP]", "you are not recognizable by the radar\n"
				+ "as long as there are more than 2 players in the game\n" + "who do not have this Kit.",
				new ItemStack(Material.COMPASS));
		this.javaPlugin = javaPlugin;
		this.enable = true;
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
	
	@EventHandler
	public void playerRadarEvent(PlayerRadarEvent event) {
		int playerInGame = javaPlugin.getServer().getOnlinePlayers().size() - SurvivalGamesAPI.getInstance().getGame().getNumberSpectator();

		if (playerInGame == getPlayers().size())
			return;
		if (containsPlayer(event.getTarget()))
				event.setCancelled(true);
	}

}
