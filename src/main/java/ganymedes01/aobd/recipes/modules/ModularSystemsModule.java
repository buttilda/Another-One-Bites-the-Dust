package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;

public class ModularSystemsModule extends RecipesModule {

	public ModularSystemsModule() {
		super(CompatType.MODULAR_SYSTEMS, "iron", "gold");
	}

	@Override
	public void initOre(Ore ore) {
	}
}