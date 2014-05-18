package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeInputOreDict;
import ic2.api.recipe.Recipes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class IC2Recipes extends RecipesModule {

	public static void init() {
		ItemStack stoneDust = getICItem("stoneDust");

		for (Ore ore : Ore.ores)
			if (ore.shouldIC2()) {
				String name = ore.name();
				try {
					Recipes.macerator.addRecipe(new RecipeInputOreDict("ore" + name), null, getOreDictItem("crushed" + name, 2));
					Recipes.macerator.addRecipe(new RecipeInputOreDict("ingot" + name), null, getOreDictItem("dust" + name));

					addCentrifugeRecipe(new RecipeInputOreDict("crushed" + name), (int) ore.energy(1500), getOreDictItem("dust" + name), getOreDictItem("dustTiny" + ore.extra()), stoneDust.copy());
					addOreWashingRecipe(new RecipeInputOreDict("crushed" + name), getOreDictItem("crushedPurified" + name), getOreDictItem("dustTiny" + name, 2), stoneDust.copy());

					addCentrifugeRecipe(new RecipeInputOreDict("crushedPurified" + name), (int) ore.energy(1500), getOreDictItem("dust" + name, 1), getOreDictItem("dustTiny" + ore.extra()));

					GameRegistry.addRecipe(new ShapedOreRecipe(getOreDictItem("dust" + name), "xxx", "xxx", "xxx", 'x', "dustTiny" + name));
					GameRegistry.addSmelting(getOreDictItem("crushed" + name), getOreDictItem("ingot" + name), 0.2F);
					GameRegistry.addSmelting(getOreDictItem("crushedPurified" + name), getOreDictItem("ingot" + name), 0.2F);

				} catch (Exception e) {
					continue;
				}
			}
	}

	private static void addCentrifugeRecipe(IRecipeInput input, int minHeat, ItemStack... output) {
		NBTTagCompound metadata = new NBTTagCompound();
		metadata.setInteger("minHeat", minHeat);

		Recipes.centrifuge.addRecipe(input, metadata, output);
	}

	private static void addOreWashingRecipe(IRecipeInput input, ItemStack... output) {
		NBTTagCompound metadata = new NBTTagCompound();
		metadata.setInteger("amount", 1000);

		Recipes.oreWashing.addRecipe(input, metadata, output);
	}

	private static ItemStack getICItem(String name) {
		try {
			Class<?> itemsClass = Class.forName("ic2.core.Ic2Items");
			return (ItemStack) itemsClass.getField(name).get(null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}