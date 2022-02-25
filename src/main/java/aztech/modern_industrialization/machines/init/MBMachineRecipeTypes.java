package aztech.modern_industrialization.machines.init;

import aztech.modern_industrialization.MIIdentifier;
import aztech.modern_industrialization.machines.recipe.MachineRecipeType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class MBMachineRecipeTypes {

    private static final List<MachineRecipeType> recipeTypes = new ArrayList<>();

    public static final MachineRecipeType AQUARIUM = create("aquarium").withItemInputs().withFluidInputs().withItemOutputs().withFluidInputs();
    public static final MachineRecipeType BIOREACTOR = create("bioreactor").withItemInputs().withFluidInputs().withItemOutputs().withFluidOutputs();
    public static final MachineRecipeType EXTRACTOR = create("extractor").withItemInputs().withItemOutputs().withFluidOutputs();
    public static final MachineRecipeType FILTER = create("filter").withItemInputs().withFluidInputs().withItemOutputs().withFluidOutputs();

    public static void init() {
        // init static
    }

    public static List<MachineRecipeType> getRecipeTypes() {
        return Collections.unmodifiableList(recipeTypes);
    }

    private static MachineRecipeType create(String name) {
        return create(name, MachineRecipeType::new);
    }

    private static MachineRecipeType create(String name, Function<ResourceLocation, MachineRecipeType> ctor) {
        MachineRecipeType type = ctor.apply(new MIIdentifier(name));
        Registry.register(Registry.RECIPE_SERIALIZER, type.getId(), type);
        Registry.register(Registry.RECIPE_TYPE, type.getId(), type);
        recipeTypes.add(type);
        return type;
    }
}
