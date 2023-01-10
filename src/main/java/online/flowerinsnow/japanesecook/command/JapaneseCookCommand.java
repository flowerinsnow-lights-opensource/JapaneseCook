package online.flowerinsnow.japanesecook.command;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.crash.CrashReport;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import online.flowerinsnow.japanesecook.config.Config;
import online.flowerinsnow.japanesecook.util.MessageUtils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@SideOnly(Side.CLIENT)
public class JapaneseCookCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "japanesecook";
    }

    @Override
    public List<String> getCommandAliases() {
        return new ArrayList<>(Collections.singletonList("jc"));
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "command.japanesecook.usage";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return sender.equals(Minecraft.getMinecraft().thePlayer);
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (1 == args.length) {
            switch (args[0].toLowerCase()) {
                case "reload":
                    Config.load();
                    Config.save();
                    try {
                        Pattern.compile(Config.Message.cooldown.getString());
                    } catch (PatternSyntaxException e) {
                        Minecraft.getMinecraft().crashed(CrashReport.makeCrashReport(e, "配置文件中的message.cooldown不是一个合法的正则表达式"));
                        return;
                    }
                    MessageUtils.showText(I18n.format("command.japanesecook.reload"));
                    return;
                case "item":
                    Minecraft mc = Minecraft.getMinecraft();
                    String itemName = MessageUtils.removeColourCode(mc.thePlayer.getHeldItem().getDisplayName());
                    Clipboard cp = Toolkit.getDefaultToolkit().getSystemClipboard();
                    StringSelection ss = new StringSelection(itemName);
                    cp.setContents(ss, null);
                    MessageUtils.showText(I18n.format("command.japanesecook.copied", itemName));
                    return;
            }
        }
        throw new WrongUsageException(getCommandUsage(sender));
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (1 == args.length) {
            List<String> subCommands = new ArrayList<>(Arrays.asList("reload", "item"));
            subCommands.removeIf(s -> !s.toLowerCase().startsWith(args[0].toLowerCase()));
            return subCommands;
        }
        return new ArrayList<>();
    }
}
