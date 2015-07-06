package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;
import net.minecraft.item.ItemStack;
import blusunrize.immersiveengineering.api.CrusherRecipe;

public class ImmersiveEngineering extends RecipesModule {

	public ImmersiveEngineering() {
		super(CompatType.IMMERSIVE_ENGINEERING, "iron", "gold", "copper", "lead", "silver", "nickel", "aluminum");
	}

	@Override
	public void initOre(Ore ore) {
		CrusherRecipe.addRecipe(getOreStack("dust", ore), "ingot" + ore.name(), 2400);
		CrusherRecipe recipe = CrusherRecipe.addRecipe(getOreStack("dust", ore, 2), "ore" + ore.name(), 4000);
		try {
			ItemStack extra = getOreStack("dust" + ore.extra());
			recipe.addSecondaryOutput(extra, 0.1F);
		} catch (IllegalArgumentException e) {
		}
	}
}