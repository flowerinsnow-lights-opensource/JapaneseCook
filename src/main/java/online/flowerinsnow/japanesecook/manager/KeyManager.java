package online.flowerinsnow.japanesecook.manager;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class KeyManager {
    public static final KeyBinding TOGGLE = new KeyBinding(
            "keybinding.japanesecook.toggle", Keyboard.KEY_H, "keybinding.japanesecook.category"
    );
}
