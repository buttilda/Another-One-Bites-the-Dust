package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

import Reika.DragonAPI.Interfaces.OreType.OreRarity;
import Reika.RotaryCraft.API.ExtractAPI;
import Reika.RotaryCraft.Auxiliary.CustomExtractLoader;
import Reika.RotaryCraft.Auxiliary.CustomExtractLoader.CustomExtractEntry;

public class RotaryCraft extends RecipesModule {

	private static final List<Ore> usedOres = new LinkedList<Ore>();

	public RotaryCraft() {
		super(CompatType.ROTARYCRAFT, "gold", "iron", "coal", "redstone", "copper", "lead", "silver", "platinum", "nickel", "aluminium", "aluminum", "iridium", "tungsten", "osmium", "cobalt");
	}

	@Override
	public void initOre(Ore ore) {
		try {
			ExtractAPI.addCustomExtractEntry(ore.name(), OreRarity.COMMON, "INGOT", ore.name(), 1, 0, 0, null, "ore" + ore.name());
			usedOres.add(ore);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
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