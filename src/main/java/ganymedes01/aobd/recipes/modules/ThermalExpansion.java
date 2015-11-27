package ganymedes01.aobd.recipes.modules;

import cpw.mods.fml.common.event.FMLInterModComms;
import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ThermalExpansion extends RecipesModule {

	public ThermalExpansion() {
		super(CompatType.THERMAL_EXPANSION, "iron", "gold", "copper", "tin", "silver", "lead", "nickel", "platinum", "mithril");
	}

	@Override
	public void initOre(Ore ore) {
		ItemStack cinnabar = getOreStack("crystalCinnabar");

		addPulveriserRecipe(1000, getOreStack("ingot", ore), getOreStack("dust", ore), null, 0);
		addPulveriserRecipe((int) ore.energy(4000), getOreStack("ore", ore), getOreStack("dust", ore, 2), getOreStackExtra("dust", ore), 10);
		addInductionSmelterRecipe((int) ore.energy(4000), getOreStack("ore", ore), cinnabar.copy(), getOreStack("ingot", ore, 3), getOreStackExtra("ingot", ore), 100);
	}

	private void addPulveriserRecipe(int energy, ItemStack input, ItemStack output, ItemStack bonus, int chance) {
		NBTTagCompound data = new NBTTagCompound();

		data.setInteger("energy", energy);
		data.setTag("input", input.writeToNBT(new NBTTagCompound()));
		data.setTag("primaryOutput", output.writeToNBT(new NBTTagCompound()));

		if (bonus != null) {
			data.setTag("secondaryOutput", bonus.writeToNBT(new NBTTagCompound()));
			data.setInteger("secondaryChance", chance);
		}

		FMLInterModComms.sendMessage("ThermalExpansion", "PulverizerRecipe", data);
	}

	private void addInductionSmelterRecipe(int energy, ItemStack input1, ItemStack input2, ItemStack output1, ItemStack output2, int chance) {
		NBTTagCompound data = new NBTTagCompound();

		data.setInteger("energy", energy);
		data.setTag("primaryInput", input1.writeToNBT(new NBTTagCompound()));
		data.setTag("secondaryInput", input2.writeToNBT(new NBTTagCompound()));
		data.setTag("primaryOutput", output1.writeToNBT(new NBTTagCompound()));

		if (output2 != null) {
			data.setTag("secondaryOutput", output2.writeToNBT(new NBTTagCompound()));
			data.setInteger("secondaryChance", chance);
		}

		FMLInterModComms.sendMessage("ThermalExpansion", "SmelterRecipe", data);
	}
}