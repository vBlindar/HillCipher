package krsu.vblindar.hillcipherweb.services;

import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidService {
    public static boolean checkValidMatrix(DoubleMatrix2D matrix, int alphabetLength){
        Algebra algebra = new Algebra();
        int determinant = (int) Math.abs(algebra.det(matrix));

        if(determinant==0)
            return false;

        var tempD = determinant;
        while (tempD != 0 && alphabetLength != 0) {
            int temp = tempD;
            tempD = alphabetLength % tempD;
            alphabetLength = temp;
        }
        // Если общий делитель не равен 1, то числа имеют общие делители
        return alphabetLength == 1;
    }

}
