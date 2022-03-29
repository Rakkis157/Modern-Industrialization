package aztech.modern_industrialization.datagen.recipe;

import aztech.modern_industrialization.MIFluids;
import aztech.modern_industrialization.MIItem;
import aztech.modern_industrialization.ModernIndustrialization;
import aztech.modern_industrialization.fluid.CraftingFluid;
import aztech.modern_industrialization.machines.init.MIMachineRecipeTypes;
import aztech.modern_industrialization.recipe.json.MIRecipeJson;
import aztech.modern_industrialization.recipe.json.RecipeJson;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import java.util.function.Consumer;

public class SampleRecipesProvider extends MIRecipesProvider{
    private Consumer<FinishedRecipe> consumer;

    public SampleRecipesProvider(FabricDataGenerator dataGenerator){
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer <FinishedRecipe> consumer){
        this.consumer = consumer;

        addSampleRecipe("blue_algae_from_water", "blue_algae", Fluids.WATER, 1000);
        addSampleRecipe(MIFluids.BLUE_ALGAE_MIXTURE);
        addSampleRecipe(MIFluids.BROWN_ALGAE_MIXTURE);
        addSampleRecipe(MIFluids.GREEN_ALGAE_MIXTURE);
        addSampleRecipe(MIFluids.YEAST_MIXTURE);

        addSampleRecipe("amylase", MIItem.WHEAT_DOUGH);
        addSampleRecipe("blue_algae", Items.SEAGRASS, 1.0);
        addSampleRecipe("brown_algae", Items.KELP, 1.0);
        addSampleRecipe("beef", Items.BEEF);
        addSampleRecipe("chicken", Items.CHICKEN);
        addSampleRecipe("cod", Items.COD);
        addSampleRecipe("mutton", Items.MUTTON);
        addSampleRecipe("pork", Items.PORKCHOP);
        addSampleRecipe("rabbit", Items.RABBIT);
        addSampleRecipe("salmon", MIItem.SALMON_FILLET);
        addSampleRecipe("spider", Items.SPIDER_EYE);
        addSampleRecipe("yeast", Items.APPLE, 1.0);
        addSampleRecipe("yeast", Items.SWEET_BERRIES, 1.0);

    }
    private void addSampleRecipe(CraftingFluid fluid){
        String fluidName = fluid.name;
        String output = fluidName.substring(0, fluidName.length()-8);
        addSampleRecipe(output, fluid);
    }

    private void addSampleRecipe(String output, Fluid fluid){
        addSampleRecipe(output, fluid, 100);
    }

    private void addSampleRecipe(String output, Fluid fluid, int amount){
        String id = "sample_%s_from_mixture".formatted(output);
        addSampleRecipe(id, output, fluid, amount);
    }

    private void addSampleRecipe(String id, String output, Fluid fluid, int amount){
        output = "sample_%s".formatted(output);
        addSampleRecipe(id, MIRecipeJson.create(MIMachineRecipeTypes.SAMPLING_MACHINE, 2, 200)
                .addItemInput(MIItem.TEST_TUBE, 1)
                .addFluidInput(fluid, amount)
                .addItemOutput(ModernIndustrialization.MOD_ID + ":" + output, 1));
    }

    private void addSampleRecipe(String output, Item item){
        addSampleRecipe(output, item, 0.1);
    }

    private void addSampleRecipe(String output, Item item, double probability){
        String id = "sample_%s_from_%s".formatted(output, getItemName(item));
        addSampleRecipe(id, output, item, probability);
    }

    private void addSampleRecipe(String id, String output, Item item, double probability){
        String sample = "sample_" + output;
        addSampleRecipe(id, MIRecipeJson.create(MIMachineRecipeTypes.SAMPLING_MACHINE, 2, 200)
                .addItemInput(MIItem.TEST_TUBE, 1)
                .addItemInput(item, 1, probability)
                .addItemOutput(ModernIndustrialization.MOD_ID + ":" + sample, 1));
    }

    private void addSampleRecipe(String id, RecipeJson<?> recipeJson) {
        recipeJson.offerTo(consumer, "biotech/sampling_machine/" + id);
    }
}
