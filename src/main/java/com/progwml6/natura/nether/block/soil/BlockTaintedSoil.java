package com.progwml6.natura.nether.block.soil;

import java.util.Locale;

import com.google.common.base.Predicate;
import com.progwml6.natura.Natura;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import slimeknights.mantle.block.EnumBlock;

public class BlockTaintedSoil extends EnumBlock<BlockTaintedSoil.SoilType> {
    public static PropertyEnum<SoilType> TYPE = PropertyEnum.create("type", SoilType.class);

    public BlockTaintedSoil() {
        super(Material.GROUND, TYPE, SoilType.class);

        this.setCreativeTab(Natura.TAB);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.GROUND);
        this.setHarvestLevel("shovel", 0);
    }

    @Override
    public boolean isFertile(World world, BlockPos pos) {
        if (world.getBlockState(pos).getValue(TYPE) == SoilType.TAINTED_FARMLAND_HEATED) {
            return true;
        }

        return false;
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

    @Override
    public boolean isReplaceableOreGen(IBlockState state, IBlockAccess world, BlockPos pos, Predicate<IBlockState> target) {
        return target.apply(Blocks.NETHERRACK.getDefaultState());
    }

    public enum SoilType implements IStringSerializable, EnumBlock.IEnumMeta {
        TAINTED_SOIL, TAINTED_FARMLAND_DRY, TAINTED_FARMLAND_HEATED;

        public final int meta;

        SoilType() {
            this.meta = this.ordinal();
        }

        @Override
        public String getName() {
            return this.toString().toLowerCase(Locale.US);
        }

        @Override
        public int getMeta() {
            return this.meta;
        }
    }
}
