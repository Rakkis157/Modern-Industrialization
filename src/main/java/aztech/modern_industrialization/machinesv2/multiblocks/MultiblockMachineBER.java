package aztech.modern_industrialization.machinesv2.multiblocks;

import aztech.modern_industrialization.ModernIndustrialization;
import aztech.modern_industrialization.machines.impl.multiblock.HatchType;
import aztech.modern_industrialization.machinesv2.MachineBlock;
import aztech.modern_industrialization.machinesv2.blockentities.multiblocks.SteamCraftingMultiblockBlockEntity;
import aztech.modern_industrialization.util.RenderHelper;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class MultiblockMachineBER extends BlockEntityRenderer<SteamCraftingMultiblockBlockEntity> {
    public MultiblockMachineBER(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(SteamCraftingMultiblockBlockEntity be, float tickDelta, MatrixStack matrices, VertexConsumerProvider vcp, int light, int overlay) {
        boolean holdingWrench = isHoldingWrench();
        HatchType hatchType = getHeldHatchType();
        if (holdingWrench || hatchType != null) {
            ShapeMatcher matcher = new ShapeMatcher(be.getWorld(), be.getPos(), be.orientation.facingDirection,be.shapeTemplate);

            for (BlockPos pos : matcher.getPositions()) {
                matrices.push();
                matrices.translate(pos.getX() - be.getPos().getX(), pos.getY() - be.getPos().getY(),
                        pos.getZ() - be.getPos().getZ());

                HatchFlags hatchFlag = matcher.getHatchFlags(pos);
                if (hatchType != null) {
                    if (hatchFlag != null && hatchFlag.allows(hatchType)) {
                        // Highlight placeable hatches in green
                        matrices.translate(-0.005, -0.005, -0.005);
                        matrices.scale(1.01f, 1.01f, 1.01f);
                        RenderHelper.drawOverlay(matrices, vcp, 111f / 256, 1, 111f / 256, 15728880, overlay);
                    }
                }
                if (holdingWrench) {
                    if (!matcher.matches(pos, be.getWorld(), null)) {
                        if (be.getWorld().getBlockState(pos).isAir()) {
                            // Draw hologram
                            matrices.translate(0.25, 0.25, 0.25);
                            matrices.scale(0.5f, 0.5f, 0.5f);
                            MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(matcher.getSimpleMember(pos).getPreviewState(), matrices, vcp,
                                    15728880, overlay);
                        } else {
                            // Hightlight in red
                            matrices.translate(-0.005, -0.005, -0.005);
                            matrices.scale(1.01f, 1.01f, 1.01f);
                            RenderHelper.drawOverlay(matrices, vcp, 1, 50f / 256, 50f / 256, 15728880, overlay);
                        }
                    }
                }

                matrices.pop();
            }
        }
    }

    private static boolean isHoldingWrench() {
        PlayerEntity player = MinecraftClient.getInstance().player;
        return player.getMainHandStack().getItem().isIn(ModernIndustrialization.WRENCHES)
                || player.getOffHandStack().getItem().isIn(ModernIndustrialization.WRENCHES);
    }

    @Nullable
    private static HatchType getHeldHatchType() {
        PlayerEntity player = MinecraftClient.getInstance().player;
        HatchType mainHand = getHatchType(player.getMainHandStack());
        HatchType offHand = getHatchType(player.getOffHandStack());
        return mainHand == null ? offHand : mainHand;
    }

    @Nullable
    private static HatchType getHatchType(ItemStack stack) {
        Item item = stack.getItem();
        if (item instanceof BlockItem) {
            BlockItem blockItem = (BlockItem) item;
            if (blockItem.getBlock() instanceof MachineBlock) {
                MachineBlock block = (MachineBlock) blockItem.getBlock();
                BlockEntity be = block.createBlockEntity(null);
                if (be instanceof HatchBlockEntity) {
                    HatchBlockEntity hatch = (HatchBlockEntity) be;
                    return hatch.getHatchType();
                }
            }
        }
        return null;
    }
}
