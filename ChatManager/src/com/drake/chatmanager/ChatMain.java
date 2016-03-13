package com.drake.chatmanager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;


public class ChatMain
extends JavaPlugin
implements Listener {
    boolean isLock = false;

    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents (this, this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("chat")){
            if (args[0].equalsIgnoreCase("lock")) {
                if (!p.hasPermission("chatmanager.chat.lock")){p.sendMessage(ChatColor.RED + "Tu n'as pas la permission");}
                if (p.hasPermission("chatmanager.chat.lock")) {
                    this.isLock = true;
            		Bukkit.broadcastMessage(ChatUtils.prefixChat() + "Le chat vient d'être bloqué par " + ChatColor.DARK_RED + sender.getName());
                }
            } else if (args[0].equalsIgnoreCase("unlock")) {
                if (!p.hasPermission("chatmanager.chat.unlock")){p.sendMessage(ChatColor.RED + "Tu n'as pas la permission");}
                if (p.hasPermission("chatmanager.chat.unlock")) {
                    this.isLock = false;
            		Bukkit.broadcastMessage(ChatUtils.prefixChat() + ChatColor.GREEN + "Le chat vient d'être débloqué par " + ChatColor.DARK_RED + sender.getName());
            		}
            } else if (args[0].equalsIgnoreCase("clear")) {
                if (!p.hasPermission("chatmanager.chat.clear")){ p.sendMessage(ChatColor.RED + "Tu n'as pas la permission");}
                if (p.hasPermission("chatmanager.chat.clear")) {
                    int i = 0;
                    while (i != 100) {
                        Bukkit.broadcastMessage("");
                        ++i;
                    }
            		Bukkit.broadcastMessage(ChatUtils.prefixChat() + "Le chat vient d'être clear par " + ChatColor.DARK_RED + sender.getName());
                }
            }else if(args[0].equalsIgnoreCase("help")){
            	if(!p.hasPermission("chatmanager.chat.help")){p.sendMessage(ChatColor.RED + "Tu n'as pas la permission");}
            	if(p.hasPermission("chatmanager.chat.help")){
            		p.sendMessage("");
            		p.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + "ChatManager-Commands" + ChatColor.GRAY + "]");
            		p.sendMessage("");
            		p.sendMessage(ChatColor.RED + " /chat lock " + ChatColor.GRAY + ": Bloque le chat");
            		p.sendMessage(ChatColor.RED + " /chat unlock " + ChatColor.GRAY + ": Débloque le chat");
            		p.sendMessage(ChatColor.RED + " /chat clear " + ChatColor.GRAY + ": Clear le chat");
            		p.sendMessage("");
            	}
            }	
            
        }
        return false;
    }

    @EventHandler
    public void PlayerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (this.isLock && ((!p.hasPermission("chatmanager.chat.lock.bypass")))){
            e.setCancelled(true);
            p.sendMessage(ChatColor.RED + "Vous n'avez pas le droit de parler dans le chat.");
        }else if(this.isLock && p.isOp()){
        	e.setCancelled(false);
        }else if(this.isLock && p.hasPermission("chatmanager.chat.lock.bypass")){
        	e.setCancelled(false);
        }
    }
}

