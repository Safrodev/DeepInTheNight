package safro.deep.in.the.night.entity.goal;

import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.HostileEntity;

public class CustomAttackGoal extends MeleeAttackGoal {
    private final HostileEntity zombie;
    private int ticks;

    public CustomAttackGoal(HostileEntity zombie, double speed, boolean pauseWhenMobIdle) {
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
        } else {
            this.zombie.setAttacking(false);
        }

    }
}
