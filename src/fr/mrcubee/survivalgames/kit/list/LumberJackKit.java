package fr.mrcubee.survivalgames.kit.list;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import fr.mrcubee.survivalgames.kit.Kit;

public class LumberJackKit extends Kit {

	private BlockFace[] blockFaces;
	
	public LumberJackKit() {
		super("LumberJack", "You can break whole trees in one shots\n" + "with a wooden axe.",
				new ItemStack(Material.WOOD_AXE));
		this.blockFaces = new BlockFace[] {
				BlockFace.NORTH,
				BlockFace.SOUTH,
				BlockFace.EAST,
				BlockFace.WEST,
				BlockFace.UP,
				BlockFace.DOWN
		};
	}

	@Override
	public boolean canTakeKit(Player player) {
		return true;
	}

	@Override
	public void givePlayerKit(Player player) {
		player.getInventory().addItem(new ItemStack(Material.WOOD_AXE));
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

	public short breakTree(Block block, short durablility) {
		Block nextBlock;
		
		if ((!block.getType().toString().toLowerCase().contains("log")
				&& !block.getType().toString().toLowerCase().contains("leaves")) || durablility <= 0)
			return durablility;
		block.breakNaturally();
		durablility--;
		for (BlockFace blockFace : blockFaces) {
			nextBlock = block.getRelative(blockFace);
			durablility = breakTree(nextBlock, durablility);
			if (durablility <= 0)
				return durablility;
		}
		return durablility;
	}

	@EventHandler
	public void breakBlock(BlockBreakEvent event) {
		if ((!containsPlayer(event.getPlayer())) || (event.getPlayer().getItemInHand() == null)
				|| (!event.getPlayer().getItemInHand().getType().toString().toLowerCase().contains("axe")))
			return;
		ItemStack itemStack = event.getPlayer().getItemInHand();
		Block block = event.getBlock();
		if ((!block.getType().toString().toLowerCase().contains("log"))
				&& (!block.getType().toString().toLowerCase().contains("leaves")))
			return;
		event.setCancelled(true);
		short durablility = breakTree(event.getBlock(),
				(short) (itemStack.getType().getMaxDurability() - itemStack.getDurability()));
		if (durablility <= 0)
			event.getPlayer().setItemInHand(null);
		else
			itemStack.setDurability((short) (itemStack.getType().getMaxDurability() - durablility));
	}
}
