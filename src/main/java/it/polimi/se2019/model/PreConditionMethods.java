package it.polimi.se2019.model;

import it.polimi.se2019.model.enumerations.CardinalPoint;
import it.polimi.se2019.model.enumerations.SquareSide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PreConditionMethods implements Serializable {

    /**EFFECT CHOOSE PRE CONDITION*/
    /*TODO gestione dall'input*/
    public boolean isScannerMode(ActionDetails actionDetails,ActionContext actionContext) {return  true;}
    public boolean isPunisherMode(ActionDetails actionDetails,ActionContext actionContext) {return true;}
    public boolean isReaperMode(ActionDetails actionDetails, ActionContext actionContext) {return true;}
    public boolean isFocusShot(ActionDetails actionDetails, ActionContext actionContext) {return true;}
    public boolean isTurretTripod(ActionDetails actionDetails, ActionContext actionContext) {return true;}
    public boolean isNanoTracerMode(ActionDetails actionDetails, ActionContext actionContext) {return true;}
    public boolean withChainReaction(ActionDetails actionDetails, ActionContext actionContext) {return true;}
    public boolean withHighVoltage(ActionDetails actionDetails, ActionContext actionContext) {return true;}
    public boolean isBlackHole(ActionDetails actionDetails, ActionContext actionContext) {return true;}
    public boolean isLongBarrelMode(ActionDetails actionDetails, ActionContext actionContext) {return true;}
    public boolean withRocketJump(ActionDetails actionDetails, ActionContext actionContext) {return true;}
    public  boolean withFragmentingWarHead(ActionDetails actionDetails, ActionContext actionContext) {return true;}
    public  boolean inRocketFistMode(ActionDetails actionDetails, ActionContext actionContext) {return true;}
    public  boolean withShadowStep(ActionDetails actionDetails, ActionContext actionContext) {return true;}
    public  boolean isPolverizeMode(ActionDetails actionDetails, ActionContext actionContext) {return true;}

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

    public boolean notFirstNorSecondExecuted(ActionDetails actionDetails, ActionContext actionContext) {         // l'effetto che continee l'azione
        // che contiene questa precondizione
        int counter = 0;
        for(PlayerHistoryElement p: actionContext.getPlayer().getPlayerHistory().historyElementList) {
            if(!p.getContextCard().equals(actionContext.getPlayer().getPlayerHistory().getRecord(
                    actionContext.getPlayer().getPlayerHistory().getSize() - 2
            ).getContextCard())) {
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

    public boolean previousTargetCanSee(ActionDetails actionDetails, ActionContext actionContext)  {

        Player me = (Player) ((Object[][]) actionContext.getPlayer().getPlayerHistory().getRecord(actionContext.getPlayer().getPlayerHistory().getSize() - 2).getInput())[0][0];
        System.out.println("# verificando se il target " + actionDetails.getUserSelectedActionDetails().getTarget().getNickname() + "   sia visibile a " +  me.getNickname() + "...");
        Position playerPosition = me.getPosition();
        Square   playerSquare   = actionContext.getBoard().getMap()[playerPosition.getY()][playerPosition.getX()];
        List<Player> targets = actionDetails.getUserSelectedActionDetails().getTargetList();
        if(
                !playerSquare.getSide(CardinalPoint.north).equals(SquareSide.door) &&
                        !playerSquare.getSide(CardinalPoint.south).equals(SquareSide.door)  &&
                        !playerSquare.getSide(CardinalPoint.west).equals(SquareSide.door) &&
                        !playerSquare.getSide(CardinalPoint.east).equals(SquareSide.door) ) {
            /*posizione senza porte*/
            boolean retVal = true;
            for (Player t : targets) {
                Square targetSquare = actionContext.getBoard().getMap()[t.getPosition().getY()][t.getPosition().getX()];
                if(targetSquare.getColor() != playerSquare.getColor())
                    retVal = false;
            }
            return retVal;
        } else {
            Square northSide = null;
            Square southSide = null;
            Square eastSide  = null;
            Square westSide  = null;

            if(playerSquare.getSide(CardinalPoint.north).equals(SquareSide.door)) {
                if(playerPosition.getX() > 0)
                    northSide = actionContext.getBoard().getSquare(playerPosition.getY(),playerPosition.getX() - 1);
            }

            if(playerSquare.getSide(CardinalPoint.east).equals(SquareSide.door)) {
                if(playerPosition.getY() < 2)
                    eastSide = actionContext.getBoard().getSquare(playerPosition.getY() + 1,playerPosition.getX());
            }

            if(playerSquare.getSide(CardinalPoint.west).equals(SquareSide.door)) {
                if(playerPosition.getY() > 0)
                    westSide = actionContext.getBoard().getSquare(playerPosition.getY() - 1,playerPosition.getX());
            }
            if(playerSquare.getSide(CardinalPoint.south).equals(SquareSide.door)) {
                if(playerPosition.getX() < 3)
                    southSide = actionContext.getBoard().getSquare(playerPosition.getY(),playerPosition.getX()+1);
            }
            boolean retVal = true;

            for(Player t: targets) {
                Square targetSquare = actionContext.getBoard().getMap()[t.getPosition().getY()][t.getPosition().getX()];
                List<Character> colors = new ArrayList<>();
                colors.add(playerSquare.getColor());
                if(northSide != null)
                    colors.add(northSide.getColor());
                if(eastSide != null)
                    colors.add(eastSide.getColor());
                if(westSide != null)
                    colors.add(westSide.getColor());
                if(southSide != null)
                    colors.add(southSide.getColor());
                System.out.println("colori disoonibili : " + colors);
                if(!colors.contains(targetSquare.getColor()))
                    retVal = false;


            }
            if(retVal) {
                System.out.println("visibile");
            } else System.out.println("non visibile");
            return retVal;
        }

    }

    public boolean youCanSee(ActionDetails actionDetails, ActionContext actionContext) {
        System.out.println("# verificando se il target sia visibile...");
        Player me = actionContext.getPlayer();
        Position playerPosition = me.getPosition();
        Square   playerSquare   = actionContext.getBoard().getMap()[playerPosition.getY()][playerPosition.getX()];
        List<Player> targets = actionDetails.getUserSelectedActionDetails().getTargetList();
        if(
                !playerSquare.getSide(CardinalPoint.north).equals(SquareSide.door) &&
                  !playerSquare.getSide(CardinalPoint.south).equals(SquareSide.door)  &&
                     !playerSquare.getSide(CardinalPoint.west).equals(SquareSide.door) &&
                         !playerSquare.getSide(CardinalPoint.east).equals(SquareSide.door) ) {
            /*posizione senza porte*/
            boolean retVal = true;
            for (Player t : targets) {
                Square targetSquare = actionContext.getBoard().getMap()[t.getPosition().getY()][t.getPosition().getX()];
                if(targetSquare.getColor() != playerSquare.getColor())
                    retVal = false;
            }
            return retVal;
        } else {
            Square northSide = null;
            Square southSide = null;
            Square eastSide  = null;
            Square westSide  = null;

            if(playerSquare.getSide(CardinalPoint.north).equals(SquareSide.door)) {
                if(playerPosition.getX() > 0)
                northSide = actionContext.getBoard().getSquare(playerPosition.getY(),playerPosition.getX() - 1);
            }

            if(playerSquare.getSide(CardinalPoint.east).equals(SquareSide.door)) {
                if(playerPosition.getY() < 2)
                eastSide = actionContext.getBoard().getSquare(playerPosition.getY() + 1,playerPosition.getX());
            }

            if(playerSquare.getSide(CardinalPoint.west).equals(SquareSide.door)) {
                if(playerPosition.getY() > 0)
                westSide = actionContext.getBoard().getSquare(playerPosition.getY() - 1,playerPosition.getX());
            }
            if(playerSquare.getSide(CardinalPoint.south).equals(SquareSide.door)) {
                if(playerPosition.getX() < 3)
                southSide = actionContext.getBoard().getSquare(playerPosition.getY(),playerPosition.getX()+1);
            }
            boolean retVal = true;

            for(Player t: targets) {
                Square targetSquare = actionContext.getBoard().getMap()[t.getPosition().getY()][t.getPosition().getX()];
                List<Character> colors = new ArrayList<>();
                colors.add(playerSquare.getColor());
                if(northSide != null)
                    colors.add(northSide.getColor());
                if(eastSide != null)
                    colors.add(eastSide.getColor());
                if(westSide != null)
                    colors.add(westSide.getColor());
                if(southSide != null)
                    colors.add(southSide.getColor());
                System.out.println("colori disoonibili : " + colors);
                if(!colors.contains(targetSquare.getColor()))
                    retVal = false;


            }
            if(retVal) {
                System.out.println("visibile");
            } else System.out.println("non visibile");
            return retVal;
        }

    }
    public boolean distanceOfTargetFromPlayerSquareIs1(ActionDetails actionDetails,ActionContext actionContext) {
        List<Player> t = actionDetails.getUserSelectedActionDetails().getTargetList();
        Player user   = actionContext.getPlayer();
        for(Player target: t) {
            int Distance = (target.getPosition().getX() - user.getPosition().getX()) * (target.getPosition().getX() - user.getPosition().getX())
                    + (target.getPosition().getY() - user.getPosition().getY()) * (target.getPosition().getY() - user.getPosition().getY());

            if (Distance != 1) {
                return false;
            }
        }
        return true;

    }
    public boolean distanceOfTargetFromPlayerSquareLessThan2Moves(ActionDetails actionDetails,ActionContext actionContext) throws Exception {
        Player target = actionDetails.getUserSelectedActionDetails().getTarget();
        Player user   = actionContext.getPlayer();

        System.out.println("## verifico che la distanza sia meno di due mosse");
        System.out.println("# numero mosse di distanza:");
        System.out.println(actionContext.getBoard().distanceFromTo(target.getPosition(), user.getPosition()));



        return ((actionContext.getBoard().distanceFromTo(target.getPosition(), user.getPosition())-1) <= 2);
    }
    public boolean distanceFromOriginalPositionLessThan2(ActionDetails actionDetails,ActionContext actionContext) {
    /*Target.square, ChosenSquare*/
        Position A = actionDetails.getUserSelectedActionDetails().getTarget().getPosition();
        Position B = actionDetails.getUserSelectedActionDetails().getChosenSquare().getCoordinates();

        System.out.println("# la distanza [("  + A.getY() + ", " + A.getX() + ") --> ("+ B.getY() + ", " + B.getX()+ ")] tra i due square è minore di 2?");
        float Distance = ( A.getX() - B.getX()) * ( A.getX() - B.getX()) +
                       ( A.getY() - B.getY()) * ( A.getY() - B.getY())  ;
        System.out.println("la distanza è " + Distance);
        if(Distance <= 4) {
            System.out.println("si");
        } else {
            System.out.println("no");
        }
        return (Distance <= 4);

    }
    public boolean moveOtherPlayer(ActionDetails actionDetails,ActionContext actionContext) {
        Player A = actionContext.getPlayer();
        Player B = actionDetails.getUserSelectedActionDetails().getTarget();
        return !A.equals(B);
    }
    public boolean atLeastOneMoveAway(ActionDetails actionDetails,ActionContext actionContext) throws Exception {
        System.out.println("# verifico che la distanza sia almeno uno");
        Player target = actionDetails.getUserSelectedActionDetails().getTarget();
        Player user   = actionContext.getPlayer();
        System.out.println("# la distanza è " + actionContext.getBoard().distanceFromTo(target.getPosition(),user.getPosition()));
       if(actionContext.getBoard().distanceFromTo(target.getPosition(),user.getPosition()) >= 1)
           return true;
        return false;
    }
    public boolean notInYourRoom(ActionDetails actionDetails,ActionContext actionContext) {
        List<Player> targets = actionDetails.getUserSelectedActionDetails().getTargetList();
        Player me = actionContext.getPlayer();
        System.out.println("#" + me.getNickname() + " nella stanza " +actionContext.getBoard().getSquare(me.getPosition()).getColor() );

        boolean retVal = true;
        for(Player t:targets) {
            System.out.println(t.getNickname() + " nella stanza " +actionContext.getBoard().getSquare(t.getPosition()).getColor() );
            if(actionContext.getBoard().getSquare(t.getPosition()).getColor() == actionContext.getBoard().getSquare(me.getPosition()).getColor() )
                retVal = false;
        }
        return retVal;
    }
    public boolean distanceOfSquareFromPlayerExactlyOne(ActionDetails actionDetails,ActionContext actionContext) throws Exception {
        Player me = actionContext.getPlayer();
        Square d  = actionDetails.getUserSelectedActionDetails().getChosenSquare();
        if((actionContext.getBoard().distanceFromTo(me.getPosition(), d.getCoordinates()) - 1) == 1) {
            return true;
        }
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
        /*@*/ System.out.println("# verifico notPreviousTarget" +  actionContext.getPlayer().toString() + ":" + actionContext.getActionContextFilteredInputs().size());

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


    public boolean lastEffectContainsDamage(ActionDetails actionDetails,ActionContext actionContext) {
        Effect lastEffect = actionContext.getPlayer().getPlayerHistory().getRecord(

                actionContext.getPlayer().getPlayerHistory().getSize()  - 2

        ).getContextEffect();
        for(Action a:lastEffect.getActions()) {
            if(a.getClass().toString().equals("it.polimi.se2019.model.Damage")) {
                if(a.getActionInfo().getActionDetails().getUserSelectedActionDetails().getTarget().equals(
                        actionDetails.getUserSelectedActionDetails().getTarget()
                )) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean pureCardinalMovement(ActionDetails actionDetails,ActionContext actionContext) {
        Position to = actionDetails.getUserSelectedActionDetails().getChosenSquare().getCoordinates();
        Position from = actionDetails.getUserSelectedActionDetails().getTarget().getPosition();

        if((to.getY() == from.getY()) || (to.getX() == from.getX())) {
            return true;
        }
        return false;
    }
    public boolean sameCardinalDirectionOfTargets(ActionDetails actionDetails,ActionContext actionContext) {
        int xIncrement = 0 - actionDetails.getUserSelectedActionDetails().getTarget().getPosition().getX();;
        int yIncrement = 0 - actionDetails.getUserSelectedActionDetails().getTarget().getPosition().getY();;
        Player buffer = actionDetails.getUserSelectedActionDetails().getTarget();
        for(Player target:actionDetails.getUserSelectedActionDetails().getTargetList()) {
            xIncrement += target.getPosition().getX() - buffer.getPosition().getX();
            yIncrement += target.getPosition().getY() - buffer.getPosition().getY();;
        }
        if(xIncrement == 0) return true;
        if(yIncrement == 0) return true;
        return false;
    }

    public boolean sameCardinalDirectionOfPreviousSquare(ActionDetails actionDetails,ActionContext actionContext) {
        Player One = ((Player)((Object[][])actionContext.getPlayer().getPlayerHistory().getLast().getInput())[0][0]);
        Player Two = ((Player)((Object[][])actionContext.getPlayer().getPlayerHistory().getLast().getInput())[0][0]);
        return false;
    }

    public boolean targetNotOnYourSquare(ActionDetails actionDetails,ActionContext actionContext) {
        System.out.println("# verifico che il target non sia sullo stesso square del player");
        Player target = actionDetails.getUserSelectedActionDetails().getTarget();
        Player player = actionContext.getPlayer();

        Position targetPosition = target.getPosition();
        Position playerPosition = player.getPosition();

        boolean val1 = (targetPosition.getX() == playerPosition.getX()) && ((targetPosition.getY() == playerPosition.getY()));
        if(!val1) System.out.println("il target è su uno square diverso");
        else System.out.println("il target è sullo stesso square");
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
    public boolean distanceFromOriginalSquareIs1(ActionDetails actionDetails,ActionContext actionContext) {
        /*Target.square */
        for(Player target:actionDetails.getUserSelectedActionDetails().getTargetList()) {
            Position A = actionContext.getPlayer().getPosition();
            Position B = actionDetails.getUserSelectedActionDetails().getTarget().getPosition();
            int Distance = (A.getX() - B.getX()) * (A.getX() - B.getX()) +
                    (A.getY() - B.getY()) * (A.getY() - B.getY());
            System.out.println("# " + Distance);
            if (Distance != 1) return false;
        }
        System.out.println("# condizione di distanza verificata!");
        return true;
    }
    public boolean targetOnYourSquare(ActionDetails actionDetails,ActionContext actionContext) {
        Position A = actionDetails.getUserSelectedActionDetails().getTarget().getPosition();
        Position B = actionContext.getPlayer().getPosition();
        if(A.equals(B)) System.out.println("# ok!");else System.out.println("# no!");
        return A.equals(B);

    }
    public boolean distanceFromOriginalPositionIs1(ActionDetails actionDetails,ActionContext actionContext) {
        /*Target.square, ChosenSquare*/
        for(Player target:actionDetails.getUserSelectedActionDetails().getTargetList()) {
            Position A = actionDetails.getUserSelectedActionDetails().getTarget().getPosition();
            Position B = actionDetails.getUserSelectedActionDetails().getChosenSquare().getCoordinates();

            System.out.println("# [" + A.getX() + "," + A.getY()+"]");
            System.out.println("# [" + B.getX() + "," + B.getY()+"]");

            int Distance = (A.getX() - B.getX()) * (A.getX() - B.getX()) +
                    (A.getY() - B.getY()) * (A.getY() - B.getY());
            System.out.println("# Distanza : " + Distance);
            if (Distance != 1) return false;
        }
        System.out.println("# condizione di distanza verificata!");
        return true;
    }
    public boolean youCanSeeThatSquare(ActionDetails actionDetails, ActionContext actionContext) {
        System.out.println("# verificando se lo square selezionato sia visibile...");
        Player me = actionContext.getPlayer();
        Position playerPosition = me.getPosition();
        Square   playerSquare   = actionContext.getBoard().getSquare(playerPosition);

        if(
                !playerSquare.getSide(CardinalPoint.north).equals(SquareSide.door) &&
                        !playerSquare.getSide(CardinalPoint.south).equals(SquareSide.door)  &&
                        !playerSquare.getSide(CardinalPoint.west).equals(SquareSide.door) &&
                        !playerSquare.getSide(CardinalPoint.east).equals(SquareSide.door) ) {
            /*posizione senza porte*/
            boolean retVal = true;
                Square targetSquare = actionDetails.getUserSelectedActionDetails().getChosenSquare();
                if(targetSquare.getColor() != playerSquare.getColor())
                    retVal = false;

            return retVal;
        } else {
            Square northSide = null;
            Square southSide = null;
            Square eastSide  = null;
            Square westSide  = null;

            if(playerSquare.getSide(CardinalPoint.north).equals(SquareSide.door)) {
                if(playerPosition.getX() > 0)
                    northSide = actionContext.getBoard().getSquare(playerPosition.getY(),playerPosition.getX() - 1);
            }

            if(playerSquare.getSide(CardinalPoint.east).equals(SquareSide.door)) {
                if(playerPosition.getY() < 2)
                    eastSide = actionContext.getBoard().getSquare(playerPosition.getY() + 1,playerPosition.getX());
            }

            if(playerSquare.getSide(CardinalPoint.west).equals(SquareSide.door)) {
                if(playerPosition.getY() > 0)
                    westSide = actionContext.getBoard().getSquare(playerPosition.getY() - 1,playerPosition.getX());
            }
            if(playerSquare.getSide(CardinalPoint.south).equals(SquareSide.door)) {
                if(playerPosition.getX() < 3)
                    southSide = actionContext.getBoard().getSquare(playerPosition.getY(),playerPosition.getX()+1);
            }
            boolean retVal = true;


                Square targetSquare = actionDetails.getUserSelectedActionDetails().getChosenSquare();
                List<Character> colors = new ArrayList<>();
                colors.add(playerSquare.getColor());
                if(northSide != null)
                    colors.add(northSide.getColor());
                if(eastSide != null)
                    colors.add(eastSide.getColor());
                if(westSide != null)
                    colors.add(westSide.getColor());
                if(southSide != null)
                    colors.add(southSide.getColor());
                System.out.println("colore square player [ "+me.getPosition().getY()+ " , "+me.getPosition().getX()+" ] " + actionContext.getBoard().getSquare(me.getPosition()).getColor());
                System.out.println("colori disoonibili : " + colors);
                if(!colors.contains(targetSquare.getColor()))
                    retVal = false;

            if(retVal) {
                System.out.println("visibile");
            } else System.out.println("non visibile");
            return retVal;

            }

        }


    public boolean distanceOfTargetFromPlayerSquareMoreThan2Moves(ActionDetails actionDetails,ActionContext actionContext) throws Exception{
        Player target = actionDetails.getUserSelectedActionDetails().getTarget();
        Player user   = actionContext.getPlayer();

        System.out.println("## verifico che la distanza sia maggiore uguale di due mosse");
        System.out.println("# numero mosse di distanza:");
        System.out.println(actionContext.getBoard().distanceFromTo(target.getPosition(), user.getPosition()));



        return (actionContext.getBoard().distanceFromTo(target.getPosition(), user.getPosition()) >= 2);
    }
    public boolean notEndingTurn(ActionDetails actionDetails,ActionContext actionContext) {
        // TODO: dipende dallo shootstate
        return true;
    }
    public boolean youCantSee(ActionDetails actionDetails,ActionContext actionContext) {
        return !youCanSee(actionDetails,actionContext);
    }
    public boolean youJustGotDamaged(ActionDetails actionDetails,ActionContext actionContext) {
        Player toMark = actionContext.getPlayer().getLastDamageSlot().getShootingPlayer();
        actionDetails.getUserSelectedActionDetails().setTarget(toMark);
        return true;
    }
    public boolean previousTarget(ActionDetails actionDetails, ActionContext actionContext) {
        return !notPreviousTarget(actionDetails,actionContext);
    }
    public boolean notPreviousTarget(ActionDetails actionDetails, ActionContext actionContext) {
        try {
            for (PlayerHistoryElement f : actionContext.getPlayer().getPlayerHistory().historyElementList) {
                System.out.println("#" + ((Player) ((Object[]) f.getInput())[0]).getNickname());
            }
            System.out.println(".");
            Player currentTarget = (Player) ((Object[]) actionContext.getPlayer().getPlayerHistory().getLast().getInput())[0];
            System.out.println("la dimensione è " +actionContext.getPlayer().getPlayerHistory().getSize());
            System.out.println("il player corrente è "  + currentTarget.getNickname());

            System.out.println(".");
            Player previousTarget = (Player) ((Object[]) actionContext.getPlayer().getPlayerHistory().getRecord(
                    actionContext.getPlayer().getPlayerHistory().getSize() - 2
            ).getInput())[0];

            System.out.println(".");
            System.out.println(currentTarget.getNickname() + "==" + previousTarget.getNickname() + "?" + (currentTarget.equals(previousTarget)));

            return (!currentTarget.equals(previousTarget));
        } catch(Exception e) {
            System.out.println("---------- " + e);
        }
        return false;
        }
    public boolean differentSingleTargets(ActionDetails actionDetails,ActionContext actionContext) {
        List<Player> target = new ArrayList<>();
        int i = 0;
        while((Player)((Object[][]) actionContext.getPlayer().getPlayerHistory().getLast().getInput())[i][0] != null) {
            target.add((Player) ((Object[][]) actionContext.getPlayer().getPlayerHistory().getLast().getInput())[i][0]);
            i++;
        }

        int counter = 0;
        for(Player x : target) {
            counter = 0;
            for(Player y: target) {
                if(x.equals(y)) counter++;
            }
            if(counter>1) return false;
        }
        return true;
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