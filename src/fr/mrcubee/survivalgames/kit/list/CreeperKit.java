package fr.mrcubee.survivalgames.kit.list;

import fr.mrcubee.langlib.Lang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import fr.mrcubee.survivalgames.kit.Kit;

public class CreeperKit extends Kit {
	
	public CreeperKit() {
		super("Creeper", new ItemStack(Material.TNT));
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
		return Lang.getMessage(player, "kit.creeper.name", "&cERROR", true);
	}

	@Override
	public String getDescription(Player player) {
		if (player == null)
			return null;
		return Lang.getMessage(player, "kit.creeper.description", "&cERROR", true);
	}

	@Override
	public void update() {

	}

	@EventHandler(priority=EventPriority.LOWEST)
	public void playerDeath(PlayerDeathEvent event) {
		if (!containsPlayer(event.getEntity()))
			return;
		event.getEntity().getWorld().createExplosion(event.getEntity().getLocation(), 10L);
	}
}
