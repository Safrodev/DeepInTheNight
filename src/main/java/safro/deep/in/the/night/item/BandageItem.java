package safro.deep.in.the.night.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import safro.deep.in.the.night.registry.EffectRegistry;

public class BandageItem extends Item {

    public BandageItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (user.hasStatusEffect(EffectRegistry.BLEEDING)) {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(stack);
        }
        return TypedActionResult.pass(stack);
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        user.removeStatusEffect(EffectRegistry.BLEEDING);
        user.heal(2.0F);
        world.playSound(null, user.getBlockPos(), SoundEvents.ITEM_BONE_MEAL_USE, SoundCategory.PLAYERS, 1.0F, 1.0F);
        stack.decrement(1);
        return stack;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.CROSSBOW;
    }

    public int getMaxUseTime(ItemStack stack) {
        return 32;
    }
}
