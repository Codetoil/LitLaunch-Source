package io.github.codetoil.litlaunch._native.mc1_12;

import io.github.codetoil.litlaunch.api.ChainableMap;
import io.github.codetoil.litlaunch.core.event.LitEvent;
import io.github.codetoil.litlaunch.core.event.LitEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.Map;

public class EventThrowingClientPlayNetHandler extends NetHandlerPlayClient {
    public NetHandlerPlayClient netHandlerOriginal;

    public EventThrowingClientPlayNetHandler(NetHandlerPlayClient netHandlerOriginal) {
        super(Minecraft.getMinecraft(), ObfuscationReflectionHelper.getPrivateValue(NetHandlerPlayClient.class, netHandlerOriginal, "field_147307_j", "guiScreenServer"), netHandlerOriginal.getNetworkManager(), netHandlerOriginal.getGameProfile());
        this.netHandlerOriginal = netHandlerOriginal;
    }

    @Override
    public void handleSpawnObject(SPacketSpawnObject packetIn) {
        netHandlerOriginal.handleSpawnObject(packetIn);
    }

    @Override
    public void handleSpawnExperienceOrb(SPacketSpawnExperienceOrb packetIn) {
        netHandlerOriginal.handleSpawnExperienceOrb(packetIn);
    }

    @Override
    public void handleSpawnGlobalEntity(SPacketSpawnGlobalEntity packetIn) {
        netHandlerOriginal.handleSpawnGlobalEntity(packetIn);
    }

    @Override
    public void handleSpawnMob(SPacketSpawnMob packetIn) {
        netHandlerOriginal.handleSpawnMob(packetIn);
    }

    @Override
    public void handleScoreboardObjective(SPacketScoreboardObjective packetIn) {
        netHandlerOriginal.handleScoreboardObjective(packetIn);
    }

    @Override
    public void handleSpawnPainting(SPacketSpawnPainting packetIn) {
        netHandlerOriginal.handleSpawnPainting(packetIn);
    }

    @Override
    public void handleSpawnPlayer(SPacketSpawnPlayer packetIn) {
        netHandlerOriginal.handleSpawnPlayer(packetIn);
    }

    @Override
    public void handleAnimation(SPacketAnimation packetIn) {
        netHandlerOriginal.handleAnimation(packetIn);
    }

    @Override
    public void handleStatistics(SPacketStatistics packetIn) {
        netHandlerOriginal.handleStatistics(packetIn);
    }

    @Override
    public void handleRecipeBook(SPacketRecipeBook packetIn) {
        netHandlerOriginal.handleRecipeBook(packetIn);
    }

    @Override
    public void handleBlockBreakAnim(SPacketBlockBreakAnim packetIn) {
        netHandlerOriginal.handleBlockBreakAnim(packetIn);
    }

    @Override
    public void handleSignEditorOpen(SPacketSignEditorOpen packetIn) {
        netHandlerOriginal.handleSignEditorOpen(packetIn);
    }

    @Override
    public void handleUpdateTileEntity(SPacketUpdateTileEntity packetIn) {
        netHandlerOriginal.handleUpdateTileEntity(packetIn);
    }

    @Override
    public void handleBlockAction(SPacketBlockAction packetIn) {
        netHandlerOriginal.handleBlockAction(packetIn);
    }

    @Override
    public void handleBlockChange(SPacketBlockChange packetIn) {
        netHandlerOriginal.handleBlockChange(packetIn);
    }

    @Override
    public void handleChat(SPacketChat packetIn) {
        netHandlerOriginal.handleChat(packetIn);
    }

    @Override
    public void handleTabComplete(SPacketTabComplete packetIn) {
        netHandlerOriginal.handleTabComplete(packetIn);
    }

    @Override
    public void handleMultiBlockChange(SPacketMultiBlockChange packetIn) {
        netHandlerOriginal.handleMultiBlockChange(packetIn);
    }

    @Override
    public void handleMaps(SPacketMaps packetIn) {
        netHandlerOriginal.handleMaps(packetIn);
    }

    @Override
    public void handleConfirmTransaction(SPacketConfirmTransaction packetIn) {
        netHandlerOriginal.handleConfirmTransaction(packetIn);
    }

    @Override
    public void handleCloseWindow(SPacketCloseWindow packetIn) {
        netHandlerOriginal.handleCloseWindow(packetIn);
    }

    @Override
    public void handleWindowItems(SPacketWindowItems packetIn) {
        netHandlerOriginal.handleWindowItems(packetIn);
    }

    @Override
    public void handleOpenWindow(SPacketOpenWindow packetIn) {
        netHandlerOriginal.handleOpenWindow(packetIn);
    }

    @Override
    public void handleWindowProperty(SPacketWindowProperty packetIn) {
        netHandlerOriginal.handleWindowProperty(packetIn);
    }

    @Override
    public void handleSetSlot(SPacketSetSlot packetIn) {
        netHandlerOriginal.handleSetSlot(packetIn);
    }

    @Override
    public void handleCustomPayload(SPacketCustomPayload packetIn) {
        netHandlerOriginal.handleCustomPayload(packetIn);
    }

    @Override
    public void handleDisconnect(SPacketDisconnect packetIn) {
        netHandlerOriginal.handleDisconnect(packetIn);
    }

    @Override
    public void handleUseBed(SPacketUseBed packetIn) {
        netHandlerOriginal.handleUseBed(packetIn);
    }

    @Override
    public void handleEntityStatus(SPacketEntityStatus packetIn) {
        netHandlerOriginal.handleEntityStatus(packetIn);
    }

    @Override
    public void handleEntityAttach(SPacketEntityAttach packetIn) {
        netHandlerOriginal.handleEntityAttach(packetIn);
    }

    @Override
    public void handleSetPassengers(SPacketSetPassengers packetIn) {
        netHandlerOriginal.handleSetPassengers(packetIn);
    }

    @Override
    public void handleExplosion(SPacketExplosion packetIn) {
        netHandlerOriginal.handleExplosion(packetIn);
    }

    @Override
    public void handleChangeGameState(SPacketChangeGameState packetIn) {
        netHandlerOriginal.handleChangeGameState(packetIn);
    }

    @Override
    public void handleKeepAlive(SPacketKeepAlive packetIn) {
        netHandlerOriginal.handleKeepAlive(packetIn);
    }

    @Override
    public void handleChunkData(SPacketChunkData packetIn) {
        netHandlerOriginal.handleChunkData(packetIn);
    }

    @Override
    public void processChunkUnload(SPacketUnloadChunk packetIn) {
        netHandlerOriginal.processChunkUnload(packetIn);
    }

    @Override
    public void handleEffect(SPacketEffect packetIn) {
        netHandlerOriginal.handleEffect(packetIn);
    }

    @Override
    public void handleJoinGame(SPacketJoinGame packetIn) {
        netHandlerOriginal.handleJoinGame(packetIn);
    }

    @Override
    public void handleEntityMovement(SPacketEntity packetIn) {
        netHandlerOriginal.handleEntityMovement(packetIn);
    }

    @Override
    public void handlePlayerPosLook(SPacketPlayerPosLook packetIn) {
        netHandlerOriginal.handlePlayerPosLook(packetIn);
    }

    @Override
    public void handleParticles(SPacketParticles packetIn) {
        netHandlerOriginal.handleParticles(packetIn);
    }

    @Override
    public void handlePlayerAbilities(SPacketPlayerAbilities packetIn) {
        netHandlerOriginal.handlePlayerAbilities(packetIn);
    }

    @Override
    public void handlePlayerListItem(SPacketPlayerListItem packetIn) {
        netHandlerOriginal.handlePlayerListItem(packetIn);
    }

    @Override
    public void handleDestroyEntities(SPacketDestroyEntities packetIn) {
        netHandlerOriginal.handleDestroyEntities(packetIn);
    }

    @Override
    public void handleRemoveEntityEffect(SPacketRemoveEntityEffect packetIn) {
        netHandlerOriginal.handleRemoveEntityEffect(packetIn);
    }

    @Override
    public void handleRespawn(SPacketRespawn packetIn) {
        netHandlerOriginal.handleRespawn(packetIn);
    }

    @Override
    public void handleEntityHeadLook(SPacketEntityHeadLook packetIn) {
        netHandlerOriginal.handleEntityHeadLook(packetIn);
    }

    @Override
    public void handleHeldItemChange(SPacketHeldItemChange packetIn) {
        netHandlerOriginal.handleHeldItemChange(packetIn);
    }

    @Override
    public void handleDisplayObjective(SPacketDisplayObjective packetIn) {
        netHandlerOriginal.handleDisplayObjective(packetIn);
    }

    @Override
    public void handleEntityMetadata(SPacketEntityMetadata packetIn) {
        netHandlerOriginal.handleEntityMetadata(packetIn);
    }

    @Override
    public void handleEntityVelocity(SPacketEntityVelocity packetIn) {
        netHandlerOriginal.handleEntityVelocity(packetIn);
    }

    @Override
    public void handleEntityEquipment(SPacketEntityEquipment packetIn) {
        netHandlerOriginal.handleEntityEquipment(packetIn);
    }

    @Override
    public void handleSetExperience(SPacketSetExperience packetIn) {
        netHandlerOriginal.handleSetExperience(packetIn);
    }

    @Override
    public void handleUpdateHealth(SPacketUpdateHealth packetIn) {
        netHandlerOriginal.handleUpdateHealth(packetIn);
    }

    @Override
    public void handleTeams(SPacketTeams packetIn) {
        netHandlerOriginal.handleTeams(packetIn);
    }

    @Override
    public void handleUpdateScore(SPacketUpdateScore packetIn) {
        netHandlerOriginal.handleUpdateScore(packetIn);
    }

    @Override
    public void handleSpawnPosition(SPacketSpawnPosition packetIn) {
        netHandlerOriginal.handleSpawnPosition(packetIn);
    }

    @Override
    public void handleTimeUpdate(SPacketTimeUpdate packetIn) {
        netHandlerOriginal.handleTimeUpdate(packetIn);
        sendClientEvent("TimeUpdate", true, packetIn, ChainableMap.<String, Object>newMap()
                .putChain("totalWorldTime", packetIn.getTotalWorldTime())
                .putChain("worldTime", packetIn.getWorldTime()));
    }

    @Override
    public void handleSoundEffect(SPacketSoundEffect packetIn) {
        netHandlerOriginal.handleSoundEffect(packetIn);
    }

    @Override
    public void handleCustomSound(SPacketCustomSound packetIn) {
        netHandlerOriginal.handleCustomSound(packetIn);
    }

    @Override
    public void handleCollectItem(SPacketCollectItem packetIn) {
        netHandlerOriginal.handleCollectItem(packetIn);
    }

    @Override
    public void handleEntityTeleport(SPacketEntityTeleport packetIn) {
        netHandlerOriginal.handleEntityTeleport(packetIn);
    }

    @Override
    public void handleEntityProperties(SPacketEntityProperties packetIn) {
        netHandlerOriginal.handleEntityProperties(packetIn);
    }

    @Override
    public void handleEntityEffect(SPacketEntityEffect packetIn) {
        netHandlerOriginal.handleEntityEffect(packetIn);
    }

    @Override
    public void handleCombatEvent(SPacketCombatEvent packetIn) {
        netHandlerOriginal.handleCombatEvent(packetIn);
    }

    @Override
    public void handleServerDifficulty(SPacketServerDifficulty packetIn) {
        netHandlerOriginal.handleServerDifficulty(packetIn);
    }

    @Override
    public void handleCamera(SPacketCamera packetIn) {
        netHandlerOriginal.handleCamera(packetIn);
    }

    @Override
    public void handleWorldBorder(SPacketWorldBorder packetIn) {
        netHandlerOriginal.handleWorldBorder(packetIn);
    }

    @Override
    public void handleTitle(SPacketTitle packetIn) {
        netHandlerOriginal.handleTitle(packetIn);
    }

    @Override
    public void handlePlayerListHeaderFooter(SPacketPlayerListHeaderFooter packetIn) {
        netHandlerOriginal.handlePlayerListHeaderFooter(packetIn);
    }

    @Override
    public void handleResourcePack(SPacketResourcePackSend packetIn) {
        netHandlerOriginal.handleResourcePack(packetIn);
    }

    @Override
    public void handleUpdateBossInfo(SPacketUpdateBossInfo packetIn) {
        netHandlerOriginal.handleUpdateBossInfo(packetIn);
    }

    @Override
    public void handleCooldown(SPacketCooldown packetIn) {
        netHandlerOriginal.handleCooldown(packetIn);
    }

    @Override
    public void handleMoveVehicle(SPacketMoveVehicle packetIn) {
        netHandlerOriginal.handleMoveVehicle(packetIn);
    }

    @Override
    public void handleAdvancementInfo(SPacketAdvancementInfo packetIn) {
        netHandlerOriginal.handleAdvancementInfo(packetIn);
    }

    @Override
    public void handleSelectAdvancementsTab(SPacketSelectAdvancementsTab packetIn) {
        netHandlerOriginal.handleSelectAdvancementsTab(packetIn);
    }

    @Override
    public void func_194307_a(SPacketPlaceGhostRecipe p_194307_1_) {
        netHandlerOriginal.func_194307_a(p_194307_1_);
    }

    @Override
    public void onDisconnect(ITextComponent reason) {
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
}
