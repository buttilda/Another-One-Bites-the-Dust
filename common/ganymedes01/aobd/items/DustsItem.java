package ganymedes01.aobd.items;

import ganymedes01.aobd.AOBD;
import ganymedes01.aobd.lib.Reference;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class DustsItem extends Item {

	@SideOnly(Side.CLIENT)
	private Icon[] icon;

	private final int NUM = 20;

	public DustsItem() {
		super(AOBD.dustsID);
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(CreativeTabs.tabMaterials);
		setUnlocalizedName(Reference.MOD_ID + "." + "dustsItem");
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName() + stack.getItemDamage();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int meta) {
		return icon[meta];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int itemID, CreativeTabs tabs, List list) {
		if (AOBD.enableIC2)
			for (int i = 5; i <= 13; i++)
				list.add(new ItemStack(itemID, 1, i));

		if (AOBD.enableMekanism)
			for (int i = 14; i <= 19; i++)
				list.add(new ItemStack(itemID, 1, i));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		icon = new Icon[NUM];

		for (int i = 5; i < icon.length; i++)
			icon[i] = reg.registerIcon(Reference.MOD_ID + ":dustsItem" + i);
	}

	public static ItemStack getItem(String name) {
		return getItem(name, 1);
	}

	public static ItemStack getItem(String name, int size) {
		int meta;

		if (name.matches("crushedCobalt"))
			meta = 5;
		else if (name.matches("crushedArdite"))
			meta = 6;
		else if (name.matches("crushedPurifiedCobalt"))
			meta = 7;
		else if (name.matches("crushedPurifiedArdite"))
			meta = 8;
		else if (name.matches("dustTinyCobalt"))
			meta = 9;
		else if (name.matches("dustTinyArdite"))
			meta = 10;
		else if (name.matches("crushedAluminium") || name.matches("crushedAluminum"))
			meta = 11;
		else if (name.matches("crushedPurifiedAluminium") || name.matches("crushedPurifiedAluminum"))
			meta = 12;
		else if (name.matches("dustTinyAluminium") || name.matches("dustTinyAluminum"))
			meta = 13;
		else if (name.matches("clumpArdite"))
			meta = 14;
		else if (name.matches("clumpCobalt"))
			meta = 15;
		else if (name.matches("clumpAluminium") || name.matches("clumpAluminum"))
			meta = 16;
		else if (name.matches("dustDirtyArdite"))
			meta = 17;
		else if (name.matches("dustDirtyCobalt"))
			meta = 18;
		else if (name.matches("dustDirtyAluminium") || name.matches("dustDirtyAluminum"))
			meta = 19;
		else
			return null;

		return new ItemStack(AOBD.dusts.itemID, size, meta);
	}
}