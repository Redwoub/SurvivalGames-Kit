package fr.mrcubee.survivalgames.kit.list;

import fr.mrcubee.langlib.Lang;
import fr.mrcubee.survivalgames.GameStats;
import fr.mrcubee.survivalgames.SurvivalGamesAPI;
import fr.mrcubee.survivalgames.kit.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class SpiderMan extends Kit {

    private final ItemStack webLauncherItem;
    private final Map<Player, Long> playerCoolDown;
    private final Set<Projectile> projectiles;

    public SpiderMan() {
        super("SpiderMan", "kit.spiderMan.name", "kit.spiderMan.description", new ItemStack(Material.WEB, 1));
        ItemMeta itemMeta;

        this.webLauncherItem = new ItemStack(Material.WEB, 1);
        itemMeta = this.webLauncherItem.getItemMeta();
        itemMeta.setDisplayName(ChatColor.YELLOW + "WEB LAUNCHER");
        itemMeta.setLore(Arrays.asList());
        this.webLauncherItem.setItemMeta(itemMeta);
        this.playerCoolDown = new HashMap<Player, Long>();
        this.projectiles = new LinkedHashSet<Projectile>();
    }

    @Override
    public boolean canTakeKit(Player player) {
        return true;
    }

    @Override
    public void givePlayerKit(Player player) {
        if (player == null)
            return;
        player.getInventory().addItem(this.webLauncherItem);
    }

    @Override
    public void removePlayerKit(Player player) {
        if (player == null)
            return;
        this.playerCoolDown.remove(player);
        player.getInventory().remove(this.webLauncherItem);
    }

    @Override
    public boolean canLostItem(ItemStack itemStack) {
        return (itemStack == null || !itemStack.isSimilar(this.webLauncherItem));
    }

    @Override
    public String getDisplayName(Player player) {
        if (player == null)
            return null;
        return Lang.getMessage(player, getNameId(), "&cERROR", true);
    }

    @Override
    public String getDescription(Player player) {
        if (player == null)
            return null;
        return Lang.getMessage(player, getDescriptionId(), "&cERROR", true);
    }

    @Override
    public void update() {

    }

    private boolean canLaunch(Player player) {
        Long time;

        if (player == null)
            return false;
        time = this.playerCoolDown.get(player);
        if (time != null && (System.currentTimeMillis() - time) < 10000)
            return false;
        this.playerCoolDown.put(player, System.currentTimeMillis());
        return true;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteract(ProjectileHitEvent event) {
        Location current;

        if (SurvivalGamesAPI.getGame().getGameStats() != GameStats.DURING || event.getEntity().getType() != EntityType.SNOWBALL
        || !this.projectiles.contains(event.getEntity()))
            return;
        this.projectiles.remove(event.getEntity());
        current = event.getEntity().getLocation().subtract(1, 1, 1);
        for (int y = 0; y < 3; y++) {
            for (int z = 0; z < 3; z++) {
                for (int x = 0; x < 3; x++) {
                    if (current.getBlock().getType().equals(Material.AIR))
                        current.getBlock().setType(Material.WEB);
                    current.add(1, 0, 0);
                }
                current.subtract(3, 0, 0).add(0, 0, 1);
            }
            current.subtract(0, 0, 3).add(0, 1, 0);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (SurvivalGamesAPI.getGame().getGameStats() != GameStats.DURING
                || (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) || event.getItem() == null
                || !event.getItem().isSimilar(this.webLauncherItem))
            return;
        event.setCancelled(true);
        if (canLaunch(event.getPlayer()))
            this.projectiles.add(event.getPlayer().launchProjectile(Snowball.class));
    }
}
