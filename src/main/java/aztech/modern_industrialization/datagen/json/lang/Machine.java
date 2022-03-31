package aztech.modern_industrialization.datagen.json.lang;

import aztech.modern_industrialization.util.string.RS;

public class Machine {
    private static final String[] MACHINES = {"aquaculture_pond", "bioreactor", "combustion_chamber", "composter", "dehydrator",
            "extractor", "filter", "growth_chamber", "industrial_oven", "industrial_press", "sampling_machine", "tree_planter"};

    public static void main(String []args){
        System.out.println("Generating code for lang/en_us.json");
        for (int i = 0; i < MACHINES.length; i++){
            printMachine(MACHINES[i]);
        }
    }

    private static void printMachine(String id){
        String name = RS.capitalizeAllWords(id);
        String output = "\"block.modern_industrialization:" + id + "\": \"" + name + "\",";
        System.out.println(output);
    }
}
