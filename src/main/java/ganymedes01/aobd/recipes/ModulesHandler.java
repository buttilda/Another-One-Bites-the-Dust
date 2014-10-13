package ganymedes01.aobd.recipes;

import ganymedes01.aobd.AOBD;
import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.modules.EnderIOModule;
import ganymedes01.aobd.recipes.modules.FactorizationModule;
import ganymedes01.aobd.recipes.modules.IC2Module;
import ganymedes01.aobd.recipes.modules.MekanismModule;
import ganymedes01.aobd.recipes.modules.RailcraftModule;
import ganymedes01.aobd.recipes.modules.ThaumcraftModule;
import ganymedes01.aobd.recipes.modules.ThermalExpansionModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModulesHandler {

	private static final List<RecipesModule> modules = new ArrayList<RecipesModule>();

	public static void init() {
		smeltingRecipes();

		if (AOBD.isCompatEnabled(CompatType.IC2)) {
			modules.add(new IC2Module());
			if (AOBD.isCompatEnabled(CompatType.RAILCRAFT))
				modules.add(new RailcraftModule());
		}
		if (AOBD.isCompatEnabled(CompatType.MEKANISM))
			modules.add(new MekanismModule());
		if (AOBD.isCompatEnabled(CompatType.ENDERIO))
			modules.add(new EnderIOModule());
		if (AOBD.isCompatEnabled(CompatType.THAUMCRAFT))
			modules.add(new ThaumcraftModule());
		if (AOBD.isCompatEnabled(CompatType.THERMAL_EXPANTION))
			modules.add(new ThermalExpansionModule());
		if (AOBD.isCompatEnabled(CompatType.FACTORISATION))
			modules.add(new FactorizationModule());

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
				GameRegistry.addSmelting(RecipesModule.getOreDictItem("dust" + name), RecipesModule.getOreDictItem("ingot" + name), 0.2F);
			}
	}
}