package com.progwml6.natura.nether.block;

import com.progwml6.natura.Natura;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class BlockSmoothNetherrack extends Block {
    public BlockSmoothNetherrack() {
        super(Material.ROCK);

        this.setCreativeTab(Natura.TAB);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setHarvestLevel("pickaxe", 0);
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        EnumPlantType plantType = plantable.getPlantType(world, pos.up());

        if (plantType == EnumPlantType.Nether) {
            return true;
        }

        return super.canSustainPlant(state, world, pos, direction, plantable);
    }

    @Override
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side) {
        return side == EnumFacing.UP;
    }
}
