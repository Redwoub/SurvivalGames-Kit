package fr.mrcubee.survivalgames.kit.list;

import fr.mrcubee.langlib.Lang;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.mrcubee.survivalgames.kit.Kit;

public class MinerKit extends Kit implements Listener {

	private final ItemStack[] items;

	public MinerKit() {
		super("Miner", new ItemStack(Material.STONE_PICKAXE));
		ItemMeta itemMeta;

		this.items = new ItemStack[] {
				new ItemStack(Material.STONE_PICKAXE)
		};
		this.items[0].addEnchantment(Enchantment.DIG_SPEED, 5);
		itemMeta = this.items[0].getItemMeta();
		itemMeta.setDisplayName(ChatColor.RED + "Miner");
		itemMeta.spigot().setUnbreakable(true);
		this.items[0].setItemMeta(itemMeta);
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
		if (itemStack == null)
			return true;
		for (ItemStack item : this.items)
			if (itemStack.isSimilar(item))
				return false;
		return true;
	}

	@Override
	public String getDisplayName(Player player) {
		if (player == null)
			return null;
		return Lang.getMessage(player, "kit.miner.name", "&cERROR", true);
	}

	@Override
	public String getDescription(Player player) {
		if (player == null)
			return null;
		return Lang.getMessage(player, "kit.miner.description", "&cERROR", true);
	}

	@Override
	public void update() {

	}

	@EventHandler
	public void onBreakBlock(BlockBreakEvent event){
		Player player = event.getPlayer();
		ItemStack itemStack = event.getPlayer().getItemInHand();
		Block block = event.getBlock();

		if(itemStack.getItemMeta().equals(this.items[0].getItemMeta())){
			switch (block.getType()){
				case IRON_ORE:
					block.setType(Material.AIR);
					player.getWorld().dropItem(block.getLocation(), new ItemStack(Material.IRON_INGOT));
					break;
				case GOLD_ORE:
					block.setType(Material.AIR);
					player.getWorld().dropItem(block.getLocation(), new ItemStack(Material.GOLD_INGOT));
					break;

				case DIAMOND_ORE:
					block.setType(Material.AIR);
					player.getWorld().dropItem(block.getLocation(), new ItemStack(Material.DIAMOND));
					break;

				default: break;
			}
		}
	}
}
