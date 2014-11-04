package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;

import java.util.Set;

import mods.railcraft.api.crafting.IRockCrusherRecipe;
import mods.railcraft.api.crafting.RailcraftCraftingManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;

public class RailcraftModule extends RecipesModule {

	public RailcraftModule() {
		super(CompatType.RAILCRAFT);
	}

	@Override
	public void initOre(Ore ore) {
		for (ItemStack stack : OreDictionary.getOres("ore" + ore.name()))
			addRecipe(stack, ore.name());
	}

	@SuppressWarnings("unchecked")
	private void addRecipe(ItemStack input, String name) {
		for (IRockCrusherRecipe rec : RailcraftCraftingManager.rockCrusher.getRecipes())
			if (rec != null && areStacksTheSame(input, rec.getInput()))
				return;

		IRockCrusherRecipe recipe = RailcraftCraftingManager.rockCrusher.createNewRecipe(input, true, false);
		recipe.addOutput(getOreDictItem("crushed" + name, 2), 1.0F);

		// Make sure we don't register the smelting recipe twice
		ItemStack crushed = getOreDictItem("crushed" + name);
		for (ItemStack stack : (Set<ItemStack>) FurnaceRecipes.smelting().getSmeltingList().keySet())
			if (areStacksTheSame(stack, crushed))
				return;
		GameRegistry.addSmelting(crushed, getOreDictItem("ingot" + name), 0.2F);
	}

	private boolean areStacksTheSame(ItemStack stack1, ItemStack stack2) {
		return stack1.getItem() == stack2.getItem() && stack1.getItemDamage() == stack2.getItemDamage();
	}
}