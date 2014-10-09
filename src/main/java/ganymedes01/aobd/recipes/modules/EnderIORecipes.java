package ganymedes01.aobd.recipes.modules;

import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;
import cpw.mods.fml.common.event.FMLInterModComms;

public class EnderIORecipes extends RecipesModule {

	public static final String[] blacklist = { "iron", "gold", "copper", "tin", "lead", "silver", "nickel" };

	//@formatter:off
	private static final String xmlMessage ="<recipeGroup name=\"AOBD\">" + 
												"<recipe name=\"%sOre\" energyCost=\"%f\">" +
      										 		"<input>" +
      										 			"<itemStack oreDictionary=\"ore%s\" />" +
      										 		"</input>" +
      										 		"<output>" +
      										 		"<itemStack oreDictionary=\"dust%s\" number=\"2\" />" +      
        												"<itemStack oreDictionary=\"dust%s\" number=\"1\" chance=\"0.1\" />" +       
        												"<itemStack modID=\"minecraft\" itemName=\"cobblestone\" chance=\"0.15\"/>" +
        											"</output>" +
        										"</recipe>" + 
        									"</recipeGroup>";
	//@formatter:on

	public static void init() {
		label: for (Ore ore : Ore.ores)
			if (ore.shouldEnderIO()) {
				for (String bEntry : blacklist)
					if (ore.name().equalsIgnoreCase(bEntry))
						continue label;

				addSAGMillRecipe(ore.name(), (float) ore.energy(360.0), ore.extra());
			}
	}

	private static void addSAGMillRecipe(String input, float energy, String extra) {
		FMLInterModComms.sendMessage("EnderIO", "recipe:sagmill", String.format(xmlMessage, input, energy, input, input, extra));
	}
}