package com.progwml6.natura.overworld;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.ModelLoader;

import com.progwml6.natura.common.ClientProxy;
import com.progwml6.natura.common.block.BlockEnumBerryBush;
import com.progwml6.natura.common.block.BlockGrassStairs;
import com.progwml6.natura.common.client.GrassColorizer;
import com.progwml6.natura.common.client.LeavesColorizer;
import com.progwml6.natura.overworld.block.crops.BlockNaturaBarley;
import com.progwml6.natura.overworld.block.crops.BlockNaturaCotton;
import com.progwml6.natura.overworld.block.grass.BlockColoredGrass;
import com.progwml6.natura.overworld.block.leaves.BlockAppleLeaves;
import com.progwml6.natura.overworld.block.leaves.BlockRedwoodLeaves;
import com.progwml6.natura.overworld.block.logs.BlockAppleLog;
import com.progwml6.natura.overworld.block.logs.BlockOverworldLog;
import com.progwml6.natura.overworld.block.logs.BlockOverworldLog2;
import com.progwml6.natura.overworld.block.saguaro.BlockSaguaroBaby;
import com.progwml6.natura.overworld.block.saplings.BlockAppleSapling;
import com.progwml6.natura.overworld.block.saplings.BlockOverworldSapling;
import com.progwml6.natura.overworld.block.saplings.BlockOverworldSapling2;
import com.progwml6.natura.overworld.block.saplings.BlockRedwoodSapling;

import static com.progwml6.natura.common.ModelRegisterUtil.registerItemBlockMeta;
import static com.progwml6.natura.common.ModelRegisterUtil.registerItemModel;

public class OverworldClientProxy extends ClientProxy
{
    public static final GrassColorizer GRASS_COLORIZER = new GrassColorizer();

    public static final Minecraft MINECRAFT = Minecraft.getMinecraft();

    @Override
    public void preInit()
    {
        ((IReloadableResourceManager) MINECRAFT.getResourceManager()).registerReloadListener(GRASS_COLORIZER);

        super.preInit();
    }

    @Override
    public void init()
    {
        final BlockColors blockColors = MINECRAFT.getBlockColors();

        blockColors.registerBlockColorHandler((state, access, pos, tintIndex) -> {
            if (state.getBlock().getClass() == BlockGrassStairs.class)
            {
                BlockGrassStairs grassStairs = (BlockGrassStairs) state.getBlock();
                BlockColoredGrass.GrassType type = grassStairs.customModelState.getValue(BlockColoredGrass.TYPE);
                if (pos == null)
                {
                    return GrassColorizer.getGrassColorStatic(type);
                }

                return GrassColorizer.getGrassColorForPos(access, pos, type);
            }
            else
            {
                BlockColoredGrass.GrassType type = state.getValue(BlockColoredGrass.TYPE);

                if (pos == null)
                {
                    return GrassColorizer.getGrassColorStatic(type);
                }

                return GrassColorizer.getGrassColorForPos(access, pos, type);
            }
        }, NaturaOverworld.coloredGrass, NaturaOverworld.coloredGrassSlab, NaturaOverworld.coloredGrassStairsTopiary, NaturaOverworld.coloredGrassStairsBlueGrass, NaturaOverworld.coloredGrassStairsAutumnal);

        blockColors.registerBlockColorHandler((state, access, pos, tintIndex) -> {
            BlockOverworldLog.LogType type = state.getValue(BlockOverworldLog.TYPE);

            if (pos == null)
            {
                return LeavesColorizer.getOverworldLeavesColorStatic(type);
            }

            return LeavesColorizer.getOverworldLeavesColorForPos(access, pos, type);
        }, NaturaOverworld.overworldLeaves);

        blockColors.registerBlockColorHandler((state, access, pos, tintIndex) -> {
            BlockOverworldLog2.LogType type = state.getValue(BlockOverworldLog2.TYPE);

            if (pos == null)
            {
                return LeavesColorizer.getSecondOverworldLeavesColorStatic(type);
            }

            return LeavesColorizer.getSecondOverworldLeavesColorForPos(access, pos, type);
        }, NaturaOverworld.overworldLeaves2);

        blockColors.registerBlockColorHandler((state, access, pos, tintIndex) -> {
            BlockRedwoodLeaves.RedwoodType type = state.getValue(BlockRedwoodLeaves.TYPE);

            if (pos == null)
            {
                return LeavesColorizer.getRedwoodLeavesColorStatic(type);
            }

            return LeavesColorizer.getRedwoodLeavesColorForPos(access, pos, type);
        }, NaturaOverworld.redwoodLeaves);

        MINECRAFT.getItemColors().registerItemColorHandler((stack, tintIndex) -> {
            @SuppressWarnings("deprecation") IBlockState iblockstate = ((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());
            return blockColors.colorMultiplier(iblockstate, null, null, tintIndex);
        }, NaturaOverworld.overworldLeaves, NaturaOverworld.overworldLeaves2, NaturaOverworld.redwoodLeaves, NaturaOverworld.appleLeaves, NaturaOverworld.coloredGrass, NaturaOverworld.coloredGrassSlab, NaturaOverworld.coloredGrassStairsTopiary, NaturaOverworld.coloredGrassStairsBlueGrass, NaturaOverworld.coloredGrassStairsAutumnal);

        super.init();
    }

    @Override
    protected void registerModels()
    {
        // blocks
        ModelLoader.setCustomStateMapper(NaturaOverworld.overworldLeaves, (new StateMap.Builder()).ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE).build());
        ModelLoader.setCustomStateMapper(NaturaOverworld.overworldLeaves2, (new StateMap.Builder()).ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE).build());
        ModelLoader.setCustomStateMapper(NaturaOverworld.redwoodLeaves, (new StateMap.Builder()).ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE).build());
        ModelLoader.setCustomStateMapper(NaturaOverworld.appleLeaves, (new StateMap.Builder()).ignore(BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE).build());

        ModelLoader.setCustomStateMapper(NaturaOverworld.overworldSapling, (new StateMap.Builder()).ignore(BlockOverworldSapling.STAGE, BlockSapling.TYPE).build());
        ModelLoader.setCustomStateMapper(NaturaOverworld.overworldSapling2, (new StateMap.Builder()).ignore(BlockOverworldSapling2.STAGE, BlockSapling.TYPE).build());
        ModelLoader.setCustomStateMapper(NaturaOverworld.redwoodSapling, (new StateMap.Builder()).ignore(BlockRedwoodSapling.STAGE, BlockSapling.TYPE).build());
        ModelLoader.setCustomStateMapper(NaturaOverworld.appleSapling, (new StateMap.Builder()).ignore(BlockAppleSapling.STAGE, BlockSapling.TYPE).build());

        ModelLoader.setCustomStateMapper(NaturaOverworld.eucalyptusDoor, (new StateMap.Builder()).ignore(BlockDoor.POWERED).build());
        ModelLoader.setCustomStateMapper(NaturaOverworld.hopseedDoor, (new StateMap.Builder()).ignore(BlockDoor.POWERED).build());
        ModelLoader.setCustomStateMapper(NaturaOverworld.sakuraDoor, (new StateMap.Builder()).ignore(BlockDoor.POWERED).build());
        ModelLoader.setCustomStateMapper(NaturaOverworld.redwoodDoor, (new StateMap.Builder()).ignore(BlockDoor.POWERED).build());
        ModelLoader.setCustomStateMapper(NaturaOverworld.redwoodBarkDoor, (new StateMap.Builder()).ignore(BlockDoor.POWERED).build());
        ModelLoader.setCustomStateMapper(NaturaOverworld.tigerDoor, (new StateMap.Builder()).ignore(BlockDoor.POWERED).build());
        ModelLoader.setCustomStateMapper(NaturaOverworld.mapleDoor, (new StateMap.Builder()).ignore(BlockDoor.POWERED).build());
        ModelLoader.setCustomStateMapper(NaturaOverworld.silverbellDoor, (new StateMap.Builder()).ignore(BlockDoor.POWERED).build());
        ModelLoader.setCustomStateMapper(NaturaOverworld.amaranthDoor, (new StateMap.Builder()).ignore(BlockDoor.POWERED).build());
        ModelLoader.setCustomStateMapper(NaturaOverworld.willowDoor, (new StateMap.Builder()).ignore(BlockDoor.POWERED).build());
        ModelLoader.setCustomStateMapper(NaturaOverworld.appleDoor, (new StateMap.Builder()).ignore(BlockDoor.POWERED).build());

        registerItemBlockMeta(NaturaOverworld.redwoodLog);

        registerItemBlockMeta(NaturaOverworld.overworldPlanks);

        registerItemBlockMeta(NaturaOverworld.coloredGrass);
        registerItemBlockMeta(NaturaOverworld.coloredGrassSlab);
        registerItemModel(NaturaOverworld.coloredGrassStairsTopiary);
        registerItemModel(NaturaOverworld.coloredGrassStairsBlueGrass);
        registerItemModel(NaturaOverworld.coloredGrassStairsAutumnal);

        ItemStack stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.bluebellsFlower), 1, 0);
        registerItemModelNatura(stack, "bluebells_flower");

        // slabs
        registerItemBlockMeta(NaturaOverworld.overworldSlab);
        registerItemBlockMeta(NaturaOverworld.overworldSlab2);

        // stairs
        registerItemModel(NaturaOverworld.overworldStairsMaple);
        registerItemModel(NaturaOverworld.overworldStairsSilverbell);
        registerItemModel(NaturaOverworld.overworldStairsAmaranth);
        registerItemModel(NaturaOverworld.overworldStairsTiger);
        registerItemModel(NaturaOverworld.overworldStairsWillow);
        registerItemModel(NaturaOverworld.overworldStairsEucalyptus);
        registerItemModel(NaturaOverworld.overworldStairsHopseed);
        registerItemModel(NaturaOverworld.overworldStairsSakura);
        registerItemModel(NaturaOverworld.overworldStairsRedwood);
        registerItemModel(NaturaOverworld.overworldStairsApple);

        // logs
        Item overworldLog = Item.getItemFromBlock(NaturaOverworld.overworldLog);
        for (BlockOverworldLog.LogType type : BlockOverworldLog.LogType.values())
        {
            String variant = String.format("%s=%s,%s=%s", BlockOverworldLog.LOG_AXIS.getName(), BlockOverworldLog.LOG_AXIS.getName(BlockOverworldLog.EnumAxis.Y), BlockOverworldLog.TYPE.getName(), BlockOverworldLog.TYPE.getName(type));
            ModelLoader.setCustomModelResourceLocation(overworldLog, type.meta, new ModelResourceLocation(overworldLog.getRegistryName(), variant));
        }

        Item overworldLog2 = Item.getItemFromBlock(NaturaOverworld.overworldLog2);
        for (BlockOverworldLog2.LogType type : BlockOverworldLog2.LogType.values())
        {
            String variant = String.format("%s=%s,%s=%s", BlockOverworldLog2.LOG_AXIS.getName(), BlockOverworldLog2.LOG_AXIS.getName(BlockOverworldLog2.EnumAxis.Y), BlockOverworldLog2.TYPE.getName(), BlockOverworldLog2.TYPE.getName(type));
            ModelLoader.setCustomModelResourceLocation(overworldLog2, type.meta, new ModelResourceLocation(overworldLog2.getRegistryName(), variant));
        }

        Item appleLog = Item.getItemFromBlock(NaturaOverworld.appleLog);
        for (BlockAppleLog.LogType type : BlockAppleLog.LogType.values())
        {
            String variant = String.format("%s=%s,%s=%s", BlockAppleLog.LOG_AXIS.getName(), BlockAppleLog.LOG_AXIS.getName(BlockAppleLog.EnumAxis.Y), BlockAppleLog.TYPE.getName(), BlockAppleLog.TYPE.getName(type));
            ModelLoader.setCustomModelResourceLocation(appleLog, type.meta, new ModelResourceLocation(appleLog.getRegistryName(), variant));
        }

        // leaves
        Item overworldLeaves = Item.getItemFromBlock(NaturaOverworld.overworldLeaves);
        for (BlockOverworldLog.LogType type : BlockOverworldLog.LogType.values())
        {
            String variant = String.format("%s=%s", BlockOverworldLog.TYPE.getName(), BlockOverworldLog.TYPE.getName(type));
            ModelLoader.setCustomModelResourceLocation(overworldLeaves, type.getMeta(), new ModelResourceLocation(overworldLeaves.getRegistryName(), variant));
        }

        Item overworldLeaves2 = Item.getItemFromBlock(NaturaOverworld.overworldLeaves2);
        for (BlockOverworldLog2.LogType type : BlockOverworldLog2.LogType.values())
        {
            String variant = String.format("%s=%s", BlockOverworldLog2.TYPE.getName(), BlockOverworldLog2.TYPE.getName(type));
            ModelLoader.setCustomModelResourceLocation(overworldLeaves2, type.getMeta(), new ModelResourceLocation(overworldLeaves2.getRegistryName(), variant));
        }

        Item redwoodLeaves = Item.getItemFromBlock(NaturaOverworld.redwoodLeaves);
        for (BlockRedwoodLeaves.RedwoodType type : BlockRedwoodLeaves.RedwoodType.values())
        {
            String variant = String.format("%s=%s", BlockRedwoodLeaves.TYPE.getName(), BlockRedwoodLeaves.TYPE.getName(type));
            ModelLoader.setCustomModelResourceLocation(redwoodLeaves, type.getMeta(), new ModelResourceLocation(redwoodLeaves.getRegistryName(), variant));
        }

        Item appleLeaves = Item.getItemFromBlock(NaturaOverworld.appleLeaves);
        for (BlockAppleLeaves.LeavesType type : BlockAppleLeaves.LeavesType.values())
        {
            String variant = String.format("%s=%s", BlockAppleLeaves.TYPE.getName(), BlockAppleLeaves.TYPE.getName(type));
            ModelLoader.setCustomModelResourceLocation(appleLeaves, type.getMeta(), new ModelResourceLocation(appleLeaves.getRegistryName(), variant));
        }

        // saplings
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.overworldSapling), 1, NaturaOverworld.overworldSapling.getMetaFromState(NaturaOverworld.overworldSapling.getDefaultState().withProperty(BlockOverworldSapling.FOLIAGE, BlockOverworldSapling.SaplingType.MAPLE)));
        registerItemModelNatura(stack, "overworld_sapling_maple");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.overworldSapling), 1, NaturaOverworld.overworldSapling.getMetaFromState(NaturaOverworld.overworldSapling.getDefaultState().withProperty(BlockOverworldSapling.FOLIAGE, BlockOverworldSapling.SaplingType.SILVERBELL)));
        registerItemModelNatura(stack, "overworld_sapling_silverbell");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.overworldSapling), 1, NaturaOverworld.overworldSapling.getMetaFromState(NaturaOverworld.overworldSapling.getDefaultState().withProperty(BlockOverworldSapling.FOLIAGE, BlockOverworldSapling.SaplingType.AMARANTH)));
        registerItemModelNatura(stack, "overworld_sapling_amaranth");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.overworldSapling), 1, NaturaOverworld.overworldSapling.getMetaFromState(NaturaOverworld.overworldSapling.getDefaultState().withProperty(BlockOverworldSapling.FOLIAGE, BlockOverworldSapling.SaplingType.TIGER)));
        registerItemModelNatura(stack, "overworld_sapling_tiger");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.overworldSapling2), 1, NaturaOverworld.overworldSapling2.getMetaFromState(NaturaOverworld.overworldSapling2.getDefaultState().withProperty(BlockOverworldSapling2.FOLIAGE, BlockOverworldSapling2.SaplingType.WILLOW)));
        registerItemModelNatura(stack, "overworld_sapling_willow");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.overworldSapling2), 1, NaturaOverworld.overworldSapling2.getMetaFromState(NaturaOverworld.overworldSapling2.getDefaultState().withProperty(BlockOverworldSapling2.FOLIAGE, BlockOverworldSapling2.SaplingType.EUCALYPTUS)));
        registerItemModelNatura(stack, "overworld_sapling_eucalyptus");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.overworldSapling2), 1, NaturaOverworld.overworldSapling2.getMetaFromState(NaturaOverworld.overworldSapling2.getDefaultState().withProperty(BlockOverworldSapling2.FOLIAGE, BlockOverworldSapling2.SaplingType.HOPSEED)));
        registerItemModelNatura(stack, "overworld_sapling_hopseed");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.overworldSapling2), 1, NaturaOverworld.overworldSapling2.getMetaFromState(NaturaOverworld.overworldSapling2.getDefaultState().withProperty(BlockOverworldSapling2.FOLIAGE, BlockOverworldSapling2.SaplingType.SAKURA)));
        registerItemModelNatura(stack, "overworld_sapling_sakura");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.redwoodSapling), 1, NaturaOverworld.redwoodSapling.getMetaFromState(NaturaOverworld.redwoodSapling.getDefaultState().withProperty(BlockRedwoodSapling.FOLIAGE, BlockRedwoodSapling.SaplingType.REDWOOD)));
        registerItemModelNatura(stack, "overworld_sapling_redwood");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.appleSapling), 1, NaturaOverworld.appleSapling.getMetaFromState(NaturaOverworld.appleSapling.getDefaultState().withProperty(BlockAppleSapling.FOLIAGE, BlockAppleSapling.SaplingType.APPLE)));
        registerItemModelNatura(stack, "overworld_sapling_apple");

        Item raspberryBerrybush = Item.getItemFromBlock(NaturaOverworld.overworldBerryBushRaspberry);
        for (int meta = 0; meta <= 3; meta++)
        {
            String variant = String.format("%s=%s", BlockEnumBerryBush.AGE.getName(), meta);
            ModelLoader.setCustomModelResourceLocation(raspberryBerrybush, meta, new ModelResourceLocation(raspberryBerrybush.getRegistryName(), variant));
        }

        Item blueberryBerrybush = Item.getItemFromBlock(NaturaOverworld.overworldBerryBushBlueberry);
        for (int meta = 0; meta <= 3; meta++)
        {
            String variant = String.format("%s=%s", BlockEnumBerryBush.AGE.getName(), meta);
            ModelLoader.setCustomModelResourceLocation(blueberryBerrybush, meta, new ModelResourceLocation(blueberryBerrybush.getRegistryName(), variant));
        }

        Item blackberryBerrybush = Item.getItemFromBlock(NaturaOverworld.overworldBerryBushBlackberry);
        for (int meta = 0; meta <= 3; meta++)
        {
            String variant = String.format("%s=%s", BlockEnumBerryBush.AGE.getName(), meta);
            ModelLoader.setCustomModelResourceLocation(blackberryBerrybush, meta, new ModelResourceLocation(blackberryBerrybush.getRegistryName(), variant));
        }

        Item maloberryBerrybush = Item.getItemFromBlock(NaturaOverworld.overworldBerryBushMaloberry);
        for (int meta = 0; meta <= 3; meta++)
        {
            String variant = String.format("%s=%s", BlockEnumBerryBush.AGE.getName(), meta);
            ModelLoader.setCustomModelResourceLocation(maloberryBerrybush, meta, new ModelResourceLocation(maloberryBerrybush.getRegistryName(), variant));
        }

        Item barleyCrop = Item.getItemFromBlock(NaturaOverworld.barleyCrop);
        for (int meta = 0; meta <= 3; meta++)
        {
            String variant = String.format("%s=%s", BlockNaturaBarley.AGE.getName(), meta);
            ModelLoader.setCustomModelResourceLocation(barleyCrop, meta, new ModelResourceLocation(barleyCrop.getRegistryName(), variant));
        }

        Item cottonCrop = Item.getItemFromBlock(NaturaOverworld.cottonCrop);
        for (int meta = 0; meta <= 4; meta++)
        {
            String variant = String.format("%s=%s", BlockNaturaCotton.AGE.getName(), meta);
            ModelLoader.setCustomModelResourceLocation(cottonCrop, meta, new ModelResourceLocation(cottonCrop.getRegistryName(), variant));
        }

        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.eucalyptusDoor), 1, NaturaOverworld.eucalyptusDoor.getMetaFromState(NaturaOverworld.eucalyptusDoor.getDefaultState().withProperty(BlockDoor.FACING, EnumFacing.EAST).withProperty(BlockDoor.OPEN, Boolean.FALSE).withProperty(BlockDoor.HINGE, BlockDoor.EnumHingePosition.LEFT).withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER)));
        this.registerItemModelNatura(stack, "overworld_door_eucalyptus");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.hopseedDoor), 1, NaturaOverworld.hopseedDoor.getMetaFromState(NaturaOverworld.hopseedDoor.getDefaultState().withProperty(BlockDoor.FACING, EnumFacing.EAST).withProperty(BlockDoor.OPEN, Boolean.FALSE).withProperty(BlockDoor.HINGE, BlockDoor.EnumHingePosition.LEFT).withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER)));
        this.registerItemModelNatura(stack, "overworld_door_hopseed");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.sakuraDoor), 1, NaturaOverworld.sakuraDoor.getMetaFromState(NaturaOverworld.sakuraDoor.getDefaultState().withProperty(BlockDoor.FACING, EnumFacing.EAST).withProperty(BlockDoor.OPEN, Boolean.FALSE).withProperty(BlockDoor.HINGE, BlockDoor.EnumHingePosition.LEFT).withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER)));
        this.registerItemModelNatura(stack, "overworld_door_sakura");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.redwoodDoor), 1, NaturaOverworld.redwoodDoor.getMetaFromState(NaturaOverworld.redwoodDoor.getDefaultState().withProperty(BlockDoor.FACING, EnumFacing.EAST).withProperty(BlockDoor.OPEN, Boolean.FALSE).withProperty(BlockDoor.HINGE, BlockDoor.EnumHingePosition.LEFT).withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER)));
        this.registerItemModelNatura(stack, "overworld_door_redwood");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.redwoodBarkDoor), 1, NaturaOverworld.redwoodBarkDoor.getMetaFromState(NaturaOverworld.redwoodBarkDoor.getDefaultState().withProperty(BlockDoor.FACING, EnumFacing.EAST).withProperty(BlockDoor.OPEN, Boolean.FALSE).withProperty(BlockDoor.HINGE, BlockDoor.EnumHingePosition.LEFT).withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER)));
        this.registerItemModelNatura(stack, "overworld_door_redwood_bark");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.tigerDoor), 1, NaturaOverworld.tigerDoor.getMetaFromState(NaturaOverworld.tigerDoor.getDefaultState().withProperty(BlockDoor.FACING, EnumFacing.EAST).withProperty(BlockDoor.OPEN, Boolean.FALSE).withProperty(BlockDoor.HINGE, BlockDoor.EnumHingePosition.LEFT).withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER)));
        this.registerItemModelNatura(stack, "overworld_door_tiger");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.mapleDoor), 1, NaturaOverworld.mapleDoor.getMetaFromState(NaturaOverworld.mapleDoor.getDefaultState().withProperty(BlockDoor.FACING, EnumFacing.EAST).withProperty(BlockDoor.OPEN, Boolean.FALSE).withProperty(BlockDoor.HINGE, BlockDoor.EnumHingePosition.LEFT).withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER)));
        this.registerItemModelNatura(stack, "overworld_door_maple");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.silverbellDoor), 1, NaturaOverworld.silverbellDoor.getMetaFromState(NaturaOverworld.silverbellDoor.getDefaultState().withProperty(BlockDoor.FACING, EnumFacing.EAST).withProperty(BlockDoor.OPEN, Boolean.FALSE).withProperty(BlockDoor.HINGE, BlockDoor.EnumHingePosition.LEFT).withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER)));
        this.registerItemModelNatura(stack, "overworld_door_silverbell");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.amaranthDoor), 1, NaturaOverworld.amaranthDoor.getMetaFromState(NaturaOverworld.amaranthDoor.getDefaultState().withProperty(BlockDoor.FACING, EnumFacing.EAST).withProperty(BlockDoor.OPEN, Boolean.FALSE).withProperty(BlockDoor.HINGE, BlockDoor.EnumHingePosition.LEFT).withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER)));
        this.registerItemModelNatura(stack, "overworld_door_amaranth");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.willowDoor), 1, NaturaOverworld.willowDoor.getMetaFromState(NaturaOverworld.willowDoor.getDefaultState().withProperty(BlockDoor.FACING, EnumFacing.EAST).withProperty(BlockDoor.OPEN, Boolean.FALSE).withProperty(BlockDoor.HINGE, BlockDoor.EnumHingePosition.LEFT).withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER)));
        this.registerItemModelNatura(stack, "overworld_door_willow");
        stack = new ItemStack(Item.getItemFromBlock(NaturaOverworld.appleDoor), 1, NaturaOverworld.appleDoor.getMetaFromState(NaturaOverworld.appleDoor.getDefaultState().withProperty(BlockDoor.FACING, EnumFacing.EAST).withProperty(BlockDoor.OPEN, Boolean.FALSE).withProperty(BlockDoor.HINGE, BlockDoor.EnumHingePosition.LEFT).withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER)));
        this.registerItemModelNatura(stack, "overworld_door_apple");

        registerItemModel(NaturaOverworld.saguaro);

        Item saguaroBaby = Item.getItemFromBlock(NaturaOverworld.saguaroBaby);
        for (int meta = 0; meta <= 1; meta++)
        {
            String variant = String.format("%s=%s", BlockSaguaroBaby.AGE.getName(), meta);
            ModelLoader.setCustomModelResourceLocation(saguaroBaby, meta, new ModelResourceLocation(saguaroBaby.getRegistryName(), variant));
        }

        registerItemModel(NaturaOverworld.saguaroFruit);

        registerItemModel(NaturaOverworld.saguaroFruitItem);

        NaturaOverworld.overworldSeeds.registerItemModels();
        NaturaOverworld.overworldSeedBags.registerItemModels();
        NaturaOverworld.overworldDoors.registerItemModels();
    }
}
