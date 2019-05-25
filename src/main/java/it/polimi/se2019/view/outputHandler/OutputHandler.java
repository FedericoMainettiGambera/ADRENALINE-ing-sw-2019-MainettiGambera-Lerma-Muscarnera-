package it.polimi.se2019.view.outputHandler;

public class OutputHandler {

    private it.polimi.se2019.view.outputHandler.GUIOutputHandler GUIOutputHandler = new GUIOutputHandler();

    private it.polimi.se2019.view.outputHandler.CLIOutputHandler CLIOutputHandler = new CLIOutputHandler();

    public void ShowCLIMessage(String message){
        //  if(il client è in modalità CLI){
                CLIOutputHandler.showMessage(message);
        //  }
    }
}
