package com.imjustdoom.justanimations.animation;

import com.imjustdoom.justanimations.JustAnimations;
import com.imjustdoom.justanimations.animation.frame.AnimationFrame;
import com.imjustdoom.justanimations.api.events.AnimationEndEvent;
import com.imjustdoom.justanimations.api.events.AnimationFrameChangeEvent;
import com.imjustdoom.justanimations.api.events.AnimationStartEvent;
import com.imjustdoom.justanimations.util.BlockVector;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class BlockAnimation implements IAnimation {

    private World world;
    private Map<Integer, AnimationFrame> frames = new HashMap<>();
    private BukkitTask runnable;
    private boolean reverse, reverseSpeedUp, running = false;
    private int frameCount;
    private File animationDir;

    public BlockAnimation() {
    }

    public BlockAnimation(World world) {
        this.world = world;
    }

    public void addFrame(int frameNumber, AnimationFrame frame) {
        frames.put(frameNumber, frame);
    }

    // TODO: Add a method to remove a frame
    public void removeFrame(int frame) {
        frames.remove(frame);
        int last = -1;
        for(int i : frames.keySet()) {
            if(i - 1 != last) {

            }
            last = i;
        }
    }

    public boolean gotoFrame(int frame) {
        if(!this.runnable.isCancelled()) {
            this.runnable.cancel();
            this.running = false;
        }

        frames.remove(frame);

        File animationFile = new File(animationDir + "/" + frame + ".yml");
        if(!animationFile.exists()) return false;

        this.frame = frame;
        FileConfiguration newFrame = YamlConfiguration.loadConfiguration(animationFile);
        Map<BlockVector, BlockData> blockVectors = new HashMap<>();

        if (newFrame.contains("blocks")) {
            for (String key : newFrame.getConfigurationSection("blocks").getKeys(false)) {
                for (String key1 : newFrame.getConfigurationSection("blocks." + key).getKeys(false)) {
                    for (String key2 : newFrame.getConfigurationSection("blocks." + key + "." + key1).getKeys(false)) {
                        BlockData blockData;
                        try {
                            blockData = Bukkit.getServer().createBlockData(newFrame.getString("blocks." + key + "." + key1 + "." + key2));
                        } catch (Exception e) {
                            blockData = Material.AIR.createBlockData();
                        }
                        blockVectors.put(new BlockVector(Integer.valueOf(key), Integer.valueOf(key1), Integer.valueOf(key2)), blockData);
                    }
                }
            }
        }

        AnimationFrame animationFrame = new AnimationFrame(blockVectors, newFrame.getInt("delay"));
        frames.put(frame, animationFrame);

        for(BlockVector loc : animationFrame.getBlockVectors().keySet()) {
            this.world.getBlockAt(loc.getX(), loc.getY(), loc.getZ()).setBlockData(getFrames().get(frame).getBlockVectors().get(loc));
        }

        AnimationFrameChangeEvent animationFrameChangeEvent = new AnimationFrameChangeEvent(this);
        Bukkit.getPluginManager().callEvent(animationFrameChangeEvent);

        return true;
    }

    private int frame = 0, timer = 0;
    public boolean goingReverse = false;
    public void play() {
        running = true;
        runnable = Bukkit.getScheduler().runTaskTimer(JustAnimations.INSTANCE, () -> {
            if (Bukkit.getOnlinePlayers().size() == 0) stop();
            if (timer == (goingReverse ? frames.get(frame).getDelay() / 2 : frames.get(frame).getDelay())) {

                for(BlockVector loc : getFrames().get(frame).getBlockVectors().keySet()) {
                    this.world.getBlockAt(loc.getX(), loc.getY(), loc.getZ()).setBlockData(getFrames().get(frame).getBlockVectors().get(loc));
                }

                frames.remove(frame);

                if (!reverse) {
                    frame = frame + 1 == frameCount - 1 ? 0 : ++frame;
                } else {
                    if (goingReverse) {
                        if (frame - 1 == -1) {
                            goingReverse = false;
                            ++frame;
                        } else --frame;
                    } else {
                        if (frame + 1 == - 1) {
                            goingReverse = true;
                            --frame;
                        } else ++frame;
                    }
                }

                FileConfiguration newFrame = YamlConfiguration.loadConfiguration(new File(animationDir + "/" + frame + ".yml"));
                Map<BlockVector, BlockData> blockVectors = new HashMap<>();

                if (newFrame.contains("blocks")) {
                    for (String key : newFrame.getConfigurationSection("blocks").getKeys(false)) {
                        for (String key1 : newFrame.getConfigurationSection("blocks." + key).getKeys(false)) {
                            for (String key2 : newFrame.getConfigurationSection("blocks." + key + "." + key1).getKeys(false)) {
                                BlockData blockData;
                                try {
                                    blockData = Bukkit.getServer().createBlockData(newFrame.getString("blocks." + key + "." + key1 + "." + key2));
                                } catch (Exception e) {
                                    blockData = Material.AIR.createBlockData();
                                }
                                blockVectors.put(new BlockVector(Integer.valueOf(key), Integer.valueOf(key1), Integer.valueOf(key2)), blockData);
                            }
                        }
                    }
                }

                frames.put(frame, new AnimationFrame(blockVectors, newFrame.getInt("delay")));

                AnimationFrameChangeEvent animationFrameChangeEvent = new AnimationFrameChangeEvent(this);
                Bukkit.getPluginManager().callEvent(animationFrameChangeEvent);

                timer = 0;
            }
            timer++;
        }, 0L, 0L);
        AnimationStartEvent animationStartEvent = new AnimationStartEvent(this);
        Bukkit.getPluginManager().callEvent(animationStartEvent);
    }

    public void stop() {
        runnable.cancel();
        running = false;
        AnimationEndEvent animationEndEvent = new AnimationEndEvent(this);
        Bukkit.getPluginManager().callEvent(animationEndEvent);
    }
}
