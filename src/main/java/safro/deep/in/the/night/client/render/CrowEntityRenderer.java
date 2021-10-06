package safro.deep.in.the.night.client.render;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import safro.deep.in.the.night.DeepInTheNight;
import safro.deep.in.the.night.client.model.CrowEntityModel;
import safro.deep.in.the.night.entity.CrowEntity;
import safro.deep.in.the.night.registry.RenderModelRegistry;

public class CrowEntityRenderer extends MobEntityRenderer<CrowEntity, CrowEntityModel> {
    public static final Identifier TEXTURE = new Identifier(DeepInTheNight.MODID, "textures/entity/crow.png");

    public CrowEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new CrowEntityModel(context.getPart(RenderModelRegistry.CROW_LAYER)), 0.3F);
    }

    public Identifier getTexture(CrowEntity crow) {
        return TEXTURE;
    }

    public float getAnimationProgress(CrowEntity crowEntity, float f) {
        float g = MathHelper.lerp(f, crowEntity.prevFlapProgress, crowEntity.flapProgress);
        float h = MathHelper.lerp(f, crowEntity.prevMaxWingDeviation, crowEntity.maxWingDeviation);
        return (MathHelper.sin(g) + 1.0F) * h;
    }
}
