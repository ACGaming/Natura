package com.progwml6.natura.tools.item.tools;

import com.progwml6.natura.library.NaturaRegistry;

import net.minecraft.item.ItemHoe;

public class ItemNaturaHoe extends ItemHoe
{
    public ItemNaturaHoe(ToolMaterial toolMaterialIn)
    {
        super(toolMaterialIn);

        this.setCreativeTab(NaturaRegistry.tabGeneral);
    }
}
