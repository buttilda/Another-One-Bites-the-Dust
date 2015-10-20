package ganymedes01.aobd.items;

import ganymedes01.aobd.AOBD;
import ganymedes01.aobd.client.ItemOreRenderer;
import ganymedes01.aobd.lib.Reference;
import ganymedes01.aobd.ore.Ore;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AOBDItem extends Item {

	@SideOnly(Side.CLIENT)
	private IIcon[] icon;

	private Boolean hasEffect = null;

	protected final Ore ore;
	protected final String base;

	public AOBDItem(String base, Ore ore) {
		this.ore = ore;
		this.base = base;
		setCreativeTab(AOBD.tab);
		setTextureName(Reference.MOD_ID + ":" + base);
		setUnlocalizedName(Reference.MOD_ID + "." + base + ore);
	}

	protected String getFullName() {
		return "item." + Reference.MOD_ID + "." + base + ore.name() + ".name";
	}

	protected String getShortName() {
		return "item." + Reference.MOD_ID + "." + base + ".name";
	}

	public String getBaseName() {
		return base;
	}

	public Ore getOre() {
		return ore;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		String fullName = getFullName();
		String shortName = getShortName();
		return StatCollector.canTranslate(fullName) ? StatCollector.translateToLocal(fullName) : String.format(StatCollector.translateToLocal(shortName), ore.translatedName());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass) {
		return pass == 0 ? ore.colour() : super.getColorFromItemStack(stack, pass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack, int pass) {
		if (hasEffect == null) {
			hasEffect = false;
			for (ItemStack ingot : OreDictionary.getOres("ingot" + ore.name()))
				if (ingot != null && ingot.getItem().hasEffect(stack, pass))
					hasEffect = true;
		}

		return pass == 0 ? hasEffect : false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
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
		icon[0] = reg.registerIcon(getIconString());
		icon[1] = reg.registerIcon(getIconString() + "_overlay");
	}

	@SideOnly(Side.CLIENT)
	public IItemRenderer getSpecialRenderer() {
		if ("ore".equals(base))
			return ItemOreRenderer.INSTANCE;
		return null;
	}
}