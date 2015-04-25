package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;
import mods.eln.Eln;
import mods.eln.misc.Recipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ElectricalAge extends RecipesModule {

	public ElectricalAge() {
		super(CompatType.ELECTRICAL_AGE, "iron", "gold", "copper", "lead", "tungsten");
	}

	@Override
	public void initOre(Ore ore) {
		for (ItemStack oreBlock : OreDictionary.getOres("ore" + ore.name()))
			Eln.instance.maceratorRecipes.addRecipe(new Recipe(oreBlock.copy(), new ItemStack[] { getOreStack("dust", ore, 2) }, ore.energy(4000)));
		for (ItemStack ingot : OreDictionary.getOres("ingot" + ore.name()))
			Eln.instance.maceratorRecipes.addRecipe(new Recipe(ingot.copy(), new ItemStack[] { getOreStack("dust", ore) }, ore.energy(2000)));
	}
}