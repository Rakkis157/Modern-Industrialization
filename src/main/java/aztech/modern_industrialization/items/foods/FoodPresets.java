package aztech.modern_industrialization.items.foods;

public class FoodPresets {

    int nutrition;
    double saturation;

    FoodPresets(int nutrition, double saturation){
        this.nutrition = nutrition;
        this.saturation = saturation;
    }

    public static final FoodPresets MUSHROOM_COOKED = new FoodPresets(2, 0.3);
    public static final FoodPresets MUSHROOM = new FoodPresets(1, 0.1);
    public static final FoodPresets RAW_FISH = new FoodPresets(2, 0.1);
}
