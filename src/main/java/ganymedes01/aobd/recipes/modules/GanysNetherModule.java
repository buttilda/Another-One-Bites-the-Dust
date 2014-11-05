package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;
import ganymedes01.ganysnether.recipes.MagmaticCentrifugeRecipes;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class GanysNetherModule extends RecipesModule {

	public GanysNetherModule() {
		super(CompatType.GANYS_NETHER);
	}

	@Override
	public void initOre(Ore ore) {
		String name = ore.name();
		ItemStack ingot = getOreStack("ingot", ore);
		ItemStack nugget = getOreStackExtra("nugget", ore);

		MagmaticCentrifugeRecipes.INSTANCE.addRecipe("ore" + name, "ore" + name, ingot, ingot, ingot, nugget);
		addRecipe(new ShapedOreRecipe(ingot.copy(), "xxx", "xxx", "xxx", 'x', nugget.copy()));
		addRecipe(new ShapedOreRecipe(getOreStackExtra("nugget", ore, 9), "x", 'x', ingot.copy()));
	}

	@SuppressWarnings("unchecked")
	private void addRecipe(ShapedOreRecipe recipe) {
		for (IRecipe r : (List<IRecipe>) CraftingManager.getInstance().getRecipeList())
			if (r instanceof ShapedOreRecipe) {
				ShapedOreRecipe oreRec = (ShapedOreRecipe) r;
				if (areStacksTheSame(oreRec.getRecipeOutput(), recipe.getRecipeOutput()) && checkInputs(oreRec.getInput(), recipe.getInput()))
					return;
			}

		GameRegistry.addRecipe(recipe);
	}

	private boolean checkInputs(Object[] inputs1, Object[] inputs2) {
		if (inputs1.length != inputs2.length)
			return false;

		for (int i = 0; i < inputs1.length; i++)
			if (!objEquals(inputs1[i], inputs2[i]))
				return false;

		return true;
	}

	private boolean objEquals(Object obj1, Object obj2) {
		if (obj1 == null && obj2 != null)
			return false;
		if (obj1 != null && obj2 == null)
			return false;

		if (obj1 instanceof ItemStack && obj2 instanceof ItemStack)
			return areStacksTheSame((ItemStack) obj1, (ItemStack) obj2);

		return obj1.equals(obj2);
	}

	private boolean areStacksTheSame(ItemStack stack1, ItemStack stack2) {
		if (stack1 == null || stack2 == null)
			return false;

		if (stack1.getItem() == stack2.getItem())
			if (stack1.getItemDamage() == stack2.getItemDamage())
				if (stack1.stackSize == stack2.stackSize)
					if (stack1.hasTagCompound() && stack2.hasTagCompound())
						return stack1.getTagCompound().equals(stack2.getTagCompound());
					else
						return stack1.hasTagCompound() == stack2.hasTagCompound();

		return false;
	}
}