package fr.mrcubee.survivalgames.kit.list;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.mrcubee.survivalgames.GameStats;
import fr.mrcubee.survivalgames.SurvivalGamesAPI;
import fr.mrcubee.survivalgames.kit.Kit;

public class HulkKit extends Kit {
	
	public static HulkKit generateKit() {
		ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
		skullMeta.setOwner("Incredible_Hulk");
		itemStack.setItemMeta(skullMeta);
		return new HulkKit("Hulk " + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "[WINNER]",
				"When you hit a player\n" + "you will kick him away with Hulk's strength !", itemStack);
	}

	private HulkKit(String name, String description, ItemStack itemStack) {
		super(name, description, itemStack);
	}

	@Override
	public boolean canPlayerTakeKit(Player player) {
		player.sendMessage(ChatColor.GRAY + "-> " + ChatColor.RESET + getName() + ChatColor.GRAY
				+ " kit will be available soon. There are still some problems to fix.");
		return false;
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (SurvivalGamesAPI.getInstance().getGame().getGameStats() != GameStats.DURING)
			return;
		if (e.getDamager() instanceof Player) {
			if ((e.getEntity() instanceof Player) && (!SurvivalGamesAPI.getInstance().getGame().isPvpEnable()))
				return;
			Player attacker = (Player) e.getDamager();
			if (containsPlayer(attacker)) {
				e.getEntity().setVelocity(attacker.getLocation().getDirection().multiply(20F));
			}

		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		if (SurvivalGamesAPI.getInstance().getGame().getGameStats() != GameStats.DURING)
			return;
		if (!containsPlayer(event.getPlayer()))
			return;
		if (!event.getPlayer().hasPotionEffect(PotionEffectType.JUMP))
			event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999, 8), true);
		if (!event.getPlayer().hasPotionEffect(PotionEffectType.SPEED))
			event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1), true);
		if (!event.getPlayer().hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE))
			event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999, 2), true);
		if (event.getPlayer().isSneaking() && (event.getPlayer().getVelocity().getY() > 1)
				&& (event.getPlayer().getLocation().clone().subtract(0, 1, 0).getBlock().getType().isSolid()
						|| event.getPlayer().getLocation().clone().subtract(0, 2, 0).getBlock().getType().isSolid()))
			event.getPlayer().setVelocity(event.getPlayer().getLocation().getDirection().multiply(5F));
	}

}
