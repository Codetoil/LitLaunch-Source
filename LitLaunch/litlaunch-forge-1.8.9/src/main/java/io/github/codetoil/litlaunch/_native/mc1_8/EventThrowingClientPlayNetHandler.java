package io.github.codetoil.litlaunch._native.mc1_8;

import io.github.codetoil.litlaunch.api.ChainableMap;
import io.github.codetoil.litlaunch.core.event.LitEvent;
import io.github.codetoil.litlaunch.core.event.LitEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S0EPacketSpawnObject;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.Map;
import net.minecraft.network.play.server.*;

public class EventThrowingClientPlayNetHandler extends NetHandlerPlayClient {
    public NetHandlerPlayClient netHandlerOriginal;

    public EventThrowingClientPlayNetHandler(NetHandlerPlayClient netHandlerOriginal) {
        super(Minecraft.getMinecraft(), ObfuscationReflectionHelper.getPrivateValue(NetHandlerPlayClient.class, netHandlerOriginal, "field_147307_j", "guiScreenServer"), netHandlerOriginal.getNetworkManager(), netHandlerOriginal.getGameProfile());
        this.netHandlerOriginal = netHandlerOriginal;
    }

    @Override
    public void handleSpawnObject(S0EPacketSpawnObject packetIn) {
        netHandlerOriginal.handleSpawnObject(packetIn);
        sendClientEvent("SpawnObject", packetIn);
    }

    @Override
    public void handleSpawnExperienceOrb(S11PacketSpawnExperienceOrb packetIn) {
        netHandlerOriginal.handleSpawnExperienceOrb(packetIn);
        sendClientEvent("SpawnExpienceOrb", packetIn);
    }

    @Override
    public void handleSpawnGlobalEntity(S2CPacketSpawnGlobalEntity packetIn) {
        netHandlerOriginal.handleSpawnGlobalEntity(packetIn);
        sendClientEvent("SpawnGlobalEntity", packetIn);
    }

    @Override
    public void handleSpawnMob(S0FPacketSpawnMob packetIn) {
        netHandlerOriginal.handleSpawnMob(packetIn);
        sendClientEvent("SpawnMob", packetIn);
    }

    @Override
    public void handleScoreboardObjective(S3BPacketScoreboardObjective packetIn) {
        netHandlerOriginal.handleScoreboardObjective(packetIn);
        sendClientEvent("ScoreboardObjective", packetIn);
    }

    @Override
    public void handleSpawnPainting(S10PacketSpawnPainting packetIn) {
        netHandlerOriginal.handleSpawnPainting(packetIn);
        sendClientEvent("SpawnPainting", packetIn);
    }

    @Override
    public void handleSpawnPlayer(S0CPacketSpawnPlayer packetIn) {
        netHandlerOriginal.handleSpawnPlayer(packetIn);
        sendClientEvent("SpawnPlayer", packetIn);
    }

    @Override
    public void handleAnimation(S0BPacketAnimation packetIn) {
        netHandlerOriginal.handleAnimation(packetIn);
        sendClientEvent("Animation", packetIn);
    }

    @Override
    public void handleStatistics(S37PacketStatistics packetIn) {
        netHandlerOriginal.handleStatistics(packetIn);
        sendClientEvent("Statistics", packetIn);
    }

    @Override
    public void handleBlockBreakAnim(S25PacketBlockBreakAnim packetIn) {
        netHandlerOriginal.handleBlockBreakAnim(packetIn);
        sendClientEvent("BlockBreakAnim", packetIn);
    }

    @Override
    public void handleSignEditorOpen(S36PacketSignEditorOpen packetIn) {
        netHandlerOriginal.handleSignEditorOpen(packetIn);
        sendClientEvent("SignEditorOpen", packetIn);
    }

    @Override
    public void handleUpdateTileEntity(S35PacketUpdateTileEntity packetIn) {
        netHandlerOriginal.handleUpdateTileEntity(packetIn);
        sendClientEvent("UpdateTileEntity", packetIn);
    }

    @Override
    public void handleBlockAction(S24PacketBlockAction packetIn) {
        netHandlerOriginal.handleBlockAction(packetIn);
        sendClientEvent("BlockAction", packetIn);
    }

    @Override
    public void handleBlockChange(S23PacketBlockChange packetIn) {
        netHandlerOriginal.handleBlockChange(packetIn);
        sendClientEvent("BlockChange", packetIn);
    }

    @Override
    public void handleChat(S02PacketChat packetIn) {
        sendClientEvent("Chat", false, packetIn, ChainableMap.<String, Object>newMap()
                .putChain("ChatComponent", packetIn.getChatComponent().getUnformattedText())
                .putChain("ChatType", packetIn.getType()));
        netHandlerOriginal.handleChat(packetIn);
    }

    @Override
    public void handleTabComplete(S3APacketTabComplete packetIn) {
        netHandlerOriginal.handleTabComplete(packetIn);
        sendClientEvent("TabComplete", packetIn);
    }

    @Override
    public void handleMultiBlockChange(S22PacketMultiBlockChange packetIn) {
        netHandlerOriginal.handleMultiBlockChange(packetIn);
        sendClientEvent("MultiBlockChange", packetIn);
    }

    @Override
    public void handleMaps(S34PacketMaps packetIn) {
        netHandlerOriginal.handleMaps(packetIn);
        sendClientEvent("Maps", packetIn);
    }

    @Override
    public void handleConfirmTransaction(S32PacketConfirmTransaction packetIn) {
        netHandlerOriginal.handleConfirmTransaction(packetIn);
        sendClientEvent("ConfirmTransaction", packetIn);
    }

    @Override
    public void handleCloseWindow(S2EPacketCloseWindow packetIn) {
        netHandlerOriginal.handleCloseWindow(packetIn);
        sendClientEvent("CloseWindow", packetIn);
    }

    @Override
    public void handleWindowItems(S30PacketWindowItems packetIn) {
        netHandlerOriginal.handleWindowItems(packetIn);
        sendClientEvent("WindowItems", packetIn);
    }

    @Override
    public void handleOpenWindow(S2DPacketOpenWindow packetIn) {
        netHandlerOriginal.handleOpenWindow(packetIn);
        sendClientEvent("OpenHorseWindow", packetIn);
    }

    @Override
    public void handleWindowProperty(S31PacketWindowProperty packetIn) {
        netHandlerOriginal.handleWindowProperty(packetIn);
        sendClientEvent("WindowProperty", packetIn);
    }

    @Override
    public void handleSetSlot(S2FPacketSetSlot packetIn) {
        netHandlerOriginal.handleSetSlot(packetIn);
        sendClientEvent("SetSlot", packetIn);
    }

    @Override
    public void handleCustomPayload(S3FPacketCustomPayload packetIn) {
        netHandlerOriginal.handleCustomPayload(packetIn);
        sendClientEvent("CustomPayload", packetIn);
    }

    @Override
    public void handleDisconnect(S40PacketDisconnect packetIn) {
        netHandlerOriginal.handleDisconnect(packetIn);
        sendClientEvent("Disconnect", packetIn);
    }

    @Override
    public void handleUseBed(S0APacketUseBed packetIn) {
        netHandlerOriginal.handleUseBed(packetIn);
        sendClientEvent("BlockAction", packetIn);
    }

    @Override
    public void handleEntityStatus(S19PacketEntityStatus packetIn) {
        netHandlerOriginal.handleEntityStatus(packetIn);
        sendClientEvent("EntityStatus", packetIn);
    }

    @Override
    public void handleEntityAttach(S1BPacketEntityAttach packetIn) {
        netHandlerOriginal.handleEntityAttach(packetIn);
        sendClientEvent("EntityAttach", packetIn);
    }

    @Override
    public void handleExplosion(S27PacketExplosion packetIn) {
        netHandlerOriginal.handleExplosion(packetIn);
        sendClientEvent("Explosion", packetIn);
    }

    @Override
    public void handleChangeGameState(S2BPacketChangeGameState packetIn) {
        netHandlerOriginal.handleChangeGameState(packetIn);
        sendClientEvent("ChangeGameState", packetIn);
    }

    @Override
    public void handleKeepAlive(S00PacketKeepAlive packetIn) {
        netHandlerOriginal.handleKeepAlive(packetIn);
        sendClientEvent("KeepAlive", packetIn);
    }

    @Override
    public void handleChunkData(S21PacketChunkData packetIn) {
        netHandlerOriginal.handleChunkData(packetIn);
        sendClientEvent("ChunkData", packetIn);
    }

    @Override
    public void handleMapChunkBulk(S26PacketMapChunkBulk packetIn) {
        netHandlerOriginal.handleMapChunkBulk(packetIn);
        sendClientEvent("MapChunkBulk", packetIn);
    }

    @Override
    public void handleEffect(S28PacketEffect packetIn) {
        netHandlerOriginal.handleEffect(packetIn);
        sendClientEvent("Effect", packetIn);
    }

    @Override
    public void handleJoinGame(S01PacketJoinGame packetIn) {
        netHandlerOriginal.handleJoinGame(packetIn);
        sendClientEvent("JoinGame", packetIn);
    }

    @Override
    public void handleEntityMovement(S14PacketEntity packetIn) {
        netHandlerOriginal.handleEntityMovement(packetIn);
        sendClientEvent("EntityMovement", packetIn);
    }

    @Override
    public void handlePlayerPosLook(S08PacketPlayerPosLook packetIn) {
        netHandlerOriginal.handlePlayerPosLook(packetIn);
        sendClientEvent("PlayerPosLook", packetIn);
    }

    @Override
    public void handleParticles(S2APacketParticles packetIn) {
        netHandlerOriginal.handleParticles(packetIn);
        sendClientEvent("Particles", packetIn);
    }

    @Override
    public void handlePlayerAbilities(S39PacketPlayerAbilities packetIn) {
        netHandlerOriginal.handlePlayerAbilities(packetIn);
        sendClientEvent("PlayerAbilities", packetIn);
    }

    @Override
    public void handlePlayerListItem(S38PacketPlayerListItem packetIn) {
        netHandlerOriginal.handlePlayerListItem(packetIn);
        sendClientEvent("PlayerListItem", packetIn);
    }

    @Override
    public void handleDestroyEntities(S13PacketDestroyEntities packetIn) {
        netHandlerOriginal.handleDestroyEntities(packetIn);
        sendClientEvent("DestroyEntities", packetIn);
    }

    @Override
    public void handleRemoveEntityEffect(S1EPacketRemoveEntityEffect packetIn) {
        netHandlerOriginal.handleRemoveEntityEffect(packetIn);
        sendClientEvent("RemoveEntityEffect", packetIn);
    }

    @Override
    public void handleRespawn(S07PacketRespawn packetIn) {
        netHandlerOriginal.handleRespawn(packetIn);
        sendClientEvent("Respawn", packetIn);
    }

    @Override
    public void handleEntityHeadLook(S19PacketEntityHeadLook packetIn) {
        netHandlerOriginal.handleEntityHeadLook(packetIn);
        sendClientEvent("EntityHeadLook", packetIn);
    }

    @Override
    public void handleHeldItemChange(S09PacketHeldItemChange packetIn) {
        netHandlerOriginal.handleHeldItemChange(packetIn);
        sendClientEvent("HeldItemChange", packetIn);
    }

    @Override
    public void handleDisplayScoreboard(S3DPacketDisplayScoreboard packetIn) {
        netHandlerOriginal.handleDisplayScoreboard(packetIn);
        sendClientEvent("DisplayObjective", packetIn);
    }

    @Override
    public void handleEntityMetadata(S1CPacketEntityMetadata packetIn) {
        netHandlerOriginal.handleEntityMetadata(packetIn);
        sendClientEvent("EntityMetadata", packetIn);
    }

    @Override
    public void handleEntityVelocity(S12PacketEntityVelocity packetIn) {
        netHandlerOriginal.handleEntityVelocity(packetIn);
        sendClientEvent("EntityVelocity", packetIn);
    }

    @Override
    public void handleEntityEquipment(S04PacketEntityEquipment packetIn) {
        netHandlerOriginal.handleEntityEquipment(packetIn);
        sendClientEvent("EntityEquipment", packetIn);
    }

    @Override
    public void handleSetExperience(S1FPacketSetExperience packetIn) {
        netHandlerOriginal.handleSetExperience(packetIn);
        sendClientEvent("SetExperience", packetIn);
    }

    @Override
    public void handleUpdateHealth(S06PacketUpdateHealth packetIn) {
        netHandlerOriginal.handleUpdateHealth(packetIn);
        sendClientEvent("UpdateHealth", packetIn);
    }

    @Override
    public void handleTeams(S3EPacketTeams packetIn) {
        netHandlerOriginal.handleTeams(packetIn);
        sendClientEvent("Teams", packetIn);
    }

    @Override
    public void handleUpdateScore(S3CPacketUpdateScore packetIn) {
        netHandlerOriginal.handleUpdateScore(packetIn);
        sendClientEvent("UpdateScore", packetIn);
    }

    @Override
    public void handleSpawnPosition(S05PacketSpawnPosition packetIn) {
        netHandlerOriginal.handleSpawnPosition(packetIn);
        sendClientEvent("SpawnPosition", packetIn);
    }

    @Override
    public void handleTimeUpdate(S03PacketTimeUpdate packetIn) {
        sendClientEvent("TimeUpdate", true, packetIn, ChainableMap.<String, Object>newMap()
            .putChain("totalWorldTime", packetIn.getTotalWorldTime())
            .putChain("worldTime", packetIn.getWorldTime()));
        netHandlerOriginal.handleTimeUpdate(packetIn);
    }

    @Override
    public void handleUpdateSign(S33PacketUpdateSign packetIn) {
        netHandlerOriginal.handleUpdateSign(packetIn);
        sendClientEvent("UpdateSign", packetIn);
    }

    @Override
    public void handleSoundEffect(S29PacketSoundEffect packetIn) {
        netHandlerOriginal.handleSoundEffect(packetIn);
        sendClientEvent("SoundEffect", packetIn);
    }

    @Override
    public void handleCollectItem(S0DPacketCollectItem packetIn) {
        netHandlerOriginal.handleCollectItem(packetIn);
        sendClientEvent("CollectItem", packetIn);
    }

    @Override
    public void handleEntityTeleport(S18PacketEntityTeleport packetIn) {
        netHandlerOriginal.handleEntityTeleport(packetIn);
        sendClientEvent("EntityTeleport", packetIn);
    }

    @Override
    public void handleEntityProperties(S20PacketEntityProperties packetIn) {
        netHandlerOriginal.handleEntityProperties(packetIn);
        sendClientEvent("EntityProperties", packetIn);
    }

    @Override
    public void handleEntityEffect(S1DPacketEntityEffect packetIn) {
        netHandlerOriginal.handleEntityEffect(packetIn);
        sendClientEvent("EntityEffect", packetIn);
    }

    @Override
    public void handleCombatEvent(S42PacketCombatEvent packetIn) {
        netHandlerOriginal.handleCombatEvent(packetIn);
        sendClientEvent("CombatEvent", packetIn);
    }

    @Override
    public void handleServerDifficulty(S41PacketServerDifficulty packetIn) {
        netHandlerOriginal.handleServerDifficulty(packetIn);
        sendClientEvent("ServerDifficulty", packetIn);
    }

    @Override
    public void handleCamera(S43PacketCamera packetIn) {
        netHandlerOriginal.handleCamera(packetIn);
        sendClientEvent("Camera", packetIn);
    }

    @Override
    public void handleWorldBorder(S44PacketWorldBorder packetIn) {
        netHandlerOriginal.handleWorldBorder(packetIn);
        sendClientEvent("WorldBorder", packetIn);
    }

    @Override
    public void handleTitle(S45PacketTitle packetIn) {
        netHandlerOriginal.handleTitle(packetIn);
        sendClientEvent("Title", packetIn);
    }

    @Override
    public void handleSetCompressionLevel(S46PacketSetCompressionLevel packetIn) {
        netHandlerOriginal.handleSetCompressionLevel(packetIn);
        sendClientEvent("SetCompressionLevel", packetIn);
    }

    @Override
    public void handlePlayerListHeaderFooter(S47PacketPlayerListHeaderFooter packetIn) {
        netHandlerOriginal.handlePlayerListHeaderFooter(packetIn);
        sendClientEvent("PlayerListHeaderFooter", packetIn);
    }

    @Override
    public void handleResourcePack(S48PacketResourcePackSend packetIn) {
        netHandlerOriginal.handleResourcePack(packetIn);
        sendClientEvent("ResourcePack", packetIn);
    }

    @Override
    public void handleEntityNBT(S49PacketUpdateEntityNBT packetIn) {
        netHandlerOriginal.handleEntityNBT(packetIn);
        sendClientEvent("EntityNBT", packetIn);
    }


    @Override
    public void onDisconnect(IChatComponent reason) {
        LitEventHandler.CLIENT.post(new LitEvent(this, LitEvent.TYPE.DISCONNECT, reason.getUnformattedText()));
        netHandlerOriginal.onDisconnect(reason);
    }

    private void sendClientEvent(String name, boolean isSpammy, Packet<?> packet, Map<String, Object> extraInfo) {
        ChainableMap<String, Object> map = ChainableMap.<String, Object>newMap()
                .putChain("Type", name)
                .putChain("Raw", packet);
        map.putAll(extraInfo);
        LitEventHandler.CLIENT.post(
                new LitEvent(this, LitEvent.TYPE.ONPACKET, map), isSpammy);
    }

    private void sendClientEvent(String name, Packet<?> packet)
    {
        sendClientEvent(name, true, packet, ChainableMap.newMap());
    }
}
