package com.progwml6.natura.library.client.model.passive;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelImp extends ModelBase {
    public ModelRenderer head;
    public ModelRenderer horn1;
    public ModelRenderer horn2;
    public ModelRenderer snout;
    public ModelRenderer body;
    public ModelRenderer arm1;
    public ModelRenderer arm2;
    public ModelRenderer leg1;
    public ModelRenderer leg2;


    public ModelImp() {
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-5.0F, -7.0F, -6.0F, 10, 8, 8, 0.0F);
        this.head.setRotationPoint(0.0F, 8.0F, 2.0F);
        this.horn1 = new ModelRenderer(this, 1, 1);
        this.horn1.addBox(-4.0F, -24.0F, -1.0F, 2, 1, 1, 0.0F);
        this.horn1.setRotationPoint(0.0F, 16.0F, -4.0F);
        this.horn2 = new ModelRenderer(this, 1, 1);
        this.horn2.addBox(2.0F, -24.0F, -1.0F, 2, 1, 1, 0.0F);
        this.horn2.setRotationPoint(0.0F, 16.0F, -4.0F);
        this.snout = new ModelRenderer(this, 37, 8);
		this.snout.addBox(-2.0F, -3.0F, -5.0F, 4, 3, 1, 0.0F);
		this.snout.setRotationPoint(0.0F, 0.0F, -2.0F);
        this.body = new ModelRenderer(this, 16, 16);
        this.body.addBox(-5.0F, 9.0F, -2.0F, 10, 9, 5, 0.0F);
        this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.arm1 = new ModelRenderer(this, 32, 0);
        this.arm1.addBox(-1.0F, 1.0F, -1.0F, 2, 5, 2, 0.0F);
        this.arm1.setRotationPoint(6.0F, 10F, 0.0F);
        this.arm2 = new ModelRenderer(this, 32, 0);
        this.arm2.addBox(-1.0F, 1.0F, -1.0F, 2, 5, 2, 0.0F);
        this.arm2.setRotationPoint(-6.0F, 10.0F, 0.0F);
        this.leg1 = new ModelRenderer(this, 0, 16);
        this.leg1.addBox(-2.0F, 0.0F, -4.0F, 4, 6, 4, 0.0F);
        this.leg1.setRotationPoint(-3.0F, 18.0F, 2.0F);
        this.leg2 = new ModelRenderer(this, 0, 16);
        this.leg2.addBox(-2.0F, 0.0F, -4.0F, 4, 6, 4, 0.0F);
        this.leg2.setRotationPoint(3.0F, 18.0F, 2.0F);
        this.head.addChild(this.horn1);
        this.head.addChild(this.horn2);
        this.head.addChild(this.snout);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        this.head.render(scale);
        this.body.render(scale);
        this.arm1.render(scale);
        this.arm2.render(scale);
        this.leg1.render(scale);
        this.leg2.render(scale);
    }

    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    	this.head.rotateAngleX = headPitch * 0.008F;
    	this.head.rotateAngleY = netHeadYaw * 0.012F;
        this.arm1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 0.0F) * 1.4F * limbSwingAmount;
        this.arm2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;
        this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 0.0F) * 1.4F * limbSwingAmount;
        this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;
    }
}
