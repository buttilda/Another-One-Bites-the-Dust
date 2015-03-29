package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.AOBD;
import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;

import java.util.ArrayList;
import java.util.List;

import mekanism.api.gas.Gas;
import mekanism.api.gas.GasRegistry;
import mekanism.api.gas.GasStack;
import mekanism.api.gas.OreGas;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Mekanism extends RecipesModule {

	private static List<OreGas> gasList = new ArrayList<OreGas>();

	public Mekanism() {
		super(CompatType.MEKANISM, "iron", "gold", "silver", "lead", "osmium", "copper", "tin");
	}

	@Override
	protected void preInit() {
		for (String gas : AOBD.userDefinedGases.split(",")) {
			String name = gas.trim();
			OreGas clean = new OreGasAOBD(name, "clean" + name, "oregas." + name.toLowerCase());
			OreGas slurry = new OreGasAOBD(name, name, "oregas." + name.toLowerCase()).setCleanGas(clean);
			gasList.add(slurry);
		}
	}

	@Override
	public void initOre(Ore ore) {
		String name = ore.name();
		OreGas clean = new OreGasAOBD(name, "clean" + name, "oregas." + name.toLowerCase());
		OreGas slurry = new OreGasAOBD(name, name, "oregas." + name.toLowerCase()).setCleanGas(clean);
		gasList.add(slurry);

		for (ItemStack stack : OreDictionary.getOres("ore" + name))
			addEnrichmentChamberRecipe(stack, getOreStack("dust", ore, 2));
		addEnrichmentChamberRecipe(getOreStack("dustDirty", ore), getOreStack("dust", ore));

		addCrusherRecipe(getOreStack("clump", ore), getOreStack("dustDirty", ore));

		Gas oxygen = GasRegistry.getGas("oxygen");
		for (ItemStack stack : OreDictionary.getOres("ore" + name))
			addPurificationChamberRecipe(stack, oxygen, getOreStack("clump", ore, 3));
		addPurificationChamberRecipe(getOreStack("shard", ore), oxygen, getOreStack("clump", ore));

		Gas hydrogenChloride = GasRegistry.getGas("hydrogenChloride");
		for (ItemStack stack : OreDictionary.getOres("ore" + name))
			addChemicalInjectionChamberRecipe(stack, hydrogenChloride, getOreStack("shard", ore, 4));
		addChemicalInjectionChamberRecipe(getOreStack("crystal", ore), hydrogenChloride, getOreStack("shard", ore));

		for (ItemStack stack : OreDictionary.getOres("ore" + name))
			addChemicalDissolutionChamberRecipe(stack, new GasStack(slurry, 1000));
		addChemicalWasherRecipe(new GasStack(slurry, 1), new GasStack(slurry.getCleanGas(), 1));
		addChemicalCrystallizerRecipe(new GasStack(slurry.getCleanGas(), 200), getOreStack("crystal", ore));
	}

	private static void addEnrichmentChamberRecipe(ItemStack input, ItemStack output) {
		addRecipe("EnrichmentChamberRecipe", input, output);
	}

	private static void addCrusherRecipe(ItemStack input, ItemStack output) {
		addRecipe("CrusherRecipe", input, output);
	}

	private static void addPurificationChamberRecipe(ItemStack input, Gas gas, ItemStack output) {
		addRecipe("PurificationChamberRecipe", input, gas, output);
	}

	private static void addChemicalInjectionChamberRecipe(ItemStack input, Gas gas, ItemStack output) {
		addRecipe("ChemicalInjectionChamberRecipe", input, gas, output);
	}

	private static void addChemicalDissolutionChamberRecipe(ItemStack input, GasStack output) {
		addRecipe("ChemicalDissolutionChamberRecipe", input, output);
	}

	private static void addChemicalWasherRecipe(GasStack input, GasStack output) {
		addRecipe("ChemicalWasherRecipe", input, output);
	}

	private static void addChemicalCrystallizerRecipe(GasStack input, ItemStack output) {
		addRecipe("ChemicalCrystallizerRecipe", input, output);
	}

	private static void addRecipe(String key, ItemStack input, ItemStack output) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setTag("input", input.writeToNBT(new NBTTagCompound()));
		nbt.setTag("output", output.writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", key, nbt);
	}

	private static void addRecipe(String key, ItemStack input, Gas gas, ItemStack output) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setTag("input", input.writeToNBT(new NBTTagCompound()));
		nbt.setTag("gasType", gas.write(new NBTTagCompound()));
		nbt.setTag("output", output.writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", key, nbt);
	}

	private static void addRecipe(String key, ItemStack input, GasStack output) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setTag("input", input.writeToNBT(new NBTTagCompound()));
		nbt.setTag("output", output.write(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", key, nbt);
	}

	private static void addRecipe(String key, GasStack input, GasStack output) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setTag("input", input.write(new NBTTagCompound()));
		nbt.setTag("output", output.write(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", key, nbt);
	}

	private static void addRecipe(String key, GasStack input, ItemStack output) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setTag("input", input.write(new NBTTagCompound()));
		nbt.setTag("output", output.writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", key, nbt);
	}

	@SideOnly(Side.CLIENT)
	public static void registerIcons(TextureMap map) {
		IIcon clean = map.registerIcon("mekanism:LiquidCleanOre");
		IIcon dirty = map.registerIcon("mekanism:LiquidOre");
		for (OreGas gas : gasList) {
			gas.setIcon(dirty);
			gas.getCleanGas().setIcon(clean);
		}
	}

	private static class OreGasAOBD extends OreGas {

		private final String ore;

		public OreGasAOBD(String ore, String s, String name) {
			super(s, name);
			this.ore = ore;
			GasRegistry.register(this);
		}

		@Override
		public String getLocalizedName() {
			return String.format(StatCollector.translateToLocal("gas.aobd." + (isClean() ? "clean" : "dirty") + ".name"), ore);
		}

		@Override
		public String getOreName() {
			return String.format(StatCollector.translateToLocal("gas.aobd.ore.name"), ore);
		}
	}
}