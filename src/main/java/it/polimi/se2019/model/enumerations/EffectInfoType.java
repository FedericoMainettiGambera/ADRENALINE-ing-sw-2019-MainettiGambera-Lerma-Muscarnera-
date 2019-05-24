package it.polimi.se2019.model.enumerations;

public enum EffectInfoType {



        /*      SECTION                             DESCRIPTION                                                        NUMBER OF DIRECT INPUTS FROM THE USER*/
        //      TARGET SELECTION
                    singleTarget,                   // select a target                                                 1
                    multipleTargets,                // select a targetList of n targets                                n
                    targetListBySquare,             // select a targetlist of n targets by the square                  1
                    targetListByRoom,               // select a targetlist of n targets by the room                    1
                    targetListBySameSquareOfPlayer, // select a targetlist of n targets on the square of the player    0
                    targetListByCardinalDirection,   // select a targetlist of n targets
        //      SQUARE SELECTION
                    simpleSquareSelect,             // select a square by id                                           1
                    multipleSquareSelect,           // select n squares by id                                          n
                    squareByTarget,                 // select a square by one of the player on it                      1
                    playerSquare,                   // square of current player                                        0
        //      ROOM SELECTION

}
