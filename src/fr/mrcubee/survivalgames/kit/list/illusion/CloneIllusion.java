package fr.mrcubee.survivalgames.kit.list.illusion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import fr.mrcubee.bukkit.packet.GenericPacketPlayOutEntityDestroy;
import fr.mrcubee.bukkit.packet.GenericPacketPlayOutEntityTeleport;
import fr.mrcubee.bukkit.packet.GenericPacketPlayOutNamedEntitySpawn;

public class CloneIllusion {
	
	private static double VIEW_FIELD = 20.0;
	
	private Player player;
	private Random random;
	private int entity_id;
	
	private boolean locChange;
	private Location lastPlayerLoc;
	private Location loc;
	private List<Player> see;
	
	private boolean blocking;
	
	private GenericPacketPlayOutNamedEntitySpawn packet_spawn;
	private GenericPacketPlayOutEntityDestroy packet_destroy;
	private GenericPacketPlayOutEntityTeleport packet_teleport;
	
	public CloneIllusion(Player player, Random random, int entity_id){
		this.player = player;
		this.random = random;
		this.entity_id = entity_id;
		this.see = new ArrayList<>();
		
		packet_spawn = GenericPacketPlayOutNamedEntitySpawn.create();
		packet_spawn.fillAllFromPlayer(player);
		packet_spawn.setNamedEntityID(this.entity_id);
		
		packet_destroy = GenericPacketPlayOutEntityDestroy.create();
		packet_destroy.setEntityID(this.entity_id);
		
		packet_teleport = GenericPacketPlayOutEntityTeleport.create();
		packet_teleport.setEntityID(this.entity_id);
		packet_teleport.setOnTheGround(false);
	}
	
	public Location getLocation(){
		return this.loc;
	}
	
	public Location makeClone(Location loc){
		this.lastPlayerLoc = this.player.getLocation();
		Location spawn = randomNearLocation(loc);
		if(!player.isOnGround()) spawn = spawn.add(loc.getX()-loc.getBlockX(), loc.getY()-loc.getBlockY(), loc.getZ()-loc.getBlockZ());
		
		float yaw = loc.getYaw();
		float pitch = loc.getPitch();
		
		if(player.isBlocking()){
			this.blocking = true;
			Set<Material> set = new HashSet();
			set.add(Material.AIR);
			set.add(Material.WATER);
			set.add(Material.STATIONARY_WATER);
			set.add(Material.LAVA);
			set.add(Material.STATIONARY_LAVA);
			
			Block blocking = player.getTargetBlock(set, 5);
			Location nearBlocSameType = getNearBlock(spawn, blocking.getType());
			if(nearBlocSameType != null){
				
			}
		}
		
		packet_spawn.setLocationX(spawn.getX());
		packet_spawn.setLocationY(spawn.getY());
		packet_spawn.setLocationZ(spawn.getZ());
		
		return (this.loc = spawn);
	}
	
	public void update(){
		Location playerLoc = player.getLocation();
		if(!playerLoc.equals(this.lastPlayerLoc)){
			double x = this.lastPlayerLoc.getX()-playerLoc.getX();
			double y = this.lastPlayerLoc.getX()-playerLoc.getX();
			double z = this.lastPlayerLoc.getX()-playerLoc.getX();
			
			this.loc = this.loc.add(x, y, z);
			
			packet_spawn.setLocationX(this.loc.getX());
			packet_spawn.setLocationY(this.loc.getY());
			packet_spawn.setLocationZ(this.loc.getZ());
			
			packet_teleport.setLocationX(this.loc.getX());
			packet_teleport.setLocationY(this.loc.getY());
			packet_teleport.setLocationZ(this.loc.getZ());
			this.lastPlayerLoc = player.getLocation();
			this.locChange = true;
		}else this.locChange = false;
		
		if(this.blocking != this.player.isBlocking()){
			this.blocking = !this.blocking;
		}
	}
	
	public void update(Player player){
		if(see.contains(player)){
			if(this.loc.distance(player.getLocation()) > VIEW_FIELD){
				this.packet_destroy.sendPlayer(player);
				this.see.remove(player);
				return;
			}
			if(this.locChange) this.packet_teleport.sendPlayer(player);
			return;
		}
		if(this.loc.distance(player.getLocation()) <= VIEW_FIELD){
			this.packet_spawn.sendPlayer(player);
			this.see.add(player);
		}
	}
	
	private Location getNearBlock(Location loc, Material type){
		Location target;
		for(int x = 0; x < 5; x++){
			for(int y = 0; y < 5; y++){
				for(int z = 0; z < 5; z++){
					if((target = loc.clone().add(x, y, z)).getBlock().getType().equals(type)) return target;
				}
			}
		}
		return null;
	}
	
//	this.packet_head = new PacketPlayOutEntityHeadRotation();
//	reflexion.change(PacketPlayOutEntityHeadRotation.class, this.packet_head);
//	reflexion.set("a", this.id);
//	reflexion.set("b", (byte)MathHelper.d(this.yaw * 256.0F / 360.0F));
//	
//	
//	this.packet_teleport = new PacketPlayOutEntityTeleport(
//			this.id,
//			MathHelper.floor(x * 32.0D), MathHelper.floor(y * 32.0D), MathHelper.floor(z * 32.0D),
//			(byte)(int)(yaw * 256.0F / 360.0F),(byte)(int)(pitch * 256.0F / 360.0F),
//			false);
	
	private Location randomNearLocation(Location loc){
		int offset_x = random.nextInt(5)+2;
		int offset_y = random.nextInt(5)+2;

		for(int i = 1; i < offset_x-1; i++){
			for(int j = 1; j < offset_y-1; j++){
				if(isAir(loc.clone().add(i, 0, j)) && isAir(loc.clone().add(i, 1, j))) continue;
				if(!isAir(loc.clone().add(i-1, 0, j-1))) return loc.clone().add(i-1, 1, j-1);
				if(!isAir(loc.clone().add(i-1, -1, j-1))) return loc.clone().add(i-1, 0, j-1);
				if(!isAir(loc.clone().add(i-1, -2, j-1))) return loc.clone().add(i-1, -1, j-1);
				return loc.clone().add(i-1, 0, j-1);
			}
		}
		return loc.clone().add(offset_x, 0, offset_y);
	}
	
	private boolean isAir(Location loc){
		return loc.getBlock().getType().equals(Material.AIR);
	}

}
