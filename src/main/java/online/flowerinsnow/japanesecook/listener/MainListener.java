package online.flowerinsnow.japanesecook.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import online.flowerinsnow.japanesecook.EnumStage;
import online.flowerinsnow.japanesecook.config.Config;
import online.flowerinsnow.japanesecook.event.ClientTickEndEvent;
import online.flowerinsnow.japanesecook.event.ClientWorldChangeEvent;
import online.flowerinsnow.japanesecook.manager.KeyManager;
import online.flowerinsnow.japanesecook.util.ItemUtils;
import online.flowerinsnow.japanesecook.util.MessageUtils;

import java.util.regex.Pattern;

@SideOnly(Side.CLIENT)
public class MainListener {
    public static EnumStage stage = EnumStage.ABILITY_COOLDOWN;
    /**
     * Tick计数
     */
    public static int tickCount;

    @SubscribeEvent
    public void onWorldChange(ClientWorldChangeEvent event) {
        stage = EnumStage.ABILITY_COOLDOWN;
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onMessage(ClientChatReceivedEvent event) {
        String message = MessageUtils.removeColourCode(event.message.getFormattedText());
        if (message.equals(Config.Message.available.getString())) {
            if (stage != EnumStage.ABILITY_READY && stage != EnumStage.ABILITY_FALLBACK && stage != EnumStage.ABILITY_USING) {
                stage = EnumStage.ABILITY_READY;
                tickCount = Config.General.period.getInt();
            }
            if (!Config.General.enabled && Config.General.notify.getBoolean()) {
                MessageUtils.showText(I18n.format("japanesecook.notify"));
            }
        } else if (message.equals(Config.Message.used.getString())
                || Pattern.compile(Config.Message.cooldown.getString()).matcher(message).matches()) {
            stage = EnumStage.ABILITY_COOLDOWN;
            tickCount = 0;
        }
    }

    @SubscribeEvent
    public void onTickEnd(ClientTickEndEvent event) {
        if (!Config.General.enabled) {
            return;
        }

        Minecraft mc = Minecraft.getMinecraft();

        if (stage == EnumStage.ABILITY_READY) { // 技能准备好了，如果间隔时间到了，就开技能
            if (tickCount++ >= Config.General.period.getInt()) {
                tickCount = 0;
                if (ItemUtils.isTargetItem()) {
                    stage = EnumStage.ABILITY_USING;
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                }
            }
        } else if (stage == EnumStage.ABILITY_FALLBACK) {
            if (!ItemUtils.isTargetItem()) {
                tickCount = 0;
                stage = EnumStage.ABILITY_READY;
            } else if (tickCount++ >= Config.General.fallback.getInt()) {
                tickCount = 0;
                stage = EnumStage.ABILITY_USING;
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
            }
        } else if (stage == EnumStage.ABILITY_USING) {
            tickCount = 0;
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
            stage = EnumStage.ABILITY_FALLBACK;
        } else {
            tickCount = 0;
        }
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (KeyManager.TOGGLE.isPressed() && mc.thePlayer != null) {
            pressToggleKey();
        }
    }

    @SubscribeEvent
    public void onKey(InputEvent.MouseInputEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (KeyManager.TOGGLE.isPressed() && mc.thePlayer != null) {
            pressToggleKey();
        }
    }

    private void pressToggleKey() {
        Config.General.enabled = !Config.General.enabled;
        Minecraft mc = Minecraft.getMinecraft();
        if (Config.General.enabled) {
            MessageUtils.showText(I18n.format("keybinding.japanesecook.toggle.enable"));
            mc.thePlayer.playSound("random.click", 1.0F, 1.0F);
        } else {
            MessageUtils.showText(I18n.format("keybinding.japanesecook.toggle.disable"));
            mc.thePlayer.playSound("note.bass", 1.0F, 1.0F);
        }
    }
}
