package safro.deep.in.the.night.registry;

import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import safro.deep.in.the.night.DeepInTheNight;
import safro.deep.in.the.night.item.DeathAxeItem;
import safro.deep.in.the.night.item.PumpkinLauncherItem;
import safro.deep.in.the.night.item.ReapingScytheItem;
import safro.deep.in.the.night.item.VampirismSerumItem;

import java.util.LinkedHashMap;
import java.util.Map;

public class ItemRegistry {
    public static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();


    // Weapons
    public static final Item DEATH_AXE = register("death_axe", new DeathAxeItem(ToolMaterials.IRON, 4, -2.4F, simple()));
    public static final Item PUMPKIN_LAUNCHER = register("pumpkin_launcher", new PumpkinLauncherItem(simple().maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item REAPING_SCYTHE = register("reaping_scythe", new ReapingScytheItem(ToolMaterials.DIAMOND, 3, -3F, 4F, 4F, simple().rarity(Rarity.EPIC)));

    // Misc
    public static final Item VAMPIRE_BLOOD = register("vampire_blood", new Item(simple()));
    public static final Item VAMPIRISM_SERUM = register("vampirism_serum", new VampirismSerumItem(simple().maxCount(1)));
    public static final Item SCYTHE_FRAGMENT = register("scythe_fragment", new Item(simple()));

    private static Item.Settings simple() {
        return new Item.Settings().group(DeepInTheNight.ITEMGROUP);
    }
    private static <T extends Item> T register(String name, T item) {
        ITEMS.put(item, new Identifier(DeepInTheNight.MODID, name));
        return item;
    }

    public static void init() {
        ITEMS.keySet().forEach(item -> Registry.register(Registry.ITEM, ITEMS.get(item), item));
    }
}
