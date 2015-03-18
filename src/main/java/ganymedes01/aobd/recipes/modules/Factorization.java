package ganymedes01.aobd.recipes.modules;

import factorization.oreprocessing.TileEntityCrystallizer;
import factorization.oreprocessing.TileEntityGrinder;
import factorization.oreprocessing.TileEntitySlagFurnace;
import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class Factorization extends RecipesModule {

	public Factorization() {
		super(CompatType.FACTORISATION, "tin", "copper", "gold", "iron", "lead", "silver", "cobalt", "ardite", "fzdarkiron");
	}

	@Override
	public void initOre(Ore ore) {
		String name = ore.name();
		ItemStack ingot = getOreStack("ingot", ore);
		ItemStack reduced = getOreStack("reduced", ore);
		ItemStack dirtyGravel = getOreStack("dirtyGravel", ore);
		ItemStack cleanGravel = getOreStack("cleanGravel", ore);
		ItemStack crystalline = getOreStack("crystalline", ore);
		ItemStack aquaRegia = getOreStack("aquaRegia");

		for (ItemStack stack : OreDictionary.getOres("dirtyGravel" + name))
			addSlagFurnaceRecipe(stack, ingot, 1.1F, Blocks.dirt, 0.2F);
		for (ItemStack stack : OreDictionary.getOres("cleanGravel" + name))
			addSlagFurnaceRecipe(stack, reduced, 0.625F, reduced, 0.625F);
		for (ItemStack stack : OreDictionary.getOres("ore" + name)) {
			addSlagFurnaceRecipe(stack, ingot, 1.2F, Blocks.stone, 0.4F);
			addLaceratorRecipe(stack, dirtyGravel, 2.0F);
		}
		for (ItemStack stack : OreDictionary.getOres("reduced" + name))
			addCrystalliserRecipe(stack, crystalline, 1.2F, aquaRegia);

		String dirty = "dirtyGravel" + name;
		GameRegistry.addRecipe(new ShapelessOreRecipe(getOreStack("cleanGravel", ore, 8), "fz.waterBucketLike", dirty, dirty, dirty, dirty, dirty, dirty, dirty, dirty));
		GameRegistry.addRecipe(new ShapelessOreRecipe(cleanGravel, "fz.waterBucketLike", dirty));

		GameRegistry.addSmelting(reduced, ingot, 1.0F);
		GameRegistry.addSmelting(dirtyGravel, ingot, 1.0F);
		GameRegistry.addSmelting(cleanGravel, ingot, 1.0F);
		GameRegistry.addSmelting(crystalline, ingot, 1.0F);
	}

	private static void addSlagFurnaceRecipe(Object input, Object output1, float prob1, Object output2, float prob2) {
		TileEntitySlagFurnace.SlagRecipes.register(input, prob1, output1, prob2, output2);
	}

	private static void addLaceratorRecipe(Object input, ItemStack output, float prob) {
		TileEntityGrinder.addRecipe(input, output, prob);
	}

	private static void addCrystalliserRecipe(ItemStack input, ItemStack output, float output_count, ItemStack solution) {
		TileEntityCrystallizer.addRecipe(input, output, output_count, solution);
	}
}