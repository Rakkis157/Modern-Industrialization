/*
 * MIT License
 *
 * Copyright (c) 2020 Azercoco & Technici4n
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package aztech.modern_industrialization;

import aztech.modern_industrialization.api.pipes.item.SpeedUpgrade;
import aztech.modern_industrialization.items.*;
import aztech.modern_industrialization.items.armor.GraviChestPlateItem;
import aztech.modern_industrialization.items.armor.JetpackItem;
import aztech.modern_industrialization.items.armor.QuantumArmorItem;
import aztech.modern_industrialization.items.armor.RubberArmorMaterial;
import aztech.modern_industrialization.items.biotech.FertilizerItem;
import aztech.modern_industrialization.items.diesel_tools.DieselToolItem;
import aztech.modern_industrialization.items.foods.MIFoodProperties;
import aztech.modern_industrialization.items.tools.CrowbarItem;
import aztech.modern_industrialization.items.tools.QuantumSword;
import aztech.modern_industrialization.items.tools.SyringeItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;

import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;

@SuppressWarnings({"unused", "UnstableApiUsage"})
public final class MIItem {

    public static SortedMap<String, Item> items = new TreeMap<>();
    public static SortedMap<String, Consumer<Item>> registrationEvents = new TreeMap<>();
    public static SortedSet<String> handhelds = new TreeSet<>();

    public static Item of(String id) {
        return of(Item::new, id, 64);
    }

    public static Item of(String id, Rarity rarity) {
        return of(Item::new, id, 64, null, false, rarity);
    }

    public static Item of(String id, int maxCount, Rarity rarity) {
        return of(Item::new, id, maxCount, null, false, rarity);
    }

    public static Item of(String id, Consumer<Item> registrationEvent) {
        return of(Item::new, id, 64, registrationEvent);
    }

    public static Item of(String id, int maxCount) {
        return of(Item::new, id, maxCount);
    }

    public static Item of(String id, int maxCount, boolean handheld) {
        return of(Item::new, id, maxCount, null, handheld);
    }

    public static Item of(String id, FoodProperties foodProperties){
        return of((item) -> new Item(new Item.Properties().food(foodProperties)), "foods/" + id, 64);
    }

    public static <T extends Item> T of(Function<? super FabricItemSettings, T> ctor, String id, int maxCount) {
        return of(ctor, id, maxCount, null);
    }

    public static <T extends Item> T of(Function<? super FabricItemSettings, T> ctor, String id, int maxCount, Consumer<Item> registrationEvent) {
        return of(ctor, id, maxCount, registrationEvent, false);
    }

    public static <T extends Item> T of(Function<? super FabricItemSettings, T> ctor, String id, int maxCount, boolean handheld) {
        return of(ctor, id, maxCount, null, handheld);
    }

    public static <T extends Item> T of(Function<? super FabricItemSettings, T> ctor, String id, int maxCount, Consumer<Item> registrationEvent,
                                        boolean handheld) {
        return of(ctor, id, maxCount, registrationEvent, handheld, Rarity.COMMON);
    }

    public static <T extends Item> T of(Function<? super FabricItemSettings, T> ctor, String id, int maxCount, Consumer<Item> registrationEvent,
                                        boolean handheld, Rarity rarity) {
        T item = ctor.apply((FabricItemSettings) new FabricItemSettings().stacksTo(maxCount).tab(ModernIndustrialization.ITEM_GROUP).rarity(rarity));
        if (items.put(id, item) != null) {
            throw new IllegalArgumentException("Item id already taken : " + id);
        }
        if (registrationEvent != null) {
            registrationEvents.put(id, registrationEvent);
        }
        if (handheld) {
            handhelds.add(id);
        }
        return item;
    }

    // Item Categories

    public static Item inAquaculture(String id){
        return of("aquaculture/" + id);
    }
    public static Item inMycology(String id) {
        return of("mycology/" + id);
    }

    public static Item ofGrainSpawn(String id) {
        return inMycology(id + "_grain_spawn");
    }
    public static Item ofGrowBag(String id) {
        return inMycology(id + "_grow_bag");
    }
    public static Item ofMushroom(String id) {
        return inMycology(id);
    }
    public static Item ofFillet(String id) {
        return of(id + "_fillet", MIFoodProperties.RAW_FILLET);
    }
    public static Item ofLive(String id) {
        return inAquaculture(id + "_live");
    }
    public static Item ofRoe(String id) {
        return inAquaculture(id + "_roe");
    }
    public static Item ofSample(String id) {
        return of("samples/sample_" + id);
    }

    //<editor-fold desc="Base Items">
    public static final Item STEEL_UPGRADE = of("steel_upgrade");

    public static final Item ITEM_GUIDE_BOOK = of(GuideBookItem::new, "guidebook", 64);
    public static final Item ITEM_UNCOOKED_STEEL_DUST = of("uncooked_steel_dust");

    public static final Item RUBBER_HELMET = of(s -> new ArmorItem(RubberArmorMaterial.INSTANCE, EquipmentSlot.HEAD, s), "rubber_helmet", 1);
    public static final Item RUBBER_BOOTS = of(s -> new ArmorItem(RubberArmorMaterial.INSTANCE, EquipmentSlot.FEET, s), "rubber_boots", 1);

    public static final Item ITEM_MOTOR = of("motor", (item) -> SpeedUpgrade.LOOKUP.registerForItems((key, vd) -> () -> 2, item));
    public static final Item ITEM_PISTON = of("piston");
    public static final Item ITEM_CONVEYOR = of("conveyor");
    public static final Item ITEM_ROBOT_ARM = of("robot_arm");
    public static final Item ITEM_CIRCUIT = of("analog_circuit");
    public static final Item ITEM_CIRCUIT_BOARD = of("analog_circuit_board");
    public static final Item ITEM_PUMP = of("pump");
    public static final Item ITEM_RESISTOR = of("resistor");
    public static final Item ITEM_CAPACITOR = of("capacitor");
    public static final Item ITEM_INDUCTOR = of("inductor");
    public static final Item ITEM_WOOD_PULP = of("wood_pulp");
    public static final Item ITEM_RUBBER_SHEET = of("rubber_sheet");
    public static final Item ITEM_INVAR_ROTARY_BLADE = of("invar_rotary_blade");

    public static final Item ITEM_ELECTRONIC_CIRCUIT = of("electronic_circuit");
    public static final Item ITEM_DIODE = of("diode");
    public static final Item ITEM_ELECTRONIC_CIRCUIT_BOARD = of("electronic_circuit_board");
    public static final Item ITEM_TRANSISTOR = of("transistor");
    public static final Item ITEM_LARGE_MOTOR = of("large_motor", (item) -> SpeedUpgrade.LOOKUP.registerForItems((key, vd) -> () -> 8, item));

    public static final Item ITEM_LARGE_PUMP = of("large_pump");

    public static final Item ITEM_DIGITAL_CIRCUIT = of("digital_circuit");
    public static final Item ITEM_DIGITAL_CIRCUIT_BOARD = of("digital_circuit_board");
    public static final Item ITEM_OP_AMP = of("op_amp");
    public static final Item ITEM_AND_GATE = of("and_gate");
    public static final Item ITEM_OR_GATE = of("or_gate");
    public static final Item ITEM_NOT_GATE = of("not_gate");

    public static final Item ITEM_PROCESSING_UNIT = of("processing_unit");
    public static final Item ITEM_PROCESSING_UNIT_BOARD = of("processing_unit_board");
    public static final Item ITEM_ARITHMETIC_LOGIC_UNIT = of("arithmetic_logic_unit");
    public static final Item ITEM_RANDOM_ACCESS_MEMORY = of("random_access_memory");
    public static final Item ITEM_MEMORY_MANAGEMENT_UNIT = of("memory_management_unit");

    public static final Item ITEM_QUANTUM_CIRCUIT_BOARD = of("quantum_circuit_board", Rarity.RARE);
    public static final Item ITEM_QUANTUM_CIRCUIT = of("quantum_circuit", Rarity.RARE);
    public static final Item ITEM_QBIT = of("qbit", Rarity.RARE);

    public static final Item ITEM_MONOCRYSTALLINE_SILICON = of("monocrystalline_silicon");
    public static final Item ITEM_SILICON_WAFER = of("silicon_wafer");

    public static final Item BASIC_UPGRADE = of("basic_upgrade");
    public static final Item ADVANCED_UPGRADE = of("advanced_upgrade");
    public static final Item TURBO_UPGRADE = of("turbo_upgrade", Rarity.UNCOMMON);
    public static final Item HIGHLY_ADVANCED_UPGRADE = of("highly_advanced_upgrade", Rarity.RARE);
    public static final Item QUANTUM_UPGRADE = of("quantum_upgrade", 1, Rarity.EPIC);

    public static final Item ADVANCED_MOTOR = of("advanced_motor", (item) -> SpeedUpgrade.LOOKUP.registerForItems((key, vd) -> () -> 32, item));
    public static final Item LARGE_ADVANCED_MOTOR = of("large_advanced_motor",
            (item) -> SpeedUpgrade.LOOKUP.registerForItems((key, vd) -> () -> 64, item));
    public static final Item ADVANCED_PUMP = of("advanced_pump");
    public static final Item LARGE_ADVANCED_PUMP = of("large_advanced_pump");

    public static final Item MIXED_INGOT_BLASTPROOF = of("mixed_ingot_blastproof");
    public static final Item MIXED_INGOT_IRIDIUM = of("mixed_ingot_iridium");

    public static final Item MIXED_PLATE_NUCLEAR = of("mixed_plate_nuclear");

    public static final Item AIR_INTAKE = of("air_intake", 1);

    public static final Item ITEM_PACKER_BLOCK_TEMPLATE = of("packer_block_template", 1, Rarity.RARE);
    public static final Item ITEM_PACKER_DOUBLE_INGOT_TEMPLATE = of("packer_double_ingot_template", 1, Rarity.RARE);

    public static final Item ITEM_SCREWDRIVER = of("screwdriver", 1, true);
    public static final Item ITEM_WRENCH = of("wrench", 1, true);
    public static final JetpackItem ITEM_DIESEL_JETPACK = of(JetpackItem::new, "diesel_jetpack", 1,
            (item) -> FluidStorage.ITEM.registerForItems((stack, ctx) -> new FluidFuelItemHelper.ItemStorage(JetpackItem.CAPACITY, ctx), item));
    public static final DieselToolItem ITEM_DIESEL_CHAINSAW = of(s -> new DieselToolItem(s, 12), "diesel_chainsaw", 1,
            (item) -> FluidStorage.ITEM.registerForItems((stack, ctx) -> new FluidFuelItemHelper.ItemStorage(DieselToolItem.CAPACITY, ctx), item),
            true);

    public static final DieselToolItem ITEM_DIESEL_MINING_DRILL = of(s -> new DieselToolItem(s, 7), "diesel_mining_drill", 1,
            (item) -> FluidStorage.ITEM.registerForItems((stack, ctx) -> new FluidFuelItemHelper.ItemStorage(DieselToolItem.CAPACITY, ctx), item),
            true);

    public static final SteamDrillItem ITEM_STEAM_MINING_DRILL = of(SteamDrillItem::new, "steam_mining_drill", 1, true);
    public static final Item ITEM_CROWBAR = of(CrowbarItem::new, "crowbar", 1, true);

    public static final Item COOLING_CELL = of("cooling_cell");

    public static final GraviChestPlateItem GRAVI_CHEST_PLATE = of(GraviChestPlateItem::new, "gravichestplate", 1);
    public static final QuantumArmorItem QUANTUM_BOOTS = of(s -> new QuantumArmorItem(EquipmentSlot.FEET, s), "quantum_boots", 1);
    public static final QuantumArmorItem QUANTUM_LEGGINGS = of(s -> new QuantumArmorItem(EquipmentSlot.LEGS, s), "quantum_leggings", 1);
    public static final QuantumArmorItem QUANTUM_CHESTPLATE = of(s -> new QuantumArmorItem(EquipmentSlot.CHEST, s), "quantum_chestplate", 1);
    public static final QuantumArmorItem QUANTUM_HELMET = of(s -> new QuantumArmorItem(EquipmentSlot.HEAD, s), "quantum_helmet", 1);
    public static final QuantumSword QUANTUM_SWORD = of(QuantumSword::new, "quantum_sword", 1, true);

    public static final Item ULTRADENSE_METAL_BALL = of("ultradense_metal_ball");
    public static final Item SINGULARITY = of("singularity", Rarity.EPIC);

    public static final ForgeTool IRON_HAMMER = new ForgeTool(Tiers.IRON, "iron_hammer");
    public static final ForgeTool STEEL_HAMMER = new ForgeTool(ForgeTool.STEEL, "steel_hammer");
    public static final ForgeTool DIAMOND_HAMMER = new ForgeTool(Tiers.DIAMOND, "diamond_hammer");
    public static final ForgeTool NETHERITE_HAMMER = new ForgeTool(Tiers.NETHERITE, "netherite_hammer");
        //</editor-fold>

    //Biotech
//    public static final Item BEVERAGE_BOTTLE = of("beverage_bottle");
    public static final Item CALCITE_DUST = of("calcite_dust");
    public static final Item CALCIUM_CHLORIDE_DUST = of("calcium_chloride_dust");
    public static final Item CALCIUM_NITRATE_DUST = of("calcium_nitrate_dust");
    public static final Item COMPOST = of(FertilizerItem::new, "compost", 64,true);
    public static final Item CRUSHED_LEAVES = of("crushed_leaves");
    public static final Item PHOSPHORITE_DUST = of("phosphorite_dust");
    public static final Item SYRINGE = of(SyringeItem::new, "syringe", 64, true);
    public static final Item TEST_TUBE = of("test_tube");
    public static final Item WAX = of("wax");
    public static final Item WOOD_ASH = of("wood_ash");

    //Agriculture
    public static final Item STRAW = of("straw");
    public static final Item SUNFLOWER_HULLS = of("sunflower_hulls");
    public static final Item SUNFLOWER_SEEDS = of("sunflower_seeds");
    public static final Item WHEAT_BRAN = of("wheat_bran");
    public static final Item WHEAT_DOUGH = of("wheat_dough");
    public static final Item WHEAT_FLOUR = of("wheat_flour");
    public static final Item WHEAT_GRAIN = of("wheat_grain");
    public static final Item WHEAT_WHOLE_FLOUR = of("wheat_whole_flour");

    //Algology
    public static final Item BLUE_ALGAE = of("blue_algae");
    public static final Item BROWN_ALGAE = of("brown_algae");
    public static final Item GREEN_ALGAE = of("green_algae");
    public static final Item CELLULOSE_FIBRES = of("cellulose_fibres");

    //<editor-fold desc="Aquaculture">
    public static final Item CRUSHED_FISH = inAquaculture("crushed_fish");
    public static final Item FISH_FEED_CARNIVORE = inAquaculture("carnivorous_fish_feed");
    public static final Item FISH_FEED_HERBIVORE = inAquaculture("herbivorous_fish_feed");
    public static final Item FISH_MEAL = inAquaculture("fish_meal");
    public static final Item FISH_NET = inAquaculture("fish_net");

    //Seafood
    public static final Item ANCHOVY = of("anchovy", MIFoodProperties.RAW_FISH);
    public static final Item ANCHOVY_LIVE = ofLive("anchovy");
    public static final Item ANCHOVY_ROE = ofRoe("anchovy");
    public static final Item BRINE_SHRIMP = of("brine_shrimp", MIFoodProperties.RAW_FISH);
    public static final Item BRINE_SHRIMP_LIVE = ofLive("brine_shrimp");
    public static final Item BRINE_SHRIMP_ROE = ofRoe("brine_shrimp");
    public static final Item CARP = of("carp", MIFoodProperties.RAW_FISH);
    public static final Item CARP_LIVE = ofLive("carp");
    public static final Item CARP_ROE = ofRoe("carp");
    public static final Item CARP_FILLET = ofFillet("carp");
    public static final Item CATFISH = of("catfish", MIFoodProperties.RAW_FISH);
    public static final Item CATFISH_LIVE = ofLive("catfish");
    public static final Item CATFISH_ROE = ofRoe("catfish");
    public static final Item CATFISH_FILLET = ofFillet("catfish");
    public static final Item COD = of("cod", MIFoodProperties.RAW_FISH);
    public static final Item COD_LIVE = ofLive("cod");
    public static final Item COD_ROE = ofRoe("cod");
    public static final Item COD_FILLET = ofFillet("cod");
    public static final Item HERRING = of("herring", MIFoodProperties.RAW_FISH);
    public static final Item HERRING_LIVE = ofLive("herring");
    public static final Item HERRING_ROE = ofRoe("herring");
    public static final Item HERRING_FILLET = ofFillet("herring");
    public static final Item MACKEREL = of("mackerel", MIFoodProperties.RAW_FISH);
    public static final Item MACKEREL_LIVE = ofLive("mackerel");
    public static final Item MACKEREL_ROE = ofRoe("mackerel");
    public static final Item MACKEREL_FILLET = ofFillet("mackerel");
    public static final Item POMFRET = of("pomfret", MIFoodProperties.RAW_FISH);
    public static final Item POMFRET_LIVE = ofLive("pomfret");
    public static final Item POMFRET_ROE = ofRoe("pomfret");
    public static final Item POMFRET_FILLET = ofFillet("pomfret");
    public static final Item SALMON = of("salmon", MIFoodProperties.RAW_FISH);
    public static final Item SALMON_LIVE = ofLive("salmon");
    public static final Item SALMON_ROE = ofRoe("salmon");
    public static final Item SALMON_FILLET = ofFillet("salmon");
    public static final Item SARDINE = of("sardine", MIFoodProperties.RAW_FISH);
    public static final Item SARDINE_LIVE = ofLive("sardine");
    public static final Item SARDINE_ROE = ofRoe("sardine");
    public static final Item SARDINE_FILLET = ofFillet("sardine");
    public static final Item SHRIMP = of("shrimp", MIFoodProperties.RAW_FISH);
    public static final Item SHRIMP_LIVE = ofLive("shrimp");
    public static final Item SHRIMP_ROE = ofRoe("shrimp");
    public static final Item SKIPJACK_TUNA = of("skipjack_tuna", MIFoodProperties.RAW_FISH);
    public static final Item SKIPJACK_TUNA_LIVE = ofLive("skipjack_tuna");
    public static final Item SKIPJACK_TUNA_ROE = ofRoe("skipjack_tuna");
    public static final Item SKIPJACK_TUNA_FILLET = ofFillet("skipjack_tuna");
    public static final Item STINGRAY = of("stingray", MIFoodProperties.RAW_FISH);
    public static final Item STINGRAY_LIVE = ofLive("stingray");
    public static final Item STINGRAY_ROE = ofRoe("stingray");
    public static final Item STINGRAY_FILLET = ofFillet("stingray");
    public static final Item TILAPIA = of("tilapia", MIFoodProperties.RAW_FISH);
    public static final Item TILAPIA_LIVE = ofLive("tilapia");
    public static final Item TILAPIA_ROE = ofRoe("tilapia");
    public static final Item TILAPIA_FILLET = ofFillet("tilapia");
    public static final Item YELLOWFIN_TUNA = of("yellowfin_tuna", MIFoodProperties.RAW_FISH);
    public static final Item YELLOWFIN_TUNA_LIVE = ofLive("yellowfin_tuna");
    public static final Item YELLOWFIN_TUNA_ROE = ofRoe("yellowfin_tuna");
    public static final Item YELLOWFIN_TUNA_FILLET = ofFillet("yellowfin_tuna");
    //</editor-fold>

    //Food
    public static final Item ALGAE_BAR = of("algae_bar", MIFoodProperties.ALGAE_BAR);
    public static final Item ALGAE_MIX = of("algae_mix");
    public static final Item COOKED_SALMON_FILLET = of("cooked_salmon_fillet", MIFoodProperties.COOKED_SALMON_FILLET);
    public static final Item SALTED_BEEF = of("salted_beef", MIFoodProperties.SALTED_BEEF);
    public static final Item SALTED_MUTTON = of("salted_mutton", MIFoodProperties.SALTED_MUTTON);
    public static final Item SALTED_PORK = of("salted_pork", MIFoodProperties.SALTED_PORK);

    //<editor-fold desc="Mycology">
    public static final Item GROW_BAG = inMycology("mushroom_grow_bag");
    public static final Item STERILIZED_GRAIN = inMycology("sterilized_grain");

    //Mycology: Mushrooms
    public static final Item BAMBOO_MUSHROOM = ofMushroom("bamboo_mushroom");
    public static final Item BAMBOO_MUSHROOM_GRAIN_SPAWN = ofGrainSpawn("bamboo_mushroom");
    public static final Item BAMBOO_MUSHROOM_GROW_BAG = ofGrowBag("bamboo_mushroom");
    public static final Item BAMBOO_MUSHROOM_SPORE_SAMPLE = ofSample("bamboo_mushroom");
    public static final Item BEECH_MUSHROOM = ofMushroom("beech_mushroom");
    public static final Item BEECH_MUSHROOM_GRAIN_SPAWN = ofGrainSpawn("beech_mushroom");
    public static final Item BEECH_MUSHROOM_GROW_BAG = ofGrowBag("beech_mushroom");
    public static final Item BEECH_MUSHROOM_SPORE_SAMPLE = ofSample("beech_mushroom");
    public static final Item BROWN_MUSHROOM_GRAIN_SPAWN = ofGrainSpawn("brown_mushroom");
    public static final Item BROWN_MUSHROOM_GROW_BAG = ofGrowBag("brown_mushroom");
    public static final Item BROWN_MUSHROOM_SPORE_SAMPLE = ofSample("brown_mushroom");
    public static final Item BUTTON_MUSHROOM = ofMushroom("button_mushroom");
    public static final Item BUTTON_MUSHROOM_GRAIN_SPAWN = ofGrainSpawn("button_mushroom");
    public static final Item BUTTON_MUSHROOM_GROW_BAG = ofGrowBag("button_mushroom");
    public static final Item BUTTON_MUSHROOM_SPORE_SAMPLE = ofSample("button_mushroom");
    public static final Item ENOKITAKE_MUSHROOM = ofMushroom("enokitake_mushroom");
    public static final Item ENOKITAKE_MUSHROOM_GRAIN_SPAWN = ofGrainSpawn("enokitake_mushroom");
    public static final Item ENOKITAKE_MUSHROOM_GROW_BAG = ofGrowBag("enokitake_mushroom");
    public static final Item ENOKITAKE_MUSHROOM_SPORE_SAMPLE = ofSample("enokitake_mushroom");
    public static final Item ESSENCE_MUSHROOM = ofMushroom("essence_mushroom");
    public static final Item ESSENCE_MUSHROOM_GRAIN_SPAWN = ofGrainSpawn("essence_mushroom");
    public static final Item ESSENCE_MUSHROOM_GROW_BAG = ofGrowBag("essence_mushroom");
    public static final Item ESSENCE_MUSHROOM_SPORE_SAMPLE = ofSample("essence_mushroom");
    public static final Item MAITAKE_MUSHROOM = ofMushroom("maitake_mushroom");
    public static final Item MAITAKE_MUSHROOM_GRAIN_SPAWN = ofGrainSpawn("maitake_mushroom");
    public static final Item MAITAKE_MUSHROOM_GROW_BAG = ofGrowBag("maitake_mushroom");
    public static final Item MAITAKE_MUSHROOM_SPORE_SAMPLE = ofSample("maitake_mushroom");
    public static final Item NETHER_WART_GRAIN_SPAWN = ofGrainSpawn("nether_wart");
    public static final Item NETHER_WART_GROW_BAG = ofGrowBag("nether_wart");
    public static final Item NETHER_WART_SPORE_SAMPLE = ofSample("nether_wart");
    public static final Item OYSTER_MUSHROOM = ofMushroom("oyster_mushroom");
    public static final Item OYSTER_MUSHROOM_GRAIN_SPAWN = ofGrainSpawn("oyster_mushroom");
    public static final Item OYSTER_MUSHROOM_GROW_BAG = ofGrowBag("oyster_mushroom");
    public static final Item OYSTER_MUSHROOM_SPORE_SAMPLE = ofSample("oyster_mushroom");
    public static final Item PORTABELLA_MUSHROOM = ofMushroom("portabella_mushroom");
    public static final Item PORTABELLA_MUSHROOM_GRAIN_SPAWN = ofGrainSpawn("portabella_mushroom");
    public static final Item PORTABELLA_MUSHROOM_GROW_BAG = ofGrowBag("portabella_mushroom");
    public static final Item PORTABELLA_MUSHROOM_SPORE_SAMPLE = ofSample("portabella_mushroom");
    public static final Item RED_MUSHROOM_GRAIN_SPAWN = ofGrainSpawn("red_mushroom");
    public static final Item RED_MUSHROOM_GROW_BAG = ofGrowBag("red_mushroom");
    public static final Item RED_MUSHROOM_SPORE_SAMPLE = ofSample("red_mushroom");
    public static final Item ROPE_MUSHROOM = ofMushroom("rope_mushroom");
    public static final Item ROPE_MUSHROOM_GRAIN_SPAWN = ofGrainSpawn("rope_mushroom");
    public static final Item ROPE_MUSHROOM_GROW_BAG = ofGrowBag("rope_mushroom");
    public static final Item ROPE_MUSHROOM_SPORE_SAMPLE = ofSample("rope_mushroom");
    public static final Item SHIITAKE_MUSHROOM = ofMushroom("shiitake_mushroom");
    public static final Item SHIITAKE_MUSHROOM_GRAIN_SPAWN = ofGrainSpawn("shiitake_mushroom");
    public static final Item SHIITAKE_MUSHROOM_GROW_BAG = ofGrowBag("shiitake_mushroom");
    public static final Item SHIITAKE_MUSHROOM_SPORE_SAMPLE = ofSample("shiitake_mushroom");
    public static final Item SNOW_MUSHROOM = ofMushroom("snow_mushroom");
    public static final Item SNOW_MUSHROOM_GRAIN_SPAWN = ofGrainSpawn("snow_mushroom");
    public static final Item SNOW_MUSHROOM_GROW_BAG = ofGrowBag("snow_mushroom");
    public static final Item SNOW_MUSHROOM_SPORE_SAMPLE = ofSample("snow_mushroom");
    public static final Item SPLITGILL_MUSHROOM = ofMushroom("splitgill_mushroom");
    public static final Item SPLITGILL_MUSHROOM_GRAIN_SPAWN = ofGrainSpawn("splitgill_mushroom");
    public static final Item SPLITGILL_MUSHROOM_GROW_BAG = ofGrowBag("splitgill_mushroom");
    public static final Item SPLITGILL_MUSHROOM_SPORE_SAMPLE = ofSample("splitgill_mushroom");
    public static final Item STRAW_MUSHROOM = ofMushroom("straw_mushroom");
    public static final Item STRAW_MUSHROOM_GRAIN_SPAWN = ofGrainSpawn("straw_mushroom");
    public static final Item STRAW_MUSHROOM_GROW_BAG = ofGrowBag("straw_mushroom");
    public static final Item STRAW_MUSHROOM_SPORE_SAMPLE = ofSample("straw_mushroom");
    //</editor-fold>

    public static final Item YEAST = of("yeast");

    //Samples
    public static final Item SAMPLE_BLOOD = ofSample("blood");
    public static final Item SAMPLE_BLUE_ALGAE = ofSample("blue_algae");
    public static final Item SAMPLE_BROWN_ALGAE = ofSample("brown_algae");
    public static final Item SAMPLE_GREEN_ALGAE = ofSample("green_algae");
    public static final Item SAMPLE_AMYLASE = ofSample("amylase");
    public static final Item SAMPLE_BEEF = ofSample("beef");
    public static final Item SAMPLE_CHICKEN = ofSample("chicken");
    public static final Item SAMPLE_COD = ofSample("cod");
    public static final Item SAMPLE_MUTTON = ofSample("mutton");
    public static final Item SAMPLE_PORK = ofSample("pork");
    public static final Item SAMPLE_RABBIT = ofSample("rabbit");
    public static final Item SAMPLE_SALMON = ofSample("salmon");
    public static final Item SAMPLE_SPIDER = ofSample("spider");
    public static final Item SAMPLE_SQUID = ofSample("squid");
    public static final Item SAMPLE_YEAST = ofSample("yeast");

    //Templates
    public static final Item ITEM_INDUSTRIAL_OVEN_LOAF_TEMPLATE = of("industrial_oven_loaf_template");
}
