package safro.deep.in.the.night.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import safro.deep.in.the.night.registry.ItemRegistry;

public class ShadowReaperEntity extends PassiveEntity {

    public ShadowReaperEntity(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new FleeEntityGoal<PlayerEntity>(this, PlayerEntity.class, 16.0F, 1.3D, 2.2D));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.5D));
    }

    public static DefaultAttributeContainer.Builder createReaperAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3000001192092896D);
    }

    public void tickMovement() {
        super.tickMovement();
        if (random.nextInt(10) == 1) {
            for (int j = 0; j < 2 * 8; ++j) {
                float f = this.random.nextFloat() * 6.2831855F;
                float g = this.random.nextFloat() * 0.5F + 0.5F;
                float h = MathHelper.sin(f) * (float) 2 * 0.5F * g;
                float k = MathHelper.cos(f) * (float) 2 * 0.5F * g;
                this.world.addParticle(ParticleTypes.SQUID_INK, this.getX() + (double) h, this.getY(), this.getZ() + (double) k, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
        super.dropEquipment(source, lootingMultiplier, allowDrops);
        ItemEntity itemEntity = this.dropItem(ItemRegistry.SCYTHE_FRAGMENT);
        if (itemEntity != null) {
            itemEntity.setCovetedItem();
        }
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_OCELOT_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_OCELOT_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_OCELOT_DEATH;
    }
}
