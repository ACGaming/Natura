package com.progwml6.natura.library;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NaturaCreativeTab extends CreativeTabs
{
    public NaturaCreativeTab(int length, String name)
    {
        super(length, name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack createIcon()
    {
        return new ItemStack(Blocks.SAPLING, 1, 0);
    }
}
