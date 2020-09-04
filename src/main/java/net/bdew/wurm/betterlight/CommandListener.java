package net.bdew.wurm.betterlight;

import com.wurmonline.client.renderer.PlayerBodyRenderable;
import com.wurmonline.client.renderer.cell.CampFireEffect;
import com.wurmonline.client.renderer.cell.CellRenderable;
import com.wurmonline.client.renderer.cell.LightAttached;
import com.wurmonline.client.renderer.effects.FireEffect;
import com.wurmonline.client.renderer.effects.TorchFlame;
import com.wurmonline.client.renderer.light.LightReference;
import com.wurmonline.client.renderer.light.MasterLightManager;
import org.gotti.wurmunlimited.modloader.ReflectionUtil;
import org.gotti.wurmunlimited.modsupport.console.ConsoleListener;

public class CommandListener implements ConsoleListener {
    @Override
    public boolean handleInput(String cmd, Boolean silent) {
        if (cmd.startsWith("lights")) {
            if (cmd.equals("lights dump")) {
                BetterLightMod.hud.consoleOutput("====== LIGHTS ======");
                MasterLightManager manager = BetterLightMod.hud.getWorld().getLightManager(BetterLightMod.hud.getWorld().getPlayer().getLayer());
                for (LightReference ref : manager.getLightSources()) {
                    if (ref == null || ref.source == null) continue;
                    try {
                        String name = ref.source.getClass().getSimpleName();
                        if (ref.source instanceof LightAttached) {
                            LightAttached source = (LightAttached) ref.source;
                            CellRenderable renderable = ReflectionUtil.getPrivateField(source, ReflectionUtil.getField(LightAttached.class, "renderable"));
                            if (renderable instanceof PlayerBodyRenderable)
                                name = "Player Body";
                            else
                                name = String.format("[%d %s] %s", renderable.getId(), ref.source.getClass().getSimpleName(), renderable.getHoverName());
                        } else if (ref.source instanceof TorchFlame) {
                            TorchFlame source = (TorchFlame) ref.source;
                            CellRenderable renderable = ReflectionUtil.getPrivateField(source, ReflectionUtil.getField(TorchFlame.class, "model"));
                            name = String.format("[%d %s] %s", renderable.getId(), ref.source.getClass().getSimpleName(), renderable.getHoverName());
                        } else if (ref.source instanceof CampFireEffect) {
                            FireEffect source = (CampFireEffect) ref.source;
                            CellRenderable renderable = ReflectionUtil.getPrivateField(source, ReflectionUtil.getField(CampFireEffect.class, "renderable"));
                            name = String.format("[%d %s] %s", renderable.getId(), ref.source.getClass().getSimpleName(), renderable.getHoverName());
                        } else if (ref.source instanceof FireEffect) {
                            FireEffect source = (FireEffect) ref.source;
                            CellRenderable renderable = ReflectionUtil.getPrivateField(source, ReflectionUtil.getField(FireEffect.class, "cellRenderable"));
                            name = String.format("[%d %s] %s", renderable.getId(), ref.source.getClass().getSimpleName(), renderable.getHoverName());
                        }

                        BetterLightMod.hud.consoleOutput(String.format("%s - Modifier=%d RGBA=(%f %f %f %f) SZ=%f", name, ref.source.getLightModifier(1), ref.source.getLightColor(1).r, ref.source.getLightColor(1).g, ref.source.getLightColor(1).b, ref.source.getLightColor(1).a, ref.source.getLightSize(1)));
                    } catch (Exception e) {
                        BetterLightMod.logException("Error dumping lights", e);
                    }
                }
            } else if (cmd.equals("lights config")) {
                Config.dump();
            } else if (cmd.startsWith("lights set")) {
                String[] split = cmd.split(" ");
                if (split.length == 4) {
                    if (!Config.set(split[2], split[3]))
                        BetterLightMod.hud.consoleOutput("Unknown config variable: " + split[2]);
                } else {
                    BetterLightMod.hud.consoleOutput("Usage: lights set <variable> <value>");
                }
            } else {
                BetterLightMod.hud.consoleOutput("Commands:");
                BetterLightMod.hud.consoleOutput(" lights dump - print all lights present in game");
                BetterLightMod.hud.consoleOutput(" lights config - print current config");
                BetterLightMod.hud.consoleOutput(" lights set <variable> <value> - change config on the fly");
            }
            return true;
        }
        return false;
    }
}
