/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  cn.nukkit.Server
 *  cn.nukkit.utils.Config
 */
package de.buddelbubi.setup;

import cn.nukkit.Server;
import cn.nukkit.utils.Config;
import java.io.File;
import java.util.LinkedHashMap;

public class ConfigGenerator {
    public static void genConfig() {
        Server.getInstance().getPluginManager().getPlugin("LobbyNK").saveResource("config.yml");
        Server.getInstance().getPluginManager().getPlugin("LobbyNK").saveResource("hotbar.yml");
        try {
            new Config(new File(Server.getInstance().getPluginManager().getPlugin("LobbyNK").getDataFolder(), "config.yml"), 2, (LinkedHashMap)new LinkedHashMap<String, Object>(){
                {
                    this.put("Configversion", 0);
                    this.put("Bossbar", true);
                    this.put("NoHunger", true);
                    this.put("NoDamage", true);
                    this.put("spawnprotection", true);
                    this.put("CompassImages", true);
                    this.put("Gamesamount", 8);
                    this.put("Gamemode", 2);
                    this.put("MaxPetSize", 20);
                    this.put("Inventorychange", false);
                    this.put("Joinquitmessage", true);
                    this.put("Jointitle", true);
                    this.put("multiworld", false);
                    this.put("multilobby", true);
                    this.put("multilobby-useallworlds", false);
                    this.put("maxplayersperlobby", 24);
                    this.put("multiworldSaveInventory", true);
                    this.put("Teleporttitle", true);
                    this.put("Teleportchat", true);
                    this.put("disableWeather", true);
                    this.put("disableInteract", true);
                    this.put("timelock", true);
                    this.put("time", 7000);
                    this.put("maxHealth", 1);
                    this.put("jumppadblock", 133);
                    this.put("Chatsystem", true);
                    this.put("Level", 2020);
                    this.put("scoreboard", true);
                    this.put("alwaysspawn", true);
                    this.put("fireworkonspawn", true);
                    this.put("bossbarpoints", 100);
                    this.put("soundeffects", true);
                    this.put("NoExplosions", true);
                    this.put("DeviceDisplay", true);
                    this.put("language", "english");
                    this.put("multilanguage", true);
                }
            });
        }
        catch (Exception e) {
            Server.getInstance().getLogger().critical("\u00a7cAn LobbyNK File is corrupted: config.yml");
            System.exit(1);
        }
        try {
            new Config(new File(Server.getInstance().getPluginManager().getPlugin("LobbyNK").getDataFolder(), "images.yml"), 2, (LinkedHashMap)new LinkedHashMap<String, Object>(){
                {
                    this.put("Slot1", "textures/items/diamond.png");
                    this.put("Slot2", "textures/items/iron_ingot.png");
                    this.put("Slot3", "textures/items/gold_ingot.png");
                    this.put("Slot4", "textures/items/emerald.png");
                    this.put("Slot5", "textures/items/coal.png");
                    this.put("Slot6", "textures/items/glowstone_dust.png");
                    this.put("Slot7", "textures/items/book_enchanted.png");
                    this.put("Slot8", "textures/items/nether_star.png");
                    this.put("Slot1.mode", "path");
                    this.put("Slot2.mode", "path");
                    this.put("Slot3.mode", "path");
                    this.put("Slot4.mode", "path");
                    this.put("Slot5.mode", "path");
                    this.put("Slot6.mode", "path");
                    this.put("Slot7.mode", "path");
                    this.put("Slot8.mode", "path");
                    this.put("serverpicture", "textures/ui/servers.png");
                    this.put("currentlobby", "textures/persona_thumbnails/alex_hair_thumbnail_0.png");
                    this.put("fulllobby", "textures/items/dye_powder_red.png");
                    this.put("nonfulllobby", "textures/items/dye_powder_lime.png");
                    this.put("INFO!", "You may change change to custom images using a server resourcepack!");
                }
            });
        }
        catch (Exception e) {
            Server.getInstance().getLogger().critical("\u00a7cAn LobbyNK File is corrupted: images.yml");
            System.exit(1);
        }
        try {
            new Config(new File(Server.getInstance().getPluginManager().getPlugin("LobbyNK").getDataFolder(), "hotbar.yml"), 2, (LinkedHashMap)new LinkedHashMap<String, Object>(){
                {
                    this.put("configversion", 2);
                    this.put("Slot1.Item", "404:0");
                    this.put("Slot1.Itemname", "&cSettings");
                    this.put("Slot2.Item", "369:0");
                    this.put("Slot2.Itemname", "&aHide players");
                    this.put("Slot3.Item", "160:8");
                    this.put("Slot3.Itemname", "");
                    this.put("Slot4.Item", "160:8");
                    this.put("Slot4.Itemname", "");
                    this.put("Slot5.Item", "345:0");
                    this.put("Slot5.Itemname", "&cNavigator");
                    this.put("Slot6.Item", "160:8");
                    this.put("Slot6.Itemname", "");
                    this.put("Slot7.Item", "160:8");
                    this.put("Slot7.Itemname", "");
                    this.put("Slot8.Item", "54:0");
                    this.put("Slot8.Itemname", "&eGadgets");
                    this.put("Slot9.Item", "399:0");
                    this.put("Slot9.Itemname", "&bLobbySelector");
                    this.put("Slot1.VIP.Item", "404:0");
                    this.put("Slot1.VIP.Itemname", "&cSettings");
                    this.put("Slot2.VIP.Item", "369:0");
                    this.put("Slot2.VIP.Itemname", "&aHide players");
                    this.put("Slot3.VIP.Item", "46:0");
                    this.put("Slot3.VIP.Itemname", "&cSilentLobby");
                    this.put("Slot4.VIP.Item", "160:8");
                    this.put("Slot4.VIP.Itemname", "");
                    this.put("Slot5.VIP.Item", "345:0");
                    this.put("Slot5.VIP.Itemname", "&cNavigator");
                    this.put("Slot6.VIP.Item", "160:8");
                    this.put("Slot6.VIP.Itemname", "");
                    this.put("Slot7.VIP.Item", "381:0");
                    this.put("Slot7.VIP.Itemname", "&2Forcefield");
                    this.put("Slot8.VIP.Item", "54:0");
                    this.put("Slot8.VIP.Itemname", "&eGadgets");
                    this.put("Slot9.VIP.Item", "399:0");
                    this.put("Slot9.VIP.Itemname", "&bLobbySelector");
                    this.put("CompassItem", "345:0");
                    this.put("HidePlayerItem", "369:0");
                    this.put("GadgetsItem", "54:0");
                    this.put("LobbyItem", "399:0");
                    this.put("SettingsItem", "404:0");
                    this.put("ForcefieldItem", "381:0");
                    this.put("SilentlobbyItem", "46:0");
                    this.put("FriendItem", "397:3");
                }
            });
        }
        catch (Exception e) {
            Server.getInstance().getLogger().critical("\u00a7cAn LobbyNK File is corrupted: horbar.yml");
            System.exit(1);
        }
        try {
            new Config(new File(Server.getInstance().getPluginManager().getPlugin("LobbyNK").getDataFolder(), "compasscommands.yml"), 2, (LinkedHashMap)new LinkedHashMap<String, Object>(){
                {
                    this.put("Slot1.command", "none");
                    this.put("Slot2.command", "none");
                    this.put("Slot3.command", "none");
                    this.put("Slot4.command", "none");
                    this.put("Slot5.command", "none");
                    this.put("Slot6.command", "none");
                    this.put("Slot7.command", "none");
                    this.put("Slot8.command", "none");
                    this.put("Slot1.disableloc", false);
                    this.put("Slot2.disableloc", false);
                    this.put("Slot3.disableloc", false);
                    this.put("Slot4.disableloc", false);
                    this.put("Slot5.disableloc", false);
                    this.put("Slot6.disableloc", false);
                    this.put("Slot7.disableloc", false);
                    this.put("Slot8.disableloc", false);
                    this.put("Information", "Change none to your command without slash.");
                }
            });
        }
        catch (Exception e) {
            Server.getInstance().getLogger().critical("\u00a7cAn LobbyNK File is corrupted: compasscommands.yml");
            System.exit(1);
        }
        try {
            new Config(new File(Server.getInstance().getPluginManager().getPlugin("LobbyNK").getDataFolder(), "chat.yml"), 2, (LinkedHashMap)new LinkedHashMap<String, Object>(){
                {
                    this.put("Owner", "&4Owner &8: &7%p &8>> &7 %msg");
                    this.put("Admin", "&4Admin &8: &7%p &8>> &7 %msg");
                    this.put("Developer", "&bDeveloper &8: &7%p &8>> &7 %msg");
                    this.put("Moderator", "&cModerator &8: &7%p &8>> &7 %msg");
                    this.put("Builder", "&eBuilder &8: &7%p &8>> &7 %msg");
                    this.put("Supporter", "&2Supporter &8: &7%p &8>> &7 %msg");
                    this.put("Youtuber", "&5Youtuber &8: &7%p &8>> &7 %msg");
                    this.put("Premium", "&6Premium &8: &7%p &8>> &7 %msg");
                    this.put("Custom1", "&1Custom1 &8: &7%p &8>> &7 %msg");
                    this.put("Custom2", "&1Custom2 &8: &7%p &8>> &7 %msg");
                    this.put("Custom3", "&1Custom3 &8: &7%p &8>> &7 %msg");
                    this.put("Default", "&7%p >> %msg");
                }
            });
        }
        catch (Exception e) {
            Server.getInstance().getLogger().critical("\u00a7cAn LobbyNK File is corrupted: chat.yml");
            System.exit(1);
        }
        Config en = new Config(new File(Server.getInstance().getPluginManager().getPlugin("LobbyNK").getDataFolder() + "/lang", "english.yml"));
        en.save();
        try {
            new Config(new File(Server.getInstance().getPluginManager().getPlugin("LobbyNK").getDataFolder() + "/lang", "german.yml"), 2, (LinkedHashMap)new LinkedHashMap<String, Object>(){
                {
                    this.put("languagecode", "de");
                    this.put("Joinprefix", "&8[&a+&8] &7%p");
                    this.put("Quitprefix", "&8[&c-&8] &7%p");
                    this.put("FirstjoinMessage", "&6%p&a spielt das erste mal hier");
                    this.put("Jointitle1", "&a&lWillkommen");
                    this.put("Jointitle2", "&2%p");
                    this.put("nopermission", "&cKeine Rechte");
                    this.put("Bossbar", "&eDies kannst du in german.yml \u00e4ndern");
                    this.put("Compass.name", "&cKompass");
                    this.put("Compass.subtitle", "&cW\u00e4hle dein Spiel");
                    this.put("Compass.Slot1", "&eSlot1");
                    this.put("Compass.Slot2", "&eSlot2");
                    this.put("Compass.Slot3", "&eSlot3");
                    this.put("Compass.Slot4", "&eSlot4");
                    this.put("Compass.Slot5", "&eSlot5");
                    this.put("Compass.Slot6", "&eSlot6");
                    this.put("Compass.Slot7", "&eSlot7");
                    this.put("Compass.Slot8", "&eSlot8");
                    this.put("Playerhider.name", "&eVerstecke Spieler");
                    this.put("Chest.name", "&bEffekte");
                    this.put("Chest.subtitle", "&bW\u00e4hle einen Effekt aus");
                    this.put("Locations.Set", "&aErfolgreich &6%loc &agesetzt");
                    this.put("Locations.Teleport.Chat", "&aDu wurdest zu %game &ateleportiert");
                    this.put("Locations.Teleport.Title", "&eDu bist nun bei &6%game");
                    this.put("Locations.Teleport.missing", "&cDiese Location wurde bisher noch nicht gesetzt");
                    this.put("Build.true.own", "&aDu kannst nun bauen/abbauen");
                    this.put("Build.false.own", "&aDu kannst nicht l\u00e4nger bauen");
                    this.put("Build.true.other", "&6%p &akann nun bauen/abbauen");
                    this.put("Build.false.other", "&6%p &akann nicht l\u00e4nger bauen/abbauen");
                    this.put("Build.notallowed", "");
                    this.put("Playeronly", "&cNur Spieler k\u00f6nnen das tun");
                    this.put("Playeroffline", "&cDieser Spieler ist nicht online");
                    this.put("Help", "&8[&fLobby&cNK&8] &7 >> &fDieser Befehl wurde nicht gefunden. Gehe bitte auf unsere Seite und lies die Beschreibung: &chttps://nukkitx.com/resources/lobbynk.380/");
                    this.put("spawn.tp", "&aDu wurdest zum Spawn teleportiert");
                    this.put("spawn.set", "&aSpawn erfolgreich gesetzt");
                    this.put("playershider.hideall", "&aVerstecke jeden");
                    this.put("playershider.hidevip", "&5Verstecke jeden au\u00dfer VIP`s");
                    this.put("playershider.hidenoone", "&cVerstecke niemanden");
                    this.put("playershider.name", "&eSpieler verstecken");
                    this.put("playershider.hidenall", "&aNanu.. Wo sind denn alle hin? Du hast alle Spieler verschwinden lassen :O");
                    this.put("playershider.hidenvip", "&aJeder, von &5VIP's &aabgesehen ist nun versteckt");
                    this.put("playershider.hidennoone", "&aJeder ist wieder sichtbar :D");
                    this.put("Effectselected", "&aDu hat nun den Effekt &e%effect");
                    this.put("Effectremoved", "&cDu hat nun den Effekt &e%effect &centfernt");
                    this.put("Chest.Walkingparticles", "&ePartickel");
                    this.put("Chest.Pets", "&eHaustiere");
                    this.put("Pet.nametag", "&e%player's Haustier");
                    this.put("Chest.Petsettings", "&eHaustiereinstellungen");
                    this.put("Chest.Pet.chicken", "&eHuhn");
                    this.put("Chest.Pet.cow", "&eKuh");
                    this.put("Chest.Pet.pig", "&eSchwein");
                    this.put("Chest.Pet.sheep", "&eSchaf");
                    this.put("Chest.Pet.squid", "&eTintenfisch");
                    this.put("Chest.Pet.polarbear", "&eEisb\u00e4r");
                    this.put("Chest.Pet.cat", "&eKatze");
                    this.put("Chest.Pet.dog", "&eHund");
                    this.put("Chest.Pet.parrot", "&ePapagei");
                    this.put("Chest.Pet.llama", "&eLama");
                    this.put("Chest.Pet.donkey", "&eEsel");
                    this.put("Chest.Pet.horse", "&ePferd");
                    this.put("Chest.Pet.zombiehorse", "&eZombiepferd");
                    this.put("Chest.Pet.skeletonhorse", "&eSkelettpferd");
                    this.put("Chest.Pet.fish", "&eFisch");
                    this.put("Chest.Pet.turtle", "&eSchildkr\u00f6te");
                    this.put("Chest.Pet.dolphin", "&eDelfin");
                    this.put("Chest.Pet.panda", "&ePanda");
                    this.put("Chest.Pet.wither", "&eGeladener Wither");
                    this.put("Chest.Pet.zombie", "&eZombie Pet");
                    this.put("Chest.Pet.skeleton", "&eSkelett Pet");
                    this.put("Chest.Pet.spider", "&eSpinnen Haustier");
                    this.put("Chest.Pet.cavespider", "&eH\u00f6hlenspinnen Haustier");
                    this.put("Chest.Pet.creeper", "&eCreeper Pet");
                    this.put("Chest.Pet.slime", "&eSlime");
                    this.put("Chest.Pet.ghast", "&eGhast");
                    this.put("Chest.Pet.guardian", "&eW\u00e4chter");
                    this.put("Chest.Pet.stray", "&eEiswanderer");
                    this.put("Chest.Pet.witch", "&eHexe");
                    this.put("Chest.Pet.blaze", "&eBlaze");
                    this.put("Chest.Pet.hoglin", "&eHoglin");
                    this.put("Chest.Pet.piglin", "&ePiglin");
                    this.put("Chest.Pet.zoglin", "&eZoglin");
                    this.put("Chest.Pet.strider", "&eStrider");
                    this.put("Chest.Pet.phantom", "&ePhantom");
                    this.put("Chest.Pet.villager", "&eDorfbewohner");
                    this.put("Chest.Pet.enderman", "&eEnderman");
                    this.put("Chest.Pet.settings.name", "&eName");
                    this.put("Chest.Pet.settings.size", "&eGr\u00f6\u00dfe");
                    this.put("Chest.Cosmetics", "&eKleiderschrank");
                    this.put("Chest.cosmetic.netherite", "&eNetherite");
                    this.put("Chest.cosmetic.diamond", "&eDiamant");
                    this.put("Chest.cosmetic.iron", "&eEisen");
                    this.put("Chest.cosmetic.chainmail", "&eKetten");
                    this.put("Chest.cosmetic.gold", "&eGold");
                    this.put("Chest.cosmetic.leather", "&eLeder");
                    this.put("Chest.cosmetic.other", "&eAnderes");
                    this.put("Chest.cosmetic.enchant", "&eVerzaubern");
                    this.put("Chest.cosmetic.dye", "\u00a7eF\u00e4rben");
                    this.put("Chest.cosmetic.helmet", "&ehelm");
                    this.put("Chest.cosmetic.chestplate", "&ebrustplatte");
                    this.put("Chest.cosmetic.leggings", "&ehose");
                    this.put("Chest.cosmetic.boots", "&eschuhe");
                    this.put("Chest.cosmetic.skull", "&eSch\u00e4del");
                    this.put("Chest.cosmetic.wither", "&eWither");
                    this.put("Chest.cosmetic.zombie", "&eZombie");
                    this.put("Chest.cosmetic.player", "&eSteve");
                    this.put("Chest.cosmetic.creeper", "&eCreeper");
                    this.put("Chest.cosmetic.dragon", "&eEnderdrache");
                    this.put("Chest.cosmetic.shell", "&eSchildkr\u00f6tenpanzer");
                    this.put("Chest.cosmetic.elytra", "&eElytra");
                    this.put("Chest.cosmetic.equipped", "&aDu hast nun &e%cosmetic &aangezogen");
                    this.put("Chest.cosmetic.nothingequipped", "&cDu hast keine R\u00fcstung an");
                    this.put("Chest.cosmetic.removed", "&cDu hast &e%cosmetic &causgezogen");
                    this.put("Chest.remove_everything", "&cDu hast alles Entfernt");
                    this.put("Chest.remove", "&cEffekte entfernen");
                    this.put("Chest.walking.angry", "&eSauer");
                    this.put("Chest.walking.bonemeal", "&eKnochenmehl");
                    this.put("Chest.walking.bubble", "&eBlase");
                    this.put("Chest.walking.flame", "&eFlamme");
                    this.put("Chest.walking.explode", "&eExplosionsrauch");
                    this.put("Chest.walking.heart", "&eHerzen");
                    this.put("Chest.walking.redstone", "&eRedstone");
                    this.put("Chest.walking.ink", "&eTinte");
                    this.put("Chest.walking.note", "&eNote");
                    this.put("Chest.walking.ink", "&eTinte");
                    this.put("Chest.effects", "&eEffekte");
                    this.put("Chest.effect.speed", "&eSchnelligkeit");
                    this.put("Chest.effect.jumpboost", "&eJump Boost");
                    this.put("Chest.effect.levitation", "&eSchweben");
                    this.put("Chest.effect.invisibility", "&eUnsichtbarkeit");
                    this.put("Chest.effect.blindness", "&eBlindheit");
                    this.put("Chest.effect.nausea", "&e\u00dcbelkeit");
                    this.put("Chest.effect.nightvision", "&eNachtsicht");
                    this.put("Chat.cleared", "&eDer Chat wurde von &c%player geleert");
                    this.put("Scoreboard.Header", "&c&lLobbyNK von Buddelbubi");
                    this.put("Scoreboard.Line1", "");
                    this.put("Scoreboard.Line2", "&7Willkommen %player auf einem NukkitX Server");
                    this.put("Scoreboard.Line3", "");
                    this.put("Scoreboard.Line4", "&cBitte bewerte LobbyNK");
                    this.put("Scoreboard.Line5", "&cIm besten Fall mit 5 Sternen");
                    this.put("Scoreboard.Line6", "");
                    this.put("Scoreboard.Line7", "");
                    this.put("Scoreboard.Line8", "");
                    this.put("Scoreboard.Line9", "");
                    this.put("Scoreboard.Line10", "");
                    this.put("lobbyselector", "&9W\u00e4hle deine Lobby aus");
                    this.put("lobbyselector.prefix", "&b");
                    this.put("lobbyselector.vipprefix", "&e");
                    this.put("lobbyselector.serverprefix", "&a");
                    this.put("Lobbyselector.Playercounter", "&e(%inlobby/%maxlobby)");
                    this.put("serveradded", "&aLobbyserver &e%servername (%address) &aerfolgreich hinzugef\u00fcgt");
                    this.put("everylobbyisfull", "&cEntschuldigung, Aber jede Lobby ist voll..");
                    this.put("fulllobby", "&cEntschuldigung... Aber diese Lobby ist voll..");
                    this.put("lobbyconnected", "&aDu bist nun auf Lobby &e%lobby");
                    this.put("settings", "&cEinstellungen");
                    this.put("settings.fly", "Fliegen");
                    this.put("settings.doublejump", "Doppelsprung");
                    this.put("settings.jumppad", "Jumppad");
                    this.put("settings.forcefield", "Schutzschild");
                    this.put("settings.enabled", "&aDu hast &e%mode &aaktiviert :D");
                    this.put("settings.disabled", "&cDu hast &e%mode &cdeaktiviert...");
                    this.put("sync.success", "&aErfolgreich %copied %overwritten synkronisiert!");
                    this.put("sync.failed", "&cEin Fehler ist beim Synkronisieren aufgetreten");
                }
            });
        }
        catch (Exception e) {
            Server.getInstance().getLogger().critical("\u00a7cAn LobbyNK File is corrupted: german.yml");
            System.exit(1);
        }
        try {
            for (File f : new File(Server.getInstance().getPluginManager().getPlugin("LobbyNK").getDataFolder() + "/lang").listFiles()) {
                new Config(f, 2, (LinkedHashMap)new LinkedHashMap<String, Object>(){
                    {
                        this.put("languagecode", "en");
                        this.put("Joinprefix", "&8[&a+&8] &7%p");
                        this.put("Quitprefix", "&8[&c-&8] &7%p");
                        this.put("FirstjoinMessage", "&6%p&a is playing first time here");
                        this.put("Jointitle1", "&a&lWelcome");
                        this.put("Jointitle2", "&2%p");
                        this.put("nopermission", "&cYou have no permission to do that...");
                        this.put("Bossbar", "&eYou can change this in english.yml");
                        this.put("Compass.name", "&cCompass");
                        this.put("Compass.subtitle", "&cSelect your game");
                        this.put("Compass.Slot1", "&eSlot1");
                        this.put("Compass.Slot2", "&eSlot2");
                        this.put("Compass.Slot3", "&eSlot3");
                        this.put("Compass.Slot4", "&eSlot4");
                        this.put("Compass.Slot5", "&eSlot5");
                        this.put("Compass.Slot6", "&eSlot6");
                        this.put("Compass.Slot7", "&eSlot7");
                        this.put("Compass.Slot8", "&eSlot8");
                        this.put("Chest.name", "&bEffects");
                        this.put("Chest.subtitle", "&bSelect your effect");
                        this.put("Locations.Set", "&aSuccessful set &6%loc");
                        this.put("Locations.Teleport.Chat", "&aYou was teleported to %game");
                        this.put("Locations.Teleport.Title", "&eTeleported to &6%game");
                        this.put("Locations.Teleport.missing", "&cThis location wasn't set yet");
                        this.put("Build.true.own", "&aYou're now able to build/break");
                        this.put("Build.false.own", "&aYou're no longer able to build/break");
                        this.put("Build.true.other", "&6%p &ais now able to build/break");
                        this.put("Build.false.other", "&6%p &ais no longer able to build/break");
                        this.put("Build.notallowed", "");
                        this.put("Playeronly", "&cOnly players can do this");
                        this.put("Playeroffline", "&cThis player isn't online");
                        this.put("Help", "&8[&fLobby&cNK&8] &7 >> &fThis command wasn't found. Check out our Page: &chttps://nukkitx.com/resources/lobbynk.380/");
                        this.put("spawn.tp", "&aYou was teleported to spawn");
                        this.put("spawn.set", "&aSpawn sucessful set");
                        this.put("playershider.hideall", "&aHide everyone");
                        this.put("playershider.hidevip", "&5Hide everyone except VIP's");
                        this.put("playershider.hidenoone", "&cShow everyone");
                        this.put("playershider.name", "&ePlayer-Hider");
                        this.put("playershider.hidenall", "&aYou snapped with your fingers. Everyone is gone now...");
                        this.put("playershider.hidenvip", "&aEveryone except &5VIP's &ais gone now!");
                        this.put("playershider.hidennoone", "&aEveryone is visible now :D");
                        this.put("Effectselected", "&aYou selected &e%effect");
                        this.put("Effectremoved", "&cYou removed &e%effect");
                        this.put("Chest.Walkingparticles", "&eWalkingparticles");
                        this.put("Chest.Pets", "&ePets");
                        this.put("Pet.nametag", "&e%player's Pet");
                        this.put("Chest.Petsettings", "&ePet Settings");
                        this.put("Chest.Pet.chicken", "&eChicken");
                        this.put("Chest.Pet.cow", "&eCow");
                        this.put("Chest.Pet.pig", "&ePig");
                        this.put("Chest.Pet.sheep", "&eSheep");
                        this.put("Chest.Pet.squid", "&eSquid");
                        this.put("Chest.Pet.polarbear", "&ePolar Bear");
                        this.put("Chest.Pet.cat", "&eCat");
                        this.put("Chest.Pet.dog", "&eDog");
                        this.put("Chest.Pet.parrot", "&eParrot");
                        this.put("Chest.Pet.llama", "&eLlama");
                        this.put("Chest.Pet.donkey", "&eDonkey");
                        this.put("Chest.Pet.horse", "&eHorse");
                        this.put("Chest.Pet.zombiehorse", "&eZombie Horse");
                        this.put("Chest.Pet.skeletonhorse", "&eSkeleton Horse");
                        this.put("Chest.Pet.fish", "&eFish");
                        this.put("Chest.Pet.turtle", "&eTurtle");
                        this.put("Chest.Pet.dolphin", "&eDolphin");
                        this.put("Chest.Pet.panda", "&ePanda");
                        this.put("Chest.Pet.wither", "&eCharched Wither");
                        this.put("Chest.Pet.zombie", "&eZombie Pet");
                        this.put("Chest.Pet.skeleton", "&eSkeleton Pet");
                        this.put("Chest.Pet.spider", "&eSpider Pet");
                        this.put("Chest.Pet.cavespider", "&eCavespider Pet");
                        this.put("Chest.Pet.creeper", "&eCreeper Pet");
                        this.put("Chest.Pet.slime", "&eSlime");
                        this.put("Chest.Pet.ghast", "&eGhast");
                        this.put("Chest.Pet.guardian", "&eGuardian");
                        this.put("Chest.Pet.stray", "&eStray");
                        this.put("Chest.Pet.witch", "&eWitch");
                        this.put("Chest.Pet.blaze", "&eBlaze");
                        this.put("Chest.Pet.hoglin", "&eHoglin");
                        this.put("Chest.Pet.piglin", "&ePiglin");
                        this.put("Chest.Pet.zoglin", "&eZoglin");
                        this.put("Chest.Pet.strider", "&eStrider");
                        this.put("Chest.Pet.phantom", "&ePhantom");
                        this.put("Chest.Pet.villager", "&eVillager");
                        this.put("Chest.Pet.enderman", "&eEnderman");
                        this.put("Chest.Pet.settings.name", "&eName");
                        this.put("Chest.Pet.settings.size", "&eSize");
                        this.put("Chest.Cosmetics", "&eCosmetics");
                        this.put("Chest.cosmetic.netherite", "&eNetherite");
                        this.put("Chest.cosmetic.diamond", "&eDiamond");
                        this.put("Chest.cosmetic.iron", "&eIron");
                        this.put("Chest.cosmetic.chainmail", "&eChainmail");
                        this.put("Chest.cosmetic.gold", "&eGold");
                        this.put("Chest.cosmetic.leather", "&eLeather");
                        this.put("Chest.cosmetic.other", "&eOther");
                        this.put("Chest.cosmetic.enchant", "&eEnchant");
                        this.put("Chest.cosmetic.dye", "\u00a7eDye");
                        this.put("Chest.cosmetic.helmet", "&ehelmet");
                        this.put("Chest.cosmetic.chestplate", "&echestplate");
                        this.put("Chest.cosmetic.leggings", "&eleggings");
                        this.put("Chest.cosmetic.boots", "&eboots");
                        this.put("Chest.cosmetic.skull", "&eSkull");
                        this.put("Chest.cosmetic.wither", "&eWither");
                        this.put("Chest.cosmetic.zombie", "&eZombie");
                        this.put("Chest.cosmetic.player", "&eSteve");
                        this.put("Chest.cosmetic.creeper", "&eCreeper");
                        this.put("Chest.cosmetic.dragon", "&eEnderdragon");
                        this.put("Chest.cosmetic.shell", "&eTurtle Shell");
                        this.put("Chest.cosmetic.elytra", "&eElytra");
                        this.put("Chest.cosmetic.equipped", "&aYou equipped &e%cosmetic");
                        this.put("Chest.cosmetic.nothingequipped", "&cYou didn't equip anything");
                        this.put("Chest.cosmetic.removed", "&cYou removed &e%cosmetic");
                        this.put("Chest.remove_everything", "&cYou removed every gadget");
                        this.put("Chest.remove", "&cRemove Effects");
                        this.put("Chest.walking.angry", "&eAngry");
                        this.put("Chest.walking.bonemeal", "&eBonemeal");
                        this.put("Chest.walking.bubble", "&ebubble");
                        this.put("Chest.walking.flame", "&eFlame");
                        this.put("Chest.walking.explode", "&eExplode");
                        this.put("Chest.walking.heart", "&eHeart");
                        this.put("Chest.walking.redstone", "&eRedstone");
                        this.put("Chest.walking.ink", "&eInk");
                        this.put("Chest.walking.note", "&eNote");
                        this.put("Chest.walking.enchant", "&eEnchant");
                        this.put("Chest.effects", "&eEffects");
                        this.put("Chest.effect.speed", "&eSpeed");
                        this.put("Chest.effect.jumpboost", "&eJump Boost");
                        this.put("Chest.effect.levitation", "&eLevitation");
                        this.put("Chest.effect.invisibility", "&eInvisibility");
                        this.put("Chest.effect.blindness", "&eBlindness");
                        this.put("Chest.effect.nausea", "&eNausea");
                        this.put("Chest.effect.nightvision", "&eNight Vision");
                        this.put("Chat.cleared", "&eThe chat has been cleared by &c%player");
                        this.put("Scoreboard.Header", "&c&lLobbyNK by Buddelbubi");
                        this.put("Scoreboard.Line1", "");
                        this.put("Scoreboard.Line2", "&7Welcome on NukkitX");
                        this.put("Scoreboard.Line3", "&r");
                        this.put("Scoreboard.Line4", "&cPlease rate LobbyNK");
                        this.put("Scoreboard.Line5", "&cIn best case with 5 stars");
                        this.put("Scoreboard.Line6", "");
                        this.put("Scoreboard.Line7", "");
                        this.put("Scoreboard.Line8", "");
                        this.put("Scoreboard.Line9", "");
                        this.put("Scoreboard.Line10", "");
                        this.put("lobbyselector", "&9Select your Lobby");
                        this.put("lobbyselector.prefix", "&b");
                        this.put("lobbyselector.vipprefix", "&e");
                        this.put("lobbyselector.serverprefix", "&a");
                        this.put("Lobbyselector.Playercounter", "&e(%inlobby/%maxlobby)");
                        this.put("serveradded", "&aLobby &e%servername (%address) &aadded");
                        this.put("everylobbyisfull", "&cSorry, but every Lobby is full.. We can't connect you");
                        this.put("fulllobby", "&cSorry, but this Lobby is full");
                        this.put("lobbyconnected", "&aYou're now in Lobby &e%lobby");
                        this.put("settings", "&cSettings");
                        this.put("settings.fly", "Flying");
                        this.put("settings.doublejump", "Doublejump");
                        this.put("settings.jumppad", "Jumppad");
                        this.put("settings.forcefield", "Forcefield");
                        this.put("settings.enabled", "&aYou enabled &e%mode");
                        this.put("settings.disabled", "&cYou disabled %mode");
                        this.put("sync.success", "&aSuccessfully synced %copied %overwritten");
                        this.put("sync.failed", "&cAn error orrupted while syncing.");
                    }
                });
            }
        }
        catch (Exception e) {
            Server.getInstance().getLogger().critical("\u00a7cAn LobbyNK Languagefile is corrupted! Please reset the file");
            System.exit(1);
        }
    }
}

