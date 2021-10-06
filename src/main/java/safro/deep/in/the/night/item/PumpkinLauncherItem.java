package safro.deep.in.the.night.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import safro.deep.in.the.night.DeepInTheNight;
import safro.deep.in.the.night.entity.PumpkinBombEntity;

import java.util.function.Predicate;

public class PumpkinLauncherItem extends RangedWeaponItem {

    public PumpkinLauncherItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        Vec3d vec3d = user.getRotationVec(1.0F);
        ItemStack pumpkinStack = this.getPumpkins(user);
        if (!world.isClient) {
            if (!pumpkinStack.isEmpty() || user.getAbilities().creativeMode) {
                Vec3d vec = user.getRotationVec(0.0F);
                double vX = (user.getX() + vec3d.x * 4.0D) - user.getX();
                double vY = (user.getY() + vec3d.y * 4.0D) - user.getY();
                double vZ = (user.getZ() + vec3d.z * 4.0D) - user.getZ();
                PumpkinBombEntity pumpkin = new PumpkinBombEntity(world, user, vX, vY, vZ);
                pumpkin.setItem(pumpkinStack);
                pumpkin.updatePosition(user.getX() + vec.x * 2.0D, user.getEyeY() - 1, user.getZ() + vec.z * 2.0D);
                pumpkinStack.decrement(1);
                world.playSound((PlayerEntity) null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_GILDED_BLACKSTONE_FALL, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
                world.spawnEntity(pumpkin);
            }
        }
        return TypedActionResult.success(itemStack);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 7200;
    }

    public ItemStack getPumpkins(PlayerEntity player) {
        if (player.getStackInHand(Hand.OFF_HAND).isIn(DeepInTheNight.PUMPKINS)) {
            return player.getStackInHand(Hand.OFF_HAND);
        } else if (player.getStackInHand(Hand.MAIN_HAND).isIn(DeepInTheNight.PUMPKINS)) {
            return player.getStackInHand(Hand.MAIN_HAND);
        } else {
            for (int index = 0; index < player.getInventory().size(); ++index) {
                ItemStack stack = player.getInventory().getStack(index);
                if (stack.isIn(DeepInTheNight.PUMPKINS)) return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return (stack) -> { return stack.isOf(Items.PUMPKIN); };
    }

    @Override
    public int getRange() {
        return 15;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK;
    }
}
