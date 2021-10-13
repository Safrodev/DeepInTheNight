package safro.deep.in.the.night.client.render;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.PlayerHeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.util.Identifier;
import safro.deep.in.the.night.entity.HeadlessHorsemanEntity;
import safro.deep.in.the.night.entity.VampireEntity;

public class VampireEntityRenderer extends LivingEntityRenderer<VampireEntity, PlayerEntityModel<VampireEntity>> {

    public VampireEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new PlayerEntityModel(context.getPart(EntityModelLayers.PLAYER), false), 0.5F);
        this.addFeature(new ArmorFeatureRenderer(this, new BipedEntityModel(context.getPart(EntityModelLayers.PLAYER_SLIM_INNER_ARMOR)), new BipedEntityModel(context.getPart(EntityModelLayers.PLAYER_SLIM_OUTER_ARMOR))));
        this.addFeature(new PlayerHeldItemFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(VampireEntity vampire) {
        return new Identifier("ditn:textures/entity/vampire.png");
    }

    @Override
    protected boolean hasLabel(VampireEntity entity) {
        return false;
    }
}
