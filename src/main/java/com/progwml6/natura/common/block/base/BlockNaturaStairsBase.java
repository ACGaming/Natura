package com.progwml6.natura.common.block.base;

import com.progwml6.natura.Natura;
import net.minecraft.block.state.IBlockState;
import slimeknights.mantle.block.BlockStairsBase;

public class BlockNaturaStairsBase extends BlockStairsBase
{
    public BlockNaturaStairsBase(IBlockState modelState)
    {
        super(modelState);
        this.setCreativeTab(Natura.TAB);
    }
}
