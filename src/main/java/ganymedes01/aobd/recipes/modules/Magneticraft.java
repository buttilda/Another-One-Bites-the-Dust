package ganymedes01.aobd.recipes.modules;

import com.cout970.magneticraft.api.access.MgRecipeRegister;

import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class Magneticraft extends RecipesModule {

	public Magneticraft() {
		super(CompatType.MAGNETICRAFT, "aluminium", "aluminum", "ardite", "bismuth", "chromium", "cobalt", "copper", "gold", "iridium", "iron", "lead", "lithium", "manganese", "mithril", "zinc", "nickel", "nickel", "osmium", "platinum", "silver", "thorium", "tin", "titanium", "tungsten", "uranium", "zinc", "sulfur");
	}

	@Override
	public void initOre(Ore ore) {
		for (ItemStack stack : OreDictionary.getOres("ore" + ore.name()))
			MgRecipeRegister.registerCrusherRecipe(stack, getOreStack("chunk", ore), getOreStack("dust", ore), 0.05F, getOreStackExtra("dust", ore), 0.05F);
		for (ItemStack stack : OreDictionary.getOres("chunk" + ore.name())) {
			MgRecipeRegister.registerGrinderRecipe(stack, getOreStack("rubble", ore), getOreStack("dust", ore), 0.05F, getOreStackExtra("dust", ore), 0.05F);
			addSmeltingNoDupes(stack, getOreStack("ingot", ore, 2), 0.0F);
		}
		for (ItemStack stack : OreDictionary.getOres("rubble" + ore.name())) {
			MgRecipeRegister.registerGrinderRecipe(stack, getOreStack("pebbles", ore), getOreStack("dust", ore), 0.05F, getOreStackExtra("dust", ore), 0.05F);
			addSmeltingNoDupes(stack, getOreStack("ingot", ore, 2), 0.0F);
		}
		for (ItemStack stack : OreDictionary.getOres("pebbles" + ore.name())) {
			MgRecipeRegister.registerSifterRecipe(stack, getOreStack("dust", ore, 3), getOreStackExtra("dust", ore), 0.05F);
			addSmeltingNoDupes(stack, getOreStack("ingot", ore, 2), 0.0F);
		}
		addSmeltingNoDupes(getOreStack("dust", ore), getOreStack("ingot", ore), 0.0F);
	}
}