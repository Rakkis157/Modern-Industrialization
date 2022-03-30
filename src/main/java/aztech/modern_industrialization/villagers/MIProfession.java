package aztech.modern_industrialization.villagers;

import aztech.modern_industrialization.MIBlock;
import aztech.modern_industrialization.MIItem;
import aztech.modern_industrialization.materials.MIMaterials;
import aztech.modern_industrialization.materials.MaterialRegistry;
import aztech.modern_industrialization.materials.part.MIParts;
import aztech.modern_industrialization.materials.part.MaterialPart;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class MIProfession {
    public static final PoiType ENGINEER_POI = PoiType.register("engineer", PoiType.getBlockStates(
            MIBlock.FORGE_HAMMER), 1, 1);
    public static final PoiType MARINE_BIOLOGIST_POI = PoiType.register("marine_biologist", PoiType.getBlockStates(
            MIBlock.AQUARIUM), 1, 1);
    public static final PoiType MYCOLOGIST_POI = PoiType.register("mycologist", PoiType.getBlockStates(
            Blocks.MUSHROOM_STEM), 1, 1);

    public static final VillagerProfession ENGINEER = VillagerProfession.register("engineer", ENGINEER_POI,
            SoundEvents.VILLAGER_WORK_TOOLSMITH);
    public static final VillagerProfession MARINE_BIOLOGIST = VillagerProfession.register("marine_biologist",
            MARINE_BIOLOGIST_POI, SoundEvents.VILLAGER_WORK_FISHERMAN);
    public static final VillagerProfession MYCOLOGIST = VillagerProfession.register("mycologist",
            MYCOLOGIST_POI, SoundEvents.VILLAGER_WORK_FARMER);

    public static void init(){
        //<editor-fold desc="Profession Name"> </editor-fold>

        //<editor-fold desc="Engineer">
        VillagerTrades.ItemListing[] engineerLevel1 = new VillagerTrades.ItemListing[]{
                new VillagerTrades.EmeraldForItems(MIItem.items.get("lignite_coal"), 8, 8, 6),
                new VillagerTrades.ItemsForEmeralds(MIItem.STEEL_HAMMER, 4, 1, 8, 10)
        };
        VillagerTrades.ItemListing[] engineerLevel2 = new VillagerTrades.ItemListing[]{
                new VillagerTrades.EmeraldForItems(MIItem.items.get("steel_gear"), 2, 8, 15),
                new VillagerTrades.ItemsForEmeralds(MIItem.items.get("forge_hammer"), 24, 1, 8, 20)
        };
        VillagerTrades.ItemListing[] engineerLevel3 = new VillagerTrades.ItemListing[]{
                new VillagerTrades.ItemsForEmeralds(MIItem.DIAMOND_HAMMER, 18, 1, 8, 40),
                new VillagerTrades.ItemsForEmeralds(MIItem.ITEM_STEAM_MINING_DRILL, 34, 1, 8, 40)
        };
        VillagerTrades.ItemListing[] engineerLevel4 = new VillagerTrades.ItemListing[]{
                new VillagerTrades.ItemsForEmeralds(MIItem.items.get("bronze_boiler"), 18, 1, 8, 18),
                new VillagerTrades.ItemsForEmeralds(MIItem.items.get("bronze_compressor"), 18, 1, 8, 18),
                new VillagerTrades.ItemsForEmeralds(MIItem.items.get("bronze_cutting_machine"), 18, 1, 8, 18),
                new VillagerTrades.ItemsForEmeralds(MIItem.items.get("bronze_macerator"), 18, 1, 8, 18),
                new VillagerTrades.ItemsForEmeralds(MIItem.items.get("bronze_mixer"), 18, 1, 8, 18),
                new VillagerTrades.ItemsForEmeralds(MIItem.items.get("bronze_water_pump"), 18, 1, 8, 18)
        };
        VillagerTrades.ItemListing[] engineerLevel5 = new VillagerTrades.ItemListing[]{
                new VillagerTrades.EnchantedItemForEmeralds(MIItem.NETHERITE_HAMMER, 24, 8, 60),
                new VillagerTrades.ItemsForEmeralds(MIItem.ITEM_ELECTRONIC_CIRCUIT_BOARD, 18, 1, 8, 34)
        };
        VillagerTrades.TRADES.put(ENGINEER, toIntMap(ImmutableMap.of(1, engineerLevel1, 2, engineerLevel2,
                3, engineerLevel3, 4, engineerLevel4, 5, engineerLevel5)));
        //</editor-fold>

        //<editor-fold desc="Marine Biologist">
        VillagerTrades.ItemListing[] marineBiologistLevel1 = new VillagerTrades.ItemListing[]{
                new VillagerTrades.ItemsForEmeralds(MIItem.BRINE_SHRIMP_ROE, 6, 48, 8, 6),
                new VillagerTrades.ItemsForEmeralds(MIItem.ANCHOVY_ROE, 10, 48, 8, 6),
                new VillagerTrades.ItemsForEmeralds(MIItem.HERRING_ROE, 12, 24, 8, 6),
                new VillagerTrades.ItemsForEmeralds(MIItem.SARDINE_ROE, 12, 24, 8, 6),
                new VillagerTrades.EmeraldForItems(MIItem.ANCHOVY_LIVE, 16, 8, 4),
                new VillagerTrades.EmeraldForItems(MIItem.HERRING_LIVE, 8, 8, 4),
                new VillagerTrades.EmeraldForItems(MIItem.SARDINE_LIVE, 8, 8, 4)
        };
        VillagerTrades.ItemListing[] marineBiologistLevel2 = new VillagerTrades.ItemListing[]{
                new VillagerTrades.ItemsForEmeralds(MIBlock.RUBBER_BLOCK, 12, 4, 24, 10),
                new VillagerTrades.EmeraldForItems(MIItem.FISH_FEED_HERBIVORE, 2, 32, 2),
                new VillagerTrades.EmeraldForItems(MIItem.FISH_FEED_CARNIVORE, 1, 32, 2)
        };
        VillagerTrades.ItemListing[] marineBiologistLevel3 = new VillagerTrades.ItemListing[]{
                new VillagerTrades.ItemsForEmeralds(MIItem.CARP_ROE, 14, 12, 8, 10),
                new VillagerTrades.ItemsForEmeralds(MIItem.CATFISH_ROE, 14, 12, 8, 10),
                new VillagerTrades.ItemsForEmeralds(MIItem.MACKEREL_ROE, 14, 12, 8, 10),
                new VillagerTrades.ItemsForEmeralds(MIItem.POMFRET_ROE, 14, 12, 8, 10),
                new VillagerTrades.ItemsForEmeralds(MIItem.SKIPJACK_TUNA_ROE, 14, 12, 8, 10),
                new VillagerTrades.ItemsForEmeralds(MIItem.TILAPIA_ROE, 14, 12, 8, 10),
                new VillagerTrades.EmeraldForItems(MIItem.CARP_LIVE, 4, 8, 8),
                new VillagerTrades.EmeraldForItems(MIItem.CATFISH_LIVE, 4, 8, 8),
                new VillagerTrades.EmeraldForItems(MIItem.MACKEREL_LIVE, 4, 8, 8),
                new VillagerTrades.EmeraldForItems(MIItem.POMFRET_LIVE, 4, 8, 8),
                new VillagerTrades.EmeraldForItems(MIItem.SKIPJACK_TUNA_LIVE, 4, 8, 8),
                new VillagerTrades.EmeraldForItems(MIItem.TILAPIA_LIVE, 4, 8, 8)
        };
        VillagerTrades.ItemListing[] marineBiologistLevel4 = new VillagerTrades.ItemListing[]{
                new VillagerTrades.ItemsForEmeralds(MIItem.COD_ROE, 16, 8, 8, 14),
                new VillagerTrades.ItemsForEmeralds(MIItem.SALMON_ROE, 16, 8, 8, 14),
                new VillagerTrades.ItemsForEmeralds(MIItem.STINGRAY_ROE, 16, 8, 8, 14),
                new VillagerTrades.EmeraldForItems(MIItem.COD_LIVE, 2, 8, 11),
                new VillagerTrades.EmeraldForItems(MIItem.SALMON_LIVE, 2, 8, 11),
                new VillagerTrades.EmeraldForItems(MIItem.STINGRAY_LIVE, 2, 8, 11)
        };
        VillagerTrades.ItemListing[] marineBiologistLevel5 = new VillagerTrades.ItemListing[]{
                new VillagerTrades.ItemsForEmeralds(MIItem.YELLOWFIN_TUNA_ROE, 20, 4, 8, 20),
                new VillagerTrades.EmeraldForItems(MIItem.YELLOWFIN_TUNA_LIVE, 1, 8, 25)
        };
        VillagerTrades.TRADES.put(MARINE_BIOLOGIST,toIntMap(ImmutableMap.of(
                1,marineBiologistLevel1,2,marineBiologistLevel2, 3,marineBiologistLevel3,
                4,marineBiologistLevel4,5,marineBiologistLevel5)));
        //</editor-fold>

        //<editor-fold desc="Mycologist">
        VillagerTrades.ItemListing[] mycologistLevel1 = new VillagerTrades.ItemListing[]{
                new VillagerTrades.ItemsForEmeralds(MIItem.BAMBOO_MUSHROOM_SPORE_SAMPLE, 6, 1, 8, 5),
                new VillagerTrades.ItemsForEmeralds(MIItem.BEECH_MUSHROOM_SPORE_SAMPLE, 6, 1, 8, 5),
                new VillagerTrades.ItemsForEmeralds(MIItem.BUTTON_MUSHROOM_SPORE_SAMPLE, 6, 1, 8, 5),
                new VillagerTrades.ItemsForEmeralds(MIItem.OYSTER_MUSHROOM_SPORE_SAMPLE, 6, 1, 8, 5),
                new VillagerTrades.ItemsForEmeralds(MIItem.ROPE_MUSHROOM_SPORE_SAMPLE, 6, 1, 8, 5),
                new VillagerTrades.ItemsForEmeralds(MIItem.STRAW_MUSHROOM_SPORE_SAMPLE, 6, 1, 8, 5),
                new VillagerTrades.EmeraldForItems(MIItem.TEST_TUBE, 4, 8, 4)
        };
        VillagerTrades.ItemListing[] mycologistLevel2 = new VillagerTrades.ItemListing[]{
                new VillagerTrades.ItemsForEmeralds(MIItem.PORTABELLA_MUSHROOM_SPORE_SAMPLE, 6, 1, 8, 8),
                new VillagerTrades.ItemsForEmeralds(MIItem.SPLITGILL_MUSHROOM_SPORE_SAMPLE, 6, 1, 8, 8),
                new VillagerTrades.ItemsForEmeralds(MIItem.SNOW_MUSHROOM_SPORE_SAMPLE, 6, 1, 8, 8),
                new VillagerTrades.EmeraldForItems(MIItem.STERILIZED_GRAIN, 3, 8, 8)
        };
        VillagerTrades.ItemListing[] mycologistLevel3 = new VillagerTrades.ItemListing[]{
                new VillagerTrades.ItemsForEmeralds(MIItem.ENOKITAKE_MUSHROOM_SPORE_SAMPLE, 6, 1, 8, 12),
                new VillagerTrades.ItemsForEmeralds(MIItem.SHIITAKE_MUSHROOM_SPORE_SAMPLE, 6, 1, 8, 12),
                new VillagerTrades.ItemsForEmeralds(MIItem.MAITAKE_MUSHROOM_SPORE_SAMPLE, 6, 1, 8, 12),
                new VillagerTrades.EmeraldForItems(MIItem.GROW_BAG, 8, 8, 8)
        };
        VillagerTrades.ItemListing[] mycologistLevel4 = new VillagerTrades.ItemListing[]{
                new VillagerTrades.ItemsForEmeralds(MIItem.ESSENCE_MUSHROOM_SPORE_SAMPLE, 6, 1, 8, 15),
                new VillagerTrades.EmeraldForItems(Items.CRIMSON_FUNGUS, 8, 8, 11),
                new VillagerTrades.EmeraldForItems(Items.WARPED_FUNGUS, 8, 8, 11)
        };
        VillagerTrades.ItemListing[] mycologistLevel5 = new VillagerTrades.ItemListing[]{
                new VillagerTrades.EmeraldForItems(MIBlock.ESSENCE_BLOCK, 4, 64, 1600)
        };
        VillagerTrades.TRADES.put(MYCOLOGIST,toIntMap(ImmutableMap.of(
                1,mycologistLevel1,2,mycologistLevel2, 3,mycologistLevel3,
                4,mycologistLevel4,5,mycologistLevel5)));
        //</editor-fold>
    }

    private static Int2ObjectMap<VillagerTrades.ItemListing[]> toIntMap(ImmutableMap<Integer, VillagerTrades.ItemListing[]> trades) {
        return new Int2ObjectOpenHashMap<>(trades);
    }
}
