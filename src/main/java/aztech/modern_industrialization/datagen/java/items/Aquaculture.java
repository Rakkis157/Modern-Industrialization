package aztech.modern_industrialization.datagen.java.items;

import aztech.modern_industrialization.items.aquaculture.MISeafood;
import aztech.modern_industrialization.items.aquaculture.Seafood;

public class Aquaculture {

    public static void main(String []args){
        System.out.println("Generating code for MIItem.java");
        for (int i = 0; i < MISeafood.SEAFOOD.length; i++){
            printSeafood(MISeafood.SEAFOOD[i]);
        }
    }

    private static void printSeafood(Seafood seafood){
        String id = seafood.getName();
        String ID = id.toUpperCase();
        if (!id.equals("brine_shrimp") || seafood.isFromMC()){
            System.out.println("public static final Item " + ID + " = of(\"" + id + "\", MIFoodProperties.RAW_FISH);");
        }
        System.out.println("public static final Item " + ID + "_LIVE = ofLive(\"" + id + "\");");
        System.out.println("public static final Item " + ID + "_ROE = ofRoe(\"" + id + "\");");
        if(seafood.getSize() >= 1 && seafood.isFish()){
            System.out.println("public static final Item " + ID + "_FILLET = ofFillet(\"" + id + "\");");
        }
    }
}
