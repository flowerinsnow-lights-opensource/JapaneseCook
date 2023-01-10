package online.flowerinsnow.japanesecook.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;

@SideOnly(Side.CLIENT)
public final class Config {
    private Config() {
    }

    private static Configuration config;
    private static File configFile;

    public static void init(File configFile) {
        Config.configFile = configFile;
    }

    public static void load() {
        config = new Configuration(configFile);
        final String generalCategory = "general";
        General.itemName = config.get(generalCategory, "item_name", "Auspicious Gemstone Gauntlet", "你的镐子的名字");
        General.period = config.get(generalCategory, "period", 200, "技能好后检测的周期，单位：Tick（1秒≈20Ticks）");
        General.fallback = config.get(generalCategory, "fallback", 60, "技能开后如果没成功开启，再次尝试的时间，单位：Tick（1秒≈20Ticks）。请勿设置的比较低，以免封号");
        General.notify = config.get(generalCategory, "notify", true, "技能准备好后，如果没有开启功能，是否提醒");

        final String messageCategory = "message";
        Message.available = config.get(messageCategory, "available", "Mining Speed Boost is now available!", "技能准备好时客户端收到的消息，解析用");
        Message.used = config.get(messageCategory, "used", "You used your Mining Speed Boost Pickaxe Ability!", "技能使用后客户端收到的消息，解析用");
        Message.cooldown = config.get(messageCategory, "cooldown", "This ability is on cooldown for [0-9]{1,3}s\\.", "技能冷却中客户端收到的消息（正则），解析用");
    }

    public static void save() {
        config.save();
    }

    public static class General {
        public static boolean enabled;
        public static Property itemName;
        public static Property period;
        public static Property fallback;
        public static Property notify;
    }

    public static class Message {
        public static Property available;
        public static Property used;
        public static Property cooldown;
    }
}
