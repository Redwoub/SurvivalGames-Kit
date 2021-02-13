package fr.mrcubee.survivalgames.kit.list;

import fr.mrcubee.survivalgames.kit.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RadinKit extends Kit {

    public RadinKit(){
        super("Radin", new ItemStack(Material.COAL_BLOCK));
    }
    @Override
    public boolean canTakeKit(Player player) {
        return false;
    }

    @Override
    public void givePlayerKit(Player player) {

    }

    @Override
    public void removePlayerKit(Player player) {

    }

    @Override
    public boolean canLostItem(ItemStack itemStack) {
        return false;
    }

    @Override
    public String getDisplayName(Player player) {
        return null;
    }

    @Override
    public String getDescription(Player player) {
        return null;
    }

    @Override
    public void update() {

    }
}
