package safro.deep.in.the.night.registry;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import safro.deep.in.the.night.DeepInTheNight;

public class SoundRegistry {

    public static SoundEvent CROW_AMBIENT = create("crow");
    public static SoundEvent GHOST_SCARE = create("ghost_scare");
    public static SoundEvent DEEP_SCREECH = create("deep_screech");

    static SoundEvent create(String id) {
        SoundEvent sound = new SoundEvent(new Identifier(DeepInTheNight.MODID, id));
        Registry.register(Registry.SOUND_EVENT, new Identifier(DeepInTheNight.MODID, id), sound);
        return sound;
    }
}
