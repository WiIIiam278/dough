package io.github.bakedlibs.dough.items.nms;

import io.github.bakedlibs.dough.reflection.ReflectionUtils;
import io.github.bakedlibs.dough.versions.UnknownServerVersionException;
import org.bukkit.inventory.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ItemNameAdapter18_2 implements ItemNameAdapter {

    private final Method getCopy;
    private final Method getName;
    private final Method toString;

    ItemNameAdapter18_2() throws NoSuchMethodException, SecurityException, ClassNotFoundException, UnknownServerVersionException {
        super();

        getCopy = ReflectionUtils.getOBCClass("inventory.CraftItemStack").getMethod("asNMSCopy", ItemStack.class);
        // Spigot has changed the obf again for 1.18.2, so we have to adapt with it.
        getName = ReflectionUtils.getMethodOrAlternative(ReflectionUtils.getNetMinecraftClass("world.item.ItemStack"), "getDisplayName", "H");
        toString = ReflectionUtils.getMethod(ReflectionUtils.getNetMinecraftClass("network.chat.IChatBaseComponent"), "getString");
    }

    @Override
    @ParametersAreNonnullByDefault
    public String getName(ItemStack item) throws IllegalAccessException, InvocationTargetException {
        Object instance = getCopy.invoke(null, item);
        return (String) toString.invoke(getName.invoke(instance));
    }

}
