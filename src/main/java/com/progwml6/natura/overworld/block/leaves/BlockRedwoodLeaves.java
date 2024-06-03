package com.progwml6.natura.overworld.block.leaves;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;
import com.progwml6.natura.common.block.base.BlockLeavesBase;
import com.progwml6.natura.library.NaturaRegistry;
import com.progwml6.natura.overworld.NaturaOverworld;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import slimeknights.mantle.block.EnumBlock;

public class BlockRedwoodLeaves extends BlockLeavesBase
{
    public static final PropertyEnum<RedwoodType> TYPE = PropertyEnum.create("type", RedwoodType.class);

    public BlockRedwoodLeaves()
    {
        this.setCreativeTab(NaturaRegistry.tabWorld);

        Blocks.FIRE.setFireInfo(this, 5, 20);

        this.setDefaultState(this.blockState.getBaseState().withProperty(CHECK_DECAY, false).withProperty(DECAYABLE, true));
    }

    @Override
    public void updateTick(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, Random rand)
    {
        if (!worldIn.isRemote && state.getValue(CHECK_DECAY) && state.getValue(DECAYABLE))
            {
                boolean nearbyTree = false;
                byte range = 4;

                for (int posX = pos.getX() - range; posX <= pos.getX() + range; posX++)
                {
                    for (int posY = pos.getY() - range; posY <= pos.getY() + range; posY++)
                    {
                        for (int posZ = pos.getZ() - range; posZ <= pos.getZ() + range; posZ++)
                        {
                            MutableBlockPos mutableblockpos = new MutableBlockPos();

                            IBlockState iblockstate = worldIn.getBlockState(mutableblockpos.setPos(posX, posY, posZ));
                            Block block = iblockstate.getBlock();

                            if (block.canSustainLeaves(iblockstate, worldIn, pos.add(posX, posY, posZ)))
                            {
                                nearbyTree = true;
                            }
                        }
                    }
                }

                if (!nearbyTree)
                {
                    this.destroy(worldIn, pos);
                }
            }
    }

    private void destroy(World worldIn, BlockPos pos)
    {
        this.dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
        worldIn.setBlockToAir(pos);
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (RedwoodType type : RedwoodType.values())
        {
            list.add(new ItemStack(this, 1, this.getMetaFromState(this.getDefaultState().withProperty(TYPE, type))));
        }
    }

    @Override
    protected int getSaplingDropChance(IBlockState state)
    {
        return 25;
    }

    // sapling item
    @Nonnull
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(NaturaOverworld.redwoodSapling);
    }

    // sapling meta
    @Override
    public int damageDropped(IBlockState state)
    {
        return (state.getValue(TYPE)).ordinal() & 3; // only first 2 bits
    }

    // item dropped on silktouching
    @Nonnull
    @Override
    protected ItemStack getSilkTouchDrop(@Nonnull IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(this), 1, (state.getValue(TYPE)).ordinal() & 3);
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, TYPE, CHECK_DECAY, DECAYABLE);
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        int type = meta % 4;
        if (type < 0 || type >= RedwoodType.values().length)
        {
            type = 0;
        }
        RedwoodType logtype = RedwoodType.values()[type];
        return this.getDefaultState()
                .withProperty(TYPE, logtype)
                .withProperty(DECAYABLE, (meta & 4) == 0)
                .withProperty(CHECK_DECAY, (meta & 8) > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int meta = (state.getValue(TYPE)).ordinal() & 3; // only first 2 bits

        if (!state.getValue(DECAYABLE))
        {
            meta |= 4;
        }

        if (state.getValue(CHECK_DECAY))
        {
            meta |= 8;
        }

        return meta;
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
    {
        IBlockState state = world.getBlockState(pos);
        return Lists.newArrayList(this.getSilkTouchDrop(state));
    }

    public enum RedwoodType implements IStringSerializable, EnumBlock.IEnumMeta
    {
        NORMAL;

        public final int meta;

        RedwoodType()
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
