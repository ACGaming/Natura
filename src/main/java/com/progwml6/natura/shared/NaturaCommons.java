package com.progwml6.natura.shared;

import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.Subscribe;
import com.progwml6.natura.Natura;
import com.progwml6.natura.common.CommonProxy;
import com.progwml6.natura.common.NaturaPulse;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.shared.block.clouds.BlockCloud;
import com.progwml6.natura.shared.item.bags.ItemBoneBag;
import com.progwml6.natura.shared.item.bags.ItemSeedBag;
import com.progwml6.natura.shared.item.food.ItemNaturaEdible;
import com.progwml6.natura.shared.item.food.ItemNaturaEdibleSoup;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import slimeknights.mantle.item.ItemMetaDynamic;
import slimeknights.mantle.pulsar.pulse.Pulse;

/**
 * Contains items and blocks and stuff that is shared by multiple pulses, but might be required individually
 */
@Pulse(id = NaturaCommons.PulseId, forced = true)
public class NaturaCommons extends NaturaPulse
{
    public static final String PulseId = "NaturaCommons";

    static final Logger log = Util.getLogger(PulseId);

    @SidedProxy(clientSide = "com.progwml6.natura.shared.CommonsClientProxy", serverSide = "com.progwml6.natura.common.CommonProxy")
    public static CommonProxy proxy;

    //@formatter:off
    // Blocks
    public static BlockCloud clouds;

    // Items
    public static ItemMetaDynamic materials;
    public static ItemNaturaEdible edibles;
    public static ItemSeedBag seed_bags;
    public static ItemBoneBag boneMealBag;

    // Material Itemstacks
    public static ItemStack barley;
    public static ItemStack barleyFlour;
    public static ItemStack wheatFlour;
    public static ItemStack cotton;

    public static ItemStack sulfurPowder;
    public static ItemStack ghostwoodFletching;
    public static ItemStack blueDye;

    public static ItemStack impLeather;
    public static ItemStack flameString;

    // Imp Meat
    public static ItemStack impmeatRaw;
    public static ItemStack impmeatCooked;

    // Soups
    public static Item berryMedley;
    public static Item glowshroom_stew;

    // Berries
    public static ItemStack raspberry;
    public static ItemStack blueberry;
    public static ItemStack blackberry;
    public static ItemStack maloberry;
    public static ItemStack blightberry;
    public static ItemStack duskberry;
    public static ItemStack skyberry;
    public static ItemStack stingberry;

    // Apples
    public static ItemStack potashApple;

    public static ItemStack cactusJuice;

    //Seed Bags
    public static ItemStack wheat_seed_bag;
    public static ItemStack carrots_seed_bag;
    public static ItemStack potatoes_seed_bag;
    public static ItemStack nether_wart_seed_bag;
    
    //@formatter:on

    @SubscribeEvent
    public void registerBlocks(Register<Block> event)
    {
        IForgeRegistry<Block> registry = event.getRegistry();

        // Blocks
        clouds = registerBlock(registry, new BlockCloud(), "clouds");
    }

    @SubscribeEvent
    public void registerItems(Register<Item> event)
    {
        IForgeRegistry<Item> registry = event.getRegistry();

        clouds = registerEnumItemBlock(registry, clouds, "clouds");

        // Items
        materials = registerItem(registry, new ItemMetaDynamic(), "materials");
        edibles = registerItem(registry, new ItemNaturaEdible(), "edibles");
        seed_bags = registerItem(registry, new ItemSeedBag(), "seed_bags");

        materials.setCreativeTab(Natura.TAB);
        edibles.setCreativeTab(Natura.TAB);
        seed_bags.setCreativeTab(Natura.TAB);

        barley = materials.addMeta(0, "barley");
        barleyFlour = materials.addMeta(1, "barley_flour");
        wheatFlour = materials.addMeta(2, "wheat_flour");
        cotton = materials.addMeta(3, "cotton");
        sulfurPowder = materials.addMeta(4, "sulfur_powder");
        ghostwoodFletching = materials.addMeta(5, "ghostwood_fletching");
        blueDye = materials.addMeta(8, "blue_dye");

        if (isEntitiesLoaded())
        {
            impLeather = materials.addMeta(6, "imp_leather");
            flameString = materials.addMeta(7, "flame_string");

            impmeatRaw = edibles.addFood(0, 3, 0.2f, 32, "impmeat_raw", false, new PotionEffect(MobEffects.HUNGER, 8 * 20, 0), new PotionEffect(MobEffects.POISON, 5 * 20, 0));
            impmeatCooked = edibles.addFood(1, 8, 0.6f, 32, "impmeat_cooked", false, new PotionEffect(MobEffects.FIRE_RESISTANCE, 15 * 20, 0), new PotionEffect(MobEffects.POISON, 5 * 20, 0));
        }

        if (isOverworldLoaded())
        {
            raspberry = edibles.addFood(2, 1, 0.4F, 16, "raspberry", false);
            blueberry = edibles.addFood(3, 1, 0.4F, 16, "blueberry", false);
            blackberry = edibles.addFood(4, 1, 0.4F, 16, "blackberry", false);
            maloberry = edibles.addFood(5, 1, 0.4F, 16, "maloberry", false);
            berryMedley = registerItem(registry, new ItemNaturaEdibleSoup(5, 1.4F, false), "berry_medley").setCreativeTab(Natura.TAB);
        }

        if (isNetherLoaded())
        {
            blightberry = edibles.addFood(6, 1, 0.4F, 16, "blightberry", false, new PotionEffect(MobEffects.REGENERATION, 8 * 20, 0), new PotionEffect(MobEffects.POISON, 5 * 20, 0), new PotionEffect(MobEffects.WITHER, 5 * 20, 0));
            duskberry = edibles.addFood(7, 1, 0.4F, 16, "duskberry", false, new PotionEffect(MobEffects.NIGHT_VISION, 15 * 20, 0), new PotionEffect(MobEffects.BLINDNESS, 3 * 20, 0));
            skyberry = edibles.addFood(8, 1, 0.4F, 16, "skyberry", false, new PotionEffect(MobEffects.JUMP_BOOST, 8 * 20, 0), new PotionEffect(MobEffects.SLOWNESS, 3 * 20, 0));
            stingberry = edibles.addFood(9, 1, 0.4F, 16, "stingberry", false, new PotionEffect(MobEffects.STRENGTH, 10 * 20, 0), new PotionEffect(MobEffects.MINING_FATIGUE, 10 * 20, 0));

            potashApple = edibles.addFood(10, 4, 0.4F, 32, "potashapple", false, new PotionEffect(MobEffects.POISON, 2 * 25, 0));
        }

        cactusJuice = edibles.addFood(11, 1, 0.1f, 12, "cactusjuice", false);

        glowshroom_stew = registerItem(registry, new ItemNaturaEdibleSoup(6, 0.6F, false), "glowshroom_stew").setCreativeTab(Natura.TAB);

        wheat_seed_bag = seed_bags.addMeta(0, "wheat_seed_bag", Blocks.WHEAT.getDefaultState().withProperty(BlockCrops.AGE, Integer.valueOf(0)));
        carrots_seed_bag = seed_bags.addMeta(1, "carrots_seed_bag", Blocks.CARROTS.getDefaultState().withProperty(BlockCrops.AGE, Integer.valueOf(0)));
        potatoes_seed_bag = seed_bags.addMeta(2, "potatoes_seed_bag", Blocks.POTATOES.getDefaultState().withProperty(BlockCrops.AGE, Integer.valueOf(0)));
        nether_wart_seed_bag = seed_bags.addMeta(3, "nether_wart_seed_bag", Blocks.NETHER_WART.getDefaultState().withProperty(BlockNetherWart.AGE, Integer.valueOf(0)));

        boneMealBag = registerItem(registry, new ItemBoneBag(), "bonemeal_bag");
    }

    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event)
    {
        proxy.preInit();
    }

    @Subscribe
    public void init(FMLInitializationEvent event)
    {
        proxy.init();

        this.registerSmelting();
    }

    private void registerSmelting()
    {
        FurnaceRecipes furnaceRecipes = FurnaceRecipes.instance();

        furnaceRecipes.addSmeltingRecipe(barleyFlour.copy(), new ItemStack(Items.BREAD, 1), 0.5f);
        furnaceRecipes.addSmeltingRecipe(wheatFlour.copy(), new ItemStack(Items.BREAD, 1), 0.5f);
    }
}
