package com.progwml6.natura.world.worldgen.trees.overworld;

import java.util.List;
import java.util.Random;
import com.google.common.collect.Lists;
import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.world.worldgen.trees.BaseTreeGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;

public class RedwoodTreeGenerator extends BaseTreeGenerator
{
    public final IBlockState bark;
    public final IBlockState heart;
    public final IBlockState root;
    public final IBlockState leaves;
    public final boolean useHeight;
    public final boolean isSapling;
    int heightLimit;
    int height;
    double heightAttenuation = 0.618D;
    double branchSlope = 0.381D;
    double scaleWidth = 1.0D;
    double leafDensity = 1.0D;
    int heightLimitLimit = 12;
    /**
     * Sets the distance limit for how far away the generator will populate leaves from the base leaf node.
     */
    int leafDistanceLimit = 4;
    List<FoliageCoordinates> foliageCoords;
    private Random rand;
    private World world;
    private BlockPos basePos = BlockPos.ORIGIN;

    public RedwoodTreeGenerator(IBlockState bark, IBlockState heart, IBlockState root, IBlockState leaves, boolean useHeight, boolean isSapling)
    {
        this.bark = bark;
        this.heart = heart;
        this.root = root;

        this.leaves = leaves;
        this.useHeight = useHeight;
        this.isSapling = isSapling;
    }

    public RedwoodTreeGenerator(IBlockState log, IBlockState heart, IBlockState root, IBlockState leaves)
    {
        this(log, heart, root, leaves, true, true);
    }

    public boolean isReplaceable(World world, BlockPos pos)
    {
        IBlockState state = world.getBlockState(pos);
        return state.getBlock().isAir(state, world, pos) || state.getBlock().isLeaves(state, world, pos) || state.getBlock().isReplaceable(world, pos) || state.getBlock().isWood(world, pos) || this.canGrowInto(state.getBlock());
    }

    public boolean isValidSpawn(World world, BlockPos pos)
    {
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        boolean ground = block.canSustainPlant(state, world, pos, EnumFacing.UP, NaturaOverworld.redwoodSapling);
        boolean transparent = !world.getBlockState(pos.up()).isFullBlock();

        return ground && transparent;
    }

    @Override
    public void generateTree(Random random, World world, BlockPos pos)
    {
        BlockPos groundPosition = pos;

        if (!this.useHeight)
        {
            groundPosition = this.findGround(world, pos);

            if (!this.isValidSpawn(world, groundPosition))
            {
                return;
            }
        }

        int treeHeight = random.nextInt(60) + 80;

        this.world = world;
        this.basePos = groundPosition;
        this.rand = new Random(random.nextLong());
        this.heightLimit = 5 + this.rand.nextInt(this.heightLimitLimit);

        if (treeHeight > 120)
        {
            for (int currentHeight = 0; currentHeight < treeHeight; currentHeight++)
            {
                if (currentHeight < treeHeight / 10)
                {
                    this.genRing13(world, groundPosition.up(currentHeight));
                }
                else if (currentHeight < treeHeight * 2 / 10)
                {
                    this.genRing12(world, groundPosition.up(currentHeight));
                }
                else if (currentHeight < treeHeight * 3 / 10)
                {
                    this.genRing11(world, groundPosition.up(currentHeight));
                }
                else if (currentHeight < treeHeight * 4 / 10)
                {
                    this.genRing10(world, groundPosition.up(currentHeight));
                }
                else if (currentHeight < treeHeight * 5 / 10)
                {
                    this.genRing9(world, groundPosition.up(currentHeight));

                    this.growLowBranch(random, groundPosition.up(currentHeight));
                }
                else if (currentHeight < treeHeight * 6 / 10)
                {
                    this.genRing8(world, groundPosition.up(currentHeight));

                    this.growLowBranch(random, groundPosition.up(currentHeight));
                }
                else if (currentHeight < treeHeight * 7 / 10)
                {
                    this.genRing7(world, groundPosition.up(currentHeight));

                    this.growMiddleBranch(random, groundPosition.up(currentHeight));
                }
                else if (currentHeight < treeHeight * 8 / 10)
                {
                    this.genRing6(world, groundPosition.up(currentHeight));

                    this.growMiddleBranch(random, groundPosition.up(currentHeight));
                }
                else if (currentHeight < treeHeight * 9 / 10)
                {
                    this.genRing5(world, groundPosition.up(currentHeight));

                    this.growHighBranch(random, groundPosition.up(currentHeight));
                }
                else
                {
                    this.genRing3(world, groundPosition.up(currentHeight));

                    this.growHighBranch(random, groundPosition.up(currentHeight));
                }
            }

            this.growBigRoots(world, groundPosition.down());

            this.growTop(groundPosition.up(this.height));
        }
        else if (treeHeight > 100)
        {
            for (int currentHeight = 0; currentHeight < treeHeight; currentHeight++)
            {
                if (currentHeight < treeHeight / 8)
                {
                    this.genRing11(world, groundPosition.up(currentHeight));
                }
                else if (currentHeight < treeHeight * 2 / 8)
                {
                    this.genRing10(world, groundPosition.up(currentHeight));
                }
                else if (currentHeight < treeHeight * 3 / 8)
                {
                    this.genRing9(world, groundPosition.up(currentHeight));
                }
                else if (currentHeight < treeHeight * 4 / 8)
                {
                    this.genRing8(world, groundPosition.up(currentHeight));
                }
                else if (currentHeight < treeHeight * 5 / 8)
                {
                    this.genRing7(world, groundPosition.up(currentHeight));

                    this.growMiddleBranch(random, groundPosition.up(currentHeight));
                }
                else if (currentHeight < treeHeight * 6 / 8)
                {
                    this.genRing6(world, groundPosition.up(currentHeight));

                    this.growMiddleBranch(random, groundPosition.up(currentHeight));
                }
                else if (currentHeight < treeHeight * 7 / 8)
                {
                    this.genRing5(world, groundPosition.up(currentHeight));

                    this.growHighBranch(random, groundPosition.up(currentHeight));
                }
                else
                {
                    this.genRing3(world, groundPosition.up(currentHeight));

                    this.growHighBranch(random, groundPosition.up(currentHeight));
                }
            }

            this.growMediumRoots(world, groundPosition.down());

            this.growTop(groundPosition.up(this.height));
        }
        else
        {
            for (int currentHeight = 0; currentHeight < treeHeight; currentHeight++)
            {
                if (currentHeight < treeHeight / 6)
                {
                    this.genRing9(world, groundPosition.up(currentHeight));
                }
                else if (currentHeight < treeHeight * 2 / 6)
                {
                    this.genRing8(world, groundPosition.up(currentHeight));
                }
                else if (currentHeight < treeHeight * 3 / 6)
                {
                    this.genRing7(world, groundPosition.up(currentHeight));
                }
                else if (currentHeight < treeHeight * 4 / 6)
                {
                    this.genRing6(world, groundPosition.up(currentHeight));

                    this.growMiddleBranch(random, groundPosition.up(currentHeight));
                }
                else if (currentHeight < treeHeight * 5 / 6)
                {
                    this.genRing5(world, groundPosition.up(currentHeight));

                    this.growHighBranch(random, groundPosition.up(currentHeight));
                }
                else
                {
                    this.genRing3(world, groundPosition.up(currentHeight));

                    this.growHighBranch(random, groundPosition.up(currentHeight));
                }
            }

            this.growSmallRoots(world, groundPosition.down());

            this.growTop(groundPosition.up(this.height));
        }
    }

    public void growTop(BlockPos pos)
    {
        this.basePos = pos.up(4);
        this.generateLeafNodeList();
        this.generateLeaves();
        this.generateLeafNodeBases();

        this.basePos = pos.up(4);
        this.generateLeafNodeList();
        this.generateLeaves();
        this.generateLeafNodeBases();

        this.basePos = pos;
        this.generateLeafNodeList();
        this.generateLeaves();
        this.generateLeafNodeBases();

        this.basePos = pos;
        this.generateLeafNodeList();
        this.generateLeaves();
        this.generateLeafNodeBases();
    }

    public void growHighBranch(Random random, BlockPos pos)
    {
        int xPos = pos.getX();
        int yPos = pos.getY();
        int zPos = pos.getZ();

        for (int iter = 0; iter < 3; iter++)
        {
            this.basePos = new BlockPos((xPos + random.nextInt(21)) - 10, yPos, (zPos + random.nextInt(21)) - 10);
            this.generateLeafNodeList();
            this.generateLeaves();
            this.generateLeafNodeBases();
        }
    }

    public void growMiddleBranch(Random random, BlockPos pos)
    {
        int xPos = pos.getX();
        int yPos = pos.getY();
        int zPos = pos.getZ();

        for (int iter = 0; iter < 6; iter++)
        {
            this.basePos = new BlockPos((xPos + random.nextInt(31)) - 15, yPos, (zPos + random.nextInt(31)) - 15);
            this.generateLeafNodeList();
            this.generateLeaves();
            this.generateLeafNodeBases();
        }
    }

    public void growLowBranch(Random random, BlockPos pos)
    {
        int xPos = pos.getX();
        int yPos = pos.getY();
        int zPos = pos.getZ();

        this.basePos = new BlockPos((xPos + random.nextInt(17)) - 8, yPos, (zPos + random.nextInt(17)) - 8);
        this.generateLeafNodeList();
        this.generateLeaves();
        this.generateLeafNodeBases();

        if (random.nextInt(2) == 0)
        {
            this.basePos = new BlockPos((xPos + random.nextInt(17)) - 8, yPos, (zPos + random.nextInt(17)) - 8);

            this.generateLeafNodeList();
            this.generateLeaves();
            this.generateLeafNodeBases();
        }

        this.basePos = new BlockPos((xPos + random.nextInt(17)) - 8, yPos, (zPos + random.nextInt(17)) - 8);
        this.generateLeafNodeList();
        this.generateLeaves();
        this.generateLeafNodeBases();
    }

    public void growSmallRoots(World world, BlockPos pos)
    {
        this.genRing9(world, pos);

        this.smallRoot1(world, pos.down(1));
        this.smallRoot1(world, pos.down(2));
        this.smallRoot1(world, pos.down(3));

        this.smallRoot2(world, pos.down(4));
        this.smallRoot2(world, pos.down(5));

        this.smallRoot3(world, pos.down(6));
        this.smallRoot3(world, pos.down(7));
        this.smallRoot3(world, pos.down(8));
        this.smallRoot3(world, pos.down(9));

        this.smallRoot4(world, pos.down(10));
        this.smallRoot4(world, pos.down(11));
    }

    public void growMediumRoots(World world, BlockPos pos)
    {
        this.genRing11(world, pos);

        this.mediumRoot1(world, pos.down(1));
        this.mediumRoot1(world, pos.down(2));
        this.mediumRoot1(world, pos.down(3));

        this.mediumRoot2(world, pos.down(4));
        this.mediumRoot2(world, pos.down(5));

        this.mediumRoot3(world, pos.down(6));
        this.mediumRoot3(world, pos.down(7));
        this.mediumRoot3(world, pos.down(8));
        this.mediumRoot3(world, pos.down(9));

        this.mediumRoot4(world, pos.down(10));
        this.mediumRoot4(world, pos.down(11));

        this.mediumRoot5(world, pos.down(12));
        this.mediumRoot5(world, pos.down(13));
        this.mediumRoot5(world, pos.down(14));
    }

    public void growBigRoots(World world, BlockPos pos)
    {
        this.genRing13(world, pos);

        this.bigRoot1(world, pos.down(1));
        this.bigRoot1(world, pos.down(2));
        this.bigRoot1(world, pos.down(3));

        this.bigRoot2(world, pos.down(4));
        this.bigRoot2(world, pos.down(5));

        this.bigRoot3(world, pos.down(6));
        this.bigRoot3(world, pos.down(7));
        this.bigRoot3(world, pos.down(8));
        this.bigRoot3(world, pos.down(9));

        this.bigRoot4(world, pos.down(10));
        this.bigRoot4(world, pos.down(11));

        this.bigRoot5(world, pos.down(12));
        this.bigRoot5(world, pos.down(13));
        this.bigRoot5(world, pos.down(14));

        this.bigRoot6(world, pos.down(15));
        this.bigRoot6(world, pos.down(16));
        this.bigRoot6(world, pos.down(17));
        this.bigRoot6(world, pos.down(18));
    }

    public void smallRoot1(World world, BlockPos pos)
    {
        if (world.getBlockState(pos).getBlock() != Blocks.BEDROCK && pos.getY() > 0)
        {
            this.setBlockAndMetadata(world, pos.add(-4, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 1), this.root);

            this.setBlockAndMetadata(world, pos.add(-3, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 3), this.root);

            this.setBlockAndMetadata(world, pos.add(-2, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 3), this.root);

            this.setBlockAndMetadata(world, pos.add(-1, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 4), this.root);

            this.setBlockAndMetadata(world, pos.add(1, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 4), this.root);

            this.setBlockAndMetadata(world, pos.add(2, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 3), this.root);

            this.setBlockAndMetadata(world, pos.add(3, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, 3), this.root);

            this.setBlockAndMetadata(world, pos.add(4, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(4, 0, 1), this.root);
        }
    }

    public void smallRoot2(World world, BlockPos pos)
    {
        if (world.getBlockState(pos).getBlock() != Blocks.BEDROCK && pos.getY() > 0)
        {
            this.setBlockAndMetadata(world, pos.add(-4, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 1), this.root);

            this.setBlockAndMetadata(world, pos.add(-3, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 3), this.root);

            this.setBlockAndMetadata(world, pos.add(-2, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 3), this.root);

            this.setBlockAndMetadata(world, pos.add(-1, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 4), this.root);

            this.setBlockAndMetadata(world, pos.add(1, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 4), this.root);

            this.setBlockAndMetadata(world, pos.add(2, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 3), this.root);

            this.setBlockAndMetadata(world, pos.add(3, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, 3), this.root);

            this.setBlockAndMetadata(world, pos.add(4, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(4, 0, 1), this.root);
        }
    }

    public void smallRoot3(World world, BlockPos pos)
    {
        if (world.getBlockState(pos).getBlock() != Blocks.BEDROCK && pos.getY() > 0)
        {
            this.setBlockAndMetadata(world, pos.add(-2, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 2), this.root);

            this.setBlockAndMetadata(world, pos.add(-1, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 3), this.root);

            this.setBlockAndMetadata(world, pos.add(1, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 3), this.root);

            this.setBlockAndMetadata(world, pos.add(2, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 2), this.root);
        }
    }

    public void smallRoot4(World world, BlockPos pos)
    {
        if (world.getBlockState(pos).getBlock() != Blocks.BEDROCK && pos.getY() > 0)
        {
            this.setBlockAndMetadata(world, pos.add(-1, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 2), this.root);

            this.setBlockAndMetadata(world, pos.add(1, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 2), this.root);
        }
    }

    public void mediumRoot1(World world, BlockPos pos)
    {
        if (world.getBlockState(pos).getBlock() != Blocks.BEDROCK && pos.getY() > 0)
        {
            this.setBlockAndMetadata(world, pos.add(-5, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(-5, 0, 1), this.root);

            this.setBlockAndMetadata(world, pos.add(-4, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-4, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(-4, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 3), this.root);

            this.setBlockAndMetadata(world, pos.add(-3, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 4), this.root);

            this.setBlockAndMetadata(world, pos.add(-2, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 4), this.root);

            this.setBlockAndMetadata(world, pos.add(-1, 0, -5), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 4), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 5), this.root);

            this.setBlockAndMetadata(world, pos.add(1, 0, -5), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 4), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 5), this.root);

            this.setBlockAndMetadata(world, pos.add(2, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 4), this.root);

            this.setBlockAndMetadata(world, pos.add(3, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, 4), this.root);

            this.setBlockAndMetadata(world, pos.add(4, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(4, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(4, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(4, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(4, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(4, 0, 3), this.root);

            this.setBlockAndMetadata(world, pos.add(5, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(5, 0, 1), this.root);
        }
    }

    public void mediumRoot2(World world, BlockPos pos)
    {
        if (world.getBlockState(pos).getBlock() != Blocks.BEDROCK && pos.getY() > 0)
        {
            this.setBlockAndMetadata(world, pos.add(-4, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 1), this.root);

            this.setBlockAndMetadata(world, pos.add(-3, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 2), this.root);

            this.setBlockAndMetadata(world, pos.add(-2, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 3), this.root);

            this.setBlockAndMetadata(world, pos.add(-1, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 4), this.root);

            this.setBlockAndMetadata(world, pos.add(1, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 4), this.root);

            this.setBlockAndMetadata(world, pos.add(2, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 3), this.root);

            this.setBlockAndMetadata(world, pos.add(3, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, 2), this.root);

            this.setBlockAndMetadata(world, pos.add(4, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(4, 0, 1), this.root);
        }
    }

    public void mediumRoot3(World world, BlockPos pos)
    {
        if (world.getBlockState(pos).getBlock() != Blocks.BEDROCK && pos.getY() > 0)
        {
            this.setBlockAndMetadata(world, pos.add(-3, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 2), this.root);

            this.setBlockAndMetadata(world, pos.add(-2, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 3), this.root);

            this.setBlockAndMetadata(world, pos.add(-1, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 4), this.root);

            this.setBlockAndMetadata(world, pos.add(1, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 4), this.root);

            this.setBlockAndMetadata(world, pos.add(2, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 3), this.root);

            this.setBlockAndMetadata(world, pos.add(3, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, 2), this.root);
        }
    }

    public void mediumRoot4(World world, BlockPos pos)
    {
        if (world.getBlockState(pos).getBlock() != Blocks.BEDROCK && pos.getY() > 0)
        {
            this.setBlockAndMetadata(world, pos.add(-2, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 2), this.root);

            this.setBlockAndMetadata(world, pos.add(-1, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 3), this.root);

            this.setBlockAndMetadata(world, pos.add(1, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 3), this.root);

            this.setBlockAndMetadata(world, pos.add(2, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 2), this.root);
        }
    }

    public void mediumRoot5(World world, BlockPos pos)
    {
        if (world.getBlockState(pos).getBlock() != Blocks.BEDROCK && pos.getY() > 0)
        {
            this.setBlockAndMetadata(world, pos.add(-1, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 3), this.root);
        }
    }

    public void bigRoot1(World world, BlockPos pos)
    {
        if (world.getBlockState(pos).getBlock() != Blocks.BEDROCK && pos.getY() > 0)
        {
            this.setBlockAndMetadata(world, pos.add(-6, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(-6, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(-6, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(-6, 0, 2), this.root);

            this.setBlockAndMetadata(world, pos.add(-5, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(-5, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-5, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(-5, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(-5, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(-5, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(-5, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(-5, 0, 4), this.root);

            this.setBlockAndMetadata(world, pos.add(-4, 0, -5), this.root);
            this.setBlockAndMetadata(world, pos.add(-4, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(-4, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-4, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(-4, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 4), this.root);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 5), this.root);

            this.setBlockAndMetadata(world, pos.add(-3, 0, -5), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 4), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 5), this.root);

            this.setBlockAndMetadata(world, pos.add(-2, 0, -6), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -5), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 4), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 5), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 6), this.root);

            this.setBlockAndMetadata(world, pos.add(-1, 0, -6), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -5), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 4), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 5), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 6), this.root);

            this.setBlockAndMetadata(world, pos.add(1, 0, -6), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, -5), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 4), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 5), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 6), this.root);

            this.setBlockAndMetadata(world, pos.add(2, 0, -6), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, -5), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 4), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 5), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 6), this.root);

            this.setBlockAndMetadata(world, pos.add(3, 0, -5), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, 4), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, 5), this.root);

            this.setBlockAndMetadata(world, pos.add(4, 0, -5), this.root);
            this.setBlockAndMetadata(world, pos.add(4, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(4, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(4, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(4, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(4, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(4, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(4, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(4, 0, 4), this.root);
            this.setBlockAndMetadata(world, pos.add(4, 0, 5), this.root);

            this.setBlockAndMetadata(world, pos.add(5, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(5, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(5, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(5, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(5, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(5, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(5, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(5, 0, 4), this.root);

            this.setBlockAndMetadata(world, pos.add(6, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(6, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(6, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(6, 0, 2), this.root);
        }
    }

    public void bigRoot2(World world, BlockPos pos)
    {
        if (world.getBlockState(pos).getBlock() != Blocks.BEDROCK && pos.getY() > 0)
        {
            this.setBlockAndMetadata(world, pos.add(-5, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(-5, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-5, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(-5, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(-5, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(-5, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(-5, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(-5, 0, 4), this.root);

            this.setBlockAndMetadata(world, pos.add(-4, 0, -5), this.root);
            this.setBlockAndMetadata(world, pos.add(-4, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(-4, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-4, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(-4, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 4), this.root);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 5), this.root);

            this.setBlockAndMetadata(world, pos.add(-3, 0, -5), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 4), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 5), this.root);

            this.setBlockAndMetadata(world, pos.add(-2, 0, -6), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -5), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 4), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 5), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 6), this.root);

            this.setBlockAndMetadata(world, pos.add(-1, 0, -6), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -5), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 4), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 5), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 6), this.root);

            this.setBlockAndMetadata(world, pos.add(1, 0, -6), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, -5), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 4), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 5), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 6), this.root);

            this.setBlockAndMetadata(world, pos.add(2, 0, -6), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, -5), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 4), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 5), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 6), this.root);

            this.setBlockAndMetadata(world, pos.add(3, 0, -5), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, 4), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, 5), this.root);

            this.setBlockAndMetadata(world, pos.add(4, 0, -5), this.root);
            this.setBlockAndMetadata(world, pos.add(4, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(4, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(4, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(4, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(4, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(4, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(4, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(4, 0, 4), this.root);
            this.setBlockAndMetadata(world, pos.add(4, 0, 5), this.root);

            this.setBlockAndMetadata(world, pos.add(5, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(5, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(5, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(5, 0, -1), this.root);
            this.setBlockAndMetadata(world, pos.add(5, 0, 1), this.root);
            this.setBlockAndMetadata(world, pos.add(5, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(5, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(5, 0, 4), this.root);
        }
    }

    //TODO: CHANGE EVERYTHING BELOW.

    public void bigRoot3(World world, BlockPos pos)
    {
        if (world.getBlockState(pos).getBlock() != Blocks.BEDROCK && pos.getY() > 0)
        {
            this.setBlockAndMetadata(world, pos.add(-4, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-4, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 3), this.root);

            this.setBlockAndMetadata(world, pos.add(-3, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 4), this.root);

            this.setBlockAndMetadata(world, pos.add(-2, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 4), this.root);

            this.setBlockAndMetadata(world, pos.add(-1, 0, -5), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 4), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 5), this.root);

            this.setBlockAndMetadata(world, pos.add(1, 0, -5), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 4), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 5), this.root);

            this.setBlockAndMetadata(world, pos.add(2, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 4), this.root);

            this.setBlockAndMetadata(world, pos.add(3, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, 4), this.root);

            this.setBlockAndMetadata(world, pos.add(4, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(4, 0, -2), this.root);
            this.setBlockAndMetadata(world, pos.add(4, 0, 2), this.root);
            this.setBlockAndMetadata(world, pos.add(4, 0, 3), this.root);
        }
    }

    public void bigRoot4(World world, BlockPos pos)
    {
        if (world.getBlockState(pos).getBlock() != Blocks.BEDROCK && pos.getY() > 0)
        {
            this.setBlockAndMetadata(world, pos.add(-4, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 3), this.root);

            this.setBlockAndMetadata(world, pos.add(-3, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 4), this.root);

            this.setBlockAndMetadata(world, pos.add(-2, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 4), this.root);

            this.setBlockAndMetadata(world, pos.add(-1, 0, -5), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 4), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 5), this.root);

            this.setBlockAndMetadata(world, pos.add(1, 0, -5), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 4), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 5), this.root);

            this.setBlockAndMetadata(world, pos.add(2, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 4), this.root);

            this.setBlockAndMetadata(world, pos.add(3, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, 4), this.root);

            this.setBlockAndMetadata(world, pos.add(4, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(4, 0, 3), this.root);
        }
    }

    public void bigRoot5(World world, BlockPos pos)
    {
        if (world.getBlockState(pos).getBlock() != Blocks.BEDROCK && pos.getY() > 0)
        {
            this.setBlockAndMetadata(world, pos.add(-3, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 3), this.root);

            this.setBlockAndMetadata(world, pos.add(-2, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 4), this.root);

            this.setBlockAndMetadata(world, pos.add(-1, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 4), this.root);

            this.setBlockAndMetadata(world, pos.add(1, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(1, 0, 4), this.root);

            this.setBlockAndMetadata(world, pos.add(2, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 4), this.root);

            this.setBlockAndMetadata(world, pos.add(3, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(3, 0, 3), this.root);
        }
    }

    public void bigRoot6(World world, BlockPos pos)
    {
        if (world.getBlockState(pos).getBlock() != Blocks.BEDROCK && pos.getY() > 0)
        {
            this.setBlockAndMetadata(world, pos.add(-2, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 4), this.root);

            this.setBlockAndMetadata(world, pos.add(2, 0, -3), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, -4), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 3), this.root);
            this.setBlockAndMetadata(world, pos.add(2, 0, 4), this.root);
        }
    }

    public void genRing13(World world, BlockPos pos)
    {
        if (world.getBlockState(pos).getBlock() != Blocks.BEDROCK && pos.getY() > 0)
        {
            this.setBlockAndMetadata(world, pos.add(-6, 0, -2), this.bark);
            this.setBlockAndMetadata(world, pos.add(-6, 0, -1), this.bark);
            this.setBlockAndMetadata(world, pos.add(-6, 0, 0), this.bark);
            this.setBlockAndMetadata(world, pos.add(-6, 0, 1), this.bark);
            this.setBlockAndMetadata(world, pos.add(-6, 0, 2), this.bark);

            this.setBlockAndMetadata(world, pos.add(-5, 0, -4), this.bark);
            this.setBlockAndMetadata(world, pos.add(-5, 0, -3), this.bark);
            this.setBlockAndMetadata(world, pos.add(-5, 0, -2), this.bark);
            this.setBlockAndMetadata(world, pos.add(-5, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-5, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(-5, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-5, 0, 2), this.bark);
            this.setBlockAndMetadata(world, pos.add(-5, 0, 3), this.bark);
            this.setBlockAndMetadata(world, pos.add(-5, 0, 4), this.bark);

            this.setBlockAndMetadata(world, pos.add(-4, 0, -5), this.bark);
            this.setBlockAndMetadata(world, pos.add(-4, 0, -4), this.bark);
            this.setBlockAndMetadata(world, pos.add(-4, 0, -3), this.heart);
            this.setBlockAndMetadata(world, pos.add(-4, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(-4, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 3), this.heart);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 4), this.bark);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 5), this.bark);

            this.setBlockAndMetadata(world, pos.add(-3, 0, -5), this.bark);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -4), this.heart);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -3), this.heart);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 3), this.heart);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 4), this.heart);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 5), this.bark);

            this.setBlockAndMetadata(world, pos.add(-2, 0, -6), this.bark);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -5), this.bark);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -4), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -3), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 3), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 4), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 5), this.bark);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 6), this.bark);

            this.setBlockAndMetadata(world, pos.add(-1, 0, -6), this.bark);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -5), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -4), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -3), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 3), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 4), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 5), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 6), this.bark);

            this.setBlockAndMetadata(world, pos.add(0, 0, -6), this.bark);
            this.setBlockAndMetadata(world, pos.add(0, 0, -5), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, -4), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, -3), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, -1), this.heart);

            this.setBlockAndMetadata(world, pos, this.heart);

            this.setBlockAndMetadata(world, pos.add(0, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, 3), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, 4), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, 5), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, 6), this.bark);

            this.setBlockAndMetadata(world, pos.add(1, 0, -6), this.bark);
            this.setBlockAndMetadata(world, pos.add(1, 0, -5), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, -4), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, -3), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 3), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 4), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 5), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 6), this.bark);

            this.setBlockAndMetadata(world, pos.add(2, 0, -6), this.bark);
            this.setBlockAndMetadata(world, pos.add(2, 0, -5), this.bark);
            this.setBlockAndMetadata(world, pos.add(2, 0, -4), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, -3), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, 3), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, 4), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, 5), this.bark);
            this.setBlockAndMetadata(world, pos.add(2, 0, 6), this.bark);

            this.setBlockAndMetadata(world, pos.add(3, 0, -5), this.bark);
            this.setBlockAndMetadata(world, pos.add(3, 0, -4), this.heart);
            this.setBlockAndMetadata(world, pos.add(3, 0, -3), this.heart);
            this.setBlockAndMetadata(world, pos.add(3, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(3, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(3, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(3, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(3, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(3, 0, 3), this.heart);
            this.setBlockAndMetadata(world, pos.add(3, 0, 4), this.heart);
            this.setBlockAndMetadata(world, pos.add(3, 0, 5), this.bark);

            this.setBlockAndMetadata(world, pos.add(4, 0, -5), this.bark);
            this.setBlockAndMetadata(world, pos.add(4, 0, -4), this.bark);
            this.setBlockAndMetadata(world, pos.add(4, 0, -3), this.heart);
            this.setBlockAndMetadata(world, pos.add(4, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(4, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(4, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(4, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(4, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(4, 0, 3), this.heart);
            this.setBlockAndMetadata(world, pos.add(4, 0, 4), this.bark);
            this.setBlockAndMetadata(world, pos.add(4, 0, 5), this.bark);

            this.setBlockAndMetadata(world, pos.add(5, 0, -4), this.bark);
            this.setBlockAndMetadata(world, pos.add(5, 0, -3), this.bark);
            this.setBlockAndMetadata(world, pos.add(5, 0, -2), this.bark);
            this.setBlockAndMetadata(world, pos.add(5, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(5, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(5, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(5, 0, 2), this.bark);
            this.setBlockAndMetadata(world, pos.add(5, 0, 3), this.bark);
            this.setBlockAndMetadata(world, pos.add(5, 0, 4), this.bark);

            this.setBlockAndMetadata(world, pos.add(6, 0, -2), this.bark);
            this.setBlockAndMetadata(world, pos.add(6, 0, -1), this.bark);
            this.setBlockAndMetadata(world, pos.add(6, 0, 0), this.bark);
            this.setBlockAndMetadata(world, pos.add(6, 0, 1), this.bark);
            this.setBlockAndMetadata(world, pos.add(6, 0, 2), this.bark);
        }
    }

    public void genRing12(World world, BlockPos pos)
    {
        if (world.getBlockState(pos).getBlock() != Blocks.BEDROCK && pos.getY() > 0)
        {
            this.setBlockAndMetadata(world, pos.add(-6, 0, -1), this.bark);
            this.setBlockAndMetadata(world, pos.add(-6, 0, 0), this.bark);
            this.setBlockAndMetadata(world, pos.add(-6, 0, 1), this.bark);

            this.setBlockAndMetadata(world, pos.add(-5, 0, -3), this.bark);
            this.setBlockAndMetadata(world, pos.add(-5, 0, -2), this.bark);
            this.setBlockAndMetadata(world, pos.add(-5, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-5, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(-5, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-5, 0, 2), this.bark);
            this.setBlockAndMetadata(world, pos.add(-5, 0, 3), this.bark);

            this.setBlockAndMetadata(world, pos.add(-4, 0, -4), this.bark);
            this.setBlockAndMetadata(world, pos.add(-4, 0, -3), this.heart);
            this.setBlockAndMetadata(world, pos.add(-4, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(-4, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 3), this.heart);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 4), this.bark);

            this.setBlockAndMetadata(world, pos.add(-3, 0, -5), this.bark);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -4), this.heart);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -3), this.heart);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 3), this.heart);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 4), this.heart);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 5), this.bark);

            this.setBlockAndMetadata(world, pos.add(-2, 0, -6), this.bark);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -5), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -4), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -3), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 3), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 4), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 5), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 6), this.bark);

            this.setBlockAndMetadata(world, pos.add(-1, 0, -6), this.bark);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -5), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -4), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -3), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 3), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 4), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 5), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 6), this.bark);

            this.setBlockAndMetadata(world, pos.add(0, 0, -6), this.bark);
            this.setBlockAndMetadata(world, pos.add(0, 0, -5), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, -4), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, -3), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, -1), this.heart);

            this.setBlockAndMetadata(world, pos, this.heart);

            this.setBlockAndMetadata(world, pos.add(0, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, 3), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, 4), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, 5), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, 6), this.bark);

            this.setBlockAndMetadata(world, pos.add(1, 0, -6), this.bark);
            this.setBlockAndMetadata(world, pos.add(1, 0, -5), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, -4), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, -3), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 3), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 4), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 5), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 6), this.bark);

            this.setBlockAndMetadata(world, pos.add(2, 0, -6), this.bark);
            this.setBlockAndMetadata(world, pos.add(2, 0, -5), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, -4), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, -3), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, 3), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, 4), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, 5), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, 6), this.bark);

            this.setBlockAndMetadata(world, pos.add(3, 0, -5), this.bark);
            this.setBlockAndMetadata(world, pos.add(3, 0, -4), this.heart);
            this.setBlockAndMetadata(world, pos.add(3, 0, -3), this.heart);
            this.setBlockAndMetadata(world, pos.add(3, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(3, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(3, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(3, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(3, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(3, 0, 3), this.heart);
            this.setBlockAndMetadata(world, pos.add(3, 0, 4), this.heart);
            this.setBlockAndMetadata(world, pos.add(3, 0, 5), this.bark);

            this.setBlockAndMetadata(world, pos.add(4, 0, -4), this.bark);
            this.setBlockAndMetadata(world, pos.add(4, 0, -3), this.heart);
            this.setBlockAndMetadata(world, pos.add(4, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(4, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(4, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(4, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(4, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(4, 0, 3), this.heart);
            this.setBlockAndMetadata(world, pos.add(4, 0, 4), this.bark);

            this.setBlockAndMetadata(world, pos.add(5, 0, -3), this.bark);
            this.setBlockAndMetadata(world, pos.add(5, 0, -2), this.bark);
            this.setBlockAndMetadata(world, pos.add(5, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(5, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(5, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(5, 0, 2), this.bark);
            this.setBlockAndMetadata(world, pos.add(5, 0, 3), this.bark);

            this.setBlockAndMetadata(world, pos.add(6, 0, -1), this.bark);
            this.setBlockAndMetadata(world, pos.add(6, 0, 0), this.bark);
            this.setBlockAndMetadata(world, pos.add(6, 0, 1), this.bark);
        }
    }

    public void genRing11(World world, BlockPos pos)
    {
        if (world.getBlockState(pos).getBlock() != Blocks.BEDROCK && pos.getY() > 0)
        {
            this.setBlockAndMetadata(world, pos.add(-5, 0, -1), this.bark);
            this.setBlockAndMetadata(world, pos.add(-5, 0, 0), this.bark);
            this.setBlockAndMetadata(world, pos.add(-5, 0, 1), this.bark);

            this.setBlockAndMetadata(world, pos.add(-4, 0, -3), this.bark);
            this.setBlockAndMetadata(world, pos.add(-4, 0, -2), this.bark);
            this.setBlockAndMetadata(world, pos.add(-4, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 2), this.bark);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 3), this.bark);

            this.setBlockAndMetadata(world, pos.add(-3, 0, -4), this.bark);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -3), this.bark);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 3), this.bark);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 4), this.bark);

            this.setBlockAndMetadata(world, pos.add(-2, 0, -4), this.bark);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -3), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 3), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 4), this.bark);

            this.setBlockAndMetadata(world, pos.add(-1, 0, -5), this.bark);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -4), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -3), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 3), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 4), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 5), this.bark);

            this.setBlockAndMetadata(world, pos.add(0, 0, -5), this.bark);
            this.setBlockAndMetadata(world, pos.add(0, 0, -4), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, -3), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, -1), this.heart);

            this.setBlockAndMetadata(world, pos, this.heart);

            this.setBlockAndMetadata(world, pos.add(0, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, 3), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, 4), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, 5), this.bark);

            this.setBlockAndMetadata(world, pos.add(1, 0, -5), this.bark);
            this.setBlockAndMetadata(world, pos.add(1, 0, -4), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, -3), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 3), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 4), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 5), this.bark);

            this.setBlockAndMetadata(world, pos.add(2, 0, -4), this.bark);
            this.setBlockAndMetadata(world, pos.add(2, 0, -3), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, 3), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, 4), this.bark);

            this.setBlockAndMetadata(world, pos.add(3, 0, -4), this.bark);
            this.setBlockAndMetadata(world, pos.add(3, 0, -3), this.bark);
            this.setBlockAndMetadata(world, pos.add(3, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(3, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(3, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(3, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(3, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(3, 0, 3), this.bark);
            this.setBlockAndMetadata(world, pos.add(3, 0, 4), this.bark);

            this.setBlockAndMetadata(world, pos.add(4, 0, -3), this.bark);
            this.setBlockAndMetadata(world, pos.add(4, 0, -2), this.bark);
            this.setBlockAndMetadata(world, pos.add(4, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(4, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(4, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(4, 0, 2), this.bark);
            this.setBlockAndMetadata(world, pos.add(4, 0, 3), this.bark);

            this.setBlockAndMetadata(world, pos.add(5, 0, -1), this.bark);
            this.setBlockAndMetadata(world, pos.add(5, 0, 0), this.bark);
            this.setBlockAndMetadata(world, pos.add(5, 0, 1), this.bark);
        }
    }

    public void genRing10(World world, BlockPos pos)
    {
        if (world.getBlockState(pos).getBlock() != Blocks.BEDROCK && pos.getY() > 0)
        {
            this.setBlockAndMetadata(world, pos.add(-4, 0, -2), this.bark);
            this.setBlockAndMetadata(world, pos.add(-4, 0, -1), this.bark);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 0), this.bark);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 1), this.bark);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 2), this.bark);

            this.setBlockAndMetadata(world, pos.add(-3, 0, -3), this.bark);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -2), this.bark);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 2), this.bark);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 3), this.bark);

            this.setBlockAndMetadata(world, pos.add(-2, 0, -4), this.bark);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -3), this.bark);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 3), this.bark);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 4), this.bark);

            this.setBlockAndMetadata(world, pos.add(-1, 0, -4), this.bark);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -3), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 3), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 4), this.bark);

            this.setBlockAndMetadata(world, pos.add(0, 0, -4), this.bark);
            this.setBlockAndMetadata(world, pos.add(0, 0, -3), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, -1), this.heart);

            this.setBlockAndMetadata(world, pos, this.heart);

            this.setBlockAndMetadata(world, pos.add(0, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, 3), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, 4), this.bark);

            this.setBlockAndMetadata(world, pos.add(1, 0, -4), this.bark);
            this.setBlockAndMetadata(world, pos.add(1, 0, -3), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 3), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 4), this.bark);

            this.setBlockAndMetadata(world, pos.add(2, 0, -4), this.bark);
            this.setBlockAndMetadata(world, pos.add(2, 0, -3), this.bark);
            this.setBlockAndMetadata(world, pos.add(2, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, 3), this.bark);
            this.setBlockAndMetadata(world, pos.add(2, 0, 4), this.bark);

            this.setBlockAndMetadata(world, pos.add(3, 0, -3), this.bark);
            this.setBlockAndMetadata(world, pos.add(3, 0, -2), this.bark);
            this.setBlockAndMetadata(world, pos.add(3, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(3, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(3, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(3, 0, 2), this.bark);
            this.setBlockAndMetadata(world, pos.add(3, 0, 3), this.bark);

            this.setBlockAndMetadata(world, pos.add(4, 0, -2), this.bark);
            this.setBlockAndMetadata(world, pos.add(4, 0, -1), this.bark);
            this.setBlockAndMetadata(world, pos.add(4, 0, 0), this.bark);
            this.setBlockAndMetadata(world, pos.add(4, 0, 1), this.bark);
            this.setBlockAndMetadata(world, pos.add(4, 0, 2), this.bark);
        }
    }

    public void genRing9(World world, BlockPos pos)
    {
        if (world.getBlockState(pos).getBlock() != Blocks.BEDROCK && pos.getY() > 0)
        {
            this.setBlockAndMetadata(world, pos.add(-4, 0, -1), this.bark);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 0), this.bark);
            this.setBlockAndMetadata(world, pos.add(-4, 0, 1), this.bark);

            this.setBlockAndMetadata(world, pos.add(-3, 0, -3), this.bark);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -2), this.bark);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 2), this.bark);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 3), this.bark);

            this.setBlockAndMetadata(world, pos.add(-2, 0, -3), this.bark);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 3), this.bark);

            this.setBlockAndMetadata(world, pos.add(-1, 0, -4), this.bark);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -3), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 3), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 4), this.bark);

            this.setBlockAndMetadata(world, pos.add(0, 0, -4), this.bark);
            this.setBlockAndMetadata(world, pos.add(0, 0, -3), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, -1), this.heart);

            this.setBlockAndMetadata(world, pos, this.heart);

            this.setBlockAndMetadata(world, pos.add(0, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, 3), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, 4), this.bark);

            this.setBlockAndMetadata(world, pos.add(1, 0, -4), this.bark);
            this.setBlockAndMetadata(world, pos.add(1, 0, -3), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 3), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 4), this.bark);

            this.setBlockAndMetadata(world, pos.add(2, 0, -3), this.bark);
            this.setBlockAndMetadata(world, pos.add(2, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, 3), this.bark);

            this.setBlockAndMetadata(world, pos.add(3, 0, -3), this.bark);
            this.setBlockAndMetadata(world, pos.add(3, 0, -2), this.bark);
            this.setBlockAndMetadata(world, pos.add(3, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(3, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(3, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(3, 0, 2), this.bark);
            this.setBlockAndMetadata(world, pos.add(3, 0, 3), this.bark);

            this.setBlockAndMetadata(world, pos.add(4, 0, -1), this.bark);
            this.setBlockAndMetadata(world, pos.add(4, 0, 0), this.bark);
            this.setBlockAndMetadata(world, pos.add(4, 0, 1), this.bark);
        }
    }

    public void genRing8(World world, BlockPos pos)
    {
        if (world.getBlockState(pos).getBlock() != Blocks.BEDROCK && pos.getY() > 0)
        {
            this.setBlockAndMetadata(world, pos.add(-3, 0, -2), this.bark);
            this.setBlockAndMetadata(world, pos.add(-3, 0, -1), this.bark);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 0), this.bark);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 1), this.bark);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 2), this.bark);

            this.setBlockAndMetadata(world, pos.add(-2, 0, -3), this.bark);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -2), this.bark);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 2), this.bark);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 3), this.bark);

            this.setBlockAndMetadata(world, pos.add(-1, 0, -3), this.bark);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 3), this.bark);

            this.setBlockAndMetadata(world, pos.add(0, 0, -3), this.bark);
            this.setBlockAndMetadata(world, pos.add(0, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, -1), this.heart);

            this.setBlockAndMetadata(world, pos, this.heart);

            this.setBlockAndMetadata(world, pos.add(0, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, 3), this.bark);

            this.setBlockAndMetadata(world, pos.add(1, 0, -3), this.bark);
            this.setBlockAndMetadata(world, pos.add(1, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 3), this.bark);

            this.setBlockAndMetadata(world, pos.add(2, 0, -3), this.bark);
            this.setBlockAndMetadata(world, pos.add(2, 0, -2), this.bark);
            this.setBlockAndMetadata(world, pos.add(2, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, 2), this.bark);
            this.setBlockAndMetadata(world, pos.add(2, 0, 3), this.bark);

            this.setBlockAndMetadata(world, pos.add(3, 0, -2), this.bark);
            this.setBlockAndMetadata(world, pos.add(3, 0, -1), this.bark);
            this.setBlockAndMetadata(world, pos.add(3, 0, 0), this.bark);
            this.setBlockAndMetadata(world, pos.add(3, 0, 1), this.bark);
            this.setBlockAndMetadata(world, pos.add(3, 0, 2), this.bark);
        }
    }

    public void genRing7(World world, BlockPos pos)
    {
        if (world.getBlockState(pos).getBlock() != Blocks.BEDROCK && pos.getY() > 0)
        {
            this.setBlockAndMetadata(world, pos.add(-3, 0, -1), this.bark);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 0), this.bark);
            this.setBlockAndMetadata(world, pos.add(-3, 0, 1), this.bark);

            this.setBlockAndMetadata(world, pos.add(-2, 0, -2), this.bark);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 2), this.bark);

            this.setBlockAndMetadata(world, pos.add(-1, 0, -3), this.bark);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 3), this.bark);

            this.setBlockAndMetadata(world, pos.add(0, 0, -3), this.bark);
            this.setBlockAndMetadata(world, pos.add(0, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, -1), this.heart);

            this.setBlockAndMetadata(world, pos, this.heart);

            this.setBlockAndMetadata(world, pos.add(0, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, 3), this.bark);

            this.setBlockAndMetadata(world, pos.add(1, 0, -3), this.bark);
            this.setBlockAndMetadata(world, pos.add(1, 0, -2), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 2), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 3), this.bark);

            this.setBlockAndMetadata(world, pos.add(2, 0, -2), this.bark);
            this.setBlockAndMetadata(world, pos.add(2, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(2, 0, 2), this.bark);

            this.setBlockAndMetadata(world, pos.add(3, 0, -1), this.bark);
            this.setBlockAndMetadata(world, pos.add(3, 0, 0), this.bark);
            this.setBlockAndMetadata(world, pos.add(3, 0, 1), this.bark);
        }

    }

    public void genRing6(World world, BlockPos pos)
    {
        if (world.getBlockState(pos).getBlock() != Blocks.BEDROCK && pos.getY() > 0)
        {
            this.setBlockAndMetadata(world, pos.add(-2, 0, -2), this.bark);
            this.setBlockAndMetadata(world, pos.add(-2, 0, -1), this.bark);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 0), this.bark);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 1), this.bark);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 2), this.bark);

            this.setBlockAndMetadata(world, pos.add(-1, 0, -2), this.bark);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 2), this.bark);

            this.setBlockAndMetadata(world, pos.add(0, 0, -2), this.bark);
            this.setBlockAndMetadata(world, pos.add(0, 0, -1), this.heart);

            this.setBlockAndMetadata(world, pos, this.heart);

            this.setBlockAndMetadata(world, pos.add(0, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, 2), this.bark);

            this.setBlockAndMetadata(world, pos.add(1, 0, -2), this.bark);
            this.setBlockAndMetadata(world, pos.add(1, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 2), this.bark);

            this.setBlockAndMetadata(world, pos.add(2, 0, -2), this.bark);
            this.setBlockAndMetadata(world, pos.add(2, 0, -1), this.bark);
            this.setBlockAndMetadata(world, pos.add(2, 0, 0), this.bark);
            this.setBlockAndMetadata(world, pos.add(2, 0, 1), this.bark);
            this.setBlockAndMetadata(world, pos.add(2, 0, 2), this.bark);
        }

    }

    public void genRing5(World world, BlockPos pos)
    {
        if (world.getBlockState(pos).getBlock() != Blocks.BEDROCK && pos.getY() > 0)
        {
            this.setBlockAndMetadata(world, pos.add(-2, 0, -1), this.bark);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 0), this.bark);
            this.setBlockAndMetadata(world, pos.add(-2, 0, 1), this.bark);

            this.setBlockAndMetadata(world, pos.add(-1, 0, -2), this.bark);
            this.setBlockAndMetadata(world, pos.add(-1, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 2), this.bark);

            this.setBlockAndMetadata(world, pos.add(0, 0, -2), this.bark);
            this.setBlockAndMetadata(world, pos.add(0, 0, -1), this.heart);

            this.setBlockAndMetadata(world, pos, this.heart);

            this.setBlockAndMetadata(world, pos.add(0, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(0, 0, 2), this.bark);

            this.setBlockAndMetadata(world, pos.add(1, 0, -2), this.bark);
            this.setBlockAndMetadata(world, pos.add(1, 0, -1), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 0), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 1), this.heart);
            this.setBlockAndMetadata(world, pos.add(1, 0, 2), this.bark);

            this.setBlockAndMetadata(world, pos.add(2, 0, -1), this.bark);
            this.setBlockAndMetadata(world, pos.add(2, 0, 0), this.bark);
            this.setBlockAndMetadata(world, pos.add(2, 0, 1), this.bark);
        }

    }

    public void genRing3(World world, BlockPos pos)
    {
        if (world.getBlockState(pos).getBlock() != Blocks.BEDROCK && pos.getY() > 0)
        {
            this.setBlockAndMetadata(world, pos.add(-1, 0, -1), this.bark);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 0), this.bark);
            this.setBlockAndMetadata(world, pos.add(-1, 0, 1), this.bark);

            this.setBlockAndMetadata(world, pos.add(0, 0, -1), this.bark);

            this.setBlockAndMetadata(world, pos, this.heart);

            this.setBlockAndMetadata(world, pos.add(0, 0, 1), this.bark);

            this.setBlockAndMetadata(world, pos.add(1, 0, -1), this.bark);
            this.setBlockAndMetadata(world, pos.add(1, 0, 0), this.bark);
            this.setBlockAndMetadata(world, pos.add(1, 0, 1), this.bark);
        }
    }

    protected void setBlockAndMetadata(World world, BlockPos pos, IBlockState stateNew)
    {
        if (this.isReplaceable(world, pos))
        {
            world.setBlockState(pos, stateNew, 2);
        }
    }

    /**
     * returns whether a tree can grow into a block
     * For example, a tree will not grow into stone
     */
    protected boolean canGrowInto(Block blockType)
    {
        Material material = blockType.getDefaultState().getMaterial();
        return material == Material.AIR || material == Material.LEAVES || blockType == Blocks.GRASS || blockType == Blocks.DIRT || blockType == Blocks.LOG || blockType == Blocks.LOG2 || blockType == Blocks.SAPLING || blockType == Blocks.VINE;
    }

    protected BlockPos findGround(World world, BlockPos pos)
    {
        if (world.getWorldType() == WorldType.FLAT && this.isSapling)
        {
            boolean foundGround = false;

            int height = Config.flatSeaLevel + 64;

            do
            {
                height--;

                BlockPos position = new BlockPos(pos.getX(), height, pos.getZ());
                IBlockState underState = world.getBlockState(position);
                Block underBlock = underState.getBlock();
                boolean isSoil = underBlock.canSustainPlant(underState, world, position, EnumFacing.UP, NaturaOverworld.redwoodSapling);

                if (isSoil || height < Config.flatSeaLevel)
                {
                    foundGround = true;
                }
            } while (!foundGround);

            return new BlockPos(pos.getX(), height, pos.getZ());
        }
        else
        {
            boolean foundGround = false;

            int height = Config.seaLevel + 64;

            do
            {
                height--;

                BlockPos position = new BlockPos(pos.getX(), height, pos.getZ());
                IBlockState underState = world.getBlockState(position);
                Block underBlock = underState.getBlock();
                boolean isSoil = underBlock.canSustainPlant(underState, world, position, EnumFacing.UP, NaturaOverworld.redwoodSapling);

                if (isSoil || height < Config.seaLevel)
                {
                    foundGround = true;
                }
            } while (!foundGround);

            return new BlockPos(pos.getX(), height, pos.getZ());
        }
    }

    /**
     * Generates a list of leaf nodes for the tree, to be populated by generateLeaves.
     */
    void generateLeafNodeList()
    {
        this.height = (int) (this.heightLimit * this.heightAttenuation);

        if (this.height >= this.heightLimit)
        {
            this.height = this.heightLimit - 1;
        }

        int i = (int) (1.382D + Math.pow(this.leafDensity * this.heightLimit / 13.0D, 2.0D));

        if (i < 1)
        {
            i = 1;
        }

        int j = this.basePos.getY() + this.height;
        int k = this.heightLimit - this.leafDistanceLimit;
        this.foliageCoords = Lists.newArrayList();
        this.foliageCoords.add(new FoliageCoordinates(this.basePos.up(k), j));

        for (; k >= 0; --k)
        {
            float f = this.layerSize(k);

            if (f >= 0.0F)
            {
                for (int l = 0; l < i; ++l)
                {
                    double d0 = this.scaleWidth * f * (this.rand.nextFloat() + 0.328D);
                    double d1 = this.rand.nextFloat() * 2.0F * Math.PI;
                    double d2 = d0 * Math.sin(d1) + 0.5D;
                    double d3 = d0 * Math.cos(d1) + 0.5D;
                    BlockPos blockpos = this.basePos.add(d2, -1, d3);
                    BlockPos blockpos1 = blockpos.up(this.leafDistanceLimit);

                    if (this.checkBlockLine(blockpos, blockpos1) == -1)
                    {
                        int i1 = this.basePos.getX() - blockpos.getX();
                        int j1 = this.basePos.getZ() - blockpos.getZ();
                        double d4 = blockpos.getY() - Math.sqrt(i1 * i1 + j1 * j1) * this.branchSlope;
                        int k1 = d4 > j ? j : (int) d4;
                        BlockPos blockpos2 = new BlockPos(this.basePos.getX(), k1, this.basePos.getZ());

                        if (this.checkBlockLine(blockpos2, blockpos) == -1)
                        {
                            this.foliageCoords.add(new FoliageCoordinates(blockpos, blockpos2.getY()));
                        }
                    }
                }
            }
        }
    }

    void crossSection(BlockPos pos, float size, IBlockState state)
    {
        int i = (int) (size + 0.618D);

        for (int j = -i; j <= i; ++j)
        {
            for (int k = -i; k <= i; ++k)
            {
                if (Math.pow(Math.abs(j) + 0.5D, 2.0D) + Math.pow(Math.abs(k) + 0.5D, 2.0D) <= size * size)
                {
                    BlockPos blockpos = pos.add(j, 0, k);
                    IBlockState stateAtPos = this.world.getBlockState(blockpos);

                    if (stateAtPos.getBlock().isAir(stateAtPos, this.world, blockpos) || stateAtPos.getBlock().isLeaves(stateAtPos, this.world, blockpos))
                    {
                        this.setBlockAndMetadata(this.world, blockpos, state);
                    }
                }
            }
        }
    }

    /**
     * Gets the rough size of a layer of the tree.
     */
    float layerSize(int y)
    {
        if (y < this.heightLimit * 0.3F)
        {
            return -1.0F;
        }
        else
        {
            float f = this.heightLimit / 2.0F;
            float f1 = f - y;
            float f2 = MathHelper.sqrt(f * f - f1 * f1);

            if (f1 == 0.0F)
            {
                f2 = f;
            }
            else if (Math.abs(f1) >= f)
            {
                return 0.0F;
            }

            return f2 * 0.5F;
        }
    }

    float leafSize(int y)
    {
        return y >= 0 && y < this.leafDistanceLimit ? (y != 0 && y != this.leafDistanceLimit - 1 ? 3.0F : 2.0F) : -1.0F;
    }

    /**
     * Generates the leaves surrounding an individual entry in the leafNodes list.
     */
    void generateLeafNode(BlockPos pos)
    {
        for (int i = 0; i < this.leafDistanceLimit; ++i)
        {
            this.crossSection(pos.up(i), this.leafSize(i), this.leaves);
        }
    }

    void limb(BlockPos pos1, BlockPos pos2, IBlockState state)
    {
        BlockPos blockpos = pos2.add(-pos1.getX(), -pos1.getY(), -pos1.getZ());
        int i = this.getGreatestDistance(blockpos);
        float f = (float) blockpos.getX() / i;
        float f1 = (float) blockpos.getY() / i;
        float f2 = (float) blockpos.getZ() / i;

        for (int j = 0; j <= i; ++j)
        {
            BlockPos blockpos1 = pos1.add(0.5F + j * f, 0.5F + j * f1, 0.5F + j * f2);
            this.setBlockAndMetadata(this.world, blockpos1, state);
        }
    }

    /**
     * Generates the leaf portion of the tree as specified by the leafNodes list.
     */
    void generateLeaves()
    {
        for (FoliageCoordinates foliagecoordinates : this.foliageCoords)
        {
            this.generateLeafNode(foliagecoordinates);
        }
    }

    /**
     * Indicates whether a leaf node requires additional wood to be added to preserve integrity.
     */
    boolean leafNodeNeedsBase(int p_76493_1_)
    {
        return p_76493_1_ >= this.heightLimit * 0.2D;
    }

    /**
     * Generates additional wood blocks to fill out the bases of different leaf nodes that would otherwise degrade.
     */
    void generateLeafNodeBases()
    {
        for (FoliageCoordinates foliagecoordinates : this.foliageCoords)
        {
            int i = foliagecoordinates.getBranchBase();
            BlockPos blockpos = new BlockPos(this.basePos.getX(), i, this.basePos.getZ());

            if (!blockpos.equals(foliagecoordinates) && this.leafNodeNeedsBase(i - this.basePos.getY()))
            {
                this.limb(blockpos, foliagecoordinates, this.bark);
            }
        }
    }

    /**
     * Checks a line of blocks in the world from the first coordinate to triplet to the second, returning the distance
     * (in blocks) before a non-air, non-leaf block is encountered and/or the end is encountered.
     */
    int checkBlockLine(BlockPos posOne, BlockPos posTwo)
    {
        BlockPos blockpos = posTwo.add(-posOne.getX(), -posOne.getY(), -posOne.getZ());
        int i = this.getGreatestDistance(blockpos);
        float f = (float) blockpos.getX() / i;
        float f1 = (float) blockpos.getY() / i;
        float f2 = (float) blockpos.getZ() / i;

        if (i != 0)
        {
            for (int j = 0; j <= i; ++j)
            {
                BlockPos pos = posOne.add(0.5F + j * f, 0.5F + j * f1, 0.5F + j * f2);

                if (!this.isReplaceable(this.world, pos))
                {
                    return j;
                }
            }
        }
        return -1;
    }

    /**
     * Returns the absolute greatest distance in the BlockPos object.
     */
    private int getGreatestDistance(BlockPos posIn)
    {
        int i = MathHelper.abs(posIn.getX());
        int j = MathHelper.abs(posIn.getY());
        int k = MathHelper.abs(posIn.getZ());
        return k > i && k > j ? k : (Math.max(j, i));
    }

    //TODO: CHANGE EVERYTHING ABOVE.

    static class FoliageCoordinates extends BlockPos
    {
        private final int branchBase;

        public FoliageCoordinates(BlockPos pos, int branchBase)
        {
            super(pos.getX(), pos.getY(), pos.getZ());
            this.branchBase = branchBase;
        }

        public int getBranchBase()
        {
            return this.branchBase;
        }
    }
}
