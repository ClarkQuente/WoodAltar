package me.clarkquente.woodaltar.commands;

import me.clarkquente.woodaltar.WoodAltar;
import me.clarkquente.woodaltar.api.WoodAltarAPI;
import me.clarkquente.woodaltar.configuration.GeneralValue;
import me.clarkquente.woodaltar.configuration.MessageValue;
import me.clarkquente.woodaltar.models.Altar;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class AltarCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
        if(!(s instanceof Player)) return false;
        Player p = (Player) s;

        if(c.getName().equalsIgnoreCase("altar")) {

            if(!p.hasPermission("woodaltar.altar")) {
                p.sendMessage(MessageValue.get(MessageValue::noPermission));
                return false;
            }

            switch (args.length) {
                case 1:
                    if(args[0].equalsIgnoreCase("help")) {
                        MessageValue.get(MessageValue::altarHelp).forEach(p::sendMessage);
                        return true;
                    } else if(args[0].equalsIgnoreCase("list")) {

                        if(WoodAltar.getAltarManager().getAltarList().isEmpty()) {
                            p.sendMessage(MessageValue.get(MessageValue::noAltares));
                            return false;
                        }

                        String altarList = WoodAltar.getAltarManager().getAltarList().stream().map(Altar::getId).collect(Collectors.joining(", "));
                        p.sendMessage(MessageValue.get(MessageValue::altarList).replace("{altares}", altarList));
                        return true;
                    }
                    break;
                case 2:
                    if(args[0].equalsIgnoreCase("criar")) {
                        String id = args[1];

                        Altar altar = WoodAltarAPI.getInstance().getAltar(id);
                        if(altar != null) {
                            p.sendMessage(MessageValue.get(MessageValue::altarAlreadyExists));
                            return false;
                        }

                        WoodAltar.getAltarManager().add(
                                new Altar(
                                        id,
                                        p.getLocation(),
                                        GeneralValue.get(GeneralValue::defaultHealth),
                                        GeneralValue.get(GeneralValue::defaultHealth),
                                        GeneralValue.get(GeneralValue::defaultTime),
                                        new ArrayList<>(),
                                        false,
                                        false)
                        );

                        p.sendMessage(MessageValue.get(MessageValue::altarCreated)
                                .replace("{id}", id));
                        return true;

                    } else if(args[0].equalsIgnoreCase("delete")) {
                        String id = args[1];

                        Altar altar = WoodAltarAPI.getInstance().getAltar(id);
                        if(altar == null) {
                            p.sendMessage(MessageValue.get(MessageValue::altarNotFound));
                            return false;
                        }

                        if(altar.isAlive()) {
                            p.sendMessage(MessageValue.get(MessageValue::altarAlive));
                            return false;
                        }

                        WoodAltar.getAltarManager().remove(altar);
                        p.sendMessage(MessageValue.get(MessageValue::altarDeleted));
                        return true;

                    } else if(args[0].equalsIgnoreCase("spawn")) {
                        String id = args[1];

                        Altar altar = WoodAltarAPI.getInstance().getAltar(id);
                        if(altar == null) {
                            p.sendMessage(MessageValue.get(MessageValue::altarNotFound));
                            return false;
                        }

                        if(altar.isAlive()) {
                            p.sendMessage(MessageValue.get(MessageValue::altarAlive));
                            return false;
                        }

                        altar.spawnAltar();
                        return true;

                    } else if(args[0].equalsIgnoreCase("unspawn")) {
                        String id = args[1];

                        Altar altar = WoodAltarAPI.getInstance().getAltar(id);
                        if(altar == null) {
                            p.sendMessage(MessageValue.get(MessageValue::altarNotFound));
                            return false;
                        }

                        if(!altar.isAlive()) {
                            p.sendMessage(MessageValue.get(MessageValue::altarNotAlive));
                            return false;
                        }

                        altar.unspawnAltar();
                        p.sendMessage(MessageValue.get(MessageValue::altarUnspawned));
                        return true;

                    } else if(args[0].equalsIgnoreCase("teleport")) {

                        String id = args[1];

                        Altar altar = WoodAltarAPI.getInstance().getAltar(id);
                        if(altar == null) {
                            p.sendMessage(MessageValue.get(MessageValue::altarNotFound));
                            return false;
                        }

                        p.teleport(altar.getLocation());
                        p.sendMessage(MessageValue.get(MessageValue::teleportedToAltar));
                        return true;
                    }
                    break;
                case 3:
                    if(args[0].equalsIgnoreCase("edit")) {

                        String id = args[1];

                        Altar altar = WoodAltarAPI.getInstance().getAltar(id);
                        if(altar == null) {
                            p.sendMessage(MessageValue.get(MessageValue::altarNotFound));
                            return false;
                        }

                        if(args[2].equalsIgnoreCase("itens")) {

                            Inventory inventory = Bukkit.createInventory(null, 3*9, "§8Recompensas do Altar: " + altar.getId());

                            if(!altar.getRewards().isEmpty())
                                altar.getRewards().forEach(inventory::addItem);

                            p.openInventory(inventory);
                            return true;

                        } else if(args[2].equalsIgnoreCase("local")) {

                            if(altar.isAlive()) {
                                p.sendMessage(MessageValue.get(MessageValue::cantChange));
                                return false;
                            }

                            altar.setLocation(p.getLocation());
                            p.sendMessage(MessageValue.get(MessageValue::localChanged));
                            return true;

                        } else if(args[2].equalsIgnoreCase("autostart")) {

                            p.sendMessage(MessageValue.get(MessageValue::autoStartChanged)
                                    .replace("{status}", altar.isAutomaticallyStart() ? "§cDesativado" : "§aAtivado"));
                            altar.setAutomaticallyStart(!altar.isAutomaticallyStart());
                            return true;

                        }
                    }
                    break;
                case 4:
                    if(args[0].equalsIgnoreCase("edit")) {

                        String id = args[1];

                        Altar altar = WoodAltarAPI.getInstance().getAltar(id);
                        if(altar == null) {
                            p.sendMessage(MessageValue.get(MessageValue::altarNotFound));
                            return false;
                        }

                        if(!NumberUtils.isNumber(args[3]) || !isInteger(args[3])) {
                            p.sendMessage(MessageValue.get(MessageValue::invalidNumber));
                            return false;
                        }

                        int number = Integer.parseInt(args[3]);

                        if(args[2].equalsIgnoreCase("tempo")) {

                            altar.setMinutesToRespawn(number);
                            p.sendMessage(MessageValue.get(MessageValue::timeChanged)
                                    .replace("{altar}", altar.getId()));
                            return true;

                        } else if(args[2].equalsIgnoreCase("vida")) {

                            if(altar.isAlive()) {
                                p.sendMessage(MessageValue.get(MessageValue::cantChange));
                                return false;
                            }

                            altar.setMaxHealth(number);
                            p.sendMessage(MessageValue.get(MessageValue::maxHealthChanged));
                            return true;
                        }
                    }
                    break;
            }
            MessageValue.get(MessageValue::altarHelp).forEach(p::sendMessage);
        }
        return false;
    }

    private boolean isInteger(String args) {
        try {
            Integer.parseInt(args);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
