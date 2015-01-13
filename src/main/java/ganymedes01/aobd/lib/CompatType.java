package ganymedes01.aobd.lib;

import ganymedes01.aobd.recipes.RecipesModule;
import ganymedes01.aobd.recipes.modules.AppEnergisticsModule;
import ganymedes01.aobd.recipes.modules.EnderIOModule;
import ganymedes01.aobd.recipes.modules.FactorizationModule;
import ganymedes01.aobd.recipes.modules.GanysNetherModule;
import ganymedes01.aobd.recipes.modules.HydraulicraftModule;
import ganymedes01.aobd.recipes.modules.IC2Module;
import ganymedes01.aobd.recipes.modules.MekanismModule;
import ganymedes01.aobd.recipes.modules.ModularSystemsModule;
import ganymedes01.aobd.recipes.modules.NetherOreModule;
import ganymedes01.aobd.recipes.modules.RailcraftModule;
import ganymedes01.aobd.recipes.modules.RandomAdditionsModule;
import ganymedes01.aobd.recipes.modules.SimpleOreGrinderModule;
import ganymedes01.aobd.recipes.modules.ThaumcraftModule;
import ganymedes01.aobd.recipes.modules.ThermalExpansionModule;
import ganymedes01.aobd.recipes.modules.TinkersConstructModule;
import ganymedes01.aobd.recipes.modules.UltraTechModule;
import cpw.mods.fml.common.Loader;

public enum CompatType {

	IC2("IC2", IC2Module.class, "dustTiny", "crushedPurified", "crushed", "dust"),
	RAILCRAFT("Railcraft", RailcraftModule.class, "crushed"),
	ENDERIO("EnderIO", EnderIOModule.class, "dust"),
	MEKANISM("Mekanism", MekanismModule.class, "clump", "crystal", "shard", "dustDirty", "dust"),
	THAUMCRAFT("Thaumcraft", ThaumcraftModule.class, "cluster"),
	THERMAL_EXPANSION("ThermalExpansion", ThermalExpansionModule.class, "dust"),
	FACTORISATION("factorization", FactorizationModule.class, "crystalline", "cleanGravel", "reduced", "dirtyGravel"),
	RANDOM_ADDITIONS("randomadditions", RandomAdditionsModule.class, "dust"),
	GANYS_NETHER("ganysnether", GanysNetherModule.class, "nugget"),
	ULTRA_TECH("UltraTech", UltraTechModule.class, "chunk", "dust"),
	MODULAR_SYSTEMS("modularsystems", ModularSystemsModule.class, "dust"),
	NETHER_ORES("NetherOres", NetherOreModule.class, "ore"),
	APPLIED_ENERGISTICS("appliedenergistics2", AppEnergisticsModule.class, "dust"),
	HYDRAULICRAFT("HydCraft", HydraulicraftModule.class, "dust", "chunk"),
	TINKERS_CONSTRUCT("TConstruct", TinkersConstructModule.class),
	SIMPLE_ORE_GRINDER("simpleoregrinder", SimpleOreGrinderModule.class, "dust");

	final String modid;
	final String[] prefixes;
	final Class<? extends RecipesModule> module;
	boolean enabled = true;

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

	public void setStatus(boolean status) {
		enabled = status;
	}

	public boolean isEnabled() {
		return enabled && Loader.isModLoaded(modID());
	}
}