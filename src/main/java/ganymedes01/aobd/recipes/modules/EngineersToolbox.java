package ganymedes01.aobd.recipes.modules;

import emasher.api.CentrifugeRecipeRegistry;
import emasher.api.GrinderRecipeRegistry;
import emasher.api.MultiSmelterRecipeRegistry;
import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;
import cpw.mods.fml.common.registry.GameRegistry;

public class EngineersToolbox extends RecipesModule {

	public EngineersToolbox() {
		super(CompatType.ENGINEERS_TOOLBOX, "gold", "iron", "aluminium", "tin", "copper", "nickel", "lead", "silver", "cobalt", "ardite");
	}

	@Override
	public void initOre(Ore ore) {
		GrinderRecipeRegistry.registerRecipe("ore" + ore.name(), getOreStack("ground", ore));
		GameRegistry.addSmelting(getOreStack("ground", ore), getOreStack("ingot", ore, 2), (float) ore.energy(1));
		MultiSmelterRecipeRegistry.registerRecipe("dustQuicklime", "ground" + ore.name(), getOreStack("dustImpure", ore, 3));
		GameRegistry.addSmelting(getOreStack("dustImpure", ore), getOreStack("ingot", ore), (float) ore.energy(1));
		CentrifugeRecipeRegistry.registerRecipe("dustImpure" + ore.name(), getOreStack("dust", ore), getOreStackExtra("dust", ore), (int) (30.0 / ore.energy(1)));
		GameRegistry.addSmelting(getOreStack("dust", ore), getOreStack("ingot", ore), (float) ore.energy(1));
	}
}