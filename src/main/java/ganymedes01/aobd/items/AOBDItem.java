package ganymedes01.aobd.items;

import ganymedes01.aobd.AOBD;
import ganymedes01.aobd.lib.Reference;
import ganymedes01.aobd.ore.OreFinder;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AOBDItem extends Item {

	@SideOnly(Side.CLIENT)
	private IIcon[] icon;

	private final String name;
	private final String base;

	public AOBDItem(String base, String name) {
		setMaxDamage(0);
		this.name = name;
		this.base = base;
		setHasSubtypes(true);
		setCreativeTab(AOBD.tab);
		setUnlocalizedName(Reference.MOD_ID + "." + name);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return String.format(StatCollector.translateToLocal("item.aobd." + base + ".name"), name);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack, int pass) {
		for (ItemStack ingot : OreDictionary.getOres("ingot" + name))
			if (ingot != null && ingot.getItem().hasEffect(stack, pass))
				return true;
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass) {
		return pass == 0 ? OreFinder.getOreColour(name) : super.getColorFromItemStack(stack, pass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int meta, int pass) {
		return pass == 0 ? icon[0] : icon[1];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		icon = new IIcon[2];
		icon[0] = reg.registerIcon(Reference.MOD_ID + ":" + base);
		icon[1] = reg.registerIcon(Reference.MOD_ID + ":" + base + "_overlay");
	}
}