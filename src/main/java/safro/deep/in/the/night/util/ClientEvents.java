package safro.deep.in.the.night.util;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.client.MinecraftClient;

public class ClientEvents {
    private static MinecraftClient currentClient;

    public static void init() {
        ClientLifecycleEvents.CLIENT_STARTED.register((client -> client = currentClient));

        ClientLifecycleEvents.CLIENT_STARTED.register((client -> currentClient = null));
    }

    public static MinecraftClient getCurrentClient() {
        return currentClient;
    }
}
