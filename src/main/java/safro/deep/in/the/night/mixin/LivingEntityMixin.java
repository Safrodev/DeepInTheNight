package safro.deep.in.the.night.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import safro.deep.in.the.night.entity.HeadlessHorsemanEntity;
import safro.deep.in.the.night.registry.EntityRegistry;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "sleep", at = @At("HEAD"))
    private void headlessHorsemanTriggers(BlockPos pos, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        double x = entity.getX() + MathHelper.nextInt(entity.getRandom(), 8, 15);
        double y = entity.getY() + 1;
        double z = entity.getZ() + MathHelper.nextInt(entity.getRandom(), 8, 15);
        ServerWorld serverWorld = (ServerWorld)entity.world;
        if (serverWorld.getEntitiesByClass(HeadlessHorsemanEntity.class, entity.getBoundingBox().expand(30), EntityPredicates.VALID_ENTITY).size() <= 2) {
            HorseEntity horse = EntityType.HORSE.create(serverWorld);
            horse.setTame(true);
            horse.setBreedingAge(0);
            horse.setPosition(x, y, z);
            serverWorld.spawnEntity(horse);
            HeadlessHorsemanEntity horseman = EntityRegistry.HEADLESS_HORSEMAN.create(serverWorld);
            horseman.setPosition(horse.getX(), horse.getY(), horse.getZ());
            horseman.startRiding(horse);
            serverWorld.spawnEntityAndPassengers(horseman);
        }
    }
}
