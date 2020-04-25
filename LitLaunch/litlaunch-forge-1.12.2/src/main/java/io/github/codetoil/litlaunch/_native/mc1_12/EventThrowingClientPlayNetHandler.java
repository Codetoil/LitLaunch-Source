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
        sendClientEvent("SpawnObject", packetIn);
    }

    @Override
    public void handleSpawnExperienceOrb(SPacketSpawnExperienceOrb packetIn) {
        netHandlerOriginal.handleSpawnExperienceOrb(packetIn);
        sendClientEvent("SpawnExpienceOrb", packetIn);
    }

    @Override
    public void handleSpawnGlobalEntity(SPacketSpawnGlobalEntity packetIn) {
        netHandlerOriginal.handleSpawnGlobalEntity(packetIn);
        sendClientEvent("SpawnGlobalEntity", packetIn);
    }

    @Override
    public void handleSpawnMob(SPacketSpawnMob packetIn) {
        netHandlerOriginal.handleSpawnMob(packetIn);
        sendClientEvent("SpawnMob", packetIn);
    }

    @Override
    public void handleScoreboardObjective(SPacketScoreboardObjective packetIn) {
        netHandlerOriginal.handleScoreboardObjective(packetIn);
        sendClientEvent("ScoreboardObjective", packetIn);
    }

    @Override
    public void handleSpawnPainting(SPacketSpawnPainting packetIn) {
        netHandlerOriginal.handleSpawnPainting(packetIn);
        sendClientEvent("SpawnPainting", packetIn);
    }

    @Override
    public void handleSpawnPlayer(SPacketSpawnPlayer packetIn) {
        netHandlerOriginal.handleSpawnPlayer(packetIn);
        sendClientEvent("SpawnPlayer", packetIn);
    }

    @Override
    public void handleAnimation(SPacketAnimation packetIn) {
        netHandlerOriginal.handleAnimation(packetIn);
        sendClientEvent("Animation", packetIn);
    }

    @Override
    public void handleStatistics(SPacketStatistics packetIn) {
        netHandlerOriginal.handleStatistics(packetIn);
        sendClientEvent("Statistics", packetIn);
    }

    @Override
    public void handleRecipeBook(SPacketRecipeBook packetIn) {
        netHandlerOriginal.handleRecipeBook(packetIn);
        sendClientEvent("RecipeBook", packetIn);
    }

    @Override
    public void handleBlockBreakAnim(SPacketBlockBreakAnim packetIn) {
        netHandlerOriginal.handleBlockBreakAnim(packetIn);
        sendClientEvent("BlockBreakAnim", packetIn);
    }

    @Override
    public void handleSignEditorOpen(SPacketSignEditorOpen packetIn) {
        netHandlerOriginal.handleSignEditorOpen(packetIn);
        sendClientEvent("SignEditorOpen", packetIn);
    }

    @Override
    public void handleUpdateTileEntity(SPacketUpdateTileEntity packetIn) {
        netHandlerOriginal.handleUpdateTileEntity(packetIn);
        sendClientEvent("UpdateTileEntity", packetIn);
    }

    @Override
    public void handleBlockAction(SPacketBlockAction packetIn) {
        netHandlerOriginal.handleBlockAction(packetIn);
        sendClientEvent("BlockAction", packetIn);
    }

    @Override
    public void handleBlockChange(SPacketBlockChange packetIn) {
        netHandlerOriginal.handleBlockChange(packetIn);
        sendClientEvent("BlockChange", packetIn);
    }

    @Override
    public void handleChat(SPacketChat packetIn) {
        netHandlerOriginal.handleChat(packetIn);
        sendClientEvent("Chat", false, packetIn, ChainableMap.<String, Object>newMap()
                .putChain("ChatComponent", packetIn.getChatComponent().getUnformattedComponentText())
                .putChain("ChatType", packetIn.getType().getId()));
    }

    @Override
    public void handleTabComplete(SPacketTabComplete packetIn) {
        netHandlerOriginal.handleTabComplete(packetIn);
        sendClientEvent("TabComplete", packetIn);
    }

    @Override
    public void handleMultiBlockChange(SPacketMultiBlockChange packetIn) {
        netHandlerOriginal.handleMultiBlockChange(packetIn);
        sendClientEvent("MultiBlockChange", packetIn);
    }

    @Override
    public void handleMaps(SPacketMaps packetIn) {
        netHandlerOriginal.handleMaps(packetIn);
        sendClientEvent("Maps", packetIn);
    }

    @Override
    public void handleConfirmTransaction(SPacketConfirmTransaction packetIn) {
        netHandlerOriginal.handleConfirmTransaction(packetIn);
        sendClientEvent("ConfirmTransaction", packetIn);
    }

    @Override
    public void handleCloseWindow(SPacketCloseWindow packetIn) {
        netHandlerOriginal.handleCloseWindow(packetIn);
        sendClientEvent("CloseWindow", packetIn);
    }

    @Override
    public void handleWindowItems(SPacketWindowItems packetIn) {
        netHandlerOriginal.handleWindowItems(packetIn);
        sendClientEvent("WindowItems", packetIn);
    }

    @Override
    public void handleOpenWindow(SPacketOpenWindow packetIn) {
        netHandlerOriginal.handleOpenWindow(packetIn);
        sendClientEvent("OpenHorseWindow", packetIn);
    }

    @Override
    public void handleWindowProperty(SPacketWindowProperty packetIn) {
        netHandlerOriginal.handleWindowProperty(packetIn);
        sendClientEvent("WindowProperty", packetIn);
    }

    @Override
    public void handleSetSlot(SPacketSetSlot packetIn) {
        netHandlerOriginal.handleSetSlot(packetIn);
        sendClientEvent("SetSlot", packetIn);
    }

    @Override
    public void handleCustomPayload(SPacketCustomPayload packetIn) {
        netHandlerOriginal.handleCustomPayload(packetIn);
        sendClientEvent("CustomPayload", packetIn);
    }

    @Override
    public void handleDisconnect(SPacketDisconnect packetIn) {
        netHandlerOriginal.handleDisconnect(packetIn);
        sendClientEvent("Disconnect", packetIn);
    }

    @Override
    public void handleUseBed(SPacketUseBed packetIn) {
        netHandlerOriginal.handleUseBed(packetIn);
        sendClientEvent("BlockAction", packetIn);
    }

    @Override
    public void handleEntityStatus(SPacketEntityStatus packetIn) {
        netHandlerOriginal.handleEntityStatus(packetIn);
        sendClientEvent("EntityStatus", packetIn);
    }

    @Override
    public void handleEntityAttach(SPacketEntityAttach packetIn) {
        netHandlerOriginal.handleEntityAttach(packetIn);
        sendClientEvent("EntityAttach", packetIn);
    }

    @Override
    public void handleSetPassengers(SPacketSetPassengers packetIn) {
        netHandlerOriginal.handleSetPassengers(packetIn);
        sendClientEvent("SetPassengers", packetIn);
    }

    @Override
    public void handleExplosion(SPacketExplosion packetIn) {
        netHandlerOriginal.handleExplosion(packetIn);
        sendClientEvent("Explosion", packetIn);
    }

    @Override
    public void handleChangeGameState(SPacketChangeGameState packetIn) {
        netHandlerOriginal.handleChangeGameState(packetIn);
        sendClientEvent("ChangeGameState", packetIn);
    }

    @Override
    public void handleKeepAlive(SPacketKeepAlive packetIn) {
        netHandlerOriginal.handleKeepAlive(packetIn);
        sendClientEvent("KeepAlive", packetIn);
    }

    @Override
    public void handleChunkData(SPacketChunkData packetIn) {
        netHandlerOriginal.handleChunkData(packetIn);
        sendClientEvent("ChunkData", packetIn);
    }

    @Override
    public void processChunkUnload(SPacketUnloadChunk packetIn) {
        netHandlerOriginal.processChunkUnload(packetIn);
        sendClientEvent("ChunkUnload", packetIn);
    }

    @Override
    public void handleEffect(SPacketEffect packetIn) {
        netHandlerOriginal.handleEffect(packetIn);
        sendClientEvent("Effect", packetIn);
    }

    @Override
    public void handleJoinGame(SPacketJoinGame packetIn) {
        netHandlerOriginal.handleJoinGame(packetIn);
        sendClientEvent("JoinGame", packetIn);
    }

    @Override
    public void handleEntityMovement(SPacketEntity packetIn) {
        netHandlerOriginal.handleEntityMovement(packetIn);
        sendClientEvent("EntityMovement", packetIn);
    }

    @Override
    public void handlePlayerPosLook(SPacketPlayerPosLook packetIn) {
        netHandlerOriginal.handlePlayerPosLook(packetIn);
        sendClientEvent("PlayerPosLook", packetIn);
    }

    @Override
    public void handleParticles(SPacketParticles packetIn) {
        netHandlerOriginal.handleParticles(packetIn);
        sendClientEvent("Particles", packetIn);
    }

    @Override
    public void handlePlayerAbilities(SPacketPlayerAbilities packetIn) {
        netHandlerOriginal.handlePlayerAbilities(packetIn);
        sendClientEvent("PlayerAbilities", packetIn);
    }

    @Override
    public void handlePlayerListItem(SPacketPlayerListItem packetIn) {
        netHandlerOriginal.handlePlayerListItem(packetIn);
        sendClientEvent("PlayerListItem", packetIn);
    }

    @Override
    public void handleDestroyEntities(SPacketDestroyEntities packetIn) {
        netHandlerOriginal.handleDestroyEntities(packetIn);
        sendClientEvent("DestroyEntities", packetIn);
    }

    @Override
    public void handleRemoveEntityEffect(SPacketRemoveEntityEffect packetIn) {
        netHandlerOriginal.handleRemoveEntityEffect(packetIn);
        sendClientEvent("RemoveEntityEffect", packetIn);
    }

    @Override
    public void handleRespawn(SPacketRespawn packetIn) {
        netHandlerOriginal.handleRespawn(packetIn);
        sendClientEvent("Respawn", packetIn);
    }

    @Override
    public void handleEntityHeadLook(SPacketEntityHeadLook packetIn) {
        netHandlerOriginal.handleEntityHeadLook(packetIn);
        sendClientEvent("EntityHeadLook", packetIn);
    }

    @Override
    public void handleHeldItemChange(SPacketHeldItemChange packetIn) {
        netHandlerOriginal.handleHeldItemChange(packetIn);
        sendClientEvent("HeldItemChange", packetIn);
    }

    @Override
    public void handleDisplayObjective(SPacketDisplayObjective packetIn) {
        netHandlerOriginal.handleDisplayObjective(packetIn);
        sendClientEvent("DisplayObjective", packetIn);
    }

    @Override
    public void handleEntityMetadata(SPacketEntityMetadata packetIn) {
        netHandlerOriginal.handleEntityMetadata(packetIn);
        sendClientEvent("EntityMetadata", packetIn);
    }

    @Override
    public void handleEntityVelocity(SPacketEntityVelocity packetIn) {
        netHandlerOriginal.handleEntityVelocity(packetIn);
        sendClientEvent("EntityVelocity", packetIn);
    }

    @Override
    public void handleEntityEquipment(SPacketEntityEquipment packetIn) {
        netHandlerOriginal.handleEntityEquipment(packetIn);
        sendClientEvent("EntityEquipment", packetIn);
    }

    @Override
    public void handleSetExperience(SPacketSetExperience packetIn) {
        netHandlerOriginal.handleSetExperience(packetIn);
        sendClientEvent("SetExperience", packetIn);
    }

    @Override
    public void handleUpdateHealth(SPacketUpdateHealth packetIn) {
        netHandlerOriginal.handleUpdateHealth(packetIn);
        sendClientEvent("UpdateHealth", packetIn);
    }

    @Override
    public void handleTeams(SPacketTeams packetIn) {
        netHandlerOriginal.handleTeams(packetIn);
        sendClientEvent("Teams", packetIn);
    }

    @Override
    public void handleUpdateScore(SPacketUpdateScore packetIn) {
        netHandlerOriginal.handleUpdateScore(packetIn);
        sendClientEvent("UpdateScore", packetIn);
    }

    @Override
    public void handleSpawnPosition(SPacketSpawnPosition packetIn) {
        netHandlerOriginal.handleSpawnPosition(packetIn);
        sendClientEvent("SpawnPosition", packetIn);
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
        sendClientEvent("SoundEffect", packetIn);
    }

    @Override
    public void handleCustomSound(SPacketCustomSound packetIn) {
        netHandlerOriginal.handleCustomSound(packetIn);
        sendClientEvent("CustomSound", packetIn);
    }

    @Override
    public void handleCollectItem(SPacketCollectItem packetIn) {
        netHandlerOriginal.handleCollectItem(packetIn);
        sendClientEvent("CollectItem", packetIn);
    }

    @Override
    public void handleEntityTeleport(SPacketEntityTeleport packetIn) {
        netHandlerOriginal.handleEntityTeleport(packetIn);
        sendClientEvent("EntityTeleport", packetIn);
    }

    @Override
    public void handleEntityProperties(SPacketEntityProperties packetIn) {
        netHandlerOriginal.handleEntityProperties(packetIn);
        sendClientEvent("EntityProperties", packetIn);
    }

    @Override
    public void handleEntityEffect(SPacketEntityEffect packetIn) {
        netHandlerOriginal.handleEntityEffect(packetIn);
        sendClientEvent("EntityEffect", packetIn);
    }

    @Override
    public void handleCombatEvent(SPacketCombatEvent packetIn) {
        netHandlerOriginal.handleCombatEvent(packetIn);
        sendClientEvent("CombatEvent", packetIn);
    }

    @Override
    public void handleServerDifficulty(SPacketServerDifficulty packetIn) {
        netHandlerOriginal.handleServerDifficulty(packetIn);
        sendClientEvent("ServerDifficulty", packetIn);
    }

    @Override
    public void handleCamera(SPacketCamera packetIn) {
        netHandlerOriginal.handleCamera(packetIn);
        sendClientEvent("Camera", packetIn);
    }

    @Override
    public void handleWorldBorder(SPacketWorldBorder packetIn) {
        netHandlerOriginal.handleWorldBorder(packetIn);
        sendClientEvent("WorldBorder", packetIn);
    }

    @Override
    public void handleTitle(SPacketTitle packetIn) {
        netHandlerOriginal.handleTitle(packetIn);
        sendClientEvent("Title", packetIn);
    }

    @Override
    public void handlePlayerListHeaderFooter(SPacketPlayerListHeaderFooter packetIn) {
        netHandlerOriginal.handlePlayerListHeaderFooter(packetIn);
        sendClientEvent("PlayerListHeaderFooter", packetIn);
    }

    @Override
    public void handleResourcePack(SPacketResourcePackSend packetIn) {
        netHandlerOriginal.handleResourcePack(packetIn);
        sendClientEvent("ResourcePack", packetIn);
    }

    @Override
    public void handleUpdateBossInfo(SPacketUpdateBossInfo packetIn) {
        netHandlerOriginal.handleUpdateBossInfo(packetIn);
        sendClientEvent("UpdateBossInfo", packetIn);
    }

    @Override
    public void handleCooldown(SPacketCooldown packetIn) {
        netHandlerOriginal.handleCooldown(packetIn);
        sendClientEvent("Cooldown", packetIn);
    }

    @Override
    public void handleMoveVehicle(SPacketMoveVehicle packetIn) {
        netHandlerOriginal.handleMoveVehicle(packetIn);
        sendClientEvent("MoveVehicle", packetIn);
    }

    @Override
    public void handleAdvancementInfo(SPacketAdvancementInfo packetIn) {
        netHandlerOriginal.handleAdvancementInfo(packetIn);
        sendClientEvent("AdvancementInfo", packetIn);
    }

    @Override
    public void handleSelectAdvancementsTab(SPacketSelectAdvancementsTab packetIn) {
        netHandlerOriginal.handleSelectAdvancementsTab(packetIn);
        sendClientEvent("SelectAdvancementsTab", packetIn);
    }

    @Override
    public void func_194307_a(SPacketPlaceGhostRecipe packetIn) {
        netHandlerOriginal.func_194307_a(packetIn);
        sendClientEvent("PlaceGhostRecipe", packetIn);
    }

    @Override
    public void onDisconnect(ITextComponent reason) {
        netHandlerOriginal.onDisconnect(reason);
        LitEventHandler.CLIENT.post(new LitEvent(this, LitEvent.TYPE.DISCONNECT, reason.getUnformattedComponentText()));
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
