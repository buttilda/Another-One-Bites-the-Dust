package ganymedes01.aobd.recipes.modules;

import cpw.mods.fml.common.event.FMLInterModComms;
import ganymedes01.aobd.lib.CompatType;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.recipes.RecipesModule;
import net.minecraftforge.oredict.OreDictionary;

public class EnderIO extends RecipesModule {

	//@formatter:off
	private static final String xmlMessage = "<recipeGroup name=\"AOBD\">" + 
                                                 "<recipe name=\"%sOre\" energyCost=\"%d\">" +
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

	public EnderIO() {
		super(CompatType.ENDERIO, "iron", "gold");
	}

	@Override
	public void initOre(Ore ore) {
		addSAGMillRecipe(ore.name(), (int) ore.energy(3600), ore.extra());
	}

	private static void addSAGMillRecipe(String input, int energy, String extra) {
		if (OreDictionary.getOres("dust" + extra).isEmpty())
			extra = input;

		FMLInterModComms.sendMessage("EnderIO", "recipe:sagmill", String.format(xmlMessage, input, energy, input, input, extra));
	}
}