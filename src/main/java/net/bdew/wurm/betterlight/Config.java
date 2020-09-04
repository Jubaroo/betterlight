package net.bdew.wurm.betterlight;

public class Config {
    public static boolean enabled = false;
    public static float attachedScale = 1, attachedAdd = 0, attachedNormalize = -1;
    public static float torchScale = 1, torchAdd = 0, torchNormalize = -1;
    public static float playerScale = 1, playerAdd = 0, playerNormalize = -1;
    public static float fireScale = 1, fireAdd = 0, fireNormalize = -1;

    public static void dump() {
        BetterLightMod.hud.consoleOutput(String.format("enabled=%s", enabled));
        BetterLightMod.hud.consoleOutput(String.format("attachedScale=%f", attachedScale));
        BetterLightMod.hud.consoleOutput(String.format("attachedAdd=%f", attachedAdd));
        BetterLightMod.hud.consoleOutput(String.format("attachedNormalize=%f", attachedNormalize));
        BetterLightMod.hud.consoleOutput(String.format("torchScale=%f", torchScale));
        BetterLightMod.hud.consoleOutput(String.format("torchAdd=%f", torchAdd));
        BetterLightMod.hud.consoleOutput(String.format("torchNormalize=%f", torchNormalize));
        BetterLightMod.hud.consoleOutput(String.format("playerScale=%f", playerScale));
        BetterLightMod.hud.consoleOutput(String.format("playerAdd=%f", playerAdd));
        BetterLightMod.hud.consoleOutput(String.format("playerNormalize=%f", playerNormalize));
        BetterLightMod.hud.consoleOutput(String.format("fireScale=%f", fireScale));
        BetterLightMod.hud.consoleOutput(String.format("fireAdd=%f", fireAdd));
        BetterLightMod.hud.consoleOutput(String.format("fireNormalize=%f", fireNormalize));
    }

    public static boolean set(String var, String val) {
        switch (var) {
            case "attachedScale":
                attachedScale = Float.parseFloat(val);
                BetterLightMod.logInfo(String.format("attachedScale=%f", attachedScale));
                return true;
            case "attachedAdd":
                attachedAdd = Float.parseFloat(val);
                BetterLightMod.logInfo(String.format("attachedAdd=%f", attachedAdd));
                return true;
            case "attachedNormalize":
                attachedNormalize = Float.parseFloat(val);
                BetterLightMod.logInfo(String.format("attachedNormalize=%f", attachedNormalize));
                return true;
            case "torchScale":
                torchScale = Float.parseFloat(val);
                BetterLightMod.logInfo(String.format("torchScale=%f", torchScale));
                return true;
            case "torchAdd":
                torchAdd = Float.parseFloat(val);
                BetterLightMod.logInfo(String.format("torchAdd=%f", torchAdd));
                return true;
            case "torchNormalize":
                torchNormalize = Float.parseFloat(val);
                BetterLightMod.logInfo(String.format("torchNormalize=%f", torchNormalize));
                return true;
            case "playerScale":
                playerScale = Float.parseFloat(val);
                BetterLightMod.logInfo(String.format("playerScale=%f", playerScale));
                return true;
            case "playerAdd":
                playerAdd = Float.parseFloat(val);
                BetterLightMod.logInfo(String.format("playerAdd=%f", playerAdd));
                return true;
            case "playerNormalize":
                playerNormalize = Float.parseFloat(val);
                BetterLightMod.logInfo(String.format("playerNormalize=%f", playerNormalize));
                return true;
            case "fireScale":
                fireScale = Float.parseFloat(val);
                BetterLightMod.logInfo(String.format("fireScale=%f", fireScale));
                return true;
            case "fireAdd":
                fireAdd = Float.parseFloat(val);
                BetterLightMod.logInfo(String.format("fireAdd=%f", fireAdd));
                return true;
            case "fireNormalize":
                fireNormalize = Float.parseFloat(val);
                BetterLightMod.logInfo(String.format("fireNormalize=%f", fireNormalize));
                return true;
            case "enabled":
                enabled = Boolean.parseBoolean(val);
                BetterLightMod.logInfo(String.format("enabled=%s", enabled));
                return true;
            default:
                return false;
        }
    }
}
