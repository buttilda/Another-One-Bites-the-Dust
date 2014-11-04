package ganymedes01.aobd.lib;

import ganymedes01.aobd.recipes.RecipesModule;
import ganymedes01.aobd.recipes.modules.*;

public enum CompatType {

	IC2("IC2", IC2Module.class, "dustTiny", "crushedPurified", "crushed", "dust"),
	RAILCRAFT("Railcraft", RailcraftModule.class, "crushed"),
	ENDERIO("EnderIO", EnderIOModule.class, "dust"),
	MEKANISM("Mekanism", MekanismModule.class, "clump", "crystal", "shard", "dustDirty", "dust"),
	THAUMCRAFT("Thaumcraft", ThaumcraftModule.class, "cluster"),
	THERMAL_EXPANTION("ThermalExpansion", ThermalExpansionModule.class, "dust"),
	FACTORISATION("factorization", FactorizationModule.class, "crystalline", "cleanGravel", "reduced", "dirtyGravel"),
	RANDOM_ADDITIONS("randomadditions", RandomAdditionsModule.class, "dust"),
	GANYS_NETHER("ganysnether", GanysNetherModule.class, "nugget"),
	ULTRA_TECH("UltraTech", UltraTechModule.class, "chunk", "dust");

	final String modid;
	final String[] prefixes;
	final Class<? extends RecipesModule> module;

	CompatType(String modid, Class<? extends RecipesModule> module, String... prefixes) {
		this.modid = modid;
		this.prefixes = prefixes;
		this.module = module;
	}

	public String modID() {
		return modid;
	}

	public String[] prefixes() {
		return prefixes;
	}

	public RecipesModule getModule() throws InstantiationException, IllegalAccessException {
		return module.newInstance();
	}
}