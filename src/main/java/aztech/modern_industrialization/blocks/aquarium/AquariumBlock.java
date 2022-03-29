package aztech.modern_industrialization.blocks.aquarium;

import aztech.modern_industrialization.MIBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class AquariumBlock extends MIBlock {

    public AquariumBlock(){
        super("aquarium", FabricBlockSettings.of(Material.GLASS).nonOpaque().sound(SoundType.GLASS));
    }
}
