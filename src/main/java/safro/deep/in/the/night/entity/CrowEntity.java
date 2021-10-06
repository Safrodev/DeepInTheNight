package safro.deep.in.the.night.entity;

import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;
import safro.deep.in.the.night.registry.SoundRegistry;

import java.util.Random;

public class CrowEntity extends AnimalEntity {
    public float flapProgress;
    public float maxWingDeviation;
    public float prevMaxWingDeviation;
    public float prevFlapProgress;
    private float flapSpeed = 1.0F;
    private float field_28640 = 1.0F;
    private static final int field_30351 = 5;

    public CrowEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 10, false);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, -1.0F);
    }

    protected void initGoals() {
        this.goalSelector.add(0, new EscapeDangerGoal(this, 1.25D));
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(1, new BreakCropsGoal(1.2000000476837158D, 12, 1));
        this.goalSelector.add(3, new FollowMobGoal(this, 1.0D, 3.0F, 7.0F));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 10D));
    }

    public static DefaultAttributeContainer.Builder createCrowAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0D).add(EntityAttributes.GENERIC_FLYING_SPEED, 0.4000000059604645D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.20000000298023224D);
    }

    public EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(true);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    public float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.6F;
    }

    public void tickMovement() {
        this.flapWings();
        super.tickMovement();
    }

    private void flapWings() {
        this.prevFlapProgress = this.flapProgress;
        this.prevMaxWingDeviation = this.maxWingDeviation;
        this.maxWingDeviation = (float)((double)this.maxWingDeviation + (double)(!this.onGround && !this.hasVehicle() ? 4 : -1) * 0.3D);
        this.maxWingDeviation = MathHelper.clamp(this.maxWingDeviation, 0.0F, 1.0F);
        if (!this.onGround && this.flapSpeed < 1.0F) {
            this.flapSpeed = 1.0F;
        }

        this.flapSpeed = (float)((double)this.flapSpeed * 0.9D);
        Vec3d vec3d = this.getVelocity();
        if (!this.onGround && vec3d.y < 0.0D) {
            this.setVelocity(vec3d.multiply(1.0D, 0.6D, 1.0D));
        }

        this.flapProgress += this.flapSpeed * 2.0F;
    }

    public static boolean canSpawn(EntityType<CrowEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        BlockState blockState = world.getBlockState(pos.down());
        return (blockState.isIn(BlockTags.LEAVES) || blockState.isOf(Blocks.GRASS_BLOCK) || blockState.isIn(BlockTags.LOGS) || blockState.isOf(Blocks.AIR)) && world.getBaseLightLevel(pos, 0) > 8;
    }

    public boolean isBaby() {
        return false;
    }

    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
    }

    public boolean canBreedWith(AnimalEntity other) {
        return false;
    }

    @Nullable
    @Override
    public CrowEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    public SoundEvent getAmbientSound() {
        return SoundRegistry.CROW_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_PARROT_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PARROT_DEATH;
    }

    protected boolean hasWings() {
        return this.field_28627 > this.field_28640;
    }

    protected void addFlapEffects() {
        this.playSound(SoundEvents.ENTITY_PARROT_FLY, 0.15F, 1.0F);
        this.field_28640 = this.field_28627 + this.maxWingDeviation / 2.0F;
    }

    public boolean isPushable() {
        return true;
    }

    protected void pushAway(Entity entity) {
        if (!(entity instanceof PlayerEntity)) {
            super.pushAway(entity);
        }
    }

    public boolean isInAir() {
        return !this.onGround;
    }

    public Vec3d getLeashOffset() {
        return new Vec3d(0.0D, (double)(0.5F * this.getStandingEyeHeight()), (double)(this.getWidth() * 0.4F));
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        if (entityData == null) {
            entityData = new PassiveData(false);
        }
        return super.initialize(world, difficulty, spawnReason, (EntityData)entityData, entityNbt);
    }

    public class BreakCropsGoal extends MoveToTargetPosGoal {
        protected int timer;

        public BreakCropsGoal(double speed, int range, int maxYDifference) {
            super(CrowEntity.this, speed, range, maxYDifference);
        }

        public double getDesiredSquaredDistanceToTarget() {
            return 2.0D;
        }

        public boolean shouldResetPath() {
            return this.tryingTime % 100 == 0;
        }

        protected boolean isTargetPos(WorldView world, BlockPos pos) {
            BlockState blockState = world.getBlockState(pos);
            return blockState.isOf(Blocks.WHEAT) && (Integer)blockState.get(CropBlock.AGE) == CropBlock.MAX_AGE;
        }

        public void tick() {
            if (this.hasReached()) {
                if (this.timer >= 40) {
                    this.eatCrop();
                } else {
                    ++this.timer;
                }
            } else if (!this.hasReached() && CrowEntity.this.random.nextFloat() < 0.05F) {
                CrowEntity.this.playSound(SoundEvents.ENTITY_PARROT_EAT, 1.0F, 1.0F);
            }

            super.tick();
        }

        protected void eatCrop() {
            if (CrowEntity.this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                BlockState blockState = CrowEntity.this.world.getBlockState(this.targetPos);
                if (blockState.isOf(Blocks.WHEAT) && blockState.getBlock() instanceof CropBlock cropBlock) {

                    int i = (Integer)blockState.get(CropBlock.AGE);
                    blockState.with(CropBlock.AGE, 1);
                    int j = 1 + CrowEntity.this.world.random.nextInt(2) + (i == 3 ? 1 : 0);
                    Block.dropStack(CrowEntity.this.world, this.targetPos, new ItemStack(cropBlock.getSeedsItem(), j));

                    CrowEntity.this.playSound(SoundEvents.BLOCK_CROP_BREAK, 1.0F, 1.0F);
                    CrowEntity.this.world.setBlockState(this.targetPos, Blocks.AIR.getDefaultState(), 2);
                }
            }
        }

        public boolean canStart() {
            return !CrowEntity.this.isSleeping() && super.canStart();
        }

        public void start() {
            this.timer = 0;
            super.start();
        }
    }
}
