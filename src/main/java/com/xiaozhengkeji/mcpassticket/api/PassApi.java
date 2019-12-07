package com.xiaozhengkeji.mcpassticket.api;

import com.xiaozhengkeji.mcpassticket.MCPassTicket;
import com.xiaozhengkeji.mcpassticket.entitys.PlayerPassTicket;
import org.bukkit.entity.Player;

public class PassApi {
    public static PlayerPassTicket getPlayerPassTicket(Player player) {
        return MCPassTicket.playerHashMap.getPalyerPassTicket(player);
    }
}
