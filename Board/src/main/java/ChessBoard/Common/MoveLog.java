package ChessBoard.Common;



import ChessBoard.Common.Interfaces.Move;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


public class MoveLog<MoveType extends Move> {
    @Getter
    private List<MoveType> pastMoves;

    public MoveLog(){
        pastMoves = new ArrayList<>();
    }

    public void AddMove(MoveType Move){
        pastMoves.add(Move);
    }

    public MoveLog Copy(){
        MoveLog moveLog = new MoveLog();

        for (MoveType m: pastMoves) {
            moveLog.AddMove(m.Copy());
        }

        moveLog.getPastMoves();
        return moveLog;
    }

}
