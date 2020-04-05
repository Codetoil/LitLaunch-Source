/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch._native.mc1_8;


import io.github.codetoil.litlaunch.api.IDoThing;
import io.github.codetoil.litlaunch.core.LaunchCommon;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DoThing implements IDoThing {
    public static final IDoThing INSTANCE = new DoThing();

    private DoThing() {
    }

    @Override
    public void sendAsChatMessage(String message) {
        if (FMLCommonHandler.instance().getSide().isClient()) {
            sendAsClientChatMessage(message);
        } else {
            sendAsServerChatMessage(message);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void sendAsClientChatMessage(String message) {
        Minecraft.getMinecraft().thePlayer.sendChatMessage(message);
    }

    @Override
    @SideOnly(Side.SERVER)
    public void sendAsServerChatMessage(String message) {
        FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().sendChatMsg(new ChatComponentText(message));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void notifyPlayer(String message) {
        notifyPlayer(message, Color.WHITE);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void notifyPlayer(String message, Color pColor) {
        notifyPlayer(message, pColor, false, false, false, false, false);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void notifyPlayer(String message, Color pColor, boolean isBold, boolean isItalic, boolean isUnderlined, boolean isObfuscated, boolean hasStrikethrough) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message).setChatStyle(getStyle(pColor, isBold, isItalic, isUnderlined, isObfuscated, hasStrikethrough)));
    }

    private ChatStyle getStyle(Color pColor, boolean isBold, boolean isItalic, boolean isUnderlined, boolean isObfuscated, boolean hasStrikethrough) {
        ChatStyle lChatStyle = new ChatStyle();
        lChatStyle
                .setBold(isBold)
                .setItalic(isItalic)
                .setUnderlined(isUnderlined)
                .setObfuscated(isObfuscated)
                .setStrikethrough(hasStrikethrough);

        EnumChatFormatting color =
                EnumChatFormatting.getValueByName(
                        pColor.name()
                );

        lChatStyle.setColor(color);

        return lChatStyle;
    }

    @Override
    @SideOnly(Side.SERVER)
    public void notifyServer(String message) {
        notifyServer(message, Color.WHITE);
    }

    @Override
    @SideOnly(Side.SERVER)
    public void notifyServer(String message, Color pColor) {
        notifyServer(message, pColor, false, false, false, false, false);
    }

    @Override
    @SideOnly(Side.SERVER)
    public void notifyServer(String message, Color pColor, boolean isBold, boolean isItalic, boolean isUnderlined, boolean isObfuscated, boolean hasStrikethrough) {
        LaunchCommon.info(new ChatComponentText(message).setChatStyle(getStyle(pColor, isBold, isItalic, isUnderlined, isObfuscated, hasStrikethrough)).getFormattedText());
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
        if (FMLCommonHandler.instance().getSide().isClient()) {
            notifyPlayer(message, pColor, isBold, isItalic, isUnderlined, isObfuscated, hasStrikethrough);
        } else {
            notifyServer(message, pColor, isBold, isItalic, isUnderlined, isObfuscated, hasStrikethrough);
        }
    }
}
