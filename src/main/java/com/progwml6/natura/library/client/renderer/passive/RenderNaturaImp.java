package com.progwml6.natura.library.client.renderer.passive;

import com.progwml6.natura.entities.entity.passive.EntityImp;
import com.progwml6.natura.library.client.model.passive.ModelImp;
import com.progwml6.natura.library.Util;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderNaturaImp extends RenderLiving<EntityImp>
{
    private static final ResourceLocation[] texture = new ResourceLocation[] {
    		Util.getResource("textures/entity/imp_brown.png"),
    		Util.getResource("textures/entity/imp_red.png"),
    		Util.getResource("textures/entity/imp_green.png"),
    		Util.getResource("textures/entity/imp_black.png"),
    		Util.getResource("textures/entity/imp_yellow_green.png")
	};

    public RenderNaturaImp(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelImp(), 0.6F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityImp entity)
    {
        return texture[entity.getSkin()];
    }
    
    @Override
    protected void preRenderCallback(EntityImp entity, float partialTickTime)
    {
        if (entity.isChild()) GlStateManager.scale(0.5F, 0.5F, 0.5F);
    }
}
