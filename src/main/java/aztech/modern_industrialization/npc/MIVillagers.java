package aztech.modern_industrialization.npc;

import aztech.modern_industrialization.MIBlock;
import aztech.modern_industrialization.MIItem;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;

public class MIVillagers {
    public static final PoiType ENGINEER_POI = PoiType.register("engineer", PoiType.getBlockStates(
            MIBlock.FORGE_HAMMER), 1, 1);

    public static final VillagerProfession ENGINEER = VillagerProfession.register("engineer", ENGINEER_POI,
            SoundEvents.VILLAGER_WORK_TOOLSMITH);

    public static void init() {
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
    }

    private static Int2ObjectMap<VillagerTrades.ItemListing[]> toIntMap(ImmutableMap<Integer, VillagerTrades.ItemListing[]> trades) {
        return new Int2ObjectOpenHashMap<>(trades);
    }
}
