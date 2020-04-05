package io.github.codetoil.litlaunch.api.arguments;

public class ArgumentParserInteger implements IArgumentParser<IArgumentValue<Integer>> {
    @Override
    public ArgumentValue<Integer> parse(String input) {
        if (input.startsWith("#")) {
            input = input.substring(1);
        } else {
            //FrontEnd.DO_THING_OBJECT().notifyUser("[" + LaunchCommon.NAME + " v" + LaunchCommon.VERSION + "] Warning: Please precede all integers with a `#` sign to make sure it registers as an integer", IDoThing.Color.YELLOW);
        }
        //FrontEnd.verbose("input: " + input);
        Integer int_ = Integer.parseInt(input);
        //FrontEnd.verbose("int_: " + input);
        ArgumentValue<Integer> Int_ = new ArgumentValue<>(int_, this);
        //FrontEnd.verbose("Int_:" + Int_);
        //FrontEnd.verbose("Int_.value:" + Int_.value);
        return Int_;
    }

    @Override
    public boolean isNumber() {
        return true;
    }

    @Override
    public Class<?> getClassOfT() {
        return Integer.class;
    }
}