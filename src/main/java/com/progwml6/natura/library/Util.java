package com.progwml6.natura.library;

import java.util.Locale;

import com.progwml6.natura.Tags;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.util.ResourceLocation;

public class Util
{
    public static final String MODID = Tags.MOD_ID;

    public static final String RESOURCE = MODID.toLowerCase(Locale.US);

    public static final Integer DATAFIXER_VERSION = 1;

    public static Logger getLogger(String type)
    {
        String log = MODID;

        return LogManager.getLogger(log + "-" + type);
    }

    /**
     * Returns the given Resource prefixed with tinkers resource location. Use this function instead of hardcoding resource locations.
     */
    public static String resource(String res)
    {
        return String.format("%s:%s", RESOURCE, res);
    }

    public static ResourceLocation getResource(String res)
    {
        return new ResourceLocation(RESOURCE, res);
    }

    /**
     * Prefixes the given unlocalized name with tinkers prefix. Use this when passing unlocalized names for a uniform namespace.
     */
    public static String prefix(String name)
    {
        return String.format("%s.%s", RESOURCE, name.toLowerCase(Locale.US));
    }
}
