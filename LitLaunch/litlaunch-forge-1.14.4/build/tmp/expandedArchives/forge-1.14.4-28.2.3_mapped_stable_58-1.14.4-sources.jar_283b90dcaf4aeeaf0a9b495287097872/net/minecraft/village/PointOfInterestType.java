package net.minecraft.village;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.state.properties.BedPart;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.registry.Registry;

public class PointOfInterestType extends net.minecraftforge.registries.ForgeRegistryEntry<PointOfInterestType> {
   private static final Predicate<PointOfInterestType> ANY_VILLAGER_WORKSTATION = (p_221041_0_) -> {
      return Registry.VILLAGER_PROFESSION.stream().map(VillagerProfession::getPointOfInterest).collect(Collectors.toSet()).contains(p_221041_0_);
   };
   public static final Predicate<PointOfInterestType> field_221053_a = (p_221049_0_) -> {
      return true;
   };
   private static final Set<BlockState> BED_HEADS = ImmutableList.of(Blocks.RED_BED, Blocks.BLACK_BED, Blocks.BLUE_BED, Blocks.BROWN_BED, Blocks.CYAN_BED, Blocks.GRAY_BED, Blocks.GREEN_BED, Blocks.LIGHT_BLUE_BED, Blocks.LIGHT_GRAY_BED, Blocks.LIME_BED, Blocks.MAGENTA_BED, Blocks.ORANGE_BED, Blocks.PINK_BED, Blocks.PURPLE_BED, Blocks.WHITE_BED, Blocks.YELLOW_BED).stream().flatMap((p_221043_0_) -> {
      return p_221043_0_.getStateContainer().getValidStates().stream();
   }).filter((p_221050_0_) -> {
      return p_221050_0_.get(BedBlock.PART) == BedPart.HEAD;
   }).collect(ImmutableSet.toImmutableSet());
   private static final Map<BlockState, PointOfInterestType> field_221073_u = Maps.newHashMap();
   public static final PointOfInterestType UNEMPLOYED = func_221039_a("unemployed", ImmutableSet.of(), 1, (SoundEvent)null, ANY_VILLAGER_WORKSTATION, 1);
   public static final PointOfInterestType ARMORER = func_221051_a("armorer", getAllStates(Blocks.BLAST_FURNACE), 1, SoundEvents.ENTITY_VILLAGER_WORK_ARMORER, 1);
   public static final PointOfInterestType BUTCHER = func_221051_a("butcher", getAllStates(Blocks.SMOKER), 1, SoundEvents.ENTITY_VILLAGER_WORK_BUTCHER, 1);
   public static final PointOfInterestType CARTOGRAPHER = func_221051_a("cartographer", getAllStates(Blocks.CARTOGRAPHY_TABLE), 1, SoundEvents.ENTITY_VILLAGER_WORK_CARTOGRAPHER, 1);
   public static final PointOfInterestType CLERIC = func_221051_a("cleric", getAllStates(Blocks.BREWING_STAND), 1, SoundEvents.ENTITY_VILLAGER_WORK_CLERIC, 1);
   public static final PointOfInterestType FARMER = func_221051_a("farmer", getAllStates(Blocks.COMPOSTER), 1, SoundEvents.ENTITY_VILLAGER_WORK_FARMER, 1);
   public static final PointOfInterestType FISHERMAN = func_221051_a("fisherman", getAllStates(Blocks.BARREL), 1, SoundEvents.ENTITY_VILLAGER_WORK_FISHERMAN, 1);
   public static final PointOfInterestType FLETCHER = func_221051_a("fletcher", getAllStates(Blocks.FLETCHING_TABLE), 1, SoundEvents.ENTITY_VILLAGER_WORK_FLETCHER, 1);
   public static final PointOfInterestType LEATHERWORKER = func_221051_a("leatherworker", getAllStates(Blocks.CAULDRON), 1, SoundEvents.ENTITY_VILLAGER_WORK_LEATHERWORKER, 1);
   public static final PointOfInterestType LIBRARIAN = func_221051_a("librarian", getAllStates(Blocks.LECTERN), 1, SoundEvents.ENTITY_VILLAGER_WORK_LIBRARIAN, 1);
   public static final PointOfInterestType MASON = func_221051_a("mason", getAllStates(Blocks.STONECUTTER), 1, SoundEvents.ENTITY_VILLAGER_WORK_MASON, 1);
   public static final PointOfInterestType NITWIT = func_221051_a("nitwit", ImmutableSet.of(), 1, (SoundEvent)null, 1);
   public static final PointOfInterestType SHEPHERD = func_221051_a("shepherd", getAllStates(Blocks.LOOM), 1, SoundEvents.ENTITY_VILLAGER_WORK_SHEPHERD, 1);
   public static final PointOfInterestType TOOLSMITH = func_221051_a("toolsmith", getAllStates(Blocks.SMITHING_TABLE), 1, SoundEvents.ENTITY_VILLAGER_WORK_TOOLSMITH, 1);
   public static final PointOfInterestType WEAPONSMITH = func_221051_a("weaponsmith", getAllStates(Blocks.GRINDSTONE), 1, SoundEvents.ENTITY_VILLAGER_WORK_WEAPONSMITH, 1);
   public static final PointOfInterestType HOME = func_221051_a("home", BED_HEADS, 1, (SoundEvent)null, 1);
   public static final PointOfInterestType MEETING = func_221051_a("meeting", getAllStates(Blocks.BELL), 32, (SoundEvent)null, 6);
   private final String name;
   private final Set<BlockState> field_221075_w;
   private final int maxFreeTickets;
   @Nullable
   private final SoundEvent workSound;
   private final Predicate<PointOfInterestType> field_221078_z;
   private final int field_225481_A;

   private static Set<BlockState> getAllStates(Block p_221042_0_) {
      return ImmutableSet.copyOf(p_221042_0_.getStateContainer().getValidStates());
   }

   public PointOfInterestType(String p_i51553_1_, Set<BlockState> p_i51553_2_, int p_i51553_3_, @Nullable SoundEvent p_i51553_4_, Predicate<PointOfInterestType> p_i51553_5_, int p_i51553_6_) {
      this.name = p_i51553_1_;
      this.field_221075_w = ImmutableSet.copyOf(p_i51553_2_);
      this.maxFreeTickets = p_i51553_3_;
      this.workSound = p_i51553_4_;
      this.field_221078_z = p_i51553_5_;
      this.field_225481_A = p_i51553_6_;
   }

   public PointOfInterestType(String p_i51554_1_, Set<BlockState> p_i51554_2_, int p_i51554_3_, @Nullable SoundEvent p_i51554_4_, int p_i51554_5_) {
      this.name = p_i51554_1_;
      this.field_221075_w = ImmutableSet.copyOf(p_i51554_2_);
      this.maxFreeTickets = p_i51554_3_;
      this.workSound = p_i51554_4_;
      this.field_221078_z = (p_221046_1_) -> {
         return p_221046_1_ == this;
      };
      this.field_225481_A = p_i51554_5_;
   }

   public int getMaxFreeTickets() {
      return this.maxFreeTickets;
   }

   public Predicate<PointOfInterestType> func_221045_c() {
      return this.field_221078_z;
   }

   public int func_225478_d() {
      return this.field_225481_A;
   }

   public String toString() {
      return this.name;
   }

   @Nullable
   public SoundEvent getWorkSound() {
      return this.workSound;
   }

   private static PointOfInterestType func_221051_a(String key, Set<BlockState> p_221051_1_, int p_221051_2_, @Nullable SoundEvent p_221051_3_, int p_221051_4_) {
      return func_221052_a(Registry.POINT_OF_INTEREST_TYPE.register(new ResourceLocation(key), new PointOfInterestType(key, p_221051_1_, p_221051_2_, p_221051_3_, p_221051_4_)));
   }

   private static PointOfInterestType func_221039_a(String key, Set<BlockState> p_221039_1_, int p_221039_2_, @Nullable SoundEvent p_221039_3_, Predicate<PointOfInterestType> p_221039_4_, int p_221039_5_) {
      return func_221052_a(Registry.POINT_OF_INTEREST_TYPE.register(new ResourceLocation(key), new PointOfInterestType(key, p_221039_1_, p_221039_2_, p_221039_3_, p_221039_4_, p_221039_5_)));
   }

   private static PointOfInterestType func_221052_a(PointOfInterestType p_221052_0_) {
      p_221052_0_.field_221075_w.forEach((p_221040_1_) -> {
         PointOfInterestType pointofinteresttype = field_221073_u.put(p_221040_1_, p_221052_0_);
         if (pointofinteresttype != null) {
            throw new IllegalStateException(String.format("%s is defined in too many tags", p_221040_1_));
         }
      });
      return p_221052_0_;
   }

   public static Optional<PointOfInterestType> forState(BlockState state) {
      return Optional.ofNullable(field_221073_u.get(state));
   }

   public static Stream<BlockState> getAllStates() {
      return field_221073_u.keySet().stream();
   }
}