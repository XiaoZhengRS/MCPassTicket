package com.xiaozhengkeji.mcpassticket.tools;

import com.xiaozhengkeji.mcpassticket.MCPassTicket;
import com.xiaozhengkeji.mcpassticket.entitys.FreePassTicket;
import com.xiaozhengkeji.mcpassticket.entitys.PlayerPassTicket;
import com.xiaozhengkeji.mcpassticket.entitys.PayPassTicket;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PassMapTool {
    /**
     * 从文件更新指定玩家Map
     */
    public static boolean upDataPlayerMap(Player p) {
        String UUID = p.getUniqueId().toString();
        File PlayerData = new File(MCPassTicket.PlayerDataPath, UUID + ".yml");
        YamlConfiguration PlayerDataYml = YamlConfiguration.loadConfiguration(PlayerData);
        Integer exp = PlayerDataYml.getInt("exp");
        List<String> 免费通行证领取记录等级 = new ArrayList<>();
        List<String> 付费通行证领取记录等级 = new ArrayList<>();
        if (PlayerDataYml.get("mf") == null) {
            免费通行证领取记录等级.add("-1");
        } else {
            Set<String> 免费通行证领取记录 = PlayerDataYml.getConfigurationSection("mf").getKeys(false);
            for (String pah : 免费通行证领取记录) {
                if (PlayerDataYml.getString("mf." + pah).equalsIgnoreCase("1")) {
                    免费通行证领取记录等级.add(pah);
                }
            }
        }

        if (PlayerDataYml.get("ff") == null) {
            付费通行证领取记录等级.add("-1");
        } else {
            Set<String> 付费通行证领取记录 = PlayerDataYml.getConfigurationSection("mf").getKeys(false);
            for (String pah : 付费通行证领取记录) {
                if (PlayerDataYml.getString("ff." + pah).equalsIgnoreCase("1")) {
                    付费通行证领取记录等级.add(pah);
                }
            }
        }


        PayPassTicket payPassTicket = new PayPassTicket(付费通行证领取记录等级);
        FreePassTicket freePassTicket = new FreePassTicket(免费通行证领取记录等级);
        PlayerPassTicket playerPassTicket = new PlayerPassTicket(freePassTicket, payPassTicket, exp);
        MCPassTicket.playerHashMap.upDatePalyerPassTicket(p, playerPassTicket);
        return true;
    }
}
