package com.progwml6.natura.nether.block;

import com.progwml6.natura.Natura;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockNetherrackBricks extends Block {
    public BlockNetherrackBricks() {
        super(Material.ROCK);

        this.setCreativeTab(Natura.TAB);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setHarvestLevel("pickaxe", 0);
    }

    @Override
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side) {
        return side == EnumFacing.UP;
    }
}
