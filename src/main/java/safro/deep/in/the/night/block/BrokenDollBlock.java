package safro.deep.in.the.night.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import safro.deep.in.the.night.registry.SoundRegistry;

public class BrokenDollBlock extends FallingBlock {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(3.0D, 0.0D, 4.0D, 12.0D, 13.0D, 13.0D);

    public BrokenDollBlock(Settings settings) {
        super(settings);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isNight()) {
            this.teleport(state, world, pos);
            world.playSound(null, pos, SoundRegistry.DOLL_LAUGH, SoundCategory.BLOCKS, 1.0F, 1.0F);
            return ActionResult.CONSUME;
        }
        return ActionResult.PASS;
    }

    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        if (world.isNight()) {
            this.teleport(state, world, pos);
        }
    }

    private void teleport(BlockState state, World world, BlockPos pos) {
        for(int i = 0; i < 1000; ++i) {
            BlockPos blockPos = pos.add(world.random.nextInt(16) - world.random.nextInt(16), world.random.nextInt(8) - world.random.nextInt(8), world.random.nextInt(16) - world.random.nextInt(16));
            if (world.getBlockState(blockPos).isAir()) {
                if (world.isClient) {
                    for(int j = 0; j < 128; ++j) {
                        double d = world.random.nextDouble();
                        float f = (world.random.nextFloat() - 0.5F) * 0.2F;
                        float g = (world.random.nextFloat() - 0.5F) * 0.2F;
                        float h = (world.random.nextFloat() - 0.5F) * 0.2F;
                        double e = MathHelper.lerp(d, (double)blockPos.getX(), (double)pos.getX()) + (world.random.nextDouble() - 0.5D) + 0.5D;
                        double k = MathHelper.lerp(d, (double)blockPos.getY(), (double)pos.getY()) + world.random.nextDouble() - 0.5D;
                        double l = MathHelper.lerp(d, (double)blockPos.getZ(), (double)pos.getZ()) + (world.random.nextDouble() - 0.5D) + 0.5D;
                        world.addParticle(ParticleTypes.POOF, e, k, l, (double)f, (double)g, (double)h);
                    }
                } else {
                    world.setBlockState(blockPos, state, 2);
                    world.removeBlock(pos, false);
                }

                return;
            }
        }

    }

    protected int getFallDelay() {
        return 5;
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }
}
