package it.polimi.se2019.networkHandler.RMI;

import java.io.Serializable;

    public class Message implements Serializable {

        String string;

        public String getString() {
            return string;
        }

        public void setString(String stringa){
            this.string=stringa;
        }
    }



