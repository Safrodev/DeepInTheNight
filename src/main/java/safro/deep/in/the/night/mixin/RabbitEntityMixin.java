package safro.deep.in.the.night.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import safro.deep.in.the.night.registry.ItemRegistry;

@Mixin(RabbitEntity.class)
public abstract class RabbitEntityMixin extends AnimalEntity {

    @Shadow public abstract void setRabbitType(int rabbitType);

    public RabbitEntityMixin(EntityType<? extends RabbitEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (player.getStackInHand(hand).isOf(ItemRegistry.VAMPIRE_BLOOD)) {
            this.setRabbitType(99);
            player.getStackInHand(hand).decrement(1);
            return ActionResult.CONSUME;
        }
        return ActionResult.PASS;
    }
}
