package utils;

import cell.Cell;

public class Utils {
        static public Cell getRandomAvailable(Cell[][] field, Cell[] taken) {

        int startRow = (int) Math.floor(Math.random() *(field.length));
        int startColumn = (int) Math.floor(Math.random() * (field[0].length));
        for (int i = startRow; i < field.length; i++) {
            for (int j = startColumn; j < field[i].length; j++) {
                if (isFree(field[i][j], taken)) {
                    return field[i][j];
                }
            }
        }

        for (int i = 0; i < startRow; i++) {
            for (int j = 0; j < startColumn; j++) {
                if (isFree(field[i][j], taken)) {
                    return field[i][j];
                }
            }
        }

        System.out.println(startRow);
        System.out.println(startColumn);
        System.out.println("not found");


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
