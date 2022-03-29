package aztech.modern_industrialization.datagen.recipe;

import aztech.modern_industrialization.machines.init.MIMachineRecipeTypes;
import aztech.modern_industrialization.recipe.json.MIRecipeJson;
import aztech.modern_industrialization.recipe.json.RecipeJson;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class WaxingRecipesProvider extends MIRecipesProvider {
    private Consumer<FinishedRecipe> consumer;

    public WaxingRecipesProvider(FabricDataGenerator dataGenerator){
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer <FinishedRecipe> consumer){
        this.consumer = consumer;

        String[] states = {"", "exposed_", "weathered_", "oxidized_"};

        for (String state : states) {
            addWaxRecipe(state + "copper");
            addWaxRecipe(state + "cut_copper");
            addWaxRecipe(state + "cut_copper_slab");
            addWaxRecipe(state + "cut_copper_stairs");
        }
    }

    private void addWaxRecipe(String input) {
        String output = "waxed_" + input;
        if(input.equals("copper")){
            input = "copper_block";
        }
        String id = "%s_to_%s".formatted(input.replace('#', '_').replace(':', '_'), output.replace(':', '_'));
        addWaxRecipe(id, MIRecipeJson.create(MIMachineRecipeTypes.CHEMICAL_REACTOR, 2, 200).addItemInput(input, 1)
                .addItemInput("#c:waxes", 1).addItemOutput("waxed_" + input, 1));
    }

    private void addWaxRecipe(String id, RecipeJson<?> recipeJson) {
        recipeJson.offerTo(consumer, "chemical_reactor/waxing/" + id);
    }
}
