package safro.deep.in.the.night.mixin;

import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import safro.deep.in.the.night.DeepInTheNight;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {

    @Shadow @Final
    private static final Identifier EDITION_TITLE_TEXTURE = new Identifier(DeepInTheNight.MODID, "textures/gui/spooky_edition.png");
}
