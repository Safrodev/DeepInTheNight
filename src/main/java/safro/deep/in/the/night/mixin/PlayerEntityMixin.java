package safro.deep.in.the.night.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import safro.deep.in.the.night.entity.ShadowReaperEntity;
import safro.deep.in.the.night.registry.EntityRegistry;
import safro.deep.in.the.night.registry.ItemRegistry;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    private boolean ability = false;

    @Inject(method = "tick", at = @At("TAIL"))
    private void vampireTrigger(CallbackInfo ci) {
        PlayerEntity entity = (PlayerEntity) (Object) this;
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

    @Inject(method = "remove", at = @At("HEAD"))
    private void summonShadowReaper(Entity.RemovalReason reason, CallbackInfo ci) {
        PlayerEntity entity = (PlayerEntity) (Object) this;
        if (entity.isDead() && !entity.world.isClient && entity.world.getEntitiesByClass(ShadowReaperEntity.class, entity.getBoundingBox().expand(5D), EntityPredicates.VALID_ENTITY).size() < 1) {
            ShadowReaperEntity reaper = EntityRegistry.SHADOW_REAPER.create(entity.world);
            reaper.setPosition(entity.getX(), entity.getY(), entity.getZ());
            entity.world.spawnEntity(reaper);
        }
    }
}
