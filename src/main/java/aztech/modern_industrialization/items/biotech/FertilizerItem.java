package aztech.modern_industrialization.items.biotech;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

public class FertilizerItem extends BoneMealItem {
    public FertilizerItem(Properties properties){
        super(properties);
    }

    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockPos blockPos2 = blockPos.relative(context.getClickedFace());
        if (growCrop(context.getItemInHand(), level, blockPos)) {
            if (!level.isClientSide) {
                level.levelEvent(1505, blockPos, 0);
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            BlockState blockState = level.getBlockState(blockPos);
            boolean bl = blockState.isFaceSturdy(level, blockPos, context.getClickedFace());
            if (bl && growWaterPlant(context.getItemInHand(), level, blockPos2, context.getClickedFace())) {
                if (!level.isClientSide) {
                    level.levelEvent(1505, blockPos2, 0);
                }

                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    public static boolean growCrop(ItemStack stack, Level level, BlockPos pos) {
        BlockState blockState = level.getBlockState(pos);
        if (blockState.getBlock() instanceof BonemealableBlock) {
            BonemealableBlock bonemealableBlock = (BonemealableBlock)blockState.getBlock();
            if (bonemealableBlock.isValidBonemealTarget(level, pos, blockState, level.isClientSide)) {
                if (level instanceof ServerLevel) {
                    if (bonemealableBlock.isBonemealSuccess(level, level.random, pos, blockState)) {
                        bonemealableBlock.performBonemeal((ServerLevel)level, level.random, pos, blockState);
                    }

                    stack.shrink(1);
                }

                return true;
            }
        }

        return false;
    }

    public static boolean growWaterPlant(ItemStack stack, Level level, BlockPos pos, @Nullable Direction clickedSide) {
        if (level.getBlockState(pos).is(Blocks.WATER) && level.getFluidState(pos).getAmount() == 8) {
            if (!(level instanceof ServerLevel)) {
                return true;
            } else {
                Random random = level.getRandom();

                label76:
                for(int i = 0; i < 128; ++i) {
                    BlockPos blockPos = pos;
                    BlockState blockState = Blocks.SEAGRASS.defaultBlockState();

                    for(int j = 0; j < i / 16; ++j) {
                        blockPos = blockPos.offset(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
                        if (level.getBlockState(blockPos).isCollisionShapeFullBlock(level, blockPos)) {
                            continue label76;
                        }
                    }

                    Optional<ResourceKey<Biome>> j = level.getBiomeName(blockPos);
                    if (Objects.equals(j, Optional.of(Biomes.WARM_OCEAN))) {
                        if (i == 0 && clickedSide != null && clickedSide.getAxis().isHorizontal()) {
                            blockState = (BlockState)((Block) BlockTags.WALL_CORALS.getRandomElement(level.random)).defaultBlockState().setValue(BaseCoralWallFanBlock.FACING, clickedSide);
                        } else if (random.nextInt(4) == 0) {
                            blockState = ((Block)BlockTags.UNDERWATER_BONEMEALS.getRandomElement(random)).defaultBlockState();
                        }
                    }

                    if (blockState.is(BlockTags.WALL_CORALS)) {
                        for(int k = 0; !blockState.canSurvive(level, blockPos) && k < 4; ++k) {
                            blockState = (BlockState)blockState.setValue(BaseCoralWallFanBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random));
                        }
                    }

                    if (blockState.canSurvive(level, blockPos)) {
                        BlockState k = level.getBlockState(blockPos);
                        if (k.is(Blocks.WATER) && level.getFluidState(blockPos).getAmount() == 8) {
                            level.setBlock(blockPos, blockState, 3);
                        } else if (k.is(Blocks.SEAGRASS) && random.nextInt(10) == 0) {
                            ((BonemealableBlock)Blocks.SEAGRASS).performBonemeal((ServerLevel)level, random, blockPos, k);
                        }
                    }
                }

                stack.shrink(1);
                return true;
            }
        } else {
            return false;
        }
    }

    public static void addGrowthParticles(LevelAccessor level, BlockPos pos, int data) {
        if (data == 0) {
            data = 15;
        }

        BlockState blockState = level.getBlockState(pos);
        if (!blockState.isAir()) {
            double d = 0.5D;
            double e;
            if (blockState.is(Blocks.WATER)) {
                data *= 3;
                e = 1.0D;
                d = 3.0D;
            } else if (blockState.isSolidRender(level, pos)) {
                pos = pos.above();
                data *= 3;
                d = 3.0D;
                e = 1.0D;
            } else {
                e = blockState.getShape(level, pos).max(Direction.Axis.Y);
            }

            level.addParticle(ParticleTypes.HAPPY_VILLAGER, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
            Random random = level.getRandom();

            for(int i = 0; i < data; ++i) {
                double f = random.nextGaussian() * 0.02D;
                double g = random.nextGaussian() * 0.02D;
                double h = random.nextGaussian() * 0.02D;
                double j = 0.5D - d;
                double k = (double)pos.getX() + j + random.nextDouble() * d * 2.0D;
                double l = (double)pos.getY() + random.nextDouble() * e;
                double m = (double)pos.getZ() + j + random.nextDouble() * d * 2.0D;
                if (!level.getBlockState((new BlockPos(k, l, m)).below()).isAir()) {
                    level.addParticle(ParticleTypes.HAPPY_VILLAGER, k, l, m, f, g, h);
                }
            }

        }
    }
}
