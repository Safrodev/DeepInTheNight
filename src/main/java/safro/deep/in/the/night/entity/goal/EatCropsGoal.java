package safro.deep.in.the.night.entity.goal;

import net.minecraft.block.*;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.WorldView;
import safro.deep.in.the.night.entity.CrowEntity;

import java.util.EnumSet;

public class EatCropsGoal extends AbstractEatCropsGoal {
    private BlockPos cachedPos;

    public EatCropsGoal(CrowEntity crow, double speed, int maxProgress) {
        super(crow, speed, maxProgress);
        this.setControls(EnumSet.of(Control.MOVE, Control.JUMP, Control.LOOK));
    }

    @Override
    public boolean canStart() {
        return super.canStart();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.hasReached() && this.use()) {
            this.crow.world.breakBlock(this.targetPos, true);
        }

        this.mob.getLookControl().lookAt(this.targetPos.getX(), this.targetPos.getY(), this.targetPos.getZ());
    }

    public boolean shouldContinue() {
        return super.shouldContinue() && this.isOrePresent();
    }

    @Override
    protected BlockPos getTargetPos() {
        if (this.cachedPos != null) {
            return this.cachedPos;
        }
        Vec3d newPos = NoPenaltyTargeting.find(this.crow, 1, 2, new Vec3d(this.targetPos.getX(), this.targetPos.getY(), this.targetPos.getZ()));
        if (newPos == null) {
            return super.getTargetPos();
        }
        this.cachedPos = new BlockPos(newPos);
        return this.cachedPos;
    }

    public boolean isOrePresent() {
        return !this.crow.world.getBlockState(this.targetPos).isAir();
    }

    @Override
    public double getDesiredSquaredDistanceToTarget() {
        return 4.0D;
    }

    public boolean isExposed(WorldView world, BlockPos pos) {
        return world.isAir(pos.up()) ||
                world.isAir(pos.down()) ||
                world.isAir(pos.north()) ||
                world.isAir(pos.south()) ||
                world.isAir(pos.west()) ||
                world.isAir(pos.east());
    }

    @Override
    public void start() {
        super.start();
        this.cachedPos = null;
    }

    @Override
    public void stop() {
        super.stop();
        this.targetPos = BlockPos.ORIGIN;
    }

    public boolean isOre(BlockState state) {
        return state.getBlock() instanceof CropBlock || state.getBlock() instanceof SweetBerryBushBlock;
    }

    public boolean raycast(BlockPos pos) {
        var context = new RaycastContext(this.crow.getEyePos(), new Vec3d(pos.getX(),
                pos.getY(),
                pos.getZ()), RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, this.crow);
        var result = this.crow.world.raycast(context);
        return pos.equals(result.getBlockPos());
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        ItemStack pickaxe = this.crow.getMainHandStack();
        return this.isExposed(world, pos) && this.isOre(state) && pickaxe.isSuitableFor(state) && (this.targetPos != BlockPos.ORIGIN || this.raycast(pos));
    }
}
