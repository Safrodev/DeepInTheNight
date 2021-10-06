package safro.deep.in.the.night.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import safro.deep.in.the.night.registry.EntityRegistry;

public class PumpkinBombEntity extends ExplosiveProjectileEntity implements FlyingItemEntity {
    private static final TrackedData<ItemStack> ITEM;

    public PumpkinBombEntity(EntityType<? extends ExplosiveProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Environment(EnvType.CLIENT)
    public PumpkinBombEntity(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        this(EntityRegistry.PUMPKIN_BOMB, world);
        this.refreshPositionAndAngles(x, y, z, this.getYaw(), this.getPitch());
        this.setVelocity(velocityX, velocityY, velocityZ);
    }

    public PumpkinBombEntity(World world, LivingEntity owner, double velocityX, double velocityY, double velocityZ) {
        super(EntityRegistry.PUMPKIN_BOMB, owner, velocityX, velocityY, velocityZ, world);
    }

    @Override
    public void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            boolean bl = this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING);
            this.world.createExplosion((Entity)null, this.getX(), this.getY(), this.getZ(), 1, false, bl ? Explosion.DestructionType.DESTROY : Explosion.DestructionType.NONE);
            this.discard();
        }
    }

    @Override
    public SoundCategory getSoundCategory() {
        return SoundCategory.PLAYERS;
    }

    @Override
    protected ParticleEffect getParticleType() {
        return new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.PUMPKIN.getDefaultState());
    }

    @Override
    protected float getDrag() {
        return 0.9F;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Environment(EnvType.CLIENT)
    public boolean shouldRender(double distance) {
        return distance < 16384.0D;
    }

    @Override
    public float getBrightnessAtEyes() {
        return 1.0F;
    }

    @Override
    public void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = this.getOwner();
        Entity hitEntity = entityHitResult.getEntity();
        if (!this.world.isClient && entity != null && hitEntity instanceof LivingEntity) {
            this.discard();
        }
    }

    @Override
    public void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.world.isClient) {
            this.discard();
        }
    }

    @Override
    public boolean collides() {
        return true;
    }

    public ItemStack getItem() {
        return new ItemStack(Items.PUMPKIN);
    }

    @Override
    public ItemStack getStack() {
        ItemStack itemStack = this.getItem();
        return itemStack.isEmpty() ? new ItemStack(Items.PUMPKIN) : itemStack;
    }

    public void setItem(ItemStack stack) {
        if (!stack.isOf(Items.PUMPKIN) || stack.hasNbt()) {
            this.getDataTracker().set(ITEM, (ItemStack) Util.make(stack.copy(), (stackx) -> {
                stackx.setCount(1);
            }));
        }

    }

    protected void initDataTracker() {
        this.getDataTracker().startTracking(ITEM, ItemStack.EMPTY);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        ItemStack itemStack = this.getItem();
        if (!itemStack.isEmpty()) {
            nbt.put("Item", itemStack.writeNbt(new NbtCompound()));
        }

    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        ItemStack itemStack = ItemStack.fromNbt(nbt.getCompound("Item"));
        this.setItem(itemStack);
    }

    static {
        ITEM = DataTracker.registerData(AbstractFireballEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
    }
}
