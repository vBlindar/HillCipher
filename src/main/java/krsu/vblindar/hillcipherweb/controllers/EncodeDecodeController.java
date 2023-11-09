package krsu.vblindar.hillcipherweb.controllers;


import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import cern.jet.math.Functions;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import krsu.vblindar.hillcipherweb.services.DecodeService;
import krsu.vblindar.hillcipherweb.services.EncodeService;
import krsu.vblindar.hillcipherweb.services.ValidService;
import krsu.vblindar.hillcipherweb.utils.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Controller
@RequiredArgsConstructor
public class EncodeDecodeController {

    private final EncodeService encodeService;

    private final DecodeService decodeService;

    private final Util util;

    @GetMapping("/encode")
    public ResponseEntity<String> encodeMessage(@RequestParam String message, @RequestParam String matrixData) throws JsonProcessingException, UnsupportedEncodingException {
        String decodedMessage = URLDecoder.decode(message, "UTF-8"); // Раскодировать сообщение
        String decodedMatrixData = URLDecoder.decode(matrixData, "UTF-8");
        int[][] matrixInt = new ObjectMapper().readValue(decodedMatrixData, int[][].class);
        DoubleMatrix2D matrix = new DenseDoubleMatrix2D(parseMatrix(matrixInt));
        if(!ValidService.checkValidMatrix(matrix, util.getSIZE())){
            return ResponseEntity.badRequest().body("Матрица не подходит для шифрования!");
        }
        String cipher = encodeService.encode(decodedMessage.toLowerCase(),matrix);
        return ResponseEntity.ok(cipher);
    }


    @GetMapping("/decode")
    public ResponseEntity<String> decodeMessage(@RequestParam String message, @RequestParam String matrixData) throws JsonProcessingException, UnsupportedEncodingException{
        String decodedMessage = URLDecoder.decode(message, "UTF-8"); // Раскодировать сообщение
        String decodedMatrixData = URLDecoder.decode(matrixData, "UTF-8");
        int[][] matrixInt = new ObjectMapper().readValue(decodedMatrixData, int[][].class);
        DoubleMatrix2D matrix = new DenseDoubleMatrix2D(parseMatrix(matrixInt));

        String decipher = decodeService.decode(decodedMessage.toLowerCase(),matrix);
        return ResponseEntity.ok(decipher);
    }


    @GetMapping("/decode-with-text")
    public ResponseEntity<int[][]> findMatrix(@RequestParam String decipher, @RequestParam String cipher,
                                              @RequestParam int size) throws UnsupportedEncodingException {
        decipher = URLDecoder.decode(decipher, "UTF-8"); // Раскодировать сообщение
        cipher = URLDecoder.decode(cipher, "UTF-8");
        var matrix = decodeService.decodeWithText(decipher.toLowerCase(),cipher.toLowerCase(),size);



        return ResponseEntity.ok(matrix);
    }

    private double[][] parseMatrix(int[][] matrixInt) {
        int size = matrixInt.length;
        double[][] matrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                var value = matrixInt[i][j];
                matrix[i][j] = value;
            }
        }
        return matrix;
    }

}
