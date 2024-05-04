package com.progwml6.natura.tools.item.tools;

import net.minecraft.item.ItemHoe;

import com.progwml6.natura.library.NaturaRegistry;

public class ItemNaturaHoe extends ItemHoe
{
    public ItemNaturaHoe(ToolMaterial toolMaterialIn)
    {
        super(toolMaterialIn);

        this.setCreativeTab(NaturaRegistry.tabGeneral);
    }
}
