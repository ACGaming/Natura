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

/**
 * This class implements the IFixableData interface to remap item IDs from legacy to new (flattened replacements).
 */
public class ItemIDFixer implements IFixableData
{
    // Map to store old item resource locations and their corresponding new resource locations
    private static final Map<ResourceLocation, ResourceLocation> ITEM_NAME_MAPPINGS = new HashMap<>();

    static
    {
        // Initialize the item mappings
        ITEM_NAME_MAPPINGS.put(new ResourceLocation(Natura.modID, "empty_bowls"), new ResourceLocation("minecraft", "bowl"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation(Natura.modID, "soups"), new ResourceLocation("minecraft", "mushroom_stew"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation(Natura.modID, "sticks"), new ResourceLocation("minecraft", "stick"));
    }

    /**
     * Constructs an instance of ItemIDFixer and registers it to Forge's event bus.
     */
    public ItemIDFixer()
    {
        MinecraftForge.EVENT_BUS.register(this);
    }

    /**
     * Returns the internal version the datafixes should be applied for.
     *
     * @return The fix version
     */
    @Override
    public int getFixVersion()
    {
        return 1;
    }

    /**
     * Fixes the item ID in the provided NBTTagCompound.
     *
     * @param compound The NBTTagCompound to fix
     * @return The fixed NBTTagCompound
     */
    @Override
    public NBTTagCompound fixTagCompound(NBTTagCompound compound)
    {
        if (compound.hasKey("id"))
        {
            String oldItemId = compound.getString("id");
            ResourceLocation oldItemLocation = new ResourceLocation(oldItemId);

            // Handle remapping for natura:soups
            if (oldItemLocation.equals(new ResourceLocation(Natura.modID, "soups")))
            {
                int damage = compound.getShort("Damage");
                ResourceLocation newSoupLocation = null;

                // Remap based on metadata (damage) value
                if (damage >= 0 && damage <= 3) newSoupLocation = new ResourceLocation("minecraft", "mushroom_stew");
                else if (damage >= 4 && damage <= 8) newSoupLocation = new ResourceLocation(Natura.modID, "glowshroom_stew");
                else if (damage == 9) newSoupLocation = new ResourceLocation(Natura.modID, "berry_medley");

                if (newSoupLocation != null)
                {
                    Item newSoupItem = ForgeRegistries.ITEMS.getValue(newSoupLocation);
                    if (newSoupItem != null)
                    {
                        compound.setString("id", newSoupLocation.toString());
                        // Set metadata to 0 for remapped items
                        compound.removeTag("Damage");
                        return compound;
                    }
                }
            }
            else
            {
                // Check if the item needs to be remapped
                ResourceLocation newItemLocation = ITEM_NAME_MAPPINGS.get(oldItemLocation);
                if (newItemLocation != null)
                {
                    Item newItem = ForgeRegistries.ITEMS.getValue(newItemLocation);
                    if (newItem != null)
                    {
                        compound.setString("id", newItemLocation.toString());
                        // Set metadata to 0 for remapped items
                        compound.removeTag("Damage");
                        return compound;
                    }
                }
            }
        }
        return compound;
    }

    /**
     * Handles ignoring of missing item mappings.
     *
     * @param event The missing item mappings event
     */
    @SubscribeEvent
    public void missingItemMapping(RegistryEvent.MissingMappings<Item> event)
    {
        for (RegistryEvent.MissingMappings.Mapping<Item> entry : event.getAllMappings())
        {
            Optional.ofNullable(ITEM_NAME_MAPPINGS.get(entry.key)).map(ForgeRegistries.ITEMS::getValue).ifPresent(item -> entry.ignore());
        }
    }
}