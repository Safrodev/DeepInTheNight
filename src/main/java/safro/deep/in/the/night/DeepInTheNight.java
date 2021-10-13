package safro.deep.in.the.night;

import com.google.gson.JsonObject;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import safro.deep.in.the.night.config.Config;
import safro.deep.in.the.night.config.DitnConfig;
import safro.deep.in.the.night.entity.ScarecrowEntity;
import safro.deep.in.the.night.entity.VampireEntity;
import safro.deep.in.the.night.registry.*;

import java.io.File;

public class DeepInTheNight implements ModInitializer {
	public static final String MODID = "ditn";

	public static ItemGroup ITEMGROUP = FabricItemGroupBuilder
			.build(new Identifier(MODID, MODID), () -> new ItemStack(ItemRegistry.PUMPKIN_LAUNCHER));

	public static final Tag<Item> PUMPKINS = TagRegistry.item(new Identifier("c:pumpkins"));

	public static SoundRegistry SOUNDS;

	@Override
	public void onInitialize() {
		DitnConfig.init();
		String defaultConfig = "{\n" + "  \"regenerate_values\": false\n" + "}";

		File configFile = Config.createFile("config/ditn/backup_config.json", defaultConfig, false);
		JsonObject json = Config.getJsonObject(Config.readFile(configFile));

		DitnConfig.generateConfigs(json == null || !json.has("regenerate_values") || json.get("regenerate_values").getAsBoolean());
		DitnConfig.loadConfig();

		EntityRegistry.init();
		BlockRegistry.init();
		ItemRegistry.init();
		EffectRegistry.init();

		SpawnRestrictionAccessor.callRegister(EntityRegistry.SCARECROW, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ScarecrowEntity::canSpawn);
		SpawnRestrictionAccessor.callRegister(EntityRegistry.VAMPIRE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, VampireEntity::canSpawn);

		SOUNDS = new SoundRegistry();

		LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, table, setter) -> {
			if (LootTables.VILLAGE_SHEPARD_CHEST.equals(id)) {
				FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
						.rolls(ConstantLootNumberProvider.create(1.0F))
						.with(ItemEntry.builder(BlockRegistry.BROKEN_DOLL.asItem())
								.weight(5));
				table.pool(poolBuilder);
			}
		});
		LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, table, setter) -> {
			if (LootTables.VILLAGE_FLETCHER_CHEST.equals(id)) {
				FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
						.rolls(ConstantLootNumberProvider.create(1.0F))
						.with(ItemEntry.builder(BlockRegistry.BROKEN_DOLL.asItem())
								.weight(2));
				table.pool(poolBuilder);
			}
		});
		LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, table, setter) -> {
			if (LootTables.VILLAGE_TANNERY_CHEST.equals(id)) {
				FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
						.rolls(ConstantLootNumberProvider.create(1.0F))
						.with(ItemEntry.builder(BlockRegistry.BROKEN_DOLL.asItem())
								.weight(4));
				table.pool(poolBuilder);
			}
		});
	}
}
