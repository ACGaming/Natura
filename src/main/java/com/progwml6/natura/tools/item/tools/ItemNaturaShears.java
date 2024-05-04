package com.progwml6.natura.tools.item.tools;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import com.progwml6.natura.library.NaturaRegistry;

public class ItemNaturaShears extends ItemShears
{
    public final Ingredient repairMaterial;

    public ItemNaturaShears(int durability, Ingredient repairMaterial)
    {
        this.maxStackSize = 1;
        this.repairMaterial = repairMaterial;
        this.setMaxDamage(durability);
        this.setCreativeTab(NaturaRegistry.tabGeneral);
    }

    @Override
    public boolean getIsRepairable(@Nonnull ItemStack toRepair, @Nonnull ItemStack repair)
    {
        return repairMaterial.test(repair) || super.getIsRepairable(toRepair, repair);
    }
}
