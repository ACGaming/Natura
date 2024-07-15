package com.progwml6.natura.overworld.block.flower;

import com.progwml6.natura.Natura;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;

public class BlockBluebellsFlower extends BlockBush
{
    public BlockBluebellsFlower()
    {
        super();
        this.setSoundType(SoundType.PLANT);
        this.setCreativeTab(Natura.TAB);
    }
}
