package com.progwml6.natura.tools.item.tools;

import com.progwml6.natura.library.NaturaRegistry;

import net.minecraft.item.ItemPickaxe;

public class ItemNaturaPickaxe extends ItemPickaxe
{
    public ItemNaturaPickaxe(ToolMaterial toolMaterialIn)
    {
        super(toolMaterialIn);

        this.setCreativeTab(NaturaRegistry.tabGeneral);
    }
}
