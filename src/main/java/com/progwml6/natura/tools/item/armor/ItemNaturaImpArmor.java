package com.progwml6.natura.tools.item.armor;

import javax.annotation.Nonnull;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import com.progwml6.natura.tools.NaturaTools;

public class ItemNaturaImpArmor extends ItemArmor
{
    public ItemNaturaImpArmor(ArmorMaterial armorMaterialIn, EntityEquipmentSlot equipmentSlotIn)
    {
        super(armorMaterialIn, 1, equipmentSlotIn);
    }

    @Override
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> list)
    {
        if (this.isInCreativeTab(tab))
        {
            switch (this.armorType)
            {
                case HEAD:
                    list.add(NaturaTools.impHelmetStack.copy());
                    break;
                case CHEST:
                    list.add(NaturaTools.impChestplateStack.copy());
                    break;
                case LEGS:
                    list.add(NaturaTools.impLeggingsStack.copy());
                    break;
                case FEET:
                    list.add(NaturaTools.impBootsStack.copy());
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public String getArmorTexture(@Nonnull ItemStack stack, @Nonnull Entity entity, @Nonnull EntityEquipmentSlot slot, @Nonnull String type)
    {
        return "natura:textures/armor/imp_armor" + (slot == EntityEquipmentSlot.LEGS ? "_legs" : "") + ".png";
    }
}
