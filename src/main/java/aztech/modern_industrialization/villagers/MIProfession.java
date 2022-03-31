package aztech.modern_industrialization.villagers;

import aztech.modern_industrialization.MIBlock;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;


public class MIProfession {

    private static VillagerProfession of(String id, Block block, SoundEvent soundEvent){
        PoiType POI = PoiType.register(id, PoiType.getBlockStates(block), 1, 1);
        return VillagerProfession.register(id, POI, soundEvent);
    }

    public static final VillagerProfession ENGINEER = of("engineer", MIBlock.FORGE_HAMMER ,SoundEvents.VILLAGER_WORK_TOOLSMITH);
    public static final VillagerProfession MARINE_BIOLOGIST = of("marine_biologist", MIBlock.AQUARIUM, SoundEvents.VILLAGER_WORK_FISHERMAN);
    public static final VillagerProfession MYCOLOGIST = of("mycologist", Blocks.MUSHROOM_STEM, SoundEvents.VILLAGER_WORK_FARMER);
}
