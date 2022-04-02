package aztech.modern_industrialization.datagen.recipe;

import aztech.modern_industrialization.MIItem;
import aztech.modern_industrialization.machines.init.MIMachineRecipeTypes;
import aztech.modern_industrialization.recipe.json.MIRecipeJson;
import aztech.modern_industrialization.recipe.json.RecipeJson;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Item;

import java.util.function.Consumer;

import static aztech.modern_industrialization.util.string.RS.clean;
import static aztech.modern_industrialization.util.string.RS.isMI;

public class ForgeHammerRecipesProvider extends MIRecipesProvider{
    private Consumer<FinishedRecipe> consumer;
    public ForgeHammerRecipesProvider(FabricDataGenerator dataGenerator){
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer<FinishedRecipe> consumer){
        this.consumer = consumer;

        addBlankRecipe();
        addForgeHammerRecipe(MIItem.EXTRUDER_BAG_TEMPLATE);
        addForgeHammerRecipe(MIItem.INDUSTRIAL_OVEN_BUN_TEMPLATE);
        addForgeHammerRecipe(MIItem.INDUSTRIAL_OVEN_LOAF_TEMPLATE);
        addForgeHammerRecipe(MIItem.PACKER_BLOCK_TEMPLATE);
        addForgeHammerRecipe(MIItem.PACKER_DOUBLE_INGOT_TEMPLATE);
    }

    private void addBlankRecipe(){
        String id = "blank_template";
        addForgeHammerRecipe(id, MIRecipeJson.create(MIMachineRecipeTypes.FORGE_HAMMER, 0, 0)
                .addItemInput(isMI("large_steel_plate"), 1).addItemOutput(isMI(id), 4));
    }

    private void addForgeHammerRecipe(Item item){
        String id = clean(item.toString());
        addForgeHammerRecipe(id, MIRecipeJson.create(MIMachineRecipeTypes.FORGE_HAMMER, 0, 0)
                .addItemInput(MIItem.BLANK_TEMPLATE, 1).addItemOutput(item, 1));
    }

    private void addForgeHammerRecipe(String id, RecipeJson<?> recipeJson){
        recipeJson.offerTo(consumer, "forge_hammer/" + id);
    }
}
