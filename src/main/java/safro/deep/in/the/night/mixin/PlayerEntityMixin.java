package safro.deep.in.the.night.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import safro.deep.in.the.night.entity.VampireEntity;
import safro.deep.in.the.night.registry.EntityRegistry;
import safro.deep.in.the.night.registry.ItemRegistry;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    private boolean ability = false;

    @Inject(method = "tick", at = @At("TAIL"))
    private void vampireTrigger(CallbackInfo ci) {
        PlayerEntity entity = (PlayerEntity) (Object) this;
        World world = entity.getEntityWorld();
    /*    if (world.getEntitiesByClass(BatEntity.class, entity.getBoundingBox().expand(5D), (b) -> {return true;}).size() >= 1) {
            world.getEntitiesByClass(BatEntity.class, entity.getBoundingBox().expand(5D), (bat) -> {
                BlockPos pos = bat.getBlockPos();
                VampireEntity vampire = EntityRegistry.VAMPIRE.create(world);
                vampire.updatePositionAndAngles(bat.getX(), bat.getY(), bat.getZ(), bat.getYaw(), bat.getPitch());
                world.spawnEntity(vampire);
                bat.discard();
                world.addParticle(ParticleTypes.SMOKE, (double) pos.getX() + 0.5D + vampire.getRandom().nextDouble() / 4.0D * (double) (vampire.getRandom().nextBoolean() ? 1 : -1), (double) pos.getY() + 0.4D, (double) pos.getZ() + 0.5D + vampire.getRandom().nextDouble() / 4.0D * (double) (vampire.getRandom().nextBoolean() ? 1 : -1), 0.0D, 0.005D, 0.0D);
                return true;
            });
        } */
            if (isUsingVampirismSerum(entity)) {
                ability = true;
            }
    }

    public boolean isUsingVampirismSerum(PlayerEntity entity) {
        return entity.isUsingItem() && entity.getActiveItem().isOf(ItemRegistry.VAMPIRISM_SERUM);
    }

    @Inject(method = "onKilledOther", at = @At("TAIL"))
    private void vampireKill(ServerWorld world, LivingEntity other, CallbackInfo ci) {
        PlayerEntity entity = (PlayerEntity) (Object) this;
        if (ability) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 100, 1));
        }
    }
}
