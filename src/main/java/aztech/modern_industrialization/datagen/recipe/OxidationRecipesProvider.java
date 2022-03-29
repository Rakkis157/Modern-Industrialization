package aztech.modern_industrialization.datagen.recipe;

import aztech.modern_industrialization.MIFluids;
import aztech.modern_industrialization.machines.init.MIMachineRecipeTypes;
import aztech.modern_industrialization.recipe.json.MIRecipeJson;
import aztech.modern_industrialization.recipe.json.RecipeJson;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class OxidationRecipesProvider extends MIRecipesProvider {
    private Consumer<FinishedRecipe> consumer;
    private String[] states = {"exposed_", "weathered_", "oxidized_"};

    public OxidationRecipesProvider(FabricDataGenerator dataGenerator){
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer <FinishedRecipe> consumer){
        this.consumer = consumer;
        addRedoxRecipe("copper_block", "copper");
        addRedoxRecipe("cut_copper");
        addRedoxRecipe("cut_copper_slab");
        addRedoxRecipe("cut_copper_stairs");
    }

    private void addRedoxRecipe(String input){
        addRedoxRecipe(input, input);
    }

    private void addRedoxRecipe(String input, String output) {
        String newOutput;
        for(int i = 0; i < states.length; i++){
            newOutput = states[i] + output;
            if(i != 0){
                input = states[i - 1] + output;
            }
            addRedoxRecipe(newOutput, MIRecipeJson.create(MIMachineRecipeTypes.CHEMICAL_REACTOR, 2, 200)
                    .addItemInput(input, 1).addFluidInput(MIFluids.OXYGEN, 100).addItemOutput(newOutput, 1));
        }
    }

    private void addRedoxRecipe(String id, RecipeJson<?> recipeJson) {
        recipeJson.offerTo(consumer, "chemical_reactor/oxidation/" + id);
    }
}
