package fr.mrcubee.survivalgames.kit.list;

import fr.mrcubee.sign.gui.SignGUi;
import fr.mrcubee.survivalgames.GameStats;
import fr.mrcubee.survivalgames.SurvivalGamesAPI;
import fr.mrcubee.survivalgames.kit.Kit;
import fr.mrcubee.survivalgames.kit.SurvivalGamesKit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class DeathNote extends Kit {

    private static final String MESSAGE_PREFIX = ChatColor.LIGHT_PURPLE + "[DEATH NOTE] " + ChatColor.GREEN;

    private final SignGUi signGUi;
    private final ItemStack deathNoteItem;

    protected DeathNote() {
        super("DeathNote", "Tuez le joueur que vous voulez.", new ItemStack(Material.PAPER, 1));
        ItemMeta itemMeta;

        this.deathNoteItem = new ItemStack(Material.PAPER, 1);
        itemMeta = this.deathNoteItem.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "DEATH NOTE");
        itemMeta.setLore(Arrays.asList());
        this.deathNoteItem.setItemMeta(itemMeta);
        this.signGUi = SignGUi.create(SurvivalGamesKit.getInstance());
    }

    @Override
    public boolean canTakeKit(Player player) {
        if (this.signGUi == null) {
            player.sendMessage(ChatColor.RED + "[ERROR] SignGUI not loaded.");
            return false;
        }
        return true;
    }

    @Override
    public void givePlayerKit(Player player) {
        if (player == null)
            return;
        player.getInventory().addItem(this.deathNoteItem);
    }

    @Override
    public void removePlayerKit(Player player) {
        if (player == null)
            return;
        player.getInventory().remove(this.deathNoteItem);
    }

    @Override
    public boolean canLostItem(ItemStack itemStack) {
        return (itemStack == null || !itemStack.isSimilar(this.deathNoteItem));
    }

    @Override
    public void update() {

    }

    private void executeDeathNote(Player player, String[] lines) {
        Player target;
        StringBuilder nameBuilder;
        String name;

        if (player == null || lines == null)
            return;
        nameBuilder = new StringBuilder();
        nameBuilder.append(lines[0]);
        nameBuilder.append(lines[1]);
        name = nameBuilder.toString();
        target = Bukkit.getPlayer(name);
        if (target == null || SurvivalGamesAPI.getGame().isSpectator(player)) {
            player.sendMessage(DeathNote.MESSAGE_PREFIX + "Player " + name + " does not exists.");
            return;
        }
        player.getInventory().remove(this.deathNoteItem);
        target.setHealth(0);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (SurvivalGamesAPI.getGame().getGameStats() != GameStats.DURING
        || event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getItem() == null
        || !event.getItem().isSimilar(this.deathNoteItem))
            return;
        this.signGUi.open(event.getPlayer(), lines -> executeDeathNote(event.getPlayer(), lines),
                "",
                "",
                "^^^",
                "Player Name");
    }
}
