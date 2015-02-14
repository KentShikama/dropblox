import org.json.simple.*;

public class AIClient {
    public static void main(String[] argv) {
        JSONObject obj = (JSONObject) JSONValue.parse(argv[0]);
        Board board = Board.initializeBoardFromJSON(obj);

        // the following "AI" moves a piece as far left as possible
        while (board._block.checkedLeft(board)) {
            System.out.println("left");
        }
        System.out.flush();
    }    

      public int chooseTopThree(ArrayList<Board> boards){
    	ArrayList<int> scores = new ArrayList<int>(boards.size());
    	for (int i=0;i<boards.size;i++){
    		scores.set(i,Score.score(board.get(i)));
    	}
    	ArrayList<Board> best = new ArrayList<Board>(3);
    	for(int i=0;i<3;i++){
    		int max = Collection.max(scores);
    		int index = scores.indexOf(max);
    		best.add(boards.get(index));
    		scores.remove(max);
    		boards.remove(boards.get(index));
    	}
    	return best;
    }
}