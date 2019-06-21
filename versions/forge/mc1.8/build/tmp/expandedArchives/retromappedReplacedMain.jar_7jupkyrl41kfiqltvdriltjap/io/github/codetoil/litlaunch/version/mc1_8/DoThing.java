/*
 * Copyright Codetoil (c) 2019
 */

package io.github.codetoil.litlaunch.version.mc1_8;

import io.github.codetoil.litlaunch.api.IDoThing;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
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
		Minecraft.func_71410_x().field_71439_g.func_71165_d(message);
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
		Minecraft.func_71410_x().field_71439_g.func_145747_a(new ChatComponentText(message).func_150255_a(getStyle(pColor, isBold, isItalic, isUnderlined, isObfuscated, hasStrikethrough)));
	}

	private ChatStyle getStyle(Color pColor, boolean isBold, boolean isItalic, boolean isUnderlined, boolean isObfuscated, boolean hasStrikethrough)
	{
		ChatStyle lChatStyle = new ChatStyle();
		lChatStyle
				.func_150227_a(isBold)
				.func_150217_b(isItalic)
				.func_150228_d(isUnderlined)
				.func_150237_e(isObfuscated)
				.func_150225_c(hasStrikethrough);

		EnumChatFormatting color =
				EnumChatFormatting.func_96300_b(
						pColor.name()
				);

		lChatStyle.func_150238_a(color);

		return lChatStyle;
	}
}
