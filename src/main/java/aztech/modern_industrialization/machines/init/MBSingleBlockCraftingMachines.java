package aztech.modern_industrialization.machines.init;

import aztech.modern_industrialization.machines.components.sync.EnergyBar;
import aztech.modern_industrialization.machines.components.sync.ProgressBar;
import aztech.modern_industrialization.machines.components.sync.RecipeEfficiencyBar;

public class MBSingleBlockCraftingMachines {
    public static void init(){
        // @formatter:off
        //Biotech


        SingleBlockCraftingMachines.registerMachineTiers(
                "aquarium", MBMachineRecipeTypes.AQUARIUM, 4, 2, 2, 2, guiParams -> {},
                new ProgressBar.Parameters(103, 33, "arrow"), new RecipeEfficiencyBar.Parameters(50, 66), new EnergyBar.Parameters(15, 34),
                items -> items.addSlots(62, 27, 2, 2).addSlots(129, 27, 2, 1), fluids -> fluids.addSlots(42, 27, 2, 1).addSlots(149, 27, 2, 1),
                true, false, true,
                TIER_ELECTRIC, 24
        );
        SingleBlockCraftingMachines.registerMachineTiers(
                "bioreactor", MBMachineRecipeTypes.BIOREACTOR, 3, 3, 3, 3, guiParams -> {},
                new ProgressBar.Parameters(88, 35, "centrifuge"), new RecipeEfficiencyBar.Parameters(50, 66), new EnergyBar.Parameters(12, 35),
                items -> items.addSlots(30, 27, 1, 3).addSlots(116, 27, 1, 3), fluids -> fluids.addSlots(30, 47, 1, 3).addSlots(116, 47, 1, 3),
                true, true, false,
                TIER_ELECTRIC, 24
        );
        SingleBlockCraftingMachines.registerMachineTiers(
                "extractor", MBMachineRecipeTypes.EXTRACTOR, 1, 4, 0, 0, guiParams -> {},
                new ProgressBar.Parameters(77, 33, "wiremill"), new RecipeEfficiencyBar.Parameters(38, 66), DEFAULT_ENERGY_BAR,
                items -> items.addSlot(56, 35).addSlots(102, 27, 2, 2), fluids -> {},
                true, false, false,
                TIER_ELECTRIC, 16
        );
        SingleBlockCraftingMachines.registerMachineTiers(
                "filter", MBMachineRecipeTypes.FILTER, 1, 4, 0, 0, guiParams -> {},
                new ProgressBar.Parameters(77, 33, "macerate"), new RecipeEfficiencyBar.Parameters(38, 66), DEFAULT_ENERGY_BAR,
                items -> items.addSlot(56, 47).addSlots(102, 35, 2, 2), fluids -> fluids.addSlot(62, 15).addSlots(102,15, 2, 1),
                true, true, false,
                TIER_ELECTRIC, 16
        );
    }

    private static final EnergyBar.Parameters DEFAULT_ENERGY_BAR = new EnergyBar.Parameters(18, 34);
    private static final int TIER_BRONZE = 1, TIER_STEEL = 2, TIER_ELECTRIC = 4;
}
