/*
 * Copyright (c) Codetoil 2019
 */

package io.github.codetoil.litlaunch.api;

public interface IDoThing
{
	void sendAsClientChatMessage(String message);

	void sendAsServerChatMessage(String message);

	void sendAsChatMessage(String message);

	void notifyUser(String message);

	void notifyUser(String message, Color pColor);

	void notifyUser(String message, Color pColor, boolean isBold, boolean isItalic, boolean isUnderlined, boolean isObfuscated, boolean hasStrikethrough);

	void notifyServer(String message);

	void notifyServer(String message, Color pColor);

	void notifyServer(String message, Color pColor, boolean isBold, boolean isItalic, boolean isUnderlined, boolean isObfuscated, boolean hasStrikethrough);

	void notifyPlayer(String message);

	void notifyPlayer(String message, Color pColor);

	void notifyPlayer(String message, Color pColor, boolean isBold, boolean isItalic, boolean isUnderlined, boolean isObfuscated, boolean hasStrikethrough);

	enum Color
	{
		BLACK,
		DARK_BLUE,
		DARK_GREEN,
		DARK_AQUA,
		DARK_RED,
		DARK_PURPLE,
		GOLD,
		GRAY,
		DARK_GRAY,
		BLUE,
		GREEN,
		AQUA,
		RED,
		LIGHT_PURPLE,
		YELLOW,
		WHITE;
	}

	enum Style
	{
		OBFUSCATED,
		BOLD,
		STRIKETHROUGH,
		UNDERLINE,
		ITALIC,
		NONE;
	}
}
