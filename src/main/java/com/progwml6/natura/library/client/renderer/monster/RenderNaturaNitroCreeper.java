package com.progwml6.natura.library.client.renderer.monster;

import com.progwml6.natura.library.Util;

import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;

public class RenderNaturaNitroCreeper extends RenderCreeper
{
    public static final ResourceLocation impTexture = Util.getResource("textures/entity/nitrocreeper.png");

    public RenderNaturaNitroCreeper(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityCreeper entity)
    {
        return impTexture;
    }
}
