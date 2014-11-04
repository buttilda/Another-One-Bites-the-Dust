package ganymedes01.aobd.lib;

public enum CompatType {

	IC2(				"IC2", 					"dustTiny", "crushedPurified", "crushed", "dust"),
	RAILCRAFT(			"Railcraft"),
	ENDERIO(			"EnderIO", 				"dust"),
	MEKANISM(			"Mekanism", 			"clump", "crystal", "shard", "dustDirty", "dust"),
	THAUMCRAFT(			"Thaumcraft", 			"cluster"),
	THERMAL_EXPANTION(	"ThermalExpansion", 	"dust"),
	FACTORISATION(		"factorization", 		"crystalline", "cleanGravel", "reduced", "dirtyGravel"),
	RANDOM_ADDITIONS(	"randomadditions", 		"dust"),
	GANYS_NETHER(		"ganysnether", 			"nugget"),
	ULTRA_TECH(			"UltraTech", 			"chunk", "dust");

	final String modid;
	final String[] prefixes;

	CompatType(String modid, String... prefixes) {
		this.modid = modid;
		this.prefixes = prefixes;
	}

	public String modID() {
		return modid;
	}

	public String[] prefixes() {
		return prefixes;
	}
}