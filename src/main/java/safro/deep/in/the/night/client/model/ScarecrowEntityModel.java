package safro.deep.in.the.night.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import safro.deep.in.the.night.entity.ScarecrowEntity;

public class ScarecrowEntityModel extends EntityModel<ScarecrowEntity> {
	private final ModelPart head;
	private final ModelPart headwear;
	private final ModelPart body;
	private final ModelPart left_arm;
	private final ModelPart left_arm_r1;
	private final ModelPart right_arm;
	private final ModelPart right_arm_r1;
	private final ModelPart left_leg;
	private final ModelPart right_leg;

	public ScarecrowEntityModel(ModelPart root) {
		this.head = root.getChild("head");
		this.headwear = root.getChild("headwear");
		this.body = root.getChild("body");
		this.left_arm = root.getChild("left_arm");
		this.left_arm_r1 = this.left_arm.getChild("left_arm_r1");
		this.right_arm = root.getChild("right_arm");
		this.right_arm_r1 = this.right_arm.getChild("right_arm_r1");
		this.left_leg = root.getChild("left_leg");
		this.right_leg = root.getChild("right_leg");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("head", ModelPartBuilder.create().uv(0,16).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F), ModelTransform.pivot(0.0F,0.0F,0.0F));
		modelPartData.addChild("headwear", ModelPartBuilder.create().uv(0,0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.25F)), ModelTransform.pivot(0.0F,0.0F,0.0F));
		modelPartData.addChild("body", ModelPartBuilder.create().uv(28,28).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F), ModelTransform.pivot(0.0F,0.0F,0.0F));
		ModelPartData modelPartData1 = modelPartData.addChild("left_arm", ModelPartBuilder.create(), ModelTransform.pivot(-5.0F,2.0F,0.0F));
		modelPartData1.addChild("left_arm_r1", ModelPartBuilder.create().uv(32,14).cuboid(16.0F, -15.0F, 0.0F, 5.0F, 10.0F, 0.0F).uv(16,32).cuboid(16.0F, 5.0F, 0.0F, 5.0F, 10.0F, 0.0F).uv(8,32).cuboid(21.0F, 4.0F, -1.0F, 2.0F, 12.0F, 2.0F), ModelTransform.pivot(5.0F,22.0F,0.0F));
		ModelPartData modelPartData2 = modelPartData.addChild("right_arm", ModelPartBuilder.create(), ModelTransform.pivot(5.0F,2.0F,0.0F));
		modelPartData2.addChild("right_arm_r1", ModelPartBuilder.create().uv(32,0).cuboid(-23.0F, 4.0F, -1.0F, 2.0F, 12.0F, 2.0F), ModelTransform.pivot(-5.0F,22.0F,0.0F));
		modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(0,32).cuboid(1.0F, 0.0F, -1.1F, 2.0F, 12.0F, 2.0F), ModelTransform.pivot(-2.0F,12.0F,0.1F));
		modelPartData.addChild("right_leg", ModelPartBuilder.create(), ModelTransform.pivot(2.0F,12.0F,0.1F));

		return TexturedModelData.of(modelData,64,64);
	}

	@Override
	public void setAngles(ScarecrowEntity entity, float limbSwing, float limbSwingAmount, float animationProgress, float netHeadYaw, float headPitch){
		left_arm_r1.roll = -1.5708F;
		right_arm_r1.roll = 1.5708F;
	}

	@Override
	public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		head.render(matrixStack, buffer, packedLight, packedOverlay);
		headwear.render(matrixStack, buffer, packedLight, packedOverlay);
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		left_arm.render(matrixStack, buffer, packedLight, packedOverlay);
		right_arm.render(matrixStack, buffer, packedLight, packedOverlay);
		left_leg.render(matrixStack, buffer, packedLight, packedOverlay);
		right_leg.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelPart bone, float x, float y, float z) {
		bone.pitch = x;
		bone.yaw = y;
		bone.roll = z;
	}
}