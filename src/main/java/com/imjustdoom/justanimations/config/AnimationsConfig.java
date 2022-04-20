package com.imjustdoom.justanimations.config;

import com.imjustdoom.justanimations.JustAnimations;
import com.imjustdoom.justanimations.animation.IAnimation;
import com.imjustdoom.justanimations.api.util.AnimationUtil;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class AnimationsConfig {

    public static FileConfiguration config;

    public static String PREFIX;

    public static class Settings {

    }

    public static class Messages {
        public static String RELOAD, RELOAD_ERROR, RELOAD_SUCCESS;
        public static String CREATE, CREATE_ERROR, CREATE_SUCCESS, CREATE_EXISTS, CREATE_INVALID;
        public static String DELETE, DELETE_ERROR, DELETE_SUCCESS, DELETE_NOT_EXISTS;
        public static String ANIMATION_NOT_EXISTS;
        public static String HELP;
        public static String TOGGLE_REVERSE;
        public static String WORLD_CHANGE, WORLD_NOT_EXISTS, WORLD_IN_USE, WORLD_NO_VALUE;
        public static String SETTINGS;
        public static String GETFRAME;
        public static String ADDFRAME, ADDFRAME_ERROR;
        public static String PLAY_ANIMATION, PLAY_ANIMATION_ERROR, PLAY_ANIMATION_RUNNING, PLAY_ANIMATION_EMPTY;
        public static String STOP_ANIMATION, STOP_ANIMATION_ERROR, STOP_ANIMATION_NOT_RUNNING;
        public static String GO_TO_FRAME, GO_TO_FRAME_ERROR, GO_TO_FRAME_NOT_EXISTS;
        public static String CONVERTING, CONVERTING_ERROR, CONVERTING_SUCCESS, CONVERTING_IN_PROGRESS;
        public static String FRAME_SELECTION_SUCCESS, FRAME_SELECTION_ERROR;
        public static String SETLOAD, SETLOAD_SUCCESS, SETLOAD_ERROR, SETLOAD_ALREADY_SET;
        public static String RANDOM_ERROR, RANDOM_SUCCESS;
        public static String RENAME_SUCCESS, RENAME_ERROR, RENAME_EXISTS, RENAME_NO_VALUE;
        public static String REMOVE, REMOVE_SUCCESS, REMOVE_ERROR, REMOVE_NOT_EXISTS;
        public static String EDIT_FRAME_SUCCESS, EDIT_FRAME_ERROR, EDIT_FRAME_NOT_EXISTS;
        public static String DELAY_SUCCESS, DELAY_ERROR, DELAY_NOT_EXISTS, DELAY_NO_VALUE, DELAY_NO_FRAME;
    }

    public static void load() {

        JustAnimations.INSTANCE.reloadConfig();
        config = JustAnimations.INSTANCE.getConfig();

        PREFIX = config.getString("prefix");
        Messages.RELOAD = config.getString("messages.reload");
        Messages.RELOAD_ERROR = config.getString("messages.reload-error");
        Messages.RELOAD_SUCCESS = config.getString("messages.reload-success");

        Messages.CREATE = config.getString("messages.create");
        Messages.CREATE_ERROR = config.getString("messages.create-error");
        Messages.CREATE_SUCCESS = config.getString("messages.create-success");
        Messages.CREATE_EXISTS = config.getString("messages.create-exists");
        Messages.CREATE_INVALID = config.getString("messages.create-invalid");

        Messages.DELETE = config.getString("messages.delete");
        Messages.DELETE_ERROR = config.getString("messages.delete-error");
        Messages.DELETE_SUCCESS = config.getString("messages.delete-success");
        Messages.DELETE_NOT_EXISTS = config.getString("messages.delete-not-exists");

        Messages.ANIMATION_NOT_EXISTS = config.getString("messages.animation-not-exists");

        Messages.HELP = config.getString("messages.help");

        Messages.TOGGLE_REVERSE = config.getString("messages.toggle-reverse");

        Messages.WORLD_CHANGE = config.getString("messages.world-change");
        Messages.WORLD_NOT_EXISTS = config.getString("messages.world-not-exists");
        Messages.WORLD_IN_USE = config.getString("messages.world-in-use");
        Messages.WORLD_NO_VALUE = config.getString("messages.world-no-value");

        Messages.SETTINGS = config.getString("messages.settings");

        Messages.GETFRAME = config.getString("messages.getframe");

        Messages.ADDFRAME = config.getString("messages.addframe");
        Messages.ADDFRAME_ERROR = config.getString("messages.addframe-error");

        Messages.PLAY_ANIMATION = config.getString("messages.play-animation");
        Messages.PLAY_ANIMATION_ERROR = config.getString("messages.play-animation-error");
        Messages.PLAY_ANIMATION_RUNNING = config.getString("messages.play-animation-running");
        Messages.PLAY_ANIMATION_EMPTY = config.getString("messages.play-animation-empty");

        Messages.STOP_ANIMATION = config.getString("messages.stop-animation");
        Messages.STOP_ANIMATION_ERROR = config.getString("messages.stop-animation-error");
        Messages.STOP_ANIMATION_NOT_RUNNING = config.getString("messages.stop-animation-not-running");

        Messages.GO_TO_FRAME = config.getString("messages.go-to-frame");
        Messages.GO_TO_FRAME_ERROR = config.getString("messages.go-to-frame-error");
        Messages.GO_TO_FRAME_NOT_EXISTS = config.getString("messages.go-to-frame-not-exists");

        Messages.CONVERTING = config.getString("messages.converting");
        Messages.CONVERTING_ERROR = config.getString("messages.converting-error");
        Messages.CONVERTING_SUCCESS = config.getString("messages.converting-success");
        Messages.CONVERTING_IN_PROGRESS = config.getString("messages.converting-in-progress");

        Messages.FRAME_SELECTION_SUCCESS = config.getString("messages.frame-selection-success");
        Messages.FRAME_SELECTION_ERROR = config.getString("messages.frame-selection-error");

        Messages.SETLOAD = config.getString("messages.setload");
        Messages.SETLOAD_SUCCESS = config.getString("messages.setload-success");
        Messages.SETLOAD_ERROR = config.getString("messages.setload-error");
        Messages.SETLOAD_ALREADY_SET = config.getString("messages.setload-already-set");

        Messages.RANDOM_SUCCESS = config.getString("messages.random-success");
        Messages.RANDOM_ERROR = config.getString("messages.random-error");

        Messages.RENAME_SUCCESS = config.getString("messages.rename-success");
        Messages.RENAME_ERROR = config.getString("messages.rename-error");
        Messages.RENAME_EXISTS = config.getString("messages.rename-exists");
        Messages.RENAME_NO_VALUE = config.getString("messages.rename-no-value");

        Messages.REMOVE = config.getString("messages.remove");
        Messages.REMOVE_SUCCESS = config.getString("messages.remove-success");
        Messages.REMOVE_ERROR = config.getString("messages.remove-error");
        Messages.REMOVE_NOT_EXISTS = config.getString("messages.remove-not-exists");

        Messages.EDIT_FRAME_ERROR = config.getString("messages.edit-error");
        Messages.EDIT_FRAME_SUCCESS = config.getString("messages.edit-success");
        Messages.EDIT_FRAME_NOT_EXISTS = config.getString("messages.edit-not-exists");

        Messages.DELAY_SUCCESS = config.getString("messages.delay-success");
        Messages.DELAY_ERROR = config.getString("messages.delay-error");
        Messages.DELAY_NOT_EXISTS = config.getString("messages.delay-not-exists");
        Messages.DELAY_NO_VALUE = config.getString("messages.delay-no-value");
        Messages.DELAY_NO_FRAME = config.getString("messages.delay-no-frame");

        for (IAnimation animation : JustAnimations.INSTANCE.getAnimations().values()) {
            animation.stop();
        }

        JustAnimations.INSTANCE.getAnimations().clear();
        if (new File(JustAnimations.INSTANCE.getAnimationDataFolder()).exists()) {
            for (File animation : new File(JustAnimations.INSTANCE.getAnimationDataFolder()).listFiles()) {
                JustAnimations.INSTANCE.getAnimations().put(animation.getName(), AnimationUtil.loadAnimation(animation));
            }
        }
    }
}