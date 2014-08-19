/*
 * Copyright (c) 2009-2014, Peter Abeles. All Rights Reserved.
 *
 * This file is part of Efficient Java Matrix Library (EJML).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ejml.equation;

import org.ejml.simple.SimpleMatrix;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * @author Peter Abeles
 */
public class TestOperation {

    Random rand = new Random(234);

    @Test
    public void divide_matrix_scalar() {
        Equation eq = new Equation();

        SimpleMatrix x = SimpleMatrix.random(5, 3, -1, 1, rand);
        SimpleMatrix b = SimpleMatrix.random(5, 3, -1, 1, rand);

        eq.alias(2.5, "A");
        eq.alias(b.getMatrix(), "b");
        eq.alias(x.getMatrix(), "x");

        eq.process("x=b/A");

        assertTrue(b.divide(2.5).isIdentical(x, 1e-8));
    }

    @Test
    public void divide_int_int() {
        Equation eq = new Equation();

        eq.alias(4, "A");
        eq.alias(13, "b");
        eq.alias(-1, "x");

        eq.process("x=b/A");

        int found = eq.lookupInteger("x");

        assertEquals(13/4,found,1e-8);
    }

    @Test
    public void divide_scalar_scalar() {
        Equation eq = new Equation();

        eq.alias(5, "A");
        eq.alias(4.2, "b");
        eq.alias(-1.0, "x");

        eq.process("x=b/A");

        double found = eq.lookupScalar("x");

        assertEquals(4.2 / 5.0, found, 1e-8);
    }

    @Test
    public void divide_matrix_matrix() {
        Equation eq = new Equation();

        SimpleMatrix A = SimpleMatrix.random(6, 5, -1, 1, rand);
        SimpleMatrix x = SimpleMatrix.random(5, 3, -1, 1, rand);
        SimpleMatrix b = SimpleMatrix.random(6, 3, -1, 1, rand);

        eq.alias(A.getMatrix(), "A");
        eq.alias(b.getMatrix(), "b");
        eq.alias(x.getMatrix(), "x");

        eq.process("x=b/A");

        assertTrue(A.solve(b).isIdentical(x, 1e-8));
    }

    @Test
    public void multiply_matrix_scalar() {
        Equation eq = new Equation();

        SimpleMatrix x = SimpleMatrix.random(5, 3, -1, 1, rand);
        SimpleMatrix b = SimpleMatrix.random(5, 3, -1, 1, rand);

        eq.alias(2.5, "A");
        eq.alias(b.getMatrix(), "b");
        eq.alias(x.getMatrix(), "x");

        eq.process("x=b*A");
        assertTrue(b.scale(2.5).isIdentical(x,1e-8));
        eq.process("x=A*b");
        assertTrue(b.scale(2.5).isIdentical(x,1e-8));
    }

    @Test
    public void multiply_int_int() {
        Equation eq = new Equation();

        eq.alias(4, "A");
        eq.alias(13, "b");
        eq.alias(-1, "x");

        eq.process("x=b*A");

        int found = eq.lookupInteger("x");

        assertEquals(13*4,found,1e-8);
    }

    @Test
    public void multiply_scalar_scalar() {
        Equation eq = new Equation();

        eq.alias(5, "A");
        eq.alias(4.2, "b");
        eq.alias(-1.0, "x");

        eq.process("x=b*A");

        double found = eq.lookupScalar("x");

        assertEquals(4.2*5.0,found,1e-8);
    }

    @Test
    public void multiply_matrix_matrix() {
        Equation eq = new Equation();

        SimpleMatrix A = SimpleMatrix.random(6, 5, -1, 1, rand);
        SimpleMatrix x = SimpleMatrix.random(5, 3, -1, 1, rand);
        SimpleMatrix b = SimpleMatrix.random(6, 3, -1, 1, rand);

        eq.alias(A.getMatrix(), "A");
        eq.alias(b.getMatrix(), "b");
        eq.alias(x.getMatrix(), "x");

        eq.process("b=A*x");

        assertTrue(A.mult(x).isIdentical(b,1e-8));
    }

    @Test
    public void mAdd() {
        fail("Implement");
    }

    @Test
    public void mSub() {
        fail("Implement");
    }

    @Test
    public void copy() {
        fail("Implement");
    }

    @Test
    public void transpose() {
        fail("Implement");
    }

    @Test
    public void inv() {
        fail("Implement");
    }

    @Test
    public void pinv() {
        fail("Implement");
    }

    @Test
    public void det() {
        fail("Implement");
    }

    @Test
    public void trace() {
        fail("Implement");
    }

    @Test
    public void normF() {
        fail("Implement");
    }

    @Test
    public void eye() {
        fail("Implement");
    }

    @Test
    public void zeros() {
        Equation eq = new Equation();

        SimpleMatrix A = SimpleMatrix.random(6, 8, -1, 1, rand);

        eq.alias(A.getMatrix(), "A");

        eq.process("A=zeros(6,8)");

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                assertEquals(0,A.get(i,j),1e-8);
            }
        }
    }

    @Test
    public void ones() {
        Equation eq = new Equation();

        SimpleMatrix A = SimpleMatrix.random(6, 8, -1, 1, rand);

        eq.alias(A.getMatrix(), "A");

        eq.process("A=ones(6,8)");

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                assertEquals(1,A.get(i,j),1e-8);
            }
        }
    }

    @Test
    public void diag_vector() {
        Equation eq = new Equation();

        SimpleMatrix A = SimpleMatrix.random(6, 6, -1, 1, rand);
        SimpleMatrix B = SimpleMatrix.random(6, 1, -1, 1, rand);


        eq.alias(A.getMatrix(), "A");
        eq.alias(B.getMatrix(), "B");

        eq.process("A=diag(B)");

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if( i == j )
                    assertEquals(B.get(i,0),A.get(i,j),1e-8);
                else
                    assertEquals(0,A.get(i,j),1e-8);
            }
        }
    }

    @Test
    public void diag_matrix() {
        Equation eq = new Equation();

        SimpleMatrix A = SimpleMatrix.random(6, 8, -1, 1, rand);
        SimpleMatrix B = SimpleMatrix.random(6, 1, -1, 1, rand);

        eq.alias(A.getMatrix(), "A");
        eq.alias(B.getMatrix(), "B");

        eq.process("B=diag(A)");

        assertEquals(6,B.numRows());
        assertEquals(1,B.numCols());

        for (int i = 0; i < 6; i++) {
            assertEquals(A.get(i,i),B.get(i,0),1e-8);
        }
    }

    @Test
    public void dot() {
        Equation eq = new Equation();

        SimpleMatrix A = SimpleMatrix.random(6, 1, -1, 1, rand);
        SimpleMatrix B = SimpleMatrix.random(6, 1, -1, 1, rand);

        eq.alias(A.getMatrix(), "A");
        eq.alias(B.getMatrix(), "B");
        eq.alias(1.0, "found");

        eq.process("found=dot(A,B)");

        double found = ((VariableDouble)eq.lookupVariable("found")).value;

        assertEquals(A.dot(B),found,1e-8);
    }

    @Test
    public void solve() {
        Equation eq = new Equation();

        SimpleMatrix A = SimpleMatrix.random(6, 5, -1, 1, rand);
        SimpleMatrix x = SimpleMatrix.random(5, 3, -1, 1, rand);
        SimpleMatrix b = SimpleMatrix.random(6, 3, -1, 1, rand);

        eq.alias(A.getMatrix(), "A");
        eq.alias(b.getMatrix(), "b");
        eq.alias(x.getMatrix(), "x");

        eq.process("x=solve(A,b)");

        assertTrue(A.solve(b).isIdentical(x, 1e-8));
    }
}
