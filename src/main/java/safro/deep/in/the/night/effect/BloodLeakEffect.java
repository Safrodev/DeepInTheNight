package safro.deep.in.the.night.effect;


import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import safro.deep.in.the.night.util.BleedingDamageSource;

public class BloodLeakEffect extends StatusEffect {
    private int timer;

    public BloodLeakEffect() {
        super(StatusEffectType.HARMFUL,
                0x5D0707);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (timer >= 100) {
            entity.damage(new BleedingDamageSource(), 1);
            timer = 0;
        } else
            ++timer;
    }
}
