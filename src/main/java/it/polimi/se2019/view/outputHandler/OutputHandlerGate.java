package it.polimi.se2019.view.outputHandler;

public class OutputHandlerGate{

    private static it.polimi.se2019.view.outputHandler.GUIOutputHandler GUIOutputHandler = new GUIOutputHandler();

    private static it.polimi.se2019.view.outputHandler.CLIOutputHandler CLIOutputHandler = new CLIOutputHandler();

    private static String UserIterface;

    public static void setUserIterface(String userIterface) {
        UserIterface = userIterface;
    }

    public static String getUserIterface() {
        return UserIterface;
    }

    public static it.polimi.se2019.view.outputHandler.CLIOutputHandler getCLIOutputHandler() {
        return CLIOutputHandler;
    }

    public static it.polimi.se2019.view.outputHandler.GUIOutputHandler getGUIOutputHandler() {
        return GUIOutputHandler;
    }

    public static void setCLIOutputHandler(it.polimi.se2019.view.outputHandler.CLIOutputHandler CLIOutputHandler) {
        OutputHandlerGate.CLIOutputHandler = CLIOutputHandler;
    }

    public static void setGUIOutputHandler(it.polimi.se2019.view.outputHandler.GUIOutputHandler GUIOutputHandler) {
        OutputHandlerGate.GUIOutputHandler = GUIOutputHandler;
    }
}
