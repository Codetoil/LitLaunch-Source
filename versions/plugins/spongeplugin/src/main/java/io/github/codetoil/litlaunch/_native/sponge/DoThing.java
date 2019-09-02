/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch._native.sponge;

import io.github.codetoil.litlaunch.api.IDoThing;
import io.github.codetoil.litlaunch.core.LaunchCommon;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.*;

public class DoThing implements IDoThing
{
	public static final IDoThing INSTANCE = new DoThing();

	private DoThing()
	{
	}

	@Override
	public void sendAsClientChatMessage(String message)
	{
		throw new IllegalStateException("This should not be running on a client!");
	}

	@Override
	public void sendAsServerChatMessage(String message)
	{
		Sponge.getServer().getBroadcastChannel().send(Text.builder(message).build());
	}

	@Override
	public void sendAsChatMessage(String message)
	{
		Sponge.getServer().getBroadcastChannel().send(Text.builder(message).build());
	}

	@Override
	public void notifyPlayer(String message)
	{
		notifyPlayer(message, Color.WHITE);
	}

	@Override
	public void notifyPlayer(String message, Color pColor)
	{
		notifyPlayer(message, pColor, false, false, false, false, false);
	}

	@Override
	public void notifyPlayer(String message, Color pColor, boolean isBold, boolean isItalic, boolean isUnderlined, boolean isObfuscated, boolean hasStrikethrough)
	{
		throw new IllegalStateException("This should not be running on a client!");
	}

	@Override
	public void notifyServer(String message)
	{
		notifyServer(message, Color.WHITE);
	}

	@Override
	public void notifyServer(String message, Color pColor)
	{
		notifyServer(message, pColor, false, false, false, false, false);
	}

	@Override
	public void notifyServer(String message, Color pColor, boolean isBold, boolean isItalic, boolean isUnderlined, boolean isObfuscated, boolean hasStrikethrough)
	{
		Text.Builder builder = Text.builder();
		builder.format(getFormat(pColor, isBold, isItalic, isUnderlined, isObfuscated, hasStrikethrough));
		LaunchCommon.info(builder.build().toPlain());
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
		notifyServer(message, pColor, isBold, isItalic, isUnderlined, isObfuscated, hasStrikethrough);
	}

	private TextFormat getFormat(Color pColor, boolean isBold, boolean isItalic, boolean isUnderlined, boolean isObfuscated, boolean hasStrikethrough)
	{
		TextStyle style = TextStyles.of().bold(isBold).italic(isItalic).underline(isUnderlined).obfuscated(isObfuscated).strikethrough(hasStrikethrough);
		TextColor color = toColor(pColor);
		return TextFormat.of(color, style);
	}

	private TextColor toColor(Color color)
	{
		TextColor textColor;
		switch (color) {
			case RED:
				textColor = TextColors.RED;
				break;
			case AQUA:
				textColor = TextColors.AQUA;
				break;
			case BLUE:
				textColor = TextColors.BLUE;
				break;
			case GOLD:
				textColor = TextColors.GOLD;
				break;
			case GRAY:
				textColor = TextColors.GRAY;
				break;
			case BLACK:
				textColor = TextColors.BLACK;
				break;
			case GREEN:
				textColor = TextColors.GREEN;
				break;
			default:
			case WHITE:
				textColor = TextColors.WHITE;
				break;
			case YELLOW:
				textColor = TextColors.YELLOW;
				break;
			case DARK_RED:
				textColor = TextColors.DARK_RED;
				break;
			case DARK_AQUA:
				textColor = TextColors.DARK_AQUA;
				break;
			case DARK_BLUE:
				textColor = TextColors.DARK_BLUE;
				break;
			case DARK_GRAY:
				textColor = TextColors.DARK_GRAY;
				break;
			case DARK_GREEN:
				textColor = TextColors.DARK_GREEN;
				break;
			case DARK_PURPLE:
				textColor = TextColors.DARK_PURPLE;
				break;
			case LIGHT_PURPLE:
				textColor = TextColors.LIGHT_PURPLE;
				break;
		}
		return textColor;
	}
}
