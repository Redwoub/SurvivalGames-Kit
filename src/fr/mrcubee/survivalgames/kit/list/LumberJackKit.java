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
	
	public LumberJackKit() {
		super("LumberJack", "You can break whole trees in one shots\n" + "with a wooden axe.",
				new ItemStack(Material.WOOD_AXE));
	}

	@Override
	public boolean canPlayerTakeKit(Player player) {
		return true;
	}

	@Override
	public void givePlayerKit(Player player) {
		ItemStack itemStackWoodAxe = new ItemStack(Material.WOOD_AXE);
		player.getInventory().addItem(itemStackWoodAxe);
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
		if (durablility <= 0) {
			event.getPlayer().setItemInHand(null);
		} else {
			itemStack.setDurability((short) (itemStack.getType().getMaxDurability() - durablility));
		}
	}

	public short breakTree(Block block, short durablility) {
		if ((!block.getType().toString().toLowerCase().contains("log"))
				&& (!block.getType().toString().toLowerCase().contains("leaves")))
			return durablility;

		if (durablility <= 0)
			return durablility;

		block.breakNaturally();

		durablility--;

		BlockFace[] blockFaces = new BlockFace[] { BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST,
				BlockFace.UP, BlockFace.DOWN };

		for (BlockFace blockFace : blockFaces) {
			Block blockF = block.getRelative(blockFace);
			durablility = breakTree(blockF, durablility);
			if (durablility <= 0)
				return durablility;
		}

		return durablility;
	}
}
