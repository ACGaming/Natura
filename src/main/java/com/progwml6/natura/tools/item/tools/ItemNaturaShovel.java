package com.progwml6.natura.tools.item.tools;

import net.minecraft.item.ItemSpade;

import com.progwml6.natura.library.NaturaRegistry;

public class ItemNaturaShovel extends ItemSpade
{
    public ItemNaturaShovel(ToolMaterial toolMaterialIn)
    {
        super(toolMaterialIn);

        this.setCreativeTab(NaturaRegistry.tabGeneral);
    }
}
