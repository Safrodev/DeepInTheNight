package safro.deep.in.the.night.entity.goal;

import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import safro.deep.in.the.night.entity.CrowEntity;

public abstract class AbstractEatCropsGoal extends MoveToTargetPosGoal {
    public final CrowEntity crow;
    private final int maxProgress;
    private int breakProgress;
    private int lastProgress;

    public AbstractEatCropsGoal(CrowEntity crow, double speed, int maxProgress) {
        super(crow, speed, 8, 3);
        this.maxProgress = maxProgress;
        this.crow = crow;
    }

    public static boolean isStandable(World world, CrowEntity entity, BlockPos pos) {
        return world.isAir(pos) && world.getBlockState(pos.down()).hasSolidTopSurface(world, pos, entity);
    }

    public static BlockPos getStandablePosition(CrowEntity entity, BlockPos pos) {
        World world = entity.world;
        if (isStandable(world, entity, pos.west())) {
            return pos.west();
        } else if (isStandable(world, entity, pos.north())) {
            return pos.north();
        } else if (isStandable(world, entity, pos.south())) {
            return pos.south();
        } else if (isStandable(world, entity, pos.up())) {
            return pos.up();
        } else if (isStandable(world, entity, pos.down())) {
            return pos.down();
        } else {
            return pos.east();
        }
    }

    @Override
    protected int getInterval(PathAwareEntity mob) {
        int min = 40;
        if (min < 40) {
            min += mob.getRandom().nextInt(40 - min);
        }
        return min;
    }

    public boolean use() {
        this.breakProgress++;
        int newProgress = (int) Math.floor((float) this.breakProgress / this.getMaxProgress() * 10.0F);
        if (this.lastProgress != newProgress) {
            this.lastProgress = newProgress;
        }
        this.crow.world.setBlockBreakingInfo(this.crow.getId(), this.targetPos, newProgress);
        return this.breakProgress >= this.getMaxProgress();
    }

    @Override
    public void start() {
        super.start();
        this.breakProgress = 0;
        this.lastProgress = 0;
    }

    public int getMaxProgress() {
        return maxProgress;
    }
}
