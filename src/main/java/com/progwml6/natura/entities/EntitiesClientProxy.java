package com.progwml6.natura.entities;

import com.progwml6.natura.common.ClientProxy;
import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.entities.entity.monster.EntityBabyHeatscarSpider;
import com.progwml6.natura.entities.entity.monster.EntityHeatscarSpider;
import com.progwml6.natura.entities.entity.monster.EntityNitroCreeper;
import com.progwml6.natura.entities.entity.passive.EntityImp;
import com.progwml6.natura.library.client.renderer.monster.RenderNaturaNitroCreeper;
import com.progwml6.natura.library.client.renderer.monster.RenderNautraBabyHeatscarSpider;
import com.progwml6.natura.library.client.renderer.monster.RenderNautraHeatscarSpider;
import com.progwml6.natura.library.client.renderer.passive.RenderNaturaImp;

import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class EntitiesClientProxy extends ClientProxy
{
    @Override
    public void preInit()
    {
        // Entities
        RenderingRegistry.registerEntityRenderingHandler(EntityImp.class, RenderNaturaImp::new);
        if (Config.enableHeatscarSpider)
        {
            RenderingRegistry.registerEntityRenderingHandler(EntityHeatscarSpider.class, RenderNautraHeatscarSpider::new);
            RenderingRegistry.registerEntityRenderingHandler(EntityBabyHeatscarSpider.class, RenderNautraBabyHeatscarSpider::new);
        }
        RenderingRegistry.registerEntityRenderingHandler(EntityNitroCreeper.class, RenderNaturaNitroCreeper::new);

        super.preInit();
    }

    @Override
    public void init()
    {

    }

    @Override
    protected void registerModels()
    {
    }

    @Override
    public void postInit()
    {
        super.postInit();

    }
}
