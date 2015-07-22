package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeCasting.RecipeBlockCasting;
import mariculture.api.core.RecipeCasting.RecipeIngotCasting;
import mariculture.api.core.RecipeCasting.RecipeNuggetCasting;
import mariculture.api.core.RecipeSmelter;
import mariculture.core.lib.MetalRates;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class Mariculture extends RecipesModule {

	public Mariculture() {
		super(CompatType.MARICULTURE, "gold", "iron", "copper", "aluminium", "aluminum", "rutile", "titanium", "magnesium");
	}

	@Override
	public void initOre(Ore ore) {
		Fluid fluid = getFluid(ore);

		MaricultureHandlers.crucible.addRecipe(new RecipeSmelter(getOreStack("ingot", ore), 500 + (int) ore.energy(600), new FluidStack(fluid, MetalRates.INGOT), null, 0));
		MaricultureHandlers.crucible.addRecipe(new RecipeSmelter(getOreStack("nugget", ore), 500 + (int) ore.energy(600), new FluidStack(fluid, MetalRates.NUGGET), null, 0));
		MaricultureHandlers.crucible.addRecipe(new RecipeSmelter(getOreStack("block", ore), 500 + (int) ore.energy(600), new FluidStack(fluid, MetalRates.INGOT * 9), null, 0));
		MaricultureHandlers.crucible.addRecipe(new RecipeSmelter(getOreStack("ore", ore), 500 + (int) ore.energy(600), new FluidStack(fluid, MetalRates.ORE), getOreStackExtra("dust", ore), 2));

		MaricultureHandlers.casting.addRecipe(new RecipeBlockCasting(new FluidStack(fluid, MetalRates.INGOT * 9), getOreStack("block", ore)));
		MaricultureHandlers.casting.addRecipe(new RecipeIngotCasting(new FluidStack(fluid, MetalRates.INGOT), getOreStack("ingot", ore)));
		MaricultureHandlers.casting.addRecipe(new RecipeNuggetCasting(new FluidStack(fluid, MetalRates.NUGGET), getOreStack("nugget", ore)));
	}
}