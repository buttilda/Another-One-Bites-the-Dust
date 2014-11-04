package ganymedes01.aobd.recipes;

import ganymedes01.aobd.AOBD;
import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModulesHandler {

	private static final List<RecipesModule> modules = new ArrayList<RecipesModule>();

	public static void init() {
		smeltingRecipes();

		for (CompatType compat : CompatType.values())
			if (AOBD.isCompatEnabled(compat))
				try {
					modules.add(compat.getModule());
				} catch (Exception e) {
					throw new RuntimeException(e);
				}

		if (modules.isEmpty())
			return;
		for (RecipesModule module : modules)
			module.init();
	}

	public static void postInit() {
		if (modules.isEmpty())
			return;
		for (RecipesModule module : modules)
			module.postInit();
	}

	public static List<String> getBlacklist(CompatType type) {
		for (RecipesModule module : modules)
			if (module.type() == type)
				return module.blacklist();

		return Collections.emptyList();
	}

	private static void smeltingRecipes() {
		for (Ore ore : Ore.ores)
			if (ore.isEnabled()) {
				String name = ore.name();
				try {
					GameRegistry.addSmelting(RecipesModule.getOreDictItem("dust" + name), RecipesModule.getOreDictItem("ingot" + name), (float) ore.chance());
				} catch (NullPointerException e) {
				}
			}
	}
}