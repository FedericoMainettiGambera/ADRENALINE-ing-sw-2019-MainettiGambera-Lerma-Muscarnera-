package it.polimi.se2019.view.outputHandler;
/**this class handles all of the output interface matters
 * @author LudoLerma
 * @author FedericoMainettiGambera*/
public class OutputHandlerGate{
    /**instance a new GUIOutputHandler*/
    private static it.polimi.se2019.view.outputHandler.GUIOutputHandler GUIOutputHandler = new GUIOutputHandler();
    /**instance a new CLI outputHandler*/
    private static it.polimi.se2019.view.outputHandler.CLIOutputHandler CLIOutputHandler = new CLIOutputHandler();
    /**contains the user interface*/
    private static String UserIterface;
    /**@param userIterface set the userInterface*/
    public static void setUserIterface(String userIterface) {
        UserIterface = userIterface;
    }
    /**@return UserInterface*/
    public static String getUserIterface() {
        return UserIterface;
    }
    /**@return the cliOutPutHandler*/
    public static it.polimi.se2019.view.outputHandler.CLIOutputHandler getCLIOutputHandler() {
        return CLIOutputHandler;
    }
    /**@deprecated */
    public static it.polimi.se2019.view.outputHandler.GUIOutputHandler getGUIOutputHandler() {
        return GUIOutputHandler;
    }
    /**@param CLIOutputHandler to set CLIOutputHandler*/
    public static void setCLIOutputHandler(it.polimi.se2019.view.outputHandler.CLIOutputHandler CLIOutputHandler) {
        OutputHandlerGate.CLIOutputHandler = CLIOutputHandler;
    }
    /**@param GUIOutputHandler to set GUIOutputHandler */
    public static void setGUIOutputHandler(it.polimi.se2019.view.outputHandler.GUIOutputHandler GUIOutputHandler) {
        OutputHandlerGate.GUIOutputHandler = GUIOutputHandler;
    }
    /**@return the correct outputHandler basing on
     * @param userIterface CLI or GUI*/
    public static OutputHandlerInterface getCorrectOutputHandler(String userIterface){
        if(userIterface.equalsIgnoreCase("CLI")){
            return CLIOutputHandler;
        }
        else{
            return GUIOutputHandler;
        }
    }
}
