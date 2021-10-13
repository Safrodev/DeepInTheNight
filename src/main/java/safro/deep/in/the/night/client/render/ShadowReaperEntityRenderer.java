package safro.deep.in.the.night.client.render;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.PlayerHeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.util.Identifier;
import safro.deep.in.the.night.entity.ShadowReaperEntity;

public class ShadowReaperEntityRenderer extends LivingEntityRenderer<ShadowReaperEntity, PlayerEntityModel<ShadowReaperEntity>> {

    public ShadowReaperEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new PlayerEntityModel(context.getPart(EntityModelLayers.PLAYER), false), 0.5F);
        this.addFeature(new ArmorFeatureRenderer(this, new BipedEntityModel(context.getPart(EntityModelLayers.PLAYER_SLIM_INNER_ARMOR)), new BipedEntityModel(context.getPart(EntityModelLayers.PLAYER_SLIM_OUTER_ARMOR))));
        this.addFeature(new PlayerHeldItemFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(ShadowReaperEntity reaper) {
        return new Identifier("ditn:textures/entity/shadow_reaper.png");
    }

    @Override
    protected boolean hasLabel(ShadowReaperEntity entity) {
        return false;
    }
}
