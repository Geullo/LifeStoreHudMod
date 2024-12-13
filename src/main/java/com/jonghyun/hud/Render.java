package com.jonghyun.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.GL11;

import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.IOException;

public class Render {
    public static double zDepth = 0.0D;
    /**
     * get image size at now window scale
     * {isWX} is {wp} is width or height or x or y to check
     * */
    public static double getWP(double wp,boolean isWX) {
        ScaledResolution sc = new ScaledResolution(Minecraft.getMinecraft());
        return (wp/(isWX?1920d:1080d))*(isWX?sc.getScaledWidth_double():sc.getScaledHeight_double());
    }


    public static void setColor(int color) {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(((color >> 16) & 0xff) / 255.0f, ((color >> 8) & 0xff) / 255.0f, ((color) & 0xff) / 255.0f, ((color >> 24) & 0xff) / 255.0f);
        GlStateManager.disableBlend();
    }

    public static void drawTexturedRect(double x, double y, double w, double h) {
        drawTexturedRect(x, y, w, h, 0.0D, 0.0D, 1.0D, 1.0D);
    }

    public static void drawTexturedRect(double x, double y, double w, double h, double u1, double v1, double u2, double v2) {
        try {
            GlStateManager.enableTexture2D();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.getBuffer();
            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
            buffer.pos(x + w, y, zDepth).tex(u2, v1).endVertex();
            buffer.pos(x, y, zDepth).tex(u1, v1).endVertex();
            buffer.pos(x, y + h, zDepth).tex(u1, v2).endVertex();
            buffer.pos(x + w, y + h, zDepth).tex(u2, v2).endVertex();
            tessellator.draw();
            GlStateManager.disableBlend();
        } catch (NullPointerException e) {
        }
    }
    public static void drawString(String s, float x, float y,int width,int height, int align,int  color) { // 0xffffff
        GlStateManager.pushMatrix();
        float scaleX = width/16.0f,scaleY = height/16.0f;
        GL11.glScalef(scaleX,scaleY,1.0f);
        drawString(s,x/scaleX, y/scaleY,align,color);
        GlStateManager.popMatrix();
    }

    public static void drawString(String s, float x, float y, int align) { // 0xffffff
        drawString(s,x,y,align,0xffffff);
    }
    public static int drawString(String s, float x, float y, int align,int  color) {
        FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
        if(align == 0) { // LEFT
            fr.drawString(s, x, y, color,false);
        } else if(align == 1) { // CENTER
            fr.drawString(s, x - fr.getStringWidth(s) / 2f, y, color,false);
        } else { // RIGHT
            fr.drawString(s, x - fr.getStringWidth(s), y, color,false);
        }
        return fr.getStringWidth(s);
    }
    public static void bindTexture(ResourceLocation resource) {
        ITextureObject textureObj = Minecraft.getMinecraft().getTextureManager().getTexture(resource);
        if(textureObj == null) {
            textureObj = new BlurTexture(resource);
            Minecraft.getMinecraft().getTextureManager().loadTexture(resource, textureObj);
        }
        GlStateManager.bindTexture(textureObj.getGlTextureId());
    }

    @SideOnly(Side.CLIENT)
    public static class BlurTexture extends AbstractTexture {
        protected final ResourceLocation textureLocation;

        public BlurTexture(ResourceLocation textureResourceLocation) {
            textureLocation = textureResourceLocation;
        }

        public void loadTexture(IResourceManager resourceManager) throws IOException {
            this.deleteGlTexture();
            IResource iresource = null;
            try {
                iresource = resourceManager.getResource(textureLocation);
                BufferedImage bufferedimage = TextureUtil.readBufferedImage(iresource.getInputStream());
                TextureUtil.uploadTextureImageAllocate(this.getGlTextureId(), bufferedimage, true, false);
            } finally {
                IOUtils.closeQuietly((Closeable)iresource);
            }
        }
    }
}