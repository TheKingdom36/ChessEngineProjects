package ChessBoard.MoveCheckers;


import ChessBoard.Enums.Color;
import ChessBoard.Enums.Type;
import ChessBoard.Models.BoardSquare;
import ChessBoard.Models.ChessPiece;
import ChessBoard.Models.Position;
import ChessBoard.Util.Directions;

public class SquareSaftyCheck {
    public static boolean isSquareSafe(Position posOfSqr, Color color, BoardSquare[][] board){

        if(CheckAttkrsDiagl(posOfSqr,color,board)==true){

            return false;
        }
        if(CheckAttackerKnight(posOfSqr,color,board) == true){
            return false;
        }

        if(CheckAttkrsVertAndHor(posOfSqr,color,board) == true){
            return false;
        }

        if(CheckPawnAttacks(posOfSqr,color,board)==true){
            return false;
        }


        return true;
    }



    private static boolean CheckPawnAttacks(Position pos, Color color, BoardSquare[][] board){
        if(color== Color.White){
            int sqrToCheckX = pos.getX()+ Directions.NW.getX();
            int sqrToCheckY = pos.getY()+ Directions.NW.getY();

            if(sqrToCheckX>=0&&sqrToCheckX<=7&&sqrToCheckY>=0&&sqrToCheckY<=7){
                if(board[sqrToCheckX][sqrToCheckY].hasPiece()){
                    if(board[sqrToCheckX][sqrToCheckY].getChessPiece().getColor()!=color && board[sqrToCheckX][sqrToCheckY].getChessPiece().getType()== Type.Pawn){
                        return true;
                    }
                }
            }

            sqrToCheckX = pos.getX()+ Directions.NE.getX();
            sqrToCheckY = pos.getY()+ Directions.NE.getY();

            if(sqrToCheckX>=0&&sqrToCheckX<=7&&sqrToCheckY>=0&&sqrToCheckY<=7){
                if(board[sqrToCheckX][sqrToCheckY].hasPiece()){
                    if(board[sqrToCheckX][sqrToCheckY].getChessPiece().getColor()!=color && board[sqrToCheckX][sqrToCheckY].getChessPiece().getType()== Type.Pawn){
                        return true;
                    }
                }
            }
        }else{

                int sqrToCheckX = pos.getX()+ Directions.SW.getX();
                int sqrToCheckY = pos.getY()+ Directions.SW.getY();

                if(sqrToCheckX>=0&&sqrToCheckX<=7&&sqrToCheckY>=0&&sqrToCheckY<=7){
                    if(board[sqrToCheckX][sqrToCheckY].hasPiece()){
                        if(board[sqrToCheckX][sqrToCheckY].getChessPiece().getColor()!=color&& board[sqrToCheckX][sqrToCheckY].getChessPiece().getType()== Type.Pawn){
                            return true;
                        }
                    }
                }

                sqrToCheckX = pos.getX()+ Directions.SE.getX();
                sqrToCheckY = pos.getY()+ Directions.SE.getY();

                if(sqrToCheckX>=0&&sqrToCheckX<=7&&sqrToCheckY>=0&&sqrToCheckY<=7){
                    if(board[sqrToCheckX][sqrToCheckY].hasPiece()){
                        if(board[sqrToCheckX][sqrToCheckY].getChessPiece().getColor()!=color&& board[sqrToCheckX][sqrToCheckY].getChessPiece().getType()== Type.Pawn){
                            return true;
                        }
                    }
                }



        }
        return false;
    }

    private static boolean CheckAttkrsDiagl(Position pos, Color color, BoardSquare[][] board){

        Position[] dirToCheck = {Directions.NE,Directions.SE,Directions.NW,Directions.SW};
        if (checkAttkrsInDirections(pos, color, board, dirToCheck)) return true;

        return false;
    }

    private static boolean CheckAttkrsVertAndHor(Position pos, Color color, BoardSquare[][] board){
        Position[] dirToCheck = {Directions.N,Directions.S,Directions.W,Directions.E};
        if (checkAttkrsInDirections(pos, color, board, dirToCheck)) return true;

        return false;
    }

    private static boolean checkAttkrsInDirections(Position pos, Color color, BoardSquare[][] board, Position[] dirToCheck) {
        ChessPiece result;

        for (Position direction : dirToCheck) {
            result = isAttkrInDir(pos, board, color, direction);
            if (result != null) {
                if (result.getType() == Type.Queen || result.getType() == Type.Bishop || result.getType() == Type.King) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean CheckAttackerKnight(Position pos, Color color, BoardSquare[][] board){


        if(isAttkrKnightAtPos(new Position( pos.getX()+(Directions.N).getX() + Directions.NE.getX(),pos.getY()+(Directions.N).getY() + Directions.NE.getY()),color,board) == true){
            return true;

        }
        if(isAttkrKnightAtPos(new Position( pos.getX()+(Directions.N).getX() + Directions.NW.getX(),pos.getY()+(Directions.N).getY() + Directions.NW.getY()),color,board)== true){
            return true;

        }

        if(        isAttkrKnightAtPos(new Position( pos.getX()+(Directions.E).getX() + Directions.NE.getX(),pos.getY()+(Directions.E).getY() + Directions.NE.getY()),color,board)== true){
            return true;

        }
        if(        isAttkrKnightAtPos(new Position( pos.getX()+(Directions.E).getX() + Directions.SE.getX(),pos.getY()+(Directions.E).getY() + Directions.SE.getY()),color,board)== true){
            return true;

        }
        if(        isAttkrKnightAtPos(new Position( pos.getX()+(Directions.S).getX() + Directions.SE.getX(),pos.getY()+(Directions.S).getY() + Directions.SE.getY()),color,board)== true){
            return true;

        }
        if(        isAttkrKnightAtPos(new Position( pos.getX()+(Directions.S).getX() + Directions.SW.getX(),pos.getY()+(Directions.S).getY() + Directions.SW.getY()),color,board)== true){
            return true;

        }
        if(        isAttkrKnightAtPos(new Position( pos.getX()+(Directions.W).getX() + Directions.SW.getX(),pos.getY()+(Directions.W).getY() + Directions.SW.getY()),color,board)== true){
            return true;

        }
        if(         isAttkrKnightAtPos(new Position( pos.getX()+(Directions.W).getX() + Directions.NW.getX(),pos.getY()+(Directions.W).getY() + Directions.NW.getY()),color,board)== true){
            return true;

        }

        return false;

    }

    private static boolean isAttkrKnightAtPos(Position posToCheck, Color color, BoardSquare[][] board){
        if(posToCheck.getX()<=7 && posToCheck.getX()>=0&&posToCheck.getY()<=7&&posToCheck.getY()>=0){
            if(board[posToCheck.getX()][posToCheck.getY()].hasPiece() == true){
                if(board[posToCheck.getX()][posToCheck.getY()].getChessPiece().getType() == Type.Knight && board[posToCheck.getX()][posToCheck.getY()].getChessPiece().getColor() != color){
                    return true;
                }
            }
        }
        return false;
    }

    private static ChessPiece isAttkrInDir(Position piecePos, BoardSquare[][] board, Color color, Position direction) {

        int curXPos = piecePos.getX();
        int curYPos = piecePos.getY();



        if(curXPos+ direction.getX()>=0 && curXPos+ direction.getX()<=7&& curYPos+ direction.getY()<=7&&curYPos+ direction.getY()>=0) {
            if (board[curXPos + direction.getX()][curYPos + direction.getY()].hasPiece() == true) {
                if (board[curXPos + direction.getX()][curYPos + direction.getY()].getChessPiece().getType() == Type.King && board[curXPos + direction.getX()][curYPos + direction.getY()].getChessPiece().getColor() != color) {
                    return board[curXPos + direction.getX()][curYPos + direction.getY()].getChessPiece();
                }
            }
        }

        do{
            curXPos = curXPos+ direction.getX();
            curYPos = curYPos+ direction.getY();

            if(curXPos <0 || curXPos>7|| curYPos>7||curYPos<0){
                return null;
            }

            if(board[curXPos][curYPos].hasPiece()==true){
                if(board[curXPos][curYPos].getChessPiece().getType() != Type.King&&board[curXPos][curYPos].getChessPiece().getColor() != color){
                    return board[curXPos][curYPos].getChessPiece();

                }   else{
                    return null;
                }
            }
        }while(true);

    }

}
