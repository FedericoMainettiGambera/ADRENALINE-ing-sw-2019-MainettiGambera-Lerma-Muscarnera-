package it.polimi.se2019.virtualView.RMI;

import java.io.Serializable;

public class NumberOfConnection implements Serializable {

    int number;

   public NumberOfConnection(){
       this.number=0;
   }
   public int getNumber(){
       return number;
   }
   public void addNumber(){
      number++;   }
   public void lessNumber(){
       number--;
}
}
