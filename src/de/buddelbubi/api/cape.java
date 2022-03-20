/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  cn.nukkit.Player
 *  cn.nukkit.Server
 *  cn.nukkit.entity.data.Skin
 *  cn.nukkit.network.protocol.DataPacket
 *  cn.nukkit.network.protocol.PlayerSkinPacket
 *  cn.nukkit.utils.SerializedImage
 */
package de.buddelbubi.api;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.data.Skin;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.PlayerSkinPacket;
import cn.nukkit.utils.SerializedImage;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class cape {
    public static void setCape(Player p) {
        Skin s = p.getSkin();
        try {
            BufferedImage si = ImageIO.read(new File(String.valueOf(Server.getInstance().getPluginPath()) + "/LobbyNK", "example.png"));
            BufferedImage sk = ImageIO.read(new File(String.valueOf(Server.getInstance().getPluginPath()) + "/LobbyNK", "skin.png"));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write((RenderedImage)si, "png", baos);
            baos.flush();
            byte[] bts = baos.toByteArray();
            baos.close();
            SerializedImage img = new SerializedImage(200, 150, bts);
            s.setSkinData(sk);
            s.setCapeOnClassic(true);
            s.setCapeId("example");
            s.setCapeData(img);
            s.setPremium(true);
            Skin os = p.getSkin();
            p.setSkin(s);
            PlayerSkinPacket psp = new PlayerSkinPacket();
            psp.skin = s;
            psp.oldSkinName = os.getSkinId();
            psp.newSkinName = s.getSkinId();
            psp.uuid = p.getUniqueId();
            System.out.print((Object)psp);
            Server.broadcastPacket(Server.getInstance().getOnlinePlayers().values(), (DataPacket)psp);
            p.hidePlayer(p);
            Server.getInstance().getScheduler().scheduleDelayedTask(new Runnable(){

                @Override
                public void run() {
                    p.showPlayer(p);
                }
            }, 15);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

