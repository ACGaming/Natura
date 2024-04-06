package com.progwml6.natura.library.client.renderer.monster;

import org.lwjgl.opengl.GL11;

import com.progwml6.natura.entities.entity.monster.EntityBabyHeatscarSpider;
import com.progwml6.natura.library.Util;

import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderNautraBabyHeatscarSpider extends RenderLiving<EntityBabyHeatscarSpider>
{
	public static final ResourceLocation texture = Util.getResource("textures/entity/heatscarspider.png");

    public RenderNautraBabyHeatscarSpider(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelSpider(), 0.9F);
        this.addLayer(new LayerHeatscarSpiderEyes<EntityBabyHeatscarSpider>(this));
    }
    
    protected float getDeathMaxRotation(EntityBabyHeatscarSpider entity)
    {
        return 180.0F;
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityBabyHeatscarSpider entity)
    {
        return texture;
    }

    @Override
    protected void preRenderCallback(EntityBabyHeatscarSpider entity, float partialTickTime)
    {
        GL11.glScalef(0.85f, 0.85f, 0.85f);
    }
}
