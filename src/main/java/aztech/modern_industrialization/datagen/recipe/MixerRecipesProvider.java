package aztech.modern_industrialization.datagen.recipe;

import aztech.modern_industrialization.machines.init.MIMachineRecipeTypes;
import aztech.modern_industrialization.recipe.json.MIRecipeJson;
import aztech.modern_industrialization.recipe.json.RecipeJson;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.level.material.Fluids;

import javax.annotation.Nullable;
import java.util.function.Consumer;

import static aztech.modern_industrialization.util.string.RS.isMC;

public class MixerRecipesProvider  extends MIRecipesProvider{
    private Consumer<FinishedRecipe> consumer;
    public MixerRecipesProvider(FabricDataGenerator dataGenerator){
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer<FinishedRecipe> consumer) {
        this.consumer = consumer;
        String[] colors = {"black", "blue", "brown", "cyan", "gray", "green", "light_blue", "light_gray", "lime",
                "magenta", "orange", "pink", "purple", "red", "white", "yellow"};
        for (int i = 0; i < colors.length; i++){
            addConcreteRecipes(colors[i]);
        }
        String[] vanillaStones = {"andesite", "calcite", "cobblestone", "cobbled_deepslate", "diorite", "dripstone_block", "granite"};
        for (int j = 0; j < vanillaStones.length; j++){
            addStoneGenRecipes(vanillaStones[j]);
        }

    }
    private void addConcreteRecipes(String id){
        addConcretePowderRecipe(id);
        addConcreteWashingRecipes(id);
    }

    private void addConcreteWashingRecipes(String id){
        addMixerRecipe( "concrete/" + id + "_concrete", MIRecipeJson.create(MIMachineRecipeTypes.MIXER, 2, 100)
                        .addItemInput(isMC(id + "_concrete_powder"), 1).addFluidInput(Fluids.WATER, 1000)
                        .addItemOutput(isMC(id + "_concrete"), 1),
                    "vanilla_recipes/");
    }

    private void addConcretePowderRecipe(String id){
        addMixerRecipe("concrete/" + id + "_concrete_powder", MIRecipeJson.create(MIMachineRecipeTypes.MIXER, 2, 100)
                        .addItemInput(isMC("sand"), 4).addItemInput(isMC("gravel"), 4)
                        .addItemInput(isMC(id + "_dye"), 1)
                        .addItemOutput(isMC(id + "_concrete_powder"), 8),
                        "vanilla_recipes/");
    }

    private void addStoneGenRecipes(String id){
        addMixerRecipe("stone_gen/" + id, MIRecipeJson.create(MIMachineRecipeTypes.MIXER, 2, 100)
                .addItemInput(isMC(id), 1).addFluidInput(Fluids.LAVA, 1000, 0)
                .addItemOutput(isMC(id), 2), "vanilla_recipes/");
    }

    private void addMixerRecipe(String id, RecipeJson<?> recipeJson){
        addMixerRecipe(id, recipeJson, null);
    }

    private void addMixerRecipe(String id, RecipeJson<?> recipeJson, @Nullable String type){
        recipeJson.offerTo(consumer, type + "mixer/" + id);
    }

}
