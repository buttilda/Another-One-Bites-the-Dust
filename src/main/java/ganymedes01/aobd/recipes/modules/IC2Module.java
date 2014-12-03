package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeInputOreDict;
import ic2.api.recipe.Recipes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class IC2Module extends RecipesModule {

	public IC2Module() {
		super(CompatType.IC2, "iron", "gold", "copper", "tin", "silver", "lead");
	}

	@Override
	public void initOre(Ore ore) {
		ItemStack stoneDust = getICItem("stoneDust");

		String name = ore.name();
		try {
			Recipes.macerator.addRecipe(new RecipeInputOreDict("ore" + name), null, getOreStack("crushed", ore, 2));
			Recipes.macerator.addRecipe(new RecipeInputOreDict("ingot" + name), null, getOreStack("dust", ore));

			addCentrifugeRecipe(new RecipeInputOreDict("crushed" + name), (int) ore.energy(1500), getOreStack("dust", ore), getOreStackExtra("dustTiny", ore), stoneDust.copy());
			addOreWashingRecipe(new RecipeInputOreDict("crushed" + name), getOreStack("crushedPurified", ore), getOreStack("dustTiny", ore, 2), stoneDust.copy());

			addCentrifugeRecipe(new RecipeInputOreDict("crushedPurified" + name), (int) ore.energy(1500), getOreStack("dust", ore, 1), getOreStackExtra("dustTiny", ore));

			GameRegistry.addRecipe(new ShapedOreRecipe(getOreStack("dust", ore), "xxx", "xxx", "xxx", 'x', "dustTiny" + name));
			addSmeltingNoDupes(getOreStack("crushed", ore), getOreStack("ingot", ore), 0.2F);
			GameRegistry.addSmelting(getOreStack("crushedPurified", ore), getOreStack("ingot", ore), 0.2F);

			if (CompatType.MEKANISM.isEnabled() && ore.isCompatEnabled(CompatType.MEKANISM))
				Recipes.macerator.addRecipe(new RecipeInputOreDict("clump" + name), null, getOreStack("dustDirty", ore));

		} catch (Exception e) {
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