package aztech.modern_industrialization.datagen.json.lang;

import aztech.modern_industrialization.items.mycology.MIMushroom;

public class Mycology {
    //Lines to copy into assets/lang/en_us.json
    private static final String ITEM = "\"item.modern_industrialization.";

    public static void main(String []args){
        /*System.out.println("Generating code for MIItem.java");
        for (int i = 0; i < MIMushroom.MUSHROOM.length; i++){
            printJava(MIMushroom.MUSHROOM[i].getName());
        }*/

        System.out.println("Generating code for lang/en_us.json");
        for (int i = 0; i < MIMushroom.MUSHROOM.length; i++){
            printLang(MIMushroom.MUSHROOM[i].getName());
        }
    }

    private static void printJava(String id){

    }

    private static void printLang(String id){
        String name = id.substring(0,1).toUpperCase() + id.substring(1);
        if(!id.equals("brown_mushroom") && !id.equals("red_mushroom")){
            System.out.println(ITEM + id + "\": \"" + name + " Mushroom\",");
        }
        System.out.println(ITEM + id + "_grow_bag\": \"" + name + " Mushroom Grow Bag\",");
    }
}

