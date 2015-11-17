package ganymedes01.aobd.client;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.aobd.items.AOBDGlassBottle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.fluids.FluidStack;

@SideOnly(Side.CLIENT)
public class ItemGlassBottleRenderer implements IItemRenderer {

	public static final ItemGlassBottleRenderer INSTANCE = new ItemGlassBottleRenderer();

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type != ItemRenderType.FIRST_PERSON_MAP;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return type == ItemRenderType.ENTITY;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
		if (stack == null || !(stack.getItem() instanceof AOBDGlassBottle))
			return;

		AOBDGlassBottle glassBottle = (AOBDGlassBottle) stack.getItem();

		switch (type) {
			case ENTITY:
				GL11.glScalef(2.0F, 2.0F, 2.0F);
				if (RenderItem.renderInFrame) {
					GL11.glScalef(6.3F / 16F, 6.3F / 16F, 6.3F / 16F);
					GL11.glRotatef(-90, 0, 1, 0);
					GL11.glTranslatef(-8 / 16F, -7 / 16F, 0.5F / 16F);
				} else {
					GL11.glScalef(7 / 16F, 7 / 16F, 7 / 16F);
					GL11.glTranslatef(-8 / 16F, -3 / 16F, 0);
				}
				break;
			case INVENTORY:
				GL11.glRotatef(180, 0, 0, 1);
				GL11.glTranslatef(-16, -16, 0);
				GL11.glScalef(16, 16, 16);
				break;
			default:
				break;
		}

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		// Render bottle
		renderItemIn2D(stack);

		GL11.glScalef(0.0625F, 0.0625F, 0.0625F);

		// Render fluid
		FluidStack fluid = glassBottle.getFluid();
		if (fluid != null) {
			glColour(fluid.getFluid().getColor(fluid));
			IIcon fluidicon = fluid.getFluid().getStillIcon();
			if (fluidicon == null)
				fluidicon = ((TextureMap) Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationBlocksTexture)).getAtlasSprite("missingno");

			Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);

			double offset = -0.999;
			if (type == ItemRenderType.INVENTORY)
				offset = -1.001;

			GL11.glPushMatrix();
			drawFace(4, 2, fluidicon, 7, 6, offset, -1);
			drawFace(4, 2, fluidicon, 7, 6, -0.001, 1);
			drawTop(5, 0, 11, 1, 2 + 6 - 0.001, fluidicon);
			GL11.glPopMatrix();
		}

		GL11.glDisable(GL11.GL_BLEND);
	}

	private void drawTop(double minX, double minZ, double maxX, double maxZ, double height, IIcon icon) {
		Tessellator tess = Tessellator.instance;

		double minU = icon.getInterpolatedU(minX);
		double maxU = icon.getInterpolatedU(maxX);
		double minV = icon.getInterpolatedV(minZ);
		double maxV = icon.getInterpolatedV(maxZ);

		tess.startDrawingQuads();
		tess.setNormal(0, 1, 0);
		tess.addVertexWithUV(maxX, height, maxZ - 1, maxU, maxV);
		tess.addVertexWithUV(maxX, height, minZ - 1, maxU, minV);
		tess.addVertexWithUV(minX, height, minZ - 1, minU, minV);
		tess.addVertexWithUV(minX, height, maxZ - 1, minU, maxV);
		tess.draw();
	}

	private void drawFace(float startU, float startV, IIcon icon, float endU, float endV, double z, int normalZ) {
		float top = icon.getInterpolatedV(16 - endV);
		float bottom = icon.getMaxV();
		float left = icon.getMinU();
		float right = icon.getInterpolatedU(endU);
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.setNormal(0.0F, 0.0F, normalZ);
		if (normalZ < 0) {
			tess.addVertexWithUV(startU + 0, startV + endV, z, left, bottom);
			tess.addVertexWithUV(startU + endU, startV + endV, z, right, bottom);
			tess.addVertexWithUV(startU + endU, startV + 0, z, right, top);
			tess.addVertexWithUV(startU + 0, startV + 0, z, left, top);
		} else {
			tess.addVertexWithUV(startU + 0, startV + 0, z, left, top);
			tess.addVertexWithUV(startU + endU, startV + 0, z, right, top);
			tess.addVertexWithUV(startU + endU, startV + endV, z, right, bottom);
			tess.addVertexWithUV(startU + 0, startV + endV, z, left, bottom);
		}
		tess.draw();
	}

	private void renderItemIn2D(ItemStack stack) {
		IIcon icon = stack.getIconIndex();
		GL11.glColor3f(1F, 1F, 1F);
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationItemsTexture);
		ItemRenderer.renderItemIn2D(Tessellator.instance, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
	}

	private void glColour(int colour) {
		float r = (colour >> 16 & 255) / 255F;
		float g = (colour >> 8 & 255) / 255F;
		float b = (colour & 255) / 255F;

		GL11.glColor3f(r, g, b);
	}
}