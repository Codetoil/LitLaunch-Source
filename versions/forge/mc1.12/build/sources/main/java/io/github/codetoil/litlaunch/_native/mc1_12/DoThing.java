/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch._native.mc1_12;

import io.github.codetoil.litlaunch.api.IDoThing;
import io.github.codetoil.litlaunch.core.LaunchCommon;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DoThing implements IDoThing
{
	public static final IDoThing INSTANCE = new DoThing();

	private DoThing()
	{
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void sendAsClientChatMessage(String message)
	{
		Minecraft.getMinecraft().player.sendChatMessage(message);
	}

	@Override
	@SideOnly(Side.SERVER)
	public void sendAsServerChatMessage(String message)
	{
		FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendMessage(new TextComponentString(message));
	}

	@Override
	public void sendAsChatMessage(String message)
	{
		if (FMLCommonHandler.instance().getSide().isClient())
		{
			sendAsClientChatMessage(message);
		} else
		{
			sendAsServerChatMessage(message);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void notifyPlayer(String message)
	{
		notifyPlayer(message, Color.WHITE);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void notifyPlayer(String message, Color pColor)
	{
		notifyPlayer(message, pColor, false, false, false, false, false);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void notifyPlayer(String message, Color pColor, boolean isBold, boolean isItalic, boolean isUnderlined, boolean isObfuscated, boolean hasStrikethrough)
	{
		Minecraft.getMinecraft().player.sendStatusMessage(new TextComponentString(message).setStyle(getStyle(pColor, isBold, isItalic, isUnderlined, isObfuscated, hasStrikethrough)), false);
	}

	@Override
	@SideOnly(Side.SERVER)
	public void notifyServer(String message)
	{
		notifyServer(message, Color.WHITE);
	}

	@Override
	@SideOnly(Side.SERVER)
	public void notifyServer(String message, Color pColor)
	{
		notifyServer(message, pColor, false, false, false, false, false);
	}

	@Override
	@SideOnly(Side.SERVER)
	public void notifyServer(String message, Color pColor, boolean isBold, boolean isItalic, boolean isUnderlined, boolean isObfuscated, boolean hasStrikethrough)
	{
		LaunchCommon.info(new TextComponentString(message).setStyle(getStyle(pColor, isBold, isItalic, isUnderlined, isObfuscated, hasStrikethrough)).getFormattedText());
	}

	@Override
	public void notifyUser(String message)
	{
		notifyUser(message, Color.WHITE);
	}

	@Override
	public void notifyUser(String message, Color pColor)
	{
		notifyUser(message, pColor, false, false, false, false, false);
	}

	@Override
	public void notifyUser(String message, Color pColor, boolean isBold, boolean isItalic, boolean isUnderlined, boolean isObfuscated, boolean hasStrikethrough)
	{
		if (FMLCommonHandler.instance().getSide().isClient())
		{
			notifyPlayer(message, pColor, isBold, isItalic, isUnderlined, isObfuscated, hasStrikethrough);
		} else
		{
			notifyServer(message, pColor, isBold, isItalic, isUnderlined, isObfuscated, hasStrikethrough);
		}
	}

	private net.minecraft.util.text.Style getStyle(Color pColor, boolean isBold, boolean isItalic, boolean isUnderlined, boolean isObfuscated, boolean hasStrikethrough)
	{
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
}
