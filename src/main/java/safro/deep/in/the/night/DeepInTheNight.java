package safro.deep.in.the.night;

import com.google.gson.JsonObject;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import safro.deep.in.the.night.config.Config;
import safro.deep.in.the.night.config.DitnConfig;
import safro.deep.in.the.night.registry.*;
import safro.deep.in.the.night.util.ClientEvents;

import java.io.File;

public class DeepInTheNight implements ModInitializer {
	public static final String MODID = "ditn";

	public static ItemGroup ITEMGROUP = FabricItemGroupBuilder
			.build(new Identifier(MODID, MODID), () -> new ItemStack(Items.CARVED_PUMPKIN));

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
		SpawningRegistry.init();
		ClientEvents.init();

		SOUNDS = new SoundRegistry();
	}
}
