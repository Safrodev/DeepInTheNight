package safro.deep.in.the.night.entity.goal;

import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public class FollowPlayerGoal extends Goal {
    private final MobEntity mob;
    private final Predicate<MobEntity> targetPredicate;
    private PlayerEntity target;
    private final double speed;
    private final EntityNavigation navigation;
    private int updateCountdownTicks;
    private final float minDistance;
    private float oldWaterPathFindingPenalty;
    private final float maxDistance;

    public FollowPlayerGoal(MobEntity mob, double speed, float minDistance, float maxDistance) {
        this.mob = mob;
        this.targetPredicate = (mobEntity2) -> {
            return mobEntity2 != null && mob.getClass() != mobEntity2.getClass();
        };
        this.speed = speed;
        this.navigation = mob.getNavigation();
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        if (!(mob.getNavigation() instanceof MobNavigation) && !(mob.getNavigation() instanceof BirdNavigation)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowPlayerGoal");
        }
    }

    public boolean canStart() {
        List<MobEntity> list = this.mob.world.getEntitiesByClass(MobEntity.class, this.mob.getBoundingBox().expand((double)this.maxDistance), this.targetPredicate);
        if (!list.isEmpty()) {
            Iterator var2 = list.iterator();

            while(var2.hasNext() && var2.next() instanceof PlayerEntity mobEntity) {
                if (!mobEntity.isInvisible()) {
                    this.target = mobEntity;
                    return true;
                }
            }
        }

        return false;
    }

    public boolean shouldContinue() {
        return this.target != null && !this.navigation.isIdle() && this.mob.squaredDistanceTo(this.target) > (double)(this.minDistance * this.minDistance);
    }

    public void start() {
        this.updateCountdownTicks = 0;
        this.oldWaterPathFindingPenalty = this.mob.getPathfindingPenalty(PathNodeType.WATER);
        this.mob.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
    }

    public void stop() {
        this.target = null;
        this.navigation.stop();
        this.mob.setPathfindingPenalty(PathNodeType.WATER, this.oldWaterPathFindingPenalty);
    }

    public void tick() {
        if (this.target != null && !this.mob.isLeashed()) {
            this.mob.getLookControl().lookAt(this.target, 10.0F, (float)this.mob.getLookPitchSpeed());
            if (--this.updateCountdownTicks <= 0) {
                this.updateCountdownTicks = 10;
                double d = this.mob.getX() - this.target.getX();
                double e = this.mob.getY() - this.target.getY();
                double f = this.mob.getZ() - this.target.getZ();
                double g = d * d + e * e + f * f;
                if (!(g <= (double)(this.minDistance * this.minDistance))) {
                    this.navigation.startMovingTo(this.target, this.speed);
                } else {
                    this.navigation.stop();
                    if (g <= (double)this.minDistance) {
                        double h = this.target.getX() - this.mob.getX();
                        double i = this.target.getZ() - this.mob.getZ();
                        this.navigation.startMovingTo(this.mob.getX() - h, this.mob.getY(), this.mob.getZ() - i, this.speed);
                    }
                }
            }
        }
    }
}