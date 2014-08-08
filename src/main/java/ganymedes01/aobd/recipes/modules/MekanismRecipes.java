package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.AOBD;
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

public class MekanismRecipes extends RecipesModule {

	public static final String[] blacklist = { "iron", "gold", "silver", "lead", "osmium", "copper", "tin" };
	private static List<OreGas> gasList = new ArrayList<OreGas>();

	public static void init() {
		Gas hydrogenChloride = GasRegistry.getGas("hydrogenChloride");

		for (String gas : AOBD.userDefinedGases.split(",")) {
			String name = gas.trim();
			OreGas gass = new OreGasAOBD(name, name, "oregas." + name.toLowerCase()).setCleanGas(new OreGasAOBD(name, "clean" + name, "oregas." + name.toLowerCase()));
			gasList.add(gass);
			GasRegistry.register(gass);
		}

		label: for (Ore ore : Ore.ores)
			if (ore.shouldMekanism()) {
				for (String bEntry : blacklist)
					if (ore.name().equalsIgnoreCase(bEntry))
						continue label;

				String name = ore.name();
				OreGas slurry = new OreGasAOBD(name, name, "oregas." + name.toLowerCase()).setCleanGas(new OreGasAOBD(name, "clean" + name, "oregas." + name.toLowerCase()));
				gasList.add(slurry);
				GasRegistry.register(slurry);

				for (ItemStack stack : OreDictionary.getOres("ore" + name))
					RecipeHandler.addEnrichmentChamberRecipe(stack, getOreDictItem("dust" + name, 2));
				RecipeHandler.addEnrichmentChamberRecipe(getOreDictItem("dustDirty" + name), getOreDictItem("dust" + name));

				RecipeHandler.addCrusherRecipe(getOreDictItem("clump" + name), getOreDictItem("dustDirty" + name));

				for (ItemStack stack : OreDictionary.getOres("ore" + name))
					RecipeHandler.addPurificationChamberRecipe(stack, getOreDictItem("clump" + name, 3));
				RecipeHandler.addPurificationChamberRecipe(getOreDictItem("shard" + name), getOreDictItem("clump" + name));

				for (ItemStack stack : OreDictionary.getOres("ore" + name))
					RecipeHandler.addChemicalInjectionChamberRecipe(new AdvancedInput(stack, hydrogenChloride), getOreDictItem("shard" + name, 4));
				RecipeHandler.addChemicalInjectionChamberRecipe(new AdvancedInput(getOreDictItem("crystal" + name), hydrogenChloride), getOreDictItem("shard" + name));

				for (ItemStack stack : OreDictionary.getOres("ore" + name))
					RecipeHandler.addChemicalDissolutionChamberRecipe(stack, new GasStack(slurry, 1000));
				RecipeHandler.addChemicalWasherRecipe(new GasStack(slurry, 1), new GasStack(slurry.getCleanGas(), 1));
				RecipeHandler.addChemicalCrystallizerRecipe(new GasStack(slurry.getCleanGas(), 200), getOreDictItem("crystal" + name));
			}
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
		}

		@Override
		public String getLocalizedName() {
			return String.format(StatCollector.translateToLocal("gas.aobd." + (isClean() ? "clean" : "dirty") + ".name"), ore);
		}
	}
}