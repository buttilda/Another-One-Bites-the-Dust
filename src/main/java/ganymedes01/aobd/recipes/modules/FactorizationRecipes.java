package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class FactorizationRecipes extends RecipesModule {

	public static final List<String> blacklist = Arrays.asList("tin", "copper", "gold", "iron", "lead", "silver", "cobalt", "ardite", "fzdarkiron");

	public static void init() {
		for (Ore ore : Ore.ores)
			if (ore.shouldFactorization()) {
				if (blacklist.contains(ore.name().toLowerCase()))
					continue;

				String name = ore.name();
				ItemStack ingot = getOreDictItem("ingot" + name);
				ItemStack reduced = getOreDictItem("reduced" + name);
				ItemStack dirtyGravel = getOreDictItem("dirtyGravel" + name);
				ItemStack cleanGravel = getOreDictItem("cleanGravel" + name);
				ItemStack crystalline = getOreDictItem("crystalline" + name);
				ItemStack aquaRegia = getOreDictItem("aquaRegia");

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
				GameRegistry.addRecipe(new ShapelessOreRecipe(getOreDictItem("cleanGravel" + name, 8), "fz.waterBucketLike", dirty, dirty, dirty, dirty, dirty, dirty, dirty, dirty));
				GameRegistry.addRecipe(new ShapelessOreRecipe(cleanGravel, "fz.waterBucketLike", dirty));

				GameRegistry.addSmelting(reduced, ingot, 1.0F);
				GameRegistry.addSmelting(dirtyGravel, ingot, 1.0F);
				GameRegistry.addSmelting(cleanGravel, ingot, 1.0F);
				GameRegistry.addSmelting(crystalline, ingot, 1.0F);
			}
	}

	private static void addSlagFurnaceRecipe(Object input, Object output1, float prob1, Object output2, float prob2) {
		try {
			Class<?> cls = Class.forName("factorization.oreprocessing.TileEntitySlagFurnace$SlagRecipes");
			Method register = cls.getMethod("register", Object.class, float.class, Object.class, float.class, Object.class);

			register.invoke(null, input, prob1, output1, prob2, output2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void addLaceratorRecipe(Object input, ItemStack output, float prob) {
		try {
			Class<?> cls = Class.forName("factorization.oreprocessing.TileEntityGrinder");
			Method addRecipe = cls.getMethod("addRecipe", Object.class, ItemStack.class, float.class);

			addRecipe.invoke(null, input, output, prob);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void addCrystalliserRecipe(ItemStack input, ItemStack output, float output_count, ItemStack solution) {
		try {
			Class<?> cls = Class.forName("factorization.oreprocessing.TileEntityCrystallizer");
			Method addRecipe = cls.getMethod("addRecipe", ItemStack.class, ItemStack.class, float.class, ItemStack.class);

			addRecipe.invoke(null, input, output, output_count, solution);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}