package ganymedes01.aobd.lib;

public enum CompatType {

	IC2("IC2"),
	RAILCRAFT("Railcraft"),
	ENDERIO("EnderIO"),
	MEKANISM("Mekanism"),
	THAUMCRAFT("Thaumcraft"),
	THERMAL_EXPANTION("ThermalExpansion"),
	FACTORISATION("factorization"),
	RANDOM_ADDITIONS("randomadditions"),
	GANYS_NETHER("ganysnether"),
	ULTRA_TECH("UltraTech");

	final String modid;

	CompatType(String modid) {
		this.modid = modid;
	}

	public String modID() {
		return modid;
	}
}