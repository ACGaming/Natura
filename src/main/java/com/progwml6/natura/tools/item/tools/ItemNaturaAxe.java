package com.progwml6.natura.tools.item.tools;

import com.progwml6.natura.library.NaturaRegistry;

import net.minecraft.item.ItemAxe;

public class ItemNaturaAxe extends ItemAxe
{
    public ItemNaturaAxe(ToolMaterial material, float damage, float speed)
    {
        super(material, damage - 1.0F, speed - 4.0F);

        this.setCreativeTab(NaturaRegistry.tabGeneral);
    }
}
