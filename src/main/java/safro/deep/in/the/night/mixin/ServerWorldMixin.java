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
    private int scareCooldown = 0;

    @Shadow public abstract ServerWorld toServerWorld();

    @Inject(method = "tick", at = @At("TAIL"))
    private void playScares(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        ServerWorld world = this.toServerWorld();
        if (world.isNight() && DitnConfig.getBooleanValue("allow_scares")) {
            if (MathHelper.nextInt(world.random, 1, DitnConfig.getIntValue("scare_chance")) <= 10 && scareCooldown >= 1200) {
                for (ServerPlayerEntity player : world.getPlayers()) {
                    if (world.random.nextFloat() <= 0.5F) {
                        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundRegistry.DEEP_SCREECH, SoundCategory.AMBIENT, 1.0F, 1.0F);
                        scareCooldown = 0;
                    } else {
                        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundRegistry.GHOST_SCARE, SoundCategory.AMBIENT, 1.0F, 1.0F);
                        scareCooldown = 0;
                    }
                }
            } else {
                ++scareCooldown;
            }
        }
    }
}
