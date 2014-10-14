package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;
import mods.railcraft.api.crafting.IRockCrusherRecipe;
import mods.railcraft.api.crafting.RailcraftCraftingManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RailcraftModule extends RecipesModule {

	public RailcraftModule() {
		super(CompatType.RAILCRAFT, "iron", "copper", "gold", "tin", "uranium", "silver", "lead");
	}

	@Override
	public void initOre(Ore ore) {
		try {
			for (ItemStack stack : OreDictionary.getOres("ore" + ore.name())) {
				IRockCrusherRecipe recipe = RailcraftCraftingManager.rockCrusher.createNewRecipe(stack, true, false);
				recipe.addOutput(getOreDictItem("crushed" + ore.name(), 2), 1.0F);
			}
		} catch (Exception e) {
		}
	}
}