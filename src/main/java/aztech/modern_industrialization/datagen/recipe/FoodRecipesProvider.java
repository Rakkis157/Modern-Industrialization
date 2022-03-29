package aztech.modern_industrialization.datagen.recipe;

import aztech.modern_industrialization.MIFluids;
import aztech.modern_industrialization.MIItem;
import aztech.modern_industrialization.machines.init.MIMachineRecipeTypes;
import aztech.modern_industrialization.recipe.json.MIRecipeJson;
import aztech.modern_industrialization.recipe.json.RecipeJson;
import aztech.modern_industrialization.recipe.json.SmeltingRecipeJson;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Consumer;

public class FoodRecipesProvider extends MIRecipesProvider{
    private Consumer<FinishedRecipe> consumer;
    public FoodRecipesProvider(FabricDataGenerator dataGenerator){
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer<FinishedRecipe> consumer){
        this.consumer = consumer;

        //Cooking
        addCookingRecipes("modern_industrialization:salmon_fillet", "modern_industrialization:cooked_salmon_fillet");

        //Industrial Press
        addPressRecipe(MIItem.CRUSHED_FISH, MIItem.FISH_MEAL, MIFluids.FISH_OIL);
        addPressRecipe(MIItem.SUNFLOWER_SEEDS, MIItem.SUNFLOWER_HULLS, MIFluids.SUNFLOWER_OIL);

        //Threshing Crops
        addThreshRecipe(Items.WHEAT, MIItem.WHEAT_GRAIN);
        addThreshRecipe(Items.SUNFLOWER, MIItem.SUNFLOWER_SEEDS);

    }

    private void addCookingRecipes(String input, String output){
        addCookingRecipe(input, output, 200, 0.7, "campfire_cooking");
        addCookingRecipe(input, output, 200, 0.7, "smelting");
        addCookingRecipe(input, output, 100, 0.7, "smoking");
    }

    private void addCookingRecipe(String input, String output, int cookingTime, double experience, String recipeType) {
        String id = "%s_to_%s_%s".formatted(scrub(input), scrub(output), recipeType);
        addCookingRecipe(id, scrub(input), SmeltingRecipeJson.create(input, output, cookingTime, experience, recipeType));
    }

    private void addCookingRecipe(String id, String foodType, RecipeJson<?> recipeJson){
        recipeJson.offerTo(consumer, "foods/" + foodType + "/" + id);
    }

    private void addPressRecipe(Item input, Item output, Fluid fluid){
        String id = scrub(getItemName(input));
        addPressRecipe(id, MIRecipeJson.create(MIMachineRecipeTypes.INDUSTRIAL_PRESS, 2, 200)
                .addItemInput(input, 4).addItemOutput(output, 4)
                .addFluidOutput(fluid, 200));
    }

    private void addPressRecipe(String id, RecipeJson<?> recipeJson) {
        recipeJson.offerTo(consumer, "biotech/industrial_press/" + id);
    }

    private void addThreshRecipe(Item input, Item output){
        addThreshRecipe(input, output, 2);
    }

    private void addThreshRecipe(Item input, Item output, int amount){
        String id = "%s_to_%s".formatted(scrub(getItemName(input)), scrub(getItemName(output)));
        addThreshRecipe(id, input, output, amount);
    }

    private void addThreshRecipe(String id, Item input, Item output, int amount){
        addThreshRecipe(id, MIRecipeJson.create(MIMachineRecipeTypes.CENTRIFUGE, 4, 200)
                .addItemInput(input, 1).addItemOutput(output , amount).addItemOutput(MIItem.STRAW, 1));
    }

    private void addThreshRecipe(String id, RecipeJson<?> recipeJson) {
        recipeJson.offerTo(consumer, "biotech/centrifuge/" + id);
    }

    private String scrub(String string){
        return string
                .replace("modern_industrialization:", "")
                .replace("minecraft:", "");
    }
}
