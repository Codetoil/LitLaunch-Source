/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch._native.mc1_14;

import io.github.codetoil.litlaunch.core.LaunchCommon;
import io.github.codetoil.litlaunch.core.event.LitEvent;
import io.github.codetoil.litlaunch.core.event.LitEventHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.DEDICATED_SERVER, modid = LaunchCommon.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandlerServer {

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase.equals(TickEvent.Phase.END)) {
            LitEventHandler.SERVER.post(new LitEvent(EventHandlerServer.class, LitEvent.TYPE.SERVERTICK, LaunchCommon.EMPTY), true);
        }
    }
}
