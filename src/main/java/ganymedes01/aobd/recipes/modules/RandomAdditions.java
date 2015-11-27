package ganymedes01.aobd.recipes.modules;

import com.creativemd.randomadditions.common.systems.machine.MachineRecipe;
import com.creativemd.randomadditions.common.systems.machine.SubSystemMachine;

import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;

public class RandomAdditions extends RecipesModule {

	public RandomAdditions() {
		super(CompatType.RANDOM_ADDITIONS, "iron", "gold", "copper", "tin", "silver", "lead", "aluminium");
	}

	@Override
	public void initOre(Ore ore) {
		String name = ore.name();
		SubSystemMachine.instance.crusher.registerRecipe(new MachineRecipe("ingot" + name, getOreStack("dust", ore)));
		SubSystemMachine.instance.crusher.registerRecipe(new MachineRecipe("ore" + name, getOreStack("dust", ore, 2)));
	}
}