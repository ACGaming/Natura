package com.progwml6.natura.overworld.block.crops;

import javax.annotation.Nonnull;

import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.item.ItemStack;

import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.shared.NaturaCommons;

public class BlockNaturaBarley extends BlockOverworldCrops
{
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 3);

    public BlockNaturaBarley()
    {
        super();
    }

    @Override
    public int getMaxAge()
    {
        return 3;
    }

    @Override
    protected PropertyInteger getAgeProperty()
    {
        return AGE;
    }

    @Override
    protected ItemStack getSeed()
    {
        return NaturaOverworld.barley_seeds.copy();
    }

    @Override
    protected ItemStack getCrop()
    {
        return NaturaCommons.barley.copy();
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, AGE);
    }
}
