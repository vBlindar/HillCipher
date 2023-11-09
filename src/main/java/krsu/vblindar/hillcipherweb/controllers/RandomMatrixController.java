package krsu.vblindar.hillcipherweb.controllers;


import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;
import krsu.vblindar.hillcipherweb.utils.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Random;


@RestController
@RequiredArgsConstructor
public class RandomMatrixController {

    private final Util util;

    @GetMapping("/random-matrix")
    public ResponseEntity<int[][]> getRandomMatrix(@RequestParam(name = "size") int size){



        return ResponseEntity.ok(getMatrix(size));
    }


    private int[][] getMatrix(int size) {
        var length = util.getSIZE();
        int determinant = 0;
        Algebra algebra = new Algebra();
        int[][] matrix = new int[size][size];
        boolean check = true;
        while (check){
            DoubleFactory2D factory = DoubleFactory2D.dense;
            DoubleMatrix2D randomMatrix = factory.make(size, size);
            Random rnd = new Random();
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    int random = rnd.nextInt(37);
                    randomMatrix.set(i, j, random);
                    matrix[i][j] = random;
                }
            }
            determinant = (int) algebra.det(randomMatrix);

            if(determinant!=0 && checkValidMatrix(length,determinant)){
                check=false;
            }

        }



        return matrix;
    }
    private boolean checkValidMatrix(int alphabetLength, int determinant){
        while (determinant != 0 && alphabetLength != 0) {
            int temp = determinant;
            determinant = alphabetLength % determinant;
            alphabetLength = temp;
        }
        // Если общий делитель не равен 1, то числа имеют общие делители
        return alphabetLength == 1;
    }
}
