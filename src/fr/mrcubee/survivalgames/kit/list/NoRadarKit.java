package fr.mrcubee.survivalgames.kit.list;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

import fr.mrcubee.survivalgames.SurvivalGamesAPI;
import fr.mrcubee.survivalgames.api.event.PlayerRadarEvent;
import fr.mrcubee.survivalgames.kit.Kit;

public class NoRadarKit extends Kit {

	public NoRadarKit() {
		super( ChatColor.YELLOW + "NoRadar", "you are not recognizable by the radar\n"
				+ "as long as there are more than 2 players in the game\n" + "who do not have this Kit.",
				new ItemStack(Material.BARRIER));
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
	public void update() {

	}

	@EventHandler
	public void playerRadarEvent(PlayerRadarEvent event) {
		int playerInGame = SurvivalGamesAPI.getGame().getNumberPlayer();

		if (playerInGame == 2 || playerInGame == getNumberPlayer())
			return;
		if (containsPlayer(event.getTarget()))
			event.setCancelled(true);
	}

}
