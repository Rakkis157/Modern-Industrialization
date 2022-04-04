package aztech.modern_industrialization.items.mycology;

import aztech.modern_industrialization.MIFluids;
import aztech.modern_industrialization.MIItem;
import aztech.modern_industrialization.machines.init.MIMachineRecipeTypes;
import aztech.modern_industrialization.recipe.json.MIRecipeJson;
import aztech.modern_industrialization.recipe.json.RecipeJson;
import aztech.modern_industrialization.util.string.RS;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

@SuppressWarnings("rawtypes")
public class Mushroom {

    private final String name;
    private final String mushroom;
    private final String grainSpawn;
    private final String growBag;
    private final String sample;
    private final String substrate;
    private final Fluid atmosphere;
    private final boolean isEdible;

    public Mushroom(String name, boolean isEdible, int habitat){
        Fluid[] FLUIDS = {Fluids.WATER, MIFluids.SULFUR_DIOXIDE, MIFluids.BLOOD};
        String[] SUBSTRATE = {"mushroom_substrate", "nether_substrate", "arcane_substrate"};
        this.name = name;
        if(name.equals("brown_mushroom") || name.equals("red_mushroom")){
            this.mushroom = RS.isMC(name);
        }
        else{
            this.mushroom = RS.isMI(name);
        }
        this.grainSpawn = RS.isMI(name + "_grain_spawn");
        this.growBag = RS.isMI(name + "_grow_bag");
        this.sample = RS.isMI("sample_" + name);
        this.substrate = RS.isMI(SUBSTRATE[habitat]);
        this.atmosphere = FLUIDS[habitat];
        this.isEdible = isEdible;
    }

    public RecipeJson getBagRecipe(){
        return MIRecipeJson.create(MIMachineRecipeTypes.MIXER, 2, 200)
                .addItemInput(sample, 1).addItemInput(MIItem.GROW_BAG, 2)
                .addItemInput(substrate, 1).addItemOutput(growBag, 2);
    }

    public RecipeJson getGrowingRecipe(){
        return MIRecipeJson.create(MIMachineRecipeTypes.GROWTH_CHAMBER, 2, 800)
                .addItemInput(growBag, 6, 0.4).addFluidInput(atmosphere, 1000)
                .addItemOutput(mushroom, 12);
    }

    public RecipeJson getSampleRecipe(){
        return MIRecipeJson.create(MIMachineRecipeTypes.SAMPLING_MACHINE, 2, 200)
                .addItemInput(mushroom, 1).addItemInput(MIItem.TEST_TUBE, 1)
                .addItemOutput(sample, 1);
    }

    public String getName(){
        return name;
    }

    @SuppressWarnings("unused")
    public boolean isEdible(){
        return isEdible;
    }
}
