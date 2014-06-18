package ganymedes01.aobd.ore;

import ganymedes01.aobd.AOBD;
import ganymedes01.aobd.items.AOBDItem;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
			for (String ore : oreColourMap.keySet())
				oreColourMap.put(ore, getColour(ore));
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

	private static void generateItems(String orePrefix) {
		for (Entry<String, Color> entry : oreColourMap.entrySet()) {
			String oreName = entry.getKey();
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

	private static Color getColour(String oreName) throws IOException {
		float red = 0;
		float green = 0;
		float blue = 0;
		ArrayList<Color> colours = new ArrayList<Color>();
		for (ItemStack stack : OreDictionary.getOres("ingot" + oreName)) {
			ResourceLocation res = getIconResource(stack);
			if (res == null)
				continue;
			BufferedImage texture = ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(res).getInputStream());
			colours.add(getAverageColour(texture));
			for (int pass = 0; pass < stack.getItem().getRenderPasses(stack.getItemDamage()); pass++) {
				int c = stack.getItem().getColorFromItemStack(stack, pass);
				if (c != 16777215)
					colours.add(new Color(c));
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
				if (c.getRed() <= 10 && c.getGreen() <= 10 && c.getBlue() <= 10)
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