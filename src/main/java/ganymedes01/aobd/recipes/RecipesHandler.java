package ganymedes01.aobd.recipes;

import ganymedes01.aobd.AOBD;
import ganymedes01.aobd.items.DustsItem;
import ganymedes01.aobd.lib.Metals;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeInputOreDict;
import ic2.api.recipe.Recipes;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import mods.railcraft.api.crafting.IRockCrusherRecipe;
import mods.railcraft.api.crafting.RailcraftCraftingManager;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipesHandler {

	public static void init() {
		craftingRecipes();
		if (AOBD.enableIC2)
			IC2Recipes();
		if (AOBD.enableRailcraft)
			RailcraftRecipes();
		if (AOBD.enableMekanism)
			MekanismRecipes();
		if (AOBD.enableEnderIO)
			EnderIORecipes();
	}

	private static void EnderIORecipes() {
		for (Metals metal : Metals.values())
			if (metal.shouldUse())
				addSAGMillRecipe("ore" + metal.name(), (float) metal.getEnergy(360.0F), new ItemStack[] { getOreDictItem("dust" + metal.name(), 2), getOreDictItem("dust" + metal.extra(), 1), new ItemStack(metal.substract()) }, new float[] { 1.0F, 0.2F, 0.15F });
	}

	private static void MekanismRecipes() {
//		for (Metals metal : Metals.values())
//			if (metal.shouldUse()) {
//				for (ItemStack ore : OreDictionary.getOres("ore" + metal.name()))
//					RecipeHelper.addPurificationChamberRecipe(ore, DustsItem.getItem("clump" + metal.name(), 3));
//
//				RecipeHelper.addCrusherRecipe(DustsItem.getItem("clump" + metal.name()), DustsItem.getItem("dustDirty" + metal.name()));
//				if (AOBD.enableIC2)
//					Recipes.macerator.addRecipe(new RecipeInputOreDict("clump" + metal.name()), null, DustsItem.getItem("dustDirty" + metal.name()));
//				RecipeHelper.addEnrichmentChamberRecipe(DustsItem.getItem("dustDirty" + metal.name()), getOreDictItem("dust" + metal.name(), 1));
//			}
	}

	private static void RailcraftRecipes() {
		for (Metals metal : Metals.values())
			if (metal.shouldUse())
				for (ItemStack cobaltOre : OreDictionary.getOres("ore" + metal.name())) {
					IRockCrusherRecipe recipeCobalt = RailcraftCraftingManager.rockCrusher.createNewRecipe(cobaltOre, true, false);
					recipeCobalt.addOutput(DustsItem.getItem("crushed" + metal.name(), 2), 1.0F);
				}
	}

	private static void IC2Recipes() {
		for (Metals metal : Metals.values())
			if (metal.shouldUse()) {
				Recipes.macerator.addRecipe(new RecipeInputOreDict("ore" + metal.name()), null, DustsItem.getItem("crushed" + metal.name(), 2));
				Recipes.macerator.addRecipe(new RecipeInputOreDict("ingot" + metal.name()), null, getOreDictItem("dust" + metal.name(), 1));

				if (metal.substract() == Blocks.cobblestone) {
					addCentrifugeRecipe(new RecipeInputOreDict("crushed" + metal.name()), (int) metal.getEnergy(1500), getOreDictItem("dust" + metal.name(), 1), getOreDictItem("dustTiny" + metal.extra(), 1), getICItem("stoneDust"));
					addOreWashingRecipe(new RecipeInputOreDict("crushed" + metal.name()), DustsItem.getItem("crushedPurified" + metal.name()), DustsItem.getItem("dustTiny" + metal.name(), 2), getICItem("stoneDust"));
				} else {
					addCentrifugeRecipe(new RecipeInputOreDict("crushed" + metal.name()), (int) metal.getEnergy(1500), getOreDictItem("dust" + metal.name(), 1), getOreDictItem("dustTiny" + metal.extra(), 1));
					addOreWashingRecipe(new RecipeInputOreDict("crushed" + metal.name()), DustsItem.getItem("crushedPurified" + metal.name()), DustsItem.getItem("dustTiny" + metal.name(), 2));
				}

				addCentrifugeRecipe(new RecipeInputOreDict("crushedPurified" + metal.name()), (int) metal.getEnergy(1500), getOreDictItem("dust" + metal.name(), 1), getOreDictItem("dustTiny" + metal.extra(), 1));
			}
	}

	private static void craftingRecipes() {
		registerOre("dustCobalt");
		registerOre("dustArdite");
		registerOre("dustAluminum");

		if (Metals.FzDarkIron.shouldUse())
			registerOre("dustFzDarkIron");

		for (Metals metal : Metals.values())
			if (metal.shouldUse()) {
				registerOre("crushed" + metal.name());
				registerOre("crushedPurified" + metal.name());
				registerOre("dustTiny" + metal.name());
				registerOre("clump" + metal.name());
				registerOre("dustDirty" + metal.name());

				GameRegistry.addRecipe(new ShapedOreRecipe(getOreDictItem("dust" + metal.name(), 1), "xxx", "xxx", "xxx", 'x', "dustTiny" + metal.name()));
				addSmelting(getOreDictItem("crushed" + metal.name(), 1), getOreDictItem("ingot" + metal.name(), 1), 0.2F);
				addSmelting(getOreDictItem("crushedPurified" + metal.name(), 1), getOreDictItem("ingot" + metal.name(), 1), 0.2F);
			}

		if (AOBD.enableIC2 && AOBD.enableTE3) {
			registerOre("dustTinyPlatinum");
			GameRegistry.addRecipe(new ShapedOreRecipe(getOreDictItem("dustPlatinum", 1), "xxx", "xxx", "xxx", 'x', "dustTinyPlatinum"));
		}
	}

	private static void addSmelting(ItemStack input, ItemStack output, float xp) {
		GameRegistry.addSmelting(input, output, xp);
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

	private static void registerOre(String name) {
		OreDictionary.registerOre(name, DustsItem.getItem(name));
	}

	private static ItemStack getICItem(String name) {
		try {
			Class<?> itemsClass = Class.forName("ic2.core.Ic2Items");
			return (ItemStack) itemsClass.getField(name).get(null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static ItemStack getOreDictItem(String name, int size) {
		while (OreDictionary.getOres(name).size() <= 0)
			System.out.println(name);
		ItemStack stack = OreDictionary.getOres(name).get(0).copy();
		stack.stackSize = size;

		return stack;
	}

	@SuppressWarnings("all")
	private static void addSAGMillRecipe(String input, float energy, ItemStack[] outputs, float[] chance) {
		try {
			Object SAGMill = Class.forName("crazypants.enderio.machine.crusher.CrusherRecipeManager").getMethod("getInstance").invoke(null);
			Method addRecipe = SAGMill.getClass().getMethod("addRecipe", Class.forName("crazypants.enderio.machine.recipe.Recipe"));

			Class recipeInput = Class.forName("crazypants.enderio.machine.recipe.RecipeInput");
			Class recipeOuput = Class.forName("crazypants.enderio.machine.recipe.RecipeOutput");
			Constructor oreDictInput = Class.forName("crazypants.enderio.machine.recipe.OreDictionaryRecipeInput").getConstructor(ItemStack.class, int.class);

			Object[] output = (Object[]) Array.newInstance(Class.forName("crazypants.enderio.machine.recipe.RecipeOutput"), outputs.length);
			Constructor recipe = Class.forName("crazypants.enderio.machine.recipe.Recipe").getConstructor(recipeInput, float.class, output.getClass());

			for (int i = 0; i < outputs.length; i++)
				output[i] = recipeOuput.getConstructor(ItemStack.class, float.class).newInstance(outputs[i], chance[i]);

			addRecipe.invoke(SAGMill, recipe.newInstance(oreDictInput.newInstance(getOreDictItem(input, 1), OreDictionary.getOreID(input)), energy, output));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}