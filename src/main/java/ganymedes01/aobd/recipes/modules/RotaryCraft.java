package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

import Reika.DragonAPI.Interfaces.Registry.OreType.OreRarity;
import Reika.DragonAPI.ModRegistry.ModOreList;
import Reika.RotaryCraft.API.ExtractAPI;
import Reika.RotaryCraft.Auxiliary.CustomExtractLoader;
import Reika.RotaryCraft.Auxiliary.CustomExtractLoader.CustomExtractEntry;

public class RotaryCraft extends RecipesModule {

	private static final List<Ore> usedOres = new LinkedList<Ore>();

	public RotaryCraft() {
		super(CompatType.ROTARYCRAFT, "gold", "iron", "redstone", "diamond", "emerald", "lapis", "quartz", "aluminium", "aluminum", "yellorium", "fzdarkiron");
	}

	@Override
	public void initOre(Ore ore) {
		for (ModOreList oreList : ModOreList.values())
			if (oreList.name().toLowerCase().equals(ore.name().toLowerCase()))
				return;

		ExtractAPI.addCustomExtractEntry(ore.name(), OreRarity.COMMON, "INGOT", ore.name(), 1, 0, 0, null, "ore" + ore.name());
		usedOres.add(ore);
	}

	public static void setOresColour() {
		List<CustomExtractEntry> list = CustomExtractLoader.instance.getEntries();
		for (int i = 0; i < list.size(); i++) {
			CustomExtractEntry entry = list.get(i);
			for (Ore ore : usedOres)
				if (ore.name().equals(entry.displayName)) {
					for (int index : new int[] { 1, 2 })
						try {
							Field field = CustomExtractEntry.class.getDeclaredField("color" + index);

							Field modifiersField = Field.class.getDeclaredField("modifiers");
							modifiersField.setAccessible(true);
							modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

							field.set(entry, index == 1 ? ore.getColour().brighter().brighter().getRGB() : ore.getColour().darker().darker().getRGB());
						} catch (Exception e) {
						}
					break;
				}
		}
	}
}