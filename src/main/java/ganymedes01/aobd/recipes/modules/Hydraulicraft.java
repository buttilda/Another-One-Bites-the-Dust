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
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setTag("itemFrom", input.writeToNBT(new NBTTagCompound()));
		nbt.setTag("itemTo", output.writeToNBT(new NBTTagCompound()));
		nbt.setFloat("pressureRatio", pressureRatio);
		FMLInterModComms.sendMessage("HydCraft", "registerCrushingRecipe", nbt);
	}

	private void addWashingRecipe(ItemStack input, ItemStack output, float pressureRatio) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setTag("itemFrom", input.writeToNBT(new NBTTagCompound()));
		nbt.setTag("itemTo", output.writeToNBT(new NBTTagCompound()));
		nbt.setFloat("pressureRatio", pressureRatio);
		FMLInterModComms.sendMessage("HydCraft", "registerWashingRecipe", nbt);
	}
	// @Override
	// public void initOre(Ore ore) {
	// addCrushingRecipe("ore" + ore.name(), getOreStack("chunk", ore, 2), 1.0F);
	// addCrushingRecipe("ingot" + ore.name(), getOreStack("dust", ore), 0.5F);
	// addWashingRecipe("chunk" + ore.name(), getOreStack("dust", ore), 400F);
	// }
	//
	// private void addCrushingRecipe(String input, ItemStack output, float pressureRatio) {
	// HydraulicRecipes.INSTANCE.addCrushingRecipe(new FluidShapelessOreRecipe(output, input).setPressure(pressureRatio).setCraftingTime(200));
	// }
	//
	// private void addWashingRecipe(String input, ItemStack output, float pressureRatio) {
	// HydraulicRecipes.INSTANCE.addWasherRecipe(new FluidShapelessOreRecipe(output, input).addFluidInput(new FluidStack(FluidRegistry.WATER, 1000)).setCraftingTime(200));
	// }
}