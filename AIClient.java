import java.util.*;
import org.json.simple.*;

public class AIClient {
	
	public static final int BREADTH = 1;

	public static void main(String[] argv) {
		Map<ArrayList<String>, Board> boards = generateBoards((JSONObject)JSONValue.parse(argv[0]));
		System.out.flush();
	}

    public ArrayList<Board> chooseTopThree(ArrayList<Board> boards){
    	ArrayList<int> scores = new ArrayList<int>(boards.size());
    	for (int i=0;i<boards.size;i++){
    		scores.set(i,Score.score(board.get(i)));
    	}
    	ArrayList<Board> best = new ArrayList<Board>(BREADTH);
    	for(int i=0;i < BREADTH;i++){
    		int max = Collection.max(scores);
    		int index = scores.indexOf(max);
    		best.add(boards.get(index));
    		scores.remove(max);
    		boards.remove(boards.get(index));
    	}
    	return best;
    }

	private Map<ArrayList<String>, Board> generateBoards(JSONObject jsonBoard) {
		Board board = Board.initializeBoardFromJSON(jsonBoard);

		ArrayList<Board> boards = new ArrayList<Board>();
		ArrayList<String> commands = new ArrayList<String>();

		// map of <commands, board>
		Map<ArrayList<String>, Board> boards = new HashMap<ArrayList<String>, Board>();

		// move the block one position to the right, one at a time
		for (int i = 0; i < board.COLS; i++) {
			// but first, start by moving the piece all the way to the left
			for (int a = 0; a < 5; a++) {
				commands.add('left');
			}

			// include the previous 'right' commands
			for (int j = 0; j <= i; j++) {
				commands.add('right');
			}

			// then, rotate the block to every possible position, one at a time
			for (int k = 0; k < 3; k++) {

				// include the previous 'rotate' commands
				for (int l = 0; l <= k; l++) {
					commands.add('rotate');
				}

				// perform the commands on the board, and then add it to the hash map, with respect to its command list
				try {
					boards.put(commands, board.doCommands(commands));
				}
				catch (InvalidMoveException e) {
					// do nothing
				}

				// reset for next iteration
				commands = new ArrayList<String>();
				board = Board.initializeBoardFromJSON(obj);
			}
		}

		return boards;
	}

}