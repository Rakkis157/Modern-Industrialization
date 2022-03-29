package aztech.modern_industrialization.blocks;

import aztech.modern_industrialization.MIBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class PaddedBlock extends MIBlock {
    public PaddedBlock(String id, Properties settings) {
        super(id, settings);
    }

    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float f) {
        entity.causeFallDamage(f, 0.2F, DamageSource.FALL);
    }
}
