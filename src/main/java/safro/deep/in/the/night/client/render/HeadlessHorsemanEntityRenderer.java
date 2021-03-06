package safro.deep.in.the.night.client.render;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.PlayerHeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.util.Identifier;
import safro.deep.in.the.night.entity.HeadlessHorsemanEntity;

public class HeadlessHorsemanEntityRenderer extends LivingEntityRenderer<HeadlessHorsemanEntity, PlayerEntityModel<HeadlessHorsemanEntity>> {

    public HeadlessHorsemanEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new PlayerEntityModel(context.getPart(EntityModelLayers.PLAYER), false), 0.5F);
        this.addFeature(new ArmorFeatureRenderer(this, new BipedEntityModel(context.getPart(EntityModelLayers.PLAYER_SLIM_INNER_ARMOR)), new BipedEntityModel(context.getPart(EntityModelLayers.PLAYER_SLIM_OUTER_ARMOR))));
        this.addFeature(new PlayerHeldItemFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(HeadlessHorsemanEntity horseman) {
        return new Identifier("ditn:textures/entity/headless_horseman.png");
    }

    @Override
    protected boolean hasLabel(HeadlessHorsemanEntity entity) {
        return false;
    }
}
