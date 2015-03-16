package ganymedes01.aobd.blocks;

import ganymedes01.aobd.AOBD;
import ganymedes01.aobd.lib.Reference;
import ganymedes01.aobd.ore.Ore;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AOBDBlock extends Block {

	public static final List<String> BLOCKS_PREFIXES = Arrays.asList("block");

	private final Ore ore;
	private final String base;

	public AOBDBlock(String base, Ore ore) {
		super(Material.iron);
		this.ore = ore;
		this.base = base;
		setCreativeTab(AOBD.tab);
		setBlockName(Reference.MOD_ID + "." + base + ore);
		setBlockTextureName(Reference.MOD_ID + ":" + base);
	}

	@Override
	public String getLocalizedName() {
		String fullName = "tile.aobd." + base + ore.name() + ".name";
		String shortName = "tile.aobd." + base + ".name";
		return StatCollector.canTranslate(fullName) ? StatCollector.translateToLocal(fullName) : String.format(StatCollector.translateToLocal(shortName), ore.translatedName());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
		return ore.colour();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int meta) {
		return ore.colour();
	}
}