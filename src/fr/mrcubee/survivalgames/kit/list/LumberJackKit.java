package fr.mrcubee.survivalgames.kit.list;

import fr.mrcubee.langlib.Lang;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import fr.mrcubee.survivalgames.kit.Kit;

public class LumberJackKit extends Kit {

	private final BlockFace[] blockFaces;
	
	public LumberJackKit() {
		super("LumberJack", new ItemStack(Material.WOOD_AXE));
		BlockFace[] values = BlockFace.values();

		this.blockFaces = new BlockFace[values.length - 1];
		for (int i = 0, remove = 0; i < values.length; i++) {
			if (values[i] != BlockFace.SELF)
				this.blockFaces[i - remove] = values[i];
			else
				remove++;
		}
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
		player.getInventory().removeItem(new ItemStack(Material.WOOD_AXE));
	}

	@Override
	public boolean canLostItem(ItemStack itemStack) {
		return true;
	}

	@Override
	public String getDisplayName(Player player) {
		if (player == null)
			return null;
		return Lang.getMessage(player, "kit.lumberJack.name", "&cERROR", true);
	}

	@Override
	public String getDescription(Player player) {
		if (player == null)
			return null;
		return Lang.getMessage(player, "kit.lumberJack.description", "&cERROR", true);
	}

	@Override
	public void update() {

	}

	public short breakTree(Block block, short durability, int limit) {
		Block nextBlock;
		boolean isLeaves;
		
		if (limit < 0 || durability <= 0
		|| (!(isLeaves = block.getType().toString().toLowerCase().contains("leaves"))
		&& !block.getType().toString().toLowerCase().contains("log")))
			return durability;
		block.breakNaturally();
		if (!isLeaves)
			durability--;
		for (BlockFace blockFace : this.blockFaces) {
			nextBlock = block.getRelative(blockFace);
			durability = breakTree(nextBlock, durability, limit - 1);
			if (durability <= 0)
				return durability;
		}
		return durability;
	}

	@EventHandler
	public void breakBlock(BlockBreakEvent event) {
		short durability;

		if ((!containsPlayer(event.getPlayer())) || (event.getPlayer().getItemInHand() == null)
		|| (!event.getPlayer().getItemInHand().getType().toString().toLowerCase().contains("axe")))
			return;
		ItemStack itemStack = event.getPlayer().getItemInHand();
		Block block = event.getBlock();
		if ((!block.getType().toString().toLowerCase().contains("log"))
		&& (!block.getType().toString().toLowerCase().contains("leaves")))
			return;
		event.setCancelled(true);
		durability = breakTree(event.getBlock(),
		(short) (itemStack.getType().getMaxDurability() - itemStack.getDurability()), 500);
		if (durability <= 0)
			event.getPlayer().setItemInHand(null);
		else
			itemStack.setDurability((short) (itemStack.getType().getMaxDurability() - durability));
	}
}
