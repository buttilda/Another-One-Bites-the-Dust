package ganymedes01.aobd.items;

import ganymedes01.aobd.AOBD;
import ganymedes01.aobd.lib.Metals;
import ganymedes01.aobd.lib.Reference;

import java.util.HashMap;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class DustsItem extends Item {

	@SideOnly(Side.CLIENT)
	private IIcon[] icon;

	private static final HashMap<String, Integer> map = new HashMap<String, Integer>();

	public DustsItem() {
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(CreativeTabs.tabMaterials);
		setUnlocalizedName(Reference.MOD_ID + "." + "dustsItem");

		map.put("crushedCobalt", 5);
		map.put("crushedPurifiedCobalt", 7);
		map.put("dustTinyCobalt", 9);
		map.put("clumpCobalt", 15);
		map.put("dustDirtyCobalt", 18);

		map.put("crushedArdite", 6);
		map.put("crushedPurifiedArdite", 8);
		map.put("dustTinyArdite", 10);
		map.put("clumpArdite", 14);
		map.put("dustDirtyArdite", 17);

		map.put("crushedAluminum", 11);
		map.put("crushedPurifiedAluminum", 12);
		map.put("dustTinyAluminum", 13);
		map.put("clumpAluminum", 16);
		map.put("dustDirtyAluminum", 19);

		map.put("crushedNickel", 0);
		map.put("crushedPurifiedNickel", 1);
		map.put("dustTinyNickel", 2);
		map.put("clumpNickel", 3);
		map.put("dustDirtyNickel", 4);

		map.put("dustTinyPlatinum", 20);

		map.put("crushedFzDarkIron", 21);
		map.put("crushedPurifiedFzDarkIron", 22);
		map.put("dustTinyFzDarkIron", 23);
		map.put("clumpFzDarkIron", 24);
		map.put("dustDirtyFzDarkIron", 25);
		map.put("dustFzDarkIron", 26);

		map.put("dustCobalt", 27);
		map.put("dustArdite", 28);
		map.put("dustAluminum", 29);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName() + stack.getItemDamage();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return icon[meta];
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("all")
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		for (Metals metal : Metals.values())
			if (metal.shouldUse()) {
				String name = metal.name();
				if (AOBD.enableIC2) {
					list.add(new ItemStack(item, 1, map.get("crushed" + name)));
					list.add(new ItemStack(item, 1, map.get("crushedPurified" + name)));
					list.add(new ItemStack(item, 1, map.get("dustTiny" + name)));
					if (AOBD.enableTE3)
						list.add(new ItemStack(item, 1, map.get("dustTinyPlatinum")));
				}
				if (AOBD.enableMekanism) {
					list.add(new ItemStack(item, 1, map.get("clump" + name)));
					list.add(new ItemStack(item, 1, map.get("dustDirty" + name)));
				}
				if (AOBD.enableFZ && AOBD.enableTE3)
					list.add(new ItemStack(item, 1, map.get("dust" + Metals.FzDarkIron.name())));
			}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		icon = new IIcon[map.size()];

		for (int i = 0; i < icon.length; i++)
			icon[i] = reg.registerIcon(Reference.MOD_ID + ":dustsItem" + i);
	}

	public static ItemStack getItem(String name) {
		return getItem(name, 1);
	}

	public static ItemStack getItem(String name, int size) {
		return new ItemStack(AOBD.dusts, size, DustsItem.map.get(name));
	}
}