package ganymedes01.aobd.lib;

import ganymedes01.aobd.AOBD;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Loader;

public enum Metals {
	// @formatter:off
	Cobalt(AOBD.energyMultiplier, "Iron", Blocks.netherrack),
	Ardite(AOBD.energyMultiplier, "Gold", Blocks.netherrack),
	Aluminum(1, "Iron", Blocks.cobblestone),
	Nickel(1, "Platinum", Blocks.cobblestone, "ThermalExpansion"),
	FzDarkIron(AOBD.energyMultiplier, "Lead", Blocks.cobblestone, "factorization");
	// @formatter:on

	final double energy;
	final String extra;
	final Block substract;
	final String modID;

	Metals(double energy, String extra, Block substract, String modID) {
		this.energy = energy;
		this.extra = extra;
		this.substract = substract;
		this.modID = modID;
	}

	Metals(double energy, String extra, Block substract) {
		this(energy, extra, substract, "TConstruct");
	}

	public double getEnergy(float energy) {
		return energy * energy;
	}

	public String extra() {
		return extra;
	}

	public Block substract() {
		return substract;
	}

	public boolean shouldUse() {
		if (this == FzDarkIron)
			return AOBD.enableFZ;
		return Loader.isModLoaded(modID);
	}
}