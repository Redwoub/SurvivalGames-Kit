package fr.mrcubee.survivalgames.kit.list;

import fr.mrcubee.langlib.Lang;
import fr.mrcubee.survivalgames.SurvivalGames;
import fr.mrcubee.survivalgames.kit.Kit;

import fr.mrcubee.survivalgames.kit.KitManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;


public class RadinKit extends Kit implements Listener {
    private KitManager kits = new KitManager(SurvivalGames.getPlugin(SurvivalGames.class));
    
    public RadinKit(){
        super("Radin", new ItemStack(Material.COAL_BLOCK));
    }

    @Override
    public boolean canTakeKit(Player player) {
        return true;
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
        if(player == null) return null;

        return Lang.getMessage(player, "kit.radin.name", "§cERROR", true);
    }

    @Override
    public String getDescription(Player player) {
        if(player == null) return null;

        return Lang.getMessage(player, "kit.radin.description", "§cERROR", true);
    }

    @Override
    public void update() {

    }
    private ItemStack radar;

    public void ClearKiller(Player killer){
        Kit[] kit = kits.getKitByPlayer(killer);
        for(int i = 0; i < 44; i++){
            if(killer.getInventory().getItem(i) == null) return;
            if(!killer.getInventory().getItem(i).hasItemMeta()) return;
            if(killer.getInventory().getItem(i).getItemMeta().getDisplayName().equals("§cRADAR")){
                radar = killer.getInventory().getItem(i);
                killer.getInventory().clear();
            }
        }

        switch (kit[0].getName()){
            case "Archer":
                killer.getInventory().addItem(ArcherKit.items[0]);
                break;
            case "Miner":
                killer.getInventory().addItem(MinerKit.items[0]);
                break;
            case "SpiderMan":
                killer.getInventory().addItem(SpiderMan.webLauncherItem);
            default: break;
        }
        killer.getInventory().addItem(radar);
        killer.sendMessage(Lang.getMessage(killer, "kit.radin.clearkiller", "§cERROR", true));
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity().getPlayer();
        Player killer = event.getEntity().getKiller();

        player.getInventory().clear();
        ClearKiller(killer);
    }

}
