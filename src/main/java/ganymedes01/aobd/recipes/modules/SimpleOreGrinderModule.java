package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;

public class SimpleOreGrinderModule extends RecipesModule {

	public SimpleOreGrinderModule() {
		super(CompatType.SIMPLE_ORE_GRINDER);
	}

	@Override
	public void initOre(Ore ore) {
	}
}