package ganymedes01.aobd.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class AOBDItemBlock extends ItemBlock {

	public AOBDItemBlock(Block block) {
		super(block);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return field_150939_a.getLocalizedName();
	}
}