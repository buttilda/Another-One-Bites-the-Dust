package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class EnderIORecipes extends RecipesModule {

	public static final String[] blacklist = { "iron", "gold", "copper", "tin", "lead", "silver", "nickel" };

	public static void init() {
		label: for (Ore ore : Ore.ores)
			if (ore.shouldEnderIO()) {
				for (String bEntry : blacklist)
					if (ore.name().equalsIgnoreCase(bEntry))
						continue label;

				addSAGMillRecipe("ore" + ore.name(), (float) ore.energy(360.0), new ItemStack[] { getOreDictItem("dust" + ore.name(), 2), getOreDictItem("dust" + ore.extra()), new ItemStack(Blocks.cobblestone) }, new float[] { 1.0F, 0.1F, 0.15F });
			}
	}

	@SuppressWarnings("all")
	private static void addSAGMillRecipe(String input, float energy, ItemStack[] outputs, float[] chance) {
		try {
			Object SAGMill = Class.forName("crazypants.enderio.machine.crusher.CrusherRecipeManager").getMethod("getInstance").invoke(null);
			Method addRecipe = SAGMill.getClass().getMethod("addRecipe", Class.forName("crazypants.enderio.machine.recipe.Recipe"));

			Class recipeInput = Class.forName("crazypants.enderio.machine.recipe.RecipeInput");
			Class recipeOuput = Class.forName("crazypants.enderio.machine.recipe.RecipeOutput");
			Constructor oreDictInput = Class.forName("crazypants.enderio.machine.recipe.OreDictionaryRecipeInput").getConstructor(ItemStack.class, int.class, int.class);

			Object[] output = (Object[]) Array.newInstance(Class.forName("crazypants.enderio.machine.recipe.RecipeOutput"), outputs.length);
			Constructor recipe = Class.forName("crazypants.enderio.machine.recipe.Recipe").getConstructor(recipeInput, float.class, output.getClass());

			for (int i = 0; i < outputs.length; i++)
				output[i] = recipeOuput.getConstructor(ItemStack.class, float.class).newInstance(outputs[i], chance[i]);

			addRecipe.invoke(SAGMill, recipe.newInstance(oreDictInput.newInstance(getOreDictItem(input, 1), OreDictionary.getOreID(input), -1), energy, output));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}