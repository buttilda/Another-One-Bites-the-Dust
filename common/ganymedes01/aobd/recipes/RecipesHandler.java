package ganymedes01.aobd.recipes;

import ganymedes01.aobd.AOBD;
import ganymedes01.aobd.items.DustsItem;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeInputOreDict;
import ic2.api.recipe.Recipes;
import mekanism.api.RecipeHelper;
import mods.railcraft.api.crafting.IRockCrusherRecipe;
import mods.railcraft.api.crafting.RailcraftCraftingManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
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
	}

	private static void MekanismRecipes() {
		for (String metal : new String[] { "Ardite", "Cobalt", "Aluminium" }) {
			for (ItemStack ore : OreDictionary.getOres("ore" + metal))
				RecipeHelper.addPurificationChamberRecipe(ore, DustsItem.getItem("clump" + metal, 3));

			RecipeHelper.addCrusherRecipe(DustsItem.getItem("clump" + metal), DustsItem.getItem("dustDirty" + metal));
			if (AOBD.enableIC2)
				Recipes.macerator.addRecipe(new RecipeInputOreDict("clump" + metal), null, DustsItem.getItem("dustDirty" + metal));
			RecipeHelper.addEnrichmentChamberRecipe(DustsItem.getItem("dustDirty" + metal), getOreDictItem("dust" + metal, 1));
		}
	}

	private static void RailcraftRecipes() {
		for (ItemStack cobaltOre : OreDictionary.getOres("oreCobalt")) {
			IRockCrusherRecipe recipeCobalt = RailcraftCraftingManager.rockCrusher.createNewRecipe(cobaltOre, true, false);
			recipeCobalt.addOutput(DustsItem.getItem("crushedCobalt", 2), 1.0F);
		}

		for (ItemStack arditeOre : OreDictionary.getOres("oreArdite")) {
			IRockCrusherRecipe recipeArdite = RailcraftCraftingManager.rockCrusher.createNewRecipe(arditeOre, true, false);
			recipeArdite.addOutput(DustsItem.getItem("crushedArdite", 2), 1.0F);
		}

		for (ItemStack aluminiumOre : OreDictionary.getOres("oreAluminium")) {
			IRockCrusherRecipe recipeArdite = RailcraftCraftingManager.rockCrusher.createNewRecipe(aluminiumOre, true, false);
			recipeArdite.addOutput(DustsItem.getItem("crushedAluminium", 2), 1.0F);
		}
	}

	private static void IC2Recipes() {
		Recipes.macerator.addRecipe(new RecipeInputOreDict("oreCobalt"), null, DustsItem.getItem("crushedCobalt", 2));
		Recipes.macerator.addRecipe(new RecipeInputOreDict("oreArdite"), null, DustsItem.getItem("crushedArdite", 2));
		Recipes.macerator.addRecipe(new RecipeInputOreDict("oreAluminium"), null, DustsItem.getItem("crushedAluminium", 2));

		Recipes.macerator.addRecipe(new RecipeInputOreDict("ingotCobalt"), null, getOreDictItem("dustCobalt", 1));
		Recipes.macerator.addRecipe(new RecipeInputOreDict("ingotArdite"), null, getOreDictItem("dustArdite", 1));
		Recipes.macerator.addRecipe(new RecipeInputOreDict("ingotAluminium"), null, getOreDictItem("dustAluminium", 1));

		addCentrifugeRecipe(new RecipeInputOreDict("crushedCobalt"), 5000, getOreDictItem("dustCobalt", 1), getICItem("smallIronDust"));
		addCentrifugeRecipe(new RecipeInputOreDict("crushedArdite"), 5000, getOreDictItem("dustArdite", 1), getICItem("smallGoldDust"));
		addCentrifugeRecipe(new RecipeInputOreDict("crushedAluminium"), 1500, getOreDictItem("dustAluminium", 1), getICItem("smallIronDust"), getICItem("stoneDust"));

		addCentrifugeRecipe(new RecipeInputOreDict("crushedPurifiedCobalt"), 3000, getOreDictItem("dustCobalt", 1), DustsItem.getItem("dustTinyCobalt"));
		addCentrifugeRecipe(new RecipeInputOreDict("crushedPurifiedArdite"), 3000, getOreDictItem("dustArdite", 1), DustsItem.getItem("dustTinyArdite"));
		addCentrifugeRecipe(new RecipeInputOreDict("crushedPurifiedAluminium"), 1500, getOreDictItem("dustAluminium", 1), getICItem("smallIronDust"));

		addOreWashingRecipe(new RecipeInputOreDict("crushedCobalt"), DustsItem.getItem("crushedPurifiedCobalt"), DustsItem.getItem("dustTinyCobalt", 2));
		addOreWashingRecipe(new RecipeInputOreDict("crushedArdite"), DustsItem.getItem("crushedPurifiedArdite"), DustsItem.getItem("dustTinyArdite", 2));
		addOreWashingRecipe(new RecipeInputOreDict("crushedAluminium"), DustsItem.getItem("crushedPurifiedAluminium"), DustsItem.getItem("dustTinyAluminium", 2));
	}

	public static void addCentrifugeRecipe(IRecipeInput input, int minHeat, ItemStack... output) {
		NBTTagCompound metadata = new NBTTagCompound();
		metadata.setInteger("minHeat", minHeat);

		Recipes.centrifuge.addRecipe(input, metadata, output);
	}

	public static void addOreWashingRecipe(IRecipeInput input, ItemStack... output) {
		NBTTagCompound metadata = new NBTTagCompound();
		metadata.setInteger("amount", 1000);

		Recipes.oreWashing.addRecipe(input, metadata, output);
	}

	private static void craftingRecipes() {
		registerOre("crushedArdite");
		registerOre("crushedCobalt");
		registerOre("crushedPurifiedArdite");
		registerOre("crushedPurifiedCobalt");
		registerOre("dustTinyArdite");
		registerOre("dustTinyCobalt");
		registerOre("crushedAluminium");
		registerOre("crushedPurifiedAluminium");
		registerOre("dustTinyAluminium");
		registerOre("clumpArdite");
		registerOre("clumpCobalt");
		registerOre("clumpAluminium");
		registerOre("dustDirtyArdite");
		registerOre("dustDirtyCobalt");
		registerOre("dustDirtyAluminium");

		GameRegistry.addRecipe(new ShapedOreRecipe(getOreDictItem("dustCobalt", 1), "xxx", "xxx", "xxx", 'x', "dustTinyCobalt"));
		GameRegistry.addRecipe(new ShapedOreRecipe(getOreDictItem("dustArdite", 1), "xxx", "xxx", "xxx", 'x', "dustTinyArdite"));
		GameRegistry.addRecipe(new ShapedOreRecipe(getOreDictItem("dustAluminium", 1), "xxx", "xxx", "xxx", 'x', "dustTinyAluminium"));

		FurnaceRecipes.smelting().addSmelting(AOBD.dusts.itemID, 5, getOreDictItem("ingotCobalt", 1), 0.2F);
		FurnaceRecipes.smelting().addSmelting(AOBD.dusts.itemID, 6, getOreDictItem("ingotArdite", 1), 0.2F);
		FurnaceRecipes.smelting().addSmelting(AOBD.dusts.itemID, 7, getOreDictItem("ingotArdite", 1), 0.2F);
		FurnaceRecipes.smelting().addSmelting(AOBD.dusts.itemID, 8, getOreDictItem("ingotArdite", 1), 0.2F);
		FurnaceRecipes.smelting().addSmelting(AOBD.dusts.itemID, 11, getOreDictItem("ingotAluminium", 1), 0.2F);
		FurnaceRecipes.smelting().addSmelting(AOBD.dusts.itemID, 12, getOreDictItem("ingotAluminium", 1), 0.2F);
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
		ItemStack stack = OreDictionary.getOres(name).get(0).copy();
		stack.stackSize = size;

		return stack;
	}
}