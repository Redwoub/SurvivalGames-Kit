package fr.mrcubee.survivalgames.kit.list;

import fr.mrcubee.langlib.Lang;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.mrcubee.survivalgames.kit.Kit;

public class ArcherKit extends Kit {

    public static ItemStack[] items;

    public ArcherKit() {
        super("Archer", new ItemStack(Material.BOW));
        ItemMeta itemMeta;

        this.items = new ItemStack[]{
                new ItemStack(Material.BOW)
        };
        this.items[0].addEnchantment(Enchantment.ARROW_DAMAGE, 1);
        this.items[0].addEnchantment(Enchantment.ARROW_INFINITE, 1);
        this.items[0].addEnchantment(Enchantment.DURABILITY, 3);
        itemMeta = this.items[0].getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "Archer");
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
        player.getInventory().addItem(new ItemStack(Material.ARROW));
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
        return Lang.getMessage(player, "kit.archer.name", "&cERROR", true);
    }

    @Override
    public String getDescription(Player player) {
        if (player == null)
            return null;
        return Lang.getMessage(player, "kit.archer.description", "&cERROR", true);
    }

    @Override
    public void update() {

    }
}
