package krsu.vblindar.hillcipherweb.services;

import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import cern.colt.matrix.linalg.LUDecomposition;
import cern.colt.matrix.linalg.LUDecompositionQuick;
import cern.jet.math.Functions;
import krsu.vblindar.hillcipherweb.utils.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class DecodeService {

    private final Util UTIL;
    private final VectorService VECTOR_SERVICE;


    public String decode(String message, DoubleMatrix2D matrix){
        var inverseMatrix = getInverseMatrix(matrix);

        var vectors = VECTOR_SERVICE.getVectorList(message.toLowerCase(),matrix.rows());

        var decipherVectors = VECTOR_SERVICE.getCipherVectors(vectors,inverseMatrix);

        var decipherString = VECTOR_SERVICE.getCipherString(decipherVectors);

        return decipherString;


    }

    private DoubleMatrix2D getInverseMatrix(DoubleMatrix2D matrix){
        Algebra algebra = new Algebra();
        int detK = (int) Math.round(algebra.det(matrix)); // Детерминант
        int inverseDeterminant = extendedEuclideanAlgorithm(detK, UTIL.getSIZE());
        var tempMatrix = calculateCofactorMatrix(matrix).assign(Functions.mod(UTIL.getSIZE()));

        var inverseMatrix = fillInverseMatrix(tempMatrix,inverseDeterminant);

        return replacingNegativeElements(inverseMatrix, UTIL.getSIZE());
    }

    private DoubleMatrix2D replacingNegativeElements(DoubleMatrix2D matrix, int size){
        for (int row = 0; row < matrix.rows(); row++) {
            for (int col = 0; col < matrix.columns(); col++) {
                if (matrix.get(row, col) < 0) {
                    matrix.set(row, col, matrix.get(row, col) + size);
                }
            }
        }
        System.out.println(matrix);
        return matrix;
    }
    private DoubleMatrix2D fillInverseMatrix(DoubleMatrix2D matrix, int inverseDet){
       Algebra algebra = new Algebra();
        // Умножаем каждый элемент матрицы на обратный детерминант и делим на длину языка
        matrix.assign(matrix, (element, value) -> element * inverseDet)
                .assign(Functions.mod(UTIL.getSIZE()));

        var transposedMatrix = algebra.transpose(matrix);

        return transposedMatrix;
    }

    public static DoubleMatrix2D calculateCofactorMatrix(DoubleMatrix2D inputMatrix) {
        Algebra algebra = new Algebra();
        int numRows = inputMatrix.rows();
        int numCols = inputMatrix.columns();
        DoubleMatrix2D cofactorMatrix = DoubleFactory2D.dense.make(numRows, numCols);

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                DoubleMatrix2D minorMatrix = createMinorMatrix(inputMatrix, i, j);
                double minorDeterminant = algebra.det(minorMatrix);

                double cofactor = Math.pow(-1, i + j) * minorDeterminant;
                cofactorMatrix.set(i, j, cofactor);
            }
        }

        return cofactorMatrix;
    }

    public static DoubleMatrix2D createMinorMatrix(DoubleMatrix2D inputMatrix, int rowToRemove, int colToRemove) {
        int numRows = inputMatrix.rows() - 1;
        int numCols = inputMatrix.columns() - 1;
        DoubleMatrix2D minorMatrix = DoubleFactory2D.dense.make(numRows, numCols);

        for (int i = 0, newRow = 0; i < inputMatrix.rows(); i++) {
            if (i == rowToRemove) {
                continue; // Пропустить удаляемую строку
            }
            for (int j = 0, newCol = 0; j < inputMatrix.columns(); j++) {
                if (j == colToRemove) {
                    continue; // Пропустить удаляемый столбец
                }
                minorMatrix.set(newRow, newCol, inputMatrix.get(i, j));
                newCol++;
            }
            newRow++;
        }

        return minorMatrix;
    }




    public int extendedEuclideanAlgorithm(int detK, int size) {
        int temp = Math.abs(detK);
        int x1 = 0, x2 = 1, y1 = 1, y2 = 0;
        while (size > 0) {
            int q = temp / size;
            int r = temp - q * size;
            int x = x1 - q * x2;
            int y = y1 - q * y2;
            temp = size;
            size = r;
            x1 = x2;
            x2 = x;
            y1 = y2;
            y2 = y;
        }
        return findModularInverse(detK,y1,UTIL.getSIZE());
    }

    public static int findModularInverse(int determinant, int x, int modulo) {
        // Если детерминант отрицательный, а x – положительный
        if (determinant < 0 && x > 0) {
            return x;
        }
        // Если детерминант положительный, а x – отрицательный
        else if (determinant > 0 && x < 0) {
            return modulo + x;
        }
        // Если детерминант и x – отрицательные
        else if (determinant < 0 && x < 0) {
            return -x;
        }
        // Если детерминант положительный, и x – положительный
        else {
            return x;
        }
    }

    public int[][] decodeWithText(String decipher, String cipher, int size) {
        String newDecipher = " "+decipher;
        String newCipher = " "+cipher;
        boolean check = true;
        DoubleFactory2D factory = DoubleFactory2D.dense;
        DoubleMatrix2D result = factory.make(size, size);

        while (check) {
             newDecipher = newDecipher.substring(1);
             newCipher = newCipher.substring(1);

            var decipherMatrix = parseMatrix(newDecipher, size);
            var cipherMatrix = parseMatrix(newCipher, size);
            var inverseDecipher = getInverseMatrix(decipherMatrix);

             result = inverseDecipher.zMult(cipherMatrix, null);

            result.assign(Functions.mod(UTIL.getSIZE()));
            if(ValidService.checkValidMatrix(result,UTIL.getSIZE())){
                check=false;
            }
        }
        System.out.println(result);
        return parseMatrixInIntArr(result);
    }

    private int[][] parseMatrixInIntArr(DoubleMatrix2D doubleMatrix){
        int[][] intMatrix = new int[doubleMatrix.rows()][doubleMatrix.columns()];
        for (int i = 0; i < doubleMatrix.rows(); i++) {
            for (int j = 0; j < doubleMatrix.columns(); j++) {
                intMatrix[i][j] = (int)Math.round(doubleMatrix.get(i, j));
            }
        }

        return intMatrix;
    }

    private DoubleMatrix2D parseMatrix(String text, int size){
        double[][] arr = new double[size][size];
        var alphabet = UTIL.getALPHABET();
        int counter = -1;
        for (int i =0; i<size; i++){
            for (int j =0;j<size;j++){
                counter++;
                var ch = text.toLowerCase().charAt(counter);
                var pos = alphabet.indexOf(ch);
                arr[i][j] = pos;
            }

        }

       return new DenseDoubleMatrix2D(arr);
    }


}
