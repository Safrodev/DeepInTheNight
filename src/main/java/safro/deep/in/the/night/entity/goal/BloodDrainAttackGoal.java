package safro.deep.in.the.night.entity.goal;

import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import safro.deep.in.the.night.registry.EffectRegistry;

public class BloodDrainAttackGoal extends MeleeAttackGoal {
    private final HostileEntity zombie;
    private int ticks;

    public BloodDrainAttackGoal(HostileEntity zombie, double speed, boolean pauseWhenMobIdle) {
        super(zombie, speed, pauseWhenMobIdle);
        this.zombie = zombie;
    }

    public void start() {
        super.start();
        this.ticks = 0;
    }

    public void stop() {
        super.stop();
        this.zombie.setAttacking(false);
    }

    public void tick() {
        super.tick();
        ++this.ticks;
        if (this.ticks >= 5 && this.getCooldown() < this.getMaxCooldown() / 2) {
            this.zombie.setAttacking(true);
            this.zombie.getTarget().addStatusEffect(new StatusEffectInstance(EffectRegistry.BLEEDING, 1200, 0));
            if (this.zombie.getTarget() instanceof PlayerEntity player) {
            //    player.sendMessage(new TranslatableText("msg.ditn.bleeding").formatted(Formatting.DARK_RED).formatted(Formatting.ITALIC), false);
            }
        } else {
            this.zombie.setAttacking(false);
        }

    }
}
