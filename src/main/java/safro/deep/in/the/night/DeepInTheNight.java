package safro.deep.in.the.night;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import safro.deep.in.the.night.registry.BlockRegistry;
import safro.deep.in.the.night.registry.EntityRegistry;
import safro.deep.in.the.night.registry.ItemRegistry;
import safro.deep.in.the.night.registry.SpawningRegistry;

public class DeepInTheNight implements ModInitializer {
	public static final String MODID = "ditn";

	public static ItemGroup ITEMGROUP = FabricItemGroupBuilder
			.build(new Identifier(MODID, MODID), () -> new ItemStack(Items.CARVED_PUMPKIN));

	public static final Tag<Item> PUMPKINS = TagRegistry.item(new Identifier("c:pumpkins"));

	@Override
	public void onInitialize() {
		EntityRegistry.init();
		BlockRegistry.init();
		ItemRegistry.init();
		SpawningRegistry.init();
	}
}
