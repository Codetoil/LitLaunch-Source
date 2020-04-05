package net.minecraft.server.management;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CommandBlockBlock;
import net.minecraft.block.JigsawBlock;
import net.minecraft.block.StructureBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.network.play.server.SPlayerDiggingPacket;
import net.minecraft.network.play.server.SPlayerListItemPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerInteractionManager {
   private static final Logger field_225418_c = LogManager.getLogger();
   public ServerWorld world;
   public ServerPlayerEntity player;
   private GameType gameType = GameType.NOT_SET;
   private boolean isDestroyingBlock;
   private int initialDamage;
   private BlockPos destroyPos = BlockPos.ZERO;
   private int ticks;
   private boolean receivedFinishDiggingPacket;
   private BlockPos delayedDestroyPos = BlockPos.ZERO;
   private int initialBlockDamage;
   private int durabilityRemainingOnBlock = -1;

   public PlayerInteractionManager(ServerWorld p_i50702_1_) {
      this.world = p_i50702_1_;
   }

   public void setGameType(GameType type) {
      this.gameType = type;
      type.configurePlayerCapabilities(this.player.abilities);
      this.player.sendPlayerAbilities();
      this.player.server.getPlayerList().sendPacketToAllPlayers(new SPlayerListItemPacket(SPlayerListItemPacket.Action.UPDATE_GAME_MODE, this.player));
      this.world.updateAllPlayersSleepingFlag();
   }

   public GameType getGameType() {
      return this.gameType;
   }

   public boolean survivalOrAdventure() {
      return this.gameType.isSurvivalOrAdventure();
   }

   public boolean isCreative() {
      return this.gameType.isCreative();
   }

   public void initializeGameType(GameType type) {
      if (this.gameType == GameType.NOT_SET) {
         this.gameType = type;
      }

      this.setGameType(this.gameType);
   }

   public void tick() {
      ++this.ticks;
      if (this.receivedFinishDiggingPacket) {
         BlockState blockstate = this.world.getBlockState(this.delayedDestroyPos);
         if (blockstate.isAir(world, delayedDestroyPos)) {
            this.receivedFinishDiggingPacket = false;
         } else {
            float f = this.func_225417_a(blockstate, this.delayedDestroyPos);
            if (f >= 1.0F) {
               this.receivedFinishDiggingPacket = false;
               this.tryHarvestBlock(this.delayedDestroyPos);
            }
         }
      } else if (this.isDestroyingBlock) {
         BlockState blockstate1 = this.world.getBlockState(this.destroyPos);
         if (blockstate1.isAir(world, destroyPos)) {
            this.world.sendBlockBreakProgress(this.player.getEntityId(), this.destroyPos, -1);
            this.durabilityRemainingOnBlock = -1;
            this.isDestroyingBlock = false;
         } else {
            this.func_225417_a(blockstate1, this.destroyPos);
         }
      }

   }

   private float func_225417_a(BlockState p_225417_1_, BlockPos p_225417_2_) {
      int i = this.ticks - this.initialBlockDamage;
      float f = p_225417_1_.getPlayerRelativeBlockHardness(this.player, this.player.world, p_225417_2_) * (float)(i + 1);
      int j = (int)(f * 10.0F);
      if (j != this.durabilityRemainingOnBlock) {
         this.world.sendBlockBreakProgress(this.player.getEntityId(), p_225417_2_, j);
         this.durabilityRemainingOnBlock = j;
      }

      return f;
   }

   public void func_225416_a(BlockPos p_225416_1_, CPlayerDiggingPacket.Action p_225416_2_, Direction p_225416_3_, int p_225416_4_) {
      double d0 = this.player.posX - ((double)p_225416_1_.getX() + 0.5D);
      double d1 = this.player.posY - ((double)p_225416_1_.getY() + 0.5D) + 1.5D;
      double d2 = this.player.posZ - ((double)p_225416_1_.getZ() + 0.5D);
      double d3 = d0 * d0 + d1 * d1 + d2 * d2;
      double dist = player.getAttribute(net.minecraft.entity.player.PlayerEntity.REACH_DISTANCE).getValue() + 1;
      net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock event = net.minecraftforge.common.ForgeHooks.onLeftClickBlock(player, p_225416_1_, p_225416_3_);
      if (event.isCanceled() || (!this.isCreative() && event.getUseItem() == net.minecraftforge.eventbus.api.Event.Result.DENY)) { // Restore block and te data
         player.connection.sendPacket(new SPlayerDiggingPacket(p_225416_1_, world.getBlockState(p_225416_1_), p_225416_2_, false));
         world.notifyBlockUpdate(p_225416_1_, world.getBlockState(p_225416_1_), world.getBlockState(p_225416_1_), 3);
         return;
      }
      dist *= dist;
      if (d3 > dist) {
         this.player.connection.sendPacket(new SPlayerDiggingPacket(p_225416_1_, this.world.getBlockState(p_225416_1_), p_225416_2_, false));
      } else if (p_225416_1_.getY() >= p_225416_4_) {
         this.player.connection.sendPacket(new SPlayerDiggingPacket(p_225416_1_, this.world.getBlockState(p_225416_1_), p_225416_2_, false));
      } else {
         if (p_225416_2_ == CPlayerDiggingPacket.Action.START_DESTROY_BLOCK) {
            if (!this.world.isBlockModifiable(this.player, p_225416_1_)) {
               this.player.connection.sendPacket(new SPlayerDiggingPacket(p_225416_1_, this.world.getBlockState(p_225416_1_), p_225416_2_, false));
               return;
            }

            if (this.isCreative()) {
               if (!this.world.extinguishFire((PlayerEntity)null, p_225416_1_, p_225416_3_)) {
                  this.func_225415_a(p_225416_1_, p_225416_2_);
               } else {
                  this.player.connection.sendPacket(new SPlayerDiggingPacket(p_225416_1_, this.world.getBlockState(p_225416_1_), p_225416_2_, true));
               }

               return;
            }

            if (this.player.func_223729_a(this.world, p_225416_1_, this.gameType)) {
               this.player.connection.sendPacket(new SPlayerDiggingPacket(p_225416_1_, this.world.getBlockState(p_225416_1_), p_225416_2_, false));
               return;
            }

            this.world.extinguishFire((PlayerEntity)null, p_225416_1_, p_225416_3_);
            this.initialDamage = this.ticks;
            float f = 1.0F;
            BlockState blockstate = this.world.getBlockState(p_225416_1_);
            if (!blockstate.isAir(world, p_225416_1_)) {
               if (event.getUseBlock() != net.minecraftforge.eventbus.api.Event.Result.DENY)
               blockstate.onBlockClicked(this.world, p_225416_1_, this.player);
               f = blockstate.getPlayerRelativeBlockHardness(this.player, this.player.world, p_225416_1_);
            }

            if (!blockstate.isAir(world, p_225416_1_) && f >= 1.0F) {
               this.func_225415_a(p_225416_1_, p_225416_2_);
            } else {
               this.isDestroyingBlock = true;
               this.destroyPos = p_225416_1_;
               int i = (int)(f * 10.0F);
               this.world.sendBlockBreakProgress(this.player.getEntityId(), p_225416_1_, i);
               this.player.connection.sendPacket(new SPlayerDiggingPacket(p_225416_1_, this.world.getBlockState(p_225416_1_), p_225416_2_, true));
               this.durabilityRemainingOnBlock = i;
            }
         } else if (p_225416_2_ == CPlayerDiggingPacket.Action.STOP_DESTROY_BLOCK) {
            if (p_225416_1_.equals(this.destroyPos)) {
               int j = this.ticks - this.initialDamage;
               BlockState blockstate1 = this.world.getBlockState(p_225416_1_);
               if (!blockstate1.isAir()) {
                  float f1 = blockstate1.getPlayerRelativeBlockHardness(this.player, this.player.world, p_225416_1_) * (float)(j + 1);
                  if (f1 >= 0.7F) {
                     this.isDestroyingBlock = false;
                     this.world.sendBlockBreakProgress(this.player.getEntityId(), p_225416_1_, -1);
                     this.func_225415_a(p_225416_1_, p_225416_2_);
                     return;
                  }

                  if (!this.receivedFinishDiggingPacket) {
                     this.isDestroyingBlock = false;
                     this.receivedFinishDiggingPacket = true;
                     this.delayedDestroyPos = p_225416_1_;
                     this.initialBlockDamage = this.initialDamage;
                  }
               }
            }

            this.player.connection.sendPacket(new SPlayerDiggingPacket(p_225416_1_, this.world.getBlockState(p_225416_1_), p_225416_2_, true));
         } else if (p_225416_2_ == CPlayerDiggingPacket.Action.ABORT_DESTROY_BLOCK) {
            this.isDestroyingBlock = false;
            this.world.sendBlockBreakProgress(this.player.getEntityId(), this.destroyPos, -1);
            this.player.connection.sendPacket(new SPlayerDiggingPacket(p_225416_1_, this.world.getBlockState(p_225416_1_), p_225416_2_, true));
         }

      }
   }

   public void func_225415_a(BlockPos p_225415_1_, CPlayerDiggingPacket.Action p_225415_2_) {
      if (this.tryHarvestBlock(p_225415_1_)) {
         this.player.connection.sendPacket(new SPlayerDiggingPacket(p_225415_1_, this.world.getBlockState(p_225415_1_), p_225415_2_, true));
      } else {
         this.player.connection.sendPacket(new SPlayerDiggingPacket(p_225415_1_, this.world.getBlockState(p_225415_1_), p_225415_2_, false));
      }

   }

   public boolean tryHarvestBlock(BlockPos pos) {
      BlockState blockstate = this.world.getBlockState(pos);
      int exp = net.minecraftforge.common.ForgeHooks.onBlockBreakEvent(world, gameType, player, pos);
      if (exp == -1) {
         return false;
      } else {
         TileEntity tileentity = this.world.getTileEntity(pos);
         Block block = blockstate.getBlock();
         if ((block instanceof CommandBlockBlock || block instanceof StructureBlock || block instanceof JigsawBlock) && !this.player.canUseCommandBlock()) {
            this.world.notifyBlockUpdate(pos, blockstate, blockstate, 3);
            return false;
         } else if (player.getHeldItemMainhand().onBlockStartBreak(pos, player)) {
            return false;
         } else if (this.player.func_223729_a(this.world, pos, this.gameType)) {
            return false;
         } else {
            if (this.isCreative()) {
               removeBlock(pos, false);
               return true;
            } else {
               ItemStack itemstack = this.player.getHeldItemMainhand();
               ItemStack copy = itemstack.copy();
               boolean flag1 = blockstate.canHarvestBlock(this.world, pos, this.player);
               itemstack.onBlockDestroyed(this.world, blockstate, pos, this.player);
               if (itemstack.isEmpty() && !copy.isEmpty()) {
                  net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(this.player, copy, Hand.MAIN_HAND);
               }
               boolean flag = removeBlock(pos, flag1);
               if (flag && flag1) {
                  ItemStack itemstack1 = itemstack.isEmpty() ? ItemStack.EMPTY : itemstack.copy();
                  block.harvestBlock(this.world, this.player, pos, blockstate, tileentity, itemstack1);
               }
               if (flag && exp > 0) {
                  blockstate.getBlock().dropXpOnBlockBreak(world, pos, exp);
               }

               return true;
            }
         }
      }
   }

   private boolean removeBlock(BlockPos p_180235_1_, boolean canHarvest) {
      BlockState state = this.world.getBlockState(p_180235_1_);
      boolean removed = state.removedByPlayer(this.world, p_180235_1_, this.player, canHarvest, this.world.getFluidState(p_180235_1_));
      if (removed)
        state.getBlock().onPlayerDestroy(this.world, p_180235_1_, state);
      return removed;
   }

   public ActionResultType processRightClick(PlayerEntity player, World worldIn, ItemStack stack, Hand hand) {
      if (this.gameType == GameType.SPECTATOR) {
         return ActionResultType.PASS;
      } else if (player.getCooldownTracker().hasCooldown(stack.getItem())) {
         return ActionResultType.PASS;
      } else {
         ActionResultType cancelResult = net.minecraftforge.common.ForgeHooks.onItemRightClick(player, hand);
         if (cancelResult != null) return cancelResult;
         int i = stack.getCount();
         int j = stack.getDamage();
         ActionResult<ItemStack> actionresult = stack.useItemRightClick(worldIn, player, hand);
         ItemStack itemstack = actionresult.getResult();
         if (itemstack == stack && itemstack.getCount() == i && itemstack.getUseDuration() <= 0 && itemstack.getDamage() == j) {
            return actionresult.getType();
         } else if (actionresult.getType() == ActionResultType.FAIL && itemstack.getUseDuration() > 0 && !player.isHandActive()) {
            return actionresult.getType();
         } else {
            player.setHeldItem(hand, itemstack);
            if (this.isCreative()) {
               itemstack.setCount(i);
               if (itemstack.isDamageable()) {
                  itemstack.setDamage(j);
               }
            }

            if (itemstack.isEmpty()) {
               player.setHeldItem(hand, ItemStack.EMPTY);
            }

            if (!player.isHandActive()) {
               ((ServerPlayerEntity)player).sendContainerToPlayer(player.container);
            }

            return actionresult.getType();
         }
      }
   }

   public ActionResultType func_219441_a(PlayerEntity playerIn, World worldIn, ItemStack stackIn, Hand handIn, BlockRayTraceResult blockRaytraceResultIn) {
      BlockPos blockpos = blockRaytraceResultIn.getPos();
      BlockState blockstate = worldIn.getBlockState(blockpos);
      if (this.gameType == GameType.SPECTATOR) {
         INamedContainerProvider inamedcontainerprovider = blockstate.getContainer(worldIn, blockpos);
         if (inamedcontainerprovider != null) {
            playerIn.openContainer(inamedcontainerprovider);
            return ActionResultType.SUCCESS;
         } else {
            return ActionResultType.PASS;
         }
      } else {
         net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock event = net.minecraftforge.common.ForgeHooks.onRightClickBlock(playerIn, handIn, blockpos, blockRaytraceResultIn.getFace());
         if (event.isCanceled()) return event.getCancellationResult();
         ItemUseContext itemusecontext = new ItemUseContext(playerIn, handIn, blockRaytraceResultIn);
         if (event.getUseItem() != net.minecraftforge.eventbus.api.Event.Result.DENY) {
            ActionResultType result = stackIn.onItemUseFirst(itemusecontext);
            if (result != ActionResultType.PASS) return result;
         }
         boolean flag = !playerIn.getHeldItemMainhand().isEmpty() || !playerIn.getHeldItemOffhand().isEmpty();
         boolean flag1 = !(playerIn.isSneaking() && flag) || (playerIn.getHeldItemMainhand().doesSneakBypassUse(worldIn,blockpos,playerIn) && playerIn.getHeldItemOffhand().doesSneakBypassUse(worldIn,blockpos,playerIn));
         if (event.getUseBlock() != net.minecraftforge.eventbus.api.Event.Result.DENY && flag1 && blockstate.onBlockActivated(worldIn, playerIn, handIn, blockRaytraceResultIn)) {
            return ActionResultType.SUCCESS;
         } else if (!stackIn.isEmpty() && !playerIn.getCooldownTracker().hasCooldown(stackIn.getItem())) {
            if (event.getUseItem() == net.minecraftforge.eventbus.api.Event.Result.DENY) return ActionResultType.PASS;
            if (this.isCreative()) {
               int i = stackIn.getCount();
               ActionResultType actionresulttype = stackIn.onItemUse(itemusecontext);
               stackIn.setCount(i);
               return actionresulttype;
            } else {
               return stackIn.onItemUse(itemusecontext);
            }
         } else {
            return ActionResultType.PASS;
         }
      }
   }

   public void func_73080_a(ServerWorld serverWorld) {
      this.world = serverWorld;
   }
}