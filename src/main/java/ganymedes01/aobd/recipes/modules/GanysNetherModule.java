package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;
import ganymedes01.ganysnether.recipes.MagmaticCentrifugeRecipes;
import net.minecraft.item.ItemStack;

public class GanysNetherModule extends RecipesModule {

	public GanysNetherModule() {
		super(CompatType.GANYS_NETHER);
	}

	@Override
	public void initOre(Ore ore) {
		String name = ore.name();
		ItemStack ingot = getOreDictItem("ingot" + name);
		ItemStack nugget = getOreDictItem("nugget" + ore.extra());

		MagmaticCentrifugeRecipes.addRecipe("ore" + name, "ore" + name, ingot, ingot, ingot, nugget);
	}
}