import java.lang.Exception;
import java.lang.String;
import java.lang.reflect.Array;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.*;

public class AIClient {

	public static final int BREADTH = 3;

	public static void main(String[] argv) {
		Board originalBoard = Board.initializeBoardFromJSON((JSONObject) JSONValue.parse(argv[0]));
		ArrayList<ArrayList<String>> generatedMoves = generateMoves();
		Map<Board, ArrayList<String>> generatedBoardsMap = generateBoards(originalBoard, generatedMoves);
		ArrayList<String> bestCommands = chooseTop(generatedBoardsMap);
		for (String command : bestCommands) {
			System.out.println(command);
		}
		System.out.flush();
	}

	public static ArrayList<String> chooseTop(Map<Board, ArrayList<String>> generatedBoardsMap){
		int maxScore = -999999999;
		ArrayList<String> bestMoves = null;
		for (Map.Entry<Board, ArrayList<String>> entry : generatedBoardsMap.entrySet()) {
			Board board = entry.getKey();
			if (Score.score(board) > maxScore) {
				maxScore = Score.score(board);
				bestMoves = entry.getValue();
			}
		}
		return bestMoves;
	}
	
	private static Map<Board, ArrayList<String>> generateBoards(Board board, ArrayList<ArrayList<String>> generatedMoves) {
		Map<Board, ArrayList<String>> boards = new HashMap<>();
		for (ArrayList<String> moves: generatedMoves) {
			Board boardCopy = new Board(board._bitmap, board._block, board._preview);
			try {
				Board newBoard = boardCopy.doCommands(moves);
				boards.put(newBoard, moves);
			} catch(Exception e) {
			//	e.printStackTrace();
			}
		}
		return boards;
	}

	private static ArrayList<ArrayList<String>> generateMoves() {
		ArrayList<ArrayList<String>> moves = new ArrayList<>();
		for (int i = 0; i < Board.COLS; i++) {
			ArrayList<String> commands = new ArrayList<String>();
			for (int a = 0; a < 5; a++) {
				commands.add("left");
			}
			for (int j = 0; j < i; j++) {
				commands.add("right");
			}
			for (int k = 0; k < 3; k++) {
				ArrayList<String> commands2 = new ArrayList<String>(commands);
				for (int l = 0; l < k; l++) {
					commands2.add("rotate");
				}
				moves.add(commands2);
			}
		}
		return moves;
	}

}