package safro.deep.in.the.night.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import safro.deep.in.the.night.config.DitnConfig;
import safro.deep.in.the.night.registry.SoundRegistry;

import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {

    @Shadow public abstract ServerWorld toServerWorld();

    @Inject(method = "tick", at = @At("TAIL"))
    private void blackNight(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        ServerWorld world = this.toServerWorld();
        if (world.isNight() && DitnConfig.getValue("allow_scares")) {
            if (MathHelper.nextInt(world.random, 1, 1000) <= 10) {
                for (ServerPlayerEntity player : world.getPlayers()) {
                    world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundRegistry.GHOST_SCARE, SoundCategory.AMBIENT, 1.0F, 1.0F);
                }
            }
        }
    }
}