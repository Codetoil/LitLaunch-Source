/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch._native.mc1_14;

import io.github.codetoil.litlaunch.api.IDoThing;
import io.github.codetoil.litlaunch.core.LaunchCommon;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class DoThing implements IDoThing {
    public static final IDoThing INSTANCE = new DoThing();

    private DoThing() {
    }

    @Override
    public void sendAsChatMessage(String message) {
        if (FMLEnvironment.dist.isClient()) {
            sendAsClientChatMessage(message);
        } else {
            sendAsServerChatMessage(message);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void sendAsClientChatMessage(String message) {
        Minecraft.getInstance().player.sendChatMessage(message);
    }

    @Override
    @OnlyIn(Dist.DEDICATED_SERVER)
    public void sendAsServerChatMessage(String message) {
        ServerLifecycleHooks.getCurrentServer().getPlayerList().sendMessage(new StringTextComponent(message));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void notifyPlayer(String message) {
        notifyPlayer(message, Color.WHITE);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void notifyPlayer(String message, Color pColor) {
        notifyPlayer(message, pColor, false, false, false, false, false);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void notifyPlayer(String message, Color pColor, boolean isBold, boolean isItalic, boolean isUnderlined, boolean isObfuscated, boolean hasStrikethrough) {
        Minecraft.getInstance().player.sendStatusMessage(new StringTextComponent(message).setStyle(getStyle(pColor, isBold, isItalic, isUnderlined, isObfuscated, hasStrikethrough)), false);
    }

    private net.minecraft.util.text.Style getStyle(Color pColor, boolean isBold, boolean isItalic, boolean isUnderlined, boolean isObfuscated, boolean hasStrikethrough) {
        net.minecraft.util.text.Style lChatStyle = new net.minecraft.util.text.Style();
        lChatStyle
                .setBold(isBold)
                .setItalic(isItalic)
                .setUnderlined(isUnderlined)
                .setObfuscated(isObfuscated)
                .setStrikethrough(hasStrikethrough);

        TextFormatting color =
                TextFormatting.getValueByName(
                        pColor.name()
                );

        lChatStyle.setColor(color);

        return lChatStyle;
    }

    @Override
    @OnlyIn(Dist.DEDICATED_SERVER)
    public void notifyServer(String message) {
        notifyServer(message, Color.WHITE);
    }

    @Override
    @OnlyIn(Dist.DEDICATED_SERVER)
    public void notifyServer(String message, Color pColor) {
        notifyServer(message, pColor, false, false, false, false, false);
    }

    @Override
    @OnlyIn(Dist.DEDICATED_SERVER)
    public void notifyServer(String message, Color pColor, boolean isBold, boolean isItalic, boolean isUnderlined, boolean isObfuscated, boolean hasStrikethrough) {
        LaunchCommon.info(new StringTextComponent(message).setStyle(getStyle(pColor, isBold, isItalic, isUnderlined, isObfuscated, hasStrikethrough)).getFormattedText());
    }

    @Override
    public void notifyUser(String message) {
        notifyUser(message, Color.WHITE);
    }

    @Override
    public void notifyUser(String message, Color pColor) {
        notifyUser(message, pColor, false, false, false, false, false);
    }

    @Override
    public void notifyUser(String message, Color pColor, boolean isBold, boolean isItalic, boolean isUnderlined, boolean isObfuscated, boolean hasStrikethrough) {
        if (FMLEnvironment.dist.isClient()) {
            notifyPlayer(message, pColor, isBold, isItalic, isUnderlined, isObfuscated, hasStrikethrough);
        } else {
            notifyServer(message, pColor, isBold, isItalic, isUnderlined, isObfuscated, hasStrikethrough);
        }
    }
}
