package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.event.FMLInterModComms;

public class TE3Recipes extends RecipesModule {

	public static void init() {
		ItemStack cinnabar = getOreDictItem("crystalCinnabar");

		for (Ore ore : Ore.ores)
			if (ore.shouldTE3()) {
				String name = ore.name();

				ItemStack ingot = getOreDictItem("ingot" + name);
				ItemStack dust = getOreDictItem("dust" + name);
				ItemStack block = getOreDictItem("ore" + name);

				addPulveriserRecipe(1000, ingot.copy(), dust.copy(), null, 0);
				dust.stackSize = 2;
				addPulveriserRecipe((int) ore.energy(4000), block, dust.copy(), getOreDictItem("dust" + ore.extra()), 10);
				ingot.stackSize = 3;
				addInductionSmelterRecipe((int) ore.energy(4000), block, cinnabar.copy(), ingot, getOreDictItem("ingot" + ore.extra()), 100);
			}
	}

	private static void addPulveriserRecipe(int energy, ItemStack input, ItemStack output, ItemStack bonus, int chance) {
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

	private static void addInductionSmelterRecipe(int energy, ItemStack input1, ItemStack input2, ItemStack output1, ItemStack output2, int chance) {
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