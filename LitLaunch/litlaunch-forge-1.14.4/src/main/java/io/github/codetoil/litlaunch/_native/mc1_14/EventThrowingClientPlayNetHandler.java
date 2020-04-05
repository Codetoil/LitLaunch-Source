package io.github.codetoil.litlaunch._native.mc1_14;

import io.github.codetoil.litlaunch.api.ChainableMap;
import io.github.codetoil.litlaunch.core.event.LitEvent;
import io.github.codetoil.litlaunch.core.event.LitEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.*;
import net.minecraft.util.text.ITextComponent;

import java.util.Map;

public class EventThrowingClientPlayNetHandler extends ClientPlayNetHandler {
    public ClientPlayNetHandler netHandlerOriginal;

    public EventThrowingClientPlayNetHandler(ClientPlayNetHandler netHandlerOriginal) {
        super(Minecraft.getInstance(), netHandlerOriginal.guiScreenServer, netHandlerOriginal.getNetworkManager(), netHandlerOriginal.getGameProfile());
        this.netHandlerOriginal = netHandlerOriginal;
    }

    @Override
    public void handleSpawnObject(SSpawnObjectPacket packetIn) {
        netHandlerOriginal.handleSpawnObject(packetIn);

    }

    @Override
    public void handleSpawnExperienceOrb(SSpawnExperienceOrbPacket packetIn) {
        netHandlerOriginal.handleSpawnExperienceOrb(packetIn);
    }

    @Override
    public void handleSpawnGlobalEntity(SSpawnGlobalEntityPacket packetIn) {
        netHandlerOriginal.handleSpawnGlobalEntity(packetIn);
    }

    @Override
    public void handleSpawnMob(SSpawnMobPacket packetIn) {
        netHandlerOriginal.handleSpawnMob(packetIn);
    }

    @Override
    public void handleScoreboardObjective(SScoreboardObjectivePacket packetIn) {
        netHandlerOriginal.handleScoreboardObjective(packetIn);
    }

    @Override
    public void handleSpawnPainting(SSpawnPaintingPacket packetIn) {
        netHandlerOriginal.handleSpawnPainting(packetIn);
    }

    @Override
    public void handleSpawnPlayer(SSpawnPlayerPacket packetIn) {
        netHandlerOriginal.handleSpawnPlayer(packetIn);
    }

    @Override
    public void handleAnimation(SAnimateHandPacket packetIn) {
        netHandlerOriginal.handleAnimation(packetIn);
    }

    @Override
    public void handleStatistics(SStatisticsPacket packetIn) {
        netHandlerOriginal.handleStatistics(packetIn);
    }

    @Override
    public void handleRecipeBook(SRecipeBookPacket packetIn) {
        netHandlerOriginal.handleRecipeBook(packetIn);
    }

    @Override
    public void handleBlockBreakAnim(SAnimateBlockBreakPacket packetIn) {
        netHandlerOriginal.handleBlockBreakAnim(packetIn);
    }

    @Override
    public void handleSignEditorOpen(SOpenSignMenuPacket packetIn) {
        netHandlerOriginal.handleSignEditorOpen(packetIn);
    }

    @Override
    public void handleUpdateTileEntity(SUpdateTileEntityPacket packetIn) {
        netHandlerOriginal.handleUpdateTileEntity(packetIn);
    }

    @Override
    public void handleBlockAction(SBlockActionPacket packetIn) {
        netHandlerOriginal.handleBlockAction(packetIn);
    }

    @Override
    public void handleBlockChange(SChangeBlockPacket packetIn) {
        netHandlerOriginal.handleBlockChange(packetIn);
    }

    @Override
    public void handleChat(SChatPacket packetIn) {
        netHandlerOriginal.handleChat(packetIn);
    }

    @Override
    public void handleMultiBlockChange(SMultiBlockChangePacket packetIn) {
        netHandlerOriginal.handleMultiBlockChange(packetIn);
    }

    @Override
    public void handleMaps(SMapDataPacket packetIn) {
        netHandlerOriginal.handleMaps(packetIn);
    }

    @Override
    public void handleConfirmTransaction(SConfirmTransactionPacket packetIn) {
        netHandlerOriginal.handleConfirmTransaction(packetIn);
    }

    @Override
    public void handleCloseWindow(SCloseWindowPacket packetIn) {
        netHandlerOriginal.handleCloseWindow(packetIn);
    }

    @Override
    public void handleWindowItems(SWindowItemsPacket packetIn) {
        netHandlerOriginal.handleWindowItems(packetIn);
    }

    @Override
    public void handleOpenHorseWindow(SOpenHorseWindowPacket sOpenHorseWindowPacket) {
        netHandlerOriginal.handleOpenHorseWindow(sOpenHorseWindowPacket);
    }

    @Override
    public void handleWindowProperty(SWindowPropertyPacket packetIn) {
        netHandlerOriginal.handleWindowProperty(packetIn);
    }

    @Override
    public void handleSetSlot(SSetSlotPacket packetIn) {
        netHandlerOriginal.handleSetSlot(packetIn);
    }

    @Override
    public void handleCustomPayload(SCustomPayloadPlayPacket packetIn) {
        netHandlerOriginal.handleCustomPayload(packetIn);
    }

    @Override
    public void handleDisconnect(SDisconnectPacket packetIn) {
        netHandlerOriginal.handleDisconnect(packetIn);
    }

    @Override
    public void handleEntityStatus(SEntityStatusPacket packetIn) {
        netHandlerOriginal.handleEntityStatus(packetIn);
    }

    @Override
    public void handleEntityAttach(SMountEntityPacket packetIn) {
        netHandlerOriginal.handleEntityAttach(packetIn);
    }

    @Override
    public void handleSetPassengers(SSetPassengersPacket packetIn) {
        netHandlerOriginal.handleSetPassengers(packetIn);
    }

    @Override
    public void handleExplosion(SExplosionPacket packetIn) {
        netHandlerOriginal.handleExplosion(packetIn);
    }

    @Override
    public void handleChangeGameState(SChangeGameStatePacket packetIn) {
        netHandlerOriginal.handleChangeGameState(packetIn);
    }

    @Override
    public void handleKeepAlive(SKeepAlivePacket packetIn) {
        netHandlerOriginal.handleKeepAlive(packetIn);
    }

    @Override
    public void handleChunkData(SChunkDataPacket packetIn) {
        netHandlerOriginal.handleChunkData(packetIn);
    }

    @Override
    public void processChunkUnload(SUnloadChunkPacket packetIn) {
        netHandlerOriginal.processChunkUnload(packetIn);
    }

    @Override
    public void handleEffect(SPlaySoundEventPacket packetIn) {
        netHandlerOriginal.handleEffect(packetIn);
    }

    @Override
    public void handleJoinGame(SJoinGamePacket packetIn) {
        netHandlerOriginal.handleJoinGame(packetIn);
    }

    @Override
    public void handleEntityMovement(SEntityPacket packetIn) {
        netHandlerOriginal.handleEntityMovement(packetIn);
    }

    @Override
    public void handlePlayerPosLook(SPlayerPositionLookPacket packetIn) {
        netHandlerOriginal.handlePlayerPosLook(packetIn);
    }

    @Override
    public void handleParticles(SSpawnParticlePacket packetIn) {
        netHandlerOriginal.handleParticles(packetIn);
    }

    @Override
    public void handlePlayerAbilities(SPlayerAbilitiesPacket packetIn) {
        netHandlerOriginal.handlePlayerAbilities(packetIn);
    }

    @Override
    public void handlePlayerListItem(SPlayerListItemPacket packetIn) {
        netHandlerOriginal.handlePlayerListItem(packetIn);
    }

    @Override
    public void handleDestroyEntities(SDestroyEntitiesPacket packetIn) {
        netHandlerOriginal.handleDestroyEntities(packetIn);
    }

    @Override
    public void handleRemoveEntityEffect(SRemoveEntityEffectPacket packetIn) {
        netHandlerOriginal.handleRemoveEntityEffect(packetIn);
    }

    @Override
    public void handleRespawn(SRespawnPacket packetIn) {
        netHandlerOriginal.handleRespawn(packetIn);
    }

    @Override
    public void handleEntityHeadLook(SEntityHeadLookPacket packetIn) {
        netHandlerOriginal.handleEntityHeadLook(packetIn);
    }

    @Override
    public void handleHeldItemChange(SHeldItemChangePacket packetIn) {
        netHandlerOriginal.handleHeldItemChange(packetIn);
    }

    @Override
    public void handleDisplayObjective(SDisplayObjectivePacket packetIn) {
        netHandlerOriginal.handleDisplayObjective(packetIn);
    }

    @Override
    public void handleEntityMetadata(SEntityMetadataPacket packetIn) {
        netHandlerOriginal.handleEntityMetadata(packetIn);
    }

    @Override
    public void handleEntityVelocity(SEntityVelocityPacket packetIn) {
        netHandlerOriginal.handleEntityVelocity(packetIn);
    }

    @Override
    public void handleEntityEquipment(SEntityEquipmentPacket packetIn) {
        netHandlerOriginal.handleEntityEquipment(packetIn);
    }

    @Override
    public void handleSetExperience(SSetExperiencePacket packetIn) {
        netHandlerOriginal.handleSetExperience(packetIn);
    }

    @Override
    public void handleUpdateHealth(SUpdateHealthPacket packetIn) {
        netHandlerOriginal.handleUpdateHealth(packetIn);
    }

    @Override
    public void handleTeams(STeamsPacket packetIn) {
        netHandlerOriginal.handleTeams(packetIn);
    }

    @Override
    public void handleUpdateScore(SUpdateScorePacket packetIn) {
        netHandlerOriginal.handleUpdateScore(packetIn);
    }

    @Override
    public void handleSpawnPosition(SSpawnPositionPacket packetIn) {
        netHandlerOriginal.handleSpawnPosition(packetIn);
    }

    @Override
    public void handleTimeUpdate(SUpdateTimePacket packetIn) {
        netHandlerOriginal.handleTimeUpdate(packetIn);
        sendClientEvent("TimeUpdate", true, packetIn, ChainableMap.<String, Object>newMap()
                .putChain("totalWorldTime", packetIn.getTotalWorldTime())
                .putChain("worldTime", packetIn.getWorldTime()));
    }

    @Override
    public void handleSoundEffect(SPlaySoundEffectPacket packetIn) {
        netHandlerOriginal.handleSoundEffect(packetIn);
    }

    @Override
    public void func_217266_a(SSpawnMovingSoundEffectPacket p_217266_1_) {
        netHandlerOriginal.func_217266_a(p_217266_1_);
    }

    @Override
    public void handleCustomSound(SPlaySoundPacket packetIn) {
        netHandlerOriginal.handleCustomSound(packetIn);
    }

    @Override
    public void handleCollectItem(SCollectItemPacket packetIn) {
        netHandlerOriginal.handleCollectItem(packetIn);
    }

    @Override
    public void handleEntityTeleport(SEntityTeleportPacket packetIn) {
        netHandlerOriginal.handleEntityTeleport(packetIn);
    }

    @Override
    public void handleEntityProperties(SEntityPropertiesPacket packetIn) {
        netHandlerOriginal.handleEntityProperties(packetIn);
    }

    @Override
    public void handleEntityEffect(SPlayEntityEffectPacket packetIn) {
        netHandlerOriginal.handleEntityEffect(packetIn);
    }

    @Override
    public void handleTags(STagsListPacket packetIn) {
        netHandlerOriginal.handleTags(packetIn);
    }

    @Override
    public void handleCombatEvent(SCombatPacket packetIn) {
        netHandlerOriginal.handleCombatEvent(packetIn);
    }

    @Override
    public void handleServerDifficulty(SServerDifficultyPacket packetIn) {
        netHandlerOriginal.handleServerDifficulty(packetIn);
    }

    @Override
    public void handleCamera(SCameraPacket packetIn) {
        netHandlerOriginal.handleCamera(packetIn);
    }

    @Override
    public void handleWorldBorder(SWorldBorderPacket packetIn) {
        netHandlerOriginal.handleWorldBorder(packetIn);
    }

    @Override
    public void handleTitle(STitlePacket packetIn) {
        netHandlerOriginal.handleTitle(packetIn);
    }

    @Override
    public void handlePlayerListHeaderFooter(SPlayerListHeaderFooterPacket packetIn) {
        netHandlerOriginal.handlePlayerListHeaderFooter(packetIn);
    }

    @Override
    public void handleResourcePack(SSendResourcePackPacket packetIn) {
        netHandlerOriginal.handleResourcePack(packetIn);
    }

    @Override
    public void handleUpdateBossInfo(SUpdateBossInfoPacket packetIn) {
        netHandlerOriginal.handleUpdateBossInfo(packetIn);
    }

    @Override
    public void handleCooldown(SCooldownPacket packetIn) {
        netHandlerOriginal.handleCooldown(packetIn);
    }

    @Override
    public void handleMoveVehicle(SMoveVehiclePacket packetIn) {
        netHandlerOriginal.handleMoveVehicle(packetIn);
    }

    @Override
    public void handleAdvancementInfo(SAdvancementInfoPacket packetIn) {
        netHandlerOriginal.handleAdvancementInfo(packetIn);
    }

    @Override
    public void handleSelectAdvancementsTab(SSelectAdvancementsTabPacket packetIn) {
        netHandlerOriginal.handleSelectAdvancementsTab(packetIn);
    }

    @Override
    public void handlePlaceGhostRecipe(SPlaceGhostRecipePacket packetIn) {
        netHandlerOriginal.handlePlaceGhostRecipe(packetIn);
    }

    @Override
    public void handleCommandList(SCommandListPacket packetIn) {
        netHandlerOriginal.handleCommandList(packetIn);
    }

    @Override
    public void handleStopSound(SStopSoundPacket packetIn) {
        netHandlerOriginal.handleStopSound(packetIn);
    }

    @Override
    public void handleTabComplete(STabCompletePacket packetIn) {
        netHandlerOriginal.handleTabComplete(packetIn);
    }

    @Override
    public void handleUpdateRecipes(SUpdateRecipesPacket packetIn) {
        netHandlerOriginal.handleUpdateRecipes(packetIn);
    }

    @Override
    public void handlePlayerLook(SPlayerLookPacket packetIn) {
        netHandlerOriginal.handlePlayerLook(packetIn);
    }

    @Override
    public void handleNBTQueryResponse(SQueryNBTResponsePacket packetIn) {
        netHandlerOriginal.handleNBTQueryResponse(packetIn);
    }

    @Override
    public void handleUpdateLight(SUpdateLightPacket packetIn) {
        netHandlerOriginal.handleUpdateLight(packetIn);
    }

    @Override
    public void handleOpenBookPacket(SOpenBookWindowPacket sOpenBookWindowPacket) {
        netHandlerOriginal.handleOpenBookPacket(sOpenBookWindowPacket);
    }

    @Override
    public void handleOpenWindowPacket(SOpenWindowPacket sOpenWindowPacket) {
        netHandlerOriginal.handleOpenWindowPacket(sOpenWindowPacket);
    }

    @Override
    public void handleMerchantOffers(SMerchantOffersPacket sMerchantOffersPacket) {
        netHandlerOriginal.handleMerchantOffers(sMerchantOffersPacket);
    }

    @Override
    public void handleUpdateViewDistancePacket(SUpdateViewDistancePacket sUpdateViewDistancePacket) {
        netHandlerOriginal.handleUpdateViewDistancePacket(sUpdateViewDistancePacket);
    }

    @Override
    public void handleChunkPositionPacket(SUpdateChunkPositionPacket sUpdateChunkPositionPacket) {
        netHandlerOriginal.handleChunkPositionPacket(sUpdateChunkPositionPacket);
    }

    @Override
    public void func_225312_a(SPlayerDiggingPacket p_225312_1_) {
        netHandlerOriginal.func_225312_a(p_225312_1_);

    }

    @Override
    public void onDisconnect(ITextComponent reason) {
        netHandlerOriginal.onDisconnect(reason);
    }

    @Override
    public NetworkManager getNetworkManager() {
        return netHandlerOriginal.getNetworkManager();
    }

    private void sendClientEvent(String name, boolean isSpammy, IPacket<?> packet, Map<String, Object> extraInfo) {
        ChainableMap<String, Object> map = ChainableMap.<String, Object>newMap()
                .putChain("Type", name)
                .putChain("Raw", packet);
        map.putAll(extraInfo);
        LitEventHandler.CLIENT.post(
                new LitEvent(this, LitEvent.TYPE.ONPACKET, map), isSpammy);
    }
}
