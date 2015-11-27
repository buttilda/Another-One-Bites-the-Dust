package ganymedes01.aobd.recipes.modules;

import cpw.mods.fml.common.registry.GameRegistry;
import ganymedes01.aobd.items.AOBDItem;
import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;
import ganymedes01.ganysnether.recipes.MagmaticCentrifugeRecipes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

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
		if (nugget.getItem() instanceof AOBDItem) { // Only add recipes if the nugget was created by AOBD
			GameRegistry.addRecipe(new ShapedOreRecipe(getOreStack("ingot", ore), "xxx", "xxx", "xxx", 'x', "nugget" + ore.name()));
			GameRegistry.addRecipe(new ShapelessOreRecipe(getOreStack("nugget", ore, 9), "ingot" + ore.name()));
		}
	}
}