package krsu.vblindar.hillcipherweb.services;

import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import krsu.vblindar.hillcipherweb.utils.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EncodeService {

    private final Util UTIL;

    private final VectorService VECTOR_SERVICE;
    public String encode(String message, DoubleMatrix2D keyMatrix) {
        var vectors = VECTOR_SERVICE.getVectorList(message,keyMatrix.rows());

        var cipherVectors = VECTOR_SERVICE.getCipherVectors(vectors,keyMatrix);

        var cipherString = VECTOR_SERVICE.getCipherString(cipherVectors);

        return cipherString;
    }



    /*
    *  int symbolInt = subMessage.charAt(j);
                String symbolStr = String.valueOf(symbolInt);
    * */
}
