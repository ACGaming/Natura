package com.progwml6.natura.world.worldgen.berry.overworld;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.progwml6.natura.common.block.BlockEnumBerryBush;
import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.world.worldgen.berry.BaseBerryBushGenerator;

public class OverworldBerryBushGenerator extends BaseBerryBushGenerator
{
    public final int spawnHeight;

    public final IBlockState berryBush;

    public OverworldBerryBushGenerator(int spawnHeight, IBlockState berryBush)
    {
        this.spawnHeight = spawnHeight;
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
        boolean isSoil = block.canSustainPlant(state, world, pos, EnumFacing.UP, NaturaOverworld.overworldBerryBushBlackberry);

        if (isSoil)
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
                        Block blockFromState = state.getBlock();

                        if (Math.abs(mathX) != subtract2 || Math.abs(mathZ) != subtract2 || random.nextInt(2) != 0 && blockFromState.canBeReplacedByLeaves(blockState, world, blockpos))
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
        BlockPos posDown = pos.down();
        IBlockState stateDown = world.getBlockState(posDown);
        Block blockDown = stateDown.getBlock();
        boolean isSoilDown = blockDown.canSustainPlant(stateDown, world, posDown, EnumFacing.UP, NaturaOverworld.overworldBerryBushBlackberry);

        if (!world.getBlockState(pos).isOpaqueCube() && isSoilDown)
        {
            return pos;
        }

        int height = this.spawnHeight;

        do
        {
            BlockPos position = new BlockPos(pos.getX(), height, pos.getZ());

            if (height < Config.seaLevel)
            {
                break;
            }

            IBlockState state = world.getBlockState(position);
            Block block = state.getBlock();
            boolean isSoil = block.canSustainPlant(state, world, position, EnumFacing.UP, NaturaOverworld.overworldBerryBushBlackberry);

            if (isSoil)
            {
                if (!world.getBlockState(position.up()).isOpaqueCube())
                {
                    returnHeight = height + 1;
                }
                break;
            }

            height--;
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
