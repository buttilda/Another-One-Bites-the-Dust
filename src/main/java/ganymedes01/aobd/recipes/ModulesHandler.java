package ganymedes01.aobd.recipes;

import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.registry.GameRegistry;
import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;

public class ModulesHandler {

	private static final Map<CompatType, RecipesModule> modules = new HashMap<CompatType, RecipesModule>();

	public static void createModules() {
		try {
			for (CompatType compat : CompatType.values())
				if (compat.isEnabled())
					modules.put(compat, compat.getModule());
		} catch (Exception e) {
			throw new RuntimeException(e); // Impossible?
		}
	}

	public static void init() {
		smeltingRecipes();

		if (modules.isEmpty())
			return;
		for (RecipesModule module : modules.values())
			module.init();
	}

	public static void postInit() {
		if (modules.isEmpty())
			return;
		for (RecipesModule module : modules.values())
			module.postInit();

		RecipesModule.clearCache();
	}

	public static boolean isOreBlacklisted(CompatType type, String ore) {
		return modules.get(type).blacklist().contains(ore.toLowerCase());
	}

	private static void smeltingRecipes() {
		for (Ore ore : Ore.ores)
			if (ore.isEnabled())
				registerSmelting(ore.name());
	}

	private static void registerSmelting(String name) {
		try {
			GameRegistry.addSmelting(RecipesModule.getOreStack("dust" + name), RecipesModule.getOreStack("ingot" + name), 0.2F);
		} catch (NullPointerException e) {
		}
	}
}