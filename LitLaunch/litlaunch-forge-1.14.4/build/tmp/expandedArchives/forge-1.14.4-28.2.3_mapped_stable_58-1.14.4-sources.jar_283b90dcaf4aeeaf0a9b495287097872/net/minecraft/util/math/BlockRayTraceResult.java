package net.minecraft.util.math;

import net.minecraft.util.Direction;

public class BlockRayTraceResult extends RayTraceResult {
   private final Direction face;
   private final BlockPos pos;
   private final boolean isMiss;
   private final boolean inside;

   public static BlockRayTraceResult createMiss(Vec3d hitVec, Direction faceIn, BlockPos posIn) {
      return new BlockRayTraceResult(true, hitVec, faceIn, posIn, false);
   }

   public BlockRayTraceResult(Vec3d hitVec, Direction faceIn, BlockPos posIn, boolean isInside) {
      this(false, hitVec, faceIn, posIn, isInside);
   }

   private BlockRayTraceResult(boolean isMissIn, Vec3d hitVec, Direction faceIn, BlockPos posIn, boolean isInside) {
      super(hitVec);
      this.isMiss = isMissIn;
      this.face = faceIn;
      this.pos = posIn;
      this.inside = isInside;
   }

   public BlockRayTraceResult withFace(Direction newFace) {
      return new BlockRayTraceResult(this.isMiss, this.hitResult, newFace, this.pos, this.inside);
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public Direction getFace() {
      return this.face;
   }

   public RayTraceResult.Type getType() {
      return this.isMiss ? RayTraceResult.Type.MISS : RayTraceResult.Type.BLOCK;
   }

   public boolean isInside() {
      return this.inside;
   }
}