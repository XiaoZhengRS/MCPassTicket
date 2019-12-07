package com.xiaozhengkeji.mcpassticket.Events;

import com.xiaozhengkeji.mcpassticket.MCPassTicket;
import com.xiaozhengkeji.mcpassticket.tools.MsgTool;
import com.xiaozhengkeji.mcpassticket.tools.PassMapTool;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;

public class PassTicketEvent implements Listener {
    @EventHandler
    public void onPlayerJoinServer(PlayerJoinEvent event) {
        String UUID = event.getPlayer().getUniqueId().toString();
        File PlayerData = new File(MCPassTicket.PlayerDataPath, UUID + ".yml");
        if (!(PlayerData.exists())) {
            //无缓存,创建缓存
            try {
                PlayerData.createNewFile();
                YamlConfiguration dataPlayer = YamlConfiguration.loadConfiguration(PlayerData);
                dataPlayer.set("exp", 0);
                dataPlayer.save(PlayerData);
                MsgTool.logServer("玩家缓存创建完成:" + PlayerData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //更新Map
        PassMapTool.upDataPlayerMap(event.getPlayer());
        MsgTool.logServer("更新玩家缓存完成:" + event.getPlayer().getName());
    }
}
