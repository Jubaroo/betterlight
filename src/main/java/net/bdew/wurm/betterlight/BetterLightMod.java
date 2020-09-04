package net.bdew.wurm.betterlight;

import com.wurmonline.client.renderer.gui.HeadsUpDisplay;
import javassist.ClassPool;
import javassist.CtClass;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;
import org.gotti.wurmunlimited.modloader.interfaces.Configurable;
import org.gotti.wurmunlimited.modloader.interfaces.Initable;
import org.gotti.wurmunlimited.modloader.interfaces.WurmClientMod;
import org.gotti.wurmunlimited.modsupport.console.ModConsole;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BetterLightMod implements WurmClientMod, Initable, Configurable {
    private static final Logger logger = Logger.getLogger("BetterLightMod");

    public static HeadsUpDisplay hud;

    public static void logInfo(String msg) {
        if (logger != null)
            logger.log(Level.INFO, msg);
    }

    public static void logWarning(String msg) {
        if (logger != null)
            logger.log(Level.WARNING, msg);
    }

    public static void logException(String msg, Throwable e) {
        if (logger != null)
            logger.log(Level.SEVERE, msg, e);
    }

    @Override
    public void configure(Properties properties) {
        for (String var : properties.stringPropertyNames()) {
            Config.set(var, properties.getProperty(var));
        }
    }

    @Override
    public void init() {
        try {
            ClassPool classPool = HookManager.getInstance().getClassPool();

            CtClass ctLightAttached = classPool.getCtClass("com.wurmonline.client.renderer.cell.LightAttached");
            CtClass ctTorchFlame = classPool.getCtClass("com.wurmonline.client.renderer.effects.TorchFlame");
            CtClass ctFireEffect = classPool.getCtClass("com.wurmonline.client.renderer.effects.FireEffect");

            ctLightAttached.getMethod("getLightModifier", "(F)I")
                    .insertAfter("if (net.bdew.wurm.betterlight.Config.enabled) {" +
                            " if (renderable.getId()==0)" +
                            "   $_ = $_ * net.bdew.wurm.betterlight.Config.playerScale + net.bdew.wurm.betterlight.Config.playerAdd;" +
                            " else " +
                            "   $_ = $_ * net.bdew.wurm.betterlight.Config.attachedScale + net.bdew.wurm.betterlight.Config.attachedAdd;" +
                            "}");

            ctLightAttached.getMethod("getLightColor", "(F)Lcom/wurmonline/client/renderer/Color;")
                    .insertAfter("if (net.bdew.wurm.betterlight.Config.enabled) {" +
                            " float tmp = java.lang.Math.max(java.lang.Math.max($_.r,$_.g),$_.b);" +
                            " if (renderable.getId()==0) {" +
                            "   if (tmp < net.bdew.wurm.betterlight.Config.playerNormalize) {" +
                            "     tmp = net.bdew.wurm.betterlight.Config.playerNormalize / tmp;" +
                            "     $_.r = $_.r * tmp;" +
                            "     $_.g = $_.g * tmp;" +
                            "     $_.b = $_.b * tmp;" +
                            "   }" +
                            " }  else {" +
                            "   if (tmp < net.bdew.wurm.betterlight.Config.attachedNormalize) {" +
                            "     tmp = net.bdew.wurm.betterlight.Config.attachedNormalize / tmp;" +
                            "     $_.r = $_.r * tmp;" +
                            "     $_.g = $_.g * tmp;" +
                            "     $_.b = $_.b * tmp;" +
                            "   }" +
                            " }" +
                            "}");


            ctTorchFlame.getMethod("getLightModifier", "(F)I")
                    .insertAfter("if (net.bdew.wurm.betterlight.Config.enabled) {" +
                            "   $_ = $_ * net.bdew.wurm.betterlight.Config.torchScale + net.bdew.wurm.betterlight.Config.torchAdd;" +
                            "}");

            ctTorchFlame.getMethod("getLightColor", "(F)Lcom/wurmonline/client/renderer/Color;")
                    .insertAfter("if (net.bdew.wurm.betterlight.Config.enabled) {" +
                            " float tmp = java.lang.Math.max(java.lang.Math.max($_.r,$_.g),$_.b);" +
                            " if (tmp < net.bdew.wurm.betterlight.Config.torchNormalize) {" +
                            "   tmp = net.bdew.wurm.betterlight.Config.torchNormalize / tmp;" +
                            "   $_.r = $_.r * tmp;" +
                            "   $_.g = $_.g * tmp;" +
                            "   $_.b = $_.b * tmp;" +
                            " }" +
                            "}");

            ctFireEffect.getMethod("getLightModifier", "(F)I")
                    .insertAfter("if (net.bdew.wurm.betterlight.Config.enabled) {" +
                            "   $_ = $_ * net.bdew.wurm.betterlight.Config.fireScale + net.bdew.wurm.betterlight.Config.fireAdd;" +
                            "}");

            ctFireEffect.getMethod("getLightColor", "(F)Lcom/wurmonline/client/renderer/Color;")
                    .insertAfter("if (net.bdew.wurm.betterlight.Config.enabled) {" +
                            " float tmp = java.lang.Math.max(java.lang.Math.max($_.r,$_.g),$_.b);" +
                            " if (tmp < net.bdew.wurm.betterlight.Config.fireNormalize) {" +
                            "   tmp = net.bdew.wurm.betterlight.Config.fireNormalize / tmp;" +
                            "   $_.r = $_.r * tmp;" +
                            "   $_.g = $_.g * tmp;" +
                            "   $_.b = $_.b * tmp;" +
                            " }" +
                            "}");

            HookManager.getInstance().registerHook("com.wurmonline.client.renderer.gui.HeadsUpDisplay", "init", "(II)V", () -> (proxy, method, args) -> {
                method.invoke(proxy, args);
                hud = (HeadsUpDisplay) proxy;
                return null;
            });

            ModConsole.addConsoleListener(new CommandListener());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
