package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;
import ganymedes01.ganysnether.recipes.MagmaticCentrifugeRecipes;
import net.minecraft.item.ItemStack;

public class GanysNether extends RecipesModule {

	public GanysNether() {
		super(CompatType.GANYS_NETHER);
	}

	@Override
	public void initOre(Ore ore) {
		String name = ore.name();
		ItemStack ingot = getOreStack("ingot", ore);
		ItemStack nugget = getOreStackExtra("nugget", ore);

		MagmaticCentrifugeRecipes.INSTANCE.addRecipe("ore" + name, "ore" + name, ingot, ingot, ingot, nugget);
	}
}