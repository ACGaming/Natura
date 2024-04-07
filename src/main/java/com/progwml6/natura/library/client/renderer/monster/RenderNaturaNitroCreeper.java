package com.progwml6.natura.library.client.renderer.monster;

import com.progwml6.natura.library.Util;

import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderNaturaNitroCreeper extends RenderCreeper
{
    public static final ResourceLocation texture = Util.getResource("textures/entity/nitro_creeper.png");

    public RenderNaturaNitroCreeper(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityCreeper entity)
    {
        return texture;
    }
}
