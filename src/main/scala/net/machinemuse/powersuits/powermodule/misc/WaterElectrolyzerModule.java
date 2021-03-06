package net.machinemuse.powersuits.powermodule.misc;

import net.machinemuse.api.IModularItem;
import net.machinemuse.api.ModuleManager;
import net.machinemuse.api.moduletrigger.IPlayerTickModule;
import net.machinemuse.api.moduletrigger.IToggleableModule;
import net.machinemuse.general.sound.SoundLoader;
import net.machinemuse.numina.sound.proxy.Musique;
import net.machinemuse.powersuits.item.ItemComponent;
import net.machinemuse.powersuits.powermodule.PowerModuleBase;
import net.machinemuse.utils.ElectricItemUtils;
import net.machinemuse.utils.MuseCommonStrings;
import net.machinemuse.utils.MuseItemUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

public class WaterElectrolyzerModule extends PowerModuleBase implements IPlayerTickModule, IToggleableModule {
    public static final String WATERBREATHING_ENERGY_CONSUMPTION = "Jolt Energy";
    public static final String MODULE_WATER_ELECTROLYZER = "Water Electrolyzer";

    public WaterElectrolyzerModule(List<IModularItem> validItems) {
        super(validItems);
        addInstallCost(MuseItemUtils.copyAndResize(ItemComponent.lvcapacitor, 1));
        addBaseProperty(WaterElectrolyzerModule.WATERBREATHING_ENERGY_CONSUMPTION, 1000, "J");
        addPropertyLocalString(WATERBREATHING_ENERGY_CONSUMPTION, StatCollector.translateToLocal("module.waterElectrolyzer.energy"));
    }

    @Override
    public String getCategory() {
        return MuseCommonStrings.CATEGORY_ENVIRONMENTAL;
    }

    @Override
    public String getDataName() {
        return MODULE_WATER_ELECTROLYZER;
    }

    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal("module.waterElectrolyzer.name");
    }

    @Override
    public String getDescription() {
        return StatCollector.translateToLocal("module.waterElectrolyzer.desc");
    }

    @Override
    public void onPlayerTickActive(EntityPlayer player, ItemStack item) {
        double energy = ElectricItemUtils.getPlayerEnergy(player);
        double energyConsumption = ModuleManager.computeModularProperty(item, WATERBREATHING_ENERGY_CONSUMPTION);
        if (energy > energyConsumption && player.getAir() < 10) {
            Musique.playerSound(player, SoundLoader.SOUND_ELECTROLYZER, 1.0f, 1.0f);
            ElectricItemUtils.drainPlayerEnergy(player, energyConsumption);
            player.setAir(300);
        }
    }

    @Override
    public void onPlayerTickInactive(EntityPlayer player, ItemStack item) {
            Musique.stopPlayerSound(player, SoundLoader.SOUND_ELECTROLYZER);
    }

    @Override
    public String getTextureFile() {
        // TODO Auto-generated method stub
        return "waterelectrolyzer";
    }

}
