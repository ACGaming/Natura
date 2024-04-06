package com.progwml6.natura.library.client.renderer.passive;

import org.lwjgl.opengl.GL11;

import com.progwml6.natura.entities.entity.passive.EntityImp;
import com.progwml6.natura.library.client.model.passive.ModelImp;
import com.progwml6.natura.library.Util;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderNaturaImp extends RenderLiving<EntityImp>
{
    public static final ResourceLocation texture = Util.getResource("textures/entity/imp.png");

    public RenderNaturaImp(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelImp(), 0.6F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityImp entity)
    {
        return texture;
    }
    
    @Override
    protected void preRenderCallback(EntityImp entity, float partialTickTime)
    {
        if (entity.isChild()) GL11.glScalef(0.5F, 0.5F, 0.5F);
    }
}
