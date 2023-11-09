package krsu.vblindar.hillcipherweb.services;

import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import krsu.vblindar.hillcipherweb.utils.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class VectorService {

    private final Util UTIL;
    public List<DoubleMatrix2D> getVectorList(String message, int size) {
        List<DoubleMatrix2D> matrices = new ArrayList<>();
        int messageSize = message.length();
        int numMatrices = (int) Math.ceil((double) messageSize / size);
        // Создаем фабрику для матриц
        DoubleFactory2D factory = DoubleFactory2D.dense;
        int currentIndex = 0;
        for (int i = 0; i < numMatrices; i++) {
            int endIndex = currentIndex + size;
            if (endIndex > messageSize) {
                endIndex = messageSize;
            }
            // Выделяем подстроку из сообщения для текущей матрицы
            String subMessage = message.substring(currentIndex, endIndex);
            // Создаем матрицу с одной строкой и size столбцами
            DoubleMatrix2D matrix = factory.make(1, size);
            for (int j = 0; j < subMessage.length(); j++) {
                char c = subMessage.charAt(j);
                int charIndex = UTIL.getALPHABET().indexOf(c);
                if (charIndex == -1) {
                    // Если символ не найден в ALPHABET, используем код символа
                    charIndex = c;
                }
                matrix.set(0, j, charIndex);
            }
            for (int j = subMessage.length(); j < size; j++) {
                matrix.set(0, j, 35);
            }

            matrices.add(matrix);
            currentIndex = endIndex;
        }
        return matrices;
    }


    public String getCipherString(List<DoubleMatrix2D> cipherVectors) {
        StringBuilder result = new StringBuilder();
        for (DoubleMatrix2D vector : cipherVectors) {
            for (int i = 0; i < vector.size(); i++) {
                int charIndex = (int) Math.round(vector.get(0,i));
                if (charIndex >= 0 && charIndex < UTIL.getALPHABET().length()) {
                    char c = UTIL.getALPHABET().charAt(charIndex);
                    result.append(c);
                }else{
                    result.append('а');
                }
            }
        }

        return result.toString();
    }

    public List<DoubleMatrix2D> getCipherVectors(List<DoubleMatrix2D> matrices, DoubleMatrix2D keyMatrix) {
        List<DoubleMatrix2D> cipherMatrices = new ArrayList<>();

        for (DoubleMatrix2D matrix : matrices) {
            DoubleMatrix2D result = matrix.zMult(keyMatrix, null); // Изменено на matrix.zMult(keyMatrix, null)
            DoubleMatrix2D cipherMatrix = DoubleFactory2D.dense.make(result.rows(), result.columns());

            for (int i = 0; i < result.rows(); i++) {
                for (int j = 0; j < result.columns(); j++) {
                    double value = Math.round(result.get(i, j) % UTIL.getSIZE());
                    if(value>37||value<0){
                        System.out.println(value);
                    }
                    cipherMatrix.set(i, j, value);
                }
            }

            cipherMatrices.add(cipherMatrix);
        }

        return cipherMatrices;
    }




}
