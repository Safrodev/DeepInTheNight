package safro.deep.in.the.night.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import safro.deep.in.the.night.entity.goal.CustomAttackGoal;

import java.util.Random;

public class ScarecrowEntity extends HostileEntity {

    public ScarecrowEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public void initGoals() {
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(7, new LookAroundGoal(this));
        this.targetSelector.add(1, (new RevengeGoal(this, new Class[0])).setGroupRevenge(new Class[]{ZombieEntity.class}));
        this.goalSelector.add(2, new CustomAttackGoal(this, 1.0D, false));
        this.targetSelector.add(2, new FollowTargetGoal(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new FollowTargetGoal(this, IronGolemEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder createScarecrowAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25000000417232513D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D).add(EntityAttributes.GENERIC_ARMOR, 2.0D);
    }

    public static boolean canSpawn(EntityType<ScarecrowEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.toServerWorld().isNight();
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SNOW_GOLEM_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SNOW_GOLEM_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SNOW_GOLEM_DEATH;
    }
}
