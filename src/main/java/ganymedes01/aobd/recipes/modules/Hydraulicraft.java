package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.event.FMLInterModComms;

public class Hydraulicraft extends RecipesModule {

	public Hydraulicraft() {
		super(CompatType.HYDRAULICRAFT, "iron", "gold", "copper", "lead", "tin", "silver", "nickel", "ardite", "cobalt", "fzdarkiron");
	}

	@Override
	public void initOre(Ore ore) {
		for (ItemStack stack : OreDictionary.getOres("ore" + ore.name()))
			addCrushingRecipe(stack, getOreStack("chunk", ore, 2), 1.0F);
		for (ItemStack stack : OreDictionary.getOres("ingot" + ore.name()))
			addCrushingRecipe(stack, getOreStack("dust", ore), 0.5F);
		for (ItemStack stack : OreDictionary.getOres("chunk" + ore.name()))
			addWashingRecipe(stack, getOreStack("dust", ore), 400F);
	}

	private void addCrushingRecipe(ItemStack input, ItemStack output, float pressureRatio) {
		NBTTagCompound toRegister = new NBTTagCompound();
		NBTTagCompound itemFrom = new NBTTagCompound();
		NBTTagCompound itemTo = new NBTTagCompound();

		input.writeToNBT(itemFrom);
		output.writeToNBT(itemTo);

		toRegister.setTag("itemFrom", itemFrom);
		toRegister.setTag("itemTo", itemTo);
		toRegister.setFloat("pressureRatio", pressureRatio);
		FMLInterModComms.sendMessage("HydCraft", "registerCrushingRecipe", toRegister);
	}

	private void addWashingRecipe(ItemStack input, ItemStack output, float pressureRatio) {
		NBTTagCompound toRegister = new NBTTagCompound();
		NBTTagCompound itemFrom = new NBTTagCompound();
		NBTTagCompound itemTo = new NBTTagCompound();

		input.writeToNBT(itemFrom);
		output.writeToNBT(itemTo);

		toRegister.setTag("itemFrom", itemFrom);
		toRegister.setTag("itemTo", itemTo);
		toRegister.setFloat("pressureRatio", pressureRatio);
		FMLInterModComms.sendMessage("HydCraft", "registerWashingRecipe", toRegister);
	}
}