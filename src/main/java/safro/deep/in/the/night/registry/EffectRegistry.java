package safro.deep.in.the.night.registry;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import safro.deep.in.the.night.DeepInTheNight;
import safro.deep.in.the.night.effect.BloodLeakEffect;

public class EffectRegistry {
    public static final StatusEffect BLEEDING = new BloodLeakEffect();

    public static void init() {
        Registry.register(Registry.STATUS_EFFECT, new Identifier(DeepInTheNight.MODID, "bleeding"), BLEEDING);
    }
}
