package safro.deep.in.the.night.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import safro.deep.in.the.night.registry.ItemRegistry;

public class HeadlessHorsemanEntity extends VindicatorEntity {

    public HeadlessHorsemanEntity(EntityType<? extends VindicatorEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(4, new HeadlessHorsemanEntity.AttackGoal(this));
        this.targetSelector.add(1, (new RevengeGoal(this, new Class[]{SkeletonEntity.class})).setGroupRevenge(new Class[0]));
        this.targetSelector.add(2, new FollowTargetGoal(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new FollowTargetGoal(this, VillagerEntity.class, true));
        this.targetSelector.add(3, new FollowTargetGoal(this, IronGolemEntity.class, true));
        this.targetSelector.add(4, new FollowTargetGoal<LivingEntity>(this, LivingEntity.class, true, true));
        this.goalSelector.add(8, new WanderAroundGoal(this, 0.6D));
        this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 8.0F));
    }

    protected void mobTick() {
        super.mobTick();
    }

    public static DefaultAttributeContainer.Builder createHeadlessHorsemanAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2899999940395355D).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 12.0D).add(EntityAttributes.GENERIC_MAX_HEALTH, 26.0D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0D);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
    }

    @Override
    protected void initEquipment(LocalDifficulty difficulty) {
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(ItemRegistry.DEATH_AXE));
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SKELETON_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SKELETON_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SKELETON_HURT;
    }

    @Override
    public void addBonusForWave(int wave, boolean unused) {

    }

    @Override
    public void setCustomName(@Nullable Text name) {
        super.setCustomName(name);
    }

    class AttackGoal extends MeleeAttackGoal {
        public AttackGoal(HeadlessHorsemanEntity horseman) {
            super(horseman, 1.0D, false);
        }

        protected double getSquaredMaxAttackDistance(LivingEntity entity) {
            if (this.mob.getVehicle() instanceof HorseEntity) {
                float f = this.mob.getVehicle().getWidth() - 0.1F;
                return (double)(f * 2.0F * f * 2.0F + entity.getWidth());
            } else {
                return super.getSquaredMaxAttackDistance(entity);
            }
        }
    }
}
