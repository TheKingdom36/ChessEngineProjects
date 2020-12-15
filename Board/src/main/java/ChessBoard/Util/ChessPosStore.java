package ChessBoard.Util;


import ChessBoard.Models.Position;

public class ChessPosStore {
    private static  Position[][] postions;


    public static Position getPostion(int i, int j){

        if(postions == null){
            postions = new Position[8][8];
            CreatePostions();
        }

        return postions[i][j];
    }

    private static  void CreatePostions() {
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                postions[i][j] = new Position(i,j);
            }
        }
    }

    public static void PrintPos(){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                System.out.println(postions[i][j].toString());
            }
        }
    }


}
