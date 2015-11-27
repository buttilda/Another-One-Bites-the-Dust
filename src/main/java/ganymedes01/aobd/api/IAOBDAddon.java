package ganymedes01.aobd.api;

import java.util.Collection;

import ganymedes01.aobd.ore.Ore;

public interface IAOBDAddon {

	void receiveOreList(Collection<Ore> ores);

	void notifyColourCreation();
}