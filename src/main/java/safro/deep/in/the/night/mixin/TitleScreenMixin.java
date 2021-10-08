package safro.deep.in.the.night.mixin;

import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import safro.deep.in.the.night.DeepInTheNight;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
/*    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;EDITION_TITLE_TEXTURE:Lnet/minecraft/util/Identifier;"))
    private Identifier addSpookyEdition() {
        return new Identifier(DeepInTheNight.MODID, "textures/gui/spooky_edition.png");
    } */

    @Shadow @Final
    private static final Identifier EDITION_TITLE_TEXTURE = new Identifier(DeepInTheNight.MODID, "textures/gui/spooky_edition.png");;
}
