package aztech.modern_industrialization.items.aquaculture;

import aztech.modern_industrialization.MIFluids;
import aztech.modern_industrialization.machines.init.MIMachineRecipeTypes;
import aztech.modern_industrialization.recipe.json.MIRecipeJson;
import aztech.modern_industrialization.recipe.json.RecipeJson;
import aztech.modern_industrialization.util.string.RS;

@SuppressWarnings("rawtypes")
public class Seafood {
    private static final String[] DIET = {"blue_algae_mixture", "brine_shrimp_live", "herbivorous_fish_feed", "carnivorous_fish_feed"};
    private static final String[] HABITAT = {"minecraft:water", "modern_industrialization:salt_water"};
    private static final double[] SIZE = {0.5, 1.0, 2.0, 3.0, 4.0};
    private final String name;
    private final String fish;
    private final String liveFish;
    private final String roe;
    private final String diet;
    private final String fillet;
    private final String adultHabitat;
    private final String youngHabitat;
    private final double size;
    private final int capacity;
    private final boolean isFish;
    private final boolean fromMC;

    public Seafood(String name, int diet, int size, int adultHabitatType, int youngHabitatType, boolean isFish){
        this.name = name;
        if (name.equals("salmon") || name.equals("cod")){
            this.fish = RS.isMC(name);
            this.fromMC = false;
        }
        else{
            this.fish = RS.isMI(name);
            this.fromMC = true;
        }
        this.liveFish = RS.isMI(name + "_live");
        this.roe = RS.isMI(name + "_roe");
        this.diet = RS.isMI(DIET[diet]);
        this.fillet = RS.isMI(name + "_fillet");
        this.adultHabitat = HABITAT[adultHabitatType];
        this.youngHabitat = HABITAT[youngHabitatType];
        this.size = SIZE[size];
        double capacity = 24.0/this.size;
        this.capacity = (int)capacity;
        this.isFish = isFish;
    }

    public RecipeJson getBreedingRecipe(){
        RecipeJson recipe;
        if (diet.equals("modern_industrialization:blue_algae_mixture")){
            recipe = MIRecipeJson.create(MIMachineRecipeTypes.AQUACULTURE_POND, 4, 600)
                    .addItemInput(liveFish, 4).addItemOutput(roe, 20)
                    .addFluidInput(youngHabitat, 10000).addFluidInput(diet, 2000)
                    .addFluidOutput(youngHabitat, 10000);
        }
        else{
            recipe = MIRecipeJson.create(MIMachineRecipeTypes.AQUACULTURE_POND, 4, 600)
                    .addItemInput(liveFish, 4).addItemInput(diet, (int)(size*4))
                    .addItemOutput(roe, 20).addFluidInput(youngHabitat, 12000)
                    .addFluidOutput(youngHabitat, 10000);
        }
        return recipe;
    }

    public RecipeJson getCuttingRecipe(){
        int quantity;
        if (size >= 4){
            quantity = 8;
        }
        else if (size == 3){
            quantity = 4;
        }
        else {
            quantity = 2;
        }
        return MIRecipeJson.create(MIMachineRecipeTypes.CUTTING_MACHINE, 2, 200)
                .addItemInput(fish, 1).addItemOutput(fillet, quantity)
                .addFluidInput(MIFluids.LUBRICANT, 1);
    }

    public RecipeJson getCrushingRecipe(){
        return MIRecipeJson.create(MIMachineRecipeTypes.MACERATOR, 2, 200)
                .addItemInput(fish, capacity).addItemOutput("modern_industrialization:crushed_fish", 24);
    }

    public RecipeJson getFarmingRecipe(){
        RecipeJson recipe;
        if (diet.equals("modern_industrialization:blue_algae_mixture")){
            recipe = MIRecipeJson.create(MIMachineRecipeTypes.AQUACULTURE_POND, 4, 1200)
                    .addItemInput(roe, capacity).addItemOutput(liveFish, capacity)
                    .addFluidInput(adultHabitat, 9000).addFluidInput(diet, 3000)
                    .addFluidOutput(youngHabitat, 9000);
        }
        else{
            recipe = MIRecipeJson.create(MIMachineRecipeTypes.AQUACULTURE_POND, 4, 1200)
                    .addItemInput(roe, capacity).addItemInput(diet, 24)
                    .addItemOutput(liveFish, capacity).addFluidInput(adultHabitat, 12000)
                    .addFluidOutput(youngHabitat, 9000);
        }
        return recipe;
    }

    public RecipeJson getFilterRecipe(){
        RecipeJson recipe;
        recipe = MIRecipeJson.create(MIMachineRecipeTypes.FILTER, 2, 100)
                .addItemInput(liveFish, 1).addItemOutput(fish, 1)
                .addFluidOutput(MIFluids.AQUARIUM_WASTE, 100);
        return recipe;
    }

    public String getName(){
        return name;
    }

    public int getCapacity(){
        return capacity;
    }

    public double getSize(){
        return size;
    }

    public boolean isFish(){
        return isFish;
    }

    public boolean isFromMC() {
        return fromMC;
    }

}
