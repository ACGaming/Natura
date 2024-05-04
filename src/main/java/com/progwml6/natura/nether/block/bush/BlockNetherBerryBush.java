package com.progwml6.natura.nether.block.bush;

import java.util.Random;
import javax.annotation.Nonnull;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeHooks;

import com.progwml6.natura.common.block.BlockEnumBerryBush;

public class BlockNetherBerryBush extends BlockEnumBerryBush
{
    public BlockNetherBerryBush()
    {
        super();

        Blocks.FIRE.setFireInfo(this, 0, 0);
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
    {
        return EnumPlantType.Nether;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        int age = state.getValue(AGE);

        if (age < 2)
        {
            int setMeta = rand.nextInt(2) + 1 + age;

            worldIn.setBlockState(pos, this.getDefaultState().withProperty(AGE, setMeta), 4);

            return;
        }

        if (worldIn.isAirBlock(pos.up()) && rand.nextInt(3) == 0)
        {
            worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(AGE, 0), 2);
        }
    }

    @Override
    public void updateTick(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand)
    {
        super.updateTick(worldIn, pos, state, rand);

        int height;

        for (height = 1; worldIn.getBlockState(pos.down(height)).getBlock() == this; ++height)
        {
        }

        boolean canGrow = (rand.nextInt(75) == 0);

        if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, canGrow))
        {
            int age = state.getValue(AGE);

            if (age < 3)
            {
                worldIn.setBlockState(pos, this.getDefaultState().withProperty(AGE, age + 1), 2);
            }

            if (rand.nextInt(3) == 0 && height < 3 && worldIn.getBlockState(pos.up()).getBlock() == Blocks.AIR && age >= 2)
            {
                worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(AGE, 0), 2);
            }

            ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
        }
    }
}
