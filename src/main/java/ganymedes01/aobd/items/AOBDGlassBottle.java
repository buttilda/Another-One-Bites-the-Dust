package ganymedes01.aobd.items;

import java.util.ArrayList;
import java.util.List;

import ganymedes01.aobd.AOBD;
import ganymedes01.aobd.lib.Reference;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class AOBDGlassBottle extends Item {

	public static final List<Item> bottles = new ArrayList<Item>();

	private final FluidStack fluid;

	public AOBDGlassBottle(Fluid fluid) {
		this.fluid = new FluidStack(fluid, 1000);
		bottles.add(this);
		setMaxStackSize(1);
		setCreativeTab(AOBD.tab);
		setContainerItem(Items.glass_bottle);
		setTextureName("potion_bottle_empty");
		setUnlocalizedName(Reference.MOD_ID + ".glassBottle." + fluid);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		String fullName = "item." + Reference.MOD_ID + ".glassBottle" + fluid.getFluid().getName() + ".name";
		String shortName = "item." + Reference.MOD_ID + ".glassBottle.name";
		FluidStack fluidStack = new FluidStack(fluid, 1000);
		return StatCollector.canTranslate(fullName) ? StatCollector.translateToLocal(fullName) : String.format(StatCollector.translateToLocal(shortName), fluidStack.getLocalizedName());
	}

	public FluidStack getFluid() {
		return fluid.copy();
	}
}