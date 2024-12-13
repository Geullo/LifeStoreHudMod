package com.jonghyun.hud.event;

import com.jonghyun.hud.Render;
import com.jonghyun.hud.networking.PacketControl;
import com.jonghyun.hud.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RenderGameOverlay extends Gui {
    private double[] headPos = new double[4], headSize = new double[4], scorePos = new double[2], scoreSize = new double[2],
        moneyPos = new double[2], moneySize = new double[2], pointPos = new double[2],
            memX = new double[9],memY = new double[9],memSz = new double[9]
    ;
    public static boolean quizUIVisible = false;
    private List<String> memList = new ArrayList<>();

    Minecraft mc = Minecraft.getMinecraft();
    public RenderGameOverlay() {
        memList.add("d7297");
        memList.add("samsik23");
        memList.add("RuTaeY");
        memList.add("Huchu95");
        memList.add("Daju_");
        memList.add("Seoneng");
        memList.add("KonG7");
        memList.add("Noonkkob");
    }
    private void initUI() {
        ScaledResolution sc = new ScaledResolution(mc);
        double width = sc.getScaledWidth(),height = sc.getScaledHeight();
        double size = 1.45;
        headSize[0] = width/4/2.25d/size;
        headSize[1] = height/4.5/1.13/size;
        headSize[2] = (headSize[0]*1.17)/size;
        headSize[3] = (headSize[1]*1.17)/size;
        headPos[0] = headSize[0]/5/2;
        headPos[1] = headSize[1]/5/2;
        headPos[2] = headPos[0]+headSize[2]/5/1.75;
        headPos[3] = headPos[1]+headSize[3]/5/1.65;
        scoreSize[0] = headSize[0]*1.5;
        scoreSize[1] = headSize[1];
        scorePos[0] = headPos[0] + headSize[0]+(headSize[0]/4.25/2.5);
        scorePos[1] = headPos[1];
        moneySize[0] = headSize[0]/3.15;
        moneySize[1] = headSize[1]/3.15;
        moneyPos[0] = (int) (scorePos[0]+(scoreSize[0]-(headSize[0]/3)));
        moneyPos[1] = (int) (scorePos[1] + (headSize[1]/2.84/1.8253));
        pointPos[0] = moneyPos[0];
        pointPos[1] = (int) (scorePos[1] + (scoreSize[1]-(headSize[1]/2.9544)));
    }

    private void initQuiz(ScaledResolution sc) {

    }

    @SubscribeEvent
    public void onRedner(RenderGameOverlayEvent e) {
        if(e.getType() == RenderGameOverlayEvent.ElementType.TEXT)
        {
            if (!quizUIVisible)
            {
                initUI();
                GlStateManager.pushMatrix();
                Render.setColor(0xffffffff);
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/head.png"));
                Render.drawTexturedRect(headPos[0], headPos[1], headSize[0], headSize[1]);
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/score.png"));
                Render.drawTexturedRect(scorePos[0], scorePos[1], scoreSize[0], scoreSize[1]);
                Render.bindTexture(Minecraft.getMinecraft().player.getLocationSkin());
                Render.drawTexturedRect(headPos[2], headPos[3], headSize[2], headSize[3], 0.125, 0.125, 0.25, 0.25);
                Render.drawTexturedRect(headPos[2], headPos[3], headSize[2], headSize[3], 0.625, 0.125, 0.75, 0.25);
                GlStateManager.popMatrix();
                String money = NumberFormat.getCurrencyInstance(Locale.KOREA).format(PacketControl.getInstance().money).replace("￦", "").replace("\\", "");
                String point = NumberFormat.getCurrencyInstance(Locale.KOREA).format(PacketControl.getInstance().point).replace("￦", "").replace("\\", "");
                Render.drawString(money, (float) moneyPos[0], (float) moneyPos[1], (int) moneySize[0], (int) moneySize[1], 2, 0xffffff);
                Render.drawString(point, (float) pointPos[0], (float) pointPos[1], (int) moneySize[0], (int) moneySize[1], 2, 0xffffff);
            }
            else
            {
                initQuiz(new ScaledResolution(mc));
            }
        }
    }


}
