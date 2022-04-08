package com.imjustdoom.justanimations.command.subcommand.impl;

import com.imjustdoom.justanimations.JustAnimations;
import com.imjustdoom.justanimations.api.util.TranslationUtil;
import com.imjustdoom.justanimations.command.subcommand.SubCommand;
import com.imjustdoom.justanimations.config.AnimationsConfig;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class GoToFrameCmd implements SubCommand {

    public String getName() {
        return "gotoframe";
    }

    public String getDescription() {
        return "Sets the current frame of an animation";
    }

    public void execute(CommandSender sender, String[] args) {
        if (!JustAnimations.INSTANCE.getAnimations().get(args[1]).gotoFrame(Integer.parseInt(args[3]))) {
            sender.sendMessage(TranslationUtil.translatePlaceholders(AnimationsConfig.PREFIX + AnimationsConfig.Messages.GO_TO_FRAME_NOT_EXISTS,
                    args[1],
                    args[3]));
            return;
        }
        JustAnimations.INSTANCE.getAnimations().get(args[1]).gotoFrame(Integer.parseInt(args[3]));
        sender.sendMessage(TranslationUtil.translatePlaceholders(AnimationsConfig.PREFIX + AnimationsConfig.Messages.GO_TO_FRAME,
                args[1],
                args[3]));
    }

    public String[] getPermission() {
        return new String[]{"justanimations.gotoframe", "justanimations.admin"};
    }

    public List<SubCommand> getSubCommands() {
        return null;
    }

    public List<String> getTabCompletions(CommandSender sender, String[] args) {
        if (JustAnimations.INSTANCE.getAnimations().get(args[1]) == null) return null;
        List<String> frames = new ArrayList<>();
        int i = 0;
        while(i <= JustAnimations.INSTANCE.getAnimations().get(args[1]).getFrameCount() - 1){
            frames.add(String.valueOf(i));
            i++;
        }
        return frames;
    }
}