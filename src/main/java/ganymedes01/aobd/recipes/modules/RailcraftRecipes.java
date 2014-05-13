package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;

import java.lang.reflect.Method;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RailcraftRecipes extends RecipesModule {

	public static void init() {
		try {
			Class<?> RailcraftCraftingManager = Class.forName("mods.railcraft.api.crafting.RailcraftCraftingManager");
			Object rockCrusher = RailcraftCraftingManager.getDeclaredField("rockCrusher").get(null);
			Method createNewRecipe = rockCrusher.getClass().getMethod("createNewRecipe", ItemStack.class, boolean.class, boolean.class);

			for (Ore ore : Ore.ores)
				if (ore.shouldRC())
					for (ItemStack stack : OreDictionary.getOres("ore" + ore.name())) {
						Object recipe = createNewRecipe.invoke(rockCrusher, stack, true, false);
						recipe.getClass().getMethod("addOutput", ItemStack.class, float.class).invoke(recipe, getOreDictItem("crushed" + ore.name(), 2), 1.0F);
					}
		} catch (Exception e) {
		}
	}
}