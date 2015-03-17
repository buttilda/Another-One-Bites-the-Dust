package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.AOBD;
import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;

import java.util.ArrayList;
import java.util.List;

import mekanism.api.AdvancedInput;
import mekanism.api.gas.Gas;
import mekanism.api.gas.GasRegistry;
import mekanism.api.gas.GasStack;
import mekanism.api.gas.OreGas;
import mekanism.common.recipe.RecipeHandler;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Mekanism7 extends RecipesModule {

	private static List<OreGas> gasList = new ArrayList<OreGas>();

	public Mekanism7() {
		super(CompatType.MEKANISM7, "iron", "gold", "silver", "lead", "osmium", "copper", "tin");
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
		Gas hydrogenChloride = GasRegistry.getGas("hydrogenChloride");

		String name = ore.name();
		OreGas clean = new OreGasAOBD(name, "clean" + name, "oregas." + name.toLowerCase());
		OreGas slurry = new OreGasAOBD(name, name, "oregas." + name.toLowerCase()).setCleanGas(clean);
		gasList.add(slurry);

		for (ItemStack stack : OreDictionary.getOres("ore" + name))
			RecipeHandler.addEnrichmentChamberRecipe(stack, getOreStack("dust", ore, 2));
		RecipeHandler.addEnrichmentChamberRecipe(getOreStack("dustDirty", ore), getOreStack("dust", ore));

		RecipeHandler.addCrusherRecipe(getOreStack("clump", ore), getOreStack("dustDirty", ore));

		for (ItemStack stack : OreDictionary.getOres("ore" + name))
			RecipeHandler.addPurificationChamberRecipe(stack, getOreStack("clump", ore, 3));
		RecipeHandler.addPurificationChamberRecipe(getOreStack("shard", ore), getOreStack("clump", ore));

		for (ItemStack stack : OreDictionary.getOres("ore" + name))
			RecipeHandler.addChemicalInjectionChamberRecipe(new AdvancedInput(stack, hydrogenChloride), getOreStack("shard", ore, 4));
		RecipeHandler.addChemicalInjectionChamberRecipe(new AdvancedInput(getOreStack("crystal", ore), hydrogenChloride), getOreStack("shard", ore));

		for (ItemStack stack : OreDictionary.getOres("ore" + name))
			RecipeHandler.addChemicalDissolutionChamberRecipe(stack, new GasStack(slurry, 1000));
		RecipeHandler.addChemicalWasherRecipe(new GasStack(slurry, 1), new GasStack(slurry.getCleanGas(), 1));
		RecipeHandler.addChemicalCrystallizerRecipe(new GasStack(slurry.getCleanGas(), 200), getOreStack("crystal", ore));
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
