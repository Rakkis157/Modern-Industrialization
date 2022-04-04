package aztech.modern_industrialization.datagen.recipe;

import aztech.modern_industrialization.MIFluids;
import aztech.modern_industrialization.machines.init.MIMachineRecipeTypes;
import aztech.modern_industrialization.recipe.json.MIRecipeJson;
import aztech.modern_industrialization.recipe.json.RecipeJson;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;

import java.util.function.Consumer;

import static aztech.modern_industrialization.util.string.RS.*;

public class PlanterRecipesProvider extends MIRecipesProvider{
    private Consumer<FinishedRecipe> consumer;
    public PlanterRecipesProvider(FabricDataGenerator dataGenerator){
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer<FinishedRecipe> consumer){
        this.consumer = consumer;

        //Crop Planter
        addCrop(isMC("beetroot"), isMC("beetroot_seeds"));
        addCrop(isMC("carrot"));
        addBlockCrop(isMC("melon"), isMC("melon_seeds"));
        addCrop(isMC("potato"));
        addBlockCrop(isMC("pumpkin"), isMC("pumpkin_seeds"));
        addCrop(isMC("sunflower"), isMI("sunflower_seeds"));
        addCrop(isMC("wheat"), isMC("wheat_seeds"));

        //Tree Planter
        addFungusRecipe("crimson");
        addFungusRecipe("warped");
        addMushroomRecipe("brown_mushroom");
        addMushroomRecipe("red_mushroom");
        addTreeRecipe("acacia");
        addTreeRecipe("birch");
        addTreeRecipe("dark_oak");
        addTreeRecipe("jungle", Items.COCOA_BEANS, 9);
        addTreeRecipe("oak", Items.APPLE, 5);
        addTreeRecipe("spruce");
        addTreeRecipe("azalea", "minecraft:azalea", "minecraft:oak_log",
                "minecraft:azalea_leaves");
        addTreeRecipe("flowering_azalea", "minecraft:flowering_azalea", "minecraft:oak_log",
                "minecraft:flowering_azalea_leaves");
    }

    private void addCrop(String crop){
        addCrop(crop, crop);
    }

    private void addCrop(String crop, String seed){
        String recipeID = recipeID(clean(seed), clean(crop));
        addCropPlanterRecipe(recipeID, MIRecipeJson.create(MIMachineRecipeTypes.CROP_PLANTER, 2, 800)
                .addItemInput(seed, 24).addItemInput("#c:fertilizers", 8)
                .addItemOutput(crop, 60).addFluidInput(Fluids.WATER, 8000));
    }

    private void addBlockCrop(String crop, String seed){
        String recipeID = recipeID(clean(seed), clean(crop));
        addCropPlanterRecipe(recipeID, MIRecipeJson.create(MIMachineRecipeTypes.CROP_PLANTER, 2, 800)
                .addItemInput(seed, 12).addItemInput("#c:fertilizers", 6)
                .addItemOutput(crop, 12).addFluidInput(Fluids.WATER, 8000));
    }

    private void addCropPlanterRecipe(String id, RecipeJson<?> recipeJson){
        recipeJson.offerTo(consumer, "crop_planter" + id);
    }

    private void addFungusRecipe(String id){
        String block;
        if(id.equals("crimson")){
            block = "minecraft:nether_wart_block";
        }
        else{
            block = "minecraft:" + id + "_wart_block";
        }
        String stem = "minecraft:" + id + "_stem";
        String fungus = "minecraft:" + id + "_fungus";
        addTreePlanterRecipe(id + "_fungus", MIRecipeJson.create(MIMachineRecipeTypes.TREE_PLANTER, 2, 1600)
                .addItemInput(fungus, 1).addItemInput("#c:fertilizers", 6)
                .addItemOutput(stem, 6).addItemOutput(block, 14)
                .addItemOutput("minecraft:shroomlight", 5).addItemOutput(fungus, 3)
                .addFluidInput(MIFluids.SULFUR_DIOXIDE, 2000));
    }

    private void addMushroomRecipe(String id){
        addTreePlanterRecipe(id, MIRecipeJson.create(MIMachineRecipeTypes.TREE_PLANTER, 2, 1600)
                .addItemInput("minecraft:" + id, 1).addItemInput("#c:fertilizers", 6)
                .addItemOutput("minecraft:mushroom_stem", 6)
                .addItemOutput("minecraft:" + id + "_block", 14)
                .addFluidInput(MIFluids.CARBON_DIOXIDE, 2000));
    }

    private void addTreeRecipe(String id){
        String sapling = "minecraft:" + id + "_sapling";
        String log = "minecraft:" + id + "_log";
        String leaves = "minecraft:" + id + "_leaves";
        addTreeRecipe(id, sapling, log, leaves);
    }

    private void addTreeRecipe(String id, Item item, int amount){
        String sapling = "minecraft:" + id + "_sapling";
        String log = "minecraft:" + id + "_log";
        String leaves = "minecraft:" + id + "_leaves";
        addTreeRecipe(id, sapling, log, leaves, item, amount);
    }

    private void addTreeRecipe(String id, String sapling, String log, String leaves){
        addTreePlanterRecipe(id, MIRecipeJson.create(MIMachineRecipeTypes.TREE_PLANTER, 2, 1600)
                .addItemInput(sapling, 3).addItemInput("#c:fertilizers", 10)
                .addItemOutput(sapling , 5).addItemOutput(log, 15).addItemOutput(leaves, 32)
                .addFluidInput(Fluids.WATER, 4000));
    }

    private void addTreeRecipe(String id, String sapling, String log, String leaves, Item item, int amount){
        addTreePlanterRecipe(id, MIRecipeJson.create(MIMachineRecipeTypes.TREE_PLANTER, 2, 1600)
                .addItemInput(sapling, 3).addItemInput("#c:fertilizers", 10)
                .addItemOutput(sapling , 5).addItemOutput(log, 15).addItemOutput(leaves, 32)
                .addItemOutput(item, amount).addFluidInput(Fluids.WATER, 4000));
    }

    private void addTreePlanterRecipe(String id, RecipeJson<?> recipeJson) {
        recipeJson.offerTo(consumer, "agriculture/tree_planter/" + id);
    }
}
