package ganymedes01.aobd.ore;

import ganymedes01.aobd.AOBD;
import ganymedes01.aobd.items.AOBDItem;
import ganymedes01.aobd.recipes.modules.FactorizationRecipes;
import ganymedes01.aobd.recipes.modules.MekanismRecipes;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.Set;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class OreFinder {

	public static final HashMap<String, Item> itemMap = new HashMap<String, Item>();
	public static final HashMap<String, Color> oreColourMap = new HashMap<String, Color>();

	public static int getOreColour(String oreName) {
		Color colour = oreColourMap.get(oreName);
		return colour != null ? colour.getRGB() : Color.WHITE.getRGB();
	}

	private static Collection<String> getMetalsWithPrefixes(String prefix1, String prefix2) {
		Set<String> ores = new LinkedHashSet<String>();
		for (String name : OreDictionary.getOreNames())
			if (name.startsWith(prefix1) && !OreDictionary.getOres(name).isEmpty()) {
				String oreName = name.substring(prefix1.length());
				for (String n : OreDictionary.getOreNames())
					if (n.startsWith(prefix2) && n.endsWith(oreName) && !OreDictionary.getOres(n).isEmpty())
						ores.add(oreName);
			}
		if (ores.contains("Aluminum") && ores.contains("Aluminium"))
			ores.remove("Aluminum");
		if (ores.contains("AluminumBrass") && ores.contains("AluminiumBrass"))
			ores.remove("AluminumBrass");

		return Collections.unmodifiableSet(ores);
	}

	public static void preInit() {
		for (String ore : getMetalsWithPrefixes("ore", "ingot")) {
			oreColourMap.put(ore, Color.BLACK);
			Ore.newOre(ore);
		}
	}

	public static void initColours() {
		try {
			for (String ore : oreColourMap.keySet()) {
				Color colour = getColour(ore);
				if (colour != null)
					oreColourMap.put(ore, colour);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void init() {
		generateItems("dust");
		if (AOBD.enableIC2) {
			generateItems("crushed");
			generateItems("crushedPurified");
			generateItems("dustTiny");
		}
		if (AOBD.enableThaumcraft)
			generateItems("cluster");
		if (AOBD.enableFactorization) {
			generateItems("dirtyGravel", FactorizationRecipes.blacklist);
			generateItems("reduced", FactorizationRecipes.blacklist);
			generateItems("cleanGravel", FactorizationRecipes.blacklist);
			generateItems("crystalline", FactorizationRecipes.blacklist);
		}
		if (AOBD.enableMekanism) {
			generateItems("dustDirty", MekanismRecipes.blacklist);
			generateItems("shard", MekanismRecipes.blacklist);
			generateItems("crystal", MekanismRecipes.blacklist);
			generateItems("clump", MekanismRecipes.blacklist);
		}

		String[] items = AOBD.userDefinedItems.trim().split(",");
		if (items.length > 0)
			for (String item : items)
				if (item.length() > 0)
					generateItems(item.trim());
	}

	public static void addCustomMetal(String name, Color colour, String... prefixes) {
		if (!oreColourMap.containsKey(name)) {
			oreColourMap.put(name, colour);
			for (String prefix : prefixes) {
				String pre = prefix.trim();
				registerOre(pre + name, new AOBDItem(pre, name));
			}
		}
	}

	private static void generateItems(String orePrefix, String... blacklist) {
		label: for (Entry<String, Color> entry : oreColourMap.entrySet()) {
			String oreName = entry.getKey();
			if (blacklist != null && blacklist.length > 0)
				for (String bEntry : blacklist)
					if (oreName.equalsIgnoreCase(bEntry))
						continue label;
			registerOre(orePrefix + oreName, new AOBDItem(orePrefix, oreName));
		}
	}

	private static void registerOre(String ore, Item item) {
		if (OreDictionary.getOres(ore).isEmpty()) {
			GameRegistry.registerItem(item, ore);
			OreDictionary.registerOre(ore, item);
			itemMap.put(ore, item);
		}
	}

	private static int getStackColour(ItemStack stack, int pass) {
		if (Loader.isModLoaded("gregtech"))
			try {
				Class<?> cls = Class.forName("gregtech.api.items.GT_MetaGenerated_Item");
				if (cls.isAssignableFrom(stack.getItem().getClass())) {
					Method m = cls.getMethod("getRGBa", ItemStack.class);
					short[] rgba = (short[]) m.invoke(stack.getItem(), stack);
					return new Color(rgba[0], rgba[1], rgba[2], rgba[3]).getRGB();
				}
			} catch (Exception e) {
			}
		return stack.getItem().getColorFromItemStack(stack, pass);
	}

	private static Color getColour(String oreName) throws IOException {
		ArrayList<ItemStack> ores = OreDictionary.getOres("ingot" + oreName);
		if (ores.isEmpty())
			return null;

		float red = 0;
		float green = 0;
		float blue = 0;
		ArrayList<Color> colours = new ArrayList<Color>();
		for (ItemStack stack : ores) {
			ResourceLocation res = getIconResource(stack);
			if (res == null)
				continue;
			BufferedImage texture = ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(res).getInputStream());
			Color texColour = getAverageColour(texture);
			colours.add(texColour);
			for (int pass = 0; pass < stack.getItem().getRenderPasses(stack.getItemDamage()); pass++) {
				int c = getStackColour(stack, pass);
				if (c != 0xFFFFFF) {
					colours.add(new Color(c));
					colours.remove(texColour);
				}
			}
		}

		for (Color c : colours) {
			red += c.getRed();
			green += c.getGreen();
			blue += c.getBlue();
		}
		float count = colours.size();
		return new Color((int) (red / count), (int) (green / count), (int) (blue / count)).brighter();
	}

	private static Color getAverageColour(BufferedImage image) {
		float red = 0;
		float green = 0;
		float blue = 0;
		float count = 0;
		for (int i = 0; i < image.getWidth(); i++)
			for (int j = 0; j < image.getHeight(); j++) {
				Color c = new Color(image.getRGB(i, j));
				if (c.getAlpha() <= 10 || c.getRed() <= 10 && c.getGreen() <= 10 && c.getBlue() <= 10)
					continue;
				red += c.getRed();
				green += c.getGreen();
				blue += c.getBlue();
				count++;
			}

		return new Color((int) (red / count), (int) (green / count), (int) (blue / count));
	}

	private static String getIconName(ItemStack stack) {
		IIcon icon = stack.getItem().getIconFromDamage(stack.getItemDamage());
		if (icon != null)
			return icon.getIconName();
		return null;
	}

	private static ResourceLocation getIconResource(ItemStack stack) {
		String iconName = getIconName(stack);
		if (iconName == null)
			return null;

		String string = "minecraft";

		int colonIndex = iconName.indexOf(58);
		if (colonIndex >= 0) {
			if (colonIndex > 1)
				string = iconName.substring(0, colonIndex);

			iconName = iconName.substring(colonIndex + 1, iconName.length());
		}

		string = string.toLowerCase();
		iconName = "textures/items/" + iconName + ".png";
		return new ResourceLocation(string, iconName);
	}
}