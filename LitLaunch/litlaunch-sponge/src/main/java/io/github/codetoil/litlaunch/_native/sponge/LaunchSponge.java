package io.github.codetoil.litlaunch._native.sponge;

import com.google.common.collect.Lists;
import io.github.codetoil.litlaunch._native.sponge.proxy.ServerProxySponge;
import io.github.codetoil.litlaunch.api.Command;
import io.github.codetoil.litlaunch.api.FrontEnd;
import io.github.codetoil.litlaunch.core.ILaunch;
import io.github.codetoil.litlaunch.core.LaunchCommon;
import io.github.codetoil.litlaunch.core.event.LitEvent;
import io.github.codetoil.litlaunch.core.event.LitEventHandler;
import io.github.codetoil.litlaunch.core.exceptions.FailedBootstrapException;
import io.github.codetoil.litlaunch.modloader.ModFinder;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePostInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.Task;

import java.util.List;

@Plugin(
        id = "litlaunch",
        name = "LitLaunch",
        description = "temp description",
        url = "https://github.com/Codetoil/TPS-Mod",
        authors = {
                "Codetoil"
        }
)
public class LaunchSponge implements ILaunch {
    public static final String VERSION = GetFields.INSTANCE.getVERSION() + "-" + LaunchCommon.VERSION;
    private Task onTick;

    public LaunchSponge() throws Throwable {
        if (Sponge.getGame().getPlatform().getExecutionType().isClient()) {
            LaunchCommon.setSide(Command.Side.CLIENT);
        } else if (Sponge.getGame().getPlatform().getExecutionType().isServer()) {
            LaunchCommon.setSide(Command.Side.SERVER);
        } else {
            throw new FailedBootstrapException("FML IS NOT SIDED!");
        }
        LaunchCommon.setGamePath(Sponge.getGame().getGameDirectory());
        LaunchCommon.setDoThing(DoThing.INSTANCE);
        LaunchCommon.setGetFields(GetFields.INSTANCE);
        try {
            LaunchCommon.bootstrap(LoggerFactory.getLogger(LaunchCommon.MODID), this, LoggerSponge.getInstance());
        } catch (Throwable t) {
            FailedBootstrapException lFailedBootstrapException = new FailedBootstrapException();
            lFailedBootstrapException.initCause(t);
            throw lFailedBootstrapException;
        }
        //FMLJavaModLoadingContext.get().getModEventBus().addListener(this::serverLoad);
    }

    @Listener
    public void onPreInit(GamePreInitializationEvent event) {
        LaunchCommon.preInit();
        LaunchCommon.getCcproxy().preInit();
    }

    @Listener
    public void onInit(GameInitializationEvent event) {
        LaunchCommon.init();
        LaunchCommon.getCcproxy().init();
    }

    @Listener
    public void onPostInit(GamePostInitializationEvent event) {
        LaunchCommon.postInit();
        LaunchCommon.getCcproxy().postInit();
    }

    @Listener
    public void onServerLoad(GameStartedServerEvent event) {
        LaunchCommon.serverLoad();
        LaunchCommon.getCcproxy().serverLoad();
        onTick = Sponge.getScheduler().createTaskBuilder().intervalTicks(1).execute(() -> {
            LitEventHandler.COMMON.post(new LitEvent(this, LitEvent.TYPE.SERVERTICK), true);
        }).name("onTick").submit(this);
        addModCommands();
    }

    private void addModCommands() {
        ModFinder.validMods.forEach((modClass) -> {
            try {
                Object oCommands = modClass.getField("commandList").get(null);
                List lCommands;
                if (oCommands instanceof List) {
                    lCommands = (List) oCommands;
                    lCommands.forEach((command) -> {
                        if (command instanceof Command) {
                            if (Command.Side.SERVER.equals(((Command) command).side) || Command.Side.BOTH.equals(((Command) command).side)) {
                            }
                            {
                                CommandCallable callable = CommandBuilder.getCommand((Command) command);
                                Sponge.getCommandManager().register(this, callable, Lists.newArrayList(((Command) command).name));
                            }
                        }
                    });
                } else {
                    FrontEnd.error("Mod " + modClass + " does not have a method named \"commandList\". This is neccesary for the api to work though. Skipping!");
                }

            } catch (Throwable pThrowable) {
                pThrowable.printStackTrace();
            }

        });
    }

    public boolean setProxy() {
        boolean result;
        if (LaunchCommon.getCcproxy() != null) {
            FrontEnd.error("Tried re-setting proxy!");
            result = false;
        } else {
            Command.Side side = LaunchCommon.getSide();
            switch (side) {
                case CLIENT:
                    FrontEnd.error("This should not be running on a client!");
                    result = false;
                    break;
                case SERVER:
                    LaunchCommon.setCcproxy(new ServerProxySponge());
                    result = true;
                    break;
                default:
                    FrontEnd.error("Sponge is not sided. This should not happen!");
                    result = false;
                    break;
            }
        }
        return result;
    }


}
