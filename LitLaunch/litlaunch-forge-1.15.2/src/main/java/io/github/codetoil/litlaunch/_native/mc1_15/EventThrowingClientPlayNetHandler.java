package io.github.codetoil.litlaunch._native.mc1_15;

import io.github.codetoil.litlaunch.api.ChainableMap;
import io.github.codetoil.litlaunch.core.event.LitEvent;
import io.github.codetoil.litlaunch.core.event.LitEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.*;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.system.NonnullDefault;

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
        sendClientEvent("SpawnObject", packetIn);
    }

    @Override
    public void handleSpawnExperienceOrb(SSpawnExperienceOrbPacket packetIn) {
        netHandlerOriginal.handleSpawnExperienceOrb(packetIn);
        sendClientEvent("SpawnExpienceOrb", packetIn);
    }

    @Override
    public void handleSpawnGlobalEntity(SSpawnGlobalEntityPacket packetIn) {
        netHandlerOriginal.handleSpawnGlobalEntity(packetIn);
        sendClientEvent("SpawnGlobalEntity", packetIn);
    }

    @Override
    public void handleSpawnMob(SSpawnMobPacket packetIn) {
        netHandlerOriginal.handleSpawnMob(packetIn);
        sendClientEvent("SpawnMob", packetIn);
    }

    @Override
    public void handleScoreboardObjective(SScoreboardObjectivePacket packetIn) {
        netHandlerOriginal.handleScoreboardObjective(packetIn);
        sendClientEvent("ScoreboardObjective", packetIn);
    }

    @Override
    public void handleSpawnPainting(SSpawnPaintingPacket packetIn) {
        netHandlerOriginal.handleSpawnPainting(packetIn);
        sendClientEvent("SpawnPainting", packetIn);
    }

    @Override
    public void handleSpawnPlayer(SSpawnPlayerPacket packetIn) {
        netHandlerOriginal.handleSpawnPlayer(packetIn);
        sendClientEvent("SpawnPlayer", packetIn);
    }

    @Override
    public void handleAnimation(SAnimateHandPacket packetIn) {
        netHandlerOriginal.handleAnimation(packetIn);
        sendClientEvent("Animation", packetIn);
    }

    @Override
    public void handleStatistics(SStatisticsPacket packetIn) {
        netHandlerOriginal.handleStatistics(packetIn);
        sendClientEvent("Statistics", packetIn);
    }

    @Override
    public void handleRecipeBook(SRecipeBookPacket packetIn) {
        netHandlerOriginal.handleRecipeBook(packetIn);
        sendClientEvent("RecipeBook", packetIn);
    }

    @Override
    public void handleBlockBreakAnim(SAnimateBlockBreakPacket packetIn) {
        netHandlerOriginal.handleBlockBreakAnim(packetIn);
        sendClientEvent("BlockBreakAnim", packetIn);
    }

    @Override
    public void handleSignEditorOpen(SOpenSignMenuPacket packetIn) {
        netHandlerOriginal.handleSignEditorOpen(packetIn);
        sendClientEvent("SignEditorOpen", packetIn);
    }

    @Override
    public void handleUpdateTileEntity(SUpdateTileEntityPacket packetIn) {
        netHandlerOriginal.handleUpdateTileEntity(packetIn);
        sendClientEvent("UpdateTileEntity", packetIn);
    }

    @Override
    public void handleBlockAction(SBlockActionPacket packetIn) {
        netHandlerOriginal.handleBlockAction(packetIn);
        sendClientEvent("BlockAction", packetIn);
    }

    @Override
    public void handleBlockChange(SChangeBlockPacket packetIn) {
        netHandlerOriginal.handleBlockChange(packetIn);
        sendClientEvent("BlockChange", packetIn);
    }

    @Override
    public void handleChat(SChatPacket packetIn) {
        netHandlerOriginal.handleChat(packetIn);
        sendClientEvent("Chat", false, packetIn, ChainableMap.<String, Object>newMap()
                .putChain("ChatComponent", packetIn.getChatComponent().getUnformattedComponentText())
                .putChain("ChatType", packetIn.getType().getId()));
    }

    @Override
    public void handleMultiBlockChange(SMultiBlockChangePacket packetIn) {
        netHandlerOriginal.handleMultiBlockChange(packetIn);
        sendClientEvent("MultiBlockChange", packetIn);
    }

    @Override
    public void handleMaps(SMapDataPacket packetIn) {
        netHandlerOriginal.handleMaps(packetIn);
        sendClientEvent("Maps", packetIn);
    }

    @Override
    public void handleConfirmTransaction(SConfirmTransactionPacket packetIn) {
        netHandlerOriginal.handleConfirmTransaction(packetIn);
        sendClientEvent("ConfirmTransaction", packetIn);
    }

    @Override
    public void handleCloseWindow(SCloseWindowPacket packetIn) {
        netHandlerOriginal.handleCloseWindow(packetIn);
        sendClientEvent("CloseWindow", packetIn);
    }

    @Override
    public void handleWindowItems(SWindowItemsPacket packetIn) {
        netHandlerOriginal.handleWindowItems(packetIn);
        sendClientEvent("WindowItems", packetIn);
    }

    @Override
    public void handleOpenHorseWindow(SOpenHorseWindowPacket packetIn) {
        netHandlerOriginal.handleOpenHorseWindow(packetIn);
        sendClientEvent("OpenHorseWindow", packetIn);
    }

    @Override
    public void handleWindowProperty(SWindowPropertyPacket packetIn) {
        netHandlerOriginal.handleWindowProperty(packetIn);
		sendClientEvent("WindowProperty", packetIn);
    }

    @Override
    public void handleSetSlot(SSetSlotPacket packetIn) {
        netHandlerOriginal.handleSetSlot(packetIn);
		sendClientEvent("SetSlot", packetIn);
    }

    @Override
    public void handleCustomPayload(SCustomPayloadPlayPacket packetIn) {
        netHandlerOriginal.handleCustomPayload(packetIn);
		sendClientEvent("CustomPayload", packetIn);
    }

    @Override
    public void handleDisconnect(SDisconnectPacket packetIn) {
        netHandlerOriginal.handleDisconnect(packetIn);
		sendClientEvent("Disconnect", packetIn);
    }

    @Override
    public void handleEntityStatus(SEntityStatusPacket packetIn) {
        netHandlerOriginal.handleEntityStatus(packetIn);
		sendClientEvent("EntityStatus", packetIn);
    }

    @Override
    public void handleEntityAttach(SMountEntityPacket packetIn) {
        netHandlerOriginal.handleEntityAttach(packetIn);
		sendClientEvent("EntityAttach", packetIn);
    }

    @Override
    public void handleSetPassengers(SSetPassengersPacket packetIn) {
        netHandlerOriginal.handleSetPassengers(packetIn);
		sendClientEvent("SetPassengers", packetIn);
    }

    @Override
    public void handleExplosion(SExplosionPacket packetIn) {
        netHandlerOriginal.handleExplosion(packetIn);
		sendClientEvent("Explosion", packetIn);
    }

    @Override
    public void handleChangeGameState(SChangeGameStatePacket packetIn) {
        netHandlerOriginal.handleChangeGameState(packetIn);
		sendClientEvent("ChangeGameState", packetIn);
    }

    @Override
    public void handleKeepAlive(SKeepAlivePacket packetIn) {
        netHandlerOriginal.handleKeepAlive(packetIn);
		sendClientEvent("KeepAlive", packetIn);
    }

    @Override
    public void handleChunkData(SChunkDataPacket packetIn) {
        netHandlerOriginal.handleChunkData(packetIn);
		sendClientEvent("ChunkData", packetIn);
    }

    @Override
    public void processChunkUnload(SUnloadChunkPacket packetIn) {
        netHandlerOriginal.processChunkUnload(packetIn);
        sendClientEvent("ChunkUnload", packetIn);
    }

    @Override
    public void handleEffect(SPlaySoundEventPacket packetIn) {
        netHandlerOriginal.handleEffect(packetIn);
		sendClientEvent("Effect", packetIn);
    }

    @Override
    public void handleJoinGame(SJoinGamePacket packetIn) {
        netHandlerOriginal.handleJoinGame(packetIn);
		sendClientEvent("JoinGame", packetIn);
    }

    @Override
    public void handleEntityMovement(SEntityPacket packetIn) {
        netHandlerOriginal.handleEntityMovement(packetIn);
		sendClientEvent("EntityMovement", packetIn);
    }

    @Override
    public void handlePlayerPosLook(SPlayerPositionLookPacket packetIn) {
        netHandlerOriginal.handlePlayerPosLook(packetIn);
		sendClientEvent("PlayerPosLook", packetIn);
    }

    @Override
    public void handleParticles(SSpawnParticlePacket packetIn) {
        netHandlerOriginal.handleParticles(packetIn);
		sendClientEvent("Particles", packetIn);
    }

    @Override
    public void handlePlayerAbilities(SPlayerAbilitiesPacket packetIn) {
        netHandlerOriginal.handlePlayerAbilities(packetIn);
		sendClientEvent("PlayerAbilities", packetIn);
    }

    @Override
    public void handlePlayerListItem(SPlayerListItemPacket packetIn) {
        netHandlerOriginal.handlePlayerListItem(packetIn);
		sendClientEvent("PlayerListItem", packetIn);
    }

    @Override
    public void handleDestroyEntities(SDestroyEntitiesPacket packetIn) {
        netHandlerOriginal.handleDestroyEntities(packetIn);
		sendClientEvent("DestroyEntities", packetIn);
    }

    @Override
    public void handleRemoveEntityEffect(SRemoveEntityEffectPacket packetIn) {
        netHandlerOriginal.handleRemoveEntityEffect(packetIn);
		sendClientEvent("RemoveEntityEffect", packetIn);
    }

    @Override
    public void handleRespawn(SRespawnPacket packetIn) {
        netHandlerOriginal.handleRespawn(packetIn);
		sendClientEvent("Respawn", packetIn);
    }

    @Override
    public void handleEntityHeadLook(SEntityHeadLookPacket packetIn) {
        netHandlerOriginal.handleEntityHeadLook(packetIn);
		sendClientEvent("EntityHeadLook", packetIn);
    }

    @Override
    public void handleHeldItemChange(SHeldItemChangePacket packetIn) {
        netHandlerOriginal.handleHeldItemChange(packetIn);
		sendClientEvent("HeldItemChange", packetIn);
    }

    @Override
    public void handleDisplayObjective(SDisplayObjectivePacket packetIn) {
        netHandlerOriginal.handleDisplayObjective(packetIn);
		sendClientEvent("DisplayObjective", packetIn);
    }

    @Override
    public void handleEntityMetadata(SEntityMetadataPacket packetIn) {
        netHandlerOriginal.handleEntityMetadata(packetIn);
		sendClientEvent("EntityMetadata", packetIn);
    }

    @Override
    public void handleEntityVelocity(SEntityVelocityPacket packetIn) {
        netHandlerOriginal.handleEntityVelocity(packetIn);
		sendClientEvent("EntityVelocity", packetIn);
    }

    @Override
    public void handleEntityEquipment(SEntityEquipmentPacket packetIn) {
        netHandlerOriginal.handleEntityEquipment(packetIn);
		sendClientEvent("EntityEquipment", packetIn);
    }

    @Override
    public void handleSetExperience(SSetExperiencePacket packetIn) {
        netHandlerOriginal.handleSetExperience(packetIn);
		sendClientEvent("SetExperience", packetIn);
    }

    @Override
    public void handleUpdateHealth(SUpdateHealthPacket packetIn) {
        netHandlerOriginal.handleUpdateHealth(packetIn);
		sendClientEvent("UpdateHealth", packetIn);
    }

    @Override
    public void handleTeams(STeamsPacket packetIn) {
        netHandlerOriginal.handleTeams(packetIn);
		sendClientEvent("Teams", packetIn);
    }

    @Override
    public void handleUpdateScore(SUpdateScorePacket packetIn) {
        netHandlerOriginal.handleUpdateScore(packetIn);
		sendClientEvent("UpdateScore", packetIn);
    }

    @Override
    public void handleSpawnPosition(SSpawnPositionPacket packetIn) {
        netHandlerOriginal.handleSpawnPosition(packetIn);
		sendClientEvent("SpawnPosition", packetIn);
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
		sendClientEvent("SoundEffect", packetIn);
    }

    @Override
    public void func_217266_a(SSpawnMovingSoundEffectPacket packetIn) {
        netHandlerOriginal.func_217266_a(packetIn);
        sendClientEvent("SpawnMovingSoundEffect", packetIn);
    }

    @Override
    public void handleCustomSound(SPlaySoundPacket packetIn) {
        netHandlerOriginal.handleCustomSound(packetIn);
		sendClientEvent("CustomSound", packetIn);
    }

    @Override
    public void handleCollectItem(SCollectItemPacket packetIn) {
        netHandlerOriginal.handleCollectItem(packetIn);
		sendClientEvent("CollectItem", packetIn);
    }

    @Override
    public void handleEntityTeleport(SEntityTeleportPacket packetIn) {
        netHandlerOriginal.handleEntityTeleport(packetIn);
		sendClientEvent("EntityTeleport", packetIn);
    }

    @Override
    public void handleEntityProperties(SEntityPropertiesPacket packetIn) {
        netHandlerOriginal.handleEntityProperties(packetIn);
		sendClientEvent("EntityProperties", packetIn);
    }

    @Override
    public void handleEntityEffect(SPlayEntityEffectPacket packetIn) {
        netHandlerOriginal.handleEntityEffect(packetIn);
		sendClientEvent("EntityEffect", packetIn);
    }

    @Override
    public void handleTags(STagsListPacket packetIn) {
        netHandlerOriginal.handleTags(packetIn);
		sendClientEvent("Tags", packetIn);
    }

    @Override
    public void handleCombatEvent(SCombatPacket packetIn) {
        netHandlerOriginal.handleCombatEvent(packetIn);
		sendClientEvent("CombatEvent", packetIn);
    }

    @Override
    public void handleServerDifficulty(SServerDifficultyPacket packetIn) {
        netHandlerOriginal.handleServerDifficulty(packetIn);
		sendClientEvent("ServerDifficulty", packetIn);
    }

    @Override
    public void handleCamera(SCameraPacket packetIn) {
        netHandlerOriginal.handleCamera(packetIn);
		sendClientEvent("Camera", packetIn);
    }

    @Override
    public void handleWorldBorder(SWorldBorderPacket packetIn) {
        netHandlerOriginal.handleWorldBorder(packetIn);
		sendClientEvent("WorldBorder", packetIn);
    }

    @Override
    public void handleTitle(STitlePacket packetIn) {
        netHandlerOriginal.handleTitle(packetIn);
		sendClientEvent("Title", packetIn);
    }

    @Override
    public void handlePlayerListHeaderFooter(SPlayerListHeaderFooterPacket packetIn) {
        netHandlerOriginal.handlePlayerListHeaderFooter(packetIn);
		sendClientEvent("PlayerListHeaderFooter", packetIn);
    }

    @Override
    public void handleResourcePack(SSendResourcePackPacket packetIn) {
        netHandlerOriginal.handleResourcePack(packetIn);
		sendClientEvent("ResourcePack", packetIn);
    }

    @Override
    public void handleUpdateBossInfo(SUpdateBossInfoPacket packetIn) {
        netHandlerOriginal.handleUpdateBossInfo(packetIn);
		sendClientEvent("UpdateBossInfo", packetIn);
    }

    @Override
    public void handleCooldown(SCooldownPacket packetIn) {
        netHandlerOriginal.handleCooldown(packetIn);
		sendClientEvent("Cooldown", packetIn);
    }

    @Override
    public void handleMoveVehicle(SMoveVehiclePacket packetIn) {
        netHandlerOriginal.handleMoveVehicle(packetIn);
		sendClientEvent("MoveVehicle", packetIn);
    }

    @Override
    public void handleAdvancementInfo(SAdvancementInfoPacket packetIn) {
        netHandlerOriginal.handleAdvancementInfo(packetIn);
		sendClientEvent("AdvancementInfo", packetIn);
    }

    @Override
    public void handleSelectAdvancementsTab(SSelectAdvancementsTabPacket packetIn) {
        netHandlerOriginal.handleSelectAdvancementsTab(packetIn);
		sendClientEvent("SelectAdvancementsTab", packetIn);
    }

    @Override
    public void handlePlaceGhostRecipe(SPlaceGhostRecipePacket packetIn) {
        netHandlerOriginal.handlePlaceGhostRecipe(packetIn);
		sendClientEvent("PlaceGhostRecipe", packetIn);
    }

    @Override
    public void handleCommandList(SCommandListPacket packetIn) {
        netHandlerOriginal.handleCommandList(packetIn);
		sendClientEvent("CommandList", packetIn);
    }

    @Override
    public void handleStopSound(SStopSoundPacket packetIn) {
        netHandlerOriginal.handleStopSound(packetIn);
		sendClientEvent("StopSound", packetIn);
    }

    @Override
    public void handleTabComplete(STabCompletePacket packetIn) {
        netHandlerOriginal.handleTabComplete(packetIn);
		sendClientEvent("TabComplete", packetIn);
    }

    @Override
    public void handleUpdateRecipes(SUpdateRecipesPacket packetIn) {
        netHandlerOriginal.handleUpdateRecipes(packetIn);
		sendClientEvent("UpdateRecipes", packetIn);
    }

    @Override
    public void handlePlayerLook(SPlayerLookPacket packetIn) {
        netHandlerOriginal.handlePlayerLook(packetIn);
		sendClientEvent("PlayerLook", packetIn);
    }

    @Override
    public void handleNBTQueryResponse(SQueryNBTResponsePacket packetIn) {
        netHandlerOriginal.handleNBTQueryResponse(packetIn);
		sendClientEvent("NBTQueryResponse", packetIn);
    }

    @Override
    public void handleUpdateLight(SUpdateLightPacket packetIn) {
        netHandlerOriginal.handleUpdateLight(packetIn);
		sendClientEvent("UpdateLight", packetIn);
    }

    @Override
    public void handleOpenBookPacket(SOpenBookWindowPacket packetIn) {
        netHandlerOriginal.handleOpenBookPacket(packetIn);
		sendClientEvent("OpenBookPacket", packetIn);
    }

    @Override
    public void handleOpenWindowPacket(SOpenWindowPacket packetIn) {
        netHandlerOriginal.handleOpenWindowPacket(packetIn);
		sendClientEvent("OpenWindowPacket", packetIn);
    }

    @Override
    public void handleMerchantOffers(SMerchantOffersPacket packetIn) {
        netHandlerOriginal.handleMerchantOffers(packetIn);
		sendClientEvent("MerchantOffers", packetIn);
    }

    @Override
    public void handleUpdateViewDistancePacket(SUpdateViewDistancePacket packetIn) {
        netHandlerOriginal.handleUpdateViewDistancePacket(packetIn);
		sendClientEvent("UpdateViewDistancePacket", packetIn);
    }

    @Override
    public void handleChunkPositionPacket(SUpdateChunkPositionPacket packetIn) {
        netHandlerOriginal.handleChunkPositionPacket(packetIn);
		sendClientEvent("ChunkPositionPacket", packetIn);
    }

    @Override
    public void func_225312_a(SPlayerDiggingPacket packetIn) {
        netHandlerOriginal.func_225312_a(packetIn);
        sendClientEvent("PlayerDigging", packetIn);

    }

    @Override
    public void onDisconnect(ITextComponent reason) {
        netHandlerOriginal.onDisconnect(reason);
        LitEventHandler.CLIENT.post(new LitEvent(this, LitEvent.TYPE.DISCONNECT, reason.getUnformattedComponentText()));
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
    private void sendClientEvent(String name, IPacket<?> packet)
    {
        sendClientEvent(name, true, packet, ChainableMap.newMap());
    }
}
