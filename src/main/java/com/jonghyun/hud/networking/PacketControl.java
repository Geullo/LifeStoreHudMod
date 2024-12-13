package com.jonghyun.hud.networking;

import com.jonghyun.hud.event.RenderGameOverlay;
import net.minecraft.client.Minecraft;

public class PacketControl {
    public int money = 0, point = 0;

    private static PacketControl instance;

    public Minecraft mc = null;

    public static PacketControl getInstance() {
        if(instance == null) instance = new PacketControl();
        return instance;
    }

    private PacketControl() {
        mc = Minecraft.getMinecraft();
    }

    public void handle(PacketMessage message)
    {
        String id = message.data.substring(0, 2);
        if (id.equals(HudPacketList.GET.recogCode)) {
            String[] k = message.data.substring(2).split("/");
            money = Integer.parseInt(k[0]);
            point = Integer.parseInt(k[1]);
        }
        else if (id.equals(HudPacketList.QUIZ_VISIBLE.recogCode)) {
            RenderGameOverlay.quizUIVisible = !RenderGameOverlay.quizUIVisible;
        }
    }
}
