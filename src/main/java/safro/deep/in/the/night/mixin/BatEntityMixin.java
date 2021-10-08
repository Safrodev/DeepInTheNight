package safro.deep.in.the.night.mixin;

import net.minecraft.entity.passive.BatEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import safro.deep.in.the.night.entity.VampireEntity;
import safro.deep.in.the.night.registry.EntityRegistry;

import java.util.Iterator;

@Mixin(BatEntity.class)
public class BatEntityMixin {

    @Inject(method = "mobTick", at = @At("TAIL"))
    private void vampireTrigger(CallbackInfo ci) {
        Iterator players;
        BatEntity bat = (BatEntity) (Object) this;
        World world = bat.world;
        players = world.getNonSpectatingEntities(ServerPlayerEntity.class, bat.getBoundingBox().expand(5.0D)).iterator();
        if (world.isNight()) {
            while (players.hasNext()) {
                BlockPos pos = bat.getBlockPos();
                VampireEntity vampire = EntityRegistry.VAMPIRE.create(world);
                vampire.updatePositionAndAngles(bat.getX(), bat.getY(), bat.getZ(), bat.getYaw(), bat.getPitch());
                world.spawnEntity(vampire);
                world.addParticle(ParticleTypes.SMOKE, (double) pos.getX() + 0.5D + vampire.getRandom().nextDouble() / 4.0D * (double) (vampire.getRandom().nextBoolean() ? 1 : -1), (double) pos.getY() + 0.4D, (double) pos.getZ() + 0.5D + vampire.getRandom().nextDouble() / 4.0D * (double) (vampire.getRandom().nextBoolean() ? 1 : -1), 0.0D, 0.005D, 0.0D);
                bat.discard();
            }
        }
    }
}
