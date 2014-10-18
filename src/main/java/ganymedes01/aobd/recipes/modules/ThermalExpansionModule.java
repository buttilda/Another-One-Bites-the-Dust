package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.event.FMLInterModComms;

public class ThermalExpansionModule extends RecipesModule {

	public ThermalExpansionModule() {
		super(CompatType.THERMAL_EXPANTION, "iron", "gold", "copper", "tin", "silver", "lead", "nickel", "platinum", "mithril");
	}

	@Override
	public void initOre(Ore ore) {
		ItemStack cinnabar = getOreDictItem("crystalCinnabar");

		String name = ore.name();
		ItemStack block = getOreDictItem("ore" + name);

		addPulveriserRecipe(1000, getOreDictItem("ingot" + name), getOreDictItem("dust" + name), null, 0);
		addPulveriserRecipe((int) ore.energy(4000), block, getOreDictItem("dust" + name, 2), getOreDictItem("dust" + ore.extra()), 10);
		addInductionSmelterRecipe((int) ore.energy(4000), block, cinnabar.copy(), getOreDictItem("ingot" + name, 3), getOreDictItem("ingot" + ore.extra()), 100);
	}

	private void addPulveriserRecipe(int energy, ItemStack input, ItemStack output, ItemStack bonus, int chance) {
		NBTTagCompound data = new NBTTagCompound();

		data.setInteger("energy", energy);

		NBTTagCompound inputCompound = new NBTTagCompound();
		input.writeToNBT(inputCompound);
		data.setTag("input", inputCompound);

		NBTTagCompound outputCompound = new NBTTagCompound();
		output.writeToNBT(outputCompound);
		data.setTag("primaryOutput", outputCompound);

		if (bonus != null) {
			NBTTagCompound outputCompound2 = new NBTTagCompound();
			bonus.writeToNBT(outputCompound2);
			data.setTag("secondaryOutput", outputCompound2);

			data.setInteger("secondaryChance", chance);
		}

		FMLInterModComms.sendMessage("ThermalExpansion", "PulverizerRecipe", data);
	}

	private void addInductionSmelterRecipe(int energy, ItemStack input1, ItemStack input2, ItemStack output1, ItemStack output2, int chance) {
		NBTTagCompound data = new NBTTagCompound();

		data.setInteger("energy", energy);

		NBTTagCompound input1Compound = new NBTTagCompound();
		input1.writeToNBT(input1Compound);
		data.setTag("primaryInput", input1Compound);

		NBTTagCompound input2Compound = new NBTTagCompound();
		input2.writeToNBT(input2Compound);
		data.setTag("secondaryInput", input2Compound);

		NBTTagCompound output1Compound = new NBTTagCompound();
		output1.writeToNBT(output1Compound);
		data.setTag("primaryOutput", output1Compound);

		if (output2 != null) {
			NBTTagCompound output2Compound = new NBTTagCompound();
			output2.writeToNBT(output2Compound);
			data.setTag("secondaryOutput", output2Compound);

			data.setInteger("secondaryChance", chance);
		}

		FMLInterModComms.sendMessage("ThermalExpansion", "SmelterRecipe", data);
	}
}