package aztech.modern_industrialization.datagen.recipe;

import aztech.modern_industrialization.MIItem;
import aztech.modern_industrialization.machines.init.MIMachineRecipeTypes;
import aztech.modern_industrialization.recipe.json.MIRecipeJson;
import aztech.modern_industrialization.recipe.json.RecipeJson;
import aztech.modern_industrialization.util.string.RS;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Item;

import java.util.function.Consumer;

public class PackUnpackRecipesProvider extends MIRecipesProvider{
    private Consumer<FinishedRecipe> consumer;

    public PackUnpackRecipesProvider(FabricDataGenerator dataGenerator){
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer <FinishedRecipe> consumer){
        this.consumer = consumer;
        addPacker("minecraft:amethyst_shard", "minecraft:amethyst_block", 4);
        addPacker("minecraft:brick", "minecraft:bricks", 4);
        addPacker("modern_industrialization:cellulose_fibres", "modern_industrialization:fibreboard", 4);
        addPacker("modern_industrialization:fire_clay_brick", "modern_industrialization:fire_clay_bricks", 4);
        addPacker("minecraft:glowstone_dust", "minecraft:glowstone", 4);
        addPacker("minecraft:honeycomb", "minecraft:honeycomb_block", 4);
        addPacker("minecraft:ice", "minecraft:packed_ice", true);
        addPacker("minecraft:magma_cream", "minecraft:magma_block", 4);
        addPacker("minecraft:melon_slice", "minecraft:melon", true);
        addPacker("minecraft:nether_brick", "minecraft:nether_bricks", 4);
        addPacker("minecraft:nether_wart", "minecraft:nether_wart_block", true);
        addPacker("minecraft:packed_ice", "minecraft:blue_ice", true);
        addPacker("minecraft:pointed_dripstone", "minecraft:dripstone_block", 4);
        addPacker("minecraft:popped_chorus_fruit", "minecraft:purpur_block", 4);
        addPacker("minecraft:prismarine_shard", "minecraft:prismarine", 4);
        addPacker("minecraft:quartz", "minecraft:quartz_block", 4);
        addPacker("minecraft:red_sand", "minecraft:red_sandstone", 4);
        addPacker("minecraft:sand", "minecraft:sandstone", 4);
        addPacker("minecraft:snowball", "minecraft:snow_block", 4);
        addReversible("minecraft:bone_meal", "minecraft:bone_block", true);
        addReversible("minecraft:dried_kelp", "minecraft:dried_kelp_block", true);
        addReversible("minecraft:netherite_ingot", "minecraft:netherite_block", true);
        addReversible("minecraft:prismarine_shard", "minecraft:prismarine_bricks", true);
        addReversible("modern_industrialization:rubber_sheet", "modern_industrialization:rubber_block", true);
        addReversible("minecraft:slime_ball", "minecraft:slime_block", true);
        addReversible("modern_industrialization:straw", "modern_industrialization:straw_bale", true);
        addReversible("modern_industrialization:wax", "modern_industrialization:wax_block", true);
        addReversible("minecraft:wheat", "minecraft:hay_block", true);
    }

    private String getRecipeID(String input, String output){
        return "%s_to_%s".formatted(RS.clean(input), RS.clean(output));
    }

    private void addReversible(String input, String output){
        addReversible(input, output, 9);
    }

    private void addReversible(String input, String output, int quantity){
        addReversible(input, output, quantity, false);
    }

    private void addReversible(String input, String output, boolean template){
        addReversible(input, output, 9, template);
    }

    private void addReversible(String input, String output, int quantity, boolean template){
        addPacker(input, output, quantity, template);
        addUnpacker(output, input, quantity);
    }

    private void addPacker(String input, String output){
        addPacker(input, output, 9);
    }

    private void addPacker(String input, String output, boolean template){
        addPacker(input, output, 9, template);
    }

    private void addPacker(String input, String output, int quantity){
        addPacker(input, output, quantity, false);
    }

    private void addPacker(String input, String output, int quantity, boolean template){
        if(template){
            addPacker(input, output, quantity, MIItem.ITEM_PACKER_BLOCK_TEMPLATE);
        }
        else{
            addPacker(getRecipeID(input, output), MIRecipeJson.create(MIMachineRecipeTypes.PACKER, 2, 100)
                    .addItemInput(input, quantity).addItemOutput(output, 1));
        }
    }

    private void addPacker(String input, String output, int quantity, Item templateItem){
        addPacker(getRecipeID(input, output), MIRecipeJson.create(MIMachineRecipeTypes.PACKER, 2, 100)
                .addItemInput(input.replace("mi:", "modern_industrialization:"), quantity)
                .addItemInput(templateItem, 1, 0)
                .addItemOutput(output.replace("mi:", "modern_industrialization:"), 1));
    }

    private void addPacker(String id, RecipeJson<?> recipeJson){
        recipeJson.offerTo(consumer, "packer/" + id);
    }

    private void addUnpacker(String input, String output){
        addUnpacker(input, output, 9);
    }

    private void addUnpacker(String input, String output, int quantity){
        addUnpacker(getRecipeID(input, output), MIRecipeJson.create(MIMachineRecipeTypes.UNPACKER, 2, 100)
                .addItemInput(input.replace("mi:", "modern_industrialization:"), 1)
                .addItemOutput(output.replace("mi:", "modern_industrialization:"), quantity));
    }

    private void addUnpacker(String id, RecipeJson<?> recipeJson){
        recipeJson.offerTo(consumer, "unpacker/" + id);
    }

}
