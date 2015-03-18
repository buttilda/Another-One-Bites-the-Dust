package ganymedes01.aobd.recipes;

import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.ore.OreFinder;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;

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

	private static final Map<String, ItemStack> cache = new HashMap<String, ItemStack>();

	protected static ItemStack getOreStack(String prefix, Ore ore) {
		return getOreStack(prefix, ore, 1, false);
	}

	protected static ItemStack getOreStack(String prefix, Ore ore, int size) {
		return getOreStack(prefix, ore, size, false);
	}

	protected static ItemStack getOreStackExtra(String prefix, Ore ore) {
		return getOreStack(prefix, ore, 1, true);
	}

	protected static ItemStack getOreStackExtra(String prefix, Ore ore, int size) {
		return getOreStack(prefix, ore, size, true);
	}

	private static ItemStack getOreStack(String prefix, Ore ore, int size, boolean extra) {
		if (extra)
			try {
				return getOreStack(prefix + ore.extra(), size);
			} catch (NullPointerException e) {
				return getOreStack(prefix, ore, size);
			}
		else
			return getOreStack(prefix + ore.name(), size);
	}

	protected static ItemStack getOreStack(String name) {
		return getOreStack(name, 1);
	}

	protected static ItemStack getOreStack(String name, int size) {
		try {
			if (OreFinder.itemMap.containsKey(name))
				return new ItemStack(OreFinder.itemMap.get(name), size);
			else {
				ItemStack stack = ItemStack.copyItemStack(cache.get(name));
				if (stack != null) {
					stack.stackSize = size;
					return stack;
				} else {
					stack = ItemStack.copyItemStack(OreDictionary.getOres(name).get(0));
					cache.put(name, stack.copy());
					stack.stackSize = size;

					return stack;
				}
			}
		} catch (Exception e) {
			throw new NullPointerException("Ore dictionary item not found: " + name);
		}
	}

	public static void clearCache() {
		cache.clear();
	}

	@SuppressWarnings("unchecked")
	protected void addSmeltingNoDupes(ItemStack input, ItemStack output, float xp) {
		for (ItemStack stack : (Set<ItemStack>) FurnaceRecipes.smelting().getSmeltingList().keySet())
			if (areStacksTheSame(stack, input))
				return;
		GameRegistry.addSmelting(input, output, xp);
	}

	protected boolean areStacksTheSame(ItemStack stack1, ItemStack stack2) {
		return stack1.getItem() == stack2.getItem() && stack1.getItemDamage() == stack2.getItemDamage();
	}

	public List<String> blacklist() {
		return Collections.unmodifiableList(blacklist);
	}

	public CompatType type() {
		return type;
	}
}