package fr.mrcubee.survivalgames.kit.list;

import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import fr.mrcubee.survivalgames.kit.Kit;

public class FarmerKit extends Kit {

	private final ItemStack[] items;

	public FarmerKit() {
		super("Farmer", "Grow the plantations instantly.", new ItemStack(Material.SEEDS, 1));
		items = new ItemStack[] {
				new ItemStack(Material.SEEDS),
				new ItemStack(Material.MELON_SEEDS),
				new ItemStack(Material.PUMPKIN_SEEDS),
				new ItemStack(Material.CARROT),
				new ItemStack(Material.POTATO),
				new ItemStack(Material.COCOA)
		};
	}

	@Override
	public boolean canTakeKit(Player player) {
		return true;
	}

	@Override
	public void givePlayerKit(Player player) {
		if (player == null)
			return;
		player.getInventory().addItem(this.items);
	}

	@Override
	public void removePlayerKit(Player player) {
		if (player == null)
			return;
		player.getInventory().removeItem(this.items);
	}

	@Override
	public boolean canLostItem(ItemStack itemStack) {
		return true;
	}

	@Override
	public void update() {

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
