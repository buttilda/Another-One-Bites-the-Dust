package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.event.FMLInterModComms;

public class Railcraft extends RecipesModule {

	public Railcraft() {
		super(CompatType.RAILCRAFT);
	}

	@Override
	public void initOre(Ore ore) {
		for (ItemStack stack : OreDictionary.getOres("ore" + ore.name()))
			addRecipe(stack, ore);
	}

	private void addRecipe(ItemStack input, Ore ore) {
		addRecipe(input, getOreStack("crushed", ore, 2), 1.0F);

		// Make sure we don't register the smelting recipe twice
		addSmeltingNoDupes(getOreStack("crushed", ore), getOreStack("ingot", ore), 0.2F);
	}

	private void addRecipe(ItemStack input, ItemStack output, float chance) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setTag("input", input.writeToNBT(new NBTTagCompound()));

		nbt.setBoolean("matchMeta", true);
		nbt.setBoolean("matchNBT", false);

		NBTTagCompound outputNBT = output.writeToNBT(new NBTTagCompound());
		outputNBT.setFloat("chance", chance);
		nbt.setTag("output0", outputNBT);

		FMLInterModComms.sendMessage("Railcraft", "rock-crusher", nbt);
	}
}