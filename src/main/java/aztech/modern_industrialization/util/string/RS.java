package aztech.modern_industrialization.util.string;

import aztech.modern_industrialization.ModernIndustrialization;

public class RS {
    //Short for Rakkis' Strings, due to brevity needed for this to be useful
    //A list of string functions that I use fairly often and don't want to make a method for everytime I use it.

    public static String capitalizeAllWords(String string){
        String string2 = string.replace("_", " ");
        char[] charArray = string2.toCharArray();
        boolean foundSpace = true;

        for(int i = 0; i < charArray.length; i++) {
            if(Character.isLetter(charArray[i])) {
                if(foundSpace) {
                    charArray[i] = Character.toUpperCase(charArray[i]);
                    foundSpace = false;
                }
            }
            else {
                foundSpace = true;
            }
        }
        string = String.valueOf(charArray);
        return string;
    }

    public static String clean(String string){
        return string.replace('#', '_').replace(':', '_')
                .replace("modern_industrialization:", "").replace("minecraft:", "");
    }

    public static String isMI(String string){
        return ModernIndustrialization.MOD_ID + ":" + string;
    }

    public static String isMC(String string){
        return "minecraft:" + string;
    }

    public static String recipeID(String input, String output){
        return "%s_to_%s".formatted(RS.clean(input), RS.clean(output));
    }
}
