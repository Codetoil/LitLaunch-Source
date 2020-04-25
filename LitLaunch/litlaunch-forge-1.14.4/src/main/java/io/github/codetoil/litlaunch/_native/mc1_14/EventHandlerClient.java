/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch._native.mc1_14;

import io.github.codetoil.litlaunch.api.FrontEnd;
import io.github.codetoil.litlaunch.core.LaunchCommon;
import io.github.codetoil.litlaunch.core.event.LitEvent;
import io.github.codetoil.litlaunch.core.event.LitEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = LaunchCommon.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandlerClient {
    @SubscribeEvent
    public static void ServerConnect(NetworkEvent.ClientCustomPayloadLoginEvent event) {
        LitEventHandler.CLIENT.post(new LitEvent(EventHandlerClient.class, LitEvent.TYPE.SERVERCONNECT, LaunchCommon.EMPTY));
        NetworkManager manager = Objects.requireNonNull(Minecraft.getInstance().getConnection()).getNetworkManager();
        if (manager.getNetHandler() instanceof IClientPlayNetHandler) {
            manager.setNetHandler(new EventThrowingClientPlayNetHandler((ClientPlayNetHandler) manager.getNetHandler()));
        } else {
            FrontEnd.error("Error, could not set net handler");
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase.equals(TickEvent.Phase.END)) {
            LitEventHandler.CLIENT.post(new LitEvent(EventHandlerClient.class, LitEvent.TYPE.CLIENTTICK, LaunchCommon.EMPTY), true);
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        EventHandlerServer.onServerTick(event);
    }
}
