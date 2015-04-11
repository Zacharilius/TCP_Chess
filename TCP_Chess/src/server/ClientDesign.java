package server;

public class ClientDesign {
    public void run(){
        String xy, response;
        boolean takingTurn, isTheirTurn, gameStillGoing;
        gameStillGoing = isTheirTurn = true;
        response = null;
        while(gameStillGoing){
            if(isTheirTurn){
                takingTurn = true;
                while(takingTurn){
                    //xy = the coordinates of whatever they clicked;
                    //send those coordinates up;
                    //response = the string from the server.
                    if(response != null){
                        //highlight their valid moves;
                        //xy = the coordinates of whatever they clicked again;
                        //response = the string from the server.
                        if("KING".equals(response)){
                            //inform user that this endangers the king
                        }
                        else if("INVALID".equals(response)){
                            //do nothing
                        }
                        else{
                            //update game board
                            takingTurn = false;
                        }
                    }
                    else{
                        //do nothing
                    }
                }
            }
        }
    }
}

