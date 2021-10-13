package safro.deep.in.the.night.registry;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import safro.deep.in.the.night.DeepInTheNight;
import safro.deep.in.the.night.client.model.CrowEntityModel;
import safro.deep.in.the.night.client.model.ScarecrowEntityModel;
import safro.deep.in.the.night.client.render.*;

public class RenderModelRegistry {

    public static final EntityModelLayer SCARECROW_LAYER = new EntityModelLayer(new Identifier(DeepInTheNight.MODID, "scarecrow_layer"), "scarecrow_layer");
    public static final EntityModelLayer CROW_LAYER = new EntityModelLayer(new Identifier(DeepInTheNight.MODID, "crow_layer"), "crow_layer");

    public static void init() {
        EntityRendererRegistry.INSTANCE.register(EntityRegistry.HEADLESS_HORSEMAN, HeadlessHorsemanEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityRegistry.SCARECROW, ScarecrowEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityRegistry.VAMPIRE, VampireEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityRegistry.CROW, CrowEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityRegistry.SHADOW_REAPER, ShadowReaperEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityRegistry.PUMPKIN_BOMB, (context) -> new FlyingItemEntityRenderer(context));

        EntityModelLayerRegistry.registerModelLayer(SCARECROW_LAYER, ScarecrowEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(CROW_LAYER, CrowEntityModel::getTexturedModelData);

        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BROKEN_DOLL, RenderLayer.getCutout());
    }
}
