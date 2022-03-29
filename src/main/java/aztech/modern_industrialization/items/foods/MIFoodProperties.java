package aztech.modern_industrialization.items.foods;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.food.Foods;

public class MIFoodProperties extends Foods {

    public static final int RAW = 1;
    public static final int POISON = 2;
    public static final int WITHER = 3;

    public static FoodProperties food(FoodPresets foodType){
        return food(0, foodType);
    }

    public static FoodProperties food(int key, FoodPresets foodType){
        return food(key, foodType.nutrition, foodType.saturation);
    }

    public static FoodProperties food(int key, int nutrition, double saturation){
        switch (key){
            case 1: return food(nutrition, saturation, 0.3, MobEffects.HUNGER, 600, 0);
            case 2: return food(nutrition, saturation, 1.0, MobEffects.POISON, 400, 0);
            case 3: return food(nutrition, saturation, 1.0, MobEffects.WITHER, 200, 0);
            default: return food(nutrition, saturation);
        }
    }

    public static FoodProperties food(int nutrition, double saturation){
        return (new Builder()).nutrition(nutrition).saturationMod((float)saturation).build();
    }

    public static FoodProperties food(FoodPresets foodType, double probability, MobEffect mobEffect,
                                            int duration, int amplifier){
        return food(foodType.nutrition, foodType.saturation, probability, mobEffect, duration, amplifier);
    }

    public static FoodProperties food(int nutrition, double saturation, double probability,
                                         MobEffect mobEffect, int duration, int amplifier){
        return (new Builder()).nutrition(nutrition).saturationMod((float)saturation)
                .effect(new MobEffectInstance(mobEffect, duration, amplifier), (float)probability)
                .build();
    }

    public static final FoodProperties ALGAE_BAR = food(10, 1, 1, MobEffects.CONFUSION, 200, 2);

    //Batter Fried
    public static final FoodProperties FRIED_MUSHROOMS = food(2, 0.4);
    public static final FoodProperties SHRIMP_TEMPURA = food(3, 0.5);
    public static final FoodProperties CHICKEN_KARAAGE = food(4, 0.6);

    //

    //Meals

    //Meat
    public static final FoodProperties SALTED_BEEF = food(5, 0.5);
    public static final FoodProperties SALTED_MUTTON = food(4, 0.5);
    public static final FoodProperties SALTED_PORK = food(5, 0.5);

    //Mushrooms
    public static final FoodProperties BUTTON_MUSHROOM = food(FoodPresets.MUSHROOM);
    public static final FoodProperties ENOKI_MUSHROOM = food(FoodPresets.MUSHROOM);
    public static final FoodProperties MUCKCAP = food(FoodPresets.MUSHROOM, 1, MobEffects.WITHER, 100, 0);
    public static final FoodProperties OYSTER_MUSHROOM = food(FoodPresets.MUSHROOM);
    public static final FoodProperties SHIITAKE_MUSHROOM = food(FoodPresets.MUSHROOM);

    //Seafood: Raw
    public static final FoodProperties RAW_FILLET = food(RAW, 1, 1.0);
    public static final FoodProperties RAW_FISH = food(RAW, FoodPresets.RAW_FISH);
    public static final FoodProperties CRAB = food(RAW, 2, 0.2);
    public static final FoodProperties SHRIMP = food(RAW, 1, 0.1);

    //Seafood: Prepared
    public static final FoodProperties COD_FILLET = food(RAW, FoodPresets.RAW_FISH);
    public static final FoodProperties COOKED_CATFISH = food(4, 0.6);
    public static final FoodProperties COOKED_CRAB = food(4, 0.5);
    public static final FoodProperties COOKED_SALMON_FILLET = food(3,0.3);
    public static final FoodProperties COOKED_SHRIMP = food(2, 0.2);
    public static final FoodProperties SALMON_FILLET = food(1,0.1);



    //Vegetables


}
