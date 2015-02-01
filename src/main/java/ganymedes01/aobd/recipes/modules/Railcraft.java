package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;
import mods.railcraft.api.crafting.IRockCrusherRecipe;
import mods.railcraft.api.crafting.RailcraftCraftingManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class Railcraft extends RecipesModule {

	public Railcraft() {
		super(CompatType.RAILCRAFT);
	}

	@Override
	public void initOre(Ore ore) {
		for (ItemStack stack : OreDictionary.getOres("ore" + ore.name()))
			addRecipe(stack, ore);
	}

	private void addRecipe(ItemStack input, Ore ore) {
		for (IRockCrusherRecipe rec : RailcraftCraftingManager.rockCrusher.getRecipes())
			if (rec != null && areStacksTheSame(input, rec.getInput()))
				return;

		IRockCrusherRecipe recipe = RailcraftCraftingManager.rockCrusher.createNewRecipe(input, true, false);
		recipe.addOutput(getOreStack("crushed", ore, 2), 1.0F);

		// Make sure we don't register the smelting recipe twice
		addSmeltingNoDupes(getOreStack("crushed", ore), getOreStack("ingot", ore), 0.2F);
	}
}