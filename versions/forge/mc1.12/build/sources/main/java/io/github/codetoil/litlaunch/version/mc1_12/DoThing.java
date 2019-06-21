/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch.version.mc1_12;

import io.github.codetoil.litlaunch.api.IDoThing;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
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
	public void sendAsChatMessage(String message)
	{
		Minecraft.getMinecraft().player.sendChatMessage(message);
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
