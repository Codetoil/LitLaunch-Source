/*
 * Copyright Codetoil (c) 2019
 */

package io.github.codetoil.litlaunch._native.mc1_7;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.github.codetoil.litlaunch.api.IDoThing;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

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
		Minecraft.getMinecraft().thePlayer.sendChatMessage(message);
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
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message).setChatStyle(getStyle(pColor, isBold, isItalic, isUnderlined, isObfuscated, hasStrikethrough)));
	}

	private ChatStyle getStyle(Color pColor, boolean isBold, boolean isItalic, boolean isUnderlined, boolean isObfuscated, boolean hasStrikethrough)
	{
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
}
