/*
 * Copyright Codetoil (c) 2019
 */

package io.github.codetoil.tpsmod;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.github.codetoil.litlaunch.api.Command;
import io.github.codetoil.litlaunch.api.EnumRequireOrNot;
import io.github.codetoil.litlaunch.api.FrontEnd;
import io.github.codetoil.litlaunch.api.IMod;
import io.github.codetoil.litlaunch.api.arguments.ArgumentParserInteger;
import io.github.codetoil.litlaunch.api.arguments.ArgumentWrapper;
import io.github.codetoil.litlaunch.core.LaunchCommon;
import io.github.codetoil.litlaunch.core.event.LitEvent;
import io.github.codetoil.litlaunch.core.event.LitEventHandler;
import io.github.codetoil.tpsmod.commands.CommandHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TPSMod implements IMod, LitEventHandler.EventListener {
    public static final String MODID = "tpsmod";
    public static final String VERSION = "2.0.1.3.0";

    public final static LitEventHandler EVENTS = new LitEventHandler("TPSMod");
    public final static Object INSTANCE = new TPSMod();
    public final static LitEvent.TYPE updateTPS = LitEvent.TYPE.getEnumFromString("updateTPS");
    public static double initialLoadTime;
    public final static List<Command> commandList = newCommandList();
    private static MeasureTPSdrop[] independentDimensionTPSMeasures;

    static {
        if (!FrontEnd.LITLAUNCH_VERSION.contains("0.0.4"))
        {
            throw new OldLitLaunchVersionException();
        }
        if (LaunchCommon.getSide() == null) {
            FrontEnd.error("wtf... LitLaunch is not sided... uh...");
        }
        if (LaunchCommon.isVerbose()) {
            try {
                new EventPrinter();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static MeasureTPSdrop[] getIndependentDimensionTPSMeasures() {
        return independentDimensionTPSMeasures;
    }

    private static List<Command> newCommandList() {
        List<Command> internalCommandList = new ArrayList<>();
        try {
            BiMap<String, ArgumentWrapper<?>> args = HashBiMap.create();
            args.put("dimension", new ArgumentWrapper<>("dimension", new ArgumentParserInteger(), "Dimension to measure the tps of", LaunchCommon.getSide() == Command.Side.SERVER));
            internalCommandList.add(new Command("/tps", CommandHandler::executeTPS, args, Command.Side.CLIENT));
            internalCommandList.add(new Command("/tpstoall", CommandHandler::executeTPSTOALL, args, Command.Side.CLIENT));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return internalCommandList;
    }

    @Override
    public void receivedEvent(LitEvent event, LitEventHandler handler) {
        try {
            switch (event.getType().getName()) {
                case "Construction":
                    construction();
                    break;
                case "PreInit":
                    preInit();
                    break;
                case "Init":
                    init();
                    break;
                case "PostInit":
                    postInit();
                    break;
                case "ServerLoad":
                    serverLoad();
                    break;
                case "ServerConnect":
                    connectedToServer();
                    break;
                default:
                    break;
            }
        } catch (IllegalArgumentException e) {
            //LaunchMods.debug("Unknown event " + event.toString());
        }
    }

    public void connectedToServer() {
        FrontEnd.info("TPSMod v" + VERSION + " connected to server");
        TPSMod.initialLoadTime = LaunchCommon.getTimeInSeconds();
    }

    @Override
    public LitEventHandler.EventListener getListener() {
        return (LitEventHandler.EventListener) INSTANCE;
    }

    @Override
    public EnumRequireOrNot onClient() {
        return EnumRequireOrNot.COMPATIBLE;
    }

    @Override
    public EnumRequireOrNot onServer() {
        return EnumRequireOrNot.INCOMPATIBLE;
    }

    @Override
    public String getModID() {
        return MODID;
    }

    @Override
    public String getVersion() {
        return VERSION;
    }

    @Override
    public List<Command> getCommandList() {
        return commandList;
    }

    public void construction() {
        FrontEnd.info("TPSMod v" + VERSION + " constructing");
    }

    public void preInit() {

        FrontEnd.info("TPSMod v" + VERSION + " preinitializing");
    }

    public void init() {
        FrontEnd.info("TPSMod v" + VERSION + " initializing");
    }

    public void postInit() {
        FrontEnd.info("TPSMod v" + VERSION + " postinitializing");
        int dimensionAmount = FrontEnd.getFields().getDimsAvailable().length;
        FrontEnd.info("Dimensions Amount: " + dimensionAmount);
        FrontEnd.info("Dimensions Available: " + Arrays.toString(FrontEnd.getFields().getDimsAvailable()));
        TPSMod.independentDimensionTPSMeasures = new MeasureTPSdrop[dimensionAmount];
        for (int i = 0; i < dimensionAmount; i++) {
            MeasureTPSdrop temp = new MeasureTPSdrop(FrontEnd.getFields().getDimsAvailable()[i]);
            TPSMod.independentDimensionTPSMeasures[i] = temp;
        }
    }

    public void serverLoad() {
        FrontEnd.info("TPSMod v" + VERSION + " starting server");
    }

    @Override
    public IMod getModINSTANCE() {
        return (IMod) INSTANCE;
    }
}
