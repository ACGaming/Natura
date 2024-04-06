package com.progwml6.natura.library.client.renderer.monster;

import org.lwjgl.opengl.GL11;

import com.progwml6.natura.entities.entity.monster.EntityHeatscarSpider;
import com.progwml6.natura.library.Util;

import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderNautraHeatscarSpider extends RenderLiving<EntityHeatscarSpider>
{
    public static final ResourceLocation heatscarSpiderTexture = Util.getResource("textures/entity/heatscarspider.png");

    public RenderNautraHeatscarSpider(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelSpider(), 2.0F);
        this.addLayer(new LayerHeatscarSpiderEyes<EntityHeatscarSpider>(this));
    }
    
    protected float getDeathMaxRotation(EntityHeatscarSpider entity)
    {
        return 180.0F;
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityHeatscarSpider entity)
    {
        return heatscarSpiderTexture;
    }

    @Override
    protected void preRenderCallback(EntityHeatscarSpider par1EntityLivingBase, float par2)
    {
        GL11.glScalef(2.0F, 2.0F, 2.0F);
    }
}
