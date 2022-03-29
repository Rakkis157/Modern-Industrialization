package aztech.modern_industrialization.datagen.recipe;

import aztech.modern_industrialization.items.aquaculture.MISeafood;
import aztech.modern_industrialization.items.aquaculture.Seafood;
import aztech.modern_industrialization.recipe.json.RecipeJson;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class AquacultureRecipesProvider extends MIRecipesProvider{
    private Consumer<FinishedRecipe> consumer;
    public AquacultureRecipesProvider(FabricDataGenerator dataGenerator){
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer <FinishedRecipe> consumer){
        this.consumer = consumer;

        addAquacultureRecipe(MISeafood.ANCHOVY);
        addAquacultureRecipe(MISeafood.BRINE_SHRIMP);
        addAquacultureRecipe(MISeafood.CATFISH);
        addAquacultureRecipe(MISeafood.CARP);
        addAquacultureRecipe(MISeafood.COD);
        addAquacultureRecipe(MISeafood.HERRING);
        addAquacultureRecipe(MISeafood.MACKEREL);
        addAquacultureRecipe(MISeafood.POMFRET);
        addAquacultureRecipe(MISeafood.SALMON);
        addAquacultureRecipe(MISeafood.SARDINE);
        addAquacultureRecipe(MISeafood.SHRIMP);
        addAquacultureRecipe(MISeafood.SKIPJACK_TUNA);
        addAquacultureRecipe(MISeafood.STINGRAY);
        addAquacultureRecipe(MISeafood.TILAPIA);
    }

    private void addAquacultureRecipe(Seafood seafood){
        String id = seafood.getName();
        addAquacultureRecipe("aquaculture_pond/" + id + "_breeding", seafood.getBreedingRecipe());
        addAquacultureRecipe("aquaculture_pond/" + id + "_farming", seafood.getFarmingRecipe());
        if(!id.equals("brine_shrimp")){
            addAquacultureRecipe("macerator/" + id, seafood.getCrushingRecipe());
            addAquacultureRecipe("filter/" + id, seafood.getFilterRecipe());
        }
        if(seafood.getSize() >= 1 && seafood.isFish()){
            addAquacultureRecipe("cutting_machine/" + id, seafood.getCuttingRecipe());
        }
    }

    private void addAquacultureRecipe(String id, RecipeJson<?> recipeJson){
        recipeJson.offerTo(consumer, "aquaculture/" + id);
    }
}
