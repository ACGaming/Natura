package com.progwml6.natura.world.worldgen.berry.nether;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import com.progwml6.natura.common.block.BlockEnumBerryBush;
import com.progwml6.natura.world.worldgen.berry.BaseBerryBushGenerator;

public class NetherBerryBushGenerator extends BaseBerryBushGenerator
{
    public final IBlockState berryBush;

    public NetherBerryBushGenerator(IBlockState berryBush)
    {
        this.berryBush = berryBush;
    }

    @Override
    public void generateBush(Random random, World world, BlockPos pos)
    {
        pos = this.findGround(world, pos);
        if (pos.getY() < 0)
        {
            return;
        }

        int yPos = pos.getY();

        if (yPos >= 1)
        {
            int size = random.nextInt(10);

            if (size == 9)
            {
                this.generateLargeNode(random, world, pos);
            }
            else if (size >= 7)
            {
                this.generateShrub(random, world, pos);
            }
            else if (size >= 3)
            {
                this.generateSmallNode(random, world, pos);
            }
            else
            {
                this.generateTinyNode(random, world, pos);
            }
        }
    }

    protected void generateLargeNode(Random random, World world, BlockPos pos)
    {
        for (int iterX = pos.getX() - 2; iterX <= pos.getX() + 2; iterX++)
        {
            for (int iterZ = pos.getZ() - 1; iterZ <= pos.getZ() + 1; iterZ++)
            {
                for (int iterY = pos.getY() - 1; iterY <= pos.getY(); iterY++)
                {
                    BlockPos blockpos = new BlockPos(iterX, iterY, iterZ);

                    this.setBlockAndMetadata(world, blockpos, this.berryBush.withProperty(BlockEnumBerryBush.AGE, this.randomFullAge(random)));
                }
            }
        }

        for (int iterX = pos.getX() - 1; iterX <= pos.getX() + 1; iterX++)
        {
            for (int iterZ = pos.getZ() - 2; iterZ <= pos.getZ() - 2; iterZ++)
            {
                for (int iterY = pos.getY() - 1; iterY <= pos.getY(); iterY++)
                {
                    BlockPos blockpos = new BlockPos(iterX, iterY, iterZ);

                    this.setBlockAndMetadata(world, blockpos, this.berryBush.withProperty(BlockEnumBerryBush.AGE, this.randomFullAge(random)));
                }
            }
        }

        for (int iterX = pos.getX() - 1; iterX <= pos.getX() + 1; iterX++)
        {
            for (int iterZ = pos.getZ() + 2; iterZ <= pos.getZ() + 2; iterZ++)
            {
                for (int iterY = pos.getY() - 1; iterY <= pos.getY(); iterY++)
                {
                    BlockPos blockpos = new BlockPos(iterX, iterY, iterZ);

                    this.setBlockAndMetadata(world, blockpos, this.berryBush.withProperty(BlockEnumBerryBush.AGE, this.randomFullAge(random)));
                }
            }
        }

        for (int iterX = pos.getX() - 1; iterX <= pos.getX() + 1; iterX++)
        {
            for (int iterZ = pos.getZ() - 1; iterZ <= pos.getZ() + 1; iterZ++)
            {
                int yPos = pos.getY() + 1;
                BlockPos blockpos = new BlockPos(iterX, yPos, iterZ);

                this.setBlockAndMetadata(world, blockpos, this.berryBush.withProperty(BlockEnumBerryBush.AGE, this.randomFullAge(random)));

                yPos = pos.getY() - 2;
                blockpos = new BlockPos(iterX, yPos, iterZ);

                this.setBlockAndMetadata(world, blockpos, this.berryBush.withProperty(BlockEnumBerryBush.AGE, this.randomFullAge(random)));
            }
        }
    }

    protected void generateShrub(Random random, World world, BlockPos pos)
    {
        do
        {
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();
            if (!block.isAir(state, world, pos) && !block.isLeaves(state, world, pos))
            {
                break;
            }
            pos = pos.down();
        } while (pos.getY() > 0);

        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (block == Blocks.NETHERRACK || block == Blocks.SOUL_SAND)
        {
            pos.up();

            for (int y = pos.getY(); y <= pos.getY() + 2; ++y)
            {
                int subtract = y - pos.getY();
                int subtract2 = 2 - subtract;

                for (int x = pos.getX() - subtract2; x <= pos.getX() + subtract2; ++x)
                {
                    int mathX = x - pos.getX();

                    for (int z = pos.getZ() - subtract2; z <= pos.getZ() + subtract2; ++z)
                    {
                        int mathZ = z - pos.getZ();

                        BlockPos blockpos = new BlockPos(x, y, z);
                        IBlockState blockState = world.getBlockState(blockpos);

                        if (Math.abs(mathX) != subtract2 || Math.abs(mathZ) != subtract2 || random.nextInt(2) != 0 && block.canBeReplacedByLeaves(blockState, world, blockpos))
                        {
                            this.setBlockAndMetadata(world, blockpos, this.berryBush.withProperty(BlockEnumBerryBush.AGE, this.randomFullAge(random)));
                        }
                    }
                }
            }
        }
    }

    protected void generateSmallNode(Random random, World world, BlockPos pos)
    {
        this.setBlockAndMetadata(world, pos, this.berryBush.withProperty(BlockEnumBerryBush.AGE, this.randomFullAge(random)));

        if (random.nextBoolean())
        {
            this.setBlockAndMetadata(world, pos.east(), this.berryBush.withProperty(BlockEnumBerryBush.AGE, this.randomAge(random)));
        }

        if (random.nextBoolean())
        {
            this.setBlockAndMetadata(world, pos.west(), this.berryBush.withProperty(BlockEnumBerryBush.AGE, this.randomAge(random)));
        }

        if (random.nextBoolean())
        {
            this.setBlockAndMetadata(world, pos.south(), this.berryBush.withProperty(BlockEnumBerryBush.AGE, this.randomAge(random)));
        }

        if (random.nextBoolean())
        {
            this.setBlockAndMetadata(world, pos.north(), this.berryBush.withProperty(BlockEnumBerryBush.AGE, this.randomAge(random)));
        }
    }

    protected void generateTinyNode(Random random, World world, BlockPos pos)
    {
        this.setBlockAndMetadata(world, pos, this.berryBush.withProperty(BlockEnumBerryBush.AGE, this.randomAge(random)));
    }

    protected void setBlockAndMetadata(World world, BlockPos pos, IBlockState stateNew)
    {
        if (!world.getBlockState(pos).isOpaqueCube())
        {
            world.setBlockState(pos, stateNew, 2);
        }
    }

    BlockPos findGround(World world, BlockPos pos)
    {
        int returnHeight = -1;
        IBlockState downState = world.getBlockState(pos.down());
        Block downBlock = downState.getBlock();

        if (!world.isAirBlock(pos.down()) && !world.getBlockState(pos).isOpaqueCube() && (downBlock == Blocks.NETHERRACK || downBlock.canSustainPlant(downState, world, pos.down(), EnumFacing.UP, (IPlantable) this.berryBush.getBlock())))
        {
            return pos;
        }

        int height = pos.getX();

        BlockPos position = new BlockPos(pos.getX(), height, pos.getZ());

        do
        {
            IBlockState state = world.getBlockState(position);
            Block block = state.getBlock();
            if (!world.isAirBlock(position) && block == Blocks.NETHERRACK || block.canSustainPlant(state, world, position, EnumFacing.UP, (IPlantable) this.berryBush.getBlock()))
            {
                if (!world.getBlockState(position.up()).isOpaqueCube())
                {
                    returnHeight = height + 1;
                }
                break;
            }
            height--;
            position = position.down();
        } while (height > 0);

        return new BlockPos(pos.getX(), returnHeight, pos.getZ());
    }

    int randomFullAge(Random random)
    {
        int fruiting = random.nextInt(5) == 0 ? 1 : 0;
        return 2 + fruiting;
    }

    int randomAge(Random random)
    {
        return random.nextInt(3);
    }
}
