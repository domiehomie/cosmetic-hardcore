package live.mufin.hardcore;

import org.bukkit.Bukkit;
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
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements Listener {

	public PlayerDataManager data;

	public void onEnable() {
		this.data = new PlayerDataManager(this);
		this.getServer().getPluginManager().registerEvents(this, this);
	}

	public void onDisable() {

	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {

		Player p = event.getPlayer();
		Bukkit.getLogger().info("Creating player: " + p.getName());

		if (data.getConfig().contains("players." + p.getUniqueId().toString()))
			return;
		data.getConfig().set("players." + p.getUniqueId().toString() + ".hardcore", true);
		data.getConfig().set("players." + p.getUniqueId().toString() + ".playername", p.getName());
		p.setDisplayName(ChatColor.RED + p.getName() + ChatColor.RESET);
		data.saveConfig();
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player p = event.getPlayer();
		if (data.getConfig().getBoolean("players." + p.getUniqueId().toString() + ".hardcore") == true) {
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
		if (p.getType() == EntityType.PLAYER) {
			Player player = (Player) p;
			if (data.getConfig().getBoolean("players." + p.getUniqueId().toString() + ".hardcore") == false) {
				return;
			} else {
				Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&8[&4HARDCORE&8] &c"
						+ player.getName() + " &7has just died, losing their Hardcore status. L"));
				player.setDisplayName(player.getName());
				data.getConfig().set("players." + p.getUniqueId().toString() + ".hardcore", false);
				data.saveConfig();
			}
		}

	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("hardcorereload") || label.equalsIgnoreCase("hcreload")
				|| label.equalsIgnoreCase("hcrl")) {
			this.data.reloadConfig();
			sender.sendMessage(
					ChatColor.translateAlternateColorCodes('&', "&8[&4HARDCORE&8] &7Reloaded &cplayerdata.yml"));

		}

		return true;
	}

}
