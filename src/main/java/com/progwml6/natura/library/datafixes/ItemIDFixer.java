package com.progwml6.natura.library.datafixes;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.IFixableData;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import com.progwml6.natura.Natura;

public class ItemIDFixer implements IFixableData
{
    private static final Map<ResourceLocation, ResourceLocation> ITEM_NAME_MAPPINGS = new HashMap<>();

    static
    {
        ITEM_NAME_MAPPINGS.put(new ResourceLocation(Natura.modID, "sticks"), new ResourceLocation("minecraft", "stick"));
    }

    public ItemIDFixer()
    {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public int getFixVersion()
    {
        return 1;
    }

    @Override
    public NBTTagCompound fixTagCompound(NBTTagCompound compound)
    {
        if (compound.hasKey("id"))
        {
            String oldItemID = compound.getString("id");
            ResourceLocation oldItemResLoc = new ResourceLocation(oldItemID);
            ResourceLocation newItemResLoc = ITEM_NAME_MAPPINGS.get(oldItemResLoc);
            if (newItemResLoc != null)
            {
                Item newItem = ForgeRegistries.ITEMS.getValue(newItemResLoc);
                if (newItem != null)
                {
                    compound.setString("id", newItemResLoc.toString());
                    compound.removeTag("Damage");
                }
            }
        }
        return compound;
    }

    @SubscribeEvent
    public void missingItemMapping(RegistryEvent.MissingMappings<Item> event)
    {
        for (RegistryEvent.MissingMappings.Mapping<Item> entry : event.getAllMappings())
        {
            Optional.ofNullable(ITEM_NAME_MAPPINGS.get(entry.key)).map(ForgeRegistries.ITEMS::getValue).ifPresent(item -> entry.ignore());
        }
    }
}