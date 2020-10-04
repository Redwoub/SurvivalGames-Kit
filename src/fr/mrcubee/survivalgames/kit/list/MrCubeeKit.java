package fr.mrcubee.survivalgames.kit.list;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import fr.mrcubee.survivalgames.kit.Kit;

public class MrCubeeKit extends Kit {

	public static MrCubeeKit generateKit() {
		ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
		skullMeta.setOwner("MrCubee");
		itemStack.setItemMeta(skullMeta);
		return new MrCubeeKit("MrCubee", "This head is the one of the person\n" + "who develop this plugin.\n" + "\n"
				+ "This kit changes frequently.\n" + "Because it is an experimental kit\n" + "to test future kits.",
				itemStack);
	}

	private MrCubeeKit(String name, String description, ItemStack itemStack) {
		super(name, description, itemStack);
	}
}
