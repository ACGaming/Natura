package com.progwml6.natura.library.client.model.passive;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelImp extends ModelBase {
    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer arm1;
    public ModelRenderer arm2;
    public ModelRenderer leg1;
    public ModelRenderer leg2;


    public ModelImp() {
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-5.0F, -7.0F, -4.0F, 10, 8, 8, 0.0F);
        this.head.setRotationPoint(0.0F, 8.0F, 2.0F);
        this.body = new ModelRenderer(this, 16, 16);
        this.body.addBox(-5.0F, 0.0F, -18.0F, 10, 5, 9, 0.0F);
        this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.arm1 = new ModelRenderer(this, 32, 0);
        this.arm1.addBox(-1.0F, 1.0F, 1.0F, 2, 5, 2, 0.0F);
        this.arm1.setRotationPoint(6.0F, 10F, 0.0F);
        this.arm2 = new ModelRenderer(this, 32, 0);
        this.arm2.addBox(-1.0F, 1.0F, 1.0F, 2, 5, 2, 0.0F);
        this.arm2.setRotationPoint(-6.0F, 10.0F, 0.0F);
        this.leg1 = new ModelRenderer(this, 0, 16);
        this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
        this.leg1.setRotationPoint(-3.0F, 18.0F, 2.0F);
        this.leg2 = new ModelRenderer(this, 0, 16);
        this.leg2.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
        this.leg2.setRotationPoint(3.0F, 18.0F, 2.0F);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        GlStateManager.translate(0.0F, 0.0F, -0.125F);
        this.head.render(scale);
        this.body.render(scale);
        this.arm1.render(scale);
        this.arm2.render(scale);
        this.leg1.render(scale);
        this.leg2.render(scale);
    }

    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    	this.head.rotateAngleX = headPitch * 0.017453292F;
    	this.head.rotateAngleY = netHeadYaw * 0.017453292F;
    	this.body.rotateAngleX = 1.570796F;
        this.arm1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 0.0F) * 1.4F * limbSwingAmount;
        this.arm2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;
        this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 0.0F) * 1.4F * limbSwingAmount;
        this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;
    }
}
