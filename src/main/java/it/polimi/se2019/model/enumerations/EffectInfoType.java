package it.polimi.se2019.model.enumerations;

public enum EffectInfoType {



        /*      SECTION                             DESCRIPTION                                                        NUMBER OF DIRECT INPUTS FROM THE USER*/
        //      TARGET SELECTION
                    none,
                    player,                         // select player                                                   0
                    singleTarget,                   // select a target                                                 1
                    twoTargets,                     // select two targets max                                          1<=x<=2
                    threeTargets,                   // select three targets max                                        1<=x<=3
                    targetListBySquareOfLastTarget,
                    multipleTargets,                // select a targetList of n targets                                n
                    singleTargetBySquare,           // select a target froma  square
                    targetListBySquare,             // select a targetlist of n targets by the square                  1
                    targetListByRoom,               // select a targetlist of n targets by the room                    1
                    targetListBySameSquareOfPlayer, // select a targetlist of n targets on the square of the player    0
                    singleTargetBySameSquareOfPlayer,
                    targetBySameSquareOfPlayer,     // select a target the square of the player                        0
                    singleTargetByCardinalDirection,   // select a targetlist of n targets
                    twoTargetsByCardinalDirection,
                    targetListByCardinalDirection,   // select a targetlist of n targets
                    targetListByLastTargetSelectedSquare,   // select a targetlist from last square selected           0
                    targetListByDistance1,           // select all the player distant one
        //      SQUARE SELECTION
                    squareOfLastTargetSelected,     // select the square by the last player attacked                   0
                    simpleSquareSelect,             // select a square by id                                           1
                    multipleSquareSelect,           // select n squares by id                                          n
                    squareByTarget,                 // select a square by one of the player on it                      1
                    squareByLastTargetSelected,                 // select a square by one of the player on it          1
                    playerSquare,                   // square of current player                                        0
        //      ROOM SELECTION
                    singleRoom                     // the room is not yours                                           0
}
