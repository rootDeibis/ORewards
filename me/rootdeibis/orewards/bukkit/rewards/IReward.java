package me.rootdeibis.orewards.bukkit.rewards;

import me.rootdeibis.orewards.bukkit.Main;
import me.rootdeibis.orewards.bukkit.db.DatabaseQuerys;
import me.rootdeibis.orewards.bukkit.enums.REWARDS_CONFIG;
import me.rootdeibis.orewards.bukkit.rewards.types.EnumRewardTypes;
import me.rootdeibis.orewards.bukkit.rewards.types.TimeReward;
import me.rootdeibis.orewards.common.TimeTool;
import me.rootdeibis.orewards.common.filemanagers.IFile;
import me.rootdeibis.orewards.common.function.Functions;
import me.rootdeibis.orewards.common.guifactory.ButtonFactory;
import me.rootdeibis.orewards.common.guifactory.MenuFactory;
import me.rootdeibis.orewards.common.guifactory.interfaces.IButton;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public abstract class IReward {
    private static final HashMap<UUID, Long> untils = new HashMap<>();

    public String getName() {
        return this.getConfig().getString(REWARDS_CONFIG.NAME.parse());
    }

    public EnumRewardTypes getType() {
        return EnumRewardTypes.valueOf(this.getConfig().getString(REWARDS_CONFIG.TYPE.parse()).toLowerCase());
    }

    abstract public IFile getConfig();

    abstract public boolean canClaim(Player player);

     public void claim(Player player) {

     }


    public String getPermission() {
        return this.getConfig().getString(REWARDS_CONFIG.PERMISSION.parse());
    }
    public boolean needPermission() {
        if (this.getPermission() != null) {
            return true;
        } else {
            return false;
        }

    }


    public boolean validate() {
        return Main.getDB().exec(String.format(DatabaseQuerys.CREATE_TABLE.parse(), this.getName()));
    }

    public boolean update(String date_format, UUID uuid) {
        String until = String.valueOf(TimeTool.addToDate(date_format));

        return Main.getDB().exec(String.format(DatabaseQuerys.UPDATE_USER.parse(), this.getName(), until, uuid.toString()));
    }

    abstract public REWARD_STATUS getStatus(Player player);


     public IButton importButtonStatus(Player player, MenuFactory menu, int slot) {
         IFile config = this.getConfig();


         Functions.RFunction<String> material = () -> {
             String $material = config.getString(REWARDS_CONFIG.STATUS_AVAILABLE_MATERIAL.parse());

             switch (this.getStatus(player)){
                 case NOT_AVAILABLE:
                     $material = config.getString(REWARDS_CONFIG.STATUS_NOT_AVAILABLE_MATERIAL.parse());
                     break;
                 case NEED_PERMISSION:
                     $material =  config.getString(REWARDS_CONFIG.STATUS_PERM_MATERIAL.parse());
             }

             return  $material;

         };

         Functions.RFunction<String> title = () -> {
             String $title = config.getString(REWARDS_CONFIG.STATUS_AVAILABLE_TITLE.parse());

            switch (this.getStatus(player)){
                case NOT_AVAILABLE:
                    $title = config.getString(REWARDS_CONFIG.STATUS_NOT_AVAILABLE_TITLE.parse());
                    break;
                case NEED_PERMISSION:
                    $title =  config.getString(REWARDS_CONFIG.STATUS_PERM_TITLE.parse());
            }

            return  $title;

         };


         Functions.RFunction<List<String>> lore = () -> {
             List<String> $lore = config.getStringList(REWARDS_CONFIG.STATUS_AVAILABLE_LORE.parse());

             switch (this.getStatus(player)){
                 case NOT_AVAILABLE:
                     $lore = config.getStringList(REWARDS_CONFIG.STATUS_NOT_AVAILABLE_LORE.parse());
                     break;
                 case NEED_PERMISSION:
                     $lore =  config.getStringList(REWARDS_CONFIG.STATUS_PERM_LORE.parse());
             }

             return  $lore;

         };

         IButton button = new ButtonFactory(menu, slot)
                 .setMaterial(material)
                 .setTitle(title)
                 .setLore(lore);

         button.setClickAction((event, factory) -> this.claim((Player)event.getWhoClicked()));

         return button;

     }


    public static IReward resolve(IFile file) {
        EnumRewardTypes type = EnumRewardTypes.valueOf(file.getString(REWARDS_CONFIG.TYPE.parse()));

        if (type == EnumRewardTypes.TIMED_REWARD) {
            return new TimeReward(file);
        }

        return null;
    }


    public long getUntil(UUID uuid) {
        if (!untils.containsKey(uuid)) {
            long until = TimeTool.addToDate("1s").getTime();

            String query = String.format(DatabaseQuerys.SELECT_USER.parse(), this.getName(), uuid.toString());
            String execQuery = String.format(DatabaseQuerys.CREATE_USER.parse(), this.getName(), uuid.toString(), until);

            if (!Main.getDB().has(query)) {
                Main.getDB().exec(execQuery);
            } else {
                until = Long.parseLong(Main.getDB().get(query, "until").get("until").toString());
            }

            untils.put(uuid, until);

        }

        return untils.get(uuid);
    }



    public enum REWARD_STATUS {
         AVAILABLE,
         NOT_AVAILABLE,
        NEED_PERMISSION
    }



}
