package com.imjustdoom.justanimations.command.subcommand.impl;

import com.imjustdoom.justanimations.JustAnimations;
import com.imjustdoom.justanimations.animation.IAnimation;
import com.imjustdoom.justanimations.api.util.TranslationUtil;
import com.imjustdoom.justanimations.command.subcommand.SubCommand;
import com.imjustdoom.justanimations.config.AnimationsConfig;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class PlayCmd implements SubCommand {

    public String getName() {
        return "play";
    }

    public String getDescription() {
        return "Plays the current animation";
    }

    public void execute(CommandSender sender, String[] args) {

        IAnimation animation = JustAnimations.INSTANCE.getAnimations().get(args[1]);

        if (animation.isRunning()) {
            sender.sendMessage(TranslationUtil.translatePlaceholders(AnimationsConfig.PREFIX + AnimationsConfig.Messages.PLAY_ANIMATION_RUNNING,
                    args[1], ""));
            return;
        }
        if(animation.getFrameCount() == 0) {
            sender.sendMessage(TranslationUtil.translatePlaceholders(AnimationsConfig.PREFIX + AnimationsConfig.Messages.PLAY_ANIMATION_EMPTY,
                    args[1], ""));
            return;
        }
        animation.getDataStore().saveSetting("inactive", false);
        animation.setInactive(false);
        animation.play(args.length != 3 && Boolean.parseBoolean(args[3]));
        sender.sendMessage(TranslationUtil.translatePlaceholders(AnimationsConfig.PREFIX + AnimationsConfig.Messages.PLAY_ANIMATION,
                args[1], ""));
    }

    public String[] getPermission() {
        return new String[]{"justanimations.play", "justanimations.admin"};
    }

    public List<SubCommand> getSubCommands() {
        return null;
    }

    public List<String> getTabCompletions(CommandSender sender, String[] args) {
        return Arrays.asList("true", "false");
    }
}