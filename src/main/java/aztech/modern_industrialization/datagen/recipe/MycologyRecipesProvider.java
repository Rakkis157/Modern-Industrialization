package aztech.modern_industrialization.datagen.recipe;

import aztech.modern_industrialization.items.mycology.MIMushroom;
import aztech.modern_industrialization.items.mycology.Mushroom;
import aztech.modern_industrialization.recipe.json.RecipeJson;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class MycologyRecipesProvider  extends MIRecipesProvider{
    private Consumer<FinishedRecipe> consumer;
    public MycologyRecipesProvider(FabricDataGenerator dataGenerator){
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer <FinishedRecipe> consumer){
        this.consumer = consumer;
        addMycologyRecipe(MIMushroom.BAMBOO_MUSHROOM);
        addMycologyRecipe(MIMushroom.BEECH_MUSHROOM);
        addMycologyRecipe(MIMushroom.BROWN_MUSHROOM);
        addMycologyRecipe(MIMushroom.BUTTON_MUSHROOM);
        addMycologyRecipe(MIMushroom.ENOKITAKE_MUSHROOM);
        addMycologyRecipe(MIMushroom.MAITAKE_MUSHROOM);
        addMycologyRecipe(MIMushroom.OYSTER_MUSHROOM);
        addMycologyRecipe(MIMushroom.PORTABELLA_MUSHROOM);
        addMycologyRecipe(MIMushroom.RED_MUSHROOM);
        addMycologyRecipe(MIMushroom.ROPE_MUSHROOM);
        addMycologyRecipe(MIMushroom.SHIITAKE_MUSHROOM);
        addMycologyRecipe(MIMushroom.SNOW_MUSHROOM);
        addMycologyRecipe(MIMushroom.SPLITGILL_MUSHROOM);
        addMycologyRecipe(MIMushroom.STRAW_MUSHROOM);
    }

    private void addMycologyRecipe(Mushroom mushroom){
        String id = mushroom.getName();
        addMycologyRecipe("mixer/" + id + "_grow_bag", mushroom.getBagRecipe());
        addMycologyRecipe("growth_chamber/" + id, mushroom.getGrowingRecipe());
        addMycologyRecipe("mixer/" + id + "_grain_spawn", mushroom.getSpawnRecipe());
        addMycologyRecipe("sampling_machine/sample_" + id, mushroom.getSampleRecipe());
    }

    private void addMycologyRecipe(String id, RecipeJson<?> recipeJson){
        recipeJson.offerTo(consumer, "mycology/" + id);
    }
}
