package pixelperfectionist.path_under_everything.mixin;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirtPathBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.block.DoorBlock.class)
public class DoorBlock {

    @Inject(method = "canPlaceAt", at = @At(value = "HEAD"), cancellable = true)
    public void canPlaceAtInject(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockPos blockPos = pos.down();
        BlockState blockState = world.getBlockState(blockPos);
        Block blockBelow = blockState.getBlock();


        if (blockBelow instanceof DirtPathBlock) {
            cir.setReturnValue(true);
        }


    }


}
