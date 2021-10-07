package safro.deep.in.the.night.mixin;

import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import safro.deep.in.the.night.registry.EntityRegistry;

@Mixin(DefaultBiomeFeatures.class)
public class DefaultBiomeFeaturesMixin {

    @Inject(method = "addPlainsMobs", at = @At("TAIL"))
    private static void addScarecrows(SpawnSettings.Builder builder, CallbackInfo ci) {
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityRegistry.SCARECROW, 7, 2, 3));
    }

    @Inject(method = "addFarmAnimals", at = @At("TAIL"))
    private static void addCrows(SpawnSettings.Builder builder, CallbackInfo ci) {
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityRegistry.CROW, 8, 4, 4));
    }
}
