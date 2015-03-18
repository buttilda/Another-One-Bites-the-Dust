package ganymedes01.aobd.recipes.modules;

import flaxbeard.steamcraft.tile.TileEntitySmasher;
import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;

public class Steamcraft extends RecipesModule {

	public Steamcraft() {
		super(CompatType.STEAMCRAFT, "iron", "gold", "copper", "zinc", "tin", "nickel", "silver", "lead", "aluminium", "osmium", "cobalt", "ardite");
	}

	@Override
	public void initOre(Ore ore) {
		TileEntitySmasher.REGISTRY.registerSmashable("ore" + ore.name(), getOreStack("dust", ore));
	}
}