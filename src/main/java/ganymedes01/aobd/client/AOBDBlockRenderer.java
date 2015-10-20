package ganymedes01.aobd.client;

import ganymedes01.aobd.blocks.AOBDBlock;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class AOBDBlockRenderer implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks renderer) {
		AOBDBlock aobdBlock = (AOBDBlock) block;
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

		aobdBlock.isRenderingOverlay = false;
		int colour = block.getRenderColor(meta);
		float r = (colour >> 16 & 255) / 255.0F;
		float g = (colour >> 8 & 255) / 255.0F;
		float b = (colour & 255) / 255.0F;
		GL11.glColor3f(r, g, b);
		renderer.setRenderBounds(0, 0, 0, 1, 1, 1);
		renderBlock(block, meta, renderer);

		if (AOBDBlock.BLOCKS_WITH_OVERLAYS.contains(aobdBlock.getBaseName())) {
			GL11.glColor3f(1, 1, 1);
			aobdBlock.isRenderingOverlay = true;
			renderer.setRenderBounds(0, 0, 0, 1, 1, 1);
			renderBlock(block, meta, renderer);
		}
	}

	private void renderBlock(Block block, int meta, RenderBlocks renderer) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, meta));
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, meta));
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, meta));
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, meta));
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, meta));
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, meta));
		tessellator.draw();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		AOBDBlock aobdBlock = (AOBDBlock) block;

		aobdBlock.isRenderingOverlay = false;
		boolean flag = renderer.renderStandardBlock(block, x, y, z);

		if (AOBDBlock.BLOCKS_WITH_OVERLAYS.contains(aobdBlock.getBaseName())) {
			aobdBlock.isRenderingOverlay = true;
			flag |= renderer.renderStandardBlock(block, x, y, z);
		}

		return flag;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return AOBDBlock.RENDER_ID;
	}
}