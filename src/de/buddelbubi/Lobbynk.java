package de.buddelbubi;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandExecutor;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.Skin;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityExplodeEvent;
import cn.nukkit.event.inventory.InventoryClickEvent;
import cn.nukkit.event.level.LevelLoadEvent;
import cn.nukkit.event.level.WeatherChangeEvent;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerCommandPreprocessEvent;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.event.player.PlayerDropItemEvent;
import cn.nukkit.event.player.PlayerFoodLevelChangeEvent;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerItemHeldEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.event.player.PlayerPreLoginEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.event.player.PlayerRespawnEvent;
import cn.nukkit.event.player.PlayerToggleFlightEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.level.ParticleEffect;
import cn.nukkit.level.Sound;
import cn.nukkit.level.particle.AngryVillagerParticle;
import cn.nukkit.level.particle.BoneMealParticle;
import cn.nukkit.level.particle.BubbleParticle;
import cn.nukkit.level.particle.EnchantmentTableParticle;
import cn.nukkit.level.particle.ExplodeParticle;
import cn.nukkit.level.particle.FlameParticle;
import cn.nukkit.level.particle.HeartParticle;
import cn.nukkit.level.particle.InkParticle;
import cn.nukkit.level.particle.Particle;
import cn.nukkit.level.particle.RedstoneParticle;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.ScriptCustomEventPacket;
import cn.nukkit.network.protocol.TransferPacket;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.DummyBossBar;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.buddelbubi.api.ConfigNK;
import de.buddelbubi.api.MultiLobbyManager;
import de.buddelbubi.api.NoteParticle;
import de.buddelbubi.api.ScoreboardManager;
import de.buddelbubi.api.SyncLobby;
import de.buddelbubi.api.player;
import de.buddelbubi.chatystem.clearchatcommand;
import de.buddelbubi.events.PetEvents;
import de.buddelbubi.events.onInteract;
import de.buddelbubi.events.onJoin;
import de.buddelbubi.events.onTeleport;
import de.buddelbubi.implementedcode.network.Metrics;
import de.buddelbubi.setup.ConfigGenerator;
import de.buddelbubi.setup.MainSetup;
import java.io.File;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Lobbynk
extends PluginBase
implements CommandExecutor,
Listener {
    int weather;
    HashMap<String, Inventory> inv = new HashMap();
    public static ConfigNK config = new ConfigNK();
    public static ConfigNK images = new ConfigNK();
    public static ConfigNK lang = new ConfigNK();
    public static ConfigNK compasscommands = new ConfigNK();
    public static Config loc = new Config();
    public static ConfigNK hotbar = new ConfigNK();
    public static ConfigNK chat = new ConfigNK();
    public static ConfigNK saves = new ConfigNK();
    public static ConfigNK level = new ConfigNK();
    public static ConfigNK forcefield = new ConfigNK();
    public static ConfigNK delaydata = new ConfigNK();
    public static ConfigNK scoreboard = new ConfigNK();
    public static ConfigNK playersettings = new ConfigNK();
    public static ConfigNK walking = new ConfigNK();
    public static HashSet<String> flyable = new HashSet();
    public static Map<String, String> map = new HashMap<String, String>();
    public static HashSet<Player> dev = new HashSet();
    public static HashSet<String> langs = new HashSet();

    public void loadConfigs() {
        try {
            try {
                config = new ConfigNK(new Config(new File(this.getDataFolder(), "config.yml")).getAll());
            }
            catch (Exception e) {
                this.getServer().getLogger().critical("\u00a74ORRUPTED CONFIGURATION FILE: config.yml");
            }
            try {
                images = new ConfigNK(new Config(new File(this.getDataFolder(), "images.yml")).getAll());
            }
            catch (Exception e) {
                this.getServer().getLogger().critical("\u00a74ORRUPTED CONFIGURATION FILE: images.yml");
            }
            this.loadLang();
            try {
                compasscommands = new ConfigNK(new Config(new File(this.getDataFolder(), "compasscommands.yml")).getAll());
            }
            catch (Exception e) {
                this.getServer().getLogger().critical("\u00a74ORRUPTED CONFIGURATION FILE: compasscommands.yml");
            }
            try {
                loc = new Config(new File(this.getDataFolder(), "locations.yml"));
            }
            catch (Exception e) {
                this.getServer().getLogger().critical("\u00a74ORRUPTED CONFIGURATION FILE: locations.yml");
            }
            try {
                hotbar = new ConfigNK(new Config(new File(this.getDataFolder(), "hotbar.yml")).getAll());
            }
            catch (Exception e) {
                this.getServer().getLogger().critical("\u00a74ORRUPTED CONFIGURATION FILE: hotbar.yml");
            }
            try {
                chat = new ConfigNK(new Config(new File(this.getDataFolder(), "chat.yml")).getAll());
            }
            catch (Exception e) {
                this.getServer().getLogger().critical("\u00a74ORRUPTED CONFIGURATION FILE: chat.yml");
            }
            saves = new ConfigNK();
            delaydata = new ConfigNK();
        }
        catch (Exception e) {
            e.printStackTrace();
            Server.getInstance().getLogger().info("Please send this error code in the LobbyNK discussion tab. (Not reviews) - https://cloudburstmc.org/threads/lobbynk.623/\nDiscord would work as well: Buddelbubi#5018");
            Server.getInstance().getLogger().error("One of your LobbyNK Config Files is orrupted!!! Please reset your config files.");
            System.exit(1);
        }
    }

    public void loadLang() {
        File path = new File(this.getDataFolder() + "/lang");
        for (File file : path.listFiles()) {
            Config c = new Config(file);
            String langcode = c.getString("languagecode");
            langs.add(langcode);
            for (String st : c.getKeys()) {
                lang.set(String.valueOf(langcode) + "_" + st, c.getString(st).replace("&", "\u00a7"));
            }
        }
    }

    @EventHandler
    public void preLogin(PlayerPreLoginEvent e) {
        String[] lang = e.getPlayer().getLoginChainData().getLanguageCode().split("_");
        if (langs.contains(lang[0]) && config.getBoolean("multilanguage")) {
            e.getPlayer().setLocale(new Locale(lang[0]));
        } else {
            e.getPlayer().setLocale(new Locale(new Config(new File(this.getDataFolder() + "/lang", String.valueOf(config.getString("language")) + ".yml")).getString("languagecode")));
        }
    }

    public void onEnable() {
        Metrics metrics = new Metrics((Plugin)this, 6598);
        this.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new onInteract(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new onTeleport(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new onJoin(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new PetEvents(), (Plugin)this);
        ConfigGenerator.genConfig();
        this.loadConfigs();
        MultiLobbyManager.registerAllLobbyworlds();
        MultiLobbyManager.registerAllLobbyservers();
        if (config.getBoolean("multiworldSaveInventory") && config.getBoolean("multiworld")) {
            for (Player p : this.getServer().getOnlinePlayers().values()) {
                if (!MultiLobbyManager.lobbyworlds.contains(p.getLevel().getName())) continue;
                onTeleport.invs.put(p.getName(), p.getInventory().getContents());
                onTeleport.xp.put(p.getName(), p.getExperience());
                onTeleport.level.put(p.getName(), p.getExperienceLevel());
                player.giveHotbar(p);
            }
        }
        clearchatcommand cc = new clearchatcommand("clearchat");
        String[] ccaliases = new String[]{"cc", "cchat"};
        cc.setDescription("Clears the chat");
        cc.setAliases(ccaliases);
        if (config.getBoolean("Chatsystem")) {
            this.getServer().getCommandMap().register(cc.getName(), (Command)cc);
        }
        String ANSI_RESET = "\u001b[0m";
        String red = "\u001b[31m";
        System.out.println(String.valueOf(red) + "  _           _     _           _   _ _  __\r\n" + red + "   | |         | |   | |         | \\ | | |/ /\r\n" + red + "   | |     ___ | |__ | |__  _   _|  \\| | ' / \r\n" + red + "   | |    / _ \\| '_ \\| '_ \\| | | | . ` |  <  \r\n" + red + "   | |___| (_) | |_) | |_) | |_| | |\\  | . \\ \r\n" + red + "   |______\\___/|_.__/|_.__/ \\__, |_| \\_|_|\\_\\\r\n" + red + "                             __/ |           \r\n" + red + "    by Buddelbubi           |___/            ");
        this.getServer().setPropertyString("allow-flight", "on");
        for (Level l : this.getServer().getLevels().values()) {
            if (!MultiLobbyManager.lobbyworlds.contains(l.getName()) || !config.getBoolean("timelock")) continue;
            l.setTime(config.getInt("time"));
            l.stopTime();
            if (!config.getBoolean("disableWeather")) continue;
            this.weather = 1;
            l.setRaining(false);
            l.setThundering(false);
            this.weather = 0;
        }
        if (!Lobbynk.versionCheck().contains(this.getServer().getPluginManager().getPlugin("LobbyNK").getDescription().getVersion())) {
            System.out.println("\u001b[35mA new version of LobbyNK is available: https://nukkitx.com/resources/lobbynk.380/" + ANSI_RESET);
        }
    }

	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		
		if(arg0 instanceof Player) {
		Player p = (Player) arg0;
	

		switch(arg1.getName()) {
		case "build":
			if(arg0 instanceof Player) {
				if(config.getBoolean("spawnprotection")) {
				if(!config.getBoolean("multiworld") || MultiLobbyManager.lobbyworlds.contains(p.getLevel().getName()) || MultiLobbyManager.lobbyworlds.isEmpty()) {
				if(arg3.length == 0) {
					if(p.hasPermission("lobbynk.build.own")) {
						if(saves.get(p.getName() +".build")== null) {
							saves.set(p.getName()+ ".build", true);
							saves.save();
							p.getInventory().clearAll();
							p.setGamemode(1);
					
							
							p.sendMessage(lang.getString(p.getLocale().toString() + "_" +"Build.true.own"));
						} else {
							saves.set(p.getName() + ".build", null);
							saves.save();
							p.sendMessage(lang.getString(p.getLocale().toString() + "_" + "Build.false.own"));
							p.setGamemode(config.getInt("Gamemode"));
							p.getInventory().clearAll();
							de.buddelbubi.api.player.giveHotbar(p);
						}
					} else {
						p.sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
					}
				} else if(arg3.length == 1) {
					if(p.hasPermission("lobbynk.build.other")) {
						Player a = getServer().getPlayer(arg3[0]);
						if(a.isOnline()) {
							if(saves.get(a.getName() + ".build") == null) {
								saves.set(a.getName() + ".build", true);
								a.sendMessage(lang.getString(p.getLocale().toString() + "_" +p.getLocale().toString() + "_" +"Build.true.own"));
								p.sendMessage(lang.getString(p.getLocale().toString() + "_" +"Build.true.other").replace("%p", p.getName()));
								a.getInventory().clearAll();
								a.setGamemode(1);
							} else {
								saves.set(a.getName() + ".build", null);
								a.sendMessage(lang.getString(p.getLocale().toString() + "_" +"Build.false.own"));
								p.sendMessage(lang.getString(p.getLocale().toString() + "_" +"Build.false.other").replaceAll("%p", p.getName()));
								a.setGamemode(config.getInt("Gamemode"));
								a.getInventory().clearAll();
								de.buddelbubi.api.player.giveHotbar(a);
							}
							
						} else {
							p.sendMessage(lang.getString(p.getLocale().toString() + "_" +"Playeroffline"));
						}
					}
				}
				} else p.sendMessage(lang.getString(p.getLocale().toString() + "_" +"Help"));
			} else p.sendMessage("§cLobbyNK > This command isn't necessary because there is no spawnprotection");
			} else {
				if(lang.get("Playeronly")!= null) {
					arg0.sendMessage(lang.getString(p.getLocale().toString() + "_" +"Playeronly"));
				} else {
					arg0.sendMessage("§cThis message was not found in your languagefile");
				}
			}
		} switch(arg1.getName()) {
		case "lobby":

		
				
			if(arg3.length ==2) {
				
				
				if(arg3[0].equalsIgnoreCase("set")) {
					if(arg0 instanceof Player) {
					if(p.hasPermission("lobbynk.lobby.set")) {
					String world = "";
					if(config.getBoolean("multilobby")) world =  "." + p.getLevel().getName();
					loc = new Config(new File(this.getDataFolder(), "locations.yml"));
					if(arg3[1].contains("loc")) {
						loc.set(arg3[1] + ".x" + world, p.getLocation().getX());
						loc.set(arg3[1] + ".y" + world, p.getLocation().getY());
						loc.set(arg3[1] + ".z" + world, p.getLocation().getZ());
						loc.set(arg3[1] + ".yaw" + world, p.getLocation().getYaw());
						loc.set(arg3[1] + ".pitch" + world, p.getLocation().getPitch());
						loc.set(arg3[1] + ".world" + world, p.getLevel().getName());
						loc.save();
						p.sendMessage(lang.getString(p.getLocale().toString() + "_" +"Locations.Set").replace("%loc", arg3[1]));
					
					} else if(arg3[1].equalsIgnoreCase("spawn")){
						
		
						loc.set("spawn.x" +world, p.getLocation().getX());
						loc.set("spawn.y" +world, p.getLocation().getY());
						loc.set("spawn.z" +world, p.getLocation().getZ());
						loc.set("spawn.yaw"+world, p.getLocation().getYaw());
						loc.set("spawn.pitch"+world, p.getLocation().getPitch());
						loc.set("spawn.world"+world, p.getLevel().getName());
						loc.save();
						p.sendMessage(lang.getString(p.getLocale().toString() + "_" +"spawn.set"));
						de.buddelbubi.api.MultiLobbyManager.lobbyworlds.add(p.getLevel().getName());
					} else {
						p.sendMessage(lang.getString(p.getLocale().toString() + "_" +"Help"));
					}
					loc.save();
					loadConfigs();
					} else p.sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
					} else arg0.sendMessage(lang.getString(p.getLocale().toString() + "_" +"Playeronly"));
					
				}else if(arg3[0].equalsIgnoreCase("gen")){
					if(p.hasPermission("lobbynk.generate")) {
							
				@SuppressWarnings("deprecation")
				Config c = new Config(new File(this.getDataFolder(), "compasscommands.yml"), Config.YAML, new LinkedHashMap<String, Object>() {{
					for(int i =1; i != Integer.parseInt(arg3[1])+1; i++) {	
			            put("Slot" + i + ".command", "none");
			            put("Slot" + i + ".disableloc", false);
					}
			        }
			});
				@SuppressWarnings("deprecation")
				Config c2 = new Config(new File(this.getDataFolder(), "images.yml"), Config.YAML, new LinkedHashMap<String, Object>() {{
					for(int i =1; i != Integer.parseInt(arg3[1])+1; i++) {	
			            put("Slot" + i, "textures/items/diamond.png");
			            put("Slot" + i + ".mode", "path");
					}
			        }
			});
			
				
				for(File f : new File(this.getDataFolder() + "/lang").listFiles()) {
					@SuppressWarnings("deprecation")
					Config c3 = new Config(f, Config.YAML, new LinkedHashMap<String, Object>() {{
						for(int i =1; i != Integer.parseInt(arg3[1])+1; i++) {	
				            put("Compass.Slot" + i, "§eSlot"+i);
				           
						}
				        }
				});
				}
				
				Config con = new Config(new File(this.getDataFolder(), "config.yml"));
						con.set("Gamesamount", Integer.parseInt(arg3[1]));
						con.save();
						loadConfigs();
				p.sendMessage("§aGenerated §e" + arg3[1] + " §aSlots.");
				
						
					}  else{
						p.sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
					}
				
				} else {
					p.sendMessage(lang.getString(p.getLocale().toString() + "_" +"Help"));
				}
			} else if(arg3.length ==1) {
				if(arg3[0].equalsIgnoreCase("reload")) {
				if(arg0 instanceof ConsoleCommandSender || p.hasPermission("lobbynk.reload")) {
					loadConfigs();
					arg0.sendMessage("§aLobbyNK RELOADED");
				} else arg0.sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
			}else if(arg3[0].equalsIgnoreCase("setup")){
				MainSetup.startSetup(p);
				
			} else if(arg3[0].equalsIgnoreCase("devmode")) {
				
				p.sendMessage("§cLobbyNK -> You're now in Devmode!");
				// Devmode does nothing bad, It is just for some testings. It doesn't give permissions to grief or sth like that. If you want, check the plugin
				// At the moment, it does nothing.
				dev.add(p);
				
				
				
			} else if(arg3[0].equals("wing")){
				
				Vector3 behind = p.getDirectionVector().multiply(-0.5);
				behind.y = p.getY() +1;
				p.getLevel().addParticleEffect(behind, ParticleEffect.BASIC_FLAME);
			       
				
				
			} else {
				arg0.sendMessage(lang.getString(p.getLocale().toString() + "_" +"Help"));
			}
				} else if(arg3.length == 3) {
					if(arg3[0].equalsIgnoreCase("sync")) {
						
						if(p.hasPermission("lobbynk.sync")) {
							if(!arg3[2].equals("all")) {
						try {
							de.buddelbubi.api.SyncLobby.sync(arg3[1], arg3[2]);
							p.sendMessage(lang.getString(p.getLocale().toString() + "_" +"sync.success").replace("%copied", arg3[1]).replace("%overwritten", arg3[2]));
						} catch (Exception e) {
							p.sendMessage(lang.getString(p.getLocale().toString() + "_" +"sync.failed"));
						}
							} else {
								try {
									for(String s : de.buddelbubi.api.MultiLobbyManager.lobbyworlds) {
									de.buddelbubi.api.SyncLobby.sync(arg3[1], s);
									
									}
									p.sendMessage(lang.getString(p.getLocale().toString() + "_" +"sync.success").replace("%copied", arg3[1]).replace("%overwritten", arg3[2]));
								} catch (Exception e) {
									p.sendMessage(lang.getString(p.getLocale().toString() + "_" +"sync.failed"));
								}
							}
						} else p.sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
					} else if(arg3[0].equalsIgnoreCase("petsize")) {
						//only for developer purposes. Dont scale your pet to high. It could crash your clients
						
						PetEvents.map.get(p).setScale(Float.parseFloat(arg3[1]));
						
					}else if(arg3[0].equalsIgnoreCase("addlobbyserver")){
						
						if(p.hasPermission("lobbynk.addlobbyserver")) {
							
						Config loc = new Config(new File(this.getDataFolder(), "locations.yml"));
							
							loc.set("server." + arg3[1],arg3[2]);
							loc.save();
							MultiLobbyManager.lobbyservers.put("server." + arg3[1], arg3[2]);
							p.sendMessage(lang.getString(p.getLocale().toString() + "_" +"serveradded").replace("%servername", arg3[1]).replace("%address", arg3[2]));
							
						
							
						}else p.sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
						
						
					} else p.sendMessage(lang.getString(p.getLocale().toString() + "_" +"Help"));
				} else if(arg3.length == 0) {
					
					player.toSpawn((Player)arg0);
					
				} else p.sendMessage(lang.getString(p.getLocale().toString() + "_" +"Help"));
		} switch(arg1.getName()) {
		case "getdata":
			
			ScriptCustomEventPacket packet = new ScriptCustomEventPacket();
			packet.eventName = "bungeecord:main";
			ByteArrayDataOutput out = ByteStreams.newDataOutput();
			out.writeUTF("Connect");
			out.writeUTF("test");
			packet.eventData = out.toByteArray();
			((Player) arg0).dataPacket(packet);
		
		}
			
		switch(arg1.getName()) {
		case "spawn":
			player.toSpawn((Player)arg0);
			
		}
			} else if(arg0 instanceof ConsoleCommandSender) {
				if(arg1.getName().equalsIgnoreCase("lobby")) {
					if(arg3[0].equalsIgnoreCase("reload")) {
						loadConfigs();
						getServer().getLogger().info("§eLobbyNK reloaded successfully");
					}
					
				}
			}
		return false;
	}
	

    @EventHandler
    public void onLevelLoad(LevelLoadEvent e) {
        if (MultiLobbyManager.lobbyworlds.contains(e.getLevel().getName()) && config.getBoolean("timelock")) {
            e.getLevel().setTime(config.getInt("time"));
            e.getLevel().stopTime();
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if ((!config.getBoolean("multiworld") || MultiLobbyManager.lobbyworlds.contains(e.getPlayer().getLevel().getName())) && config.getBoolean("spawnprotection") && saves.get(String.valueOf(e.getPlayer().getName()) + ".build") == null) {
            e.setCancelled(true);
            Player p = e.getPlayer();
            if (lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Build.notallowed").isEmpty()) {
                return;
            }
            e.getPlayer().sendMessage(lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Build.notallowed"));
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if ((!config.getBoolean("multiworld") || MultiLobbyManager.lobbyworlds.contains(e.getPlayer().getLevel().getName())) && config.getBoolean("spawnprotection") && saves.get(String.valueOf(e.getPlayer().getName()) + ".build") == null) {
            e.setCancelled(true);
            Player p = e.getPlayer();
            if (lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Build.notallowed").isEmpty()) {
                return;
            }
            e.getPlayer().sendMessage(lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Build.notallowed"));
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        if (!config.getBoolean("multiworld") || MultiLobbyManager.lobbyworlds.contains(e.getPlayer().getLevel().getName())) {
            e.getPlayer().setGamemode(config.getInt("Gamemode"));
            this.onCmd(new PlayerCommandPreprocessEvent(e.getPlayer(), "URL"));
            if (config.getBoolean("scoreboard")) {
                // empty if block
            }
            if (p.isOp() && !Lobbynk.versionCheck().contains(this.getServer().getPluginManager().getPlugin("LobbyNK").getDescription().getVersion())) {
                p.sendMessage("\u00a7cA new version of LobbyNK is available: \u00a7$https://nukkitx.com/resources/lobbynk.380/");
            }
            this.getServer().getScheduler().scheduleDelayedTask((Plugin)this, new Runnable(){

                @Override
                public void run() {
                    if (!Lobbynk.versionCheck().contains(Lobbynk.this.getServer().getPluginManager().getPlugin("LobbyNK").getDescription().getVersion()) && p.isOp()) {
                        p.sendTitle("\u00a7cLobbyNK got an update", "\u00a7cPlease download and install the update!");
                    } else if (config.getBoolean("Jointitle")) {
                        p.sendTitle(lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Jointitle1").replace("%p", p.getName()), lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Jointitle2").replace("%p", p.getName()));
                        for (DummyBossBar b : p.getDummyBossBars().values()) {
                            b.destroy();
                        }
                        if (config.getBoolean("Bossbar")) {
                            p.createBossBar(lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Bossbar").replace("%p", p.getName()), config.getInt("bossbarpoints"));
                        }
                    }
                }
            }, 100);
        }
    }

    @EventHandler
    public void on(PlayerJoinEvent e) {
        block4: {
            Player p = e.getPlayer();
            if (!config.getBoolean("Joinquitmessage")) break block4;
            e.setJoinMessage("");
            if (p.hasPlayedBefore()) {
                for (Player player2 : this.getServer().getOnlinePlayers().values()) {
                    player2.sendMessage(lang.getString(String.valueOf(player2.getLocale().toString()) + "_" + "Joinprefix").replace("%p", e.getPlayer().getName()));
                }
            } else {
                saves.set(String.valueOf(e.getPlayer().getName()) + ".nick", e.getPlayer().getName());
                saves.save();
                for (Player player3 : this.getServer().getOnlinePlayers().values()) {
                    player3.sendMessage(lang.getString(String.valueOf(player3.getLocale().toString()) + "_" + "FirstjoinMessage").replace("%p", e.getPlayer().getName()));
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        ScoreboardManager.deleteScoreboard(e.getPlayer());
        if (saves.has(String.valueOf(e.getPlayer().getName()) + ".build")) {
            saves.remove(String.valueOf(e.getPlayer().getName()) + ".build");
        }
        if (config.getBoolean("Joinquitmessage")) {
            Player p = e.getPlayer();
            e.setQuitMessage(" ");
            for (Player player2 : this.getServer().getOnlinePlayers().values()) {
                player2.sendMessage(lang.getString(String.valueOf(player2.getLocale().toString()) + "_" + "Quitprefix").replace("%p", e.getPlayer().getName()));
            }
        }
    }

    @EventHandler
    public void onLogin(PlayerPreLoginEvent e) {
        if (MultiLobbyManager.getLobby().equals("error") && !e.getPlayer().hasPermission("lobbynk.fulllobby")) {
            e.setCancelled(true);
            Player p = e.getPlayer();
            e.setKickMessage(lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "everylobbyisfull"));
            return;
        }
        if (e.getPlayer().hasPermission("lobbynk.trusted")) {
            Skin s = e.getPlayer().getSkin();
            s.setTrusted(true);
            e.getPlayer().setSkin(s);
        }
    }

    @EventHandler
    public void onInvMove(InventoryClickEvent e) {
        if (!(e.getInventory() instanceof PlayerInventory) || e.getPlayer().getGamemode() == 1) {
            return;
        }
        if (!(config.getBoolean("multiworld") && !MultiLobbyManager.lobbyworlds.contains(e.getPlayer().getLevel().getName()) || config.getBoolean("Inventorychange") || e.getPlayer().getGamemode() != config.getInt("Gamemode"))) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (!(config.getBoolean("multiworld") && !MultiLobbyManager.lobbyworlds.contains(e.getPlayer().getLevel().getName()) || config.getBoolean("Inventorychange"))) {
            e.setCancelled(true);
        }
    }


    @EventHandler
    public void hungerChange(PlayerFoodLevelChangeEvent e) {
        if ((!config.getBoolean("multiworld") || MultiLobbyManager.lobbyworlds.contains(e.getPlayer().getLevel().getName())) && config.getBoolean("NoHunger")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDie(PlayerRespawnEvent e) {
        if (config.getBoolean("multiworld") && MultiLobbyManager.lobbyworlds.contains(e.getPlayer().getSpawn().getLevel().getName()) && !e.isFirstSpawn()) {
            onTeleport.xp.put(e.getPlayer().getName(), e.getPlayer().getExperience());
            onTeleport.level.put(e.getPlayer().getName(), e.getPlayer().getExperienceLevel());
            onTeleport.invs.put(e.getPlayer().getName(), e.getPlayer().getInventory().getContents());
            if (config.getBoolean("alwaysspawn")) {
                player.toSpawn(e.getPlayer());
            }
            player.giveHotbar(e.getPlayer());
        }
    }

    @EventHandler
    public void dieEvent(PlayerDeathEvent e) {
        if (config.getBoolean("multiworld") && !MultiLobbyManager.lobbyworlds.contains(e.getEntity().getLevel().getName()) && config.getBoolean("multiworldSaveInventory")) {
            if (e.getKeepInventory()) {
                onTeleport.invs.put(e.getEntity().getName(), e.getEntity().getInventory().getContents());
            } else {
                onTeleport.invs.put(e.getEntity().getName(), new HashMap());
            }
            if (e.getKeepExperience()) {
                onTeleport.xp.put(e.getEntity().getName(), e.getEntity().getExperience());
                onTeleport.level.put(e.getEntity().getName(), e.getEntity().getExperienceLevel());
            } else {
                onTeleport.xp.put(e.getEntity().getName(), 0);
                onTeleport.level.put(e.getEntity().getName(), 0);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        ConfigNK c;
        if (e.getEntity() instanceof Player && (!(c = config).getBoolean("multiworld") || MultiLobbyManager.lobbyworlds.contains(e.getEntity().getLevel().getName()))) {
            Player p = (Player)e.getEntity();
            String world = "";
            if (config.getBoolean("multilobby")) {
                world = "." + e.getEntity().getLevel().getName();
            }
            if (c.getBoolean("NoDamage")) {
                e.setCancelled(true);
                if (e.getCause() == EntityDamageEvent.DamageCause.VOID) {
                    loc = new Config(new File(Server.getInstance().getPluginManager().getPlugin("LobbyNK").getDataFolder(), "locations.yml"));
                    try {
                        if (loc.exists("spawn.world" + world)) {
                            double x = loc.getDouble("spawn.x" + world);
                            double y = loc.getDouble("spawn.y" + world);
                            double z = loc.getDouble("spawn.z" + world);
                            float yaw = (float)loc.getDouble("spawn.yaw" + world);
                            float pitch = (float)loc.getDouble("spawn.pitch" + world);
                            Level le = Server.getInstance().getLevelByName(loc.getString("spawn.world" + world));
                            Location tp = new Location(x, y, z, (double)yaw, (double)pitch, le);
                            p.teleport(tp);
                            p.sendMessage(lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "spawn.tp"));
                        } else {
                            p.sendMessage(lang.getString(String.valueOf(p.getLocale().toString()) + "_" + p.getLocale().toString() + "_" + "Locations.Teleport.missing").replace("%game", "spawn"));
                            p.teleport(p.getLevel().getSpawnLocation());
                        }
                    }
                    catch (Exception e2) {
                        p.sendMessage(lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Locations.Teleport.missing").replace("%game", "spawn"));
                        p.teleport(p.getLevel().getSpawnLocation());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent e) {
        if (config.getBoolean("multiworld") && config.getBoolean("multiworldSaveInventory") && MultiLobbyManager.lobbyworlds.contains(e.getPlayer().getLevel().getName())) {
            if (onTeleport.invs.containsKey(e.getPlayer().getName())) {
                e.getPlayer().getInventory().clearAll();
                e.getPlayer().getInventory().setContents(onTeleport.invs.get(e.getPlayer().getName()));
                e.getPlayer().setExperience(onTeleport.xp.get(e.getPlayer().getName()).intValue(), onTeleport.level.get(e.getPlayer().getName()).intValue());
                if (!e.getPlayer().isBanned()) {
                    e.getPlayer().getInventory().sendContents(e.getPlayer());
                }
            } else {
                e.getPlayer().setExperience(0, 0);
                e.getPlayer().getInventory().clearAll();
            }
        }
    }

    public void onDisable() {
        if (config.getBoolean("multiworld") && config.getBoolean("multiworldSaveInventory")) {
            for (Player p : this.getServer().getOnlinePlayers().values()) {
                if (!MultiLobbyManager.lobbyworlds.contains(p.getLevel().getName())) continue;
                p.getInventory().clearAll();
                p.setExperience(0, 0);
                p.getInventory().setContents(onTeleport.invs.get(p.getName()));
                p.setExperience(onTeleport.xp.get(p.getName()).intValue(), onTeleport.level.get(p.getName()).intValue());
                p.getInventory().sendContents(p);
            }
        }
        for (Entity e : PetEvents.map.values()) {
            e.despawnFromAll();
            e.close();
        }
        for (Player p : this.getServer().getOnlinePlayers().values()) {
            if (p.getGamemode() != 0 || !p.getAllowFlight()) continue;
            p.setAllowFlight(false);
            p.setGamemode(config.getInt("Gamemode"));
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onChat(PlayerChatEvent e) {
        Player p = e.getPlayer();
        if (config.getBoolean("Chatsystem")) {
            if (p.hasPermission("lobbynk.owner")) {
                e.setCancelled(true);
                if (config.getBoolean("multilobby")) {
                    for (Player p2 : p.getLevel().getPlayers().values()) {
                        p2.sendMessage(chat.getString("Owner").replace("%p", p.getDisplayName()).replace("%msg", e.getMessage()));
                    }
                } else {
                    this.getServer().broadcastMessage(chat.getString("Owner").replace("%p", p.getDisplayName()).replace("%msg", e.getMessage()));
                }
            } else if (p.hasPermission("lobbynk.admin")) {
                e.setCancelled(true);
                if (config.getBoolean("multilobby")) {
                    for (Player p2 : this.getServer().getOnlinePlayers().values()) {
                        if (p2.getLevel() != p.getLevel()) continue;
                        p2.sendMessage(chat.getString("Admin").replace("%p", p.getDisplayName()).replace("%msg", e.getMessage()));
                    }
                } else {
                    this.getServer().broadcastMessage(chat.getString("Admin").replace("%p", p.getDisplayName()).replace("%msg", e.getMessage()));
                }
            } else if (p.hasPermission("lobbynk.moderator")) {
                e.setCancelled(true);
                if (config.getBoolean("multilobby")) {
                    for (Player p2 : this.getServer().getOnlinePlayers().values()) {
                        if (p2.getLevel() != p.getLevel()) continue;
                        p2.sendMessage(chat.getString("Moderator").replace("%p", p.getDisplayName()).replace("%msg", e.getMessage()));
                    }
                } else {
                    this.getServer().broadcastMessage(chat.getString("Moderator").replace("%p", p.getDisplayName()).replace("%msg", e.getMessage()));
                }
            } else if (p.hasPermission("lobbynk.developer")) {
                e.setCancelled(true);
                if (config.getBoolean("multilobby")) {
                    for (Player p2 : this.getServer().getOnlinePlayers().values()) {
                        if (p2.getLevel() != p.getLevel()) continue;
                        p2.sendMessage(chat.getString("Developer").replace("%p", p.getDisplayName()).replace("%msg", e.getMessage()));
                    }
                } else {
                    this.getServer().broadcastMessage(chat.getString("Moderator").replace("%p", p.getDisplayName()).replace("%msg", e.getMessage()));
                }
            } else if (p.hasPermission("lobbynk.builder")) {
                e.setCancelled(true);
                if (config.getBoolean("multilobby")) {
                    for (Player p2 : this.getServer().getOnlinePlayers().values()) {
                        if (p2.getLevel() != p.getLevel()) continue;
                        p2.sendMessage(chat.getString("Builder").replace("%p", p.getDisplayName()).replace("%msg", e.getMessage()));
                    }
                } else {
                    this.getServer().broadcastMessage(chat.getString("Builder").replace("%p", p.getDisplayName()).replace("%msg", e.getMessage()));
                }
            } else if (p.hasPermission("lobbynk.supporter")) {
                e.setCancelled(true);
                if (config.getBoolean("multilobby")) {
                    for (Player p2 : this.getServer().getOnlinePlayers().values()) {
                        if (p2.getLevel() != p.getLevel()) continue;
                        p2.sendMessage(chat.getString("Supporter").replace("%p", p.getDisplayName()).replace("%msg", e.getMessage()));
                    }
                } else {
                    this.getServer().broadcastMessage(chat.getString("Supporter").replace("%p", p.getDisplayName()).replace("%msg", e.getMessage()));
                }
            } else if (p.hasPermission("lobbynk.youtuber")) {
                e.setCancelled(true);
                if (config.getBoolean("multilobby")) {
                    for (Player p2 : this.getServer().getOnlinePlayers().values()) {
                        if (p2.getLevel() != p.getLevel()) continue;
                        p2.sendMessage(chat.getString("Youtuber").replace("%p", p.getDisplayName()).replace("%msg", e.getMessage()));
                    }
                } else {
                    this.getServer().broadcastMessage(chat.getString("Youtuber").replace("%p", p.getDisplayName()).replace("%msg", e.getMessage()));
                }
            } else if (p.hasPermission("lobbynk.premium")) {
                e.setCancelled(true);
                if (config.getBoolean("multilobby")) {
                    for (Player p2 : this.getServer().getOnlinePlayers().values()) {
                        if (p2.getLevel() != p.getLevel()) continue;
                        p2.sendMessage(chat.getString("Premium").replace("%p", p.getDisplayName()).replace("%msg", e.getMessage()));
                    }
                } else {
                    this.getServer().broadcastMessage(chat.getString("Premium").replace("%p", p.getDisplayName()).replace("%msg", e.getMessage()));
                }
            } else if (p.hasPermission("lobbynk.custom1")) {
                e.setCancelled(true);
                if (config.getBoolean("multilobby")) {
                    for (Player p2 : this.getServer().getOnlinePlayers().values()) {
                        if (p2.getLevel() != p.getLevel()) continue;
                        p2.sendMessage(chat.getString("Custom1").replace("%p", p.getDisplayName()).replace("%msg", e.getMessage()));
                    }
                } else {
                    this.getServer().broadcastMessage(chat.getString("Custom1").replace("%p", p.getDisplayName()).replace("%msg", e.getMessage()));
                }
            } else if (p.hasPermission("lobbynk.custom2")) {
                e.setCancelled(true);
                if (config.getBoolean("multilobby")) {
                    for (Player p2 : this.getServer().getOnlinePlayers().values()) {
                        if (p2.getLevel() != p.getLevel()) continue;
                        p2.sendMessage(chat.getString("Custom2").replace("%p", p.getDisplayName()).replace("%msg", e.getMessage()));
                    }
                } else {
                    this.getServer().broadcastMessage(chat.getString("Custom2").replace("%p", p.getDisplayName()).replace("%msg", e.getMessage()));
                }
            } else if (p.hasPermission("lobbynk.custom3")) {
                e.setCancelled(true);
                if (config.getBoolean("multilobby")) {
                    for (Player p2 : this.getServer().getOnlinePlayers().values()) {
                        if (p2.getLevel() != p.getLevel()) continue;
                        p2.sendMessage(chat.getString("Custom3").replace("%p", p.getDisplayName()).replace("%msg", e.getMessage()));
                    }
                } else {
                    this.getServer().broadcastMessage(chat.getString("Custom3").replace("%p", p.getDisplayName()).replace("%msg", e.getMessage()));
                }
            } else {
                e.setCancelled(true);
                if (config.getBoolean("multilobby")) {
                    for (Player p2 : this.getServer().getOnlinePlayers().values()) {
                        if (p2.getLevel() != p.getLevel()) continue;
                        p2.sendMessage(chat.getString("Default").replace("%p", p.getDisplayName()).replace("%msg", e.getMessage()));
                    }
                } else {
                    this.getServer().broadcastMessage(chat.getString("Default").replace("%p", p.getDisplayName()).replace("%msg", e.getMessage()));
                }
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (!config.getBoolean("multiworld") || MultiLobbyManager.lobbyworlds.contains(e.getPlayer().getLevel().getName())) {
            Player p = e.getPlayer();
            if (map.containsKey(e.getPlayer().getName())) {
                if (map.get(e.getPlayer().getName()).equals("angry")) {
                    p.getLevel().addParticle((Particle)new AngryVillagerParticle((Vector3)p));
                }
                if (map.get(e.getPlayer().getName()).equals("bubble")) {
                    p.getLevel().addParticle((Particle)new BubbleParticle((Vector3)p));
                }
                if (map.get(e.getPlayer().getName()).equals("bonemeal")) {
                    p.getLevel().addParticle((Particle)new BoneMealParticle((Vector3)p));
                }
                if (map.get(e.getPlayer().getName()).equals("explode")) {
                    p.getLevel().addParticle((Particle)new ExplodeParticle((Vector3)p));
                }
                if (map.get(e.getPlayer().getName()).equals("heart")) {
                    p.getLevel().addParticle((Particle)new HeartParticle((Vector3)p));
                }
                if (map.get(e.getPlayer().getName()).equals("flame")) {
                    p.getLevel().addParticle((Particle)new FlameParticle((Vector3)p));
                }
                if (map.get(e.getPlayer().getName()).equals("redstone")) {
                    p.getLevel().addParticle((Particle)new RedstoneParticle((Vector3)p));
                }
                if (map.get(e.getPlayer().getName()).equals("ink")) {
                    p.getLevel().addParticle((Particle)new InkParticle((Vector3)p));
                }
                if (map.get(e.getPlayer().getName()).equals("note")) {
                    p.getLevel().addParticle((Particle)new NoteParticle((Vector3)p));
                }
                if (map.get(e.getPlayer().getName()).equals("enchant")) {
                    Vector3 vec = new Vector3(p.getX(), p.getY() + 1.0, p.getZ());
                    p.getLevel().addParticle((Particle)new EnchantmentTableParticle(vec));
                    p.getLevel().addParticle((Particle)new EnchantmentTableParticle(vec));
                    p.getLevel().addParticle((Particle)new EnchantmentTableParticle(vec));
                    p.getLevel().addParticle((Particle)new EnchantmentTableParticle(vec));
                    p.getLevel().addParticle((Particle)new EnchantmentTableParticle(vec));
                }
            }
            Vector3 pos = new Vector3(p.getLevelBlock().getX(), p.getLevelBlock().getY() - 2.0, p.getLevelBlock().getZ());
            if (this.getServer().getLevelByName(p.getLevel().getName()).getBlock(pos).getName().equalsIgnoreCase(Block.get((int)config.getInt("jumppadblock")).getName()) && Boolean.valueOf(playersettings.getString(String.valueOf(e.getPlayer().getName()) + ".jumppad")).booleanValue() && delaydata.get(String.valueOf(p.getName()) + "jp") == null) {
                delaydata.set(String.valueOf(p.getName()) + "jp", true);
                delaydata.save();
                p.setMotion(p.getLocation().getDirectionVector().multiply(5.0).add(0.0, 1.5, 0.0));
                p.getLevel().addSound(new Vector3(p.getX(), p.getY(), p.getZ()), Sound.MOB_ENDERDRAGON_FLAP);
                this.getServer().getScheduler().scheduleDelayedTask((Plugin)this, new Runnable(){

                    @Override
                    public void run() {
                        delaydata.set(String.valueOf(p.getName()) + "jp", null);
                    }
                }, 5);
            }
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        if (config.getBoolean("disableWeather") && MultiLobbyManager.lobbyworlds.contains(e.getLevel().getName()) && this.weather == 0) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onJump(PlayerMoveEvent e) {
        if (!config.getBoolean("multiworld") || MultiLobbyManager.lobbyworlds.contains(e.getPlayer().getLevel().getName())) {

            for (Player p2 : e.getPlayer().getLevel().getPlayers().values()) {
                if (p2 == e.getPlayer() || e.getPlayer().hasPermission("lobbynk.forcefield") || e.getPlayer().hasPermission("lobbynk.forcefield.bypass") || !playersettings.has(String.valueOf(p2.getName()) + ".forcefield") || !Boolean.valueOf(playersettings.get(String.valueOf(p2.getName()) + ".forcefield").toString()).booleanValue()) continue;
                Vector3 vector3 = new Vector3(e.getPlayer().getX(), e.getPlayer().getY(), e.getPlayer().getZ());
                if (!(p2.getLocation().distance(vector3) <= 3.0)) continue;
                Vector3 vec = new Vector3(e.getPlayer().getX(), e.getPlayer().getY(), e.getPlayer().getZ());
                if (config.getBoolean("soundeffects")) {
                    e.getPlayer().getLevel().addSound(vec, Sound.MOB_ENDERDRAGON_FLAP);
                }
                e.getPlayer().getLevel().addParticleEffect(vec, ParticleEffect.EYE_OF_ENDER_BUBBLE);
                e.getPlayer().setMotion(p2.getLocation().getDirectionVector().multiply(-0.7).add(0.0, 0.5, 0.0));
            }
            Player p2 = e.getPlayer();
            if (e.getPlayer().hasPermission("lobbynk.doublejump") && (Boolean.valueOf(playersettings.getString(String.valueOf(e.getPlayer().getName()) + ".doublejump")).booleanValue() || !playersettings.has(String.valueOf(e.getPlayer().getName()) + ".doublejump")) && !flyable.contains(e.getPlayer().getName())) {
            
                if (!p2.isCreative() && p2.isOnGround() && !p2.getAllowFlight()) {
                    p2.setAllowFlight(true);
                }
            } else if (Boolean.valueOf(playersettings.getString(String.valueOf(e.getPlayer().getName()) + ".fly")).booleanValue() && !(p2 = e.getPlayer()).isCreative() && p2.isOnGround() && !p2.getAllowFlight()) {
                p2.setAllowFlight(true);
            }
        }
    }
    @EventHandler
    public void onCompass(PlayerFormRespondedEvent e) {
    	try {
    		if(!(e.getWindow() instanceof FormWindowSimple)) return;
    	FormWindowSimple gui = (FormWindowSimple) e.getWindow();
    	if(gui.getResponse() == null) return;
    	String world = "";
    	 if(config.getBoolean("multilobby")) {world = "." + e.getPlayer().getLevel().getName();}
    	loc = new Config(new File(this.getDataFolder(), "locations.yml"));
    	 String responseName = gui.getResponse().getClickedButton().getText();
    	 Player p = e.getPlayer();
    	 if(gui.getTitle().equals(lang.getString(p.getLocale().toString() + "_" +"Compass.name")))
    	responseName = lang.getString(p.getLocale().toString() + "_" +"Compass.Slot"+ (gui.getResponse().getClickedButtonId()+1));
    	 for(int i = 1; i != config.getInt("Gamesamount")+1; i++) {
    		 if(responseName.equals(lang.getString(p.getLocale().toString() + "_" +"Compass.Slot" + i))){
    			 if(!compasscommands.getString("Slot"+i+".command").equalsIgnoreCase("none")) {
    				 	String o = compasscommands.getString("Slot" + i + ".command");
    					
    					if(o.startsWith("bungee[")) {
    						ScriptCustomEventPacket packet = new ScriptCustomEventPacket();
    						packet.eventName = "bungeecord:main";
    						ByteArrayDataOutput out = ByteStreams.newDataOutput();
    						out.writeUTF("Connect");
    						out.writeUTF(o.replace("bungee[", "").replace("]", ""));
    						packet.eventData = out.toByteArray();
    						p.dataPacket(packet);
    					}else p.getServer().dispatchCommand(p, compasscommands.getString("Slot"+i+".command").replace("/", "").replace("%player", e.getPlayer().getName()));
    				 }
    			 if(!compasscommands.getBoolean("Slot"+i+".disableloc")) {
    				 
    				 if(loc.get("loc"+i+".x" + world) != null) {
    				 double x = loc.getDouble("loc"+i+".x" +world);
    				 double y = loc.getDouble("loc"+i+".y"+world);
    				 double z = loc.getDouble("loc"+i+".z"+world);
    				 float yaw = (float) loc.getDouble("loc"+i+".yaw"+world);
    				 float pitch = (float) loc.getDouble("loc"+i+".pitch"+world);
    				 Level level = getServer().getLevelByName(loc.getString("loc"+i+".world"+world));
    				 Location tp = new Location(x,y,z,yaw,pitch,level);
    				 e.getPlayer().teleport(tp);
    				 if(config.getBoolean("Teleportchat")) {
    					 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Locations.Teleport.Chat").replace("%game", responseName.split("\\s+")[0]));
    					 }
    					 if(config.getBoolean("Teleporttitle")) {
    					 e.getPlayer().sendTitle(lang.getString(p.getLocale().toString() + "_" +"Locations.Teleport.Title").replace("%game", responseName.split("\\s+")[0]));
    					 }
    				 } else {
    					 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Locations.Teleport.missing").replace("%game", responseName.split("\\s+")[0]));
    				 }}
    		 }
    	 }
    if(responseName.equals(lang.getString(p.getLocale().toString() + "_" +"Chest.Walkingparticles"))) {
    		 FormWindowSimple w = new FormWindowSimple(lang.getString(p.getLocale().toString() + "_" +"Chest.Walkingparticles"), "");
    		
    		 
    		 
    		 String[] effects = new String[]{"angry", "bonemeal", "bubble", "explode", "heart", "flame", "redstone", "ink", "enchant", "note"};
    		 String[] img = new String[]{"gui/newgui/mob_effects/bad_omen_effect", "items/bone", "ui/bubble", "blocks/tnt_side", "gui/newgui/mob_effects/regeneration_effect", "items/flint_and_steel", "items/redstone_dust", "items/dye_powder_black", "items/book_enchanted", "items/record_13"};
    		 int counter = 0;
    		 int index = 0;
    		 for(String s : effects) { index++; if(e.getPlayer().hasPermission("lobbynk.effect." + s)) { w.addButton(new ElementButton(lang.getString(p.getLocale().toString() + "_" +"Chest.walking." +s), new ElementButtonImageData("path", "textures/" + img[index-1] + ".png"))); counter++;}}
    		 
    		 if(counter == 0) w.setContent(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 e.getPlayer().showFormWindow(w);
    	 }else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.Cosmetics"))){
    		 
    		 FormWindowSimple w = new FormWindowSimple(lang.getString(p.getLocale().toString() + "_" +"Chest.Cosmetics"), "");
    		 String[] effects = new String[]{"diamond", "iron", "chainmail", "gold", "leather", "other", "enchant"};
    		 String[] img = new String[]{"items/diamond", "items/iron_ingot", "blocks/iron_bars", "items/gold_ingot", "items/leather", "items/elytra", "items/book_enchanted"};
    		 int counter = 0;
    		 int index = 0;
    		 for(String s : effects) { index++; if(e.getPlayer().hasPermission("lobbynk.cosmetic.view." + s)) { w.addButton(new ElementButton(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic." +s), new ElementButtonImageData("path", "textures/" + img[index-1] + ".png"))); counter++;}}
    		
    		 if(counter == 0) w.setContent(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 e.getPlayer().showFormWindow(w);
    	 }else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.diamond"))){
    		 
    		 FormWindowSimple w = new FormWindowSimple(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.diamond"), "");
    		 String[] effects = new String[]{"helmet", "chestplate", "leggings", "boots"};
    		 int counter = 0;
    		 for(String s : effects) {if(e.getPlayer().hasPermission("lobbynk.cosmetic.diamond." + s)) { w.addButton(new ElementButton(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.diamond") + lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic." +s), new ElementButtonImageData("path", "textures/items/diamond_" + s + ".png"))); counter++;}}
    		
    		 if(counter == 0) w.setContent(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 e.getPlayer().showFormWindow(w);
    		 
    		
    	 }else  if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.iron"))){
    		 
    		 FormWindowSimple w = new FormWindowSimple(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.iron"), "");
    		 String[] effects = new String[]{"helmet", "chestplate", "leggings", "boots"};
    		 int counter = 0;
    		 for(String s : effects) {if(e.getPlayer().hasPermission("lobbynk.cosmetic.iron." + s)) { w.addButton(new ElementButton(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.iron") + lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic." +s), new ElementButtonImageData("path", "textures/items/iron_" + s + ".png"))); counter++;}}
    		
    		 if(counter == 0) w.setContent(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 e.getPlayer().showFormWindow(w);

    	 }else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.chainmail"))){
    		 FormWindowSimple w = new FormWindowSimple(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.chainmail"), "");
    		 String[] effects = new String[]{"helmet", "chestplate", "leggings", "boots"};
    		 int counter = 0;
    		 for(String s : effects) {if(e.getPlayer().hasPermission("lobbynk.cosmetic.chainmail." + s)) { w.addButton(new ElementButton(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.chainmail") + lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic." +s), new ElementButtonImageData("path", "textures/items/chainmail_" + s + ".png"))); counter++;}}
    		
    		 if(counter == 0) w.setContent(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 e.getPlayer().showFormWindow(w);
    		 
    	 }else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.gold"))){
    		 FormWindowSimple w = new FormWindowSimple(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.gold"), "");
    		 String[] effects = new String[]{"helmet", "chestplate", "leggings", "boots"};
    		 int counter = 0;
    		 for(String s : effects) {if(e.getPlayer().hasPermission("lobbynk.cosmetic.gold." + s)) { w.addButton(new ElementButton(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.gold") + lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic." +s), new ElementButtonImageData("path", "textures/items/gold_" + s + ".png"))); counter++;}}
    		
    		 if(counter == 0) w.setContent(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 e.getPlayer().showFormWindow(w);
    	 }else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.leather"))){
    		 
    		 FormWindowSimple w = new FormWindowSimple(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.leather"), "");
    		 String[] effects = new String[]{"helmet", "chestplate", "leggings", "boots"};
    		 String[] img = new String[]{"tga", "png", "tga", "tga"};
    		 int counter = 0;
    		 int index = 0;
    		 for(String s : effects) { index++; if(e.getPlayer().hasPermission("lobbynk.cosmetic.leather." + s)) { w.addButton(new ElementButton(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.leather") + lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic." +s), new ElementButtonImageData("path", "textures/items/leather_" +s + "." + img[index-1]))); counter++;}}
    		
    		 if(counter == 0) w.setContent(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 e.getPlayer().showFormWindow(w);
    		 
    		
    	 }else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.other"))){
    		 
    		 FormWindowSimple w = new FormWindowSimple(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.other"), "");
    		 String[] effects = new String[]{"skull", "wither", "zombie", "player", "creeper", "dragon", "shell", "elytra"};
    		 String[] img = new String[]{"items/egg_skeleton", "items/egg_skeleton", "items/egg_zombie", "ui/icon_steve", "items/egg_creeper", "items/egg_enderman", "items/turtle_helmet", "items/elytra"};
    		 int counter = 0;
    		 int index = 0;
    		 for(String s : effects) { index++; if(e.getPlayer().hasPermission("lobbynk.cosmetic." + s)) { w.addButton(new ElementButton(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic." +s), new ElementButtonImageData("path", "textures/" + img[index-1] + ".png"))); counter++;}}
    		
    		 if(counter == 0) w.setContent(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 e.getPlayer().showFormWindow(w);
    	 }
    	 else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.enchant"))) {
    		 
    		 FormWindowSimple w = new FormWindowSimple(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.enchant"), "");
    		 String[] effects = new String[]{"helmet", "chestplate", "leggings", "boots"};
    		 int counter = 0;
    		 int index = -1;
    		 int eq = 0;
    		 for(String s : effects) {
    			 index++;
    			 if(e.getPlayer().hasPermission("lobbynk.cosmetic.enchant." + s)) { 
    			if(e.getPlayer().getInventory().getArmorItem(index).getId() != 0) eq++; 
    			 String name = lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic."+s).replaceFirst(String.valueOf(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic." +s).charAt(2)), String.valueOf(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic." +s).charAt(2)).toUpperCase());
    			 if(e.getPlayer().getInventory().getArmorItem(index).getId() != 0) w.addButton(new ElementButton(name)); counter++;}}
    		
    		 if(eq == 0) {
    			 w.getButtons().clear();
    			 w.setContent(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.nothingequipped"));
    		 }
    		 if(counter == 0) w.setContent(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 e.getPlayer().showFormWindow(w);
    		 
    		 
    	 } /*else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.dye"))) {
    		 
    		 FormWindowSimple fw = new FormWindowSimple(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.dye"), "");
    		 int count = 0;
    		 
    		 String helmet = lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.helmet").replaceFirst(String.valueOf(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.helmet").charAt(2)), String.valueOf(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.helmet").charAt(2)).toUpperCase());
    		 String chestplate = lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.chestplate").replaceFirst(String.valueOf(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.chestplate").charAt(2)), String.valueOf(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.chestplate").charAt(2)).toUpperCase());
    		 String leggings = lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.leggings").replaceFirst(String.valueOf(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.leggings").charAt(2)), String.valueOf(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.leggings").charAt(2)).toUpperCase());
    		 String boots = lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.boots").replaceFirst(String.valueOf(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.boots").charAt(2)), String.valueOf(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.boots").charAt(2)).toUpperCase());
    		 if(e.getPlayer().getInventory().getArmorItem(0).getId() != 0 && e.getPlayer().hasPermission("lobbynk.cosmetic.dye.helmet")) {fw.addButton(new ElementButton(helmet)); count++;}
    		 if(e.getPlayer().getInventory().getArmorItem(1).getId() != 0 && e.getPlayer().hasPermission("lobbynk.cosmetic.dye.chestplate")) {fw.addButton(new ElementButton(chestplate)); count++;}
    		 if(e.getPlayer().getInventory().getArmorItem(2).getId() != 0 && e.getPlayer().hasPermission("lobbynk.cosmetic.dye.leggings")) {fw.addButton(new ElementButton(leggings)); count++;}
    		 if(e.getPlayer().getInventory().getArmorItem(3).getId() != 0 && e.getPlayer().hasPermission("lobbynk.cosmetic.dye.boots")) {fw.addButton(new ElementButton(boots)); count++;}
    		 
    		// if(!e.getPlayer().hasPermission("lobbynk.cosmetic.dye.helmet") && !e.getPlayer().hasPermission("lobbynk.cosmetic.dye.chestplate") && !e.getPlayer().hasPermission("lobbynk.cosmetic.dye.leggings") && !e.getPlayer().hasPermission("lobbynk.cosmetic.dye.boots")) count = 0;
    		 
    		 if(count == 0) {
    			 fw.setContent(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.nothingequipped"));
    		 } 
    		 p.showFormWindow(fw);
    	 } */
    	 else if(responseName.toLowerCase().equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.helmet").toLowerCase())) {
    		 
    		 if(((FormWindowSimple) e.getWindow()).getTitle().equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.enchant"))) {
    			 
    			 Item i = e.getPlayer().getInventory().getArmorItem(0);
    			if(i.hasEnchantments()) {
    				e.getPlayer().getInventory().setArmorItem(0, Item.get(i.getId()));
    			} else {
    				i.addEnchantment(Enchantment.get(Enchantment.ID_MENDING));
    				e.getPlayer().getInventory().setArmorItem(0, i);
    			}
    			e.getPlayer().getInventory().sendContents(e.getPlayer()); 
    		 }
    		 
    	 }
     else if(responseName.toLowerCase().equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.chestplate").toLowerCase())) {
    		 
    		 if(((FormWindowSimple) e.getWindow()).getTitle().equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.enchant"))) {
    			 
    			 Item i = e.getPlayer().getInventory().getArmorItem(1);
    			if(i.hasEnchantments()) {
    				e.getPlayer().getInventory().setArmorItem(1, Item.get(i.getId()));
    			} else {
    				i.addEnchantment(Enchantment.get(Enchantment.ID_MENDING));
    				e.getPlayer().getInventory().setArmorItem(1, i);
    			}
    			e.getPlayer().getInventory().sendContents(e.getPlayer()); 
    		 }
    		 
    	 }
     else if(responseName.toLowerCase().equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.leggings").toLowerCase())) {
    	 
    	 if(((FormWindowSimple) e.getWindow()).getTitle().equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.enchant"))) {
    		 
    		 Item i = e.getPlayer().getInventory().getArmorItem(2);
    		if(i.hasEnchantments()) {
    			e.getPlayer().getInventory().setArmorItem(2, Item.get(i.getId()));
    		} else {
    			i.addEnchantment(Enchantment.get(Enchantment.ID_MENDING));
    			e.getPlayer().getInventory().setArmorItem(2, i);
    		}
    		e.getPlayer().getInventory().sendContents(e.getPlayer()); 
    	 }
    	 
     }
     else if(responseName.toLowerCase().equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.boots").toLowerCase())) {
    	 
    	 if(((FormWindowSimple) e.getWindow()).getTitle().equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.enchant"))) {
    		 
    		 Item i = e.getPlayer().getInventory().getArmorItem(3);
    		if(i.hasEnchantments()) {
    			e.getPlayer().getInventory().setArmorItem(3, Item.get(i.getId()));
    		} else {
    			i.addEnchantment(Enchantment.get(Enchantment.ID_MENDING));
    			e.getPlayer().getInventory().setArmorItem(3, i);
    		}
    		e.getPlayer().getInventory().sendContents(e.getPlayer()); 
    	 }
    	 
     }
    	 else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.netherite")+ lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.helmet"))){
    		 if(e.getPlayer().hasPermission("lobbynk.cosmetic.netherite.helmet")) {
    			 	if(e.getPlayer().getInventory().getArmorItem(0).getId() == Item.NETHERITE_HELMET) {
    			 	if(e.getPlayer().hasPermission("lobbynk.remove")) {
    			 		e.getPlayer().getInventory().setArmorItem(0, Item.get(0));
    					 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.removed").replace("%cosmetic", responseName));
    					 return;
    			 	} else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));return;
    			 	}
    			 e.getPlayer().getInventory().setArmorItem(0, Item.get(Item.NETHERITE_HELMET));
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.equipped").replace("%cosmetic", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    		 
    	 }
    	 else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.netherite")+ lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.chestplate"))){
    		 if(e.getPlayer().hasPermission("lobbynk.cosmetic.netherite.chestplate")) {
    			 	if(e.getPlayer().getInventory().getArmorItem(1).getId() == Item.NETHERITE_CHESTPLATE) {
    			 	if(e.getPlayer().hasPermission("lobbynk.remove")) {
    			 		e.getPlayer().getInventory().setArmorItem(1, Item.get(0));
    					 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.removed").replace("%cosmetic", responseName));
    					 return;
    			 	} else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));return;
    			 	}
    			 e.getPlayer().getInventory().setArmorItem(1, Item.get(Item.NETHERITE_CHESTPLATE));
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.equipped").replace("%cosmetic", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    		 
    	 }
    	 else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.netherite")+ lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.leggings"))){
    		 if(e.getPlayer().hasPermission("lobbynk.cosmetic.netherite.leggings")) {
    			 	if(e.getPlayer().getInventory().getArmorItem(2).getId() == Item.NETHERITE_LEGGINGS) {
    			 	if(e.getPlayer().hasPermission("lobbynk.remove")) {
    			 		e.getPlayer().getInventory().setArmorItem(2, Item.get(0));
    					 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.removed").replace("%cosmetic", responseName));
    					 return;
    			 	} else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));return;
    			 	}
    			 e.getPlayer().getInventory().setArmorItem(2, Item.get(Item.NETHERITE_LEGGINGS));
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.equipped").replace("%cosmetic", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    		 
    	 }
    	 else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.netherite")+ lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.boots"))){
    		 if(e.getPlayer().hasPermission("lobbynk.cosmetic.netherite.boots")) {
    			 	if(e.getPlayer().getInventory().getArmorItem(3).getId() == Item.NETHERITE_BOOTS) {
    			 	if(e.getPlayer().hasPermission("lobbynk.remove")) {
    			 		e.getPlayer().getInventory().setArmorItem(3, Item.get(0));
    					 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.removed").replace("%cosmetic", responseName));
    					 return;
    			 	} else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));return;
    			 	}
    			 e.getPlayer().getInventory().setArmorItem(3, Item.get(Item.NETHERITE_BOOTS));
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.equipped").replace("%cosmetic", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    		 
    	 }
    	 else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.diamond")+ lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.helmet"))){
    		 if(e.getPlayer().hasPermission("lobbynk.cosmetic.diamond.helmet")) {
    			 	if(e.getPlayer().getInventory().getArmorItem(0).getId() == Item.DIAMOND_HELMET) {
    			 	if(e.getPlayer().hasPermission("lobbynk.remove")) {
    			 		e.getPlayer().getInventory().setArmorItem(0, Item.get(0));
    					 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.removed").replace("%cosmetic", responseName));
    					 return;
    			 	} else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));return;
    			 	}
    			 e.getPlayer().getInventory().setArmorItem(0, Item.get(Item.DIAMOND_HELMET));
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.equipped").replace("%cosmetic", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    		 
    	 } else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.diamond")+ lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.chestplate"))){
    		 if(e.getPlayer().hasPermission("lobbynk.cosmetic.diamond.chestplate")) {
    			 if(e.getPlayer().getInventory().getArmorItem(1).getId() == Item.DIAMOND_CHESTPLATE) {
    				 	if(e.getPlayer().hasPermission("lobbynk.remove")) {
    				 		e.getPlayer().getInventory().setArmorItem(1, Item.get(0));
    						 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.removed").replace("%cosmetic", responseName));
    						 return;
    				 	} else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));return;
    				 	}
    			 e.getPlayer().getInventory().setArmorItem(1, Item.get(Item.DIAMOND_CHESTPLATE));
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.equipped").replace("%cosmetic", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    		 
    	 }else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.diamond")+ lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.leggings"))){
    		 if(e.getPlayer().hasPermission("lobbynk.cosmetic.diamond.leggings")) {
    			 if(e.getPlayer().getInventory().getArmorItem(2).getId() == Item.DIAMOND_LEGGINGS) {
    				 	if(e.getPlayer().hasPermission("lobbynk.remove")) {
    				 		e.getPlayer().getInventory().setArmorItem(2, Item.get(0));
    						 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.removed").replace("%cosmetic", responseName));
    						 return;
    				 	} else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));return;
    				 	}
    			 e.getPlayer().getInventory().setArmorItem(2, Item.get(Item.DIAMOND_LEGGINGS));
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.equipped").replace("%cosmetic", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    		 
    	 }else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.diamond")+ lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.boots"))){
    		 if(e.getPlayer().hasPermission("lobbynk.cosmetic.diamond.boots")) {
    			 if(e.getPlayer().getInventory().getArmorItem(3).getId() == Item.DIAMOND_BOOTS) {
    				 	if(e.getPlayer().hasPermission("lobbynk.remove")) {
    				 		e.getPlayer().getInventory().setArmorItem(3, Item.get(0));
    						 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.removed").replace("%cosmetic", responseName));
    						
    				 	} else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));return;
    				 	}
    			 e.getPlayer().getInventory().setArmorItem(3, Item.get(Item.DIAMOND_BOOTS));
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.equipped").replace("%cosmetic", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    		 
    	 } else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.iron")+ lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.helmet"))){
    		 if(e.getPlayer().hasPermission("lobbynk.cosmetic.iron.helmet")) {
    			 if(e.getPlayer().getInventory().getArmorItem(0).getId() == Item.IRON_HELMET) {
    				 	if(e.getPlayer().hasPermission("lobbynk.remove")) {
    				 		e.getPlayer().getInventory().setArmorItem(0, Item.get(0));
    						 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.removed").replace("%cosmetic", responseName));
    				
    				 	} else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));return;
    				 	}
    			 e.getPlayer().getInventory().setArmorItem(0, Item.get(Item.IRON_HELMET));
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.equipped").replace("%cosmetic", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    		 
    	 }else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.iron")+ lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.chestplate"))){
    		 if(e.getPlayer().hasPermission("lobbynk.cosmetic.iron.chestplate")) {
    			 if(e.getPlayer().getInventory().getArmorItem(1).getId() == Item.IRON_CHESTPLATE) {
    				 	if(e.getPlayer().hasPermission("lobbynk.remove")) {
    				 		e.getPlayer().getInventory().setArmorItem(1, Item.get(0));
    						 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.removed").replace("%cosmetic", responseName));
    			
    				 	} else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));return;
    				 	}
    			 e.getPlayer().getInventory().setArmorItem(1, Item.get(Item.IRON_CHESTPLATE));
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.equipped").replace("%cosmetic", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    		 
    	 }else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.iron")+ lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.leggings"))){
    		 if(e.getPlayer().hasPermission("lobbynk.cosmetic.iron.leggings")) {
    			 if(e.getPlayer().getInventory().getArmorItem(2).getId() == Item.IRON_LEGGINGS) {
    				 	if(e.getPlayer().hasPermission("lobbynk.remove")) {
    				 		e.getPlayer().getInventory().setArmorItem(2, Item.get(0));
    						 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.removed").replace("%cosmetic", responseName));
    					
    				 	} else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));return;
    				 	}
    			 e.getPlayer().getInventory().setArmorItem(2, Item.get(Item.IRON_LEGGINGS));
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.equipped").replace("%cosmetic", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    		 
    	 }else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.iron")+ lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.boots"))){
    		 if(e.getPlayer().hasPermission("lobbynk.cosmetic.iron.boots")) {
    			 if(e.getPlayer().getInventory().getArmorItem(3).getId() == Item.IRON_BOOTS) {
    				 	if(e.getPlayer().hasPermission("lobbynk.remove")) {
    				 		e.getPlayer().getInventory().setArmorItem(3, Item.get(0));
    						 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.removed").replace("%cosmetic", responseName));
    					
    				 	} else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));return;
    				 	}
    			 e.getPlayer().getInventory().setArmorItem(3, Item.get(Item.IRON_BOOTS));
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.equipped").replace("%cosmetic", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    		 
    	 }else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.chainmail")+ lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.helmet"))){
    		 if(e.getPlayer().hasPermission("lobbynk.cosmetic.chainmail.helmet")) {
    			 if(e.getPlayer().getInventory().getArmorItem(0).getId() == Item.CHAIN_HELMET) {
    				 	if(e.getPlayer().hasPermission("lobbynk.remove")) {
    				 		e.getPlayer().getInventory().setArmorItem(0, Item.get(0));
    						 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.removed").replace("%cosmetic", responseName));
    					
    				 	} else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));return;
    				 	}
    			 e.getPlayer().getInventory().setArmorItem(0, Item.get(Item.CHAIN_HELMET));
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.equipped").replace("%cosmetic", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    		 
    	 }else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.chainmail")+ lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.chestplate"))){
    		 if(e.getPlayer().hasPermission("lobbynk.cosmetic.chainmail.helmet")) {
    			 if(e.getPlayer().getInventory().getArmorItem(1).getId() == Item.CHAIN_CHESTPLATE) {
    				 	if(e.getPlayer().hasPermission("lobbynk.remove")) {
    				 		e.getPlayer().getInventory().setArmorItem(1, Item.get(0));
    						 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.removed").replace("%cosmetic", responseName));
    				
    				 	} else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));return;
    				 	}
    			 e.getPlayer().getInventory().setArmorItem(1, Item.get(Item.CHAIN_CHESTPLATE));
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.equipped").replace("%cosmetic", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    		 
    	 }else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.chainmail")+ lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.leggings"))){
    		 if(e.getPlayer().hasPermission("lobbynk.cosmetic.chainmail.leggings")) {
    			 if(e.getPlayer().getInventory().getArmorItem(2).getId() == Item.CHAIN_LEGGINGS) {
    				 	if(e.getPlayer().hasPermission("lobbynk.remove")) {
    				 		e.getPlayer().getInventory().setArmorItem(2, Item.get(0));
    						 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.removed").replace("%cosmetic", responseName));
    						
    				 	} else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));return;
    				 	}
    			 e.getPlayer().getInventory().setArmorItem(2, Item.get(Item.CHAIN_LEGGINGS));
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.equipped").replace("%cosmetic", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    		 
    	 }else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.chainmail")+ lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.boots"))){
    		 if(e.getPlayer().hasPermission("lobbynk.cosmetic.chainmail.boots")) {
    			 if(e.getPlayer().getInventory().getArmorItem(3).getId() == Item.CHAIN_BOOTS) {
    				 	if(e.getPlayer().hasPermission("lobbynk.remove")) {
    				 		e.getPlayer().getInventory().setArmorItem(3, Item.get(0));
    						 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.removed").replace("%cosmetic", responseName));
    						
    				 	} else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));return;
    				 	}
    			 e.getPlayer().getInventory().setArmorItem(3, Item.get(Item.CHAIN_BOOTS));
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.equipped").replace("%cosmetic", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    		 
    	 }else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.gold")+ lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.helmet"))){
    		 if(e.getPlayer().hasPermission("lobbynk.cosmetic.gold.helmet")) {
    			 if(e.getPlayer().getInventory().getArmorItem(0).getId() == Item.GOLD_HELMET) {
    				 	if(e.getPlayer().hasPermission("lobbynk.remove")) {
    				 		e.getPlayer().getInventory().setArmorItem(0, Item.get(0));
    						 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.removed").replace("%cosmetic", responseName));
    					
    				 	} else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));return;
    				 	}
    			 e.getPlayer().getInventory().setArmorItem(0, Item.get(Item.GOLD_HELMET));
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.equipped").replace("%cosmetic", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    		 
    	 }else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.gold")+ lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.chestplate"))){
    		 if(e.getPlayer().hasPermission("lobbynk.cosmetic.gold.helmet")) {
    			 if(e.getPlayer().getInventory().getArmorItem(1).getId() == Item.GOLD_CHESTPLATE) {
    				 	if(e.getPlayer().hasPermission("lobbynk.remove")) {
    				 		e.getPlayer().getInventory().setArmorItem(1, Item.get(0));
    						 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.removed").replace("%cosmetic", responseName));
    						
    				 	} else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));return;
    				 	}
    			 e.getPlayer().getInventory().setArmorItem(1, Item.get(Item.GOLD_CHESTPLATE));
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.equipped").replace("%cosmetic", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    		 
    	 }else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.gold")+ lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.leggings"))){
    		 if(e.getPlayer().hasPermission("lobbynk.cosmetic.gold.leggings")) {
    			 if(e.getPlayer().getInventory().getArmorItem(2).getId() == Item.GOLD_LEGGINGS) {
    				 	if(e.getPlayer().hasPermission("lobbynk.remove")) {
    				 		e.getPlayer().getInventory().setArmorItem(2, Item.get(0));
    						 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.removed").replace("%cosmetic", responseName));
    					
    				 	} else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));return;
    				 	}
    			 e.getPlayer().getInventory().setArmorItem(2, Item.get(Item.GOLD_LEGGINGS));
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.equipped").replace("%cosmetic", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    		 
    	 }else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.gold")+ lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.boots"))){
    		 if(e.getPlayer().hasPermission("lobbynk.cosmetic.gold.boots")) {
    			 if(e.getPlayer().getInventory().getArmorItem(3).getId() == Item.GOLD_BOOTS) {
    				 	if(e.getPlayer().hasPermission("lobbynk.remove")) {
    				 		e.getPlayer().getInventory().setArmorItem(3, Item.get(0));
    						 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.removed").replace("%cosmetic", responseName));
    						
    				 	} else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));return;
    				 	}
    			 e.getPlayer().getInventory().setArmorItem(3, Item.get(Item.GOLD_BOOTS));
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.equipped").replace("%cosmetic", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    		 
    	 }else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.leather")+ lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.helmet"))){
    		 if(e.getPlayer().hasPermission("lobbynk.cosmetic.leather.helmet")) {
    			 if(e.getPlayer().getInventory().getArmorItem(0).getId() == Item.LEATHER_CAP) {
    				 	if(e.getPlayer().hasPermission("lobbynk.remove")) {
    				 		e.getPlayer().getInventory().setArmorItem(0, Item.get(0));
    						 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.removed").replace("%cosmetic", responseName));
    					
    				 	} else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));return;
    				 	}
    			 e.getPlayer().getInventory().setArmorItem(0, Item.get(Item.LEATHER_CAP));
    			 
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.equipped").replace("%cosmetic", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    		 
    	 }else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.leather")+ lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.chestplate"))){
    		 if(e.getPlayer().hasPermission("lobbynk.cosmetic.leather.helmet")) {
    			 if(e.getPlayer().getInventory().getArmorItem(1).getId() == Item.LEATHER_TUNIC) {
    				 	if(e.getPlayer().hasPermission("lobbynk.remove")) {
    				 		e.getPlayer().getInventory().setArmorItem(1, Item.get(0));
    						 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.removed").replace("%cosmetic", responseName));
    					
    				 	} else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));return;
    				 	}
    			 e.getPlayer().getInventory().setArmorItem(1, Item.get(Item.LEATHER_TUNIC));
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.equipped").replace("%cosmetic", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    		 
    	 }else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.leather")+ lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.leggings"))){
    		 if(e.getPlayer().hasPermission("lobbynk.cosmetic.leather.leggings")) {
    			 if(e.getPlayer().getInventory().getArmorItem(2).getId() == Item.LEATHER_PANTS) {
    				 	if(e.getPlayer().hasPermission("lobbynk.remove")) {
    				 		e.getPlayer().getInventory().setArmorItem(1, Item.get(0));
    						 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.removed").replace("%cosmetic", responseName));
    			
    				 	} else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));return;
    				 	}
    			 e.getPlayer().getInventory().setArmorItem(2, Item.get(Item.LEATHER_PANTS));
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.equipped").replace("%cosmetic", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    		 
    	 }else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.leather")+ lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.boots"))){
    		 if(e.getPlayer().hasPermission("lobbynk.cosmetic.leather.boots")) {
    			 if(e.getPlayer().getInventory().getArmorItem(3).getId() == Item.LEATHER_BOOTS) {
    				 	if(e.getPlayer().hasPermission("lobbynk.remove")) {
    				 		e.getPlayer().getInventory().setArmorItem(3, Item.get(0));
    						 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.removed").replace("%cosmetic", responseName));
    					
    				 	} else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));return;
    				 	}
    			 e.getPlayer().getInventory().setArmorItem(3, Item.get(Item.LEATHER_BOOTS));
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.equipped").replace("%cosmetic", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    		 
    	 } else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.skull")) && e.getWindow() instanceof FormWindowSimple && ((FormWindowSimple) e.getWindow()).getTitle().equals(lang.get("Chest.cosmetic.other"))){
    		 if(e.getPlayer().hasPermission("lobbynk.cosmetic.skull")) {
    			 if(e.getPlayer().getInventory().getArmorItem(0).getId() == Item.SKULL && e.getPlayer().getInventory().getArmorItem(0).getDamage() == 0) {
    				 	if(e.getPlayer().hasPermission("lobbynk.remove")) {
    				 		e.getPlayer().getInventory().setArmorItem(0, Item.get(0));
    						 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.removed").replace("%cosmetic", responseName));
    					
    				 	} else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));return;
    				 	}
    			 Item i = Item.get(Item.SKULL);
    			 i.setDamage(0);
    			 e.getPlayer().getInventory().setArmorItem(0, i);
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.equipped").replace("%cosmetic", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    		 
    	 }else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.wither")) && e.getWindow() instanceof FormWindowSimple && ((FormWindowSimple) e.getWindow()).getTitle().equals(lang.get("Chest.cosmetic.other"))){
    		 if(e.getPlayer().hasPermission("lobbynk.cosmetic.wither")) {
    			 if(e.getPlayer().getInventory().getArmorItem(0).getId() == Item.SKULL && e.getPlayer().getInventory().getArmorItem(0).getDamage() == 1) {
    				 	if(e.getPlayer().hasPermission("lobbynk.remove")) {
    				 		e.getPlayer().getInventory().setArmorItem(0, Item.get(0));
    						 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.removed").replace("%cosmetic", responseName));
    				
    				 	} else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));return;
    				 	}
    			 Item i = Item.get(Item.SKULL);
    			 i.setDamage(1);
    			 e.getPlayer().getInventory().setArmorItem(0, i);
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.equipped").replace("%cosmetic", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    		 
    	 }else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.zombie")) && e.getWindow() instanceof FormWindowSimple && ((FormWindowSimple) e.getWindow()).getTitle().equals(lang.get("Chest.cosmetic.other"))){
    		 if(e.getPlayer().hasPermission("lobbynk.cosmetic.zombie")) {
    			 if(e.getPlayer().getInventory().getArmorItem(0).getId() == Item.SKULL && e.getPlayer().getInventory().getArmorItem(0).getDamage() == 2) {
    				 	if(e.getPlayer().hasPermission("lobbynk.remove")) {
    				 		e.getPlayer().getInventory().setArmorItem(0, Item.get(0));
    						 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.removed").replace("%cosmetic", responseName));
    						 return;
    				 	} else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));return;
    				 	}
    			 Item i = Item.get(Item.SKULL);
    			 i.setDamage(2);
    			 e.getPlayer().getInventory().setArmorItem(0, i);
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.equipped").replace("%cosmetic", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));return;
    		 }
    		 
    	 }else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.player")) && e.getWindow() instanceof FormWindowSimple && ((FormWindowSimple) e.getWindow()).getTitle().equals(lang.get("Chest.cosmetic.other"))){
    		 if(e.getPlayer().hasPermission("lobbynk.cosmetic.player")) {
    			 if(e.getPlayer().getInventory().getArmorItem(0).getId() == Item.SKULL && e.getPlayer().getInventory().getArmorItem(0).getDamage() == 3) {
    				 	if(e.getPlayer().hasPermission("lobbynk.remove")) {
    				 		e.getPlayer().getInventory().setArmorItem(0, Item.get(0));
    						 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.removed").replace("%cosmetic", responseName));
    						 return;
    				 	} else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));return;
    				 	}
    			 Item i = Item.get(Item.SKULL);
    			 i.setDamage(3);
    			 e.getPlayer().getInventory().setArmorItem(0, i);
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.equipped").replace("%cosmetic", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));return;
    		 }
    		 
    	 } else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.creeper")) && e.getWindow() instanceof FormWindowSimple && ((FormWindowSimple) e.getWindow()).getTitle().equals(lang.get("Chest.cosmetic.other"))){
    		 if(e.getPlayer().hasPermission("lobbynk.cosmetic.creeper")) {
    			 if(e.getPlayer().getInventory().getArmorItem(0).getId() == Item.SKULL && e.getPlayer().getInventory().getArmorItem(0).getDamage() == 4) {
    				 	if(e.getPlayer().hasPermission("lobbynk.remove")) {
    				 		e.getPlayer().getInventory().setArmorItem(0, Item.get(0));
    						 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.removed").replace("%cosmetic", responseName));
    						 return;
    				 	} else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));return;
    				 	}
    			 Item i = Item.get(Item.SKULL);
    			 i.setDamage(4);
    			 e.getPlayer().getInventory().setArmorItem(0, i);
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.equipped").replace("%cosmetic", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    		 
    	 }else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.dragon")) && e.getWindow() instanceof FormWindowSimple && ((FormWindowSimple) e.getWindow()).getTitle().equals(lang.get("Chest.cosmetic.other"))){
    		 if(e.getPlayer().hasPermission("lobbynk.cosmetic.dragon")) {
    			 if(e.getPlayer().getInventory().getArmorItem(0).getId() == Item.SKULL && e.getPlayer().getInventory().getArmorItem(0).getDamage() == 5) {
    				 	if(e.getPlayer().hasPermission("lobbynk.remove")) {
    				 		e.getPlayer().getInventory().setArmorItem(0, Item.get(0));
    						 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.removed").replace("%cosmetic", responseName));
    				 	} else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    				 	return;
    				 	}
    			 Item i = Item.get(Item.SKULL);
    			 i.setDamage(5);
    			 e.getPlayer().getInventory().setArmorItem(0, i);
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.equipped").replace("%cosmetic", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    			
    		 }
    		 
    	 } else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.shell"))) {
    		 if(e.getPlayer().hasPermission("lobbynk.cosmetic.shell")) {
    			 if(e.getPlayer().getInventory().getArmorItem(0).getId() == Item.TURTLE_SHELL) {
    				 	if(e.getPlayer().hasPermission("lobbynk.remove")) {
    				 		e.getPlayer().getInventory().setArmorItem(0, Item.get(0));
    						 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.removed").replace("%cosmetic", responseName));
    						 
    				 	} else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    				 	return;
    				 	}
    			 Item i = Item.get(Item.TURTLE_SHELL);
    			 e.getPlayer().getInventory().setArmorItem(0, i);
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.equipped").replace("%cosmetic", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    	 } else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.elytra"))) {
    		 if(e.getPlayer().hasPermission("lobbynk.cosmetic.elytra")) {
    			 if(e.getPlayer().getInventory().getArmorItem(1).getId() == Item.ELYTRA) {
    				 	if(e.getPlayer().hasPermission("lobbynk.remove")) {
    				 		e.getPlayer().getInventory().setArmorItem(1, Item.get(0));
    						 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.removed").replace("%cosmetic", responseName));
    						
    				 	} else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    				 	 return;
    				 	}
    			 Item i = Item.get(Item.ELYTRA);
    			 e.getPlayer().getInventory().setArmorItem(1, i);
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.cosmetic.equipped").replace("%cosmetic", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    	 }
    	 
    	 
    	 else if(responseName.equals(lang.getString(p.getLocale().toString() + "_" +"Chest.walking.angry"))) {
    		 if(e.getPlayer().hasPermission("lobbynk.effect.angry")) {
    			 if(map.containsKey(e.getPlayer().getName())) {
    				 if(map.get(e.getPlayer().getName()).toString().equals("angry")) {
    					 if(e.getPlayer().hasPermission("lobbynk.remove")) {
    					 map.remove(e.getPlayer().getName());
    					 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Effectremoved").replace("%effect", responseName));
    					 
    					 } else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    					 return;
    				 }
    			 }
    			 map.put(e.getPlayer().getName(), "angry");
    			
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Effectselected").replace("%effect", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    	 }
    	 else if(responseName.equals(lang.getString(p.getLocale().toString() + "_" +"Chest.walking.note"))) {
    		 if(e.getPlayer().hasPermission("lobbynk.effect.note")) {
    			 if(map.containsKey(e.getPlayer().getName())) {
    				 if(map.get(e.getPlayer().getName()).toString().equals("note")) {
    					 if(e.getPlayer().hasPermission("lobbynk.remove")) {
    					 map.remove(e.getPlayer().getName());
    					 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Effectremoved").replace("%effect", responseName));
    					 
    					 } else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    					 return;
    				 }
    			 }
    			 map.put(e.getPlayer().getName(), "note");
    			
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Effectselected").replace("%effect", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    	 }
    	 else if(responseName.equals(lang.getString(p.getLocale().toString() + "_" +"Chest.walking.bonemeal"))) {
    		 if(e.getPlayer().hasPermission("lobbynk.effect.bonemeal")) {
    			 if(map.containsKey(e.getPlayer().getName())) {
    				 if(map.get(e.getPlayer().getName()).toString().equals("bonemeal")) {
    					 if(e.getPlayer().hasPermission("lobbynk.remove")) {
    					 map.remove(e.getPlayer().getName());
    					 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Effectremoved").replace("%effect", responseName));
    					 
    					 } else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    					 return;
    				 }
    			 }
    			 map.put(e.getPlayer().getName(), "bonemeal");
    			
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Effectselected").replace("%effect", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    	 }else if(responseName.equals(lang.getString(p.getLocale().toString() + "_" +"Chest.walking.bubble"))) {
    		 if(e.getPlayer().hasPermission("lobbynk.effect.bubble")) {
    			 if(map.containsKey(e.getPlayer().getName())) {
    				 if(map.get(e.getPlayer().getName()).toString().equals("bubble")) {
    					 if(e.getPlayer().hasPermission("lobbynk.remove")) {
    					 map.remove(e.getPlayer().getName());
    					 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Effectremoved").replace("%effect", responseName));
    					 
    					 } else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    					 return;
    				 }
    			 }
    			 map.put(e.getPlayer().getName(), "bubble");
    			 
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Effectselected").replace("%effect", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    	 }else if(responseName.equals(lang.getString(p.getLocale().toString() + "_" +"Chest.walking.explode"))) {
    		 if(e.getPlayer().hasPermission("lobbynk.effect.explode")) {
    			 if(map.containsKey(e.getPlayer().getName())) {
    				 if(map.get(e.getPlayer().getName()).toString().equals("explode")) {
    					 if(e.getPlayer().hasPermission("lobbynk.remove")) {
    					 map.remove(e.getPlayer().getName());
    					 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Effectremoved").replace("%effect", responseName));
    					 
    					 } else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    					 return;
    				 }
    			 }
    			 map.put(e.getPlayer().getName(), "explode");
    			 
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Effectselected").replace("%effect", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    	 }else if(responseName.equals(lang.getString(p.getLocale().toString() + "_" +"Chest.walking.heart"))) {
    		 if(e.getPlayer().hasPermission("lobbynk.effect.heart")) {
    			 if(map.containsKey(e.getPlayer().getName())) {
    				 if(map.get(e.getPlayer().getName()).toString().equals("heart")) {
    					 if(e.getPlayer().hasPermission("lobbynk.remove")) {
    					 map.remove(e.getPlayer().getName());
    					 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Effectremoved").replace("%effect", responseName));
    					 
    					 } else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    					 return;
    				 }
    			 }
    			 map.put(e.getPlayer().getName(), "heart");
    		
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Effectselected").replace("%effect", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    	 }else if(responseName.equals(lang.getString(p.getLocale().toString() + "_" +"Chest.walking.flame"))) {
    		 if(e.getPlayer().hasPermission("lobbynk.effect.flame")) {
    			 if(map.containsKey(e.getPlayer().getName())) {
    				 if(map.get(e.getPlayer().getName()).toString().equals("flame")) {
    					 if(e.getPlayer().hasPermission("lobbynk.remove")) {
    					 map.remove(e.getPlayer().getName());
    					 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Effectremoved").replace("%effect", responseName));
    					 
    					 } else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    					 return;
    				 }
    			 }
    			 map.put(e.getPlayer().getName(), "flame");
    			 
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Effectselected").replace("%effect", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    	 }else if(responseName.equals(lang.getString(p.getLocale().toString() + "_" +"Chest.remove"))) {
    		 if(e.getPlayer().hasPermission("lobbynk.remove")) {
    			 map.remove(e.getPlayer().getName());
    			 if(de.buddelbubi.events.PetEvents.map.containsKey(e.getPlayer())) {
    			 de.buddelbubi.events.PetEvents.map.get(e.getPlayer()).close();
    			 de.buddelbubi.events.PetEvents.map.remove(e.getPlayer());
    			 }
    			 e.getPlayer().removeAllEffects();
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Chest.remove_everything"));
    			 e.getPlayer().getInventory().setArmorItem(0, Item.get(0));
    			 e.getPlayer().getInventory().setArmorItem(1, Item.get(0));
    			 e.getPlayer().getInventory().setArmorItem(2, Item.get(0));
    			 e.getPlayer().getInventory().setArmorItem(3, Item.get(0));
    			 
    			 
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    	 }else if(responseName.equals(lang.getString(p.getLocale().toString() + "_" +"Chest.walking.redstone"))) {
    		 if(e.getPlayer().hasPermission("lobbynk.effect.redstone")) {
    			 if(map.containsKey(e.getPlayer().getName())) {
    				 if(map.get(e.getPlayer().getName()).toString().equals("redstone")) {
    					 if(e.getPlayer().hasPermission("lobbynk.remove")) {
    					 map.remove(e.getPlayer().getName());
    					 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Effectremoved").replace("%effect", responseName));
    					 
    					 } else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    					 return;
    				 }
    			 }
    			 map.put(e.getPlayer().getName(), "redstone");
    		
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Effectselected").replace("%effect", responseName));
    		 } else {
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    		 }
    		 }else if(responseName.equals(lang.getString(p.getLocale().toString() + "_" +"Chest.walking.ink"))) {
    			 if(e.getPlayer().hasPermission("lobbynk.effect.ink")) {
    				 if(map.containsKey(e.getPlayer().getName())) {
    					 if(map.get(e.getPlayer().getName()).toString().equals("ink")) {
    						 if(e.getPlayer().hasPermission("lobbynk.remove")) {
    						 map.remove(e.getPlayer().getName());
    						 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Effectremoved").replace("%effect", responseName));
    						 
    						 } else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    						 return;
    					 }
    				 }
    				 map.put(e.getPlayer().getName(), "ink");
    				 
    				 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Effectselected").replace("%effect", responseName));
    			 } else {
    				 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    			 }}else if(responseName.equals(lang.getString(p.getLocale().toString() + "_" +"Chest.walking.enchant"))) {
    				 if(e.getPlayer().hasPermission("lobbynk.effect.enchant")) {
    					 if(map.containsKey(e.getPlayer().getName())) {
    						 if(map.get(e.getPlayer().getName()).toString().equals("enchant")) {
    							 if(e.getPlayer().hasPermission("lobbynk.remove")) {
    							 map.remove(e.getPlayer().getName());
    							 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Effectremoved").replace("%effect", responseName));
    							 
    							 } else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    							 return;
    						 }
    					 }
    					 map.put(e.getPlayer().getName(), "enchant");
    					 
    					 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Effectselected").replace("%effect", responseName));
    				 } else {
    					 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    				 }}else if(responseName.equals(lang.getString(p.getLocale().toString() + "_" +"Chest.effects"))) {
    					 FormWindowSimple w = new FormWindowSimple(lang.getString(p.getLocale().toString() + "_" +"Chest.effects"), "");
    					 ElementButtonImageData speed = new ElementButtonImageData("path", "textures/gui/newgui/mob_effects/speed_effect.png");
    					 ElementButtonImageData jumpboost = new ElementButtonImageData("path", "textures/gui/newgui/mob_effects/jump_boost_effect.png");
    					 ElementButtonImageData levitation = new ElementButtonImageData("path", "textures/gui/newgui/mob_effects/levitation_effect.png");
    					 ElementButtonImageData invisibility = new ElementButtonImageData("path", "textures/gui/newgui/mob_effects/invisibility_effect.png");
    					 ElementButtonImageData blindness = new ElementButtonImageData("path", "textures/gui/newgui/mob_effects/blindness_effect.png");
    					 ElementButtonImageData nausea = new ElementButtonImageData("path", "textures/gui/newgui/mob_effects/nausea_effect.png");
    					 ElementButtonImageData nightvision = new ElementButtonImageData("path", "textures/gui/newgui/mob_effects/night_vision_effect.png");
    			
    					 int counter = 0;
    					if(e.getPlayer().hasPermission("lobbynk.effect.speed")) { w.addButton(new ElementButton(lang.getString(p.getLocale().toString() + "_" +"Chest.effect.speed"), speed));counter++;}
    					if(e.getPlayer().hasPermission("lobbynk.effect.jumpboost")) {w.addButton(new ElementButton(lang.getString(p.getLocale().toString() + "_" +"Chest.effect.jumpboost"), jumpboost));counter++;}
    					if(e.getPlayer().hasPermission("lobbynk.effect.levitation")) {w.addButton(new ElementButton(lang.getString(p.getLocale().toString() + "_" +"Chest.effect.levitation"), levitation));counter++;}
    					if(e.getPlayer().hasPermission("lobbynk.effect.invisibility")) {w.addButton(new ElementButton(lang.getString(p.getLocale().toString() + "_" +"Chest.effect.invisibility"), invisibility));counter++;}
    					if(e.getPlayer().hasPermission("lobbynk.effect.blindness")) {w.addButton(new ElementButton(lang.getString(p.getLocale().toString() + "_" +"Chest.effect.blindness"), blindness));counter++;}
    					if(e.getPlayer().hasPermission("lobbynk.effect.nausea")) {w.addButton(new ElementButton(lang.getString(p.getLocale().toString() + "_" +"Chest.effect.nausea"), nausea));counter++;}
    					if(e.getPlayer().hasPermission("lobbynk.effect.nightvision")) { w.addButton(new ElementButton(lang.getString(p.getLocale().toString() + "_" +"Chest.effect.nightvision"), nightvision));counter++;}
    				 if(counter == 0) w.setContent(lang.getString(p.getLocale().toString() + "_" +"nopermission"));
    					
    					 e.getPlayer().showFormWindow(w);
    				 }else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.effect.speed"))) {
    					 if(e.getPlayer().hasPermission("lobbynk.effect.speed")) {
    						 
    						 if(e.getPlayer().hasEffect(Effect.SPEED)) {
    							 if(e.getPlayer().hasPermission("lobbynk.remove")) {
    								 e.getPlayer().removeEffect(Effect.SPEED);
    								 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Effectremoved").replace("%effect", responseName));
    							 } else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission")); return;
    						 }
    					 Effect effect = new Effect(Effect.SPEED, lang.getString(p.getLocale().toString() + "_" +"Chest.effect.speed"), 1, 1, 1);
    					 effect.setVisible(false);
    					 effect.setAmplifier(1);
    					 effect.setDuration(20*120);
    					 e.getPlayer().addEffect(effect);
    					 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Effectselected").replace("%effect", responseName));
    				 }else{
    				e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));	 
    				 }
    				 }else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.effect.jumpboost"))) {
    					 if(e.getPlayer().hasPermission("lobbynk.effect.jumpboost")) {
    						 if(e.getPlayer().hasEffect(Effect.JUMP)) {
    							 if(e.getPlayer().hasPermission("lobbynk.remove")) {
    								 e.getPlayer().removeEffect(Effect.JUMP);
    								 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Effectremoved").replace("%effect", responseName));
    							 } else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission")); return;
    						 }
    					 Effect effect = new Effect(Effect.JUMP, lang.getString(p.getLocale().toString() + "_" +"Chest.effect.jumpboost"), 1, 1, 1);
    					 effect.setVisible(false);
    					 effect.setDuration(20*120);
    					 effect.setAmplifier(1);
    					 e.getPlayer().addEffect(effect);
    					
    					 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Effectselected").replace("%effect", responseName));
    				 }else{
    				e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));	 
    				 }
    				 }else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.effect.levitation"))) {
    					 if(e.getPlayer().hasPermission("lobbynk.effect.levitation")) {
    						 if(e.getPlayer().hasEffect(Effect.LEVITATION)) {
    							 if(e.getPlayer().hasPermission("lobbynk.remove")) {
    								 e.getPlayer().removeEffect(Effect.LEVITATION);
    								 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Effectremoved").replace("%effect", responseName));
    							 } else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission")); return;
    						 }
    					 Effect effect = new Effect(Effect.LEVITATION, lang.getString(p.getLocale().toString() + "_" +"Chest.effect.levitation"), 1, 1, 1);
    					 effect.setVisible(false);
    					 effect.setAmplifier(1);
    					 effect.setDuration(20*120);
    					 e.getPlayer().addEffect(effect);
    					 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Effectselected").replace("%effect", responseName));
    				 }else{
    				e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));	 
    				 }
    				 }else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.effect.invisibility"))) {
    					 if(e.getPlayer().hasPermission("lobbynk.effect.invisibility")) {
    						 if(e.getPlayer().hasEffect(Effect.INVISIBILITY)) {
    							 if(e.getPlayer().hasPermission("lobbynk.remove")) {
    								 e.getPlayer().removeEffect(Effect.INVISIBILITY);
    								 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Effectremoved").replace("%effect", responseName));
    							 } else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission")); return;
    						 }
    					 Effect effect = new Effect(Effect.INVISIBILITY, lang.getString(p.getLocale().toString() + "_" +"Chest.effect.invisibility"), 1, 1, 1);
    					 effect.setVisible(false);
    					 effect.setAmplifier(1);
    					 effect.setDuration(20*120);
    					 e.getPlayer().addEffect(effect);
    					 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Effectselected").replace("%effect", responseName));
    				 }else{
    				e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));	 
    				 }
    				 }else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.effect.blindness"))) {
    					 if(e.getPlayer().hasPermission("lobbynk.effect.blindness")) {
    						 if(e.getPlayer().hasEffect(Effect.BLINDNESS)) {
    							 if(e.getPlayer().hasPermission("lobbynk.remove")) {
    								 e.getPlayer().removeEffect(Effect.BLINDNESS);
    								 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Effectremoved").replace("%effect", responseName));
    							 } else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission")); return;
    						 }
    					 Effect effect = new Effect(Effect.BLINDNESS, lang.getString(p.getLocale().toString() + "_" +"Chest.effect.blindness"), 1, 1, 1);
    					 effect.setVisible(false);
    					 effect.setAmplifier(1);
    					 effect.setDuration(20*120);
    					 e.getPlayer().addEffect(effect);
    					 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Effectselected").replace("%effect", responseName));
    				 }else{
    				e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));	 
    				 }
    				 }else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.effect.nausea"))) {
    					 if(e.getPlayer().hasPermission("lobbynk.effect.nausea")) {
    						 if(e.getPlayer().hasEffect(Effect.NAUSEA)) {
    							 if(e.getPlayer().hasPermission("lobbynk.remove")) {
    								 e.getPlayer().removeEffect(Effect.NAUSEA);
    								 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Effectremoved").replace("%effect", responseName));
    							 } else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission")); return;
    						 }
    					 Effect effect = new Effect(Effect.NAUSEA, lang.getString(p.getLocale().toString() + "_" +"Chest.effect.nausea"), 1, 1, 1);
    					 effect.setVisible(false);
    					 effect.setAmplifier(1);
    					 effect.setDuration(20*120);
    					 e.getPlayer().addEffect(effect);
    					 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Effectselected").replace("%effect", responseName));
    				 }else{
    				e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));	 
    				 }
    				 }else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"Chest.effect.nightvision"))) {
    					 if(e.getPlayer().hasPermission("lobbynk.effect.nightvision")) {
    						 if(e.getPlayer().hasEffect(Effect.NIGHT_VISION)) {
    							 if(e.getPlayer().hasPermission("lobbynk.remove")) {
    								 e.getPlayer().removeEffect(Effect.NIGHT_VISION);
    								 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Effectremoved").replace("%effect", responseName));
    							 } else e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission")); return;
    						 }
    					 Effect effect = new Effect(Effect.NIGHT_VISION, lang.getString(p.getLocale().toString() + "_" +"Chest.effect.nightvision"), 1, 1, 1);
    					 effect.setVisible(false);
    					 effect.setAmplifier(1);
    					 effect.setDuration(20*120);
    					 e.getPlayer().addEffect(effect);
    					 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Effectselected").replace("%effect", responseName));
    				 }else{
    				e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"nopermission"));	 
    				 }
    				 } else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"playershider.hideall"))) {
    							saves.set(e.getPlayer().getName()+".hidden", true);	
    					 for(Player p2 : getServer().getOnlinePlayers().values()) {
    						 e.getPlayer().hidePlayer(p2);
    					 }
    					 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"playershider.hidenall"));
    					 if(config.getBoolean("soundeffects")) {
    							e.getPlayer().getLevel().addSound(new Vector3(e.getPlayer().getX(), e.getPlayer().getY(), e.getPlayer().getZ()), Sound.MOB_BLAZE_BREATHE, 1, 1, Collections.singletonList(e.getPlayer()));
    							}
    					 
    				 } else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"playershider.hidevip"))) {
    						saves.set(e.getPlayer().getName()+".hidden", true);	
    				 for(Player p2 : getServer().getOnlinePlayers().values()) {
    					 e.getPlayer().showPlayer(p2);
    					 if(!p.hasPermission("lobbynk.vip")) {
    					 e.getPlayer().hidePlayer(p2);
    					 }
    				 }
    				 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"playershider.hidenvip"));
    				 if(config.getBoolean("soundeffects")) {
    						e.getPlayer().getLevel().addSound(new Vector3(e.getPlayer().getX(), e.getPlayer().getY(), e.getPlayer().getZ()), Sound.MOB_BLAZE_BREATHE, 1, 1, Collections.singletonList(e.getPlayer()));
    						}
    			 } else if(responseName.equalsIgnoreCase(lang.getString(p.getLocale().toString() + "_" +"playershider.hidenoone"))) {
    					saves.set(e.getPlayer().getName()+".hidden", false);	
    			 for(Player p2 : getServer().getOnlinePlayers().values()) {
    				 e.getPlayer().showPlayer(p2);
    			 }
    			 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"playershider.hidennoone"));
    			 if(config.getBoolean("soundeffects")) {
    					e.getPlayer().getLevel().addSound(new Vector3(e.getPlayer().getX(), e.getPlayer().getY(), e.getPlayer().getZ()), Sound.MOB_BLAZE_BREATHE, 1, 1, Collections.singletonList(e.getPlayer()));
    					}
    			 
    		 } 


    if(responseName.replace("§a", "").replace("§c", "").equals(lang.getString(p.getLocale().toString() + "_" +"settings.doublejump"))) {
    	
    	 if(responseName.startsWith("§a")) {
    		 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"settings.disabled").replace("%mode", responseName.replace("§a", "").replace("§c", "")));
    		 playersettings.put(e.getPlayer().getName() + ".doublejump", false);
    		 e.getPlayer().setAllowFlight(false);
    			e.getPlayer().getAllowFlight();
    	 } else {
    		 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"settings.enabled").replace("%mode", responseName.replace("§a", "").replace("§c", ""))); 
    		 playersettings.put(e.getPlayer().getName() + ".doublejump", true);
    		 playersettings.put(e.getPlayer().getName() + ".fly", false);
    		 flyable.add(e.getPlayer().getName());
    		 e.getPlayer().setAllowFlight(false);
    			e.getPlayer().getAllowFlight();
    			e.getPlayer().setMotion(e.getPlayer().getLocation().getDirectionVector().multiply(1).add(0, -300, 0));
    			getServer().getScheduler().scheduleDelayedTask(new Runnable() {
    				
    				@Override
    				public void run() {
    					 flyable.remove(e.getPlayer().getName());
    				}
    			}, 10);
    			
    	 }
    } else if (responseName.replace("§a", "").replace("§c", "").equals(lang.getString(p.getLocale().toString() + "_" +"settings.fly"))) {
    	 if(responseName.startsWith("§a")) {
    		 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"settings.disabled").replace("%mode", responseName.replace("§a", "").replace("§c", "")));
    		 playersettings.put(e.getPlayer().getName() + ".fly", false);
    		 flyable.add(e.getPlayer().getName());
    		 e.getPlayer().setAllowFlight(false);
    		 e.getPlayer().getAllowFlight();
    			e.getPlayer().setMotion(e.getPlayer().getLocation().getDirectionVector().multiply(1).add(0, -300, 0));
    getServer().getScheduler().scheduleDelayedTask(new Runnable() {
    				
    				@Override
    				public void run() {
    					 flyable.remove(e.getPlayer().getName());
    				}
    			}, 10);
    	 } else {
    		 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"settings.enabled").replace("%mode", responseName.replace("§a", "").replace("§c", ""))); 
    		 playersettings.put(e.getPlayer().getName() + ".fly", true);
    		 e.getPlayer().setAllowFlight(true);
    		 playersettings.put(e.getPlayer().getName() + ".doublejump", false);
    	 }
    }else if(responseName.replace("§a", "").replace("§c", "").equals(lang.getString(p.getLocale().toString() + "_" +"settings.jumppad"))) {
    	 if(responseName.startsWith("§a")) {
    		 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"settings.disabled").replace("%mode", responseName.replace("§a", "").replace("§c", "")));
    		 playersettings.put(e.getPlayer().getName() + ".jumppad", false);
    	 } else {
    		 e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"settings.enabled").replace("%mode", responseName.replace("§a", "").replace("§c", ""))); 
    		 playersettings.put(e.getPlayer().getName() + ".jumppad", true);
    	 }
    } else if (responseName.replace("§a", "").replace("§c", "").equals(lang.getString(p.getLocale().toString() + "_" +"settings.forcefield"))) {
    	if(responseName.startsWith("§a")) {
    		e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"settings.disabled").replace("%mode", responseName.replace("§a", "").replace("§c", ""))); 
    		 playersettings.put(e.getPlayer().getName() + ".forcefield", false);
    	} else {
    		e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"settings.enabled").replace("%mode", responseName.replace("§a", "").replace("§c", ""))); 
    		 playersettings.put(e.getPlayer().getName() + ".forcefield", true);
    	}
    }

    if(responseName.equals("§eFriends")) {
    	try {
    		if(Server.getInstance().getPluginManager().getPlugin("FriendSystem").isEnabled()) {
    			
    			Server.getInstance().dispatchCommand(e.getPlayer(), new Config(new File(Server.getInstance().getPluginManager().getPlugin("FriendSystem").getDataFolder() , "config.yml")).getString("Commands.Friend.Name"));
    			
    		}
    	} catch (Exception e2) {
    		
    	}
    }

    	 if(de.buddelbubi.events.onInteract.ls.contains(e.getPlayer().getName())) {
    		 de.buddelbubi.events.onInteract.ls.remove(e.getPlayer().getName());
    		 responseName = responseName.split("\\s+")[0];
    			 for(String s : MultiLobbyManager.lobbyservers.keySet()) {
    				 if(s.contains(responseName.replace(lang.getString(p.getLocale().toString() + "_" +"lobbyselector.serverprefix"), ""))) responseName = s;
    				 
    			 }
    		 if(MultiLobbyManager.lobbyservers.containsKey(responseName.replace(lang.getString(p.getLocale().toString() + "_" +"lobbyselector.serverprefix"), ""))){
    			
    			 
    			 if(MultiLobbyManager.lobbyservers.get(responseName.replace(lang.getString(p.getLocale().toString() + "_" +"lobbyselector.serverprefix"), "")).startsWith("bungee[")) {
    				 String server = MultiLobbyManager.lobbyservers.get(responseName.replace(lang.getString(p.getLocale().toString() + "_" +"lobbyselector.serverprefix"), "")).replace("bungee[", "").replace("]", "");
    				 ScriptCustomEventPacket packet = new ScriptCustomEventPacket();
    				 packet.eventName = "bungeecord:main";
    				 ByteArrayDataOutput out = ByteStreams.newDataOutput();
    				 out.writeUTF("Connect");
    				 out.writeUTF(server);
    				 packet.eventData = out.toByteArray();
    				 e.getPlayer().dataPacket(packet);
    				 
    				 return;
    			 }
    			String[] address = loc.getString("server." + responseName.replace(lang.getString(p.getLocale().toString() + "_" +"lobbyselector.serverprefix"), "")).split(":");
    			 
    			 if(address.length == 2) {
    				 e.getPlayer().transfer(new InetSocketAddress(address[0], Integer.parseInt(address[1])));
    			 } else e.getPlayer().transfer(new InetSocketAddress(address[0], 19132));
    			 return;
    		 }
    		 String w = "." +responseName.replace(lang.getString(p.getLocale().toString() + "_" +"lobbyselector.prefix"), "");
    		 if(responseName.startsWith(lang.getString(p.getLocale().toString() + "_" +"lobbyselector.prefix")) && (getServer().getLevelByName(responseName.replace(lang.getString(p.getLocale().toString() + "_" +"lobbyselector.prefix"), "")).getPlayers().size() < config.getInt("maxplayersperlobby") ||
    				 e.getPlayer().hasPermission("lobbynk.fulllobby"))) {
    		if(loc.exists("spawn.x"+w)) {
    		 double x = loc.getDouble("spawn.x"+w);
    			double y = loc.getDouble("spawn.y"+w);
    			double z = loc.getDouble("spawn.z"+w);
    			float yaw = (float) loc.getDouble("spawn.yaw"+w);
    			float pitch = (float) loc.getDouble("spawn.pitch"+w);
    			Level le = Server.getInstance().getLevelByName(loc.getString("spawn.world"+w));
    			Location tp = new Location(x,y,z,yaw,pitch,le);
    		
    			
    			
    			e.getPlayer().teleport(tp);
    			e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"lobbyconnected").replace("%lobby", responseName.replace(lang.getString(p.getLocale().toString() + "_" +"lobbyselector.prefix"), "")));
    		} else {
    			e.getPlayer().teleport(getServer().getLevelByName(w).getSpawnLocation());
    			e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Locations.Teleport.missing"));
    		}
    	 } else {
    		 if(responseName.startsWith(lang.getString(p.getLocale().toString() + "_" +"lobbyselector.prefix")))  e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"fulllobby").replace("%lobby", responseName.replace(lang.getString(p.getLocale().toString() + "_" +"lobbyselector.prefix"), "")));
    	 }
    		 if(responseName.startsWith(lang.getString(p.getLocale().toString() + "_" +"lobbyselector.vipprefix")) && (getServer().getLevelByName("VIP-" + responseName.replace(lang.getString(p.getLocale().toString() + "_" +"lobbyselector.vipprefix"), "")).getPlayers().size() < config.getInt("maxplayersperlobby") || 
    				 e.getPlayer().hasPermission("lobbynk.fulllobby"))) {
    			 w = ".VIP-" +responseName.replace(lang.getString(p.getLocale().toString() + "_" +"lobbyselector.vipprefix"), "");
    			 if(loc.exists("spawn.x" +w)) {
    				
    				double x = loc.getDouble("spawn.x"+w);
    				double y = loc.getDouble("spawn.y"+w);
    				double z = loc.getDouble("spawn.z"+w);
    				float yaw = (float) loc.getDouble("spawn.yaw"+w);
    				float pitch = (float) loc.getDouble("spawn.pitch"+w);
    				Level le = Server.getInstance().getLevelByName(loc.getString("spawn.world"+w));
    				Location tp = new Location(x,y,z,yaw,pitch,le);

    				e.getPlayer().teleport(tp);
    				e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"lobbyconnected").replace("%lobby", responseName.replace(lang.getString(p.getLocale().toString() + "_" +"lobbyselector.prefix"), "")));
    			 } else {
    				 e.getPlayer().teleport(getServer().getLevelByName(w).getSpawnLocation());
    					e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"Locations.Teleport.missing"));
    			 }
    			 }else {
    				 if(responseName.startsWith(lang.getString(p.getLocale().toString() + "_" +"lobbyselector.vipprefix")))  e.getPlayer().sendMessage(lang.getString(p.getLocale().toString() + "_" +"fulllobby").replace("%lobby", responseName.replace(lang.getString(p.getLocale().toString() + "_" +"lobbyselector.vipprefix"), "")));
    			 }
    		 
    		
    	 }
    	}catch (Exception ignore) {
    	
    	}
    }

    @EventHandler
    public void oPtf(PlayerToggleFlightEvent e) {
        if (e.getPlayer().hasPermission("lobbynk.doublejump") && (Boolean.valueOf(playersettings.getString(String.valueOf(e.getPlayer().getName()) + ".doublejump")).booleanValue() || !playersettings.has(String.valueOf(e.getPlayer().getName()) + ".doublejump")) && !flyable.contains(e.getPlayer().getName())) {
            Player p = e.getPlayer();
            if (p.isCreative()) {
                return;
            }
            e.setCancelled(true);
            Level level = p.getLevel();
            Vector3 vec = new Vector3(p.getX(), p.getY(), p.getZ());
            if (config.getBoolean("soundeffects")) {
                level.addSound(vec, Sound.MOB_ENDERDRAGON_FLAP);
            }
            p.setAllowFlight(false);
            p.getAllowFlight();
            p.setMotion(p.getLocation().getDirectionVector().multiply(2.5).add(0.0, 1.5, 0.0));
        }
    }

    @EventHandler
    public void onScroll(PlayerItemHeldEvent e) {
        if ((!config.getBoolean("multiworld") || MultiLobbyManager.lobbyworlds.contains(e.getPlayer().getLevel().getName())) && config.getBoolean("soundeffects")) {
            e.getPlayer().getLevel().addSound(new Vector3(e.getPlayer().getX(), e.getPlayer().getY(), e.getPlayer().getZ()), Sound.BLOCK_ITEMFRAME_ADD_ITEM, 1.0f, 2.0f, Collections.singletonList(e.getPlayer()));
        }
    }

    public static String versionCheck() {
        return Server.getInstance().getPluginManager().getPlugin("LobbyNK").getDescription().getVersion();
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent e) {
        if ((!config.getBoolean("multiworld") || MultiLobbyManager.lobbyworlds.contains(e.getEntity().getLevel().getName())) && config.getBoolean("NoExplosions")) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onCmd(PlayerCommandPreprocessEvent e) {
        List blockedCommands;
        if (e.getMessage().equals("/plugins")) {
            if (e.getPlayer().isOp()) {
                return;
            }
            e.setCancelled(true);
            e.getPlayer().sendMessage("[1] \u00a7aLobbyNK " + this.getServer().getPluginManager().getPlugin("LobbyNK").getDescription().getVersion() + " by Buddelbubi");
        }
        if (e.getMessage().equals("/pl")) {
            if (e.getPlayer().isOp()) {
                return;
            }
            e.setCancelled(true);
            e.getPlayer().sendMessage("[1] \u00a7aLobbyNK " + this.getServer().getPluginManager().getPlugin("LobbyNK").getDescription().getVersion() + " by Buddelbubi");
        }
        if (e.getMessage().equals("/about")) {
            if (e.getPlayer().isOp()) {
                return;
            }
            e.setCancelled(true);
            e.getPlayer().sendMessage("\u00a7cLobbyNK " + this.getServer().getPluginManager().getPlugin("LobbyNK").getDescription().getVersion() + " by Buddelbubi");
        }
        if (e.getMessage().equals("/ver")) {
            if (e.getPlayer().isOp()) {
                return;
            }
            e.setCancelled(true);
            e.getPlayer().sendMessage("\u00a7cThis Server is running Nukkit with LobbyNK " + this.getServer().getPluginManager().getPlugin("LobbyNK").getDescription().getVersion() + " by Buddelbubi");
        }
        if (e.getMessage().equals("/version")) {
            if (e.getPlayer().isOp()) {
                return;
            }
            e.setCancelled(true);
            e.getPlayer().sendMessage("\u00a7cThis Server is running Nukkit with LobbyNK " + this.getServer().getPluginManager().getPlugin("LobbyNK").getDescription().getVersion() + " by Buddelbubi");
        }
        if (MultiLobbyManager.lobbyworlds.contains(e.getPlayer().getLevel().getName()) && (blockedCommands = new Config(new File(this.getDataFolder(), "config.yml")).getStringList("blockedcommands")).contains(e.getMessage().replace("/", ""))) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(lang.getString(String.valueOf(e.getPlayer().getLocale().toString()) + "_" + "nopermission"));
        }
    }
}

