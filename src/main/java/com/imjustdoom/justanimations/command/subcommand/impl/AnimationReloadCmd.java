package com.imjustdoom.justanimations.command.subcommand.impl;

import com.imjustdoom.justanimations.JustAnimations;
import com.imjustdoom.justanimations.animation.IAnimation;
import com.imjustdoom.justanimations.api.util.TranslationUtil;
import com.imjustdoom.justanimations.command.subcommand.SubCommand;
import com.imjustdoom.justanimations.config.AnimationsConfig;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class AnimationReloadCmd implements SubCommand {

    public String getName() {
        return "reload";
    }

    public String getDescription() {
        return "Reloads a specific animation";
    }

    public void execute(CommandSender sender, String[] args) {

        sender.sendMessage(TranslationUtil.translatePlaceholders(AnimationsConfig.PREFIX + AnimationsConfig.Messages.RELOAD));

        IAnimation animation = JustAnimations.INSTANCE.getAnimations().get(args[1]);
        animation.reload();

        sender.sendMessage(TranslationUtil.translatePlaceholders(AnimationsConfig.PREFIX + AnimationsConfig.Messages.RELOAD_SUCCESS));
    }

    public String[] getPermission() {
        return new String[]{"justanimations.reload", "justanimations.admin"};
    }

    public List<SubCommand> getSubCommands() {
        return null;
    }

    public List<String> getTabCompletions(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}
