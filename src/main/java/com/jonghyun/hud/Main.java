package com.jonghyun.hud;

import com.jonghyun.hud.event.RenderGameOverlay;
import com.jonghyun.hud.networking.PacketMessage;
import com.jonghyun.hud.proxy.CommonProxy;
import com.jonghyun.hud.util.Reference;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;

@Mod(name = Reference.NAME, modid = Reference.MOD_ID, version = Reference.VERSION)
public class Main {

    public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel("CustomHudChannel");

    @Mod.Instance
    public static Main instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.COMMON_PROXY)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void init(FMLInitializationEvent e)
    {

    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) throws IOException {
        if(e.getSide() == Side.CLIENT) MinecraftForge.EVENT_BUS.register(new RenderGameOverlay());
        NETWORK.registerMessage(PacketMessage.Handle.class, PacketMessage.class, 0, Side.CLIENT);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e)
    {

    }

}
