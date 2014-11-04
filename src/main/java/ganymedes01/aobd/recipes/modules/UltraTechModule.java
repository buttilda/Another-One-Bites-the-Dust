package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import ultratech.api.recipes.RecipeRegistry;

import common.cout970.UltraTech.managers.ItemManager;

public class UltraTechModule extends RecipesModule {

	public UltraTechModule() {
		super(CompatType.ULTRA_TECH, "gold", "iron");
	}

	@Override
	public void initOre(Ore ore) {
		String name = ore.name();

		for (ItemStack stack : OreDictionary.getOres("ore" + name))
			RecipeRegistry.addRecipeCutter(stack, getOreStack("chunk", ore, 3));
		for (ItemStack stack : OreDictionary.getOres("chunk" + name))
			RecipeRegistry.addRecipePurifier(getOreStack("dust", ore), stack);
	}

	public static void registerOres() {
		Item chunk = ItemManager.ItemName.get("Chunk");

		OreDictionary.registerOre("chunkAluminium", new ItemStack(chunk, 1, 0));
		OreDictionary.registerOre("chunkAluminum", new ItemStack(chunk, 1, 0));
		OreDictionary.registerOre("chunkCopper", new ItemStack(chunk, 1, 1));
		OreDictionary.registerOre("chunkTin", new ItemStack(chunk, 1, 2));
		OreDictionary.registerOre("chunkLead", new ItemStack(chunk, 1, 3));
		OreDictionary.registerOre("chunkSilver", new ItemStack(chunk, 1, 4));
		OreDictionary.registerOre("chunkIron", new ItemStack(chunk, 1, 5));
		OreDictionary.registerOre("chunkGold", new ItemStack(chunk, 1, 6));
		OreDictionary.registerOre("chunkRadionite", new ItemStack(chunk, 1, 7));
	}
}