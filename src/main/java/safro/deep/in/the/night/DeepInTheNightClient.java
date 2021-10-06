package safro.deep.in.the.night;

import net.fabricmc.api.ClientModInitializer;
import safro.deep.in.the.night.registry.RenderModelRegistry;

public class DeepInTheNightClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        RenderModelRegistry.init();
    }
}
