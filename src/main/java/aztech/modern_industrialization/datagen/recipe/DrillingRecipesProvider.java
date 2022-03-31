package aztech.modern_industrialization.datagen.recipe;

import aztech.modern_industrialization.MIFluids;
import aztech.modern_industrialization.machines.init.MIMachineRecipeTypes;
import aztech.modern_industrialization.recipe.json.MIRecipeJson;
import aztech.modern_industrialization.recipe.json.RecipeJson;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import javax.annotation.Nullable;
import java.util.function.Consumer;

import static aztech.modern_industrialization.util.string.RS.isMC;
import static aztech.modern_industrialization.util.string.RS.isMI;

public class DrillingRecipesProvider extends MIRecipesProvider{
    private Consumer<FinishedRecipe> consumer;
    public DrillingRecipesProvider(FabricDataGenerator dataGenerator){
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer<FinishedRecipe> consumer) {
        this.consumer = consumer;
        addDrillingRecipe("aluminum", 0.01, 8, 100, MIFluids.CRUDE_OIL, 500);
        addDrillingRecipe("copper", 0.005, 2, 100, Fluids.WATER, 16000);
        addDrillingRecipe("stainless_steel", 0.005, 32, 200, MIFluids.SHALE_OIL, 1000);
        addDrillingRecipe("tungsten", 0.01, 16, 200, Fluids.LAVA, 4000);

    }

    private void addDrillingRecipe(String id, double probability, int eu, int duration, Fluid fluid, int fluidAmount){
        addDrillingRecipe(id, MIRecipeJson.create(MIMachineRecipeTypes.DRILLING_RIG, eu, duration)
                .addItemInput(isMI(id + "_drill"), 1, probability)
                .addFluidOutput(fluid, fluidAmount));
    }

    private void addDrillingRecipe(String id, RecipeJson<?> recipeJson){
        recipeJson.offerTo(consumer, "drilling/" + id);
    }

}
