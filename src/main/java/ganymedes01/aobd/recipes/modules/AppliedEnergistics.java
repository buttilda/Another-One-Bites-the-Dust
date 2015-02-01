package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import appeng.api.AEApi;
import appeng.core.AEConfig;

public class AppliedEnergistics extends RecipesModule {

	public AppliedEnergistics() {
		super(CompatType.APPLIED_ENERGISTICS, "iron", "gold");
	}

	@Override
	public void initOre(Ore ore) {
		ItemStack dust = getOreStack("dust", ore);
		float doubleChance = (float) (AEConfig.instance.oreDoublePercentage / 100.0D);

		for (ItemStack stack : OreDictionary.getOres("ore" + ore.name()))
			AEApi.instance().registries().grinder().addRecipe(stack, dust, dust, doubleChance, (int) ore.energy(8));
		for (ItemStack stack : OreDictionary.getOres("ingot" + ore.name()))
			AEApi.instance().registries().grinder().addRecipe(stack, dust, (int) ore.energy(4));
	}
}