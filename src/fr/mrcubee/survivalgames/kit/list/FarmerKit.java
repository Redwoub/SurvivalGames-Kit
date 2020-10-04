package fr.mrcubee.survivalgames.kit.list;

import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import fr.mrcubee.survivalgames.kit.Kit;

public class FarmerKit extends Kit {

	public FarmerKit() {
		super("Farmer", "Grow the plantations instantly.", new ItemStack(Material.SEEDS, 1));
	}

	@Override
	public void givePlayerKit(Player player) {
		player.getInventory().addItem(new ItemStack(Material.SEEDS));
		player.getInventory().addItem(new ItemStack(Material.MELON_SEEDS));
		player.getInventory().addItem(new ItemStack(Material.PUMPKIN_SEEDS));
		player.getInventory().addItem(new ItemStack(Material.CARROT));
		player.getInventory().addItem(new ItemStack(Material.POTATO));
		player.getInventory().addItem(new ItemStack(Material.COCOA));
	}
	
	@Override
	public void removePlayerKit(Player player) {
		player.getInventory().clear();
	}

	@EventHandler
	public void blockPlace(BlockPlaceEvent event) {
		if (!containsPlayer(event.getPlayer()))
			return;
		
		if (event.getBlock().getType().equals(Material.CROPS) || event.getBlock().getType().equals(Material.COCOA)
				|| event.getBlock().getType().equals(Material.CARROT)
				|| event.getBlock().getType().equals(Material.POTATO)
				|| event.getBlock().getType().equals(Material.MELON_STEM)
				|| event.getBlock().getType().equals(Material.PUMPKIN_STEM))
			event.getBlock().setData(CropState.RIPE.getData());
	}

}
