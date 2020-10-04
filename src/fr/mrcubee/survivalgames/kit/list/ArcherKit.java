package fr.mrcubee.survivalgames.kit.list;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.mrcubee.survivalgames.kit.Kit;

public class ArcherKit extends Kit {

	public ArcherKit() {
		super("Archer", "You appear with:\n" + "- 1x Bow power 1 and infinity 1\n" + "- 1x Arrow",
				new ItemStack(Material.BOW));
	}

	@Override
	public void givePlayerKit(Player player) {
		ItemStack itemStackBow = new ItemStack(Material.BOW);
		itemStackBow.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
		itemStackBow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
		ItemMeta itemMetaBow = itemStackBow.getItemMeta();
		itemMetaBow.setDisplayName(ChatColor.RED + "Archer");
		itemStackBow.setItemMeta(itemMetaBow);
		player.getInventory().addItem(itemStackBow);
		player.getInventory().addItem(new ItemStack(Material.ARROW));
	}
}
