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
package aztech.modern_industrialization.machines.init;

import static aztech.modern_industrialization.machines.models.MachineCasings.CLEAN_STAINLESS_STEEL;
import static aztech.modern_industrialization.machines.multiblocks.HatchType.*;

import aztech.modern_industrialization.MIBlock;
import aztech.modern_industrialization.MIFluids;
import aztech.modern_industrialization.api.FluidFuelRegistry;
import aztech.modern_industrialization.compat.rei.Rectangle;
import aztech.modern_industrialization.compat.rei.machines.MachineCategoryParams;
import aztech.modern_industrialization.compat.rei.machines.ReiMachineRecipes;
import aztech.modern_industrialization.inventory.SlotPositions;
import aztech.modern_industrialization.machines.MachineScreenHandlers;
import aztech.modern_industrialization.machines.SyncedComponent;
import aztech.modern_industrialization.machines.blockentities.multiblocks.*;
import aztech.modern_industrialization.machines.components.sync.CraftingMultiblockGui;
import aztech.modern_industrialization.machines.components.sync.ProgressBar;
import aztech.modern_industrialization.machines.models.MachineCasing;
import aztech.modern_industrialization.machines.models.MachineCasings;
import aztech.modern_industrialization.machines.models.MachineModels;
import aztech.modern_industrialization.machines.multiblocks.*;
import aztech.modern_industrialization.machines.recipe.MachineRecipe;
import aztech.modern_industrialization.machines.recipe.MachineRecipeType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;

// @formatter:off
@SuppressWarnings("rawtypes")
public class MultiblockMachines {
    public static BlockEntityType COKE_OVEN;
    public static BlockEntityType STEAM_BLAST_FURNACE;
    public static BlockEntityType STEAM_QUARRY;
    public static BlockEntityType ELECTRIC_BLAST_FURNACE;
    public static BlockEntityType LARGE_STEAM_BOILER;
    public static BlockEntityType ADVANCED_LARGE_STEAM_BOILER;
    public static BlockEntityType HIGH_PRESSURE_LARGE_STEAM_BOILER;
    public static BlockEntityType HIGH_PRESSURE_ADVANCED_LARGE_STEAM_BOILER;
    public static BlockEntityType ELECTRIC_QUARRY;
    public static BlockEntityType DRILLING_RIG;
    public static BlockEntityType VACUUM_FREEZER;
    public static BlockEntityType DISTILLATION_TOWER;
    public static BlockEntityType LARGE_DIESEL_GENERATOR;
    public static BlockEntityType LARGE_STEAM_TURBINE;
    public static BlockEntityType HEAT_EXCHANGER;
    public static BlockEntityType PRESSURIZER;
    public static BlockEntityType IMPLOSION_COMPRESSOR;
    public static BlockEntityType NUCLEAR_REACTOR;
    public static BlockEntityType LARGE_TANK;
    public static BlockEntityType FUSION_REACTOR;
    public static BlockEntityType PLASMA_TURBINE;

    //Biotech
    public static BlockEntityType AQUACULTURE_POND;
    public static BlockEntityType COMPOSTER;
    public static BlockEntityType CROP_PLANTER;
    public static BlockEntityType GROWTH_CHAMBER;
    public static BlockEntityType INDUSTRIAL_OVEN;
    public static BlockEntityType INDUSTRIAL_PRESS;
    public static BlockEntityType TREE_PLANTER;

    private static SimpleMember invarCasings;

    private static SimpleMember bronzePlatedBricks;
    private static SimpleMember bronzePipe;

    private static SimpleMember steelCasing;
    private static SimpleMember steelPipe;

    private static SimpleMember frostproofMachineCasing;

    private static SimpleMember stainlessSteelClean;
    private static SimpleMember stainlessSteelPipe;

    private static SimpleMember titaniumCasing;
    private static SimpleMember titaniumPipe;


    private static SimpleMember blastProofCasing;

    private static SimpleMember highlyAdvancedHull;
    private static SimpleMember fusionChamber;

    private static SimpleMember plasmaHandlingIridium;
    private static SimpleMember iridiumPipe;

    private static final HatchFlags fluidInputs = new HatchFlags.Builder().with(FLUID_INPUT).build();
    private static final HatchFlags energyOutput = new HatchFlags.Builder().with(ENERGY_OUTPUT).build();
    private static final HatchFlags energyInput = new HatchFlags.Builder().with(ENERGY_INPUT).build();

    //Shape Placement Methods
    private static void addSideX(@NotNull ShapeTemplate.Builder builder, int x1, int x2, int y, int z
            , SimpleMember simpleMember, HatchFlags hatchFlags){
        builder.add(x1, y, z, simpleMember, hatchFlags);
        builder.add(x2, y, z, simpleMember, hatchFlags);
    }

    private static void addSideZ(@NotNull ShapeTemplate.Builder builder, int x, int y, int z1, int z2
            , SimpleMember simpleMember, HatchFlags hatchFlags){
        builder.add(x, y, z1, simpleMember, hatchFlags);
        builder.add(x, y, z2, simpleMember, hatchFlags);
    }

    private static void addPlane(ShapeTemplate.Builder builder, int x1, int x2, int y, int z1, int z2
            , SimpleMember simpleMember, HatchFlags hatchFlags){
        int placeholder;
        if (x1 > x2){
            placeholder = x1;
            x1 = x2;
            x2 = placeholder;
        }
        if (z1 > z2){
            placeholder = z1;
            z1 = z2;
            z2 = placeholder;
        }

        for(int x = x1; x <= x2; x++){
            for(int z = z1; z <= z2; z++){
                builder.add(x, y, z, simpleMember, hatchFlags);
            }
        }
    }

    private static void addRectangle(ShapeTemplate.Builder builder, int x1, int x2, int y, int z1, int z2
            , SimpleMember simpleMember, HatchFlags hatchFlags){
        int placeholder;

        if (x1 > x2){
            placeholder = x1;
            x1 = x2;
            x2 = placeholder;
        }
        if (z1 > z2){
            placeholder = z1;
            z1 = z2;
            z2 = placeholder;
        }

        for(int x = x1; x <= x2; x++){
            builder.add(x, y, z1, simpleMember, hatchFlags);
            if(z1 != z2){
                builder.add(x, y, z2, simpleMember, hatchFlags);
            }
        }

        if((z2 - z1) >= 3){
            for(int z = (z1 + 1); z <= (z2 - 1); z++){
                builder.add(x1, y, z, simpleMember, hatchFlags);
                if (x1 != x2){
                    builder.add(x2, y, z, simpleMember, hatchFlags);
                }
            }
        }
    }



    //Multiblock Shapes
    private static void cokeOven() {
        SimpleMember bricks = SimpleMember.forBlock(Blocks.BRICKS);
        HatchFlags cokeOvenHatches = new HatchFlags.Builder().with(ITEM_INPUT).with(ITEM_OUTPUT).with(FLUID_INPUT).with(FLUID_OUTPUT).build();
        ShapeTemplate cokeOvenShape = new ShapeTemplate.Builder(MachineCasings.BRICKS).add3by3Levels(-1, 1, bricks, cokeOvenHatches).build();
        COKE_OVEN = MachineRegistrationHelper.registerMachine("coke_oven",
                bet -> new SteamCraftingMultiblockBlockEntity(bet, "coke_oven", cokeOvenShape, MIMachineRecipeTypes.COKE_OVEN));
        ReiMachineRecipes.registerMultiblockShape("coke_oven", cokeOvenShape);
    }

    private static void steamBlastFurnace() {
        SimpleMember fireclayBricks = SimpleMember.forBlock(MIBlock.BLOCK_FIRE_CLAY_BRICKS);
        HatchFlags sbfHatches = new HatchFlags.Builder().with(ITEM_INPUT, ITEM_OUTPUT, FLUID_INPUT, FLUID_OUTPUT).build();
        ShapeTemplate sbfShape = new ShapeTemplate.Builder(MachineCasings.FIREBRICKS).add3by3Levels(-1, 2, fireclayBricks, sbfHatches).build();
        STEAM_BLAST_FURNACE = MachineRegistrationHelper.registerMachine("steam_blast_furnace",
                bet -> new SteamCraftingMultiblockBlockEntity(bet, "steam_blast_furnace", sbfShape, MIMachineRecipeTypes.BLAST_FURNACE));
        ReiMachineRecipes.registerMultiblockShape("steam_blast_furnace", sbfShape);
    }

    private static void electricBlastFurnace() {
        ELECTRIC_BLAST_FURNACE = MachineRegistrationHelper.registerMachine("electric_blast_furnace",
                ElectricBlastFurnaceBlockEntity::new);
        ElectricBlastFurnaceBlockEntity.registerReiShapes();
    }

    private static void steamBoilers() {

        HatchFlags slbHatchFlags = new HatchFlags.Builder().with(ITEM_INPUT, FLUID_INPUT, FLUID_OUTPUT).build();
        ShapeTemplate largeSteamBoilerShape = new ShapeTemplate.Builder(MachineCasings.HEATPROOF).add3by3(-1, invarCasings, false, slbHatchFlags)
                .add3by3(0, bronzePlatedBricks, true, null).add3by3(1, bronzePlatedBricks, true, null).add3by3(2, bronzePlatedBricks, false, null)
                .add(0, 0, 1, bronzePipe, null).add(0, 1, 1, bronzePipe, null).build();


        LARGE_STEAM_BOILER = MachineRegistrationHelper.registerMachine("large_steam_boiler",
                bet -> new SteamBoilerMultiblockBlockEntity(bet, largeSteamBoilerShape, "large_steam_boiler",
                        256, false));
        ReiMachineRecipes.registerMultiblockShape("large_steam_boiler", largeSteamBoilerShape);

        ShapeTemplate advancedLargeSteamBoilerShape = new ShapeTemplate.Builder(MachineCasings.HEATPROOF)
                .add3by3(-2, invarCasings, false, slbHatchFlags)
                .add3by3(-1, bronzePlatedBricks, true, null)
                .add3by3(0, bronzePlatedBricks, true, null)
                .add3by3(1, bronzePlatedBricks, true, null)
                .add3by3(2, bronzePlatedBricks, false, null)
                .add(0, -1, 1, bronzePipe, null)
                .add(0, 0, 1, bronzePipe, null)
                .add(0, 1, 1, bronzePipe, null).build();

        ADVANCED_LARGE_STEAM_BOILER = MachineRegistrationHelper.registerMachine("advanced_large_steam_boiler",
                bet -> new SteamBoilerMultiblockBlockEntity(bet, advancedLargeSteamBoilerShape, "advanced_large_steam_boiler",
                        1024, false));
        ReiMachineRecipes.registerMultiblockShape("advanced_large_steam_boiler", advancedLargeSteamBoilerShape);


        ShapeTemplate highPressureLargeSteamBoilerShape = new ShapeTemplate.Builder(MachineCasings.HEATPROOF)
                .add3by3(-1, invarCasings, false, slbHatchFlags)
                .add3by3(0, stainlessSteelClean, true, null)
                .add3by3(1, stainlessSteelClean, true, null)
                .add3by3(2, stainlessSteelClean, false, null)
                .add(0, 0, 1, stainlessSteelPipe, null)
                .add(0, 1, 1, stainlessSteelPipe, null).build();

        HIGH_PRESSURE_LARGE_STEAM_BOILER = MachineRegistrationHelper.registerMachine("high_pressure_large_steam_boiler",
                bet -> new SteamBoilerMultiblockBlockEntity(bet, highPressureLargeSteamBoilerShape, "high_pressure_large_steam_boiler",
                        2048, true));
        ReiMachineRecipes.registerMultiblockShape("high_pressure_large_steam_boiler", highPressureLargeSteamBoilerShape);

        ShapeTemplate highPressureAdvancedLargeSteamBoilerShape = new ShapeTemplate.Builder(MachineCasings.HEATPROOF)
                .add3by3(-2, invarCasings, false, slbHatchFlags)
                .add3by3(-1, stainlessSteelClean, true, null)
                .add3by3(0, stainlessSteelClean, true, null)
                .add3by3(1, stainlessSteelClean, true, null)
                .add3by3(2, stainlessSteelClean, false, null)
                .add(0, -1, 1, stainlessSteelPipe, null)
                .add(0, 0, 1, stainlessSteelPipe, null)
                .add(0, 1, 1, stainlessSteelPipe, null).build();

        HIGH_PRESSURE_ADVANCED_LARGE_STEAM_BOILER = MachineRegistrationHelper.registerMachine("high_pressure_advanced_large_steam_boiler",
                bet -> new SteamBoilerMultiblockBlockEntity(bet, highPressureAdvancedLargeSteamBoilerShape, "high_pressure_advanced_large_steam_boiler",
                        8192, true));
        ReiMachineRecipes.registerMultiblockShape("high_pressure_advanced_large_steam_boiler", highPressureAdvancedLargeSteamBoilerShape);
    }

    private static void quarries() {
        HatchFlags quarryHatchFlags = new HatchFlags.Builder().with(ITEM_INPUT, FLUID_INPUT, ITEM_OUTPUT).build();
        HatchFlags quarryElectricHatchFlags = new HatchFlags.Builder().with(ITEM_INPUT, ITEM_OUTPUT, ENERGY_INPUT).build();

        ShapeTemplate.Builder quarryShapeBuilder = new ShapeTemplate.Builder(MachineCasings.STEEL).add3by3(0, steelCasing, true, quarryHatchFlags)
                .add3by3(1, steelCasing, true, quarryHatchFlags);

        ShapeTemplate.Builder quarryElectricShapeBuilder = new ShapeTemplate.Builder(MachineCasings.STEEL)
                .add3by3(0, steelCasing, true, quarryElectricHatchFlags).add3by3(1, steelCasing, true, quarryElectricHatchFlags);

        for (int y = 2; y <= 4; y++) {
            quarryShapeBuilder.add(-1, y, 1, steelPipe, null);
            quarryShapeBuilder.add(1, y, 1, steelPipe, null);
            quarryElectricShapeBuilder.add(-1, y, 1, steelPipe, null);
            quarryElectricShapeBuilder.add(1, y, 1, steelPipe, null);
        }
        quarryShapeBuilder.add(0, 4, 1, steelCasing, null);
        quarryElectricShapeBuilder.add(0, 4, 1, steelCasing, null);

        SimpleMember chain = SimpleMember.verticalChain();

        for (int y = 0; y <= 3; y++) {
            quarryShapeBuilder.add(0, y, 1, chain, null);
            quarryElectricShapeBuilder.add(0, y, 1, chain, null);
        }

        ShapeTemplate quarryShape = quarryShapeBuilder.build();
        ShapeTemplate quarryElectricShape = quarryElectricShapeBuilder.build();

        STEAM_QUARRY = MachineRegistrationHelper.registerMachine("steam_quarry",
                bet -> new SteamCraftingMultiblockBlockEntity(bet, "steam_quarry", quarryShape, MIMachineRecipeTypes.QUARRY));
        ReiMachineRecipes.registerMultiblockShape("steam_quarry", quarryShape);
        ELECTRIC_QUARRY = MachineRegistrationHelper.registerMachine("electric_quarry",
                bet -> new ElectricCraftingMultiblockBlockEntity(bet, "electric_quarry", quarryElectricShape, MIMachineRecipeTypes.QUARRY));
        ReiMachineRecipes.registerMultiblockShape("electric_quarry", quarryElectricShape);
    }

    private static void drillingRig() {
        ShapeTemplate.Builder drillingRigShapeBuilder = new ShapeTemplate.Builder(MachineCasings.STEEL);
        SimpleMember chain = SimpleMember.verticalChain();
        HatchFlags hatchFlags = new HatchFlags.Builder().with(ITEM_INPUT).with(FLUID_OUTPUT).with(ENERGY_INPUT).build();
        // pillars
        for (int y = -4; y <= -2; ++y) {
            drillingRigShapeBuilder.add(-2, y, -1, steelCasing, null);
            drillingRigShapeBuilder.add(2, y, -1, steelCasing, null);
            drillingRigShapeBuilder.add(-2, y, 3, steelCasing, null);
            drillingRigShapeBuilder.add(2, y, 3, steelCasing, null);
        }
        // platform
        for (int x = -2; x <= 2; ++x) {
            for (int z = -1; z <= 3; ++z) {
                if (x == 2 || x == -2 || z == -1 || z == 3) {
                    drillingRigShapeBuilder.add(x, -1, z, steelCasing, null);
                }
            }
        }
        // chains and pipe casings
        for (int y = -4; y <= 4; ++y) {
            drillingRigShapeBuilder.add(-1, y, 1, chain, null);
            drillingRigShapeBuilder.add(1, y, 1, chain, null);
            if (y >= -1) {
                drillingRigShapeBuilder.add(0, y, 1, steelPipe, null);
            }
        }
        // top
        for (int x = -2; x <= 2; ++x) {
            drillingRigShapeBuilder.add(x, 5, 1, steelCasing, null);
        }
        // hatches
        drillingRigShapeBuilder.add(-1, 0, 0, steelCasing, hatchFlags);
        drillingRigShapeBuilder.add(1, 0, 0, steelCasing, hatchFlags);
        drillingRigShapeBuilder.add(-1, 0, 2, steelCasing, hatchFlags);
        drillingRigShapeBuilder.add(0, 0, 2, steelCasing, hatchFlags);
        drillingRigShapeBuilder.add(1, 0, 2, steelCasing, hatchFlags);

        ShapeTemplate drillingRigShape = drillingRigShapeBuilder.build();

        DRILLING_RIG = MachineRegistrationHelper.registerMachine("drilling_rig", bet -> new ElectricCraftingMultiblockBlockEntity(bet,
                "drilling_rig", drillingRigShape, MIMachineRecipeTypes.DRILLING_RIG));
        ReiMachineRecipes.registerMultiblockShape("drilling_rig", drillingRigShape);
    }

    private static void vacuumFreezer() {
        HatchFlags vacuumFreezerHatches = new HatchFlags.Builder().with(ITEM_INPUT).with(ITEM_OUTPUT).with(FLUID_INPUT).with(FLUID_OUTPUT)
                .with(ENERGY_INPUT).build();
        ShapeTemplate vacuumFreezerShape = new ShapeTemplate.Builder(MachineCasings.FROSTPROOF)
                .add3by3LevelsRoofed(-1, 2, frostproofMachineCasing, vacuumFreezerHatches).build();
        VACUUM_FREEZER = MachineRegistrationHelper.registerMachine("vacuum_freezer",
                bet -> new ElectricCraftingMultiblockBlockEntity(bet, "vacuum_freezer", vacuumFreezerShape, MIMachineRecipeTypes.VACUUM_FREEZER));
        ReiMachineRecipes.registerMultiblockShape("vacuum_freezer", vacuumFreezerShape);
    }

    private static void distillationTower() {
        DISTILLATION_TOWER = MachineRegistrationHelper.registerMachine("distillation_tower", DistillationTowerBlockEntity::new);
        DistillationTowerBlockEntity.registerReiShapes();
    }

    private static void largeDieselGenerator() {
        ShapeTemplate.Builder largeDieselGeneratorShapeBuilder = new ShapeTemplate.Builder(MachineCasings.SOLID_TITANIUM);
        for (int z = 1; z < 4; z++) {
            largeDieselGeneratorShapeBuilder.add(0, 0, z, z < 3 ? titaniumPipe : titaniumCasing, z == 3 ? energyOutput : null);
            for (int x = -1; x < 2; x++) {
                largeDieselGeneratorShapeBuilder.add(x, 1, z, titaniumCasing, null);
                largeDieselGeneratorShapeBuilder.add(x, -1, z, titaniumCasing, null);
                if (x != 0) {
                    largeDieselGeneratorShapeBuilder.add(x, 0, z, titaniumCasing, z < 3 ? fluidInputs : null);
                }
            }
        }
        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                if (x != 0 || y != 0) {
                    largeDieselGeneratorShapeBuilder.add(x, y, 0, titaniumPipe, null);
                }
            }
        }
        ShapeTemplate largeDieselGeneratorShape = largeDieselGeneratorShapeBuilder.build();
        LARGE_DIESEL_GENERATOR = MachineRegistrationHelper.registerMachine("large_diesel_generator", bet ->
                new EnergyFromFluidMultiblockBlockEntity(bet, "large_diesel_generator", largeDieselGeneratorShape,
                        (Fluid f) -> (FluidFuelRegistry.getEu(f) != 0), FluidFuelRegistry::getEu, 16384));
        ReiMachineRecipes.registerMultiblockShape("large_diesel_generator", largeDieselGeneratorShape);
    }

    private static ShapeTemplate largeTurbineShape(MachineCasing mainCasing, SimpleMember casing, SimpleMember pipe){
        ShapeTemplate.Builder largeTurbineBuilder = new ShapeTemplate.Builder(mainCasing);
        for (int z = 0; z < 4; z++) {
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    if (z == 0) {
                        if (x != 0 || y != 0) {
                            largeTurbineBuilder.add(x, y, z, casing, fluidInputs);
                        }
                    } else if (z == 3) {
                        largeTurbineBuilder.add(x, y, z, casing, (x == 0 && y == 0) ? energyOutput : null);
                    } else {
                        largeTurbineBuilder.add(x, y, z, pipe, null);
                    }

                }
            }
        }
        return largeTurbineBuilder.build();

    }

    private static void largeSteamTurbine() {
        ShapeTemplate largeSteamTurbineShape = largeTurbineShape(MachineCasings.CLEAN_STAINLESS_STEEL,
                stainlessSteelClean, stainlessSteelPipe);

        LARGE_STEAM_TURBINE = MachineRegistrationHelper.registerMachine("large_steam_turbine", bet ->
                new EnergyFromFluidMultiblockBlockEntity(bet, "large_steam_turbine", largeSteamTurbineShape,
                        (Fluid f) -> (f == MIFluids.STEAM || f == MIFluids.HIGH_PRESSURE_STEAM
                                || f == MIFluids.HIGH_PRESSURE_HEAVY_WATER_STEAM
                                || f == MIFluids.HEAVY_WATER_STEAM),
                        (Fluid f) -> ((f == MIFluids.STEAM || f == MIFluids.HEAVY_WATER_STEAM) ? 1 : 8)
                        , 16384));
        ReiMachineRecipes.registerMultiblockShape("large_steam_turbine", largeSteamTurbineShape);
    }

    private static void heatExchanger() {
        ShapeTemplate.Builder heatExchangerShapeBuilder = new ShapeTemplate.Builder(MachineCasings.STAINLESS_STEEL_PIPE);
        for (int z = 0; z < 5; z++) {
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    if (z > 0 && z < 4) {

                        heatExchangerShapeBuilder.add(x, y, z, x == -1 ? invarCasings : x == 0 ? stainlessSteelPipe : frostproofMachineCasing,
                                (y == 1 && x == 0 && z == 2) ? energyInput : null);
                    } else {
                        if (z != 0 || x != 0 || y != 0) {
                            HatchFlags flag = null;
                            if (y == -1 && x == 0) {
                                flag = new HatchFlags.Builder().with(z == 0 ? ITEM_INPUT : ITEM_OUTPUT).build();
                            } else if (y == 0 && x != 0) {
                                boolean fluidOutput = (x == -1) ^ (z == 0);
                                flag = new HatchFlags.Builder().with(fluidOutput ? FLUID_OUTPUT : FLUID_INPUT).build();
                            }

                            heatExchangerShapeBuilder.add(x, y, z, stainlessSteelPipe, flag);
                        }
                    }
                }
            }
        }
        ShapeTemplate heatExchangerShape = heatExchangerShapeBuilder.build();
        HEAT_EXCHANGER = MachineRegistrationHelper.registerMachine("heat_exchanger",
                bet -> new ElectricCraftingMultiblockBlockEntity(bet, "heat_exchanger", heatExchangerShape, MIMachineRecipeTypes.HEAT_EXCHANGER));
        ReiMachineRecipes.registerMultiblockShape("heat_exchanger", heatExchangerShape);

    }

    private static void pressurizer() {
        ShapeTemplate.Builder pressurizeShapeBuilder = new ShapeTemplate.Builder(MachineCasings.TITANIUM);
        for (int y = -1; y < 3; y++) {
            SimpleMember member = (y == -1 || y == 2) ? titaniumCasing : titaniumPipe;
            HatchFlags flag = null;
            if (y == -1) {
                flag = new HatchFlags.Builder().with(ENERGY_INPUT, FLUID_OUTPUT).build();
            } else if (y == 2) {
                flag = new HatchFlags.Builder().with(FLUID_INPUT, ITEM_INPUT).build();
            }
            pressurizeShapeBuilder.add(-1, y, 1, member, flag);
            pressurizeShapeBuilder.add(0, y, 1, member, flag);
            pressurizeShapeBuilder.add(1, y, 1, member, flag);
            pressurizeShapeBuilder.add(0, y, 2, member, flag);
            if (y != 0) {
                pressurizeShapeBuilder.add(0, y, 0, member, flag);
            }
        }
        ShapeTemplate pressurizerShape = pressurizeShapeBuilder.build();
        PRESSURIZER = MachineRegistrationHelper.registerMachine("pressurizer",
                bet -> new ElectricCraftingMultiblockBlockEntity(bet, "pressurizer", pressurizerShape, MIMachineRecipeTypes.PRESSURIZER));
        ReiMachineRecipes.registerMultiblockShape("pressurizer", pressurizerShape);
    }

    private static void implosionCompressor() {
        ShapeTemplate.Builder implosionCompressorShapeBuilder = new ShapeTemplate.Builder(MachineCasings.TITANIUM);
        HatchFlags hatchs = new HatchFlags.Builder().with(ITEM_OUTPUT, ITEM_INPUT, ENERGY_INPUT).build();
        implosionCompressorShapeBuilder.add3by3(0, titaniumCasing, false, hatchs);
        implosionCompressorShapeBuilder.add3by3(1, blastProofCasing, true, null);
        implosionCompressorShapeBuilder.add3by3(2, blastProofCasing, true, null);
        implosionCompressorShapeBuilder.add3by3(3, titaniumCasing, false, null);

        ShapeTemplate implosionCompressorShape = implosionCompressorShapeBuilder.build();
        IMPLOSION_COMPRESSOR = MachineRegistrationHelper.registerMachine("implosion_compressor",
                bet -> new ElectricCraftingMultiblockBlockEntity(bet, "implosion_compressor", implosionCompressorShape, MIMachineRecipeTypes.IMPLOSION_COMPRESSOR));
        ReiMachineRecipes.registerMultiblockShape("implosion_compressor", implosionCompressorShape);
    }

    private static void nuclearReactor() {
        NUCLEAR_REACTOR = MachineRegistrationHelper.registerMachine("nuclear_reactor", NuclearReactorMultiblockBlockEntity::new);
        NuclearReactorMultiblockBlockEntity.registerReiShapes();
    }

    private static void largeTank() {
        LARGE_TANK = MachineRegistrationHelper.registerMachine("large_tank", LargeTankMultiblockBlockEntity::new);
        LargeTankMultiblockBlockEntity.registerFluidAPI(LARGE_TANK);
    }

    private static void fusionReactor() {
        ShapeTemplate.Builder fusionReactorShapeBuilder = new ShapeTemplate.Builder(MachineCasings.EV);
        int[][] shapeEdge = new int[][]{
                {6, 1, 0, 0},
                {4, 3, 0, 0},
                {3, 3, 0, 0},
                {2, 2, 0, 0},
                {1, 2, 0, 0},
                {1, 2, 0, 0},
                {0, 2, 0, 0},
        };


        int[][] shapeCenter = new int[][]{
                {5, 2, 0, 0},
                {3, 2, 2, 0},
                {2, 1, 2, 2},
                {1, 1, 2, 1},
                {1, 1, 1, 1},
                {0, 1, 1, 1},
                {0, 1, 1, 1}
        };

        for (int y = -1; y <= 1; y++) {

            int[][] shape = (y == 0) ? shapeCenter : shapeEdge;

            for (int i = 0; i < 7; i++) {
                int x = i + 1;

                for(int k = 0; k < 4; k++){
                    int[] placement = shape[6 - i];
                    int z0 = placement[0];
                    int z1 = z0 + placement[1];
                    int z2 = z1 + placement[2];
                    int z3 = z2 + placement[3];
                    for (int z = z0; z < z3; z++) {
                        if(z < z1 || z >= z2) {
                            fusionReactorShapeBuilder.add(x, y, z, highlyAdvancedHull);
                            fusionReactorShapeBuilder.add(-x, y, z, highlyAdvancedHull);
                            fusionReactorShapeBuilder.add(x, y, 14 - z, highlyAdvancedHull);
                            fusionReactorShapeBuilder.add(-x, y, 14 - z, highlyAdvancedHull);
                        }else if(z >= z1) {
                            fusionReactorShapeBuilder.add(x, y, z, fusionChamber);
                            fusionReactorShapeBuilder.add(-x, y, z, fusionChamber);
                            fusionReactorShapeBuilder.add(x, y, 14 - z, fusionChamber);
                            fusionReactorShapeBuilder.add(-x, y, 14 - z, fusionChamber);
                        }
                    }
                }

            }

            HatchFlags flags = new HatchFlags.Builder().with(FLUID_INPUT, FLUID_OUTPUT, ENERGY_INPUT).build();

            for (int l = 0; l < ((y == 0) ? 3 : 2); l++) {
                if(!(y == 0 && l == 1)) {

                    HatchFlags currentFlag = l == 0 ? flags : null;

                    if (l != 0 || y != 0) {
                        fusionReactorShapeBuilder.add(0, y, l, highlyAdvancedHull, currentFlag);
                    }
                    fusionReactorShapeBuilder.add(0, y, 14 - l, highlyAdvancedHull, currentFlag);
                    fusionReactorShapeBuilder.add(-7 + l, y, 7, highlyAdvancedHull, currentFlag);
                    fusionReactorShapeBuilder.add(7 - l, y, 7, highlyAdvancedHull, currentFlag);
                }else{
                    fusionReactorShapeBuilder.add(0, y, l, fusionChamber);
                    fusionReactorShapeBuilder.add(0, y, 14 - l, fusionChamber);
                    fusionReactorShapeBuilder.add(-7+l, y, 7, fusionChamber);
                    fusionReactorShapeBuilder.add(7 -l , y, 7, fusionChamber);
                }
            }
        }

        ShapeTemplate fusionReactorShape = fusionReactorShapeBuilder.build();
        FUSION_REACTOR = MachineRegistrationHelper.registerMachine("fusion_reactor",
                bet -> new FusionReactorBlockEntity(bet, "fusion_reactor",
                        fusionReactorShape));
        ReiMachineRecipes.registerMultiblockShape("fusion_reactor", fusionReactorShape);

    }

    private static void plasmaTurbine() {
        ShapeTemplate plasmaTurbineShape = largeTurbineShape(MachineCasings.PLASMA_HANDLING_IRIDIUM,
                plasmaHandlingIridium, iridiumPipe);

        PLASMA_TURBINE = MachineRegistrationHelper.registerMachine("plasma_turbine", bet ->
                new EnergyFromFluidMultiblockBlockEntity(bet, "plasma_turbine", plasmaTurbineShape,
                        (fluid) -> (fluid == MIFluids.HELIUM_PLASMA),
                        (heliumPlasma) -> 100000,
                        1 << 20));
        ReiMachineRecipes.registerMultiblockShape("plasma_turbine", plasmaTurbineShape);
    }

    //Biotech
    public static void aquaculturePond(){
        ShapeTemplate.Builder aquaculturePondShapeBuilder = new ShapeTemplate.Builder(MachineCasings.RUBBER);
        SimpleMember rubber = SimpleMember.forBlock(MIBlock.RUBBER_BLOCK);
        SimpleMember water = SimpleMember.forBlock(Blocks.WATER);
        HatchFlags aquaculturePondHatches = new HatchFlags.Builder().with(ITEM_INPUT).with(ITEM_OUTPUT).with(FLUID_INPUT).with(FLUID_OUTPUT)
                .with(ENERGY_INPUT).build();
        //Base

        addPlane(aquaculturePondShapeBuilder, -2, 2, -3, 0, 4, rubber, null);

        //Water
        for (int y = -2; y <= -1; y++){
            addPlane(aquaculturePondShapeBuilder, -1, 1, y, 1, 3, water, null);
        }

        //Walls
        for (int y = -2; y <= -1; y++){
            addRectangle(aquaculturePondShapeBuilder, -2, 2, y, 0, 4, rubber, null);
        }

        //Top
        for (int z = 0; z <= 4; z++){
            addSideX(aquaculturePondShapeBuilder, -2, 2, 0, z, rubber, aquaculturePondHatches);
        }
        addRectangle(aquaculturePondShapeBuilder, -1, 1, 0, 4, 4, rubber, aquaculturePondHatches);
        addSideX(aquaculturePondShapeBuilder, -1, 1, 0, 0, rubber, aquaculturePondHatches);

        ShapeTemplate aquaculturePondShape = aquaculturePondShapeBuilder.build();
        AQUACULTURE_POND = MachineRegistrationHelper.registerMachine("aquaculture_pond",
                bet -> new ElectricCraftingMultiblockBlockEntity(bet, "aquaculture_pond", aquaculturePondShape, MIMachineRecipeTypes.AQUACULTURE_POND));
        ReiMachineRecipes.registerMultiblockShape("aquaculture_pond", aquaculturePondShape);
    }

    public static void composter(){
        ShapeTemplate.Builder composterShapeBuilder = new ShapeTemplate.Builder(MachineCasings.RUBBER);
        HatchFlags composterHatches = new HatchFlags.Builder().with(ITEM_INPUT).with(ITEM_OUTPUT)
                .with(FLUID_INPUT).with(FLUID_OUTPUT).build();

        SimpleMember rubber = SimpleMember.forBlock(MIBlock.RUBBER_BLOCK);

        //TopBottom
        addRectangle(composterShapeBuilder, -1, 1, 2, 0, 2, rubber, composterHatches);
        composterShapeBuilder.add(0, 2, 1, steelPipe, null);
        addRectangle(composterShapeBuilder, -1, 1, -1, 0, 2, rubber, composterHatches);
        composterShapeBuilder.add(0, -1, 1, rubber, null);
        //Body
        composterShapeBuilder.add(0, 1, 0, rubber, null);
        for (int y = 0; y <= 1; y++){
            composterShapeBuilder.add(0, y, 2, steelPipe, null);
            addSideX(composterShapeBuilder, -1, 1, y, 0, rubber, null);
            addSideX(composterShapeBuilder, -1, 1, y, 1, steelPipe, null);
            addSideX(composterShapeBuilder, -1, 1, y, 2, rubber, null);
        }

        ShapeTemplate composterShape = composterShapeBuilder.build();
        COMPOSTER = MachineRegistrationHelper.registerMachine("composter", bet -> new SteamCraftingMultiblockBlockEntity(
                bet, "composter", composterShape, MIMachineRecipeTypes.COMPOSTER));
        ReiMachineRecipes.registerMultiblockShape("composter", composterShape);
    }

    public static void cropPlanter(){
        ShapeTemplate.Builder cropPlanterShapeBuilder = new ShapeTemplate.Builder(MachineCasings.STEEL);
        HatchFlags cropPlanterHatches = new HatchFlags.Builder().with(ITEM_INPUT).with(ITEM_OUTPUT).with(FLUID_INPUT)
                .with(FLUID_OUTPUT).build();
        SimpleMember greenGlass = SimpleMember.forBlock(Blocks.GREEN_STAINED_GLASS);

        //Base + Walls
        cropPlanterShapeBuilder.add(0, 0, 6, steelCasing, cropPlanterHatches);
        cropPlanterShapeBuilder.add(0, 3, 6, steelCasing, null);
        for (int x = -3; x <= 3; x++){
            if (x != 0){
                addSideZ(cropPlanterShapeBuilder, x, 0, 0, 6, steelCasing, cropPlanterHatches);
            }
        }
        for (int y = 1; y <= 3; y++){
            addSideZ(cropPlanterShapeBuilder, 2, y, 0, 6, greenGlass, null);
            addSideZ(cropPlanterShapeBuilder, -2, y, 0, 6, greenGlass, null);
            cropPlanterShapeBuilder.add(1, y, 6, steelCasing, null);
            cropPlanterShapeBuilder.add(-1, y, 6, steelCasing, null);
            cropPlanterShapeBuilder.add(1, y, 0, greenGlass, null);
            cropPlanterShapeBuilder.add(-1, y, 0, greenGlass, null);
            cropPlanterShapeBuilder.add(0, y, 0, greenGlass, null);
        }
        for (int z = 0; z <= 6; z++){
            addSideX(cropPlanterShapeBuilder, -3, 3, 0, z, steelCasing, cropPlanterHatches);
            for (int y = 1; y <= 3; y++){
                addSideX(cropPlanterShapeBuilder, -3, 3, y, z, greenGlass, null);
            }
        }
        addRectangle(cropPlanterShapeBuilder, -3, 3, 4, 0, 6, greenGlass, null);
        //Roof
        addPlane(cropPlanterShapeBuilder, -3, 3, 5, 0, 6, greenGlass, null);

        ShapeTemplate cropPlanterShape = cropPlanterShapeBuilder.build();
        CROP_PLANTER = MachineRegistrationHelper.registerMachine("crop_planter", bet -> new SteamCraftingMultiblockBlockEntity(
                bet, "crop_planter", cropPlanterShape, MIMachineRecipeTypes.CROP_PLANTER));
        ReiMachineRecipes.registerMultiblockShape("crop_planter", cropPlanterShape);
    }

    public static void growthChamber(){
        ShapeTemplate.Builder growthChamberShapeBuilder = new ShapeTemplate.Builder(MachineCasings.BRICKS);
        SimpleMember bricks = SimpleMember.forBlock(Blocks.BRICKS);
        SimpleMember tintedGlass = SimpleMember.forBlock(Blocks.TINTED_GLASS);
        HatchFlags growthChamberHatches = new HatchFlags.Builder().with(ITEM_INPUT).with(ITEM_OUTPUT).with(FLUID_INPUT)
                .with(ENERGY_INPUT).build();

        //Base
        for (int x = -2; x <= 2; x++){
            addSideZ(growthChamberShapeBuilder, x, -1, 0, 4, bricks, growthChamberHatches);
            growthChamberShapeBuilder.add(x, 0, 4, bricks, null);
            if(x != 0){
                growthChamberShapeBuilder.add(x, 0, 0, bricks, null);
            }
        }
        for (int z = 1; z <= 3; z++){
            addSideX(growthChamberShapeBuilder, -2, 2, -1, z, bricks, growthChamberHatches);
            addSideX(growthChamberShapeBuilder, -2, 2, 0, z, tintedGlass, null);
        }

        //Glass
        for (int y = 1; y <= 3; y++){
            addRectangle(growthChamberShapeBuilder, -2, 2, y, 0, 4, tintedGlass, null);
        }

        //Top
        addPlane(growthChamberShapeBuilder, -1, 1, 3, 1, 3, tintedGlass, null);

        ShapeTemplate growthChamberShape = growthChamberShapeBuilder.build();
        GROWTH_CHAMBER = MachineRegistrationHelper.registerMachine("growth_chamber",
                bet -> new ElectricCraftingMultiblockBlockEntity(bet, "growth_chamber", growthChamberShape,
                        MIMachineRecipeTypes.GROWTH_CHAMBER));
        ReiMachineRecipes.registerMultiblockShape("growth_chamber", growthChamberShape);
    }

    public static void industrialOven(){
        ShapeTemplate.Builder industrialOvenShapeBuilder = new ShapeTemplate.Builder(MachineCasings.BRICKS);
        SimpleMember bricks = SimpleMember.forBlock(Blocks.BRICKS);
        SimpleMember heatedStone = SimpleMember.forBlock(MIBlock.HEATING_ELEMENT);
        HatchFlags industrialOvenHatches = new HatchFlags.Builder().with(ITEM_INPUT).with(ITEM_OUTPUT)
                .with(ENERGY_INPUT).build();

        //Top
        addPlane(industrialOvenShapeBuilder, 0, 3, 1, 0, 3, bricks, null);

        //Middle
        for(int x = 0; x <= 3; x++){
            industrialOvenShapeBuilder.add(x, 0, 3, bricks, null);
        }
        for(int z = 1; z <= 2; z++){
            industrialOvenShapeBuilder.add(0, 0, z, bricks, industrialOvenHatches);
            industrialOvenShapeBuilder.add(3, 0, z, bricks, industrialOvenHatches);
        }
        industrialOvenShapeBuilder.add(0, 0, 3, bricks, null);

        //Bottom
        for(int x = 0; x <= 3; x++){
            industrialOvenShapeBuilder.add(x, -1, 3, bricks, industrialOvenHatches);
            industrialOvenShapeBuilder.add(x, -1, 0, bricks, industrialOvenHatches);
        }
        for(int z = 1; z <= 2; z++){
            industrialOvenShapeBuilder.add(0, -1, z, bricks, industrialOvenHatches);
            industrialOvenShapeBuilder.add(3, -1, z, bricks, industrialOvenHatches);
        }
        for(int x = 1; x <= 2; x++){
            for(int z = 1; z <= 2; z++){
                industrialOvenShapeBuilder.add(x, -1, z, heatedStone,null);
            }
        }

        ShapeTemplate industrialOvenShape = industrialOvenShapeBuilder.build();
        INDUSTRIAL_OVEN = MachineRegistrationHelper.registerMachine("industrial_oven",
                bet -> new ElectricCraftingMultiblockBlockEntity(bet, "industrial_oven", industrialOvenShape,
                        MIMachineRecipeTypes.INDUSTRIAL_OVEN));
        ReiMachineRecipes.registerMultiblockShape("industrial_oven", industrialOvenShape);
    }

    public static void industrialPress(){
        ShapeTemplate.Builder industrialPressShapeBuilder = new ShapeTemplate.Builder(MachineCasings.STEEL);
        HatchFlags topHatches = new HatchFlags.Builder().with(ITEM_INPUT).with(ITEM_OUTPUT).with(ENERGY_INPUT).build();
        HatchFlags baseHatches = new HatchFlags.Builder().with(FLUID_INPUT).with(FLUID_OUTPUT).build();

        SimpleMember glass = SimpleMember.forBlock(Blocks.GLASS);
        SimpleMember steelPipe = SimpleMember.forBlock(MIBlock.blocks.get("steel_machine_casing_pipe"));
        SimpleMember cauldron = SimpleMember.forBlock(Blocks.CAULDRON);

        //Components
        for(int y = 3; y <= 5; y++){
            industrialPressShapeBuilder.add(0, y, 1, steelPipe);
        }
        industrialPressShapeBuilder.add(0, 0, 1, cauldron);

        //Press
        for(int y = 3; y <= 5; y++){
            addRectangle(industrialPressShapeBuilder, -1, 1, y, 0, 2, steelCasing, topHatches);
        }
        for(int y = 1; y <= 2; y++){
            addRectangle(industrialPressShapeBuilder, -1, 1, y, 0, 2, glass, null);
        }

        //Base
        for(int y = 0; y <= 5; y++){
            addSideX(industrialPressShapeBuilder, -2, 2, y, 1, steelCasing, null);
        }
        for(int z = 0; z <= 3; z++){
            if(z != 1){
                addSideX(industrialPressShapeBuilder, -1, 1, 0, z, steelCasing, null);
            }
        }
        for(int z = -1; z <= 0; z++){
            addSideX(industrialPressShapeBuilder, -2, 2, 0, z, steelCasing, null);
        }
        addSideX(industrialPressShapeBuilder, -1, 1, 0, 0, steelCasing, null);
        addSideX(industrialPressShapeBuilder, -2, 2, 1, 0, steelCasing, null);
        addSideX(industrialPressShapeBuilder, -2, 2, 0, 2, steelCasing, null);
        addSideX(industrialPressShapeBuilder, -1, 1, 0, 1, steelCasing, baseHatches);
        industrialPressShapeBuilder.add(0, 0, 2, steelCasing, baseHatches);

        ShapeTemplate industrialPressShape = industrialPressShapeBuilder.build();
        INDUSTRIAL_PRESS = MachineRegistrationHelper.registerMachine("industrial_press",
                bet -> new ElectricCraftingMultiblockBlockEntity(bet, "industrial_press", industrialPressShape,
                        MIMachineRecipeTypes.INDUSTRIAL_PRESS));
        ReiMachineRecipes.registerMultiblockShape("industrial_press", industrialPressShape);
    }

    public static void treePlanter(){
        ShapeTemplate.Builder treePlanterShapeBuilder = new ShapeTemplate.Builder(MachineCasings.STEEL);
        HatchFlags treePlanterHatches = new HatchFlags.Builder().with(ITEM_INPUT).with(ITEM_OUTPUT).with(FLUID_INPUT)
                .with(FLUID_OUTPUT).with(ENERGY_INPUT).build();
        SimpleMember greenGlass = SimpleMember.forBlock(Blocks.GREEN_STAINED_GLASS);

        //base
        for (int x = -4; x <= 4; x++){
            if (x != 0) {
                addSideZ(treePlanterShapeBuilder, x, 0, 0, 16, steelCasing, treePlanterHatches);
            }
            else{
                treePlanterShapeBuilder.add(x, 0, 16, steelCasing, treePlanterHatches);
            }
        }
        for (int z = 4; z <= 12; z++){
            addSideX(treePlanterShapeBuilder,-8,8,0, z, steelCasing, treePlanterHatches);
        }
        for (int z = 1; z <= 3; z++){    //diagonals
            addSideX(treePlanterShapeBuilder, -(4+z), (4+z), 0, z, steelCasing, null);
            addSideX(treePlanterShapeBuilder, -(4+z), (4+z), 0, (16 - z), steelCasing, null);
        }

        //walls
        for(int y = 1; y <= 8; y++){
            for(int x = -3; x <= 3; x++){
                addSideZ(treePlanterShapeBuilder, x, y, 0, 16, greenGlass, null);
            }
            for(int z = 5; z <= 11; z++){
                if(y <= 3){
                    if(z < 7 || z > 9){
                        addSideX(treePlanterShapeBuilder, -8, 8, y, z, greenGlass, null);
                    }
                    else if(z == 7 || z == 9){
                        addSideX(treePlanterShapeBuilder, -8, 8, y, z, steelCasing, null);
                    }
                }
                else {
                    addSideX(treePlanterShapeBuilder, -8, 8, y, z, greenGlass, null);
                }
            }
            addSideZ(treePlanterShapeBuilder, -4, y, 0, 16, steelPipe, null);
            addSideZ(treePlanterShapeBuilder, 4, y, 0, 16, steelPipe, null);
            addSideX(treePlanterShapeBuilder, -8, 8, y, 12, steelPipe, null);
            addSideX(treePlanterShapeBuilder, -8, 8, y, 4, steelPipe, null);
            for (int z = 1; z <= 3; z++){    //diagonals
                addSideX(treePlanterShapeBuilder, -(4+z), (4+z), y, z, greenGlass, null);
                addSideX(treePlanterShapeBuilder, -(4+z), (4+z), y, (16 - z), greenGlass, null);
            }
        }
        addSideX(treePlanterShapeBuilder, -8, 8, 3, 8, steelCasing, null);

        //roof
        for (int i = 1; i <= 3; i++){
            addSideZ(treePlanterShapeBuilder, -4, (7 + i), i, (16 - i), steelCasing, null);
            addSideZ(treePlanterShapeBuilder, -4, (8 + i), i, (16 - i), steelCasing, null);
            addSideZ(treePlanterShapeBuilder, 4, (7 + i), i, (16 - i), steelCasing, null);
            addSideZ(treePlanterShapeBuilder, 4, (8 + i), i, (16 - i), steelCasing, null);
            for (int x = -3; x <= 3; x++){
                addSideZ(treePlanterShapeBuilder, x, (8 + i), i, (16 - i), greenGlass, null);
            }
            addSideX(treePlanterShapeBuilder, -(8 - i), (8 - i), (7 + i), 4, steelCasing, null);
            addSideX(treePlanterShapeBuilder, -(8 - i), (8 - i), (8 + i), 4, steelCasing, null);
            addSideX(treePlanterShapeBuilder, -(8 - i), (8 - i), (7 + i), 12, steelCasing, null);
            addSideX(treePlanterShapeBuilder, -(8 - i), (8 - i), (8 + i), 12, steelCasing, null);
            for (int z = 5; z <= 11; z++){
                addSideX(treePlanterShapeBuilder, -(8 - i), (8 - i), (8 + i), z, greenGlass, null);
            }
            for (int z = 1; z <= (4 - i); z++){    //diagonals
                addSideX(treePlanterShapeBuilder, -(9 - z - i), (9 - z - i), (8 + i), (4 - z), greenGlass, null);
                addSideX(treePlanterShapeBuilder, -(9 - z - i), (9 - z - i), (8 + i), (12 + z), greenGlass, null);
            }
        }
        addSideX(treePlanterShapeBuilder, -4, 4, 11, 4, steelCasing, null);
        addSideX(treePlanterShapeBuilder, -4, 4, 11, 12, steelCasing, null);
        addRectangle(treePlanterShapeBuilder, -4, 4, 12, 4, 12, steelCasing, null);
        addPlane(treePlanterShapeBuilder, -3, 3, 12, 5, 11, greenGlass, null);

        ShapeTemplate treePlanterShape = treePlanterShapeBuilder.build();
        TREE_PLANTER = MachineRegistrationHelper.registerMachine("tree_planter",
                bet -> new ElectricCraftingMultiblockBlockEntity(bet, "tree_planter", treePlanterShape,
                        MIMachineRecipeTypes.TREE_PLANTER));
        ReiMachineRecipes.registerMultiblockShape("tree_planter", treePlanterShape);
    }



    public static void init() {

        invarCasings = SimpleMember.forBlock(MIBlock.blocks.get("heatproof_machine_casing"));

        bronzePlatedBricks = SimpleMember.forBlock(MIBlock.blocks.get("bronze_plated_bricks"));
        bronzePipe = SimpleMember.forBlock(MIBlock.blocks.get("bronze_machine_casing_pipe"));

        steelCasing = SimpleMember.forBlock(MIBlock.blocks.get("steel_machine_casing"));
        steelPipe = SimpleMember.forBlock(MIBlock.blocks.get("steel_machine_casing_pipe"));

        frostproofMachineCasing = SimpleMember.forBlock(MIBlock.blocks.get("frostproof_machine_casing"));

        stainlessSteelClean = SimpleMember.forBlock(MIBlock.blocks.get("clean_stainless_steel_machine_casing"));
        stainlessSteelPipe = SimpleMember.forBlock(MIBlock.blocks.get("stainless_steel_machine_casing_pipe"));

        titaniumCasing = SimpleMember.forBlock(MIBlock.blocks.get("solid_titanium_machine_casing"));
        titaniumPipe = SimpleMember.forBlock(MIBlock.blocks.get("titanium_machine_casing_pipe"));

        blastProofCasing = SimpleMember.forBlock(MIBlock.blocks.get("blastproof_casing"));

        highlyAdvancedHull = SimpleMember.forBlock(MIBlock.blocks.get("highly_advanced_machine_hull"));
        fusionChamber = SimpleMember.forBlock(MIBlock.blocks.get("fusion_chamber"));

        plasmaHandlingIridium = SimpleMember.forBlock(MIBlock.blocks.get("plasma_handling_iridium_machine_casing"));
        iridiumPipe = SimpleMember.forBlock(MIBlock.blocks.get("iridium_machine_casing_pipe"));


        cokeOven();
        steamBlastFurnace();
        electricBlastFurnace();
        steamBoilers();
        quarries();
        drillingRig();
        vacuumFreezer();
        distillationTower();
        largeDieselGenerator();
        largeSteamTurbine();
        heatExchanger();
        pressurizer();
        implosionCompressor();
        nuclearReactor();
        largeTank();
        fusionReactor();
        plasmaTurbine();

        //Biotech
        aquaculturePond();
        composter();
        cropPlanter();
        growthChamber();
        industrialOven();
        industrialPress();
        treePlanter();
    }


    public static void clientInit() {
        MachineModels.addTieredMachine("coke_oven", "coke_oven", MachineCasings.BRICKS, true, false, false);
        BlockEntityRendererRegistry.INSTANCE.register(COKE_OVEN, MultiblockMachineBER::new);
        new Rei("coke_oven", MIMachineRecipeTypes.COKE_OVEN, new ProgressBar.Parameters(77, 33, "arrow"))
                .items(inputs -> inputs.addSlot(56, 35), outputs -> outputs.addSlot(102, 35))
                .fluids(inputs -> {
                }, outputs -> outputs.addSlot(102, 53))
                .register();

        MachineModels.addTieredMachine("steam_blast_furnace", "steam_blast_furnace", MachineCasings.FIREBRICKS, true, false, false);
        BlockEntityRendererRegistry.INSTANCE.register(STEAM_BLAST_FURNACE, MultiblockMachineBER::new);
        new Rei("steam_blast_furnace", MIMachineRecipeTypes.BLAST_FURNACE, new ProgressBar.Parameters(77, 33, "arrow"))
                .items(inputs -> inputs.addSlots(56, 35, 2, 1), outputs -> outputs.addSlots(102, 35, 1, 1))
                .fluids(fluids -> fluids.addSlots(36, 35, 1, 1), outputs -> outputs.addSlots(122, 35, 1, 1))
                .workstations("steam_blast_furnace", "electric_blast_furnace").extraTest(recipe -> recipe.eu <= 4)
                .register();

        MachineModels.addTieredMachine("electric_blast_furnace", "electric_blast_furnace", MachineCasings.HEATPROOF, true, false, false);
        BlockEntityRendererRegistry.INSTANCE.register(ELECTRIC_BLAST_FURNACE, MultiblockMachineBER::new);
        // note: the REI category is built in the static {} block of ElectricBlastFurnaceBlockEntity.java

        MachineModels.addTieredMachine("large_steam_boiler", "large_boiler", MachineCasings.BRONZE_PLATED_BRICKS, true, false, false);
        BlockEntityRendererRegistry.INSTANCE.register(LARGE_STEAM_BOILER, MultiblockMachineBER::new);

        MachineModels.addTieredMachine("advanced_large_steam_boiler", "large_boiler", MachineCasings.BRONZE_PLATED_BRICKS, true, false, false);
        BlockEntityRendererRegistry.INSTANCE.register(ADVANCED_LARGE_STEAM_BOILER, MultiblockMachineBER::new);

        MachineModels.addTieredMachine("high_pressure_large_steam_boiler", "large_boiler", CLEAN_STAINLESS_STEEL, true, false, false);
        BlockEntityRendererRegistry.INSTANCE.register(HIGH_PRESSURE_LARGE_STEAM_BOILER, MultiblockMachineBER::new);

        MachineModels.addTieredMachine("high_pressure_advanced_large_steam_boiler", "large_boiler", CLEAN_STAINLESS_STEEL, true, false, false);
        BlockEntityRendererRegistry.INSTANCE.register(HIGH_PRESSURE_ADVANCED_LARGE_STEAM_BOILER, MultiblockMachineBER::new);


        MachineModels.addTieredMachine("steam_quarry", "quarry", MachineCasings.STEEL, true, false, false);
        BlockEntityRendererRegistry.INSTANCE.register(STEAM_QUARRY, MultiblockMachineBER::new);
        new Rei("steam_quarry", MIMachineRecipeTypes.QUARRY, new ProgressBar.Parameters(77, 33, "arrow"))
                .items(inputs -> inputs.addSlot(56, 35), outputs -> outputs.addSlots(102, 35, 4, 4))
                .workstations("steam_quarry", "electric_quarry").extraTest(recipe -> recipe.eu <= 4)
                .register();
        new Rei("electric_quarry", MIMachineRecipeTypes.QUARRY, new ProgressBar.Parameters(77, 33, "arrow"))
                .items(inputs -> inputs.addSlot(56, 35), outputs -> outputs.addSlots(102, 35, 4, 4))
                .workstations("electric_quarry").extraTest(recipe -> recipe.eu > 4)
                .register();

        MachineModels.addTieredMachine("electric_quarry", "quarry", MachineCasings.STEEL, true, false, false);
        BlockEntityRendererRegistry.INSTANCE.register(ELECTRIC_QUARRY, MultiblockMachineBER::new);

        MachineModels.addTieredMachine("vacuum_freezer", "vacuum_freezer", MachineCasings.FROSTPROOF, true, false, false);
        BlockEntityRendererRegistry.INSTANCE.register(VACUUM_FREEZER, MultiblockMachineBER::new);
        new Rei("vacuum_freezer", MIMachineRecipeTypes.VACUUM_FREEZER, new ProgressBar.Parameters(77, 33, "arrow"))
                .items(inputs -> inputs.addSlots(56, 35, 2, 1), outputs -> outputs.addSlot(102, 35))
                .fluids(inputs -> inputs.addSlots(36, 35, 2, 1), outputs -> outputs.addSlot(122, 35))
                .register();

        MachineModels.addTieredMachine("drilling_rig", "drilling_rig", MachineCasings.STEEL, true, false, false);
        BlockEntityRendererRegistry.INSTANCE.register(DRILLING_RIG, MultiblockMachineBER::new);
        new Rei("drilling_rig", MIMachineRecipeTypes.DRILLING_RIG, new ProgressBar.Parameters(77, 33, "arrow"))
                .items(inputs -> inputs.addSlot(36, 35), outputs -> {
                })
                .fluids(inputs -> {
                }, outputs -> outputs.addSlot(122, 35))
                .register();

        MachineModels.addTieredMachine("distillation_tower", "distillation_tower", CLEAN_STAINLESS_STEEL, true, false, false);
        BlockEntityRendererRegistry.INSTANCE.register(DISTILLATION_TOWER, MultiblockMachineBER::new);
        new Rei("distillation_tower", MIMachineRecipeTypes.DISTILLATION_TOWER, new ProgressBar.Parameters(77, 33, "arrow"))
                .fluids(inputs -> inputs.addSlot(56, 35), outputs -> outputs.addSlots(102, 35, 1, 8))
                .register();

        MachineModels.addTieredMachine("large_diesel_generator", "diesel_generator", MachineCasings.SOLID_TITANIUM, true, false, false);
        BlockEntityRendererRegistry.INSTANCE.register(LARGE_DIESEL_GENERATOR, MultiblockMachineBER::new);

        MachineModels.addTieredMachine("large_steam_turbine", "steam_turbine", CLEAN_STAINLESS_STEEL, true, false, false);
        BlockEntityRendererRegistry.INSTANCE.register(LARGE_STEAM_TURBINE, MultiblockMachineBER::new);

        MachineModels.addTieredMachine("heat_exchanger", "heat_exchanger", MachineCasings.STAINLESS_STEEL_PIPE, true, false, false);
        BlockEntityRendererRegistry.INSTANCE.register(HEAT_EXCHANGER, MultiblockMachineBER::new);
        new Rei("heat_exchanger", MIMachineRecipeTypes.HEAT_EXCHANGER, new ProgressBar.Parameters(77, 42, "arrow"))
                .items(inputs -> inputs.addSlot(36, 35), outputs -> outputs.addSlot(122, 35))
                .fluids(inputs -> inputs.addSlots(56, 35, 2, 1), outputs -> outputs.addSlots(102, 35, 2, 1))
                .register();

        MachineModels.addTieredMachine("pressurizer", "pressurizer", MachineCasings.TITANIUM_PIPE, true, false, false);
        BlockEntityRendererRegistry.INSTANCE.register(PRESSURIZER, MultiblockMachineBER::new);
        new Rei("pressurizer", MIMachineRecipeTypes.PRESSURIZER, new ProgressBar.Parameters(77, 33, "arrow"))
                .items(inputs -> inputs.addSlot(38, 35), outputs -> {
                })
                .fluids(inputs -> inputs.addSlot(56, 35), outputs -> outputs.addSlot(102, 35))
                .register();

        MachineModels.addTieredMachine("implosion_compressor", "compressor", MachineCasings.SOLID_TITANIUM, true, false, false);
        BlockEntityRendererRegistry.INSTANCE.register(IMPLOSION_COMPRESSOR, MultiblockMachineBER::new);
        new Rei("implosion_compressor", MIMachineRecipeTypes.IMPLOSION_COMPRESSOR, new ProgressBar.Parameters(77, 42, "compress"))
                .items(inputs -> inputs.addSlots(36, 35, 2, 2), outputs -> outputs.addSlot(102, 42))
                .register();

        MachineModels.addTieredMachine("nuclear_reactor", "nuclear_reactor", MachineCasings.NUCLEAR, true, false, false, true);
        BlockEntityRendererRegistry.INSTANCE.register(NUCLEAR_REACTOR, MultiblockMachineBER::new);

        MachineModels.addTieredMachine("large_tank",
                "large_tank", MachineCasings.STEEL, true, false, false, false);
        BlockEntityRendererRegistry.INSTANCE.register(LARGE_TANK, MultiblockTankBER::new);

        MachineModels.addTieredMachine("fusion_reactor",
                "fusion_reactor", MachineCasings.EV, true, false, false, true);
        BlockEntityRendererRegistry.INSTANCE.register(FUSION_REACTOR, MultiblockMachineBER::new);
        new Rei("fusion_reactor", MIMachineRecipeTypes.FUSION_REACTOR, new ProgressBar.Parameters(66, 33, "arrow"))
                .fluids(inputs -> inputs.addSlots(26, 35, 1, 2), outputs -> outputs.addSlots(92, 35, 1, 3))
                .register();

        MachineModels.addTieredMachine("plasma_turbine", "steam_turbine",
                MachineCasings.PLASMA_HANDLING_IRIDIUM, true, false, false);
        BlockEntityRendererRegistry.INSTANCE.register(PLASMA_TURBINE, MultiblockMachineBER::new);

        //Biotech
        MachineModels.addTieredMachine("aquaculture_pond",
                "aquaculture_pond", MachineCasings.RUBBER, true, false, true, true);
        BlockEntityRendererRegistry.INSTANCE.register(AQUACULTURE_POND, MultiblockMachineBER::new);
        new Rei("aquaculture_pond", MIMachineRecipeTypes.AQUACULTURE_POND, new ProgressBar.Parameters(77, 33, "arrow"))
                .items(inputs -> inputs.addSlots(56, 35, 2, 1), outputs -> outputs.addSlots(102, 35, 2, 1))
                .fluids(inputs -> inputs.addSlots(36, 35, 2, 1), outputs -> outputs.addSlots(122, 35, 2, 1))
                .register();

        MachineModels.addTieredMachine("composter",
                "composter", MachineCasings.RUBBER, true, false, false, true);
        BlockEntityRendererRegistry.INSTANCE.register(COMPOSTER, MultiblockMachineBER::new);
        new Rei("composter", MIMachineRecipeTypes.COMPOSTER, new ProgressBar.Parameters(77, 33, "arrow"))
                .items(inputs -> inputs.addSlots(56, 35, 2, 1), outputs -> outputs.addSlot(102, 35))
                .fluids(inputs -> inputs.addSlot(36, 35), outputs -> outputs.addSlot(122, 35))
                .register();

        MachineModels.addTieredMachine("crop_planter",
                "crop_planter", MachineCasings.STEEL, true, false, false, true);
        BlockEntityRendererRegistry.INSTANCE.register(CROP_PLANTER, MultiblockMachineBER::new);
        new Rei("crop_planter", MIMachineRecipeTypes.CROP_PLANTER, new ProgressBar.Parameters(68, 53, "arrow"))
                .items(inputs -> inputs.addSlots(30, 40, 1, 2),
                        outputs -> outputs.addSlots(95, 20, 3, 2))
                .fluids(inputs -> inputs.addSlots(30, 60, 1, 2),
                        outputs -> outputs.addSlots(95, 80, 1, 2))
                .register();

        MachineModels.addTieredMachine("growth_chamber",
                "growth_chamber", MachineCasings.BRICKS, true, false, false, true);
        BlockEntityRendererRegistry.INSTANCE.register(GROWTH_CHAMBER, MultiblockMachineBER::new);
        new Rei("growth_chamber", MIMachineRecipeTypes.GROWTH_CHAMBER, new ProgressBar.Parameters(77, 33, "arrow"))
                .items(inputs -> inputs.addSlot(56, 35), outputs -> outputs.addSlot(102, 35))
                .fluids(inputs -> inputs.addSlot(36, 35), outputs -> {})
                .register();

        MachineModels.addTieredMachine("industrial_oven",
                "industrial_oven", MachineCasings.BRICKS, true, false, false, true);
        BlockEntityRendererRegistry.INSTANCE.register(INDUSTRIAL_OVEN, MultiblockMachineBER::new);
        new Rei("industrial_oven", MIMachineRecipeTypes.INDUSTRIAL_OVEN, new ProgressBar.Parameters(77, 33, "arrow"))
                .items(inputs -> inputs.addSlots(36, 35, 1, 2), outputs -> outputs.addSlot(102, 35))
                .register();

        MachineModels.addTieredMachine("industrial_press",
                "industrial_press", MachineCasings.STEEL, true, false, false, true);
        BlockEntityRendererRegistry.INSTANCE.register(INDUSTRIAL_PRESS, MultiblockMachineBER::new);
        new Rei("industrial_press", MIMachineRecipeTypes.INDUSTRIAL_PRESS, new ProgressBar.Parameters(57, 33, "arrow"))
                .items(inputs -> inputs.addSlots(36, 50, 2, 1), outputs -> outputs.addSlot(82, 50))
                .fluids(inputs -> {}, outputs -> outputs.addSlot(82, 30))
                .register();

        MachineModels.addTieredMachine("tree_planter",
                "tree_planter", MachineCasings.STEEL, true, false, false, true);
        BlockEntityRendererRegistry.INSTANCE.register(TREE_PLANTER, MultiblockMachineBER::new);
        new Rei("tree_planter", MIMachineRecipeTypes.TREE_PLANTER, new ProgressBar.Parameters(68, 53, "arrow"))
                .items(inputs -> inputs.addSlots(30, 40, 1, 2),
                        outputs -> outputs.addSlots(95, 20, 3, 2))
                .fluids(inputs -> inputs.addSlots(30, 60, 1, 2),
                        outputs -> outputs.addSlots(95, 80, 1, 2))
                .register();
    }

    private static final Rectangle CRAFTING_GUI = new Rectangle(CraftingMultiblockGui.X, CraftingMultiblockGui.Y,
            CraftingMultiblockGui.W, CraftingMultiblockGui.H);

    public static class Rei {
        private final String category;
        private final MachineRecipeType recipeType;
        private final ProgressBar.Parameters progressBarParams;
        private final List<String> workstations;
        private Predicate<MachineRecipe> extraTest = recipe -> true;
        private SlotPositions itemInputs = SlotPositions.empty();
        private SlotPositions itemOutputs = SlotPositions.empty();
        private SlotPositions fluidInputs = SlotPositions.empty();
        private SlotPositions fluidOutputs = SlotPositions.empty();
        private static final Predicate<MachineScreenHandlers.ClientScreen> SHAPE_VALID_PREDICATE = screen -> {
            for (SyncedComponent.Client client : screen.getMenu().components) {
                if (client instanceof CraftingMultiblockGui.Client cmGui) {
                    if (cmGui.isShapeValid) {
                        return true;
                    }
                }
            }
            return false;
        };

        public Rei(String category, MachineRecipeType recipeType, ProgressBar.Parameters progressBarParams) {
            this.category = category;
            this.recipeType = recipeType;
            this.progressBarParams = progressBarParams;
            this.workstations = new ArrayList<>();
            workstations.add(category);
        }

        public Rei items(Consumer<SlotPositions.Builder> inputs, Consumer<SlotPositions.Builder> outputs) {
            itemInputs = new SlotPositions.Builder().buildWithConsumer(inputs);
            itemOutputs = new SlotPositions.Builder().buildWithConsumer(outputs);
            return this;
        }

        public Rei fluids(Consumer<SlotPositions.Builder> inputs, Consumer<SlotPositions.Builder> outputs) {
            fluidInputs = new SlotPositions.Builder().buildWithConsumer(inputs);
            fluidOutputs = new SlotPositions.Builder().buildWithConsumer(outputs);
            return this;
        }

        public Rei extraTest(Predicate<MachineRecipe> extraTest) {
            this.extraTest = extraTest;
            return this;
        }

        public Rei workstations(String... workstations) {
            this.workstations.clear();
            this.workstations.addAll(Arrays.asList(workstations));
            return this;
        }

        public final void register() {
            ReiMachineRecipes.registerCategory(category, new MachineCategoryParams(category, itemInputs, itemOutputs, fluidInputs, fluidOutputs, progressBarParams, recipe -> recipe.getType() == recipeType && extraTest.test(recipe)));
            for (String workstation : workstations) {
                ReiMachineRecipes.registerWorkstation(category, workstation);
                ReiMachineRecipes.registerRecipeCategoryForMachine(workstation, category, SHAPE_VALID_PREDICATE);
                ReiMachineRecipes.registerMachineClickArea(workstation, CRAFTING_GUI);
            }
        }
    }
}
