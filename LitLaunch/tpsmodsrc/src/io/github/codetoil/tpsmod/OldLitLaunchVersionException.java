package io.github.codetoil.tpsmod;

public class OldLitLaunchVersionException extends RuntimeException {
    public OldLitLaunchVersionException()
    {
        super("LitLaunch is incompatible with this version of tpsmod, please update to 0.0.4.0!");
    }
}
