package com.xiaozhengkeji.mcpassticket.data;

import com.xiaozhengkeji.mcpassticket.entitys.FreePassTicket;
import com.xiaozhengkeji.mcpassticket.entitys.PlayerPassTicket;
import com.xiaozhengkeji.mcpassticket.entitys.PayPassTicket;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * 玩家的通行证实体HashMap集合
 * 玩家UUID作为Key
 */
public class PlayerHashMap {
    HashMap<String, PlayerPassTicket> map = new HashMap<>();

    public HashMap<String, PlayerPassTicket> getMap() {
        return map;
    }

    public PlayerPassTicket getPalyerPassTicket(Player player) {
        return map.get(player.getUniqueId().toString());
    }

    public PayPassTicket getPayPassTicket(Player player) {
        return map.get(player.getUniqueId().toString()).getPayPassTicket();
    }

    public FreePassTicket getFreePassTicket(Player player) {
        return map.get(player.getUniqueId().toString()).getFreePassTicket();
    }

    public void upDatePalyerPassTicket(Player player, PlayerPassTicket playerPassTicket) {
        map.put(player.getUniqueId().toString(), playerPassTicket);
    }
}
