package twilightforest.advancements;

import net.minecraft.advancements.AdvancementRequirements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public record CountRequirementsStrategy(int... sizes) implements AdvancementRequirements.Strategy {

	@Override
	public AdvancementRequirements create(Collection<String> strings) {
		String[][] requirements = new String[sizes.length][];
		List<String> list = new ArrayList<>(strings);
		int nextIndex = 0;
		for (int i = 0; i < sizes.length; i++) {
			requirements[i] = new String[sizes[i]];
			for (int j = 0; j < sizes[i]; j++) {
				requirements[i][j] = list.get(nextIndex);
				nextIndex++;
			}
		}
		return new AdvancementRequirements(requirements);
	}
}

