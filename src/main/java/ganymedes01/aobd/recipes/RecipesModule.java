package ganymedes01.aobd.recipes;

import ganymedes01.aobd.ore.OreFinder;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipesModule {

	protected static ItemStack getOreDictItem(String name) {
		return getOreDictItem(name, 1);
	}

	protected static ItemStack getOreDictItem(String name, int size) {
		if (OreFinder.itemMap.containsKey(name))
			return new ItemStack(OreFinder.itemMap.get(name), size);
		try {
			ItemStack stack = OreDictionary.getOres(name).get(0).copy();
			stack.stackSize = size;

			return stack;
		} catch (IndexOutOfBoundsException e) {
			throw new NullPointerException("Ore dictionary item not found: " + name);
		}
	}
}