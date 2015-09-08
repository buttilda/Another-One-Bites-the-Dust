package ganymedes01.aobd.recipes.modules;

import exnihilo.images.Resource;
import exnihilo.images.TextureDynamic;
import ganymedes01.aobd.blocks.AOBDBlock;
import ganymedes01.aobd.items.AOBDItem;
import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.lib.Reference;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ExNihilo extends RecipesModule {

	public ExNihilo() {
		super(CompatType.EX_NIHILO, "iron", "gold", "copper", "tin", "lead", "silver", "nickel", "platinum", "aluminum", "osmium");
	}

	@Override
	public void initOre(Ore ore) {
	}

	@SideOnly(Side.CLIENT)
	public static TextureAtlasSprite createIcon(AOBDItem item, String base, String template, TextureMap map) {
		String name = item.getBaseName();
		Ore ore = item.getOre();
		ResourceLocation baseTexture = Resource.getItemTextureLocation("exnihilo", base);
		ResourceLocation templateTexture = Resource.getItemTextureLocation("exnihilo", template);
		return createIcon(name, ore, baseTexture, templateTexture, map);
	}

	@SideOnly(Side.CLIENT)
	public static TextureAtlasSprite createIcon(AOBDBlock block, String base, String template, TextureMap map) {
		String name = block.getBaseName();
		Ore ore = block.getOre();
		ResourceLocation baseTexture = Resource.getBlockTextureLocation("exnihilo", base);
		ResourceLocation templateTexture = Resource.getBlockTextureLocation("exnihilo", template);
		return createIcon(name, ore, baseTexture, templateTexture, map);
	}

	@SideOnly(Side.CLIENT)
	private static TextureAtlasSprite createIcon(String name, Ore ore, ResourceLocation baseTexture, ResourceLocation templateTexture, TextureMap map) {
		float red = ore.getColour().getRed() / 255F;
		float green = ore.getColour().getGreen() / 255F;
		float blue = ore.getColour().getBlue() / 255F;
		return new TextureDynamic(Reference.MOD_ID + ":" + name + ore.name(), baseTexture, templateTexture, new exnihilo.registries.helpers.Color(red, green, blue, 1));
	}
}