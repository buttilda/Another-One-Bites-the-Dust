package ganymedes01.aobd.recipes.modules;

import cpw.mods.fml.common.registry.GameRegistry;
import ganymedes01.aobd.items.AOBDItemBlock;
import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import tconstruct.TConstruct;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.Smeltery;
import tconstruct.smeltery.TinkerSmeltery;

public class TinkersConstruct extends RecipesModule {

	public TinkersConstruct() {
		super(CompatType.TINKERS_CONSTRUCT, "iron", "gold", "aluminium", "cobalt", "ardite", "platinum", "nickel", "silver", "lead", "copper", "tin", "steel", "endium");
	}

	@Override
	public void initOre(Ore ore) {
		Fluid fluid = getFluid(ore);

		int temp = (int) ore.energy(600);

		int blockLiquidValue = TConstruct.blockLiquidValue;
		int oreLiquidValue = TConstruct.oreLiquidValue;
		int ingotLiquidValue = TConstruct.ingotLiquidValue;
		int nuggetLiquidValue = TConstruct.nuggetLiquidValue;

		ItemStack block = null;
		for (ItemStack b : OreDictionary.getOres("block" + ore.name()))
			if (b.getItem() instanceof ItemBlock)
				block = b;

		if (block == null)
			throw new RuntimeException("Couldn't find a block" + ore.name() + " that was actually a block. This error should not happen. Please report it!");

		// Block
		addMeltingRecipe(block, temp, new FluidStack(fluid, blockLiquidValue));
		TConstructRegistry.getBasinCasting().addCastingRecipe(block, new FluidStack(fluid, blockLiquidValue), 50);

		// Ore
		addMeltingRecipe(getOreStack("ore", ore), temp, new FluidStack(fluid, oreLiquidValue));

		// Ingot
		Smeltery.addMelting(getOreStack("ingot", ore), Block.getBlockFromItem(block.getItem()), block.getItemDamage(), temp, new FluidStack(fluid, ingotLiquidValue));
		TConstructRegistry.getTableCasting().addCastingRecipe(getOreStack("ingot", ore), new FluidStack(fluid, ingotLiquidValue), new ItemStack(TinkerSmeltery.metalPattern), 50);

		// Nugget
		tryAddMelting("nugget", ore, block, fluid, temp, nuggetLiquidValue);
		tryAddCasting("nugget", ore, new FluidStack(fluid, nuggetLiquidValue), 27);

		// Others
		tryAddMelting("dust", ore, block, fluid, temp, ingotLiquidValue);
		tryAddMelting("crushed", ore, block, fluid, temp, ingotLiquidValue); // Railcraft
		tryAddMelting("cluster", ore, block, fluid, temp, 2 * ingotLiquidValue); // Thaumcraft
		if (block.getItem() instanceof AOBDItemBlock) { // Avoid adding duplicate recipes this way
			GameRegistry.addRecipe(new ShapedOreRecipe(block, "xxx", "xxx", "xxx", 'x', "ingot" + ore.name()));
			GameRegistry.addRecipe(new ShapelessOreRecipe(getOreStack("ingot", ore, 9), "block" + ore.name()));
		}
	}

	private void addMeltingRecipe(ItemStack input, int temp, FluidStack output) {
		if (input.getItem() instanceof ItemBlock)
			Smeltery.addMelting(input, temp, output);
		else
			Smeltery.addMelting(input, Blocks.iron_block, 0, temp, output);
	}

	private void tryAddMelting(String prefix, Ore ore, ItemStack block, Fluid fluid, int temp, int fluidAmount) {
		try {
			Smeltery.addMelting(getOreStack(prefix, ore), Block.getBlockFromItem(block.getItem()), block.getItemDamage(), temp, new FluidStack(fluid, fluidAmount));
		} catch (NullPointerException e) {
		}
	}

	private void tryAddCasting(String prefix, Ore ore, FluidStack fluid, int patternMeta) {
		try {
			TConstructRegistry.getTableCasting().addCastingRecipe(getOreStack(prefix, ore), fluid, new ItemStack(TinkerSmeltery.metalPattern, 1, patternMeta), 50);
		} catch (NullPointerException e) {
		}
	}
}