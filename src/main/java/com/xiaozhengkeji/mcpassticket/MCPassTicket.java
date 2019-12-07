package com.xiaozhengkeji.mcpassticket;

import com.xiaozhengkeji.mcpassticket.Events.PassTicketEvent;
import com.xiaozhengkeji.mcpassticket.commands.PlayerCommand;
import com.xiaozhengkeji.mcpassticket.data.PassHashMap;
import com.xiaozhengkeji.mcpassticket.data.PlayerHashMap;
import com.xiaozhengkeji.mcpassticket.tools.MsgTool;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public final class MCPassTicket extends JavaPlugin {
    public static File PluginPath;
    public static File PlayerDataPath;
    public static PlayerHashMap playerHashMap = new PlayerHashMap();
    public static PassHashMap passHashMap = new PassHashMap();

    @Override
    public void onLoad() {
        MsgTool.logServer("插件读取开始!");
        saveDefaultConfig();//创建config.yml
        MCPassTicket.PluginPath = new File("./plugins/MCPassTicket");
        MsgTool.logServer("插件路径设置完成[" + MCPassTicket.PluginPath + "]");
        MCPassTicket.PlayerDataPath = new File("./plugins/MCPassTicket/data");
        if (!MCPassTicket.PlayerDataPath.exists()) {
            //创建目录
            MCPassTicket.PlayerDataPath.mkdirs();
            MsgTool.logServer("检测到第一次加载,创建缓存目录完成!");
        }
        MsgTool.logServer("插件玩家缓存路径设置完成[" + MCPassTicket.PlayerDataPath + "]");
    }

    @Override
    public void onEnable() {
        MsgTool.logServer("插件加载开始!");
        Set<String> 免费通行证奖励 = getConfig().getConfigurationSection("通行证奖励.免费通行证").getKeys(false);
        Set<String> 付费通行证奖励 = getConfig().getConfigurationSection("通行证奖励.付费通行证").getKeys(false);
        for (String 等级 : 免费通行证奖励) {
            passHashMap.get免费map().put(等级, getConfig().getStringList("通行证奖励.免费通行证." + 等级));
        }
        for (String 等级 : 付费通行证奖励) {
            passHashMap.get付费map().put(等级, getConfig().getStringList("通行证奖励.付费通行证." + 等级));
        }
        passHashMap.setDate(getConfig().getString("通行证.结算日期"));
        passHashMap.setExp(getConfig().getInt("通行证.每级需要经验"));
        //注册控制台
        this.getCommand("pass").setExecutor(new PlayerCommand());
        //注册监听器
        getServer().getPluginManager().registerEvents(new PassTicketEvent(), this);
        //创建缓存定时保存器
        //
        new BukkitRunnable() {
            @Override
            public void run() {
                upData();
            }
            //60秒 * 1000毫秒 更新一次本地缓存
        }.runTaskTimer(MCPassTicket.getProvidingPlugin(MCPassTicket.class), 60 * 1000, 60 * 1000);
    }

    @Override
    public void onDisable() {
        upData();
    }

    public static void upData() {
        MsgTool.logServer("正在将内存数据保存至磁盘请稍后!");
        Set<String> 玩家集合 = playerHashMap.getMap().keySet();
        for (String 玩家UUDI : 玩家集合) {
            File PlayerData = new File(MCPassTicket.PlayerDataPath, 玩家UUDI + ".yml");
            YamlConfiguration dataPlayer = YamlConfiguration.loadConfiguration(PlayerData);
            dataPlayer.set("exp", playerHashMap.getMap().get(玩家UUDI).getExp());
            List<String> 免费_已兑换 = playerHashMap.getMap().get(玩家UUDI).getFreePassTicket().getGiveOK();
            List<String> 付费_已兑换 = playerHashMap.getMap().get(玩家UUDI).getPayPassTicket().getGiveOK();
            for (String 已兑换 : 免费_已兑换) {
                dataPlayer.set("mf." + 已兑换, "1");
            }
            for (String 已兑换 : 付费_已兑换) {
                dataPlayer.set("ff." + 已兑换, "1");
            }
            try {
                dataPlayer.save(PlayerData);
                MsgTool.logServer("保存UUID:" + 玩家UUDI + "[成功]");
            } catch (IOException e) {
                MsgTool.logServer("保存UUID:" + 玩家UUDI + "[失败]");
                e.printStackTrace();
            }
        }
    }
}
