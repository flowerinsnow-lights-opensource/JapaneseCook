package online.flowerinsnow.japanesecook.util;

import net.minecraft.client.Minecraft;
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

        return MessageUtils.removeColourCode(mc.thePlayer.getHeldItem().getDisplayName()).equals(Config.General.itemName.getString());
    }
}
