package com.progwml6.natura.tools.item.tools;

import com.progwml6.natura.library.NaturaRegistry;

import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class ItemNaturaShears extends ItemShears
{
	public Ingredient repairMaterial;
	
    public ItemNaturaShears(int durability, Ingredient repairMaterial)
    {
    	this.maxStackSize = 1;
        this.repairMaterial = repairMaterial;
        this.setMaxDamage(durability);
        this.setCreativeTab(NaturaRegistry.tabGeneral);
    }
    
    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return repairMaterial.test(repair) || super.getIsRepairable(toRepair, repair);
    }
}
