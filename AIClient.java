import java.util.*;
import java.util.HashMap;

import org.json.simple.*;

public class AIClient {

	public static final int BREADTH = 1;

	public static void main(String[] argv) {
		Board originalBoard = Board.initializeBoardFromJSON((JSONObject) JSONValue.parse(argv[0]));
		Map<Board, ArrayList<String>> generatedBoardsMap = generateBoards(originalBoard);
		ArrayList<Board> bestThreeBoards = chooseTopThree(new ArrayList<Board>(generatedBoardsMap.keySet()));
		
		Map<Board, Board> bestBoardToBestThreeBoards = new HashMap<Board, Board>();
		
		for (Board board : bestThreeBoards) {
			Board blockChangedBoard = new Board(board._bitmap, board._preview[0], board._preview); // Change later
			Map<Board, ArrayList<String>> generatedBoardsMap2 = generateBoards(board);
			Board bestBoardOfBestThreeBoards = chooseTop(new ArrayList<Board>(generatedBoardsMap2.keySet()));
			bestBoardToBestThreeBoards.put(bestBoardOfBestThreeBoards, board);
		}
		
		Set<Board> bestBoards = bestBoardToBestThreeBoards.keySet();
		Board bestBoard = chooseTop(new ArrayList<Board>(bestBoards));
		Board parentBoard = bestBoardToBestThreeBoards.get(bestBoard);
		ArrayList<String> commands = generatedBoardsMap.get(parentBoard);
		
		for (String command : commands) {
			System.out.println(command);
		}
		System.out.println("drop");
		System.out.flush();
	}

    public static ArrayList<Board> chooseTopThree(ArrayList<Board> boards){
    	ArrayList<Integer> scores = new ArrayList<Integer>(boards.size());
    	for (int i=0;i<boards.size();i++){
    		scores.set(i,Score.score(boards.get(i)));
    	}
    	ArrayList<Board> best = new ArrayList<Board>(BREADTH);
    	for(int i=0;i < BREADTH;i++){
    		int max = Collections.max(scores);
    		int index = scores.indexOf(max);
    		best.add(boards.get(index));
    		scores.remove(max);
    		boards.remove(boards.get(index));
    	}
    	return best;
    }

	public static Board chooseTop(ArrayList<Board> boards){
		ArrayList<Integer> scores = new ArrayList<Integer>(boards.size());
		for (int i=0;i<boards.size();i++){
			scores.set(i,Score.score(boards.get(i)));
		}
		ArrayList<Board> best = new ArrayList<Board>(BREADTH);
		int max = Collections.max(scores);
		int index = scores.indexOf(max);
		return boards.get(index);
	}

	private static Map<Board, ArrayList<String>> generateBoards(Board board) {
		// save a copy of the initial board before we run any commands on it and mutate it
		Board boardCopy = new Board(board._bitmap, board._block, board._preview);

//		ArrayList<Board> boards = new ArrayList<Board>();
		ArrayList<String> commands = new ArrayList<String>();

		// map of <commands, board>
		Map<Board, ArrayList<String>> boards = new HashMap<Board, ArrayList<String>>();

		// move the block one position to the right, one at a time
		for (int i = 0; i < board.COLS; i++) {
			// but first, start by moving the piece all the way to the left
			for (int a = 0; a < 5; a++) {
				commands.add("left");
			}

			// include the previous 'right' commands
			for (int j = 0; j <= i; j++) {
				commands.add("right");
			}

			// then, rotate the block to every possible position, one at a time
			for (int k = 0; k < 3; k++) {

				// include the previous 'rotate' commands
				for (int l = 0; l <= k; l++) {
					commands.add("rotate");
				}

				// perform the commands on the board, and then add it to the hash map, with respect to its command list
				try {
					boards.put(board.doCommands(commands), commands);
				}
				catch (InvalidMoveException e) {
					// do nothing
				}

				// reset for next iteration
				commands = new ArrayList<String>();
				board = boardCopy;
				boardCopy = new Board(board._bitmap, board._block, board._preview);
			}
		}

		return boards;
	}

}