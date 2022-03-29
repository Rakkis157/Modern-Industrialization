package aztech.modern_industrialization.items.aquaculture;

public class MISeafood {

    //Habitat
    private static final int FRESH = 0;
    private static final int SALT = 1;

    //Diet
    private static final int FILTER = 0;
    private static final int FORAGE = 1;
    private static final int CARNIVORE = 2;
    private static final int HERBIVORE = 3;

    //Sizes
    private static final int TINY = 0;
    private static final int SMALL = 1;
    private static final int MEDIUM = 2;
    private static final int LARGE = 3;
    private static final int GIANT = 4;

    private static Seafood of(String name){
        return of(name, CARNIVORE, MEDIUM, SALT);
    }

    private static Seafood of(String name, int size){
        return of(name, CARNIVORE, size, SALT, true);
    }

    private static Seafood of(String name, int diet, int size, int adultHabitat){
        return of(name, diet, size, adultHabitat, adultHabitat, true);
    }

    private static Seafood of(String name, int diet, int size, int adultHabitat, boolean isFish){
        return of(name, diet, size, adultHabitat, adultHabitat, isFish);
    }

    private static Seafood of(String name, int diet, int size, int youngHabitat, int adultHabitat, boolean isFish){
        return new Seafood(name, diet, size, youngHabitat, adultHabitat, isFish);
    }

    public static final Seafood ANCHOVY = of("anchovy", FORAGE, TINY, SALT);
    public static final Seafood BRINE_SHRIMP = of("brine_shrimp", FILTER, TINY, SALT);
    public static final Seafood CARP = of("carp", HERBIVORE, MEDIUM, FRESH);
    public static final Seafood CATFISH = of("catfish", CARNIVORE, MEDIUM, FRESH);
    public static final Seafood COD = of("cod", LARGE);
    public static final Seafood HERRING = of("herring", FORAGE, SMALL, SALT);
    public static final Seafood MACKEREL = of("mackerel");
    public static final Seafood POMFRET = of("pomfret");
    public static final Seafood SALMON = of("salmon", CARNIVORE, LARGE, SALT, FRESH, true);
    public static final Seafood SARDINE = of("sardine", FORAGE, SMALL, SALT);
    public static final Seafood SHRIMP = of("shrimp", FILTER, TINY, FRESH, false);
    public static final Seafood SKIPJACK_TUNA = of("skipjack_tuna");
    public static final Seafood STINGRAY = of("stingray", CARNIVORE, LARGE, SALT);
    public static final Seafood TILAPIA = of("tilapia", HERBIVORE, MEDIUM, FRESH);
    public static final Seafood YELLOWFIN_TUNA = of("yellowfin_tuna", GIANT);

    public static Seafood[] SEAFOOD =
            {ANCHOVY, BRINE_SHRIMP, CARP, CATFISH, COD, HERRING, MACKEREL, POMFRET, SALMON,
                    SARDINE, SHRIMP, SKIPJACK_TUNA, STINGRAY, TILAPIA, YELLOWFIN_TUNA};

}
