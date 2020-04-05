package net.minecraft.entity;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.PushReaction;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ICommandSource;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.DoubleNBT;
import net.minecraft.nbt.FloatNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SEntityPacket;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.INameable;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ReuseableStream;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.TicketType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Entity extends net.minecraftforge.common.capabilities.CapabilityProvider<Entity> implements INameable, ICommandSource, net.minecraftforge.common.extensions.IForgeEntity {
   protected static final Logger LOGGER = LogManager.getLogger();
   private static final AtomicInteger NEXT_ENTITY_ID = new AtomicInteger();
   private static final List<ItemStack> EMPTY_EQUIPMENT = Collections.emptyList();
   private static final AxisAlignedBB ZERO_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
   private static double renderDistanceWeight = 1.0D;
   @Deprecated // Forge: Use the getter to allow overriding in mods
   private final EntityType<?> type;
   private int entityId = NEXT_ENTITY_ID.incrementAndGet();
   public boolean preventEntitySpawning;
   private final List<Entity> passengers = Lists.newArrayList();
   protected int rideCooldown;
   private Entity ridingEntity;
   public boolean forceSpawn;
   public World world;
   public double prevPosX;
   public double prevPosY;
   public double prevPosZ;
   public double posX;
   public double posY;
   public double posZ;
   private Vec3d motion = Vec3d.ZERO;
   public float rotationYaw;
   public float rotationPitch;
   public float prevRotationYaw;
   public float prevRotationPitch;
   private AxisAlignedBB boundingBox = ZERO_AABB;
   public boolean onGround;
   public boolean collidedHorizontally;
   public boolean collidedVertically;
   public boolean collided;
   public boolean velocityChanged;
   protected Vec3d motionMultiplier = Vec3d.ZERO;
   @Deprecated //Forge: Use isAlive, remove(boolean) and revive() instead of directly accessing this field. To allow the entity to react to and better control this information.
   public boolean removed;
   public float prevDistanceWalkedModified;
   public float distanceWalkedModified;
   public float distanceWalkedOnStepModified;
   public float fallDistance;
   private float nextStepDistance = 1.0F;
   private float nextFlap = 1.0F;
   public double lastTickPosX;
   public double lastTickPosY;
   public double lastTickPosZ;
   public float stepHeight;
   public boolean noClip;
   public float entityCollisionReduction;
   protected final Random rand = new Random();
   public int ticksExisted;
   private int fire = -this.getFireImmuneTicks();
   protected boolean inWater;
   protected double submergedHeight;
   protected boolean eyesInWater;
   protected boolean inLava;
   public int hurtResistantTime;
   protected boolean firstUpdate = true;
   protected final EntityDataManager dataManager;
   protected static final DataParameter<Byte> FLAGS = EntityDataManager.createKey(Entity.class, DataSerializers.BYTE);
   private static final DataParameter<Integer> AIR = EntityDataManager.createKey(Entity.class, DataSerializers.VARINT);
   private static final DataParameter<Optional<ITextComponent>> CUSTOM_NAME = EntityDataManager.createKey(Entity.class, DataSerializers.OPTIONAL_TEXT_COMPONENT);
   private static final DataParameter<Boolean> CUSTOM_NAME_VISIBLE = EntityDataManager.createKey(Entity.class, DataSerializers.BOOLEAN);
   private static final DataParameter<Boolean> SILENT = EntityDataManager.createKey(Entity.class, DataSerializers.BOOLEAN);
   private static final DataParameter<Boolean> NO_GRAVITY = EntityDataManager.createKey(Entity.class, DataSerializers.BOOLEAN);
   protected static final DataParameter<Pose> POSE = EntityDataManager.createKey(Entity.class, DataSerializers.POSE);
   public boolean addedToChunk;
   public int chunkCoordX;
   public int chunkCoordY;
   public int chunkCoordZ;
   @OnlyIn(Dist.CLIENT)
   public long serverPosX;
   @OnlyIn(Dist.CLIENT)
   public long serverPosY;
   @OnlyIn(Dist.CLIENT)
   public long serverPosZ;
   public boolean ignoreFrustumCheck;
   public boolean isAirBorne;
   public int timeUntilPortal;
   protected boolean inPortal;
   protected int portalCounter;
   public DimensionType dimension;
   protected BlockPos lastPortalPos;
   protected Vec3d lastPortalVec;
   protected Direction teleportDirection;
   private boolean invulnerable;
   protected UUID entityUniqueID = MathHelper.getRandomUUID(this.rand);
   protected String cachedUniqueIdString = this.entityUniqueID.toString();
   protected boolean glowing;
   private final Set<String> tags = Sets.newHashSet();
   private boolean isPositionDirty;
   private final double[] pistonDeltas = new double[]{0.0D, 0.0D, 0.0D};
   private long pistonDeltasGameTime;
   private EntitySize size;
   private float eyeHeight;

   public Entity(EntityType<?> entityTypeIn, World worldIn) {
      super(Entity.class);
      this.type = entityTypeIn;
      this.world = worldIn;
      this.size = entityTypeIn.getSize();
      this.setPosition(0.0D, 0.0D, 0.0D);
      if (worldIn != null) {
         this.dimension = worldIn.dimension.getType();
      }

      this.dataManager = new EntityDataManager(this);
      this.dataManager.register(FLAGS, (byte)0);
      this.dataManager.register(AIR, this.getMaxAir());
      this.dataManager.register(CUSTOM_NAME_VISIBLE, false);
      this.dataManager.register(CUSTOM_NAME, Optional.empty());
      this.dataManager.register(SILENT, false);
      this.dataManager.register(NO_GRAVITY, false);
      this.dataManager.register(POSE, Pose.STANDING);
      this.registerData();
      this.eyeHeight = getEyeHeightForge(Pose.STANDING, this.size);
      net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.EntityEvent.EntityConstructing(this));
      this.gatherCapabilities();
   }

   public boolean isSpectator() {
      return false;
   }

   public final void detach() {
      if (this.isBeingRidden()) {
         this.removePassengers();
      }

      if (this.isPassenger()) {
         this.stopRiding();
      }

   }

   @OnlyIn(Dist.CLIENT)
   public void setPacketCoordinates(double p_213312_1_, double p_213312_3_, double p_213312_5_) {
      this.serverPosX = SEntityPacket.func_218743_a(p_213312_1_);
      this.serverPosY = SEntityPacket.func_218743_a(p_213312_3_);
      this.serverPosZ = SEntityPacket.func_218743_a(p_213312_5_);
   }

   public EntityType<?> getType() {
      return this.type;
   }

   public int getEntityId() {
      return this.entityId;
   }

   public void setEntityId(int id) {
      this.entityId = id;
   }

   public Set<String> getTags() {
      return this.tags;
   }

   public boolean addTag(String tag) {
      return this.tags.size() >= 1024 ? false : this.tags.add(tag);
   }

   public boolean removeTag(String tag) {
      return this.tags.remove(tag);
   }

   public void onKillCommand() {
      this.remove();
   }

   protected abstract void registerData();

   public EntityDataManager getDataManager() {
      return this.dataManager;
   }

   public boolean equals(Object p_equals_1_) {
      if (p_equals_1_ instanceof Entity) {
         return ((Entity)p_equals_1_).entityId == this.entityId;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.entityId;
   }

   @OnlyIn(Dist.CLIENT)
   protected void preparePlayerToSpawn() {
      if (this.world != null) {
         while(this.posY > 0.0D && this.posY < 256.0D) {
            this.setPosition(this.posX, this.posY, this.posZ);
            if (this.world.areCollisionShapesEmpty(this)) {
               break;
            }

            ++this.posY;
         }

         this.setMotion(Vec3d.ZERO);
         this.rotationPitch = 0.0F;
      }
   }

   public void remove() {
      this.remove(false);
   }

   public void remove(boolean keepData) {
      this.removed = true;
      if (!keepData)
         this.invalidateCaps();
   }

   protected void setPose(Pose poseIn) {
      this.dataManager.set(POSE, poseIn);
   }

   public Pose getPose() {
      return this.dataManager.get(POSE);
   }

   protected void setRotation(float yaw, float pitch) {
      this.rotationYaw = yaw % 360.0F;
      this.rotationPitch = pitch % 360.0F;
   }

   public void setPosition(double x, double y, double z) {
      this.posX = x;
      this.posY = y;
      this.posZ = z;
      if (this.isAddedToWorld() && !this.world.isRemote && world instanceof ServerWorld) ((ServerWorld)this.world).chunkCheck(this); // Forge - Process chunk registration after moving.
      float f = this.size.width / 2.0F;
      float f1 = this.size.height;
      this.setBoundingBox(new AxisAlignedBB(x - (double)f, y, z - (double)f, x + (double)f, y + (double)f1, z + (double)f));
   }

   @OnlyIn(Dist.CLIENT)
   public void rotateTowards(double yaw, double pitch) {
      double d0 = pitch * 0.15D;
      double d1 = yaw * 0.15D;
      this.rotationPitch = (float)((double)this.rotationPitch + d0);
      this.rotationYaw = (float)((double)this.rotationYaw + d1);
      this.rotationPitch = MathHelper.clamp(this.rotationPitch, -90.0F, 90.0F);
      this.prevRotationPitch = (float)((double)this.prevRotationPitch + d0);
      this.prevRotationYaw = (float)((double)this.prevRotationYaw + d1);
      this.prevRotationPitch = MathHelper.clamp(this.prevRotationPitch, -90.0F, 90.0F);
      if (this.ridingEntity != null) {
         this.ridingEntity.applyOrientationToEntity(this);
      }

   }

   public void tick() {
      if (!this.world.isRemote) {
         this.setFlag(6, this.isGlowing());
      }

      this.baseTick();
   }

   public void baseTick() {
      this.world.getProfiler().startSection("entityBaseTick");
      if (this.isPassenger() && this.getRidingEntity().removed) {
         this.stopRiding();
      }

      if (this.rideCooldown > 0) {
         --this.rideCooldown;
      }

      this.prevDistanceWalkedModified = this.distanceWalkedModified;
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      this.prevRotationPitch = this.rotationPitch;
      this.prevRotationYaw = this.rotationYaw;
      this.updatePortal();
      this.spawnRunningParticles();
      this.updateAquatics();
      if (this.world.isRemote) {
         this.extinguish();
      } else if (this.fire > 0) {
         if (this.isImmuneToFire()) {
            this.fire -= 4;
            if (this.fire < 0) {
               this.extinguish();
            }
         } else {
            if (this.fire % 20 == 0) {
               this.attackEntityFrom(DamageSource.ON_FIRE, 1.0F);
            }

            --this.fire;
         }
      }

      if (this.isInLava()) {
         this.setOnFireFromLava();
         this.fallDistance *= 0.5F;
      }

      if (this.posY < -64.0D) {
         this.outOfWorld();
      }

      if (!this.world.isRemote) {
         this.setFlag(0, this.fire > 0);
      }

      this.firstUpdate = false;
      this.world.getProfiler().endSection();
   }

   protected void decrementTimeUntilPortal() {
      if (this.timeUntilPortal > 0) {
         --this.timeUntilPortal;
      }

   }

   public int getMaxInPortalTime() {
      return 1;
   }

   protected void setOnFireFromLava() {
      if (!this.isImmuneToFire()) {
         this.setFire(15);
         this.attackEntityFrom(DamageSource.LAVA, 4.0F);
      }
   }

   public void setFire(int seconds) {
      int i = seconds * 20;
      if (this instanceof LivingEntity) {
         i = ProtectionEnchantment.getFireTimeForEntity((LivingEntity)this, i);
      }

      if (this.fire < i) {
         this.fire = i;
      }

   }

   public void setFireTimer(int p_223308_1_) {
      this.fire = p_223308_1_;
   }

   public int getFireTimer() {
      return this.fire;
   }

   public void extinguish() {
      this.fire = 0;
   }

   protected void outOfWorld() {
      this.remove();
   }

   public boolean isOffsetPositionInLiquid(double x, double y, double z) {
      return this.isLiquidPresentInAABB(this.getBoundingBox().offset(x, y, z));
   }

   private boolean isLiquidPresentInAABB(AxisAlignedBB bb) {
      return this.world.isCollisionBoxesEmpty(this, bb) && !this.world.containsAnyLiquid(bb);
   }

   public void move(MoverType typeIn, Vec3d pos) {
      if (this.noClip) {
         this.setBoundingBox(this.getBoundingBox().offset(pos));
         this.resetPositionToBB();
      } else {
         if (typeIn == MoverType.PISTON) {
            pos = this.handlePistonMovement(pos);
            if (pos.equals(Vec3d.ZERO)) {
               return;
            }
         }

         this.world.getProfiler().startSection("move");
         if (this.motionMultiplier.lengthSquared() > 1.0E-7D) {
            pos = pos.mul(this.motionMultiplier);
            this.motionMultiplier = Vec3d.ZERO;
            this.setMotion(Vec3d.ZERO);
         }

         pos = this.handleSneakMovement(pos, typeIn);
         Vec3d vec3d = this.getAllowedMovement(pos);
         if (vec3d.lengthSquared() > 1.0E-7D) {
            this.setBoundingBox(this.getBoundingBox().offset(vec3d));
            this.resetPositionToBB();
         }

         this.world.getProfiler().endSection();
         this.world.getProfiler().startSection("rest");
         this.collidedHorizontally = !MathHelper.epsilonEquals(pos.x, vec3d.x) || !MathHelper.epsilonEquals(pos.z, vec3d.z);
         this.collidedVertically = pos.y != vec3d.y;
         this.onGround = this.collidedVertically && pos.y < 0.0D;
         this.collided = this.collidedHorizontally || this.collidedVertically;
         int i = MathHelper.floor(this.posX);
         int j = MathHelper.floor(this.posY - (double)0.2F);
         int k = MathHelper.floor(this.posZ);
         BlockPos blockpos = new BlockPos(i, j, k);
         BlockState blockstate = this.world.getBlockState(blockpos);
         if (blockstate.isAir(this.world, blockpos)) {
            BlockPos blockpos1 = blockpos.down();
            BlockState blockstate1 = this.world.getBlockState(blockpos1);
            Block block = blockstate1.getBlock();
            if (blockstate.collisionExtendsVertically(this.world, blockpos, this)) {
               blockstate = blockstate1;
               blockpos = blockpos1;
            }
         }

         this.updateFallState(vec3d.y, this.onGround, blockstate, blockpos);
         Vec3d vec3d2 = this.getMotion();
         if (pos.x != vec3d.x) {
            this.setMotion(0.0D, vec3d2.y, vec3d2.z);
         }

         if (pos.z != vec3d.z) {
            this.setMotion(vec3d2.x, vec3d2.y, 0.0D);
         }

         Block block1 = blockstate.getBlock();
         if (pos.y != vec3d.y) {
            block1.onLanded(this.world, this);
         }

         if (this.canTriggerWalking() && (!this.onGround || !this.isSneaking() || !(this instanceof PlayerEntity)) && !this.isPassenger()) {
            double d2 = vec3d.x;
            double d0 = vec3d.y;
            double d1 = vec3d.z;
            if (block1 != Blocks.LADDER && block1 != Blocks.SCAFFOLDING) {
               d0 = 0.0D;
            }

            if (this.onGround) {
               block1.onEntityWalk(this.world, blockpos, this);
            }

            this.distanceWalkedModified = (float)((double)this.distanceWalkedModified + (double)MathHelper.sqrt(horizontalMag(vec3d)) * 0.6D);
            this.distanceWalkedOnStepModified = (float)((double)this.distanceWalkedOnStepModified + (double)MathHelper.sqrt(d2 * d2 + d0 * d0 + d1 * d1) * 0.6D);
            if (this.distanceWalkedOnStepModified > this.nextStepDistance && !blockstate.isAir(this.world, blockpos)) {
               this.nextStepDistance = this.determineNextStepDistance();
               if (this.isInWater()) {
                  Entity entity = this.isBeingRidden() && this.getControllingPassenger() != null ? this.getControllingPassenger() : this;
                  float f = entity == this ? 0.35F : 0.4F;
                  Vec3d vec3d1 = entity.getMotion();
                  float f1 = MathHelper.sqrt(vec3d1.x * vec3d1.x * (double)0.2F + vec3d1.y * vec3d1.y + vec3d1.z * vec3d1.z * (double)0.2F) * f;
                  if (f1 > 1.0F) {
                     f1 = 1.0F;
                  }

                  this.playSwimSound(f1);
               } else {
                  this.playStepSound(blockpos, blockstate);
               }
            } else if (this.distanceWalkedOnStepModified > this.nextFlap && this.makeFlySound() && blockstate.isAir(this.world, blockpos)) {
               this.nextFlap = this.playFlySound(this.distanceWalkedOnStepModified);
            }
         }

         try {
            this.inLava = false;
            this.doBlockCollisions();
         } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Checking entity block collision");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
            this.fillCrashReport(crashreportcategory);
            throw new ReportedException(crashreport);
         }

         boolean flag = this.isInWaterRainOrBubbleColumn();
         if (this.world.isFlammableWithin(this.getBoundingBox().shrink(0.001D))) {
            if (!flag) {
               ++this.fire;
               if (this.fire == 0) {
                  this.setFire(8);
               }
            }

            this.dealFireDamage(1);
         } else if (this.fire <= 0) {
            this.fire = -this.getFireImmuneTicks();
         }

         if (flag && this.isBurning()) {
            this.playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 0.7F, 1.6F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
            this.fire = -this.getFireImmuneTicks();
         }

         this.world.getProfiler().endSection();
      }
   }

   protected Vec3d handleSneakMovement(Vec3d pos, MoverType typeIn) {
      if (this instanceof PlayerEntity && (typeIn == MoverType.SELF || typeIn == MoverType.PLAYER) && this.onGround && this.isSneaking()) {
         double d0 = pos.x;
         double d1 = pos.z;
         double d2 = 0.05D;

         while(d0 != 0.0D && this.world.isCollisionBoxesEmpty(this, this.getBoundingBox().offset(d0, (double)(-this.stepHeight), 0.0D))) {
            if (d0 < 0.05D && d0 >= -0.05D) {
               d0 = 0.0D;
            } else if (d0 > 0.0D) {
               d0 -= 0.05D;
            } else {
               d0 += 0.05D;
            }
         }

         while(d1 != 0.0D && this.world.isCollisionBoxesEmpty(this, this.getBoundingBox().offset(0.0D, (double)(-this.stepHeight), d1))) {
            if (d1 < 0.05D && d1 >= -0.05D) {
               d1 = 0.0D;
            } else if (d1 > 0.0D) {
               d1 -= 0.05D;
            } else {
               d1 += 0.05D;
            }
         }

         while(d0 != 0.0D && d1 != 0.0D && this.world.isCollisionBoxesEmpty(this, this.getBoundingBox().offset(d0, (double)(-this.stepHeight), d1))) {
            if (d0 < 0.05D && d0 >= -0.05D) {
               d0 = 0.0D;
            } else if (d0 > 0.0D) {
               d0 -= 0.05D;
            } else {
               d0 += 0.05D;
            }

            if (d1 < 0.05D && d1 >= -0.05D) {
               d1 = 0.0D;
            } else if (d1 > 0.0D) {
               d1 -= 0.05D;
            } else {
               d1 += 0.05D;
            }
         }

         pos = new Vec3d(d0, pos.y, d1);
      }

      return pos;
   }

   protected Vec3d handlePistonMovement(Vec3d pos) {
      if (pos.lengthSquared() <= 1.0E-7D) {
         return pos;
      } else {
         long i = this.world.getGameTime();
         if (i != this.pistonDeltasGameTime) {
            Arrays.fill(this.pistonDeltas, 0.0D);
            this.pistonDeltasGameTime = i;
         }

         if (pos.x != 0.0D) {
            double d2 = this.calculatePistonDeltas(Direction.Axis.X, pos.x);
            return Math.abs(d2) <= (double)1.0E-5F ? Vec3d.ZERO : new Vec3d(d2, 0.0D, 0.0D);
         } else if (pos.y != 0.0D) {
            double d1 = this.calculatePistonDeltas(Direction.Axis.Y, pos.y);
            return Math.abs(d1) <= (double)1.0E-5F ? Vec3d.ZERO : new Vec3d(0.0D, d1, 0.0D);
         } else if (pos.z != 0.0D) {
            double d0 = this.calculatePistonDeltas(Direction.Axis.Z, pos.z);
            return Math.abs(d0) <= (double)1.0E-5F ? Vec3d.ZERO : new Vec3d(0.0D, 0.0D, d0);
         } else {
            return Vec3d.ZERO;
         }
      }
   }

   private double calculatePistonDeltas(Direction.Axis axis, double distance) {
      int i = axis.ordinal();
      double d0 = MathHelper.clamp(distance + this.pistonDeltas[i], -0.51D, 0.51D);
      distance = d0 - this.pistonDeltas[i];
      this.pistonDeltas[i] = d0;
      return distance;
   }

   private Vec3d getAllowedMovement(Vec3d vec) {
      AxisAlignedBB axisalignedbb = this.getBoundingBox();
      ISelectionContext iselectioncontext = ISelectionContext.forEntity(this);
      VoxelShape voxelshape = this.world.getWorldBorder().getShape();
      Stream<VoxelShape> stream = VoxelShapes.compare(voxelshape, VoxelShapes.create(axisalignedbb.shrink(1.0E-7D)), IBooleanFunction.AND) ? Stream.empty() : Stream.of(voxelshape);
      Stream<VoxelShape> stream1 = this.world.getEmptyCollisionShapes(this, axisalignedbb.expand(vec), ImmutableSet.of());
      ReuseableStream<VoxelShape> reuseablestream = new ReuseableStream<>(Stream.concat(stream1, stream));
      Vec3d vec3d = vec.lengthSquared() == 0.0D ? vec : collideBoundingBoxHeuristically(this, vec, axisalignedbb, this.world, iselectioncontext, reuseablestream);
      boolean flag = vec.x != vec3d.x;
      boolean flag1 = vec.y != vec3d.y;
      boolean flag2 = vec.z != vec3d.z;
      boolean flag3 = this.onGround || flag1 && vec.y < 0.0D;
      if (this.stepHeight > 0.0F && flag3 && (flag || flag2)) {
         Vec3d vec3d1 = collideBoundingBoxHeuristically(this, new Vec3d(vec.x, (double)this.stepHeight, vec.z), axisalignedbb, this.world, iselectioncontext, reuseablestream);
         Vec3d vec3d2 = collideBoundingBoxHeuristically(this, new Vec3d(0.0D, (double)this.stepHeight, 0.0D), axisalignedbb.expand(vec.x, 0.0D, vec.z), this.world, iselectioncontext, reuseablestream);
         if (vec3d2.y < (double)this.stepHeight) {
            Vec3d vec3d3 = collideBoundingBoxHeuristically(this, new Vec3d(vec.x, 0.0D, vec.z), axisalignedbb.offset(vec3d2), this.world, iselectioncontext, reuseablestream).add(vec3d2);
            if (horizontalMag(vec3d3) > horizontalMag(vec3d1)) {
               vec3d1 = vec3d3;
            }
         }

         if (horizontalMag(vec3d1) > horizontalMag(vec3d)) {
            return vec3d1.add(collideBoundingBoxHeuristically(this, new Vec3d(0.0D, -vec3d1.y + vec.y, 0.0D), axisalignedbb.offset(vec3d1), this.world, iselectioncontext, reuseablestream));
         }
      }

      return vec3d;
   }

   public static double horizontalMag(Vec3d vec) {
      return vec.x * vec.x + vec.z * vec.z;
   }

   public static Vec3d collideBoundingBoxHeuristically(@Nullable Entity p_223307_0_, Vec3d p_223307_1_, AxisAlignedBB p_223307_2_, World p_223307_3_, ISelectionContext p_223307_4_, ReuseableStream<VoxelShape> p_223307_5_) {
      boolean flag = p_223307_1_.x == 0.0D;
      boolean flag1 = p_223307_1_.y == 0.0D;
      boolean flag2 = p_223307_1_.z == 0.0D;
      if ((!flag || !flag1) && (!flag || !flag2) && (!flag1 || !flag2)) {
         ReuseableStream<VoxelShape> reuseablestream = new ReuseableStream<>(Stream.concat(p_223307_5_.createStream(), p_223307_3_.getCollisionShapes(p_223307_0_, p_223307_2_.expand(p_223307_1_))));
         return collideBoundingBox(p_223307_1_, p_223307_2_, reuseablestream);
      } else {
         return getAllowedMovement(p_223307_1_, p_223307_2_, p_223307_3_, p_223307_4_, p_223307_5_);
      }
   }

   public static Vec3d collideBoundingBox(Vec3d p_223310_0_, AxisAlignedBB p_223310_1_, ReuseableStream<VoxelShape> p_223310_2_) {
      double d0 = p_223310_0_.x;
      double d1 = p_223310_0_.y;
      double d2 = p_223310_0_.z;
      if (d1 != 0.0D) {
         d1 = VoxelShapes.getAllowedOffset(Direction.Axis.Y, p_223310_1_, p_223310_2_.createStream(), d1);
         if (d1 != 0.0D) {
            p_223310_1_ = p_223310_1_.offset(0.0D, d1, 0.0D);
         }
      }

      boolean flag = Math.abs(d0) < Math.abs(d2);
      if (flag && d2 != 0.0D) {
         d2 = VoxelShapes.getAllowedOffset(Direction.Axis.Z, p_223310_1_, p_223310_2_.createStream(), d2);
         if (d2 != 0.0D) {
            p_223310_1_ = p_223310_1_.offset(0.0D, 0.0D, d2);
         }
      }

      if (d0 != 0.0D) {
         d0 = VoxelShapes.getAllowedOffset(Direction.Axis.X, p_223310_1_, p_223310_2_.createStream(), d0);
         if (!flag && d0 != 0.0D) {
            p_223310_1_ = p_223310_1_.offset(d0, 0.0D, 0.0D);
         }
      }

      if (!flag && d2 != 0.0D) {
         d2 = VoxelShapes.getAllowedOffset(Direction.Axis.Z, p_223310_1_, p_223310_2_.createStream(), d2);
      }

      return new Vec3d(d0, d1, d2);
   }

   public static Vec3d getAllowedMovement(Vec3d vec, AxisAlignedBB collisionBox, IWorldReader worldIn, ISelectionContext selectionContext, ReuseableStream<VoxelShape> potentialHits) {
      double d0 = vec.x;
      double d1 = vec.y;
      double d2 = vec.z;
      if (d1 != 0.0D) {
         d1 = VoxelShapes.getAllowedOffset(Direction.Axis.Y, collisionBox, worldIn, d1, selectionContext, potentialHits.createStream());
         if (d1 != 0.0D) {
            collisionBox = collisionBox.offset(0.0D, d1, 0.0D);
         }
      }

      boolean flag = Math.abs(d0) < Math.abs(d2);
      if (flag && d2 != 0.0D) {
         d2 = VoxelShapes.getAllowedOffset(Direction.Axis.Z, collisionBox, worldIn, d2, selectionContext, potentialHits.createStream());
         if (d2 != 0.0D) {
            collisionBox = collisionBox.offset(0.0D, 0.0D, d2);
         }
      }

      if (d0 != 0.0D) {
         d0 = VoxelShapes.getAllowedOffset(Direction.Axis.X, collisionBox, worldIn, d0, selectionContext, potentialHits.createStream());
         if (!flag && d0 != 0.0D) {
            collisionBox = collisionBox.offset(d0, 0.0D, 0.0D);
         }
      }

      if (!flag && d2 != 0.0D) {
         d2 = VoxelShapes.getAllowedOffset(Direction.Axis.Z, collisionBox, worldIn, d2, selectionContext, potentialHits.createStream());
      }

      return new Vec3d(d0, d1, d2);
   }

   protected float determineNextStepDistance() {
      return (float)((int)this.distanceWalkedOnStepModified + 1);
   }

   public void resetPositionToBB() {
      AxisAlignedBB axisalignedbb = this.getBoundingBox();
      this.posX = (axisalignedbb.minX + axisalignedbb.maxX) / 2.0D;
      this.posY = axisalignedbb.minY;
      this.posZ = (axisalignedbb.minZ + axisalignedbb.maxZ) / 2.0D;
      if (this.isAddedToWorld() && !this.world.isRemote && world instanceof ServerWorld) ((ServerWorld)this.world).chunkCheck(this); // Forge - Process chunk registration after moving.
   }

   protected SoundEvent getSwimSound() {
      return SoundEvents.ENTITY_GENERIC_SWIM;
   }

   protected SoundEvent getSplashSound() {
      return SoundEvents.ENTITY_GENERIC_SPLASH;
   }

   protected SoundEvent getHighspeedSplashSound() {
      return SoundEvents.ENTITY_GENERIC_SPLASH;
   }

   protected void doBlockCollisions() {
      AxisAlignedBB axisalignedbb = this.getBoundingBox();

      try (
         BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain(axisalignedbb.minX + 0.001D, axisalignedbb.minY + 0.001D, axisalignedbb.minZ + 0.001D);
         BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos1 = BlockPos.PooledMutableBlockPos.retain(axisalignedbb.maxX - 0.001D, axisalignedbb.maxY - 0.001D, axisalignedbb.maxZ - 0.001D);
         BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos2 = BlockPos.PooledMutableBlockPos.retain();
      ) {
         if (this.world.isAreaLoaded(blockpos$pooledmutableblockpos, blockpos$pooledmutableblockpos1)) {
            for(int i = blockpos$pooledmutableblockpos.getX(); i <= blockpos$pooledmutableblockpos1.getX(); ++i) {
               for(int j = blockpos$pooledmutableblockpos.getY(); j <= blockpos$pooledmutableblockpos1.getY(); ++j) {
                  for(int k = blockpos$pooledmutableblockpos.getZ(); k <= blockpos$pooledmutableblockpos1.getZ(); ++k) {
                     blockpos$pooledmutableblockpos2.setPos(i, j, k);
                     BlockState blockstate = this.world.getBlockState(blockpos$pooledmutableblockpos2);

                     try {
                        blockstate.onEntityCollision(this.world, blockpos$pooledmutableblockpos2, this);
                        this.onInsideBlock(blockstate);
                     } catch (Throwable throwable) {
                        CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Colliding entity with block");
                        CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being collided with");
                        CrashReportCategory.addBlockInfo(crashreportcategory, blockpos$pooledmutableblockpos2, blockstate);
                        throw new ReportedException(crashreport);
                     }
                  }
               }
            }
         }
      }

   }

   protected void onInsideBlock(BlockState p_191955_1_) {
   }

   protected void playStepSound(BlockPos pos, BlockState blockIn) {
      if (!blockIn.getMaterial().isLiquid()) {
         BlockState blockstate = this.world.getBlockState(pos.up());
         SoundType soundtype = blockstate.getBlock() == Blocks.SNOW ? blockstate.getSoundType(world, pos, this) : blockIn.getSoundType(world, pos, this);
         this.playSound(soundtype.getStepSound(), soundtype.getVolume() * 0.15F, soundtype.getPitch());
      }
   }

   protected void playSwimSound(float volume) {
      this.playSound(this.getSwimSound(), volume, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
   }

   protected float playFlySound(float volume) {
      return 0.0F;
   }

   protected boolean makeFlySound() {
      return false;
   }

   public void playSound(SoundEvent soundIn, float volume, float pitch) {
      if (!this.isSilent()) {
         this.world.playSound((PlayerEntity)null, this.posX, this.posY, this.posZ, soundIn, this.getSoundCategory(), volume, pitch);
      }

   }

   public boolean isSilent() {
      return this.dataManager.get(SILENT);
   }

   public void setSilent(boolean isSilent) {
      this.dataManager.set(SILENT, isSilent);
   }

   public boolean hasNoGravity() {
      return this.dataManager.get(NO_GRAVITY);
   }

   public void setNoGravity(boolean noGravity) {
      this.dataManager.set(NO_GRAVITY, noGravity);
   }

   protected boolean canTriggerWalking() {
      return true;
   }

   protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
      if (onGroundIn) {
         if (this.fallDistance > 0.0F) {
            state.getBlock().onFallenUpon(this.world, pos, this, this.fallDistance);
         }

         this.fallDistance = 0.0F;
      } else if (y < 0.0D) {
         this.fallDistance = (float)((double)this.fallDistance - y);
      }

   }

   @Nullable
   public AxisAlignedBB getCollisionBoundingBox() {
      return null;
   }

   protected void dealFireDamage(int amount) {
      if (!this.isImmuneToFire()) {
         this.attackEntityFrom(DamageSource.IN_FIRE, (float)amount);
      }

   }

   public final boolean isImmuneToFire() {
      return this.getType().isImmuneToFire();
   }

   public void fall(float distance, float damageMultiplier) {
      if (this.isBeingRidden()) {
         for(Entity entity : this.getPassengers()) {
            entity.fall(distance, damageMultiplier);
         }
      }

   }

   public boolean isInWater() {
      return this.inWater;
   }

   private boolean isInRain() {
      boolean flag;
      try (BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain(this)) {
         flag = this.world.isRainingAt(blockpos$pooledmutableblockpos) || this.world.isRainingAt(blockpos$pooledmutableblockpos.setPos(this.posX, this.posY + (double)this.size.height, this.posZ));
      }

      return flag;
   }

   private boolean isInBubbleColumn() {
      return this.world.getBlockState(new BlockPos(this)).getBlock() == Blocks.BUBBLE_COLUMN;
   }

   public boolean isWet() {
      return this.isInWater() || this.isInRain();
   }

   public boolean isInWaterRainOrBubbleColumn() {
      return this.isInWater() || this.isInRain() || this.isInBubbleColumn();
   }

   public boolean isInWaterOrBubbleColumn() {
      return this.isInWater() || this.isInBubbleColumn();
   }

   public boolean canSwim() {
      return this.eyesInWater && this.isInWater();
   }

   private void updateAquatics() {
      this.handleWaterMovement();
      this.updateEyesInWater();
      this.updateSwimming();
   }

   public void updateSwimming() {
      if (this.isSwimming()) {
         this.setSwimming(this.isSprinting() && this.isInWater() && !this.isPassenger());
      } else {
         this.setSwimming(this.isSprinting() && this.canSwim() && !this.isPassenger());
      }

   }

   public boolean handleWaterMovement() {
      if (this.getRidingEntity() instanceof BoatEntity) {
         this.inWater = false;
      } else if (this.handleFluidAcceleration(FluidTags.WATER)) {
         if (!this.inWater && !this.firstUpdate) {
            this.doWaterSplashEffect();
         }

         this.fallDistance = 0.0F;
         this.inWater = true;
         this.extinguish();
      } else {
         this.inWater = false;
      }

      return this.inWater;
   }

   private void updateEyesInWater() {
      this.eyesInWater = this.areEyesInFluid(FluidTags.WATER, true);
   }

   protected void doWaterSplashEffect() {
      Entity entity = this.isBeingRidden() && this.getControllingPassenger() != null ? this.getControllingPassenger() : this;
      float f = entity == this ? 0.2F : 0.9F;
      Vec3d vec3d = entity.getMotion();
      float f1 = MathHelper.sqrt(vec3d.x * vec3d.x * (double)0.2F + vec3d.y * vec3d.y + vec3d.z * vec3d.z * (double)0.2F) * f;
      if (f1 > 1.0F) {
         f1 = 1.0F;
      }

      if ((double)f1 < 0.25D) {
         this.playSound(this.getSplashSound(), f1, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
      } else {
         this.playSound(this.getHighspeedSplashSound(), f1, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
      }

      float f2 = (float)MathHelper.floor(this.getBoundingBox().minY);

      for(int i = 0; (float)i < 1.0F + this.size.width * 20.0F; ++i) {
         float f3 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.size.width;
         float f4 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.size.width;
         this.world.addParticle(ParticleTypes.BUBBLE, this.posX + (double)f3, (double)(f2 + 1.0F), this.posZ + (double)f4, vec3d.x, vec3d.y - (double)(this.rand.nextFloat() * 0.2F), vec3d.z);
      }

      for(int j = 0; (float)j < 1.0F + this.size.width * 20.0F; ++j) {
         float f5 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.size.width;
         float f6 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.size.width;
         this.world.addParticle(ParticleTypes.SPLASH, this.posX + (double)f5, (double)(f2 + 1.0F), this.posZ + (double)f6, vec3d.x, vec3d.y, vec3d.z);
      }

   }

   public void spawnRunningParticles() {
      if (this.isSprinting() && !this.isInWater()) {
         this.createRunningParticles();
      }

   }

   protected void createRunningParticles() {
      int i = MathHelper.floor(this.posX);
      int j = MathHelper.floor(this.posY - (double)0.2F);
      int k = MathHelper.floor(this.posZ);
      BlockPos blockpos = new BlockPos(i, j, k);
      BlockState blockstate = this.world.getBlockState(blockpos);
      if (!blockstate.addRunningEffects(world, blockpos, this))
      if (blockstate.getRenderType() != BlockRenderType.INVISIBLE) {
         Vec3d vec3d = this.getMotion();
         this.world.addParticle(new BlockParticleData(ParticleTypes.BLOCK, blockstate).setPos(blockpos), this.posX + ((double)this.rand.nextFloat() - 0.5D) * (double)this.size.width, this.posY + 0.1D, this.posZ + ((double)this.rand.nextFloat() - 0.5D) * (double)this.size.width, vec3d.x * -4.0D, 1.5D, vec3d.z * -4.0D);
      }

   }

   public boolean areEyesInFluid(Tag<Fluid> tagIn) {
      return this.areEyesInFluid(tagIn, false);
   }

   public boolean areEyesInFluid(Tag<Fluid> p_213290_1_, boolean checkChunkLoaded) {
      if (this.getRidingEntity() instanceof BoatEntity) {
         return false;
      } else {
         double d0 = this.posY + (double)this.getEyeHeight();
         BlockPos blockpos = new BlockPos(this.posX, d0, this.posZ);
         if (checkChunkLoaded && !this.world.chunkExists(blockpos.getX() >> 4, blockpos.getZ() >> 4)) {
            return false;
         } else {
            IFluidState ifluidstate = this.world.getFluidState(blockpos);
            return ifluidstate.isEntityInside(world, blockpos, this, d0, p_213290_1_, true);
         }
      }
   }

   public void setInLava() {
      this.inLava = true;
   }

   public boolean isInLava() {
      return this.inLava;
   }

   public void moveRelative(float p_213309_1_, Vec3d relative) {
      Vec3d vec3d = getAbsoluteMotion(relative, p_213309_1_, this.rotationYaw);
      this.setMotion(this.getMotion().add(vec3d));
   }

   protected static Vec3d getAbsoluteMotion(Vec3d relative, float p_213299_1_, float facing) {
      double d0 = relative.lengthSquared();
      if (d0 < 1.0E-7D) {
         return Vec3d.ZERO;
      } else {
         Vec3d vec3d = (d0 > 1.0D ? relative.normalize() : relative).scale((double)p_213299_1_);
         float f = MathHelper.sin(facing * ((float)Math.PI / 180F));
         float f1 = MathHelper.cos(facing * ((float)Math.PI / 180F));
         return new Vec3d(vec3d.x * (double)f1 - vec3d.z * (double)f, vec3d.y, vec3d.z * (double)f1 + vec3d.x * (double)f);
      }
   }

   @OnlyIn(Dist.CLIENT)
   public int getBrightnessForRender() {
      BlockPos blockpos = new BlockPos(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ);
      return this.world.isBlockLoaded(blockpos) ? this.world.getCombinedLight(blockpos, 0) : 0;
   }

   public float getBrightness() {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(this.posX, 0.0D, this.posZ);
      if (this.world.isBlockLoaded(blockpos$mutableblockpos)) {
         blockpos$mutableblockpos.setY(MathHelper.floor(this.posY + (double)this.getEyeHeight()));
         return this.world.getBrightness(blockpos$mutableblockpos);
      } else {
         return 0.0F;
      }
   }

   public void setWorld(World worldIn) {
      this.world = worldIn;
   }

   public void setPositionAndRotation(double x, double y, double z, float yaw, float pitch) {
      this.posX = MathHelper.clamp(x, -3.0E7D, 3.0E7D);
      this.posY = y;
      this.posZ = MathHelper.clamp(z, -3.0E7D, 3.0E7D);
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      pitch = MathHelper.clamp(pitch, -90.0F, 90.0F);
      this.rotationYaw = yaw;
      this.rotationPitch = pitch;
      this.prevRotationYaw = this.rotationYaw;
      this.prevRotationPitch = this.rotationPitch;
      double d0 = (double)(this.prevRotationYaw - yaw);
      if (d0 < -180.0D) {
         this.prevRotationYaw += 360.0F;
      }

      if (d0 >= 180.0D) {
         this.prevRotationYaw -= 360.0F;
      }

      if (!this.world.isRemote && this.isAlive()) this.world.getChunk((int) Math.floor(this.posX) >> 4, (int) Math.floor(this.posZ) >> 4); // Forge - ensure target chunk is loaded.
      this.setPosition(this.posX, this.posY, this.posZ);
      this.setRotation(yaw, pitch);
   }

   public void moveToBlockPosAndAngles(BlockPos pos, float rotationYawIn, float rotationPitchIn) {
      this.setLocationAndAngles((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, rotationYawIn, rotationPitchIn);
   }

   public void setLocationAndAngles(double x, double y, double z, float yaw, float pitch) {
      this.posX = x;
      this.posY = y;
      this.posZ = z;
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      this.lastTickPosX = this.posX;
      this.lastTickPosY = this.posY;
      this.lastTickPosZ = this.posZ;
      this.rotationYaw = yaw;
      this.rotationPitch = pitch;
      this.setPosition(this.posX, this.posY, this.posZ);
   }

   public float getDistance(Entity entityIn) {
      float f = (float)(this.posX - entityIn.posX);
      float f1 = (float)(this.posY - entityIn.posY);
      float f2 = (float)(this.posZ - entityIn.posZ);
      return MathHelper.sqrt(f * f + f1 * f1 + f2 * f2);
   }

   public double getDistanceSq(double x, double y, double z) {
      double d0 = this.posX - x;
      double d1 = this.posY - y;
      double d2 = this.posZ - z;
      return d0 * d0 + d1 * d1 + d2 * d2;
   }

   public double getDistanceSq(Entity entityIn) {
      return this.getDistanceSq(entityIn.getPositionVec());
   }

   public double getDistanceSq(Vec3d p_195048_1_) {
      double d0 = this.posX - p_195048_1_.x;
      double d1 = this.posY - p_195048_1_.y;
      double d2 = this.posZ - p_195048_1_.z;
      return d0 * d0 + d1 * d1 + d2 * d2;
   }

   public void onCollideWithPlayer(PlayerEntity entityIn) {
   }

   public void applyEntityCollision(Entity entityIn) {
      if (!this.isRidingSameEntity(entityIn)) {
         if (!entityIn.noClip && !this.noClip) {
            double d0 = entityIn.posX - this.posX;
            double d1 = entityIn.posZ - this.posZ;
            double d2 = MathHelper.absMax(d0, d1);
            if (d2 >= (double)0.01F) {
               d2 = (double)MathHelper.sqrt(d2);
               d0 = d0 / d2;
               d1 = d1 / d2;
               double d3 = 1.0D / d2;
               if (d3 > 1.0D) {
                  d3 = 1.0D;
               }

               d0 = d0 * d3;
               d1 = d1 * d3;
               d0 = d0 * (double)0.05F;
               d1 = d1 * (double)0.05F;
               d0 = d0 * (double)(1.0F - this.entityCollisionReduction);
               d1 = d1 * (double)(1.0F - this.entityCollisionReduction);
               if (!this.isBeingRidden()) {
                  this.addVelocity(-d0, 0.0D, -d1);
               }

               if (!entityIn.isBeingRidden()) {
                  entityIn.addVelocity(d0, 0.0D, d1);
               }
            }

         }
      }
   }

   public void addVelocity(double x, double y, double z) {
      this.setMotion(this.getMotion().add(x, y, z));
      this.isAirBorne = true;
   }

   protected void markVelocityChanged() {
      this.velocityChanged = true;
   }

   public boolean attackEntityFrom(DamageSource source, float amount) {
      if (this.isInvulnerableTo(source)) {
         return false;
      } else {
         this.markVelocityChanged();
         return false;
      }
   }

   public final Vec3d getLook(float partialTicks) {
      return this.getVectorForRotation(this.getPitch(partialTicks), this.getYaw(partialTicks));
   }

   public float getPitch(float partialTicks) {
      return partialTicks == 1.0F ? this.rotationPitch : MathHelper.lerp(partialTicks, this.prevRotationPitch, this.rotationPitch);
   }

   public float getYaw(float partialTicks) {
      return partialTicks == 1.0F ? this.rotationYaw : MathHelper.lerp(partialTicks, this.prevRotationYaw, this.rotationYaw);
   }

   protected final Vec3d getVectorForRotation(float pitch, float yaw) {
      float f = pitch * ((float)Math.PI / 180F);
      float f1 = -yaw * ((float)Math.PI / 180F);
      float f2 = MathHelper.cos(f1);
      float f3 = MathHelper.sin(f1);
      float f4 = MathHelper.cos(f);
      float f5 = MathHelper.sin(f);
      return new Vec3d((double)(f3 * f4), (double)(-f5), (double)(f2 * f4));
   }

   public final Vec3d getUpVector(float partialTicks) {
      return this.calculateUpVector(this.getPitch(partialTicks), this.getYaw(partialTicks));
   }

   protected final Vec3d calculateUpVector(float p_213320_1_, float p_213320_2_) {
      return this.getVectorForRotation(p_213320_1_ - 90.0F, p_213320_2_);
   }

   public Vec3d getEyePosition(float partialTicks) {
      if (partialTicks == 1.0F) {
         return new Vec3d(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ);
      } else {
         double d0 = MathHelper.lerp((double)partialTicks, this.prevPosX, this.posX);
         double d1 = MathHelper.lerp((double)partialTicks, this.prevPosY, this.posY) + (double)this.getEyeHeight();
         double d2 = MathHelper.lerp((double)partialTicks, this.prevPosZ, this.posZ);
         return new Vec3d(d0, d1, d2);
      }
   }

   @OnlyIn(Dist.CLIENT)
   public RayTraceResult pick(double p_213324_1_, float p_213324_3_, boolean p_213324_4_) {
      Vec3d vec3d = this.getEyePosition(p_213324_3_);
      Vec3d vec3d1 = this.getLook(p_213324_3_);
      Vec3d vec3d2 = vec3d.add(vec3d1.x * p_213324_1_, vec3d1.y * p_213324_1_, vec3d1.z * p_213324_1_);
      return this.world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d2, RayTraceContext.BlockMode.OUTLINE, p_213324_4_ ? RayTraceContext.FluidMode.ANY : RayTraceContext.FluidMode.NONE, this));
   }

   public boolean canBeCollidedWith() {
      return false;
   }

   public boolean canBePushed() {
      return false;
   }

   public void awardKillScore(Entity p_191956_1_, int p_191956_2_, DamageSource p_191956_3_) {
      if (p_191956_1_ instanceof ServerPlayerEntity) {
         CriteriaTriggers.ENTITY_KILLED_PLAYER.trigger((ServerPlayerEntity)p_191956_1_, this, p_191956_3_);
      }

   }

   @OnlyIn(Dist.CLIENT)
   public boolean isInRangeToRender3d(double x, double y, double z) {
      double d0 = this.posX - x;
      double d1 = this.posY - y;
      double d2 = this.posZ - z;
      double d3 = d0 * d0 + d1 * d1 + d2 * d2;
      return this.isInRangeToRenderDist(d3);
   }

   @OnlyIn(Dist.CLIENT)
   public boolean isInRangeToRenderDist(double distance) {
      double d0 = this.getBoundingBox().getAverageEdgeLength();
      if (Double.isNaN(d0)) {
         d0 = 1.0D;
      }

      d0 = d0 * 64.0D * renderDistanceWeight;
      return distance < d0 * d0;
   }

   public boolean writeUnlessRemoved(CompoundNBT compound) {
      String s = this.getEntityString();
      if (!this.removed && s != null) {
         compound.putString("id", s);
         this.writeWithoutTypeId(compound);
         return true;
      } else {
         return false;
      }
   }

   public boolean writeUnlessPassenger(CompoundNBT compound) {
      return this.isPassenger() ? false : this.writeUnlessRemoved(compound);
   }

   public CompoundNBT writeWithoutTypeId(CompoundNBT compound) {
      try {
         compound.put("Pos", this.newDoubleNBTList(this.posX, this.posY, this.posZ));
         Vec3d vec3d = this.getMotion();
         compound.put("Motion", this.newDoubleNBTList(vec3d.x, vec3d.y, vec3d.z));
         compound.put("Rotation", this.newFloatNBTList(this.rotationYaw, this.rotationPitch));
         compound.putFloat("FallDistance", this.fallDistance);
         compound.putShort("Fire", (short)this.fire);
         compound.putShort("Air", (short)this.getAir());
         compound.putBoolean("OnGround", this.onGround);
         compound.putInt("Dimension", this.dimension.getId());
         compound.putBoolean("Invulnerable", this.invulnerable);
         compound.putInt("PortalCooldown", this.timeUntilPortal);
         compound.putUniqueId("UUID", this.getUniqueID());
         ITextComponent itextcomponent = this.getCustomName();
         if (itextcomponent != null) {
            compound.putString("CustomName", ITextComponent.Serializer.toJson(itextcomponent));
         }

         if (this.isCustomNameVisible()) {
            compound.putBoolean("CustomNameVisible", this.isCustomNameVisible());
         }

         if (this.isSilent()) {
            compound.putBoolean("Silent", this.isSilent());
         }

         if (this.hasNoGravity()) {
            compound.putBoolean("NoGravity", this.hasNoGravity());
         }

         if (this.glowing) {
            compound.putBoolean("Glowing", this.glowing);
         }
         compound.putBoolean("CanUpdate", canUpdate);

         if (!this.tags.isEmpty()) {
            ListNBT listnbt = new ListNBT();

            for(String s : this.tags) {
               listnbt.add(new StringNBT(s));
            }

            compound.put("Tags", listnbt);
         }

         CompoundNBT caps = serializeCaps();
         if (caps != null) compound.put("ForgeCaps", caps);
         if (persistentData != null) compound.put("ForgeData", persistentData);

         this.writeAdditional(compound);
         if (this.isBeingRidden()) {
            ListNBT listnbt1 = new ListNBT();

            for(Entity entity : this.getPassengers()) {
               CompoundNBT compoundnbt = new CompoundNBT();
               if (entity.writeUnlessRemoved(compoundnbt)) {
                  listnbt1.add(compoundnbt);
               }
            }

            if (!listnbt1.isEmpty()) {
               compound.put("Passengers", listnbt1);
            }
         }

         return compound;
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Saving entity NBT");
         CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being saved");
         this.fillCrashReport(crashreportcategory);
         throw new ReportedException(crashreport);
      }
   }

   public void read(CompoundNBT compound) {
      try {
         ListNBT listnbt = compound.getList("Pos", 6);
         ListNBT listnbt2 = compound.getList("Motion", 6);
         ListNBT listnbt3 = compound.getList("Rotation", 5);
         double d0 = listnbt2.getDouble(0);
         double d1 = listnbt2.getDouble(1);
         double d2 = listnbt2.getDouble(2);
         this.setMotion(Math.abs(d0) > 10.0D ? 0.0D : d0, Math.abs(d1) > 10.0D ? 0.0D : d1, Math.abs(d2) > 10.0D ? 0.0D : d2);
         this.posX = listnbt.getDouble(0);
         this.posY = listnbt.getDouble(1);
         this.posZ = listnbt.getDouble(2);
         this.lastTickPosX = this.posX;
         this.lastTickPosY = this.posY;
         this.lastTickPosZ = this.posZ;
         this.prevPosX = this.posX;
         this.prevPosY = this.posY;
         this.prevPosZ = this.posZ;
         this.rotationYaw = listnbt3.getFloat(0);
         this.rotationPitch = listnbt3.getFloat(1);
         this.prevRotationYaw = this.rotationYaw;
         this.prevRotationPitch = this.rotationPitch;
         this.setRotationYawHead(this.rotationYaw);
         this.setRenderYawOffset(this.rotationYaw);
         this.fallDistance = compound.getFloat("FallDistance");
         this.fire = compound.getShort("Fire");
         this.setAir(compound.getShort("Air"));
         this.onGround = compound.getBoolean("OnGround");
         if (compound.contains("Dimension")) {
            this.dimension = DimensionType.getById(compound.getInt("Dimension"));
         }

         this.invulnerable = compound.getBoolean("Invulnerable");
         this.timeUntilPortal = compound.getInt("PortalCooldown");
         if (compound.hasUniqueId("UUID")) {
            this.entityUniqueID = compound.getUniqueId("UUID");
            this.cachedUniqueIdString = this.entityUniqueID.toString();
         }

         if (Double.isFinite(this.posX) && Double.isFinite(this.posY) && Double.isFinite(this.posZ)) {
            if (Double.isFinite((double)this.rotationYaw) && Double.isFinite((double)this.rotationPitch)) {
               this.setPosition(this.posX, this.posY, this.posZ);
               this.setRotation(this.rotationYaw, this.rotationPitch);
               if (compound.contains("CustomName", 8)) {
                  this.setCustomName(ITextComponent.Serializer.fromJson(compound.getString("CustomName")));
               }

               this.setCustomNameVisible(compound.getBoolean("CustomNameVisible"));
               this.setSilent(compound.getBoolean("Silent"));
               this.setNoGravity(compound.getBoolean("NoGravity"));
               this.setGlowing(compound.getBoolean("Glowing"));
               if (compound.contains("ForgeData", 10)) persistentData = compound.getCompound("ForgeData");
               if (compound.contains("CanUpdate", 99)) this.canUpdate(compound.getBoolean("CanUpdate"));
               if (compound.contains("ForgeCaps", 10)) deserializeCaps(compound.getCompound("ForgeCaps"));
               if (compound.contains("Tags", 9)) {
                  this.tags.clear();
                  ListNBT listnbt1 = compound.getList("Tags", 8);
                  int i = Math.min(listnbt1.size(), 1024);

                  for(int j = 0; j < i; ++j) {
                     this.tags.add(listnbt1.getString(j));
                  }
               }

               this.readAdditional(compound);
               if (this.shouldSetPosAfterLoading()) {
                  this.setPosition(this.posX, this.posY, this.posZ);
               }

            } else {
               throw new IllegalStateException("Entity has invalid rotation");
            }
         } else {
            throw new IllegalStateException("Entity has invalid position");
         }
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Loading entity NBT");
         CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being loaded");
         this.fillCrashReport(crashreportcategory);
         throw new ReportedException(crashreport);
      }
   }

   protected boolean shouldSetPosAfterLoading() {
      return true;
   }

   @Nullable
   public final String getEntityString() {
      EntityType<?> entitytype = this.getType();
      ResourceLocation resourcelocation = EntityType.getKey(entitytype);
      return entitytype.isSerializable() && resourcelocation != null ? resourcelocation.toString() : null;
   }

   protected abstract void readAdditional(CompoundNBT compound);

   protected abstract void writeAdditional(CompoundNBT compound);

   protected ListNBT newDoubleNBTList(double... numbers) {
      ListNBT listnbt = new ListNBT();

      for(double d0 : numbers) {
         listnbt.add(new DoubleNBT(d0));
      }

      return listnbt;
   }

   protected ListNBT newFloatNBTList(float... numbers) {
      ListNBT listnbt = new ListNBT();

      for(float f : numbers) {
         listnbt.add(new FloatNBT(f));
      }

      return listnbt;
   }

   @Nullable
   public ItemEntity entityDropItem(IItemProvider itemIn) {
      return this.entityDropItem(itemIn, 0);
   }

   @Nullable
   public ItemEntity entityDropItem(IItemProvider itemIn, int offset) {
      return this.entityDropItem(new ItemStack(itemIn), (float)offset);
   }

   @Nullable
   public ItemEntity entityDropItem(ItemStack stack) {
      return this.entityDropItem(stack, 0.0F);
   }

   @Nullable
   public ItemEntity entityDropItem(ItemStack stack, float offsetY) {
      if (stack.isEmpty()) {
         return null;
      } else if (this.world.isRemote) {
         return null;
      } else {
         ItemEntity itementity = new ItemEntity(this.world, this.posX, this.posY + (double)offsetY, this.posZ, stack);
         itementity.setDefaultPickupDelay();
         if (captureDrops() != null) captureDrops().add(itementity);
         else
         this.world.addEntity(itementity);
         return itementity;
      }
   }

   public boolean isAlive() {
      return !this.removed;
   }

   public boolean isEntityInsideOpaqueBlock() {
      if (this.noClip) {
         return false;
      } else {
         try (BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain()) {
            for(int i = 0; i < 8; ++i) {
               int j = MathHelper.floor(this.posY + (double)(((float)((i >> 0) % 2) - 0.5F) * 0.1F) + (double)this.eyeHeight);
               int k = MathHelper.floor(this.posX + (double)(((float)((i >> 1) % 2) - 0.5F) * this.size.width * 0.8F));
               int l = MathHelper.floor(this.posZ + (double)(((float)((i >> 2) % 2) - 0.5F) * this.size.width * 0.8F));
               if (blockpos$pooledmutableblockpos.getX() != k || blockpos$pooledmutableblockpos.getY() != j || blockpos$pooledmutableblockpos.getZ() != l) {
                  blockpos$pooledmutableblockpos.setPos(k, j, l);
                  if (this.world.getBlockState(blockpos$pooledmutableblockpos).causesSuffocation(this.world, blockpos$pooledmutableblockpos)) {
                     boolean flag = true;
                     return flag;
                  }
               }
            }

            return false;
         }
      }
   }

   public boolean processInitialInteract(PlayerEntity player, Hand hand) {
      return false;
   }

   @Nullable
   public AxisAlignedBB getCollisionBox(Entity entityIn) {
      return null;
   }

   public void updateRidden() {
      this.setMotion(Vec3d.ZERO);
      if (canUpdate())
      this.tick();
      if (this.isPassenger()) {
         this.getRidingEntity().updatePassenger(this);
      }
   }

   public void updatePassenger(Entity passenger) {
      if (this.isPassenger(passenger)) {
         passenger.setPosition(this.posX, this.posY + this.getMountedYOffset() + passenger.getYOffset(), this.posZ);
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void applyOrientationToEntity(Entity entityToUpdate) {
   }

   public double getYOffset() {
      return 0.0D;
   }

   public double getMountedYOffset() {
      return (double)this.size.height * 0.75D;
   }

   public boolean startRiding(Entity entityIn) {
      return this.startRiding(entityIn, false);
   }

   @OnlyIn(Dist.CLIENT)
   public boolean isLiving() {
      return this instanceof LivingEntity;
   }

   public boolean startRiding(Entity entityIn, boolean force) {
      for(Entity entity = entityIn; entity.ridingEntity != null; entity = entity.ridingEntity) {
         if (entity.ridingEntity == this) {
            return false;
         }
      }

      if (!net.minecraftforge.event.ForgeEventFactory.canMountEntity(this, entityIn, true)) return false;
      if (force || this.canBeRidden(entityIn) && entityIn.canFitPassenger(this)) {
         if (this.isPassenger()) {
            this.stopRiding();
         }

         this.ridingEntity = entityIn;
         this.ridingEntity.addPassenger(this);
         return true;
      } else {
         return false;
      }
   }

   protected boolean canBeRidden(Entity entityIn) {
      return this.rideCooldown <= 0;
   }

   protected boolean isPoseClear(Pose p_213298_1_) {
      return this.world.isCollisionBoxesEmpty(this, this.getBoundingBox(p_213298_1_));
   }

   public void removePassengers() {
      for(int i = this.passengers.size() - 1; i >= 0; --i) {
         this.passengers.get(i).stopRiding();
      }

   }

   public void stopRiding() {
      if (this.ridingEntity != null) {
         Entity entity = this.ridingEntity;
         if (!net.minecraftforge.event.ForgeEventFactory.canMountEntity(this, entity, false)) return;
         this.ridingEntity = null;
         entity.removePassenger(this);
      }

   }

   protected void addPassenger(Entity passenger) {
      if (passenger.getRidingEntity() != this) {
         throw new IllegalStateException("Use x.startRiding(y), not y.addPassenger(x)");
      } else {
         if (!this.world.isRemote && passenger instanceof PlayerEntity && !(this.getControllingPassenger() instanceof PlayerEntity)) {
            this.passengers.add(0, passenger);
         } else {
            this.passengers.add(passenger);
         }

      }
   }

   protected void removePassenger(Entity passenger) {
      if (passenger.getRidingEntity() == this) {
         throw new IllegalStateException("Use x.stopRiding(y), not y.removePassenger(x)");
      } else {
         this.passengers.remove(passenger);
         passenger.rideCooldown = 60;
      }
   }

   protected boolean canFitPassenger(Entity passenger) {
      return this.getPassengers().size() < 1;
   }

   @OnlyIn(Dist.CLIENT)
   public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
      this.setPosition(x, y, z);
      this.setRotation(yaw, pitch);
   }

   @OnlyIn(Dist.CLIENT)
   public void setHeadRotation(float yaw, int pitch) {
      this.setRotationYawHead(yaw);
   }

   public float getCollisionBorderSize() {
      return 0.0F;
   }

   public Vec3d getLookVec() {
      return this.getVectorForRotation(this.rotationPitch, this.rotationYaw);
   }

   public Vec2f getPitchYaw() {
      return new Vec2f(this.rotationPitch, this.rotationYaw);
   }

   @OnlyIn(Dist.CLIENT)
   public Vec3d getForward() {
      return Vec3d.fromPitchYaw(this.getPitchYaw());
   }

   public void setPortal(BlockPos pos) {
      if (this.timeUntilPortal > 0) {
         this.timeUntilPortal = this.getPortalCooldown();
      } else {
         if (!this.world.isRemote && !pos.equals(this.lastPortalPos)) {
            this.lastPortalPos = new BlockPos(pos);
            BlockPattern.PatternHelper blockpattern$patternhelper = ((NetherPortalBlock)Blocks.NETHER_PORTAL).createPatternHelper(this.world, this.lastPortalPos);
            double d0 = blockpattern$patternhelper.getForwards().getAxis() == Direction.Axis.X ? (double)blockpattern$patternhelper.getFrontTopLeft().getZ() : (double)blockpattern$patternhelper.getFrontTopLeft().getX();
            double d1 = Math.abs(MathHelper.pct((blockpattern$patternhelper.getForwards().getAxis() == Direction.Axis.X ? this.posZ : this.posX) - (double)(blockpattern$patternhelper.getForwards().rotateY().getAxisDirection() == Direction.AxisDirection.NEGATIVE ? 1 : 0), d0, d0 - (double)blockpattern$patternhelper.getWidth()));
            double d2 = MathHelper.pct(this.posY - 1.0D, (double)blockpattern$patternhelper.getFrontTopLeft().getY(), (double)(blockpattern$patternhelper.getFrontTopLeft().getY() - blockpattern$patternhelper.getHeight()));
            this.lastPortalVec = new Vec3d(d1, d2, 0.0D);
            this.teleportDirection = blockpattern$patternhelper.getForwards();
         }

         this.inPortal = true;
      }
   }

   protected void updatePortal() {
      if (this.world instanceof ServerWorld) {
         int i = this.getMaxInPortalTime();
         if (this.inPortal) {
            if (this.world.getServer().getAllowNether() && !this.isPassenger() && this.portalCounter++ >= i) {
               this.world.getProfiler().startSection("portal");
               this.portalCounter = i;
               this.timeUntilPortal = this.getPortalCooldown();
               this.changeDimension(this.world.dimension.getType() == DimensionType.THE_NETHER ? DimensionType.OVERWORLD : DimensionType.THE_NETHER);
               this.world.getProfiler().endSection();
            }

            this.inPortal = false;
         } else {
            if (this.portalCounter > 0) {
               this.portalCounter -= 4;
            }

            if (this.portalCounter < 0) {
               this.portalCounter = 0;
            }
         }

         this.decrementTimeUntilPortal();
      }
   }

   public int getPortalCooldown() {
      return 300;
   }

   @OnlyIn(Dist.CLIENT)
   public void setVelocity(double x, double y, double z) {
      this.setMotion(x, y, z);
   }

   @OnlyIn(Dist.CLIENT)
   public void handleStatusUpdate(byte id) {
   }

   @OnlyIn(Dist.CLIENT)
   public void performHurtAnimation() {
   }

   public Iterable<ItemStack> getHeldEquipment() {
      return EMPTY_EQUIPMENT;
   }

   public Iterable<ItemStack> getArmorInventoryList() {
      return EMPTY_EQUIPMENT;
   }

   public Iterable<ItemStack> getEquipmentAndArmor() {
      return Iterables.concat(this.getHeldEquipment(), this.getArmorInventoryList());
   }

   public void setItemStackToSlot(EquipmentSlotType slotIn, ItemStack stack) {
   }

   public boolean isBurning() {
      boolean flag = this.world != null && this.world.isRemote;
      return !this.isImmuneToFire() && (this.fire > 0 || flag && this.getFlag(0));
   }

   public boolean isPassenger() {
      return this.getRidingEntity() != null;
   }

   public boolean isBeingRidden() {
      return !this.getPassengers().isEmpty();
   }

   @Deprecated //Forge: Use rider sensitive version
   public boolean canBeRiddenInWater() {
      return true;
   }

   public boolean isSneaking() {
      return this.getFlag(1);
   }

   @OnlyIn(Dist.CLIENT)
   public boolean shouldRenderSneaking() {
      return this.getPose() == Pose.SNEAKING;
   }

   public void setSneaking(boolean sneaking) {
      this.setFlag(1, sneaking);
   }

   public boolean isSprinting() {
      return this.getFlag(3);
   }

   public void setSprinting(boolean sprinting) {
      this.setFlag(3, sprinting);
   }

   public boolean isSwimming() {
      return this.getFlag(4);
   }

   public boolean isActualySwimming() {
      return this.getPose() == Pose.SWIMMING;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean isVisuallySwimming() {
      return this.isActualySwimming() && !this.isInWater();
   }

   public void setSwimming(boolean p_204711_1_) {
      this.setFlag(4, p_204711_1_);
   }

   public boolean isGlowing() {
      return this.glowing || this.world.isRemote && this.getFlag(6);
   }

   public void setGlowing(boolean glowingIn) {
      this.glowing = glowingIn;
      if (!this.world.isRemote) {
         this.setFlag(6, this.glowing);
      }

   }

   public boolean isInvisible() {
      return this.getFlag(5);
   }

   @OnlyIn(Dist.CLIENT)
   public boolean isInvisibleToPlayer(PlayerEntity player) {
      if (player.isSpectator()) {
         return false;
      } else {
         Team team = this.getTeam();
         return team != null && player != null && player.getTeam() == team && team.getSeeFriendlyInvisiblesEnabled() ? false : this.isInvisible();
      }
   }

   @Nullable
   public Team getTeam() {
      return this.world.getScoreboard().getPlayersTeam(this.getScoreboardName());
   }

   public boolean isOnSameTeam(Entity entityIn) {
      return this.isOnScoreboardTeam(entityIn.getTeam());
   }

   public boolean isOnScoreboardTeam(Team teamIn) {
      return this.getTeam() != null ? this.getTeam().isSameTeam(teamIn) : false;
   }

   public void setInvisible(boolean invisible) {
      this.setFlag(5, invisible);
   }

   protected boolean getFlag(int flag) {
      return (this.dataManager.get(FLAGS) & 1 << flag) != 0;
   }

   protected void setFlag(int flag, boolean set) {
      byte b0 = this.dataManager.get(FLAGS);
      if (set) {
         this.dataManager.set(FLAGS, (byte)(b0 | 1 << flag));
      } else {
         this.dataManager.set(FLAGS, (byte)(b0 & ~(1 << flag)));
      }

   }

   public int getMaxAir() {
      return 300;
   }

   public int getAir() {
      return this.dataManager.get(AIR);
   }

   public void setAir(int air) {
      this.dataManager.set(AIR, air);
   }

   public void onStruckByLightning(LightningBoltEntity lightningBolt) {
      ++this.fire;
      if (this.fire == 0) {
         this.setFire(8);
      }

      this.attackEntityFrom(DamageSource.LIGHTNING_BOLT, 5.0F);
   }

   public void onEnterBubbleColumnWithAirAbove(boolean downwards) {
      Vec3d vec3d = this.getMotion();
      double d0;
      if (downwards) {
         d0 = Math.max(-0.9D, vec3d.y - 0.03D);
      } else {
         d0 = Math.min(1.8D, vec3d.y + 0.1D);
      }

      this.setMotion(vec3d.x, d0, vec3d.z);
   }

   public void onEnterBubbleColumn(boolean downwards) {
      Vec3d vec3d = this.getMotion();
      double d0;
      if (downwards) {
         d0 = Math.max(-0.3D, vec3d.y - 0.03D);
      } else {
         d0 = Math.min(0.7D, vec3d.y + 0.06D);
      }

      this.setMotion(vec3d.x, d0, vec3d.z);
      this.fallDistance = 0.0F;
   }

   public void onKillEntity(LivingEntity entityLivingIn) {
   }

   protected void pushOutOfBlocks(double x, double y, double z) {
      BlockPos blockpos = new BlockPos(x, y, z);
      Vec3d vec3d = new Vec3d(x - (double)blockpos.getX(), y - (double)blockpos.getY(), z - (double)blockpos.getZ());
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      Direction direction = Direction.UP;
      double d0 = Double.MAX_VALUE;

      for(Direction direction1 : new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST, Direction.UP}) {
         blockpos$mutableblockpos.setPos(blockpos).move(direction1);
         if (!this.world.getBlockState(blockpos$mutableblockpos).func_224756_o(this.world, blockpos$mutableblockpos)) {
            double d1 = vec3d.getCoordinate(direction1.getAxis());
            double d2 = direction1.getAxisDirection() == Direction.AxisDirection.POSITIVE ? 1.0D - d1 : d1;
            if (d2 < d0) {
               d0 = d2;
               direction = direction1;
            }
         }
      }

      float f = this.rand.nextFloat() * 0.2F + 0.1F;
      float f1 = (float)direction.getAxisDirection().getOffset();
      Vec3d vec3d1 = this.getMotion().scale(0.75D);
      if (direction.getAxis() == Direction.Axis.X) {
         this.setMotion((double)(f1 * f), vec3d1.y, vec3d1.z);
      } else if (direction.getAxis() == Direction.Axis.Y) {
         this.setMotion(vec3d1.x, (double)(f1 * f), vec3d1.z);
      } else if (direction.getAxis() == Direction.Axis.Z) {
         this.setMotion(vec3d1.x, vec3d1.y, (double)(f1 * f));
      }

   }

   public void setMotionMultiplier(BlockState p_213295_1_, Vec3d p_213295_2_) {
      this.fallDistance = 0.0F;
      this.motionMultiplier = p_213295_2_;
   }

   private static void removeClickEvents(ITextComponent p_207712_0_) {
      p_207712_0_.applyTextStyle((p_213318_0_) -> {
         p_213318_0_.setClickEvent((ClickEvent)null);
      }).getSiblings().forEach(Entity::removeClickEvents);
   }

   public ITextComponent getName() {
      ITextComponent itextcomponent = this.getCustomName();
      if (itextcomponent != null) {
         ITextComponent itextcomponent1 = itextcomponent.deepCopy();
         removeClickEvents(itextcomponent1);
         return itextcomponent1;
      } else {
         return this.getType().getName(); // Forge: Use getter to allow overriding by mods
      }
   }

   public boolean isEntityEqual(Entity entityIn) {
      return this == entityIn;
   }

   public float getRotationYawHead() {
      return 0.0F;
   }

   public void setRotationYawHead(float rotation) {
   }

   public void setRenderYawOffset(float offset) {
   }

   public boolean canBeAttackedWithItem() {
      return true;
   }

   public boolean hitByEntity(Entity entityIn) {
      return false;
   }

   public String toString() {
      return String.format(Locale.ROOT, "%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f]", this.getClass().getSimpleName(), this.getName().getUnformattedComponentText(), this.entityId, this.world == null ? "~NULL~" : this.world.getWorldInfo().getWorldName(), this.posX, this.posY, this.posZ);
   }

   public boolean isInvulnerableTo(DamageSource source) {
      return this.invulnerable && source != DamageSource.OUT_OF_WORLD && !source.isCreativePlayer();
   }

   public boolean isInvulnerable() {
      return this.invulnerable;
   }

   public void setInvulnerable(boolean isInvulnerable) {
      this.invulnerable = isInvulnerable;
   }

   public void copyLocationAndAnglesFrom(Entity entityIn) {
      this.setLocationAndAngles(entityIn.posX, entityIn.posY, entityIn.posZ, entityIn.rotationYaw, entityIn.rotationPitch);
   }

   public void copyDataFromOld(Entity entityIn) {
      CompoundNBT compoundnbt = entityIn.writeWithoutTypeId(new CompoundNBT());
      compoundnbt.remove("Dimension");
      this.read(compoundnbt);
      this.timeUntilPortal = entityIn.timeUntilPortal;
      this.lastPortalPos = entityIn.lastPortalPos;
      this.lastPortalVec = entityIn.lastPortalVec;
      this.teleportDirection = entityIn.teleportDirection;
   }

   @Nullable
   public Entity changeDimension(DimensionType destination) {
      if (!net.minecraftforge.common.ForgeHooks.onTravelToDimension(this, destination)) return null;
      if (!this.world.isRemote && !this.removed) {
         this.world.getProfiler().startSection("changeDimension");
         MinecraftServer minecraftserver = this.getServer();
         DimensionType dimensiontype = this.dimension;
         ServerWorld serverworld = minecraftserver.func_71218_a(dimensiontype);
         ServerWorld serverworld1 = minecraftserver.func_71218_a(destination);
         this.dimension = destination;
         this.detach();
         this.world.getProfiler().startSection("reposition");
         Vec3d vec3d = this.getMotion();
         float f = 0.0F;
         BlockPos blockpos;
         if (dimensiontype == DimensionType.THE_END && destination == DimensionType.OVERWORLD) {
            blockpos = serverworld1.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, serverworld1.getSpawnPoint());
         } else if (destination == DimensionType.THE_END) {
            blockpos = serverworld1.getSpawnCoordinate();
         } else {
            double movementFactor = serverworld.getDimension().getMovementFactor() / serverworld1.getDimension().getMovementFactor();
            double d0 = this.posX * movementFactor;
            double d1 = this.posZ * movementFactor;

            double d3 = Math.min(-2.9999872E7D, serverworld1.getWorldBorder().minX() + 16.0D);
            double d4 = Math.min(-2.9999872E7D, serverworld1.getWorldBorder().minZ() + 16.0D);
            double d5 = Math.min(2.9999872E7D, serverworld1.getWorldBorder().maxX() - 16.0D);
            double d6 = Math.min(2.9999872E7D, serverworld1.getWorldBorder().maxZ() - 16.0D);
            d0 = MathHelper.clamp(d0, d3, d5);
            d1 = MathHelper.clamp(d1, d4, d6);
            Vec3d vec3d1 = this.getLastPortalVec();
            blockpos = new BlockPos(d0, this.posY, d1);
            BlockPattern.PortalInfo blockpattern$portalinfo = serverworld1.getDefaultTeleporter().placeInExistingPortal(blockpos, vec3d, this.getTeleportDirection(), vec3d1.x, vec3d1.y, this instanceof PlayerEntity);
            if (blockpattern$portalinfo == null) {
               return null;
            }

            blockpos = new BlockPos(blockpattern$portalinfo.pos);
            vec3d = blockpattern$portalinfo.motion;
            f = (float)blockpattern$portalinfo.rotation;
         }

         this.world.getProfiler().endStartSection("reloading");
         Entity entity = this.getType().create(serverworld1);
         if (entity != null) {
            entity.copyDataFromOld(this);
            entity.moveToBlockPosAndAngles(blockpos, entity.rotationYaw + f, entity.rotationPitch);
            entity.setMotion(vec3d);
            serverworld1.func_217460_e(entity);
         }

         this.remove(false);
         this.world.getProfiler().endSection();
         serverworld.resetUpdateEntityTick();
         serverworld1.resetUpdateEntityTick();
         this.world.getProfiler().endSection();
         return entity;
      } else {
         return null;
      }
   }

   public boolean isNonBoss() {
      return true;
   }

   public float getExplosionResistance(Explosion explosionIn, IBlockReader worldIn, BlockPos pos, BlockState blockStateIn, IFluidState p_180428_5_, float p_180428_6_) {
      return p_180428_6_;
   }

   public boolean canExplosionDestroyBlock(Explosion explosionIn, IBlockReader worldIn, BlockPos pos, BlockState blockStateIn, float p_174816_5_) {
      return true;
   }

   public int getMaxFallHeight() {
      return 3;
   }

   public Vec3d getLastPortalVec() {
      if (this.lastPortalVec == null) return Vec3d.ZERO;
      return this.lastPortalVec;
   }

   public Direction getTeleportDirection() {
      if (this.teleportDirection == null) return Direction.NORTH;
      return this.teleportDirection;
   }

   public boolean doesEntityNotTriggerPressurePlate() {
      return false;
   }

   public void fillCrashReport(CrashReportCategory category) {
      category.func_189529_a("Entity Type", () -> {
         return EntityType.getKey(this.getType()) + " (" + this.getClass().getCanonicalName() + ")";
      });
      category.func_71507_a("Entity ID", this.entityId);
      category.func_189529_a("Entity Name", () -> {
         return this.getName().getString();
      });
      category.func_71507_a("Entity's Exact location", String.format(Locale.ROOT, "%.2f, %.2f, %.2f", this.posX, this.posY, this.posZ));
      category.func_71507_a("Entity's Block location", CrashReportCategory.getCoordinateInfo(MathHelper.floor(this.posX), MathHelper.floor(this.posY), MathHelper.floor(this.posZ)));
      Vec3d vec3d = this.getMotion();
      category.func_71507_a("Entity's Momentum", String.format(Locale.ROOT, "%.2f, %.2f, %.2f", vec3d.x, vec3d.y, vec3d.z));
      category.func_189529_a("Entity's Passengers", () -> {
         return this.getPassengers().toString();
      });
      category.func_189529_a("Entity's Vehicle", () -> {
         return this.getRidingEntity().toString();
      });
   }

   @OnlyIn(Dist.CLIENT)
   public boolean canRenderOnFire() {
      return this.isBurning();
   }

   public void setUniqueId(UUID uniqueIdIn) {
      this.entityUniqueID = uniqueIdIn;
      this.cachedUniqueIdString = this.entityUniqueID.toString();
   }

   public UUID getUniqueID() {
      return this.entityUniqueID;
   }

   public String getCachedUniqueIdString() {
      return this.cachedUniqueIdString;
   }

   public String getScoreboardName() {
      return this.cachedUniqueIdString;
   }

   public boolean isPushedByWater() {
      return true;
   }

   @OnlyIn(Dist.CLIENT)
   public static double getRenderDistanceWeight() {
      return renderDistanceWeight;
   }

   @OnlyIn(Dist.CLIENT)
   public static void setRenderDistanceWeight(double renderDistWeight) {
      renderDistanceWeight = renderDistWeight;
   }

   public ITextComponent getDisplayName() {
      return ScorePlayerTeam.formatMemberName(this.getTeam(), this.getName()).applyTextStyle((p_211516_1_) -> {
         p_211516_1_.setHoverEvent(this.getHoverEvent()).setInsertion(this.getCachedUniqueIdString());
      });
   }

   public void setCustomName(@Nullable ITextComponent name) {
      this.dataManager.set(CUSTOM_NAME, Optional.ofNullable(name));
   }

   @Nullable
   public ITextComponent getCustomName() {
      return this.dataManager.get(CUSTOM_NAME).orElse((ITextComponent)null);
   }

   public boolean hasCustomName() {
      return this.dataManager.get(CUSTOM_NAME).isPresent();
   }

   public void setCustomNameVisible(boolean alwaysRenderNameTag) {
      this.dataManager.set(CUSTOM_NAME_VISIBLE, alwaysRenderNameTag);
   }

   public boolean isCustomNameVisible() {
      return this.dataManager.get(CUSTOM_NAME_VISIBLE);
   }

   public final void teleportKeepLoaded(double p_223102_1_, double p_223102_3_, double p_223102_5_) {
      if (this.world instanceof ServerWorld) {
         ChunkPos chunkpos = new ChunkPos(new BlockPos(p_223102_1_, p_223102_3_, p_223102_5_));
         ((ServerWorld)this.world).getChunkProvider().func_217228_a(TicketType.POST_TELEPORT, chunkpos, 0, this.getEntityId());
         this.world.getChunk(chunkpos.x, chunkpos.z);
         this.setPositionAndUpdate(p_223102_1_, p_223102_3_, p_223102_5_);
      }
   }

   public void setPositionAndUpdate(double x, double y, double z) {
      if (this.world instanceof ServerWorld) {
         this.isPositionDirty = true;
         this.setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
         ((ServerWorld)this.world).chunkCheck(this);
      }
   }

   @OnlyIn(Dist.CLIENT)
   public boolean getAlwaysRenderNameTagForRender() {
      return this.isCustomNameVisible();
   }

   public void notifyDataManagerChange(DataParameter<?> key) {
      if (POSE.equals(key)) {
         this.recalculateSize();
      }

   }

   public void recalculateSize() {
      EntitySize entitysize = this.size;
      Pose pose = this.getPose();
      EntitySize entitysize1 = this.getSize(pose);
      this.size = entitysize1;
      this.eyeHeight = getEyeHeightForge(pose, entitysize1);
      if (entitysize1.width < entitysize.width) {
         double d0 = (double)entitysize1.width / 2.0D;
         this.setBoundingBox(new AxisAlignedBB(this.posX - d0, this.posY, this.posZ - d0, this.posX + d0, this.posY + (double)entitysize1.height, this.posZ + d0));
      } else {
         AxisAlignedBB axisalignedbb = this.getBoundingBox();
         this.setBoundingBox(new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, axisalignedbb.minX + (double)entitysize1.width, axisalignedbb.minY + (double)entitysize1.height, axisalignedbb.minZ + (double)entitysize1.width));
         if (entitysize1.width > entitysize.width && !this.firstUpdate && !this.world.isRemote) {
            float f = entitysize.width - entitysize1.width;
            this.move(MoverType.SELF, new Vec3d((double)f, 0.0D, (double)f));
         }

      }
   }

   public Direction getHorizontalFacing() {
      return Direction.fromAngle((double)this.rotationYaw);
   }

   public Direction getAdjustedHorizontalFacing() {
      return this.getHorizontalFacing();
   }

   protected HoverEvent getHoverEvent() {
      CompoundNBT compoundnbt = new CompoundNBT();
      ResourceLocation resourcelocation = EntityType.getKey(this.getType());
      compoundnbt.putString("id", this.getCachedUniqueIdString());
      if (resourcelocation != null) {
         compoundnbt.putString("type", resourcelocation.toString());
      }

      compoundnbt.putString("name", ITextComponent.Serializer.toJson(this.getName()));
      return new HoverEvent(HoverEvent.Action.SHOW_ENTITY, new StringTextComponent(compoundnbt.toString()));
   }

   public boolean isSpectatedByPlayer(ServerPlayerEntity player) {
      return true;
   }

   public AxisAlignedBB getBoundingBox() {
      return this.boundingBox;
   }

   @OnlyIn(Dist.CLIENT)
   public AxisAlignedBB getRenderBoundingBox() {
      return this.getBoundingBox();
   }

   protected AxisAlignedBB getBoundingBox(Pose p_213321_1_) {
      EntitySize entitysize = this.getSize(p_213321_1_);
      float f = entitysize.width / 2.0F;
      Vec3d vec3d = new Vec3d(this.posX - (double)f, this.posY, this.posZ - (double)f);
      Vec3d vec3d1 = new Vec3d(this.posX + (double)f, this.posY + (double)entitysize.height, this.posZ + (double)f);
      return new AxisAlignedBB(vec3d, vec3d1);
   }

   public void setBoundingBox(AxisAlignedBB bb) {
      this.boundingBox = bb;
   }

   protected float getEyeHeight(Pose poseIn, EntitySize sizeIn) {
      return sizeIn.height * 0.85F;
   }

   @OnlyIn(Dist.CLIENT)
   public float getEyeHeight(Pose p_213307_1_) {
      return this.getEyeHeight(p_213307_1_, this.getSize(p_213307_1_));
   }

   public final float getEyeHeight() {
      return this.eyeHeight;
   }

   public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
      return false;
   }

   public void sendMessage(ITextComponent component) {
   }

   public BlockPos getPosition() {
      return new BlockPos(this);
   }

   public Vec3d getPositionVector() {
      return new Vec3d(this.posX, this.posY, this.posZ);
   }

   public World getEntityWorld() {
      return this.world;
   }

   @Nullable
   public MinecraftServer getServer() {
      return this.world.getServer();
   }

   public ActionResultType applyPlayerInteraction(PlayerEntity player, Vec3d vec, Hand hand) {
      return ActionResultType.PASS;
   }

   public boolean isImmuneToExplosions() {
      return false;
   }

   protected void applyEnchantments(LivingEntity entityLivingBaseIn, Entity entityIn) {
      if (entityIn instanceof LivingEntity) {
         EnchantmentHelper.applyThornEnchantments((LivingEntity)entityIn, entityLivingBaseIn);
      }

      EnchantmentHelper.applyArthropodEnchantments(entityLivingBaseIn, entityIn);
   }

   public void addTrackingPlayer(ServerPlayerEntity player) {
   }

   public void removeTrackingPlayer(ServerPlayerEntity player) {
   }

   public float getRotatedYaw(Rotation transformRotation) {
      float f = MathHelper.wrapDegrees(this.rotationYaw);
      switch(transformRotation) {
      case CLOCKWISE_180:
         return f + 180.0F;
      case COUNTERCLOCKWISE_90:
         return f + 270.0F;
      case CLOCKWISE_90:
         return f + 90.0F;
      default:
         return f;
      }
   }

   public float getMirroredYaw(Mirror transformMirror) {
      float f = MathHelper.wrapDegrees(this.rotationYaw);
      switch(transformMirror) {
      case LEFT_RIGHT:
         return -f;
      case FRONT_BACK:
         return 180.0F - f;
      default:
         return f;
      }
   }

   public boolean ignoreItemEntityData() {
      return false;
   }

   public boolean setPositionNonDirty() {
      boolean flag = this.isPositionDirty;
      this.isPositionDirty = false;
      return flag;
   }

   @Nullable
   public Entity getControllingPassenger() {
      return null;
   }

   public List<Entity> getPassengers() {
      return (List<Entity>)(this.passengers.isEmpty() ? Collections.emptyList() : Lists.newArrayList(this.passengers));
   }

   public boolean isPassenger(Entity entityIn) {
      for(Entity entity : this.getPassengers()) {
         if (entity.equals(entityIn)) {
            return true;
         }
      }

      return false;
   }

   public boolean isPassenger(Class<? extends Entity> p_205708_1_) {
      for(Entity entity : this.getPassengers()) {
         if (p_205708_1_.isAssignableFrom(entity.getClass())) {
            return true;
         }
      }

      return false;
   }

   public Collection<Entity> getRecursivePassengers() {
      Set<Entity> set = Sets.newHashSet();

      for(Entity entity : this.getPassengers()) {
         set.add(entity);
         entity.getRecursivePassengers(false, set);
      }

      return set;
   }

   public boolean isOnePlayerRiding() {
      Set<Entity> set = Sets.newHashSet();
      this.getRecursivePassengers(true, set);
      return set.size() == 1;
   }

   private void getRecursivePassengers(boolean playersOnly, Set<Entity> p_200604_2_) {
      for(Entity entity : this.getPassengers()) {
         if (!playersOnly || ServerPlayerEntity.class.isAssignableFrom(entity.getClass())) {
            p_200604_2_.add(entity);
         }

         entity.getRecursivePassengers(playersOnly, p_200604_2_);
      }

   }

   public Entity getLowestRidingEntity() {
      Entity entity;
      for(entity = this; entity.isPassenger(); entity = entity.getRidingEntity()) {
         ;
      }

      return entity;
   }

   public boolean isRidingSameEntity(Entity entityIn) {
      return this.getLowestRidingEntity() == entityIn.getLowestRidingEntity();
   }

   public boolean isRidingOrBeingRiddenBy(Entity entityIn) {
      for(Entity entity : this.getPassengers()) {
         if (entity.equals(entityIn)) {
            return true;
         }

         if (entity.isRidingOrBeingRiddenBy(entityIn)) {
            return true;
         }
      }

      return false;
   }

   public boolean canPassengerSteer() {
      Entity entity = this.getControllingPassenger();
      if (entity instanceof PlayerEntity) {
         return ((PlayerEntity)entity).isUser();
      } else {
         return !this.world.isRemote;
      }
   }

   @Nullable
   public Entity getRidingEntity() {
      return this.ridingEntity;
   }

   public PushReaction getPushReaction() {
      return PushReaction.NORMAL;
   }

   public SoundCategory getSoundCategory() {
      return SoundCategory.NEUTRAL;
   }

   protected int getFireImmuneTicks() {
      return 1;
   }

   public CommandSource getCommandSource() {
      return new CommandSource(this, new Vec3d(this.posX, this.posY, this.posZ), this.getPitchYaw(), this.world instanceof ServerWorld ? (ServerWorld)this.world : null, this.getPermissionLevel(), this.getName().getString(), this.getDisplayName(), this.world.getServer(), this);
   }

   protected int getPermissionLevel() {
      return 0;
   }

   public boolean hasPermissionLevel(int p_211513_1_) {
      return this.getPermissionLevel() >= p_211513_1_;
   }

   public boolean shouldReceiveFeedback() {
      return this.world.getGameRules().getBoolean(GameRules.SEND_COMMAND_FEEDBACK);
   }

   public boolean shouldReceiveErrors() {
      return true;
   }

   public boolean allowLogging() {
      return true;
   }

   public void lookAt(EntityAnchorArgument.Type p_200602_1_, Vec3d p_200602_2_) {
      Vec3d vec3d = p_200602_1_.apply(this);
      double d0 = p_200602_2_.x - vec3d.x;
      double d1 = p_200602_2_.y - vec3d.y;
      double d2 = p_200602_2_.z - vec3d.z;
      double d3 = (double)MathHelper.sqrt(d0 * d0 + d2 * d2);
      this.rotationPitch = MathHelper.wrapDegrees((float)(-(MathHelper.atan2(d1, d3) * (double)(180F / (float)Math.PI))));
      this.rotationYaw = MathHelper.wrapDegrees((float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F);
      this.setRotationYawHead(this.rotationYaw);
      this.prevRotationPitch = this.rotationPitch;
      this.prevRotationYaw = this.rotationYaw;
   }

   public boolean handleFluidAcceleration(Tag<Fluid> p_210500_1_) {
      AxisAlignedBB axisalignedbb = this.getBoundingBox().shrink(0.001D);
      int i = MathHelper.floor(axisalignedbb.minX);
      int j = MathHelper.ceil(axisalignedbb.maxX);
      int k = MathHelper.floor(axisalignedbb.minY);
      int l = MathHelper.ceil(axisalignedbb.maxY);
      int i1 = MathHelper.floor(axisalignedbb.minZ);
      int j1 = MathHelper.ceil(axisalignedbb.maxZ);
      if (!this.world.isAreaLoaded(i, k, i1, j, l, j1)) {
         return false;
      } else {
         double d0 = 0.0D;
         boolean flag = this.isPushedByWater();
         boolean flag1 = false;
         Vec3d vec3d = Vec3d.ZERO;
         int k1 = 0;

         try (BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain()) {
            for(int l1 = i; l1 < j; ++l1) {
               for(int i2 = k; i2 < l; ++i2) {
                  for(int j2 = i1; j2 < j1; ++j2) {
                     blockpos$pooledmutableblockpos.setPos(l1, i2, j2);
                     IFluidState ifluidstate = this.world.getFluidState(blockpos$pooledmutableblockpos);
                     if (ifluidstate.isTagged(p_210500_1_)) {
                        double d1 = (double)((float)i2 + ifluidstate.getActualHeight(this.world, blockpos$pooledmutableblockpos));
                        if (d1 >= axisalignedbb.minY) {
                           flag1 = true;
                           d0 = Math.max(d1 - axisalignedbb.minY, d0);
                           if (flag) {
                              Vec3d vec3d1 = ifluidstate.getFlow(this.world, blockpos$pooledmutableblockpos);
                              if (d0 < 0.4D) {
                                 vec3d1 = vec3d1.scale(d0);
                              }

                              vec3d = vec3d.add(vec3d1);
                              ++k1;
                           }
                        }
                     }
                  }
               }
            }
         }

         if (vec3d.length() > 0.0D) {
            if (k1 > 0) {
               vec3d = vec3d.scale(1.0D / (double)k1);
            }

            if (!(this instanceof PlayerEntity)) {
               vec3d = vec3d.normalize();
            }

            this.setMotion(this.getMotion().add(vec3d.scale(0.014D)));
         }

         this.submergedHeight = d0;
         return flag1;
      }
   }

   public double getSubmergedHeight() {
      return this.submergedHeight;
   }


   public final float getWidth() {
      return this.size.width;
   }

   public final float getHeight() {
      return this.size.height;
   }

   public abstract IPacket<?> createSpawnPacket();

   public EntitySize getSize(Pose poseIn) {
      return this.type.getSize();
   }

   public Vec3d getPositionVec() {
      return new Vec3d(this.posX, this.posY, this.posZ);
   }

   public Vec3d getMotion() {
      return this.motion;
   }

   public void setMotion(Vec3d motionIn) {
      this.motion = motionIn;
   }

   public void setMotion(double x, double y, double z) {
      this.setMotion(new Vec3d(x, y, z));
   }

   /* ================================== Forge Start =====================================*/

   private boolean canUpdate = true;
   @Override
   public void canUpdate(boolean value) {
      this.canUpdate = value;
   }
   @Override
   public boolean canUpdate() {
      return this.canUpdate;
   }
   private Collection<ItemEntity> captureDrops = null;
   @Override
   public Collection<ItemEntity> captureDrops() {
      return captureDrops;
   }
   @Override
   public Collection<ItemEntity> captureDrops(Collection<ItemEntity> value) {
      Collection<ItemEntity> ret = captureDrops;
      this.captureDrops = value;
      return ret;
   }
   private CompoundNBT persistentData;
   @Override
   public CompoundNBT getPersistentData() {
      if (persistentData == null)
         persistentData = new CompoundNBT();
      return persistentData;
   }
   @Override
   public boolean canTrample(BlockState state, BlockPos pos, float fallDistance) {
      return world.rand.nextFloat() < fallDistance - 0.5F
              && this instanceof LivingEntity
              && (this instanceof PlayerEntity || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(world, this))
              && this.getWidth() * this.getWidth() * this.getHeight() > 0.512F;
   }

   /**
    * Internal use for keeping track of entities that are tracked by a world, to
    * allow guarantees that entity position changes will force a chunk load, avoiding
    * potential issues with entity desyncing and bad chunk data.
    */
   private boolean isAddedToWorld;

   @Override
   public final boolean isAddedToWorld() { return this.isAddedToWorld; }

   @Override
   public void onAddedToWorld() { this.isAddedToWorld = true; }

   @Override
   public void onRemovedFromWorld() { this.isAddedToWorld = false; }

   @Override
   public void revive() {
      this.removed = false;
      this.reviveCaps();
   }

   private float getEyeHeightForge(Pose pose, EntitySize size) {
      net.minecraftforge.event.entity.EntityEvent.EyeHeight evt = new net.minecraftforge.event.entity.EntityEvent.EyeHeight(this, pose, size, this.getEyeHeight(pose, size));
      net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(evt);
      return evt.getNewHeight();
   }
}