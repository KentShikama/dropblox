public class Score {
    private static final int AGGREGATE_HEIGHT_CONSTANT = -2;
    private static final int MAX_HEIGHT_CONSTANT = -4;
    private static final int NUMBER_OF_HOLES_CONSTANT = -2;
    private static final int BUMPINESS_CONSTANT = -1;

    public static int score(Board board) {
        int aggregateHeight = calculateAggregateHeight(board);
        int maxHeight = calculateMaxHeight(board);
        int num_holes = calculateNumberOfHoles(board);
        int bumpiness = calculateBumpiness(board);
        return AGGREGATE_HEIGHT_CONSTANT * aggregateHeight 
                + MAX_HEIGHT_CONSTANT * maxHeight + NUMBER_OF_HOLES_CONSTANT * num_holes
                    + BUMPINESS_CONSTANT * bumpiness;
    }

    private static int calculateAggregateHeight(Board board) {
        int[][] bitmap = board._bitmap;
        int[][] transposedBitmap = transposeMatrix(bitmap);
        int aggregateHeight = 0;
        for (int[] row : transposedBitmap) {
            // Each row is actually a column as it is transposed
            for (int i = 0; i < row.length; i++) {
                // i = 0 is the top and i = row.length is the bottom
                // hence row.length - the position of the first non zero row[i] is the height of that column
                if (row[i] != 0) {
                    aggregateHeight += row.length - i;
                }
            }
        }
        return aggregateHeight;
    }

    private static int calculateMaxHeight(Board board) {
        int height = 0;
        for (int i = 0; i < board.ROWS; i++) {
            for (int j = 0; j < board.COLUMNS; j++) {
                if (board._bitmap[i][j] != 0) {
                    height = 32 - i;
                    break;
                }
            }
        }
        return height;
    }

    private static int calculateNumberOfHoles(Board board) {
        boolean start_counting = false;
        int num_holes = 0;
        int height = this.calculateMaxHeight(board);
        for (int i = board.ROWS - height; i < board.ROWS; i++) {
            for (int j = 0; j < board.COLUMNS; j++) {
                if (board._bitmap[i][j] == 0) {
                    num_holes++;
                }
            }
        }
        return num_holes;
    }

    private static int calculateBumpiness(Board board){
        ArrayList<Integer> heights = new ArrayList<Integer>(board.COLS);
        for(int j=0;j<board.COLS;j++){
            for(int i=0;j<boards.ROWS;i++){
                if (board._bitmap(i,j)!=0){
                    int height = board.ROWS-i;
                    heights.set(j,height);
                    break;
                }
            }
        }
        int bumpiness = 0;
        for(int i=0;i<heights.size()-1;i++){
            int bump = Math.abs(heights.get(i+1)-heights.get(i));
            bumpiness = bumpiness + bump;
        }
        return bumpiness;
    }

    /**
     * Adapted from http://stackoverflow.com/questions/15449711/transpose-double-matrix-with-a-java-function
     */
    private static double[][] transposeMatrix(int[][] m) {
        int[][] temp = new int[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
    }
}