package fr.mrcubee.survivalgames.kit.list;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import fr.mrcubee.survivalgames.kit.Kit;

public class CreeperKit extends Kit {
	
	public CreeperKit() {
		super("Creeper", "When you die, you make a huge explosion.", new ItemStack(Material.TNT));
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void death(PlayerDeathEvent event) {
		if (!containsPlayer(event.getEntity()))
			return;
		event.getEntity().getWorld().createExplosion(event.getEntity().getLocation(), 10L);
	}
}
