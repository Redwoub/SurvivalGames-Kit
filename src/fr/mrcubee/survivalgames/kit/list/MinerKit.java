package fr.mrcubee.survivalgames.kit.list;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.mrcubee.survivalgames.kit.Kit;

public class MinerKit extends Kit {

	public MinerKit() {
		super("Miner", "You appear with:\n" + "- 1x Stone pickaxe durability 2 and efficiency 5",
				new ItemStack(Material.STONE_PICKAXE));
	}

	@Override
	public void givePlayerKit(Player player) {
		ItemStack itemStackPickaxe = new ItemStack(Material.STONE_PICKAXE);
		itemStackPickaxe.addEnchantment(Enchantment.DURABILITY, 2);
		itemStackPickaxe.addEnchantment(Enchantment.DIG_SPEED, 5);
		ItemMeta itemMetaPickaxe = itemStackPickaxe.getItemMeta();
		itemMetaPickaxe.setDisplayName(ChatColor.RED + "Miner");
		itemStackPickaxe.setItemMeta(itemMetaPickaxe);
		player.getInventory().addItem(itemStackPickaxe);
	}
}
