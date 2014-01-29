package ganymedes01.aobd.recipes;

import ganymedes01.aobd.AOBD;
import ganymedes01.aobd.items.DustsItem;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeInputOreDict;
import ic2.api.recipe.Recipes;

import java.lang.reflect.Method;

import mekanism.api.RecipeHelper;
import mods.railcraft.api.crafting.IRockCrusherRecipe;
import mods.railcraft.api.crafting.RailcraftCraftingManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipesHandler {

	private static int MULT;

	private static ItemStack ingotArdite;
	private static ItemStack ingotCobalt;
	private static ItemStack ingotManyullyn;
	private static ItemStack ingotAluminium;
	private static ItemStack ingotAluminiumBrass;

	private static ItemStack dustIron;
	private static ItemStack dustGold;
	private static ItemStack crystalCinnabar;

	private static ItemStack dustTinyIron;
	private static ItemStack dustTinyGold;
	private static ItemStack stoneDust;

	public static void preInit() {
		ingotCobalt = OreDictionary.getOres("ingotCobalt").get(0);
		ingotArdite = OreDictionary.getOres("ingotArdite").get(0);
		ingotManyullyn = OreDictionary.getOres("ingotManyullyn").get(0);
		ingotAluminium = OreDictionary.getOres("ingotAluminium").get(0);
		ingotAluminiumBrass = OreDictionary.getOres("ingotAluminumBrass").get(0);

		if (AOBD.enableTE3) {
			dustIron = getTEItem("dustIron");
			dustGold = getTEItem("dustGold");
			crystalCinnabar = OreDictionary.getOres("crystalCinnabar").get(0);
		}

		if (AOBD.enableIC2) {
			dustTinyIron = getICItem("smallIronDust");
			dustTinyGold = getICItem("smallGoldDust");
			stoneDust = getICItem("stoneDust");
		}
	}

	public static void init() {
		craftingRecipes();
		TiCRecipes();
		if (AOBD.enableTE3)
			TE3Recipes();
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
			RecipeHelper.addEnrichmentChamberRecipe(DustsItem.getItem("dustDirty" + metal), DustsItem.getItem("dust" + metal));
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

		Recipes.macerator.addRecipe(new RecipeInputOreDict("ingotCobalt"), null, DustsItem.getItem("dustCobalt"));
		Recipes.macerator.addRecipe(new RecipeInputOreDict("ingotArdite"), null, DustsItem.getItem("dustArdite"));
		Recipes.macerator.addRecipe(new RecipeInputOreDict("ingotAluminium"), null, DustsItem.getItem("dustAluminium"));

		addCentrifugeRecipe(new RecipeInputOreDict("crushedCobalt"), 5000, DustsItem.getItem("dustCobalt"), dustTinyIron);
		addCentrifugeRecipe(new RecipeInputOreDict("crushedArdite"), 5000, DustsItem.getItem("dustArdite"), dustTinyGold);
		addCentrifugeRecipe(new RecipeInputOreDict("crushedAluminium"), 1500, DustsItem.getItem("dustAluminium"), dustTinyIron, stoneDust);

		addCentrifugeRecipe(new RecipeInputOreDict("crushedPurifiedCobalt"), 3000, DustsItem.getItem("dustCobalt"), DustsItem.getItem("dustTinyCobalt"));
		addCentrifugeRecipe(new RecipeInputOreDict("crushedPurifiedArdite"), 3000, DustsItem.getItem("dustArdite"), DustsItem.getItem("dustTinyArdite"));
		addCentrifugeRecipe(new RecipeInputOreDict("crushedPurifiedAluminium"), 1500, DustsItem.getItem("dustAluminium"), dustTinyIron);

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

	private static void TiCRecipes() {
		try {
			Class tContruct = Class.forName("tconstruct.TConstruct");
			int ingotValue = tContruct.getDeclaredField("ingotLiquidValue").getInt(null);

			Class smeltery = Class.forName("tconstruct.library.crafting.Smeltery");
			Method addMelting = smeltery.getMethod("addMelting", ItemStack.class, int.class, FluidStack.class);
			addMelting.invoke(null, DustsItem.getItem("dustCobalt"), 600, new FluidStack(FluidRegistry.getFluid("cobalt.molten"), ingotValue));
			addMelting.invoke(null, DustsItem.getItem("dustArdite"), 600, new FluidStack(FluidRegistry.getFluid("ardite.molten"), ingotValue));
			addMelting.invoke(null, DustsItem.getItem("dustManyullyn"), 600, new FluidStack(FluidRegistry.getFluid("manyullyn.molten"), ingotValue));
			addMelting.invoke(null, DustsItem.getItem("dustAluminium"), 600, new FluidStack(FluidRegistry.getFluid("aluminum.molten"), ingotValue));
			addMelting.invoke(null, DustsItem.getItem("dustAluminiumBrass"), 600, new FluidStack(FluidRegistry.getFluid("aluminumbrass.molten"), ingotValue));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void craftingRecipes() {
		registerOre("dustCobalt");
		registerOre("dustArdite");
		registerOre("dustManyullyn");
		registerOre("dustAluminium");
		registerOre("dustAluminum");
		registerOre("dustAluminiumBrass");
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

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(AOBD.dusts, 1, 2), "dustArdite", "dustCobalt"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(AOBD.dusts, 3, 3), "dustCopper", "dustCopper", "dustCopper", "dustAluminium"));

		GameRegistry.addRecipe(DustsItem.getItem("dustCobalt"), "xxx", "xxx", "xxx", 'x', DustsItem.getItem("dustTinyCobalt"));
		GameRegistry.addRecipe(DustsItem.getItem("dustArdite"), "xxx", "xxx", "xxx", 'x', DustsItem.getItem("dustTinyArdite"));
		GameRegistry.addRecipe(DustsItem.getItem("dustAluminium"), "xxx", "xxx", "xxx", 'x', DustsItem.getItem("dustTinyAluminium"));

		ItemStack ardite = ingotArdite.copy();
		ardite.stackSize = 1;
		ItemStack cobalt = ingotCobalt.copy();
		cobalt.stackSize = 1;
		ItemStack manyullyn = ingotManyullyn.copy();
		manyullyn.stackSize = 1;
		ItemStack aluminium = ingotAluminium.copy();
		aluminium.stackSize = 1;
		ItemStack aluminiumBrass = ingotAluminiumBrass.copy();
		aluminiumBrass.stackSize = 1;
		FurnaceRecipes.smelting().addSmelting(AOBD.dusts.itemID, 0, ardite, 0.0F);
		FurnaceRecipes.smelting().addSmelting(AOBD.dusts.itemID, 1, cobalt, 0.0F);
		FurnaceRecipes.smelting().addSmelting(AOBD.dusts.itemID, 2, manyullyn, 0.0F);
		FurnaceRecipes.smelting().addSmelting(AOBD.dusts.itemID, 4, aluminium, 0.0F);
		FurnaceRecipes.smelting().addSmelting(AOBD.dusts.itemID, 3, aluminiumBrass, 0.0F);
		FurnaceRecipes.smelting().addSmelting(AOBD.dusts.itemID, 5, cobalt, 0.0F);
		FurnaceRecipes.smelting().addSmelting(AOBD.dusts.itemID, 6, ardite, 0.0F);
		FurnaceRecipes.smelting().addSmelting(AOBD.dusts.itemID, 7, cobalt, 0.0F);
		FurnaceRecipes.smelting().addSmelting(AOBD.dusts.itemID, 8, ardite, 0.0F);
		FurnaceRecipes.smelting().addSmelting(AOBD.dusts.itemID, 11, aluminium, 0.0F);
		FurnaceRecipes.smelting().addSmelting(AOBD.dusts.itemID, 12, aluminium, 0.0F);
	}

	private static void registerOre(String name) {
		OreDictionary.registerOre(name, DustsItem.getItem(name));
	}

	private static void TE3Recipes() {
		MULT = 3;

		addPulveriserRecipe(1000, ingotAluminium, DustsItem.getItem("dustAluminium"), null, 0);
		addPulveriserRecipe(1000, ingotCobalt, DustsItem.getItem("dustCobalt"), null, 0);
		addPulveriserRecipe(1000, ingotArdite, DustsItem.getItem("dustArdite"), null, 0);
		for (ItemStack cobaltOre : OreDictionary.getOres("oreCobalt"))
			addPulveriserRecipe(4000 * MULT, cobaltOre, DustsItem.getItem("dustCobalt", 2), dustIron, 10);
		for (ItemStack arditeOre : OreDictionary.getOres("oreArdite"))
			addPulveriserRecipe(4000 * MULT, arditeOre, DustsItem.getItem("dustArdite", 2), dustGold, 10);

		ingotCobalt.stackSize = 3;
		for (ItemStack cobaltOre : OreDictionary.getOres("oreCobalt"))
			addInductionSmelterRecipe(4000 * MULT, cobaltOre, crystalCinnabar, ingotCobalt, new ItemStack(Item.ingotIron), 100);
		ingotArdite.stackSize = 3;
		for (ItemStack arditeOre : OreDictionary.getOres("oreArdite"))
			addInductionSmelterRecipe(4000 * MULT, arditeOre, crystalCinnabar, ingotArdite, new ItemStack(Item.ingotGold), 100);

		ingotArdite.stackSize = 1;
		ingotCobalt.stackSize = 1;
		ingotManyullyn.stackSize = 1;
		addInductionSmelterRecipe(4000, ingotArdite, ingotCobalt, ingotManyullyn, null, 0);
		for (ItemStack ingotCopper : OreDictionary.getOres("ingotCopper")) {
			ItemStack copper = ingotCopper.copy();
			copper.stackSize = 3;
			ItemStack aluminium = ingotAluminium.copy();
			aluminium.stackSize = 1;
			ItemStack aluminiumBtass = ingotAluminiumBrass.copy();
			aluminiumBtass.stackSize = 4;
			addInductionSmelterRecipe(4000, copper, aluminium, aluminiumBtass, null, 0);
		}
	}

	private static void addInductionSmelterRecipe(int energy, ItemStack input1, ItemStack input2, ItemStack output1, ItemStack output2, int chance) {
		NBTTagCompound data = new NBTTagCompound();

		data.setInteger("energy", energy);

		NBTTagCompound input1Compound = new NBTTagCompound();
		input1.writeToNBT(input1Compound);
		data.setCompoundTag("primaryInput", input1Compound);

		NBTTagCompound input2Compound = new NBTTagCompound();
		input2.writeToNBT(input2Compound);
		data.setCompoundTag("secondaryInput", input2Compound);

		NBTTagCompound output1Compound = new NBTTagCompound();
		output1.writeToNBT(output1Compound);
		data.setCompoundTag("primaryOutput", output1Compound);

		if (output2 != null) {
			NBTTagCompound output2Compound = new NBTTagCompound();
			output2.writeToNBT(output2Compound);
			data.setCompoundTag("secondaryOutput", output2Compound);

			data.setInteger("secondaryChance", chance);
		}

		FMLInterModComms.sendMessage("ThermalExpansion", "SmelterRecipe", data);
	}

	private static void addPulveriserRecipe(int energy, ItemStack input, ItemStack output, ItemStack bonus, int chance) {
		NBTTagCompound data = new NBTTagCompound();

		data.setInteger("energy", energy);

		NBTTagCompound inputCompound = new NBTTagCompound();
		input.writeToNBT(inputCompound);
		data.setCompoundTag("input", inputCompound);

		NBTTagCompound outputCompound = new NBTTagCompound();
		output.writeToNBT(outputCompound);
		data.setCompoundTag("primaryOutput", outputCompound);

		if (bonus != null) {
			NBTTagCompound outputCompound2 = new NBTTagCompound();
			bonus.writeToNBT(outputCompound2);
			data.setCompoundTag("secondaryOutput", outputCompound2);

			data.setInteger("secondaryChance", chance);
		}

		FMLInterModComms.sendMessage("ThermalExpansion", "PulverizerRecipe", data);
	}

	private static ItemStack getTEItem(String name) {
		try {
			Class itemsClass = Class.forName("thermalexpansion.item.TEItems");
			return (ItemStack) itemsClass.getDeclaredField(name).get(null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static ItemStack getICItem(String name) {
		try {
			Class<?> itemsClass = Class.forName("ic2.core.Ic2Items");
			return (ItemStack) itemsClass.getField(name).get(null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}