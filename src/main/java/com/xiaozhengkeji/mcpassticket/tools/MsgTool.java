package com.xiaozhengkeji.mcpassticket.tools;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;

public class MsgTool {
    /**
     * 发送给服务端的日记方法
     *
     * @param str 消息内容
     */
    public static void logServer(String str) {
        Bukkit.getLogger().info("§d[§5MC通行证§d]§7>>>§b" + str);
    }

    /**
     * 发送给服务端的日记方法
     *
     * @param p   玩家
     * @param str 消息内容
     */
    public static void logPlayer(Player p, String str) {
        p.sendMessage("§d[§5MC通行证§d]§7>>>§b" + str);
    }

    /**
     * 给全服玩家发送消息
     *
     * @param str 消息内容
     */
    public static void logALLPlayer(String str) {
        Collection<? extends Player> OnlinePlayer = Bukkit.getOnlinePlayers();
        OnlinePlayer.forEach(player -> player.sendMessage("§d[§5MC通行证§d]§7>>>§b" + str));
    }
}
