package ganymedes01.aobd.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ganymedes01.aobd.ore.Ore;

public class AOBDAddonManager {

	private static List<IAOBDAddon> addons = new ArrayList<IAOBDAddon>();

	public static void registerAddon(IAOBDAddon addon) {
		addons.add(addon);
	}

	public static void passOreListToAddons(Collection<Ore> ores) {
		for (IAOBDAddon addon : addons)
			addon.receiveOreList(ores);
	}

	public static void notifyColourCreation() {
		for (IAOBDAddon addon : addons)
			addon.notifyColourCreation();
	}
}