package safro.deep.in.the.night.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.util.List;
import java.util.Random;

public class TrapPumpkinBlock extends Block {

    public TrapPumpkinBlock(Settings settings) {
        super(settings);
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        List<PlayerEntity> players = world.getEntitiesByClass(PlayerEntity.class, new Box((double)pos.getX() - 5.0D, (double)pos.getY() - 5.0D, (double)pos.getZ() - 5.0D, (double)pos.getX() + 5.0D, (double)pos.getY() + 5.0D, (double)pos.getZ() + 5.0D), (player) -> { return true;});
        if (players.size() >= 1) {
            if (players.stream().anyMatch((player) -> player.getAbilities().creativeMode)) {
                return;
            } else
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_WITCH_CELEBRATE, SoundCategory.BLOCKS, 5.0F, 1.0F);
        }
    }
}
