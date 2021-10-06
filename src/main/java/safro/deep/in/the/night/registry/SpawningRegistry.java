package safro.deep.in.the.night.registry;

import com.google.common.base.Preconditions;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import safro.deep.in.the.night.entity.ScarecrowEntity;

import java.util.function.Predicate;

public class SpawningRegistry {

    public static void addSpawn(Predicate<BiomeSelectionContext> biomeSelector, SpawnGroup spawnGroup,
                                SpawnSettings.SpawnEntry se) {
        Preconditions.checkArgument(se.type.getSpawnGroup() != SpawnGroup.MISC,
                "Cannot add entity spawns with group: MISC");

        Identifier id = Registry.ENTITY_TYPE.getId(se.type);
        Preconditions.checkState(id != Registry.ENTITY_TYPE.getDefaultId(), "Unregistered entity type: %s", se.type);

        BiomeModifications.create(id).add(ModificationPhase.ADDITIONS, biomeSelector, context -> {
            context.getSpawnSettings().addSpawn(spawnGroup, se);
        });
    }

    private static void normal() {
        Predicate<BiomeSelectionContext> biomeSelector = (context) -> {
            Biome.Category category = context.getBiome().getCategory();
            return category != Biome.Category.NETHER && category != Biome.Category.THEEND
                    && category != Biome.Category.ICY && category != Biome.Category.OCEAN
                    && category != Biome.Category.MUSHROOM && category != Biome.Category.EXTREME_HILLS
                    && category != Biome.Category.MESA && category != Biome.Category.DESERT;
        };

        addSpawn(biomeSelector, EntityRegistry.SCARECROW.getSpawnGroup(),
                new SpawnSettings.SpawnEntry(EntityRegistry.SCARECROW, 5, 1, 2));
    }

    public static void init() {
        SpawnRestrictionAccessor.callRegister(EntityRegistry.SCARECROW, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ScarecrowEntity::canSpawn);
        normal();
    }
}
