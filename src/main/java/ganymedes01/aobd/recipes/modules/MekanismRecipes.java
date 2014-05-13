package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.ore.Ore;

public class MekanismRecipes {

	public static void init() {
		for (Ore ore : Ore.ores)
			if (ore.shouldMeka()) {
				//				for (ItemStack ore : OreDictionary.getOres("ore" + metal.name()))
				//					RecipeHelper.addPurificationChamberRecipe(ore, DustsItem.getItem("clump" + metal.name(), 3));
				//
				//				RecipeHelper.addCrusherRecipe(DustsItem.getItem("clump" + metal.name()), DustsItem.getItem("dustDirty" + metal.name()));
				//				if (AOBD.enableIC2)
				//					Recipes.macerator.addRecipe(new RecipeInputOreDict("clump" + metal.name()), null, DustsItem.getItem("dustDirty" + metal.name()));
				//				RecipeHelper.addEnrichmentChamberRecipe(DustsItem.getItem("dustDirty" + metal.name()), getOreDictItem("dust" + metal.name(), 1));
			}
	}
}