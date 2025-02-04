package com.progwml6.natura.decorative.block.workbenches;

import com.progwml6.natura.Natura;
import com.progwml6.natura.common.GuiIDs;
import com.progwml6.natura.nether.block.planks.BlockNetherPlanks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.mantle.block.EnumBlock;

public class BlockNetherWorkbenches extends EnumBlock<BlockNetherPlanks.PlankType>
{
    public static PropertyEnum<BlockNetherPlanks.PlankType> TYPE = PropertyEnum.create("type", BlockNetherPlanks.PlankType.class);

    public BlockNetherWorkbenches()
    {
        super(Material.WOOD, TYPE, BlockNetherPlanks.PlankType.class);

        this.setSoundType(SoundType.WOOD);
        this.setHardness(2.5F);
        this.setResistance(10.0F);
        this.setCreativeTab(Natura.TAB);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
        {
            return true;
        }
        else
        {
            playerIn.openGui(Natura.instance, GuiIDs.CRAFTING_TABLE, worldIn, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
    }
}
