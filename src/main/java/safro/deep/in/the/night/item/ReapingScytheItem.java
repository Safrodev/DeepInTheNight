package safro.deep.in.the.night.item;

import com.google.common.collect.ImmutableMultimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import safro.deep.in.the.night.mixin.SwordItemAccessor;

import java.util.UUID;

public class ReapingScytheItem extends SwordItem {
    private static final UUID REACH_MODIFIER = UUID.fromString("E70BE4E9-B163-4CC1-8848-F860B0BC02FC");
    private static final UUID ATTACK_RANGE_MODIFIER = UUID.fromString("7CB7BC58-D3BA-40AE-BC95-F8C38fE144FF");

    public ReapingScytheItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, float reachDistance, float attackDistance, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(this.getAttributeModifiers(EquipmentSlot.MAINHAND));
        builder.put(ReachEntityAttributes.REACH, new EntityAttributeModifier(REACH_MODIFIER, "Weapon modifier", reachDistance, EntityAttributeModifier.Operation.ADDITION));
        builder.put(ReachEntityAttributes.ATTACK_RANGE, new EntityAttributeModifier(ATTACK_RANGE_MODIFIER, "Weapon modifier", attackDistance, EntityAttributeModifier.Operation.ADDITION));
        ((SwordItemAccessor) this).setAttributeModifiers(builder.build());
    }

}
