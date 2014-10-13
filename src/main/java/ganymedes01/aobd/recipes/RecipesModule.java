package ganymedes01.aobd.recipes;

import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.ore.OreFinder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public abstract class RecipesModule {

	private final CompatType type;
	private final List<String> blacklist;

	public RecipesModule(CompatType type, String... blacklist) {
		this.type = type;
		if (blacklist != null && blacklist.length > 0)
			this.blacklist = Arrays.asList(blacklist);
		else
			this.blacklist = Collections.emptyList();
	}

	public final void init() {
		preInit();

		for (Ore ore : Ore.ores)
			if (isOreEnabled(ore))
				initOre(ore);
	}

	protected boolean isOreEnabled(Ore ore) {
		return ore.isCompatEnabled(type) && !blacklist.contains(ore.name().toLowerCase());
	}

	protected void preInit() {
	}

	protected abstract void initOre(Ore ore);

	protected void postInit() {
	}

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

	public List<String> blacklist() {
		return Collections.unmodifiableList(blacklist);
	}

	public CompatType type() {
		return type;
	}
}