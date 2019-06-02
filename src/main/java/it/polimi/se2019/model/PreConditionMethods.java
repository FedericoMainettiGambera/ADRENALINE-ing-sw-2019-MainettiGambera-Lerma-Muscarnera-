package it.polimi.se2019.model;

import it.polimi.se2019.model.enumerations.SquareSide;
import it.polimi.se2019.model.enumerations.SquareTypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PreConditionMethods implements Serializable {

    /**EFFECT CHOOSE PRE CONDITION*/
    /*TODO gestione dall'input*/
    boolean isReaperMode(ActionDetails actionDetails, ActionContext actionContext) {return true;}
    boolean isFocusShot(ActionDetails actionDetails, ActionContext actionContext) {return true;}
    boolean isTurretTripod(ActionDetails actionDetails, ActionContext actionContext) {return true;}
    boolean isNanoTracerMode(ActionDetails actionDetails, ActionContext actionContext) {return true;}
    boolean withChainReaction(ActionDetails actionDetails, ActionContext actionContext) {return true;}
    boolean withHighVoltage(ActionDetails actionDetails, ActionContext actionContext) {return true;}
    boolean isBlackHole(ActionDetails actionDetails, ActionContext actionContext) {return true;}
    boolean isLongBarrelMode(ActionDetails actionDetails, ActionContext actionContext) {return true;}
    boolean withRocketJump(ActionDetails actionDetails, ActionContext actionContext) {return true;}
    boolean withFragmentingWarHead(ActionDetails actionDetails, ActionContext actionContext) {return true;}
    boolean inRocketFistMode(ActionDetails actionDetails, ActionContext actionContext) {return true;}
    boolean withShadowStep(ActionDetails actionDetails, ActionContext actionContext) {return true;}
    boolean isPolverizeMode(ActionDetails actionDetails, ActionContext actionContext) {return true;}

    /*EFFECT - ORDER */

    public boolean notFirstExecuted(ActionDetails actionDetails, ActionContext actionContext) {         // l'effetto che continee l'azione
                                                                                                        // che contiene questa precondizione
        int counter = 0;
        for(PlayerHistoryElement p: actionContext.getPlayer().getPlayerHistory().historyElementList) {
            if(!p.getContextCard().equals(actionContext.getPlayer().getPlayerHistory().getLast().getContextCard())) {
                break;
            }

            System.out.println(p.getContextEffect().toString()  + " in " + p.getContextCard().toString() + " with input " + p.getInput());
            counter++;
        }                                                                                                // non può essere eseguito per primo
        if(counter <= 1) return false;
        return true;
    }


    /*CONTEXT - INPUT PRECODITION*/


    public boolean validPayment(ActionDetails actionDetails, ActionContext actionContext) {
        Player player = actionContext.getPlayer();
        AmmoCubes ammoCost = actionDetails.getFileSelectedActionDetails().getAmmoCubesCost();
        if(player.getPlayerBoard().canPayAmmoCubes(ammoCost.getColor(), ammoCost.getQuantity() )) {

            return true;

        }

        return false;

    }
    public boolean alwaysExceptional(ActionDetails actionDetails, ActionContext actionContext) throws Exception {
        throw new Exception("");
    }

    public boolean alwaysFalse(ActionDetails actionDetails, ActionContext actionContext) {
        return false;
    }
    public boolean alwaysTrue(ActionDetails actionDetails, ActionContext actionContext) {
        /*@*/ System.out.println("verifico alwaysTrue, sempre vera!" + actionContext.getPlayer().getNickname());
        return true;

    }


        public boolean sameSquareTarget(ActionDetails actionDetails, ActionContext actionContext) {
        actionDetails.getUserSelectedActionDetails().setTarget(null); // TODO : important info : this is not a proper precondition but a it forces the selection of the target to the players in the same square
        // TODO there is no connection between player and board so it cant select the players,
        // TODO also, implement the possibility of using more targets
        return true;

    }
    public boolean youCanSee(ActionDetails actionDetails, ActionContext actionContext) {
        System.out.println("\tverificando se sia visibile...");
        if(true)
        return true;
        int Tx = actionDetails.getUserSelectedActionDetails().getTarget().getPosition().getX();
        int Ty = actionDetails.getUserSelectedActionDetails().getTarget().getPosition().getY();

        int Px = actionContext.getPlayer().getPosition().getX();
        int Py = actionContext.getPlayer().getPosition().getY();

        if((Tx == Px) || (Py == Ty)) {          //visibile
            if((Tx == Px)) {                    //sono sulla stessa verticale
                if(Ty>Py) {

                } else {
                    if(Ty<Py) {
                        int i = Ty;
                        int j = Py;
                        boolean noWalls = true;
                        for(i = Ty;i < j;i++) {
                          //  if(actionContext.getBoard().getMap()[0][0].)
                        }
                        return noWalls;
                    } else {
                        return true;            // nella stessa cella
                    }
                }

            } else {
                int i = Py;
                int j = Ty;
                boolean noWalls = true;
                for(i = Py;i < j;i++) {
                    // TODO: insert board reference

                }
                return noWalls;
            }


        }


        return true;

    }
    public boolean distanceOfTargetFromPlayerSquareIs1(ActionDetails actionDetails,ActionContext actionContext) {
        Player target = actionDetails.getUserSelectedActionDetails().getTarget();
        Player user   = actionContext.getPlayer();
        int Distance = (target.getPosition().getX() - user.getPosition().getX()) * (target.getPosition().getX() - user.getPosition().getX() )
                + (target.getPosition().getY() - user.getPosition().getY()) * (target.getPosition().getY() - user.getPosition().getY());

        if(Distance==1) {
            return true;
        }
        return false;

    }
    public boolean distanceOfTargetFromPlayerSquareLessThan2Moves(ActionDetails actionDetails,ActionContext actionContext) {
        Player target = actionDetails.getUserSelectedActionDetails().getTarget();
        Player user   = actionContext.getPlayer();

        int Distance = (target.getPosition().getX() - user.getPosition().getX()) * (target.getPosition().getX() - user.getPosition().getX() )
        + (target.getPosition().getY() - user.getPosition().getY()) * (target.getPosition().getY() - user.getPosition().getY());
        System.out.println(Distance);
        if(Distance<=4) {
            return true;
        }
        return false;
    }
    public boolean distanceFromOriginalPositionLessThan2(ActionDetails actionDetails,ActionContext actionContext) {
    /*Target.square, ChosenSquare*/
        Position A = actionDetails.getUserSelectedActionDetails().getTarget().getPosition();
        Position B = actionDetails.getUserSelectedActionDetails().getChosenSquare().getCoordinates();

        int Distance = ( A.getX() - B.getX()) * ( A.getX() - B.getX()) +
                       ( A.getY() - B.getY()) * ( A.getY() - B.getY())  ;

        return (Distance <= 4);

    }
    public boolean moveOtherPlayer(ActionDetails actionDetails,ActionContext actionContext) {
        Player A = actionContext.getPlayer();
        Player B = actionDetails.getUserSelectedActionDetails().getTarget();
        return !A.equals(B);
    }
    public boolean atLeastOneMoveAway(ActionDetails actionDetails,ActionContext actionContext) {
        Player target = actionDetails.getUserSelectedActionDetails().getTarget();
        Player user   = actionContext.getPlayer();

        int Distance = (target.getPosition().getX() - user.getPosition().getX()) * (target.getPosition().getX() - user.getPosition().getX() )
                + (target.getPosition().getY() - user.getPosition().getY()) * (target.getPosition().getY() - user.getPosition().getY());
        System.out.println(Distance);
        if(Distance>=1) {
            return true;
        }
        return false;
    }
    public boolean notInYourRoom(ActionDetails actionDetails,ActionContext actionContext) {
        /*TODO: LE STANZE COME SONO GESTITE???*/
        return false;
    }
    public boolean distanceOfTargetFromPlayerExactlyOne(ActionDetails actionDetails,ActionContext actionContext) {
        return distanceOfTargetFromPlayerSquareIs1(actionDetails,actionContext); // alias function
    }
    public boolean distanceFromLastSelectedSquareExactlyOne(ActionDetails actionDetails,ActionContext actionContext) {
        /*square,square*/
        Square A = actionDetails.getUserSelectedActionDetails().getChosenSquare();

        for(int i = actionContext.getActionContextFilteredInputs().size()-2;i >= 0; i--) {

            System.out.println(">" + actionContext.getActionContextFilteredInputs().get(i).getType());
            if(actionContext.getActionContextFilteredInputs().get(i).getType().equals("Square")) {
                        Square B = (Square) actionContext.getActionContextFilteredInputs().get(i).getContent()[0];
                        int Distance = (A.getCoordinates().getX() -  B.getCoordinates().getX()) * (A.getCoordinates().getX() -  B.getCoordinates().getX()) +
                                (A.getCoordinates().getY() -  B.getCoordinates().getY()) * (A.getCoordinates().getY() -  B.getCoordinates().getY());
                        return (Distance == 1);
                }
            }
        return false;
    }
    public boolean previousPreviousTargetDifferent(ActionDetails actionDetails,ActionContext actionContext) {
        /*@*/ System.out.println("verifico notPreviousTarget" +  actionContext.getPlayer().toString() + ":" + actionContext.getActionContextFilteredInputs().size());

        boolean FLAG = false;
        for(int i = actionContext.getActionContextFilteredInputs().size()-2;i >= 0; i--) {

            System.out.println(">" + actionContext.getActionContextFilteredInputs().get(i).getType());
            if(actionContext.getActionContextFilteredInputs().get(i).getType().equals("Target")) {
                System.out.println(actionDetails.getUserSelectedActionDetails().getTarget().getNickname() +
                        " == " +  ((Player) actionContext.getActionContextFilteredInputs().get(i).getContent()[0]).getNickname() +"?" );
                if (actionDetails.getUserSelectedActionDetails().getTarget().equals(
                        actionContext.getActionContextFilteredInputs().get(i).getContent()[0]
                )
                ) {
                    if(FLAG) {

                    } else {
                        return false;
                    }
                    FLAG = true;
                }
            } else {
                if(FLAG) {
                    return true;
                }
            }
        }
        return true;

    }
    private List<Square> squareSelect(List<ActionContextFilteredInput> l) {
        List<Square> ret = new ArrayList<>();
        for(ActionContextFilteredInput a: l) {
            if(a.getType().equals("Square")) {
                ret.add((Square) a.getContent()[0]);
                System.out.println("#" + ret.get(ret.size()-1).getCoordinates().getX() + "," + ret.get(ret.size()-1).getCoordinates().getY()  );
            }
        }

        return ret;
    }

    public boolean sameCardinalDirectionOfPreviousSquare(ActionDetails actionDetails,ActionContext actionContext) {
        Square A = actionDetails.getUserSelectedActionDetails().getChosenSquare();
        Square B = new NormalSquare(1, 0, SquareSide.wall, SquareSide.wall, SquareSide.wall, SquareSide.wall, SquareTypes.normal, 'r');
        ;
        squareSelect(actionContext.getActionContextFilteredInputs());
        for (int i = actionContext.getActionContextFilteredInputs().size() - 2; i >= 0; i--) {
            System.out.println(">" + ((Square) actionContext.getActionContextFilteredInputs().get(i).getContent()[0]).getCoordinates().getX() +
                    "," + ((Square) actionContext.getActionContextFilteredInputs().get(i).getContent()[0]).getCoordinates().getY());

            if (actionContext.getActionContextFilteredInputs().get(i).getType().equals("Square")) {
                B = (Square) actionContext.getActionContextFilteredInputs().get(i).getContent()[0];
                Position A_coords = A.getCoordinates();
                Position B_coords = B.getCoordinates();
                Position P_coords = actionContext.getPlayer().getPosition();

                //  AB + BP = AP -- same cardinal point
                System.out.println("confronto...");

                System.out.println("A " + A.getCoordinates().getX() + "," + A.getCoordinates().getY());
                System.out.println("B "+ B.getCoordinates().getX() + "," + B.getCoordinates().getY());
                System.out.println("P " + P_coords.getX() + "," + P_coords.getY());
                boolean val1 = (A_coords.getX() == B_coords.getX()) && (A_coords.getX() == P_coords.getX()) && (B_coords.getX() == P_coords.getX());
                boolean val2 = (A_coords.getY() == B_coords.getY()) && (A_coords.getY() == P_coords.getY()) && (B_coords.getY() == P_coords.getY());

                return val1 || val2;

            }

        }
        return false;
    }

    public boolean targetNotOnYourSquare(ActionDetails actionDetails,ActionContext actionContext) {
        System.out.println("verifico che il target non sia sullo stesso square del player");
        Player target = actionDetails.getUserSelectedActionDetails().getTarget();
        Player player = actionContext.getPlayer();

        Position targetPosition = target.getPosition();
        Position playerPosition = player.getPosition();

        boolean val1 = (targetPosition.getX() == playerPosition.getX()) && ((targetPosition.getY() == playerPosition.getY()));
        return !val1;

    }
    public boolean targetInLastSquareSelected(ActionDetails actionDetails,ActionContext actionContext) {
        Position A = actionDetails.getUserSelectedActionDetails().getTarget().getPosition();
        Position B;

        for (int i = actionContext.getActionContextFilteredInputs().size() - 2; i >= 0; i--) {
            System.out.println(">" + ((Square) actionContext.getActionContextFilteredInputs().get(i).getContent()[0]).getCoordinates().getX() +
                    "," + ((Square) actionContext.getActionContextFilteredInputs().get(i).getContent()[0]).getCoordinates().getY());

            if (actionContext.getActionContextFilteredInputs().get(i).getType().equals("Square")) {
                B = ((Square) actionContext.getActionContextFilteredInputs().get(i).getContent()[0]).getCoordinates();

                boolean val1 = (A.getX() == B.getX()) && (A.getY() == B.getY());
                return val1;
            }

        }
        return false;
    }
    public boolean distanceFromOriginalPositionIs1(ActionDetails actionDetails,ActionContext actionContext) {
        /*Target.square, ChosenSquare*/
        Position A = actionDetails.getUserSelectedActionDetails().getTarget().getPosition();
        Position B = actionDetails.getUserSelectedActionDetails().getChosenSquare().getCoordinates();

        int Distance = ( A.getX() - B.getX()) * ( A.getX() - B.getX()) +
                ( A.getY() - B.getY()) * ( A.getY() - B.getY())  ;

        return (Distance == 1);

    }
    public boolean distanceOfTargetFromPlayerSquareMoreThan2Moves(ActionDetails actionDetails,ActionContext actionContext) {
        return !distanceOfTargetFromPlayerSquareLessThan2Moves(actionDetails,actionContext);
    }
    public boolean notPreviousTarget(ActionDetails actionDetails, ActionContext actionContext) {
        Player currentTarget = actionDetails.getUserSelectedActionDetails().getTarget();
        Player previousTarget = actionContext.getPlayer().getPlayerHistory().getRecord(actionContext.getPlayer().getPlayerHistory().getSize() - 2).getContextEffect().getActions().get((0)).getActionInfo().getActionDetails().getUserSelectedActionDetails().getTarget();
        System.out.println(currentTarget.getNickname() + "==" + previousTarget.getNickname() + "?" + (currentTarget.equals(previousTarget)));
        return (!currentTarget.equals(previousTarget));
    }
    public boolean itsValidPosition(ActionDetails actionDetails, ActionContext actionContext) {
        int x = actionDetails.getUserSelectedActionDetails().getNewPosition().getX();
        int y = actionDetails.getUserSelectedActionDetails().getNewPosition().getY();
        try {
            Board board = new Board("", null, null); // example. TODO: insert board reference here
            if(board.getMap()[x][y] != null) {
                return true;
            }
            return false;
        } catch (Exception e) {}
        finally {
            //  return false;
        }
        return false;
    }
    public boolean thereAreNotWallsBetweenTargetAndPlayer(ActionDetails actionDetails, ActionContext actionContext) {
        Position playerPos = actionContext.getPlayer().getPosition();
        Position targetPos = actionDetails.getUserSelectedActionDetails().getTarget().getPosition();
        boolean noWalls = true;
        /*
         *
         * */
        return false;

    }

}