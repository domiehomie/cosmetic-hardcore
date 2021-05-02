package live.mufin.hardcore;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements Listener {

	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
	}

	public void onDisable() {

	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		NamespacedKey key = new NamespacedKey(this, "hardcore");
		Player p = event.getPlayer();
		if (p.getPersistentDataContainer().has(key, PersistentDataType.STRING))
			return;
		p.setDisplayName(ChatColor.RED + p.getName() + ChatColor.RESET);
		p.getPersistentDataContainer().set(key, PersistentDataType.STRING, "true");
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		NamespacedKey key = new NamespacedKey(this, "hardcore");
		Player p = event.getPlayer();
		if (!p.getPersistentDataContainer().has(key, PersistentDataType.STRING))
			return;
		if (p.getPersistentDataContainer().get(key, PersistentDataType.STRING).equals("true")) {
			p.setDisplayName(ChatColor.RED + p.getName() + ChatColor.RESET);
		} else {
			p.setDisplayName(p.getName());
		}
		return;
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Bukkit.getLogger().info("Player died!");
		Entity p = event.getEntity();
		NamespacedKey key = new NamespacedKey(this, "hardcore");
		if (p.getType() == EntityType.PLAYER) {
			Player player = (Player) p;
			if (player.getPersistentDataContainer().get(key, PersistentDataType.STRING).equals("false")) {
				return;
			} else {
				Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&8[&4HARDCORE&8] &c"
						+ player.getName() + " &7has just died, losing their Hardcore status. L"));
				player.setDisplayName(player.getName());
				player.getPersistentDataContainer().set(key, PersistentDataType.STRING, "false");
			}
		}

	}
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("cosmetichardcore.sethardcore")) return true;
		if(label.equalsIgnoreCase("sethardcore")) {
			if(args.length != 2) return false;
			
			try {
				Player p = Bukkit.getPlayer(args[0]);
				boolean value = Boolean.parseBoolean(args[1]);
				NamespacedKey key = new NamespacedKey(this, "hardcore");
				p.getPersistentDataContainer().set(key, PersistentDataType.STRING, String.valueOf(value));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&4CH&8] &7Set hardcore value of &c" + p.getName() + "&7 to &c" + value + "&7."));
			} catch (NullPointerException | IllegalArgumentException e) {
				return false;
			}
			
			
		}
		
		
		return true;
	}

}
