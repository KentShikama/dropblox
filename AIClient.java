import org.json.simple.*;

public class AIClient {
	public static void main(String[] argv) {		
		JSONObject obj = (JSONObject)JSONValue.parse(argv[0]);
		Board board = Board.initializeBoardFromJSON(obj);
		
		// the following "AI" moves a piece as far left as possible
		while (board._block.checkedLeft(board)) {
			System.out.println("left");
		}
		System.out.flush();
	}

	public int maxHeight(Board board){
		int height = 0;
		for(int i=0;i<board.ROWS;i++){
			for(int j=0;j<board.COLUMNS;j++){
				if (board._bitmap[i][j]!=0){
					height = 32-i;
					break;
				}
			}
		}
		return height;
	}

	public int num_holes(Board board){
		boolean start_counting = false;
		int num_holes = 0;
		int height = this.maxHeight(board);
		for(int i=board.ROWS-height;i<board.ROWS;i++){
			for(int j=0;j<board.COLUMNS;j++){
				if (board._bitmap[i][j]==0){
					num_holes++;
				}
		}
	}
	return num_holes;
}

}