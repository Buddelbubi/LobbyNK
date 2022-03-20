package de.buddelbubi.events;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.mob.EntityBlaze;
import cn.nukkit.entity.mob.EntityCaveSpider;
import cn.nukkit.entity.mob.EntityCreeper;
import cn.nukkit.entity.mob.EntityEnderman;
import cn.nukkit.entity.mob.EntityGhast;
import cn.nukkit.entity.mob.EntityGuardian;
import cn.nukkit.entity.mob.EntityHoglin;
import cn.nukkit.entity.mob.EntityPhantom;
import cn.nukkit.entity.mob.EntityPiglin;
import cn.nukkit.entity.mob.EntitySkeleton;
import cn.nukkit.entity.mob.EntitySlime;
import cn.nukkit.entity.mob.EntitySpider;
import cn.nukkit.entity.mob.EntityStray;
import cn.nukkit.entity.mob.EntityWitch;
import cn.nukkit.entity.mob.EntityWither;
import cn.nukkit.entity.mob.EntityZoglin;
import cn.nukkit.entity.mob.EntityZombie;
import cn.nukkit.entity.passive.EntityCat;
import cn.nukkit.entity.passive.EntityChicken;
import cn.nukkit.entity.passive.EntityCow;
import cn.nukkit.entity.passive.EntityDolphin;
import cn.nukkit.entity.passive.EntityDonkey;
import cn.nukkit.entity.passive.EntityHorse;
import cn.nukkit.entity.passive.EntityLlama;
import cn.nukkit.entity.passive.EntityPanda;
import cn.nukkit.entity.passive.EntityParrot;
import cn.nukkit.entity.passive.EntityPig;
import cn.nukkit.entity.passive.EntityPolarBear;
import cn.nukkit.entity.passive.EntitySalmon;
import cn.nukkit.entity.passive.EntitySheep;
import cn.nukkit.entity.passive.EntitySkeletonHorse;
import cn.nukkit.entity.passive.EntitySquid;
import cn.nukkit.entity.passive.EntityStrider;
import cn.nukkit.entity.passive.EntityTurtle;
import cn.nukkit.entity.passive.EntityVillager;
import cn.nukkit.entity.passive.EntityWolf;
import cn.nukkit.entity.passive.EntityZombieHorse;
import cn.nukkit.event.Event;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerInteractEntityEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.event.player.PlayerRespawnEvent;
import cn.nukkit.event.player.PlayerTeleportEvent;
import cn.nukkit.form.element.Element;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementSlider;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import de.buddelbubi.Lobbynk;
import de.buddelbubi.api.MultiLobbyManager;
import java.util.HashMap;
import java.util.Map;

public class PetEvents
implements Listener {
    public static Map<String, Double> high = new HashMap<String, Double>();
    public static Map<String, Double> distance = new HashMap<String, Double>();
    public static Map<String, Entity> map = new HashMap<String, Entity>();
    public static Map<String, Map<String, String>> petsettings = new HashMap<String, Map<String, String>>();

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (map.containsKey(e.getPlayer().getName())) {
            Player p = e.getPlayer();
            Entity en = map.get(p.getName());
            if (!Block.get((int)p.getLevel().getBlockIdAt(Math.round(Math.round(p.x + 1.5 + distance.get(e.getPlayer().getName()) * Math.cos(4.0))), Math.round(Math.round(p.y + high.get(p.getName()))), Math.round(Math.round(p.z + 1.5 + distance.get(e.getPlayer().getName()) * Math.sin(4.0))))).isSolid() && p.getLevel().getBlockIdAt(Math.round(Math.round(p.x + 1.5 + distance.get(e.getPlayer().getName()) * Math.cos(4.0))), Math.round(Math.round(p.y - 1.0 + high.get(p.getName()))), Math.round(Math.round(p.z + 1.5 + distance.get(e.getPlayer().getName()) * Math.sin(4.0)))) != 0 || high.get(e.getPlayer().getName()) != 0.0) {
                en.addMovement(p.x + 1.5 + distance.get(e.getPlayer().getName()) * Math.cos(4.0), p.y + high.get(p.getName()), p.z + 1.5 + distance.get(e.getPlayer().getName()) * Math.sin(4.0), p.getYaw(), p.getPitch(), p.getYaw());
            }
        }
    }

    @SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.LOW)
    public void interactEvent(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        Server.getInstance().getScheduler().scheduleDelayedTask(new Runnable(){

            @Override
            public void run() {
                if (e.getEntity() instanceof Player) {
                    return;
                }
                if (!e.getPlayer().getTopWindow().isPresent() && !e.isCancelled() && (!Lobbynk.config.getBoolean("multiworld") || MultiLobbyManager.lobbyworlds.contains(e.getPlayer().getLevel().getName()) && map.containsKey(e.getPlayer().getName()))) {
                    e.setCancelled(true);
                    if (e.getPlayer().hasPermission("lobbynk.pet.settings")) {
                        FormWindowCustom fw = new FormWindowCustom(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Petsettings"));
                        if (e.getPlayer().hasPermission("lobbynk.pet.setting.rename")) {
                            fw.addElement((Element)new ElementInput(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.settings.name"), map.get(e.getPlayer().getName()).getNameTag()));
                        }
                        if (e.getPlayer().hasPermission("lobbynk.pet.setting.resize")) {
                            fw.addElement((Element)new ElementSlider(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.settings.size"), 1.0f, (float)Lobbynk.config.getInt("MaxPetSize"), 1, map.get(e.getPlayer().getName()).getScale() * 10.0f));
                        }
                        e.getPlayer().showFormWindow((FormWindow)fw);
                    }
                }
            }
        }, 5);
    }

    @EventHandler
    public void playerJoin(PlayerRespawnEvent e) {
        if (!e.isFirstSpawn()) {
            return;
        }
        HashMap<String, String> def = new HashMap<String, String>();
        def.put("name", String.valueOf(e.getPlayer().getName()) + "'s Pet");
        def.put("size", "1");
        def.put("flame", "false");
        petsettings.put(e.getPlayer().getName(), def);
    }

    @EventHandler
    public void onDmg(EntityDamageEvent e) {
        if (map.containsValue((Object)e.getEntity())) {
            e.setCancelled();
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        try {
            if (map.containsKey(e.getPlayer().getName()) && e.getTo().getLevel() != e.getFrom().getLevel()) {
                map.get(e.getPlayer().getName()).close();
                map.remove(e.getPlayer().getName());
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @EventHandler
    public void playerDisconnect(PlayerQuitEvent e) {
        if (map.containsKey(e.getPlayer().getName())) {
            map.get(e.getPlayer().getName()).close();
            map.remove(e.getPlayer().getName());
        }
    }

    @EventHandler
    public void formResp(PlayerFormRespondedEvent e) {
        if (e.getWindow() instanceof FormWindowSimple) {
            FormWindowSimple fw;
            Player p;
            if (e.getWindow().wasClosed()) {
                return;
            }
            String responseName = ((FormWindowSimple)e.getWindow()).getResponse().getClickedButton().getText();
            if (responseName.equalsIgnoreCase(Lobbynk.lang.getString(String.valueOf((p = e.getPlayer()).getLocale().toString()) + "_" + "Chest.Pets"))) {
                fw = new FormWindowSimple(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pets"), "");
                if (e.getPlayer().hasPermission("lobbynk.pet.chicken")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.chicken"), new ElementButtonImageData("path", "textures/items/egg_chicken.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.cow")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.cow"), new ElementButtonImageData("path", "textures/items/egg_cow.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.pig")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.pig"), new ElementButtonImageData("path", "textures/items/egg_pig.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.sheep")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.sheep"), new ElementButtonImageData("path", "textures/items/egg_sheep.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.squid")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.squid"), new ElementButtonImageData("path", "textures/items/egg_squid.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.polarbear")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.polarbear"), new ElementButtonImageData("path", "textures/items/egg_polarbear.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.cat")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.cat"), new ElementButtonImageData("path", "textures/items/egg_cat.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.dog")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.dog"), new ElementButtonImageData("path", "textures/items/egg_wolf.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.parrot")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.parrot"), new ElementButtonImageData("path", "textures/items/egg_parrot.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.llama")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.llama"), new ElementButtonImageData("path", "textures/items/egg_llama.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.donkey")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.donkey"), new ElementButtonImageData("path", "textures/items/egg_donkey.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.horse")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.horse"), new ElementButtonImageData("path", "textures/items/egg_horse.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.zombiehorse")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.zombiehorse"), new ElementButtonImageData("path", "textures/items/egg_zombiehorse.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.skeletonhorse")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.skeletonhorse"), new ElementButtonImageData("path", "textures/items/egg_skeletonhorse.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.fish")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.fish"), new ElementButtonImageData("path", "textures/items/egg_salmon.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.turtle")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.turtle"), new ElementButtonImageData("path", "textures/items/egg_turtle.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.dolphin")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.dolphin"), new ElementButtonImageData("path", "textures/items/egg_dolphin.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.panda")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.panda"), new ElementButtonImageData("path", "textures/items/egg_panda.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.zombie")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.zombie"), new ElementButtonImageData("path", "textures/items/egg_zombie.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.skeleton")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.skeleton"), new ElementButtonImageData("path", "textures/items/egg_skeleton.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.creeper")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.creeper"), new ElementButtonImageData("path", "textures/items/egg_creeper.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.spider")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.spider"), new ElementButtonImageData("path", "textures/items/egg_spider.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.cavespider")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.cavespider"), new ElementButtonImageData("path", "textures/items/egg_cave_spider.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.slime")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.slime"), new ElementButtonImageData("path", "textures/items/egg_slime.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.ghast")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.ghast"), new ElementButtonImageData("path", "textures/items/egg_ghast.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.guardian")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.guardian"), new ElementButtonImageData("path", "textures/items/egg_guardian.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.stray")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.stray"), new ElementButtonImageData("path", "textures/items/egg_stray.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.witch")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.witch"), new ElementButtonImageData("path", "textures/items/egg_witch.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.blaze")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.blaze"), new ElementButtonImageData("path", "textures/items/egg_blaze.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.hoglin")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.hoglin"), new ElementButtonImageData("path", "textures/items/egg_null.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.piglin")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.piglin"), new ElementButtonImageData("path", "textures/items/egg_null.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.zoglin")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.zoglin"), new ElementButtonImageData("path", "textures/items/egg_null.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.strider")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.strider"), new ElementButtonImageData("path", "textures/items/egg_null.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.phantom")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.phantom"), new ElementButtonImageData("path", "textures/items/egg_phantom.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.villager")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.villager"), new ElementButtonImageData("path", "textures/items/egg_villager.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.enderman")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.enderman"), new ElementButtonImageData("path", "textures/items/egg_enderman.png")));
                }
                if (e.getPlayer().hasPermission("lobbynk.pet.wither")) {
                    fw.addButton(new ElementButton(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.wither"), new ElementButtonImageData("path", "textures/items/nether_star.png")));
                }
                distance.put(e.getPlayer().getName(), 0.0);
                e.getPlayer().showFormWindow((FormWindow)fw);
            }
            if (((FormWindowSimple)e.getWindow()).getTitle().equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pets"))) {
                Entity ent;
                if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.phantom"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntityPhantom)) {
                        high.put(e.getPlayer().getName(), 2.0);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"Phantom", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        ent.setNameTagAlwaysVisible(true);
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.blaze"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntityBlaze)) {
                        high.put(e.getPlayer().getName(), 0.0);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"Blaze", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        ent.setNameTagAlwaysVisible(true);
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.hoglin"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntityHoglin)) {
                        high.put(e.getPlayer().getName(), 0.5);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"Hoglin", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        ent.setNameTagAlwaysVisible(true);
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.piglin"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntityPiglin)) {
                        high.put(e.getPlayer().getName(), 0.0);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"Piglin", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        ent.setNameTagAlwaysVisible(true);
                        ent.setScale(0.5f);
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.zoglin"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntityZoglin)) {
                        high.put(e.getPlayer().getName(), 0.0);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"Zoglin", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        ent.setNameTagAlwaysVisible(true);
                        ent.setScale(0.5f);
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.strider"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntityStrider)) {
                        high.put(e.getPlayer().getName(), 0.0);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"Strider", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setScale(0.5f);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        ent.setNameTagAlwaysVisible(true);
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.witch"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntityWitch)) {
                        high.put(e.getPlayer().getName(), 0.0);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"Witch", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        ent.setNameTagAlwaysVisible(true);
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.ghast"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntityGhast)) {
                        high.put(e.getPlayer().getName(), 1.0);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"Ghast", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setScale(0.19f);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        ent.setNameTagAlwaysVisible(true);
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.stray"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntityStray)) {
                        high.put(e.getPlayer().getName(), 0.0);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"Stray", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        ent.setNameTagAlwaysVisible(true);
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.guardian"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntityGuardian)) {
                        high.put(e.getPlayer().getName(), 0.0);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"Guardian", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setNameTagAlwaysVisible(true);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.slime"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntitySlime)) {
                        high.put(e.getPlayer().getName(), 0.0);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"Slime", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setNameTagAlwaysVisible(true);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        ent.setScale(1.0f);
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.spider"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntitySpider)) {
                        high.put(e.getPlayer().getName(), 0.0);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"Spider", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setNameTagAlwaysVisible(true);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.cavespider"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntityCaveSpider)) {
                        high.put(e.getPlayer().getName(), 0.0);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"CaveSpider", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setNameTagAlwaysVisible(true);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.creeper"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntityCreeper)) {
                        high.put(e.getPlayer().getName(), 0.0);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"Creeper", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setNameTagAlwaysVisible(true);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.skeleton"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntitySkeleton)) {
                        high.put(e.getPlayer().getName(), 0.0);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"Skeleton", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        map.put(e.getPlayer().getName(), ent);
                        ent.setNameTagAlwaysVisible(true);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.zombie"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntityZombie)) {
                        high.put(e.getPlayer().getName(), 0.0);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"Zombie", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        ent.setNameTagAlwaysVisible(true);
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.chicken"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntityChicken)) {
                        high.put(e.getPlayer().getName(), 0.0);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"Chicken", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        ent.setNameTagAlwaysVisible(true);
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.cow"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntityCow)) {
                        high.put(e.getPlayer().getName(), 0.0);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"Cow", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setNameTagAlwaysVisible(true);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.pig"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntityPig)) {
                        high.put(e.getPlayer().getName(), 0.0);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"Pig", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setNameTagAlwaysVisible(true);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.sheep"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntitySheep)) {
                        high.put(e.getPlayer().getName(), 0.0);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"Sheep", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setNameTagAlwaysVisible(true);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.polarbear"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntityPolarBear)) {
                        high.put(e.getPlayer().getName(), 0.0);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"PolarBear", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setNameTagAlwaysVisible(true);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.cat"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntityCat)) {
                        high.put(e.getPlayer().getName(), 0.0);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"Cat", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setNameTagAlwaysVisible(true);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.dog"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntityWolf)) {
                        high.put(e.getPlayer().getName(), 0.0);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"Wolf", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setNameTagAlwaysVisible(true);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.parrot"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntityParrot)) {
                        high.put(e.getPlayer().getName(), 1.0);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"Parrot", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setNameTagAlwaysVisible(true);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.llama"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntityLlama)) {
                        high.put(e.getPlayer().getName(), 0.0);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"Llama", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setNameTagAlwaysVisible(true);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.donkey"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntityDonkey)) {
                        high.put(e.getPlayer().getName(), 0.0);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"Donkey", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        ent.setNameTagAlwaysVisible(true);
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.horse"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntityHorse)) {
                        high.put(e.getPlayer().getName(), 0.0);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"Horse", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        ent.setNameTagAlwaysVisible(true);
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.zombiehorse"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntityZombieHorse)) {
                        high.put(e.getPlayer().getName(), 0.0);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"ZombieHorse", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        ent.setNameTagAlwaysVisible(true);
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.skeletonhorse"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntitySkeletonHorse)) {
                        high.put(e.getPlayer().getName(), 0.0);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"SkeletonHorse", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        ent.setNameTagAlwaysVisible(true);
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.fish"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntitySalmon)) {
                        high.put(e.getPlayer().getName(), 0.0);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"Salmon", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setNameTagAlwaysVisible(true);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.turtle"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntityTurtle)) {
                        high.put(e.getPlayer().getName(), 0.0);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"Turtle", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setNameTagAlwaysVisible(true);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.dolphin"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntityDolphin)) {
                        high.put(e.getPlayer().getName(), 0.0);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"Dolphin", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        ent.setNameTagAlwaysVisible(true);
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.panda"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntityPanda)) {
                        high.put(e.getPlayer().getName(), 0.0);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"Panda", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setNameTagAlwaysVisible(true);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.villager"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntityVillager)) {
                        high.put(e.getPlayer().getName(), 0.0);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"Villager", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setNameTagAlwaysVisible(true);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.enderman"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntityEnderman)) {
                        high.put(e.getPlayer().getName(), 0.0);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"Enderman", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setNameTagAlwaysVisible(true);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.squid"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntitySquid)) {
                        high.put(e.getPlayer().getName(), 2.001);
                        distance.put(e.getPlayer().getName(), 0.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"Squid", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setNameTagAlwaysVisible(true);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                } else if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.wither"))) {
                    if (!map.containsKey(e.getPlayer().getName()) || !(map.get(e.getPlayer().getName()) instanceof EntityWither)) {
                        high.put(e.getPlayer().getName(), 0.1);
                        distance.put(e.getPlayer().getName(), 5.0);
                        if (map.containsKey(e.getPlayer().getName())) {
                            map.get(e.getPlayer().getName()).close();
                            map.remove(e.getPlayer().getName());
                        }
                        ent = Entity.createEntity((String)"Wither", (Position)e.getPlayer(), (Object[])new Object[0]);
                        ent.setDataFlag(27, 27, true);
                        System.out.print(ent.getDataFlag(27, 27));
                        ent.setNameTagAlwaysVisible(true);
                        ent.setNameTag(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Pet.nametag").replace("%player", e.getPlayer().getDisplayName()));
                        map.put(e.getPlayer().getName(), ent);
                        ent.spawnToAll();
                    } else {
                        map.get(e.getPlayer().getName()).close();
                        map.remove(e.getPlayer().getName());
                    }
                }
            }
            if (responseName.equals(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Petsettings"))) {
                Server.getInstance().getPluginManager().callEvent((Event)new PlayerInteractEntityEvent(e.getPlayer(), map.get(e.getPlayer().getName()), e.getPlayer().getInventory().getItemInHand(), new Vector3(p.x, p.y, p.z)));
            } else if (responseName.equalsIgnoreCase(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.settings.size"))) {
               FormWindowCustom fw2 = new FormWindowCustom(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.settings.size"));
                fw2.addElement((Element)new ElementSlider(Lobbynk.lang.getString(String.valueOf(p.getLocale().toString()) + "_" + "Chest.Pet.settings.size"), 1.0f, (float)Lobbynk.config.getInt("MaxPetSize"), 1, map.get(e.getPlayer().getName()).getScale() * 10.0f));
                e.getPlayer().showFormWindow((FormWindow)fw2);
            }
        } else if (e.getWindow() instanceof FormWindowCustom) {
            FormWindowCustom window = (FormWindowCustom)e.getWindow();
            String responseName = window.getTitle();
            if (window.wasClosed()) {
                return;
            }
            if (!((FormWindowCustom)e.getWindow()).getResponse().getResponses().isEmpty() && responseName.equalsIgnoreCase(Lobbynk.lang.getString(String.valueOf(e.getPlayer().getLocale().toString()) + "_" + "Chest.Petsettings"))) {
                if (!window.getResponse().getInputResponse(0).isEmpty()) {
                    map.get(e.getPlayer().getName()).setNameTag("\u00a7e" + window.getResponse().getInputResponse(0).replace("&", "\u00a7"));
                }
                map.get(e.getPlayer().getName()).setScale(window.getResponse().getSliderResponse(1) / 10.0f);
            }
        }
    }
}

