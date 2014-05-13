package ganymedes01.aobd.recipes;

import ganymedes01.aobd.AOBD;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.modules.EnderIORecipes;
import ganymedes01.aobd.recipes.modules.IC2Recipes;
import ganymedes01.aobd.recipes.modules.MekanismRecipes;
import ganymedes01.aobd.recipes.modules.RailcraftRecipes;
import ganymedes01.aobd.recipes.modules.TE3Recipes;
import ganymedes01.aobd.recipes.modules.ThaumcraftRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipesHandler extends RecipesModule {

	public static void init() {
		craftingRecipes();

		if (AOBD.enableIC2)
			IC2Recipes.init();
		if (AOBD.enableRailcraft)
			RailcraftRecipes.init();
		if (AOBD.enableMekanism)
			MekanismRecipes.init();
		if (AOBD.enableEnderIO)
			EnderIORecipes.init();
		if (AOBD.enableThaumcraft)
			ThaumcraftRecipes.init();
		if (AOBD.enableTE3)
			TE3Recipes.init();
	}

	public static void postInit() {
		if (AOBD.enableThaumcraft)
			ThaumcraftRecipes.postInit();
	}

	private static void craftingRecipes() {
		for (Ore ore : Ore.ores) {
			String name = ore.name();
			GameRegistry.addRecipe(new ShapedOreRecipe(getOreDictItem("dust" + name), "xxx", "xxx", "xxx", 'x', "dustTiny" + name));
			GameRegistry.addSmelting(getOreDictItem("crushed" + name), getOreDictItem("ingot" + name), 0.2F);
			GameRegistry.addSmelting(getOreDictItem("crushedPurified" + name), getOreDictItem("ingot" + name), 0.2F);
			GameRegistry.addSmelting(getOreDictItem("cluster" + name), getOreDictItem("ingot" + name, 2), 0.2F);
		}
	}
}