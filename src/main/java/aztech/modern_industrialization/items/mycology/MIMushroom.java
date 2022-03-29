package aztech.modern_industrialization.items.mycology;

public class MIMushroom {

    private static final int REGULAR = 0;
    private static final int NETHER = 1;
    private static final int ARCANE = 1;


    private static Mushroom of(String name){
        return of(name + "_mushroom", true, REGULAR);
    }

    private static Mushroom of(String name, boolean isEdible, int substrate){
        return new Mushroom(name, isEdible, substrate);
    }

    public static final Mushroom BAMBOO_MUSHROOM = of("bamboo");
    public static final Mushroom BEECH_MUSHROOM = of("beech");
    public static final Mushroom BROWN_MUSHROOM = of("brown");
    public static final Mushroom BUTTON_MUSHROOM = of("button");
    public static final Mushroom ENOKITAKE_MUSHROOM = of("enokitake");
    public static final Mushroom MAITAKE_MUSHROOM = of("maitake");
    public static final Mushroom OYSTER_MUSHROOM = of("oyster");
    public static final Mushroom PORTABELLA_MUSHROOM = of("portabella");
    public static final Mushroom RED_MUSHROOM = of("red");
    public static final Mushroom ROPE_MUSHROOM = of("rope");
    public static final Mushroom ROTGUT_MUSHROOM = of("rotgut");
    public static final Mushroom SHIITAKE_MUSHROOM = of("shiitake");
    public static final Mushroom SNOW_MUSHROOM = of("snow");
    public static final Mushroom SPLITGILL_MUSHROOM = of("splitgill");
    public static final Mushroom STRAW_MUSHROOM = of("straw");

    public static Mushroom[] MUSHROOM = {BAMBOO_MUSHROOM, BEECH_MUSHROOM, BROWN_MUSHROOM, BUTTON_MUSHROOM, ENOKITAKE_MUSHROOM,
            MAITAKE_MUSHROOM, OYSTER_MUSHROOM, PORTABELLA_MUSHROOM, RED_MUSHROOM, ROPE_MUSHROOM, SHIITAKE_MUSHROOM, SNOW_MUSHROOM,
            SPLITGILL_MUSHROOM, STRAW_MUSHROOM};

}
