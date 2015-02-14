import java.util.*;
import org.json.simple.*;

public class AIClient {
	public static void main(String[] argv) {
		Map<ArrayList<String>, Board> boards = generateBoards();

		System.out.flush();
	}

	private Map<ArrayList<String>, Board> generateBoards(Board board) {
		JSONObject obj = (JSONObject)JSONValue.parse(argv[0]);
		Board board = Board.initializeBoardFromJSON(obj);

		ArrayList<Board> boards = new ArrayList<Board>();
		ArrayList<String> commands = new ArrayList<String>();

		// map of <commands, board>
		Map<ArrayList<String>, Board> boards = new HashMap<ArrayList<String>, Board>();

		// first, start by moving the piece all the way to the left
		for (int i = 0; i < 5; i++) {
			commands.add('left');
		}

		// then, move the block one position to the right, one at a time
		for (int i = 0; i < board.COLS; i++) {

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