package com.progwml6.natura.tools.item.tools;

import javax.annotation.Nonnull;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import com.progwml6.natura.Natura;

public class ItemNaturaShears extends ItemShears
{
    public final Ingredient repairMaterial;

    public ItemNaturaShears(int durability, Ingredient repairMaterial)
    {
        this.maxStackSize = 1;
        this.repairMaterial = repairMaterial;
        this.setMaxDamage(durability);
        this.setCreativeTab(Natura.TAB);
    }
    
    // Hardcoded blocks...
    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state)
    {
        Material material = state.getMaterial();

        if (material != Material.WEB && material != Material.LEAVES)
        {
            return material == Material.CLOTH ? 5.0F : super.getDestroySpeed(stack, state);
        }
        else
        {
            return 15.0F;
        }
    }

    @Override
    public boolean getIsRepairable(@Nonnull ItemStack toRepair, @Nonnull ItemStack repair)
    {
        return repairMaterial.test(repair) || super.getIsRepairable(toRepair, repair);
    }
}
