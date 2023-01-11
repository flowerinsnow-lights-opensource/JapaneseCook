package online.flowerinsnow.japanesecook.util;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import online.flowerinsnow.japanesecook.config.Config;

public class ItemUtils {
    private ItemUtils() {
    }

    /**
     * 判断当前手上的物品是否与设置的物品相符
     */
    public static boolean isTargetItem() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null || mc.currentScreen != null) {
            return false;
        }
        ItemStack heldItem = mc.thePlayer.getHeldItem();
        if (heldItem != null) {
            return MessageUtils.removeColourCode(heldItem.getDisplayName())
                    .equals(Config.General.itemName.getString());
        } else {
            return false;
        }
    }
}
