package com.progwml6.natura.decorative.block.bookshelves;

import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.progwml6.natura.Natura;
import com.progwml6.natura.overworld.block.planks.BlockOverworldPlanks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import slimeknights.mantle.block.EnumBlock;

public class BlockOverworldBookshelves extends EnumBlock<BlockOverworldPlanks.PlankType>
{
    public static PropertyEnum<BlockOverworldPlanks.PlankType> TYPE = PropertyEnum.create("type", BlockOverworldPlanks.PlankType.class);

    public BlockOverworldBookshelves()
    {
        super(Material.WOOD, TYPE, BlockOverworldPlanks.PlankType.class);

        this.setSoundType(SoundType.WOOD);
        this.setHardness(1.5F);
        this.setCreativeTab(Natura.TAB);
    }

    /**
     * Called when a user uses the creative pick block button on this block
     *
     * @param target The full target the player is looking at
     * @return A ItemStack to add to the player's inventory, Null if nothing should be added.
     */
    @Nonnull
    @Override
    public ItemStack getPickBlock(@Nonnull IBlockState state, RayTraceResult target, @Nonnull World world, @Nonnull BlockPos pos, EntityPlayer player)
    {
        int meta = this.getMetaFromState(state);

        return new ItemStack(Item.getItemFromBlock(this), 1, meta);
    }

    /**
     * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It
     * returns the metadata of the dropped item based on the old metadata of the block.
     */
    @Override
    public int damageDropped(IBlockState state)
    {
        return 0;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(Random random)
    {
        return 3;
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    @Override
    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Items.BOOK;
    }

    /**
     * Determines the amount of enchanting power this block can provide to an enchanting table.
     * @param world The World
     * @param pos Block position in world
     * @return The amount of enchanting power this block produces.
     */
    @Override
    public float getEnchantPowerBonus(World world, BlockPos pos)
    {
        return 1;
    }
}
