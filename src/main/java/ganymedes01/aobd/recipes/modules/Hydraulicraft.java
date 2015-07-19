package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;
import k4unl.minecraft.Hydraulicraft.api.HCApi;
import k4unl.minecraft.Hydraulicraft.api.recipes.FluidShapelessOreRecipe;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class Hydraulicraft extends RecipesModule {

	public Hydraulicraft() {
		super(CompatType.HYDRAULICRAFT, "iron", "gold", "copper", "lead", "tin", "silver", "nickel", "ardite", "cobalt", "fzdarkiron");
	}

	@Override
	public void initOre(Ore ore) {
		addCrushingRecipe("ore" + ore.name(), getOreStack("chunk", ore, 2), 1.0F);
		addCrushingRecipe("ingot" + ore.name(), getOreStack("dust", ore), 0.5F);
		addWashingRecipe("chunk" + ore.name(), getOreStack("dust", ore));
		GameRegistry.addSmelting(getOreStack("chunk", ore), getOreStack("ingot", ore), (float) ore.energy(0.5F));
	}

	private void addCrushingRecipe(String input, ItemStack output, float pressureRatio) {
		HCApi.getInstance().getRecipeHandler().addCrushingRecipe(new FluidShapelessOreRecipe(output, input).setPressure(pressureRatio).setCraftingTime(200));
	}

	private void addWashingRecipe(String input, ItemStack output) {
		HCApi.getInstance().getRecipeHandler().addWasherRecipe(new FluidShapelessOreRecipe(output, input));
	}
}