package fr.mrcubee.survivalgames.kit.list;

import fr.mrcubee.survivalgames.SurvivalGamesAPI;
import fr.mrcubee.survivalgames.api.event.PlayerRadarEvent;
import fr.mrcubee.survivalgames.kit.Kit;
import fr.mrcubee.survivalgames.kit.KitManager;
import fr.mrcubee.survivalgames.listeners.player.PlayerInteract;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class FakeRadarKit extends Kit {

	private HashMap<Player, Location> players;

	public FakeRadarKit() {
		super("FakeRadar", "You can misplace your position on the radar.",
				new ItemStack(Material.COMPASS));
		this.players = new HashMap<Player, Location>();
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
		return true;
	}

	@Override
	public void update() {

	}

	@EventHandler
	public void playerInteract(PlayerInteractEvent event) {
		KitManager kitManager = SurvivalGamesAPI.getGame().getKitManager();

		if (event.getAction() != Action.LEFT_CLICK_BLOCK
		|| event.getItem() == null || !event.getItem().isSimilar(kitManager.getRadarItem()) || !containsPlayer(event.getPlayer()))
			return;
		this.players.put(event.getPlayer(), event.getClickedBlock().getLocation());
		event.getPlayer().sendMessage(ChatColor.GRAY + "You set your new position on the players' radar.");
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void playerMove(PlayerMoveEvent event) {
		Location target;

		if (event.isCancelled())
			return;
		target = this.players.get(event.getPlayer());
		if (target == null || target.distance(event.getTo()) <= 20)
			return;
		this.players.remove(event.getPlayer());
		event.getPlayer().sendMessage(ChatColor.GRAY + "Your false location has been deleted because you are too far away.");
	}

	@EventHandler
	public void playerRadarEvent(PlayerRadarEvent event) {
		event.setTargetLocation(this.players.get(event.getTarget()));
	}

}
