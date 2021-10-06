package safro.deep.in.the.night.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import safro.deep.in.the.night.DeepInTheNight;
import safro.deep.in.the.night.block.TrapPumpkinBlock;

import java.util.LinkedHashMap;
import java.util.Map;

public class BlockRegistry {
    private static final Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();


    public static final Block TRAP_PUMPKIN = register("trap_pumpkin", new TrapPumpkinBlock(FabricBlockSettings.of(Material.GOURD).mapColor(MapColor.ORANGE).strength(1.0F).sounds(BlockSoundGroup.WOOD).ticksRandomly()), true);

    private static <T extends Block> T register(String name, T block, boolean createItem) {
        BLOCKS.put(block, new Identifier(DeepInTheNight.MODID, name));
        if (createItem) {
            ItemRegistry.ITEMS.put(new BlockItem(block, new Item.Settings().group(DeepInTheNight.ITEMGROUP)), BLOCKS.get(block));
        }
        return block;
    }

    public static void init() {
        BLOCKS.keySet().forEach(block -> Registry.register(Registry.BLOCK, BLOCKS.get(block), block));
    }
}
