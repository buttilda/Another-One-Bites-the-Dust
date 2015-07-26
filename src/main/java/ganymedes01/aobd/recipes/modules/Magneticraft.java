package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.cout970.magneticraft.api.acces.MgRecipeRegister;

public class Magneticraft extends RecipesModule {

	public Magneticraft() {
		super(CompatType.MAGNETICRAFT, "iron", "gold", "copper", "thorium", "uranium", "silver", "tin", "lead", "aluminium", "zinc", "nickel", "titanium", "tungsten", "sulfur");
	}

	@Override
	public void initOre(Ore ore) {
		for (ItemStack stack : OreDictionary.getOres("ore" + ore.name()))
			MgRecipeRegister.registerCrusherRecipe(stack, getOreStack("chunk", ore), getOreStackExtra("dust", ore), 5, null, 0);
		for (ItemStack stack : OreDictionary.getOres("chunk" + ore.name())) {
			MgRecipeRegister.registerGrinderRecipe(stack, getOreStack("sand", ore), getOreStackExtra("dust", ore), 0.05F, null, 0);
			addSmeltingNoDupes(stack, getOreStack("ingot", ore), 0.0F);
		}
		addSmeltingNoDupes(getOreStack("sand", ore), getOreStack("ingot", ore, 2), 0.0F);
	}
}