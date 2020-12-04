package fr.mrcubee.survivalgames.kit.list;

import fr.mrcubee.survivalgames.SurvivalGamesAPI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.mrcubee.survivalgames.kit.Kit;

public class MinerKit extends Kit {

	private final ItemStack[] items;

	public MinerKit() {
		super("Miner", "You appear with:\n" + "- 1x Stone pickaxe durability 2 and efficiency 5",
				new ItemStack(Material.STONE_PICKAXE));
		ItemMeta itemMeta;

		this.items = new ItemStack[] {
				new ItemStack(Material.STONE_PICKAXE)
		};
		this.items[0].addEnchantment(Enchantment.DURABILITY, 2);
		this.items[0].addEnchantment(Enchantment.DIG_SPEED, 5);
		this.items[0].addEnchantment(Enchantment.DURABILITY, 3);
		itemMeta = this.items[0].getItemMeta();
		itemMeta.setDisplayName(ChatColor.RED + "Miner");
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
	public void update() {

	}
}
