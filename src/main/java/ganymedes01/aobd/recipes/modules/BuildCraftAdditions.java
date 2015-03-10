package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.event.FMLInterModComms;

public class BuildCraftAdditions extends RecipesModule {

	public BuildCraftAdditions() {
		super(CompatType.BC_ADDITIONS, "iron", "gold");
	}

	@Override
	public void initOre(Ore ore) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("Input", "ore" + ore.name());

		NBTTagCompound outputTag = new NBTTagCompound();
		getOreStack("dust", ore, 2).writeToNBT(outputTag);
		nbt.setTag("Output", outputTag);

		FMLInterModComms.sendMessage("bcadditions", "addDustingRecipe", nbt);
	}
}