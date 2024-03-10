package pixelperfectionist.path_under_everything.mixin;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ShovelItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShovelItem.class)
public class Shovel {

    @Inject(method = "useOnBlock", at = @At(value = "HEAD"), cancellable = true)
    private void makePath(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        if (context.getHand() == Hand.MAIN_HAND) {
            World world = context.getWorld();
            PlayerEntity player = context.getPlayer();

            BlockPos pos = context.getBlockPos();
            BlockState blockState = world.getBlockState(pos);
            Block block = blockState.getBlock();




            if (block == Blocks.GRASS_BLOCK || block == Blocks.DIRT) {

                world.playSound(null, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.setBlockState(pos, Blocks.DIRT_PATH.getDefaultState());
                cir.setReturnValue(ActionResult.SUCCESS);
                if (player != null) {
                    context.getStack().damage(1, player, (p) -> {
                        p.sendToolBreakStatus(context.getHand());
                    });
                }
            }
        }
    }
}
