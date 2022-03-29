package aztech.modern_industrialization.datagen.json.lang;

import aztech.modern_industrialization.items.aquaculture.MISeafood;
import aztech.modern_industrialization.util.string.RS;

public class Seafood {

    public static void main(String []args){
        System.out.println("Generating code for lang/en_us.json");
        for (int i = 0; i < MISeafood.SEAFOOD.length; i++){
            printLang(MISeafood.SEAFOOD[i]);
        }
    }

    private static void printLang(aztech.modern_industrialization.items.aquaculture.Seafood seafood){
        String id = seafood.getName();
        String name = RS.capitalizeAllWords(id);
        if (!id.equals("brine_shrimp") || seafood.isFromMC()){
            System.out.println("\"item.modern_industrialization." + id + "\": \"" + name + "\",");
        }
        System.out.println("\"item.modern_industrialization." + id + "_live\": \"Live " + name + "\",");
        System.out.println("\"item.modern_industrialization." + id + "_roe\": \"" + name + " Roe\",");
        if(seafood.getSize() >= 1 && seafood.isFish()){
            System.out.println("\"item.modern_industrialization." + id + "_fillet\": \"" + name + " Fillet\",");
        }
    }
}
