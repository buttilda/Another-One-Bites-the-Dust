package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;

import com.creativemd.randomadditions.common.energy.machine.MachineSystem;
import com.creativemd.randomadditions.common.energy.machine.recipe.MachineRecipe;

public class RandomAdditionsModule extends RecipesModule {

	public RandomAdditionsModule() {
		super(CompatType.RANDOM_ADDITIONS, "iron", "gold", "copper", "tin", "silver", "lead");
	}

	@Override
	public void initOre(Ore ore) {
		String name = ore.name();
		MachineSystem.crusher.registerRecipe(new MachineRecipe("ingot" + name, getOreDictItem("dust" + name), 250));
		MachineSystem.crusher.registerRecipe(new MachineRecipe("ore" + name, getOreDictItem("dust" + name, 2), 250));
	}
}