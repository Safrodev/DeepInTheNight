package safro.deep.in.the.night.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import safro.deep.in.the.night.entity.CrowEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import safro.deep.in.the.night.entity.goal.EatCropsGoal;

import java.util.EnumSet;
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
        this.goalSelector.add(1, new EatCropsGoal(this, 1.0D, 60));
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
        return SoundEvents.ENTITY_PARROT_IMITATE_WITCH;
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
}
