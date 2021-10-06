package safro.deep.in.the.night.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import safro.deep.in.the.night.DeepInTheNight;
import safro.deep.in.the.night.entity.*;

public class EntityRegistry {

    public static final EntityType<HeadlessHorsemanEntity>  HEADLESS_HORSEMAN = FabricEntityTypeBuilder.<HeadlessHorsemanEntity>create(SpawnGroup.MONSTER, HeadlessHorsemanEntity::new)
            .dimensions(EntityDimensions.fixed(0.6F, 1.99F)).trackRangeBlocks(15).build();

    public static final EntityType<ScarecrowEntity>  SCARECROW = FabricEntityTypeBuilder.<ScarecrowEntity>create(SpawnGroup.MONSTER, ScarecrowEntity::new)
            .dimensions(EntityDimensions.fixed(0.6F, 1.95F)).trackRangeBlocks(25).build();

    public static final EntityType<VampireEntity>  VAMPIRE = FabricEntityTypeBuilder.<VampireEntity>create(SpawnGroup.MONSTER, VampireEntity::new)
            .dimensions(EntityDimensions.fixed(0.6F, 1.99F)).trackRangeBlocks(15).build();

    public static final EntityType<CrowEntity>  CROW = FabricEntityTypeBuilder.<CrowEntity>create(SpawnGroup.CREATURE, CrowEntity::new)
            .dimensions(EntityDimensions.fixed(0.5F, 0.9F)).trackRangeBlocks(8).build();

    public static final EntityType<PumpkinBombEntity>  PUMPKIN_BOMB = FabricEntityTypeBuilder.<PumpkinBombEntity>create(SpawnGroup.MISC, PumpkinBombEntity::new)
            .dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeBlocks(4).trackedUpdateRate(10).build();

    public static void init() {
        Registry.register(Registry.ENTITY_TYPE, new Identifier(DeepInTheNight.MODID, "headless_horseman"), HEADLESS_HORSEMAN);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(DeepInTheNight.MODID, "scarecrow"), SCARECROW);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(DeepInTheNight.MODID, "vampire"), VAMPIRE);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(DeepInTheNight.MODID, "crow"), CROW);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(DeepInTheNight.MODID, "pumpkin_bomb"), PUMPKIN_BOMB);

        // Attributes
        FabricDefaultAttributeRegistry.register(HEADLESS_HORSEMAN, HeadlessHorsemanEntity.createHeadlessHorsemanAttributes());
        FabricDefaultAttributeRegistry.register(SCARECROW, ScarecrowEntity.createScarecrowAttributes());
        FabricDefaultAttributeRegistry.register(VAMPIRE, VampireEntity.createVampireAttributes());
        FabricDefaultAttributeRegistry.register(CROW, CrowEntity.createCrowAttributes());
    }
}
