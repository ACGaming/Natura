package com.progwml6.natura.overworld.block.saplings;

import java.util.Locale;
import java.util.Random;
import javax.annotation.Nonnull;

import net.minecraft.block.BlockSapling;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.event.terraingen.TerrainGen;

import com.progwml6.natura.Natura;
import com.progwml6.natura.library.NaturaRegistry;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.overworld.block.leaves.BlockAppleLeaves;
import com.progwml6.natura.overworld.block.logs.BlockAppleLog;
import com.progwml6.natura.world.worldgen.trees.BaseTreeGenerator;
import com.progwml6.natura.world.worldgen.trees.overworld.AppleTreeGenerator;
import slimeknights.mantle.block.EnumBlock;

public class BlockAppleSapling extends BlockSapling
{
    public static final PropertyEnum<SaplingType> FOLIAGE = PropertyEnum.create("foliage", SaplingType.class);

    public BlockAppleSapling()
    {
        this.setCreativeTab(NaturaRegistry.tabWorld);
        this.setDefaultState(this.blockState.getBaseState());
        this.setSoundType(SoundType.PLANT);
        this.setHardness(0.0F);
    }

    @Nonnull
    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
    {
        return EnumPlantType.Plains;
    }

    @Override
    public boolean isReplaceable(IBlockAccess worldIn, @Nonnull BlockPos pos)
    {
        return false;
    }

    @Nonnull
    @Override
    public ItemStack getPickBlock(@Nonnull IBlockState state, RayTraceResult target, @Nonnull World world, @Nonnull BlockPos pos, EntityPlayer player)
    {
        IBlockState iblockstate = world.getBlockState(pos);
        int meta = iblockstate.getBlock().getMetaFromState(iblockstate);
        return new ItemStack(Item.getItemFromBlock(this), 1, meta);
    }

    @Override
    public void generateTree(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand)
    {
        if (!TerrainGen.saplingGrowTree(worldIn, rand, pos))
        {
            return;
        }
        BaseTreeGenerator gen = new BaseTreeGenerator();

        IBlockState log;
        IBlockState leavesNormal;
        IBlockState leavesFlowering;
        IBlockState leavesFruiting;
        IBlockState leavesFruitingGolden;

        if (state.getValue(FOLIAGE) == SaplingType.APPLE)
        {
            log = NaturaOverworld.appleLog.getDefaultState().withProperty(BlockAppleLog.TYPE, BlockAppleLog.LogType.APPLE);
            leavesNormal = NaturaOverworld.appleLeaves.getDefaultState().withProperty(BlockAppleLeaves.TYPE, BlockAppleLeaves.LeavesType.NORMAL);
            leavesFlowering = NaturaOverworld.appleLeaves.getDefaultState().withProperty(BlockAppleLeaves.TYPE, BlockAppleLeaves.LeavesType.FLOWERING);
            leavesFruiting = NaturaOverworld.appleLeaves.getDefaultState().withProperty(BlockAppleLeaves.TYPE, BlockAppleLeaves.LeavesType.FRUIT);
            leavesFruitingGolden = NaturaOverworld.appleLeaves.getDefaultState().withProperty(BlockAppleLeaves.TYPE, BlockAppleLeaves.LeavesType.GOLDEN_FRUIT);

            gen = new AppleTreeGenerator(6, 4, log, leavesNormal, leavesFlowering, leavesFruiting, leavesFruitingGolden);
        }
        else
        {
            Natura.log.warn("BlockAppleSapling Warning: Invalid sapling meta/foliage, " + state.getValue(FOLIAGE) + ". Please report!");
        }

        // replace sapling with air
        worldIn.setBlockToAir(pos);

        // try generating
        gen.generateTree(rand, worldIn, pos);

        // check if it generated
        if (worldIn.isAirBlock(pos))
        {
            // nope, set sapling again
            worldIn.setBlockState(pos, state, 4);
        }
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return this.getMetaFromState(state);
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (SaplingType type : SaplingType.values())
        {
            list.add(new ItemStack(this, 1, this.getMetaFromState(this.getDefaultState().withProperty(FOLIAGE, type))));
        }
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        if (meta < 0 || meta >= SaplingType.values().length)
        {
            meta = 0;
        }

        SaplingType sapling = SaplingType.values()[meta];

        return this.getDefaultState().withProperty(FOLIAGE, sapling);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FOLIAGE).ordinal();
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState()
    {
        // TYPE has to be included because of the BlockSapling constructor, but it's never used.
        return new BlockStateContainer(this, FOLIAGE, STAGE, TYPE);
    }

    public enum SaplingType implements IStringSerializable, EnumBlock.IEnumMeta
    {
        APPLE;

        public final int meta;

        SaplingType()
        {
            this.meta = this.ordinal();
        }

        @Nonnull
        @Override
        public String getName()
        {
            return this.toString().toLowerCase(Locale.US);
        }

        @Override
        public int getMeta()
        {
            return this.meta;
        }
    }
}
