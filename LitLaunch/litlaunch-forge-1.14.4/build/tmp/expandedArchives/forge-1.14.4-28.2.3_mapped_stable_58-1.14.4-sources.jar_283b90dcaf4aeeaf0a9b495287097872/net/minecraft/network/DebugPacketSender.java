package net.minecraft.network;

import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.raid.Raid;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DebugPacketSender {
   private static final Logger LOGGER = LogManager.getLogger();

   public static void func_218802_a(ServerWorld worldIn, ChunkPos p_218802_1_) {
   }

   public static void func_218799_a(ServerWorld worldIn, BlockPos p_218799_1_) {
   }

   public static void func_218805_b(ServerWorld worldIn, BlockPos p_218805_1_) {
   }

   public static void func_218801_c(ServerWorld worldIn, BlockPos p_218801_1_) {
   }

   public static void sendPath(World worldIn, MobEntity p_218803_1_, @Nullable Path p_218803_2_, float p_218803_3_) {
   }

   public static void func_218806_a(World worldIn, BlockPos p_218806_1_) {
   }

   public static void sendStructureStart(IWorld worldIn, StructureStart p_218804_1_) {
   }

   public static void sendGoal(World worldIn, MobEntity p_218800_1_, GoalSelector p_218800_2_) {
   }

   public static void func_222946_a(ServerWorld worldIn, Collection<Raid> p_222946_1_) {
   }

   public static void sendLivingEntity(LivingEntity p_218798_0_) {
   }
}