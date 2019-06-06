package it.polimi.se2019.view.components;

public class ViewModelGate {

    private static GameV model;

    private static String me;

    public static void setMe(String me) {
        ViewModelGate.me = me;
    }

    public static String getMe() {
        return me;
    }

    public static GameV getModel() {
        System.out.println("\n\n--------------------------------------------------------------------------------------------------------------------DEBUG");
        if(model.getPlayers()!= null && model.getPlayers().getPlayers()!=null) {
            for (PlayerV p : model.getPlayers().getPlayers()) {
                System.out.println(p.getNickname());
            }
        }
        System.out.println("--------------------------------------------------------------------------------------------------------------------DEBUG\n\n");
        return model;
    }

    public static void setModel(GameV model) {
        ViewModelGate.model = model;
    }
}
