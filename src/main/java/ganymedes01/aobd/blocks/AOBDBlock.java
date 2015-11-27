package ganymedes01.aobd.blocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.aobd.AOBD;
import ganymedes01.aobd.lib.Reference;
import ganymedes01.aobd.ore.Ore;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;

public class AOBDBlock extends Block {

	public static final int RENDER_ID = RenderingRegistry.getNextAvailableRenderId();

	public static final List<AOBDBlock> ALL_BLOCKS = new ArrayList<AOBDBlock>();
	public static final Map<String, Float> BLOCKS_PREFIXES = new HashMap<String, Float>();
	public static final Map<String, Material> BLOCKS_MATERIALS = new HashMap<String, Material>();
	public static final Map<String, Block.SoundType> BLOCKS_SOUNDS = new HashMap<String, Block.SoundType>();
	public static final List<String> BLOCKS_WITH_OVERLAYS = new ArrayList<String>();
	static {
		BLOCKS_PREFIXES.put("block", 4F);
		BLOCKS_PREFIXES.put("oreSand", 0.4F);
		BLOCKS_PREFIXES.put("oreDust", 0.6F);
		BLOCKS_PREFIXES.put("oreGravel", 0.8F);
		BLOCKS_PREFIXES.put("oreNetherGravel", 0.8F);

		BLOCKS_MATERIALS.put("block", Material.iron);
		BLOCKS_MATERIALS.put("oreSand", Material.sand);
		BLOCKS_MATERIALS.put("oreDust", Material.sand);
		BLOCKS_MATERIALS.put("oreGravel", Material.sand);
		BLOCKS_MATERIALS.put("oreNetherGravel", Material.sand);

		BLOCKS_SOUNDS.put("block", Block.soundTypeMetal);
		BLOCKS_SOUNDS.put("oreSand", Block.soundTypeSand);
		BLOCKS_SOUNDS.put("oreDust", Block.soundTypeSand);
		BLOCKS_SOUNDS.put("oreGravel", Block.soundTypeGravel);
		BLOCKS_SOUNDS.put("oreNetherGravel", Block.soundTypeGravel);

		BLOCKS_WITH_OVERLAYS.add("oreGravel");
		BLOCKS_WITH_OVERLAYS.add("oreNetherGravel");
	}

	public boolean isRenderingOverlay = false;
	protected final Ore ore;
	protected final String base;
	@SideOnly(Side.CLIENT)
	private IIcon overlay;

	public AOBDBlock(String base, Ore ore) {
		this(BLOCKS_MATERIALS.get(base), base, ore);
	}

	public AOBDBlock(Material material, String base, Ore ore) {
		super(material);
		this.ore = ore;
		this.base = base;

		Float hardness = BLOCKS_PREFIXES.get(base);
		setHardness(hardness == null ? 1.0F : hardness);

		setStepSound(BLOCKS_SOUNDS.get(base));

		setCreativeTab(AOBD.tab);
		setBlockName(Reference.MOD_ID + "." + base + ore);
		setBlockTextureName(Reference.MOD_ID + ":" + base);
		ALL_BLOCKS.add(this);
	}

	protected String getFullName() {
		return "tile." + Reference.MOD_ID + "." + base + ore.name() + ".name";
	}

	protected String getShortName() {
		return "tile." + Reference.MOD_ID + "." + base + ".name";
	}

	public String getBaseName() {
		return base;
	}

	public Ore getOre() {
		return ore;
	}

	@Override
	public int getRenderType() {
		return RENDER_ID;
	}

	@Override
	public String getLocalizedName() {
		String fullName = getFullName();
		String shortName = getShortName();
		return StatCollector.canTranslate(fullName) ? StatCollector.translateToLocal(fullName) : String.format(StatCollector.translateToLocal(shortName), ore.translatedName());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return isRenderingOverlay ? overlay : super.getIcon(side, meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg);
		if (BLOCKS_WITH_OVERLAYS.contains(base))
			overlay = reg.registerIcon(getTextureName() + "_overlay");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
		return isRenderingOverlay ? super.colorMultiplier(world, x, y, z) : ore.colour();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int meta) {
		return isRenderingOverlay ? super.getRenderColor(meta) : ore.colour();
	}
}