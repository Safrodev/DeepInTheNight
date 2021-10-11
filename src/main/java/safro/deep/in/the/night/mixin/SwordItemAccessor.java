package safro.deep.in.the.night.mixin;

import com.google.common.collect.Multimap;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.SwordItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SwordItem.class)
public interface SwordItemAccessor {
    @Accessor
    @Mutable
    void setAttributeModifiers(Multimap<EntityAttribute, EntityAttributeModifier> value);
}
