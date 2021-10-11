package safro.deep.in.the.night;

import com.google.gson.JsonObject;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
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
	}
}
