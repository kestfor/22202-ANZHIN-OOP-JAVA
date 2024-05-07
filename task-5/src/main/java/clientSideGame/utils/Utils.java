package clientSideGame.utils;

import clientSideGame.cell.Cell;

public class Utils {
        static public Cell getRandomAvailable(Cell[][] field, Cell[] taken) {

        int startRow = (int) Math.floor(Math.random() *(field.length));
        int startColumn = (int) Math.floor(Math.random() * (field[0].length));

        for (int i = startRow; i < field.length; i++) {
            int start = i == startRow ? startColumn : 0;
            for (int j = start; j < field[i].length; j++) {
                if (isFree(field[i][j], taken)) {
                    return field[i][j];
                }
            }
        }

        for (int i = 0; i <= startRow; i++) {
            int end = i == startRow ? startColumn : field[i].length;
            for (int j = 0; j < end; j++) {
                if (isFree(field[i][j], taken)) {
                    return field[i][j];
                }
            }
        }

        return null;
    }


    private static boolean isFree(Cell cellToCheck, Cell[] taken) {
        for (Cell value : taken) {
            if (value.equals(cellToCheck)) {
                return false;
            }
        }
        return true;
    }
}
