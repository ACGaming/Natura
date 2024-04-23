package com.progwml6.natura.overworld.block.logs;

import java.util.Locale;
import javax.annotation.Nonnull;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

import com.progwml6.natura.common.block.BlockEnumLog;
import slimeknights.mantle.block.EnumBlock;

public class BlockAppleLog extends BlockEnumLog<BlockAppleLog.LogType>
{
    public static final PropertyEnum<LogType> TYPE = PropertyEnum.create("type", LogType.class);

    public BlockAppleLog()
    {
        super(TYPE, LogType.class);

        this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, EnumAxis.Y));
        this.setHarvestLevel("axe", -1);
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, TYPE, LOG_AXIS);
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState iblockstate = this.getDefaultState().withProperty(TYPE, this.fromMeta((meta & 3)));

        switch (meta & 12)
        {
            case 0:
                iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.Y);
                break;
            case 4:
                iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.X);
                break;
            case 8:
                iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.Z);
                break;
            default:
                iblockstate = iblockstate.withProperty(LOG_AXIS, EnumAxis.NONE);
        }

        return iblockstate;
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    @SuppressWarnings("incomplete-switch")
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | (state.getValue(TYPE)).getMeta();

        switch (state.getValue(LOG_AXIS))
        {
            case X:
                i |= 4;
                break;
            case Z:
                i |= 8;
                break;
            case NONE:
                i |= 12;
        }

        return i;
    }

    /**
     * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It returns the metadata of the dropped item based on the old metadata
     * of the block.
     */
    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(TYPE).getMeta();
    }

    @Nonnull
    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(this), 1, state.getValue(TYPE).getMeta());
    }

    public enum LogType implements IStringSerializable, EnumBlock.IEnumMeta
    {
        APPLE;

        public final int meta;

        LogType()
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
