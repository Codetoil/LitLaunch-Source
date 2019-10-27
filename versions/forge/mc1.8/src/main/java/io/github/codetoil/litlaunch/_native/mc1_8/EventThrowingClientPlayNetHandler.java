package io.github.codetoil.litlaunch._native.mc1_8;

import io.github.codetoil.litlaunch.api.ChainableMap;
import io.github.codetoil.litlaunch.core.event.LitEvent;
import io.github.codetoil.litlaunch.core.event.LitEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.IChatComponent;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class EventThrowingClientPlayNetHandler extends NetHandlerPlayClient
{
	public NetHandlerPlayClient netHandlerOriginal;

	public EventThrowingClientPlayNetHandler(NetHandlerPlayClient netHandlerOriginal)
	{
		super(Minecraft.getMinecraft(), ObfuscationReflectionHelper.getPrivateValue(NetHandlerPlayClient.class, netHandlerOriginal, "field_147307_j", "guiScreenServer"), netHandlerOriginal.getNetworkManager(), netHandlerOriginal.getGameProfile());
		this.netHandlerOriginal = netHandlerOriginal;
	}

	@Override
	public void handleSpawnObject(S0EPacketSpawnObject packetIn)
	{
		netHandlerOriginal.handleSpawnObject(packetIn);
	}

	@Override
	public void handleSpawnExperienceOrb(S11PacketSpawnExperienceOrb packetIn)
	{
		netHandlerOriginal.handleSpawnExperienceOrb(packetIn);
	}

	@Override
	public void handleSpawnGlobalEntity(S2CPacketSpawnGlobalEntity packetIn)
	{
		netHandlerOriginal.handleSpawnGlobalEntity(packetIn);
	}

	@Override
	public void handleSpawnMob(S0FPacketSpawnMob packetIn)
	{
		netHandlerOriginal.handleSpawnMob(packetIn);
	}

	@Override
	public void handleScoreboardObjective(S3BPacketScoreboardObjective packetIn)
	{
		netHandlerOriginal.handleScoreboardObjective(packetIn);
	}

	@Override
	public void handleSpawnPainting(S10PacketSpawnPainting packetIn)
	{
		netHandlerOriginal.handleSpawnPainting(packetIn);
	}

	@Override
	public void handleSpawnPlayer(S0CPacketSpawnPlayer packetIn)
	{
		netHandlerOriginal.handleSpawnPlayer(packetIn);
	}

	@Override
	public void handleAnimation(S0BPacketAnimation packetIn)
	{
		netHandlerOriginal.handleAnimation(packetIn);
	}

	@Override
	public void handleStatistics(S37PacketStatistics packetIn)
	{
		netHandlerOriginal.handleStatistics(packetIn);
	}

	@Override
	public void handleBlockBreakAnim(S25PacketBlockBreakAnim packetIn)
	{
		netHandlerOriginal.handleBlockBreakAnim(packetIn);
	}

	@Override
	public void handleSignEditorOpen(S36PacketSignEditorOpen packetIn)
	{
		netHandlerOriginal.handleSignEditorOpen(packetIn);
	}

	@Override
	public void handleUpdateTileEntity(S35PacketUpdateTileEntity packetIn)
	{
		netHandlerOriginal.handleUpdateTileEntity(packetIn);
	}

	@Override
	public void handleBlockAction(S24PacketBlockAction packetIn)
	{
		netHandlerOriginal.handleBlockAction(packetIn);
	}

	@Override
	public void handleBlockChange(S23PacketBlockChange packetIn)
	{
		netHandlerOriginal.handleBlockChange(packetIn);
	}

	@Override
	public void handleChat(S02PacketChat packetIn)
	{
		netHandlerOriginal.handleChat(packetIn);
	}

	@Override
	public void handleTabComplete(S3APacketTabComplete packetIn)
	{
		netHandlerOriginal.handleTabComplete(packetIn);
	}

	@Override
	public void handleMultiBlockChange(S22PacketMultiBlockChange packetIn)
	{
		netHandlerOriginal.handleMultiBlockChange(packetIn);
	}

	@Override
	public void handleMaps(S34PacketMaps packetIn)
	{
		netHandlerOriginal.handleMaps(packetIn);
	}

	@Override
	public void handleConfirmTransaction(S32PacketConfirmTransaction packetIn)
	{
		netHandlerOriginal.handleConfirmTransaction(packetIn);
	}

	@Override
	public void handleCloseWindow(S2EPacketCloseWindow packetIn)
	{
		netHandlerOriginal.handleCloseWindow(packetIn);
	}

	@Override
	public void handleWindowItems(S30PacketWindowItems packetIn)
	{
		netHandlerOriginal.handleWindowItems(packetIn);
	}

	@Override
	public void handleOpenWindow(S2DPacketOpenWindow packetIn)
	{
		netHandlerOriginal.handleOpenWindow(packetIn);
	}

	@Override
	public void handleWindowProperty(S31PacketWindowProperty packetIn)
	{
		netHandlerOriginal.handleWindowProperty(packetIn);
	}

	@Override
	public void handleSetSlot(S2FPacketSetSlot packetIn)
	{
		netHandlerOriginal.handleSetSlot(packetIn);
	}

	@Override
	public void handleCustomPayload(S3FPacketCustomPayload packetIn)
	{
		netHandlerOriginal.handleCustomPayload(packetIn);
	}

	@Override
	public void handleDisconnect(S40PacketDisconnect packetIn)
	{
		netHandlerOriginal.handleDisconnect(packetIn);
	}

	@Override
	public void handleUseBed(S0APacketUseBed packetIn)
	{
		netHandlerOriginal.handleUseBed(packetIn);
	}

	@Override
	public void handleEntityStatus(S19PacketEntityStatus packetIn)
	{
		netHandlerOriginal.handleEntityStatus(packetIn);
	}

	@Override
	public void handleEntityAttach(S1BPacketEntityAttach packetIn)
	{
		netHandlerOriginal.handleEntityAttach(packetIn);
	}

	@Override
	public void handleExplosion(S27PacketExplosion packetIn)
	{
		netHandlerOriginal.handleExplosion(packetIn);
	}

	@Override
	public void handleChangeGameState(S2BPacketChangeGameState packetIn)
	{
		netHandlerOriginal.handleChangeGameState(packetIn);
	}

	@Override
	public void handleKeepAlive(S00PacketKeepAlive packetIn)
	{
		netHandlerOriginal.handleKeepAlive(packetIn);
	}

	@Override
	public void handleChunkData(S21PacketChunkData packetIn)
	{
		netHandlerOriginal.handleChunkData(packetIn);
	}

	@Override
	public void handleMapChunkBulk(S26PacketMapChunkBulk packetIn)
	{
		netHandlerOriginal.handleMapChunkBulk(packetIn);
	}

	@Override
	public void handleEffect(S28PacketEffect packetIn)
	{
		netHandlerOriginal.handleEffect(packetIn);
	}

	@Override
	public void handleJoinGame(S01PacketJoinGame packetIn)
	{
		netHandlerOriginal.handleJoinGame(packetIn);
	}

	@Override
	public void handleEntityMovement(S14PacketEntity packetIn)
	{
		netHandlerOriginal.handleEntityMovement(packetIn);
	}

	@Override
	public void handlePlayerPosLook(S08PacketPlayerPosLook packetIn)
	{
		netHandlerOriginal.handlePlayerPosLook(packetIn);
	}

	@Override
	public void handleParticles(S2APacketParticles packetIn)
	{
		netHandlerOriginal.handleParticles(packetIn);
	}

	@Override
	public void handlePlayerAbilities(S39PacketPlayerAbilities packetIn)
	{
		netHandlerOriginal.handlePlayerAbilities(packetIn);
	}

	@Override
	public void handlePlayerListItem(S38PacketPlayerListItem packetIn)
	{
		netHandlerOriginal.handlePlayerListItem(packetIn);
	}

	@Override
	public void handleDestroyEntities(S13PacketDestroyEntities packetIn)
	{
		netHandlerOriginal.handleDestroyEntities(packetIn);
	}

	@Override
	public void handleRemoveEntityEffect(S1EPacketRemoveEntityEffect packetIn)
	{
		netHandlerOriginal.handleRemoveEntityEffect(packetIn);
	}

	@Override
	public void handleRespawn(S07PacketRespawn packetIn)
	{
		netHandlerOriginal.handleRespawn(packetIn);
	}

	@Override
	public void handleEntityHeadLook(S19PacketEntityHeadLook packetIn)
	{
		netHandlerOriginal.handleEntityHeadLook(packetIn);
	}

	@Override
	public void handleHeldItemChange(S09PacketHeldItemChange packetIn)
	{
		netHandlerOriginal.handleHeldItemChange(packetIn);
	}

	@Override
	public void handleDisplayScoreboard(S3DPacketDisplayScoreboard packetIn)
	{
		netHandlerOriginal.handleDisplayScoreboard(packetIn);
	}

	@Override
	public void handleEntityMetadata(S1CPacketEntityMetadata packetIn)
	{
		netHandlerOriginal.handleEntityMetadata(packetIn);
	}

	@Override
	public void handleEntityVelocity(S12PacketEntityVelocity packetIn)
	{
		netHandlerOriginal.handleEntityVelocity(packetIn);
	}

	@Override
	public void handleEntityEquipment(S04PacketEntityEquipment packetIn)
	{
		netHandlerOriginal.handleEntityEquipment(packetIn);
	}

	@Override
	public void handleSetExperience(S1FPacketSetExperience packetIn)
	{
		netHandlerOriginal.handleSetExperience(packetIn);
	}

	@Override
	public void handleUpdateHealth(S06PacketUpdateHealth packetIn)
	{
		netHandlerOriginal.handleUpdateHealth(packetIn);
	}

	@Override
	public void handleTeams(S3EPacketTeams packetIn)
	{
		netHandlerOriginal.handleTeams(packetIn);
	}

	@Override
	public void handleUpdateScore(S3CPacketUpdateScore packetIn)
	{
		netHandlerOriginal.handleUpdateScore(packetIn);
	}

	@Override
	public void handleSpawnPosition(S05PacketSpawnPosition packetIn)
	{
		netHandlerOriginal.handleSpawnPosition(packetIn);
	}

	@Override
	public void handleTimeUpdate(S03PacketTimeUpdate packetIn)
	{
		netHandlerOriginal.handleTimeUpdate(packetIn);
		LitEventHandler.COMMON.post(new LitEvent(this, LitEvent.TYPE.ONPACKET, ChainableMap.<String, Object>newMap().putChain("Type", "WorldTime").putChain("totalWorldTime", packetIn.getTotalWorldTime()).putChain("worldTime", packetIn.getWorldTime()).putChain("raw", packetIn)), true);
	}

	@Override
	public void handleUpdateSign(S33PacketUpdateSign packetIn)
	{
		netHandlerOriginal.handleUpdateSign(packetIn);
	}

	@Override
	public void handleSoundEffect(S29PacketSoundEffect packetIn)
	{
		netHandlerOriginal.handleSoundEffect(packetIn);
	}

	@Override
	public void handleCollectItem(S0DPacketCollectItem packetIn)
	{
		netHandlerOriginal.handleCollectItem(packetIn);
	}

	@Override
	public void handleEntityTeleport(S18PacketEntityTeleport packetIn)
	{
		netHandlerOriginal.handleEntityTeleport(packetIn);
	}

	@Override
	public void handleEntityProperties(S20PacketEntityProperties packetIn)
	{
		netHandlerOriginal.handleEntityProperties(packetIn);
	}

	@Override
	public void handleEntityEffect(S1DPacketEntityEffect packetIn)
	{
		netHandlerOriginal.handleEntityEffect(packetIn);
	}

	@Override
	public void handleCombatEvent(S42PacketCombatEvent packetIn)
	{
		netHandlerOriginal.handleCombatEvent(packetIn);
	}

	@Override
	public void handleServerDifficulty(S41PacketServerDifficulty packetIn)
	{
		netHandlerOriginal.handleServerDifficulty(packetIn);
	}

	@Override
	public void handleCamera(S43PacketCamera packetIn)
	{
		netHandlerOriginal.handleCamera(packetIn);
	}

	@Override
	public void handleWorldBorder(S44PacketWorldBorder packetIn)
	{
		netHandlerOriginal.handleWorldBorder(packetIn);
	}

	@Override
	public void handleTitle(S45PacketTitle packetIn)
	{
		netHandlerOriginal.handleTitle(packetIn);
	}

	@Override
	public void handleSetCompressionLevel(S46PacketSetCompressionLevel packetIn)
	{
		netHandlerOriginal.handleSetCompressionLevel(packetIn);
	}

	@Override
	public void handlePlayerListHeaderFooter(S47PacketPlayerListHeaderFooter packetIn)
	{
		netHandlerOriginal.handlePlayerListHeaderFooter(packetIn);
	}

	@Override
	public void handleResourcePack(S48PacketResourcePackSend packetIn)
	{
		netHandlerOriginal.handleResourcePack(packetIn);
	}

	@Override
	public void handleEntityNBT(S49PacketUpdateEntityNBT packetIn)
	{
		netHandlerOriginal.handleEntityNBT(packetIn);
	}

	@Override
	public void onDisconnect(IChatComponent reason)
	{
		netHandlerOriginal.onDisconnect(reason);
	}
}
