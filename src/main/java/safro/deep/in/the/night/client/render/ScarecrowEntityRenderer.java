package safro.deep.in.the.night.client.render;


import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import safro.deep.in.the.night.DeepInTheNight;
import safro.deep.in.the.night.client.model.ScarecrowEntityModel;
import safro.deep.in.the.night.entity.ScarecrowEntity;
import safro.deep.in.the.night.registry.RenderModelRegistry;

public class ScarecrowEntityRenderer extends MobEntityRenderer<ScarecrowEntity, ScarecrowEntityModel> {
    private static final Identifier TEXTURE = new Identifier(DeepInTheNight.MODID, "textures/entity/scarecrow.png");

    public ScarecrowEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new ScarecrowEntityModel(context.getPart(RenderModelRegistry.SCARECROW_LAYER)), 0.7F);
    }

    @Override
    public Identifier getTexture(ScarecrowEntity scarecrowEntity) {
        return TEXTURE;
    }
}
