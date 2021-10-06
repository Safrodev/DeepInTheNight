package safro.deep.in.the.night.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import safro.deep.in.the.night.entity.CrowEntity;

public class CrowEntityModel extends EntityModel<CrowEntity> {
	private final ModelPart head;
	private final ModelPart feather;
	private final ModelPart body;
	private final ModelPart leftWing;
	private final ModelPart left_wing_rotation;
	private final ModelPart rightWing;
	private final ModelPart right_wing_rotation;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;
	private final ModelPart right_leg_r1;
	private final ModelPart tail;

	public CrowEntityModel(ModelPart root) {
		this.head = root.getChild("head");
		this.feather = this.head.getChild("feather");
		this.body = root.getChild("body");
		this.leftWing = root.getChild("left_wing");
		this.left_wing_rotation = this.leftWing.getChild("left_wing_rotation");
		this.rightWing = root.getChild("right_wing");
		this.right_wing_rotation = this.rightWing.getChild("right_wing_rotation");
		this.leftLeg = root.getChild("left_leg");
		this.rightLeg = root.getChild("right_leg");
		this.right_leg_r1 = this.rightLeg.getChild("right_leg_r1");
		this.tail = root.getChild("tail");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData modelPartData1 = modelPartData.addChild("head", ModelPartBuilder.create().uv(8,14).cuboid(-1.0F, -1.5F, -1.0F, 2.0F, 3.0F, 2.0F).uv(5,9).cuboid(-0.5F, -1.5F, -2.0F, 1.0F, 1.0F, 1.0F), ModelTransform.pivot(0.0F,16.0F,-0.5F));
		modelPartData1.addChild("feather", ModelPartBuilder.create(), ModelTransform.pivot(0.0F,-1.5F,-2.0F));
		modelPartData.addChild("body", ModelPartBuilder.create().uv(0,0).cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F), ModelTransform.pivot(0.0F,16.5F,-1.0F));
		ModelPartData modelPartData2 = modelPartData.addChild("left_wing", ModelPartBuilder.create(), ModelTransform.pivot(-1.5F,16.9F,-0.8F));
		modelPartData2.addChild("left_wing_rotation", ModelPartBuilder.create().uv(9,6).cuboid(-0.5F, -2.5F, -1.5F, 1.0F, 5.0F, 3.0F), ModelTransform.pivot(3.0F,2.5F,0.0F));
		ModelPartData modelPartData3 = modelPartData.addChild("right_wing", ModelPartBuilder.create(), ModelTransform.pivot(1.5F,16.9F,-0.8F));
		modelPartData3.addChild("right_wing_rotation", ModelPartBuilder.create().uv(0,9).cuboid(-0.5F, -2.5F, -1.5F, 1.0F, 5.0F, 3.0F), ModelTransform.pivot(-3.0F,2.5F,0.0F));
		modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(0,9).cuboid(-0.5F, 0.0F, 0.5F, 1.0F, 2.0F, 0.0F), ModelTransform.pivot(-1.0F,22.0F,-1.0F));
		ModelPartData modelPartData4 = modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(0,0).cuboid(-0.5F, 0.0F, 0.5F, 1.0F, 2.0F, 0.0F), ModelTransform.pivot(1.0F,22.0F,-1.0F));
		modelPartData4.addChild("right_leg_r1", ModelPartBuilder.create().uv(0,2).cuboid(-1.5F, -1.5F, 0.0F, 1.0F, 1.0F, 0.0F).uv(9,0).cuboid(0.5F, -1.5F, 0.0F, 1.0F, 1.0F, 0.0F), ModelTransform.pivot(-1.0F,2.0F,1.0F));
		modelPartData.addChild("tail", ModelPartBuilder.create().uv(12,0).cuboid(-1.5F, -1.0F, -1.0F, 3.0F, 4.0F, 1.0F), ModelTransform.pivot(0.0F,21.1F,1.2F));

		return TexturedModelData.of(modelData,32,32);
	}

	public void setAngles(CrowEntity crow, float f, float g, float h, float i, float j) {
		this.setAngles(getPose(crow), crow.age, f, g, h, i, j);
	}

	public void animateModel(CrowEntity crow, float f, float g, float h) {
		this.animateModel(getPose(crow));
	}


	public void setAngles(CrowEntityModel.CrowPose pose, int danceAngle, float limbAngle, float limbDistance, float age, float headYaw, float headPitch) {
		this.head.pitch = headPitch * 0.017453292F;
		this.head.yaw = headYaw * 0.017453292F;
		this.head.roll = 0.0F;
		this.head.pivotX = 0.0F;
		this.body.pivotX = 0.0F;
		this.tail.pivotX = 0.0F;
		this.rightWing.pivotX = -1.5F;
		this.leftWing.pivotX = 1.5F;
		switch(pose) {
			case STANDING:
				ModelPart var10000 = this.leftLeg;
				var10000.pitch += MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
				var10000 = this.rightLeg;
				var10000.pitch += MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
			case FLYING:
			default:
				float h = age * 0.3F;
				this.head.pivotY = 15.69F + h;
				this.tail.pitch = 1.015F + MathHelper.cos(limbAngle * 0.6662F) * 0.3F * limbDistance;
				this.tail.pivotY = 21.07F + h;
				this.body.pivotY = 16.5F + h;
				this.leftWing.roll = -0.0873F - age;
				this.leftWing.pivotY = 16.94F + h;
				this.rightWing.roll = 0.0873F + age;
				this.rightWing.pivotY = 16.94F + h;
				this.leftLeg.pivotY = 22.0F + h;
				this.rightLeg.pivotY = 22.0F + h;
		}
		setRotationAngle(feather, -0.2618F, 0.0F, 0.0F);
		setRotationAngle(left_wing_rotation, 0.0F, 3.1416F, 0.0F);
		setRotationAngle(right_wing_rotation, 0.0F, 3.1416F, 0.0F);
		setRotationAngle(right_leg_r1, 1.5708F, 0.0F, 0.0F);
	}

	private void animateModel(CrowEntityModel.CrowPose pose) {
		this.feather.pitch = -0.2214F;
		this.body.pitch = 0.4937F;
		this.leftWing.pitch = -0.6981F;
		this.leftWing.yaw = -3.1415927F;
		this.rightWing.pitch = -0.6981F;
		this.rightWing.yaw = -3.1415927F;
		this.leftLeg.pitch = -0.0299F;
		this.rightLeg.pitch = -0.0299F;
		this.leftLeg.pivotY = 22.0F;
		this.rightLeg.pivotY = 22.0F;
		this.leftLeg.roll = 0.0F;
		this.rightLeg.roll = 0.0F;
		switch(pose) {
			case STANDING:
			default:
				break;
			case FLYING:
				ModelPart var10000 = this.leftLeg;
				var10000.pitch += 0.6981317F;
				var10000 = this.rightLeg;
				var10000.pitch += 0.6981317F;
		}

	}

	private static CrowEntityModel.CrowPose getPose(CrowEntity crow) {
			return crow.isInAir() ? CrowEntityModel.CrowPose.FLYING : CrowEntityModel.CrowPose.STANDING;
	}

	@Override
	public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		head.render(matrixStack, buffer, packedLight, packedOverlay);
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		leftWing.render(matrixStack, buffer, packedLight, packedOverlay);
		rightWing.render(matrixStack, buffer, packedLight, packedOverlay);
		leftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
		rightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
		tail.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelPart bone, float x, float y, float z) {
		bone.pitch = x;
		bone.yaw = y;
		bone.roll = z;
	}

	@Environment(EnvType.CLIENT)
	public static enum CrowPose {
		FLYING,
		STANDING;

		private CrowPose() {
		}
	}
}