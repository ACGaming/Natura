package com.progwml6.natura.world.worldgen.glowshroom.nether;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockMycelium;
import net.minecraft.block.BlockNetherrack;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

import com.progwml6.natura.nether.NaturaNether;
import com.progwml6.natura.nether.block.shrooms.BlockNetherLargeGlowshroom;
import com.progwml6.natura.nether.block.shrooms.BlockNetherLargeGlowshroom.EnumType;
import com.progwml6.natura.world.worldgen.glowshroom.BaseGlowshroomGenerator;

public class PurpleGlowshroomGenerator extends BaseGlowshroomGenerator
{
    public final IBlockState glowshroom;

    public PurpleGlowshroomGenerator(IBlockState glowshroom)
    {
        this.glowshroom = glowshroom;
    }

    @Override
    public void generateShroom(Random random, World world, BlockPos pos)
    {
        int height = random.nextInt(4) + 1;

        boolean flag = true;

        if (pos.getY() >= 1 && pos.getY() + height + 1 < 256)
        {
            for (int j = pos.getY(); j <= pos.getY() + 1 + height; ++j)
            {
                int k = 3;

                if (j <= pos.getY() + 3)
                {
                    k = 0;
                }

                MutableBlockPos mutableblockpos = new MutableBlockPos();

                for (int l = pos.getX() - k; l <= pos.getX() + k && flag; ++l)
                {
                    for (int i1 = pos.getZ() - k; i1 <= pos.getZ() + k && flag; ++i1)
                    {
                        if (j >= 0 && j < 256)
                        {
                            IBlockState state = world.getBlockState(mutableblockpos.setPos(l, j, i1));

                            if (!state.getBlock().isAir(state, world, mutableblockpos) && !state.getBlock().isLeaves(state, world, mutableblockpos))
                            {
                                flag = false;
                            }
                        }
                        else
                        {
                            flag = false;
                        }
                    }
                }
            }
            if (flag)
            {
                Block block1 = world.getBlockState(pos.down()).getBlock();

                if (block1 instanceof BlockMycelium || block1 instanceof BlockNetherrack || block1 instanceof BlockSoulSand || block1 == NaturaNether.netherTaintedSoil)
                {
                    int k2 = pos.getY() + height - 1;

                    for (int l2 = k2; l2 <= pos.getY() + height; ++l2)
                    {
                        int j3 = 2;

                        if (l2 < pos.getY() + height)
                        {
                            ++j3;
                        }

                        int k3 = pos.getX() - j3;
                        int l3 = pos.getX() + j3;
                        int j1 = pos.getZ() - j3;
                        int k1 = pos.getZ() + j3;

                        for (int l1 = k3; l1 <= l3; ++l1)
                        {
                            for (int i2 = j1; i2 <= k1; ++i2)
                            {
                                int j2 = 5;

                                if (l1 == k3)
                                {
                                    --j2;
                                }
                                else if (l1 == l3)
                                {
                                    ++j2;
                                }

                                if (i2 == j1)
                                {
                                    j2 -= 3;
                                }
                                else if (i2 == k1)
                                {
                                    j2 += 3;
                                }

                                EnumType enumtype = EnumType.byMetadata(j2);

                                if (l2 < pos.getY() + height)
                                {
                                    int swap = l2 < pos.getY() + height ? 2 : 1;

                                    if ((l1 == k3 || l1 == l3) && (i2 == j1 || i2 == k1))
                                    {
                                        continue;
                                    }

                                    if (l2 < pos.getY() + height)
                                    {
                                        if ((l1 == k3 + 1 || l1 == l3 - 1) && (i2 == j1 + 1 || i2 == k1 - 1))
                                        {
                                            continue;
                                        }
                                    }

                                    if (l1 == pos.getX() - (j3 - swap) && i2 == j1)
                                    {
                                        enumtype = EnumType.NORTH_WEST;
                                    }

                                    if (l1 == k3 && i2 == pos.getZ() - (j3 - swap))
                                    {
                                        enumtype = EnumType.NORTH_WEST;
                                    }

                                    if (l1 == pos.getX() + (j3 - swap) && i2 == j1)
                                    {
                                        enumtype = EnumType.NORTH_EAST;
                                    }

                                    if (l1 == l3 && i2 == pos.getZ() - (j3 - swap))
                                    {
                                        enumtype = EnumType.NORTH_EAST;
                                    }

                                    if (l1 == pos.getX() - (j3 - swap) && i2 == k1)
                                    {
                                        enumtype = EnumType.SOUTH_WEST;
                                    }

                                    if (l1 == k3 && i2 == pos.getZ() + (j3 - swap))
                                    {
                                        enumtype = EnumType.SOUTH_WEST;
                                    }

                                    if (l1 == pos.getX() + (j3 - swap) && i2 == k1)
                                    {
                                        enumtype = EnumType.SOUTH_EAST;
                                    }

                                    if (l1 == l3 && i2 == pos.getZ() + (j3 - swap))
                                    {
                                        enumtype = EnumType.SOUTH_EAST;
                                    }
                                }

                                if (enumtype == EnumType.CENTER && l2 < pos.getY() + height)
                                {
                                    enumtype = EnumType.ALL_INSIDE;
                                }

                                if (pos.getY() >= pos.getY() + height - 1 || enumtype != EnumType.ALL_INSIDE)
                                {
                                    BlockPos blockpos = new BlockPos(l1, l2, i2);
                                    IBlockState state = world.getBlockState(blockpos);

                                    if (state.getBlock().canBeReplacedByLeaves(state, world, blockpos))
                                    {
                                        world.setBlockState(blockpos, this.glowshroom.withProperty(BlockNetherLargeGlowshroom.VARIANT, enumtype), 2);
                                    }
                                }
                            }
                        }
                    }

                    IBlockState iblockstate = world.getBlockState(pos.add(-2, height - 1, -2));

                    if (iblockstate.getBlock().canBeReplacedByLeaves(iblockstate, world, pos.add(-2, height - 1, -2)))
                    {
                        world.setBlockState(pos.add(-2, height - 1, -2), this.glowshroom.withProperty(BlockNetherLargeGlowshroom.VARIANT, EnumType.NORTH_WEST), 2);
                    }

                    iblockstate = world.getBlockState(pos.add(2, height - 1, -2));

                    if (iblockstate.getBlock().canBeReplacedByLeaves(iblockstate, world, pos.add(2, height - 1, -2)))
                    {
                        world.setBlockState(pos.add(2, height - 1, -2), this.glowshroom.withProperty(BlockNetherLargeGlowshroom.VARIANT, EnumType.NORTH_EAST), 2);
                    }

                    iblockstate = world.getBlockState(pos.add(-2, height - 1, 2));

                    if (iblockstate.getBlock().canBeReplacedByLeaves(iblockstate, world, pos.add(-2, height - 1, 2)))
                    {
                        world.setBlockState(pos.add(-2, height - 1, 2), this.glowshroom.withProperty(BlockNetherLargeGlowshroom.VARIANT, EnumType.SOUTH_WEST), 2);
                    }

                    iblockstate = world.getBlockState(pos.add(2, height - 1, 2));

                    if (iblockstate.getBlock().canBeReplacedByLeaves(iblockstate, world, pos.add(2, height - 1, 2)))
                    {
                        world.setBlockState(pos.add(2, height - 1, 2), this.glowshroom.withProperty(BlockNetherLargeGlowshroom.VARIANT, EnumType.SOUTH_EAST), 2);
                    }

                    for (int i3 = 0; i3 < height; ++i3)
                    {
                        iblockstate = world.getBlockState(pos.up(i3));

                        if (iblockstate.getBlock().canBeReplacedByLeaves(iblockstate, world, pos.up(i3)))
                        {
                            world.setBlockState(pos.up(i3), this.glowshroom.withProperty(BlockNetherLargeGlowshroom.VARIANT, EnumType.STEM), 2);
                        }
                    }
                }
            }
        }
    }
}
