package com.progwml6.natura.overworld.block.slabs;

import java.util.Locale;
import javax.annotation.Nonnull;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.IStringSerializable;

import com.progwml6.natura.Natura;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.overworld.block.planks.BlockOverworldPlanks;
import slimeknights.mantle.block.EnumBlock;
import slimeknights.mantle.block.EnumBlockSlab;

public class BlockOverworldSlab2 extends EnumBlockSlab<BlockOverworldSlab2.PlankType>
{
    public final static PropertyEnum<PlankType> TYPE = PropertyEnum.create("type", PlankType.class);

    public BlockOverworldSlab2()
    {
        super(Material.WOOD, TYPE, PlankType.class);

        Blocks.FIRE.setFireInfo(this, 5, 20);

        this.setHardness(2.0F);
        this.setSoundType(SoundType.WOOD);
        this.setCreativeTab(Natura.TAB);
    }

    @Override
    public IBlockState getFullBlock(IBlockState state)
    {
        if (NaturaOverworld.overworldPlanks == null)
        {
            return null;
        }
        return NaturaOverworld.overworldPlanks.getDefaultState().withProperty(BlockOverworldPlanks.TYPE, state.getValue(TYPE).asFullBlock());
    }

    // using a separate Enum than BlockOverworldPlanks since there are more variants than the 8 types slabs support
    public enum PlankType implements IStringSerializable, EnumBlock.IEnumMeta
    {
        EUCALYPTUS, HOPSEED, SAKURA, REDWOOD, APPLE;

        public final int meta;

        PlankType()
        {
            this.meta = this.ordinal();
        }

        @Nonnull
        @Override
        public String getName()
        {
            return this.toString().toLowerCase(Locale.US);
        }

        public BlockOverworldPlanks.PlankType asFullBlock()
        {
            switch (this)
            {
                case EUCALYPTUS:
                    return BlockOverworldPlanks.PlankType.EUCALYPTUS;
                case HOPSEED:
                    return BlockOverworldPlanks.PlankType.HOPSEED;
                case SAKURA:
                    return BlockOverworldPlanks.PlankType.SAKURA;
                case REDWOOD:
                    return BlockOverworldPlanks.PlankType.REDWOOD;
                case APPLE:
                    return BlockOverworldPlanks.PlankType.APPLE;
                default:
                    return null;
            }
        }

        @Override
        public int getMeta()
        {
            return this.meta;
        }
    }
}
