package com.progwml6.natura.tools.item.tools;

import net.minecraft.item.ItemSword;

import com.progwml6.natura.Natura;

public class ItemNaturaSword extends ItemSword
{
    public ItemNaturaSword(ToolMaterial toolMaterialIn)
    {
        super(toolMaterialIn);
        this.setCreativeTab(Natura.TAB);
    }
}
