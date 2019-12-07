package com.xiaozhengkeji.mcpassticket.commands;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.xiaozhengkeji.mcpassticket.MCPassTicket;
import com.xiaozhengkeji.mcpassticket.tools.MsgTool;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args[0].equalsIgnoreCase("give")) {
            Player p = (Player) sender;
            if (!(p.hasPermission("pass.admin"))) {
                MsgTool.logPlayer(p, "权限不足无法给予玩家经验");
                return true;
            }
            Integer exp = MCPassTicket.playerHashMap.getPalyerPassTicket(Bukkit.getPlayer(args[1])).getExp();
            MCPassTicket.playerHashMap.getPalyerPassTicket(Bukkit.getPlayer(args[1])).setExp(exp + Integer.valueOf(args[2]));
            MsgTool.logPlayer(p, "给予玩家经验完成!");
            MCPassTicket.upData();
            return true;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage("请用客户端打开");
            return true;
        }
        Player p = (Player) sender;
        if (args.length == 0) {
            MsgTool.logPlayer(p, "通行证结束时间 ---> " + MCPassTicket.passHashMap.getDate());
            MsgTool.logPlayer(p, "通行证每级经验 ---> " + MCPassTicket.passHashMap.getExp());
            MsgTool.logPlayer(p, "您通行证当前经验 ---> " + MCPassTicket.playerHashMap.getMap().get(p.getUniqueId().toString()).getExp());
            MsgTool.logPlayer(p, "您通行证升级所需经验 ---> " + MCPassTicket.playerHashMap.getMap().get(p.getUniqueId().toString()).getExp() % MCPassTicket.passHashMap.getExp());
            MsgTool.logPlayer(p, "免费通行证奖励等级 ↓↓↓↓↓ ");
            for (String 等级 : MCPassTicket.passHashMap.get免费map().keySet()) {
                MsgTool.logPlayer(p, "[免费]等级[" + 等级 + "]");
            }
            MsgTool.logPlayer(p, "付费通行证奖励等级 ↓↓↓↓↓ ");
            for (String 等级 : MCPassTicket.passHashMap.get付费map().keySet()) {
                MsgTool.logPlayer(p, "[付费]等级[" + 等级 + "]");
            }
            MsgTool.logPlayer(p, "/pass mf [等级]  领取指定等级的免费通行证奖励");
            MsgTool.logPlayer(p, "/pass ff [等级]  领取指定等级的付费通行证奖励");
            MsgTool.logPlayer(p, "/pass give [玩家名] [经验]  给予指定玩家通行证经验 [需要pass.admin]");
            return true;
        }

        if (args[0].equalsIgnoreCase("mf")) {
            Integer 当前等级 = MCPassTicket.playerHashMap.getPalyerPassTicket(p).getExp() / MCPassTicket.passHashMap.getExp();
            if (当前等级 < Integer.valueOf(args[1])) {
                MsgTool.logPlayer(p, "等级不足无法兑换!");
                return true;
            }
            Boolean 是否可以兑换 = false;
            for (String 等级 : MCPassTicket.passHashMap.get免费map().keySet()) {
                if (等级.equalsIgnoreCase(args[1])) {
                    是否可以兑换 = true;
                }
            }
            if (!(是否可以兑换)) {
                MsgTool.logPlayer(p, "未找到指定等级免费通行证礼包");
                return true;
            }
            List<String> 免费兑换集合 = MCPassTicket.playerHashMap.getMap().get(p.getUniqueId().toString()).getFreePassTicket().getGiveOK();
            Boolean 兑换 = false;
            for (String 等级 : 免费兑换集合) {
                if (args[1].equalsIgnoreCase(等级)) {
                    兑换 = true;
                }
            }
            if (兑换) {
                MsgTool.logPlayer(p, "指定等级的免费通行证奖励已经领取,无法重复领取!");
            } else {
                List<String> CMDS = MCPassTicket.passHashMap.get免费map().get(args[1]);
                for (String cmd : CMDS) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replaceAll("\\{player}", p.getName()));
                }
                免费兑换集合.add(args[1]);
                MCPassTicket.playerHashMap.getPalyerPassTicket(p).getFreePassTicket().setGiveOK(免费兑换集合);
                MsgTool.logPlayer(p, "指定等级的免费通行证奖励领取成功");
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("ff")) {
            if (!(p.hasPermission("pass.ff"))) {
                MsgTool.logPlayer(p, "权限不足无法激活付费通行证礼包!");
                return true;
            }
            Integer 当前等级 = MCPassTicket.playerHashMap.getPalyerPassTicket(p).getExp() / MCPassTicket.passHashMap.getExp();
            if (当前等级 < Integer.valueOf(args[1])) {
                MsgTool.logPlayer(p, "等级不足无法兑换!");
                return true;
            }
            Boolean 是否可以兑换 = false;
            for (String 等级 : MCPassTicket.passHashMap.get付费map().keySet()) {
                if (等级.equalsIgnoreCase(args[1])) {
                    是否可以兑换 = true;
                }
            }
            if (!(是否可以兑换)) {
                MsgTool.logPlayer(p, "未找到指定等级付费通行证礼包");
                return true;
            }
            List<String> 付费兑换集合 = MCPassTicket.playerHashMap.getMap().get(p.getUniqueId().toString()).getPayPassTicket().getGiveOK();
            Boolean 兑换 = false;
            for (String 等级 : 付费兑换集合) {
                if (args[1].equalsIgnoreCase(等级)) {
                    兑换 = true;
                }
            }
            if (兑换) {
                MsgTool.logPlayer(p, "指定等级的付费通行证奖励已经领取,无法重复领取!");
            } else {
                List<String> CMDS = MCPassTicket.passHashMap.get付费map().get(args[1]);
                for (String cmd : CMDS) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replaceAll("\\{player}", p.getName()));
                }
                付费兑换集合.add(args[1]);
                MCPassTicket.playerHashMap.getPalyerPassTicket(p).getPayPassTicket().setGiveOK(付费兑换集合);
                MsgTool.logPlayer(p, "指定等级的付费通行证奖励领取成功");
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("give")) {
            if (!(p.hasPermission("pass.admin"))) {
                MsgTool.logPlayer(p, "权限不足无法给予玩家经验");
                return true;
            }
            Integer exp = MCPassTicket.playerHashMap.getPalyerPassTicket(Bukkit.getPlayer(args[1])).getExp();
            MCPassTicket.playerHashMap.getPalyerPassTicket(Bukkit.getPlayer(args[1])).setExp(exp + Integer.valueOf(args[2]));
            MsgTool.logPlayer(p, "给予玩家经验完成!");
            MCPassTicket.upData();
            return true;
        }
        return false;
    }
}
