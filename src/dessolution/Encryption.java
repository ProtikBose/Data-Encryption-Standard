/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dessolution;

import java.math.BigInteger;

/**
 *
 * @author PRANTO
 */
public class Encryption {

    //key
    String modifiedKey = "";
    String[] KeysIteration = new String[16];
    String[] Keys_in_Round = new String[16];
    String RoundKey = "";

    //plainText
    String plainString = "";
    String plainTextBinaryString = "";
    int leftSpacePadding;
    String finalResult = "";
    String Stringencipher = "";
    String textBinaryEncipher = "";
    String Stringdecipher = "";
    String textbinaryDecipher = "";

    int CP_1[] = new int[]{57, 49, 41, 33, 25, 17, 9,
        1, 58, 50, 42, 34, 26, 18,
        10, 2, 59, 51, 43, 35, 27,
        19, 11, 3, 60, 52, 44, 36,
        63, 55, 47, 39, 31, 23, 15,
        7, 62, 54, 46, 38, 30, 22,
        14, 6, 61, 53, 45, 37, 29,
        21, 13, 5, 28, 20, 12, 4};

    int PI_2[] = new int[]{35, 38, 46, 6, 43, 40, 14, 45,
        33, 19, 26, 15, 23, 8, 22, 10,
        12, 11, 5, 25, 27, 21, 16, 31,
        28, 32, 34, 24, 9, 37, 2, 1};

    int[] P = {16, 7, 20, 21,
        29, 12, 28, 17,
        1, 15, 23, 26,
        5, 18, 31, 10,
        2, 8, 24, 14,
        32, 27, 3, 9,
        19, 13, 30, 6,
        22, 11, 4, 25};

    int[] Shift = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};

    int CP_2[] = new int[]{14, 17, 11, 24, 1, 5, 3, 28,
        15, 6, 21, 10, 23, 19, 12, 4,
        26, 8, 16, 7, 27, 20, 13, 2,
        41, 52, 31, 37, 47, 55, 30, 40,
        51, 45, 33, 48, 44, 49, 39, 56,
        34, 53, 46, 42, 50, 36, 29, 32};

    int[] pi = {
        58, 50, 42, 34, 26, 18, 10, 2,
        60, 52, 44, 36, 28, 20, 12, 4,
        62, 54, 46, 38, 30, 22, 14, 6,
        64, 56, 48, 40, 32, 24, 16, 8,
        57, 49, 41, 33, 25, 17, 9, 1,
        59, 51, 43, 35, 27, 19, 11, 3,
        61, 53, 45, 37, 29, 21, 13, 5,
        63, 55, 47, 39, 31, 23, 15, 7};

    int[] E = {
        32, 1, 2, 3, 4, 5,
        4, 5, 6, 7, 8, 9,
        8, 9, 10, 11, 12, 13,
        12, 13, 14, 15, 16, 17,
        16, 17, 18, 19, 20, 21,
        20, 21, 22, 23, 24, 25,
        24, 25, 26, 27, 28, 29,
        28, 29, 30, 31, 32, 1};

    int[] PI_1 = {
        40, 8, 48, 16, 56, 24, 64, 32,
        39, 7, 47, 15, 55, 23, 63, 31,
        38, 6, 46, 14, 54, 22, 62, 30,
        37, 5, 45, 13, 53, 21, 61, 29,
        36, 4, 44, 12, 52, 20, 60, 28,
        35, 3, 43, 11, 51, 19, 59, 27,
        34, 2, 42, 10, 50, 18, 58, 26,
        33, 1, 41, 9, 49, 17, 57, 25};

    public String strTobin(String str) {

        byte[] bytes = str.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
            binary.append(' ');
        }
        return binary.toString();
    }

    /* Convert integer to String */
    public String intTostr(String stream, int size) {

        String result = "";
        for (int i = 0; i < stream.length(); i += size) {
            result += (stream.substring(i, Math.min(stream.length(), i + size)) + " ");
        }
        String[] ss = result.split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ss.length; i++) {
            sb.append((char) Integer.parseInt(ss[i], 2));
        }
        return sb.toString();
    }

    /* Left shift function */
    String CircularLeftRotation(String str, int pos) {

        String result = str.substring(pos);
        for (int i = 0; i < pos; i++) {
            result += str.charAt(i);
        }
        return result;
    }

    public void doEncrypt(String key, String plainText) {

        String keyString = new String("");
        keyString = new BigInteger(key.getBytes()).toString(2);

        if (keyString.length() < 64) {
            int l = 64 - keyString.length();
            for (int k = 0; k < l; k++) {
                keyString = '0' + keyString;
            }
        }

        //56 bit transposition of key
        //System.out.println(keyString);
        for (int i : CP_1) {
            modifiedKey += keyString.charAt(i - 1);
        }
        //System.out.println(modifiedKey);

        //partitioning into two parts
        String LeftK = modifiedKey.substring(0, 28);
        String RightK = modifiedKey.substring(28, 56);

        //performing left circular rotation on two parts
        String Leftkey = LeftK;
        String Rightkey = RightK;
        System.out.println("");
        int Index = 0;
        for (int a : Shift) {
            Leftkey = CircularLeftRotation(Leftkey, a);
            Rightkey = CircularLeftRotation(Rightkey, a);
            KeysIteration[Index] = Leftkey + Rightkey;
            Index++;
        }
//        for(String s:KeysIteration){
//            System.out.println(s);
//        }
//        System.out.println("");

        //transforming into 48 bits
        Index = 0;
        for (String k : KeysIteration) {
            for (int j : CP_2) {
                RoundKey += k.charAt(j - 1);
            }
            Keys_in_Round[Index] = RoundKey;
            Index++;
            RoundKey = "";
        }
        if (plainText.length() < 8) {
            while (plainText.length() < 8) {
                plainText += '-';
            }
        }
        //System.out.println(plainText);
        plainString = strTobin(plainText);
        //System.out.println(plainString);
        plainString = plainString.replace(" ", "");
        String plainStringText = plainString;

        //making a loop for all chunk
        int plainTextLength = plainString.length();

        //extra bit greather than multiple of 64
        int leftpadString = plainTextLength % 64;

        //number of iteration
        int numOfLoop;
        if (leftpadString != 0) {
            numOfLoop = plainTextLength / 64 + 1;
        } else {
            numOfLoop = plainTextLength / 64;
        }
        //int numOfLoop = leftpadString != 0 ? plainTextLength / 64 + 1 : plainTextLength / 64;

        //number of position
        int startPos = 0;
        int endPos = 64;

        //loop for each chunk
        for (int idNum = numOfLoop, wordCount = 1; idNum > 0; idNum--, wordCount++) {
            //number of padding
            int leftpadNum = 64 - leftpadString;
            //space padding
            if (leftpadNum % 64 != 0) {
                leftSpacePadding = leftpadNum / 8;
            } else {
                leftSpacePadding = 0;
            }
            //leftSpacePadding = leftpadNum % 64 != 0 ? leftpadNum / 8 : 0;
            //position number of chunk(ending)
            if (plainTextLength - startPos < 64) {
                endPos = plainTextLength;
            } else {
                endPos = endPos;
            }
            //endPos = plainTextLength - startPos < 64 ? plainTextLength : endPos;
            //chunk
            plainTextBinaryString = plainStringText.substring(startPos, endPos);
            //bit level padding
            while (plainTextBinaryString.length() != 64) {
                plainTextBinaryString = "0" + plainTextBinaryString;
            }

            //Initial permutation using PI array
            String initialBinary = "";
            for (int i : pi) {
                initialBinary += plainTextBinaryString.charAt(i - 1);
            }

            //partitioning of two parts after initial permutation
            String LeftInitialPBinary = initialBinary.substring(0, 32);
            String RightInitialPBinary = initialBinary.substring(32, 64);

            int count = 1;
            for (String k : Keys_in_Round) {

                //System.out.println("");
//                System.out.println("Iteration : " + count);
//                System.out.println("Key : " + k);
                //right part is inserted into the left part
                String LeftBlock = RightInitialPBinary;
                //System.out.println("Left Partition : " + LeftBlock);

                //function
                //right 32 bits are expanded by E array
                String expandBits = "";
                for (int i : E) {
                    expandBits += RightInitialPBinary.charAt(i - 1);

                }
//                System.out.println(RightInitialPBinary);
//                System.out.println(expandBits);
                //right part and expanded bits are 48 bits and XORed
                StringBuilder sblder = new StringBuilder();
                for (int i = 0; i < k.length(); i++) {
                    sblder.append((k.charAt(i) ^ expandBits.charAt(i)));
                }
                String resultXOR = sblder.toString();
                //System.out.println(resultXOR);

                //result in transformed into 32 bits using PI_2 array
                String binaryTargetResult = new String("");
                for (int d : PI_2) {
                    binaryTargetResult += resultXOR.charAt(d - 1);
                }

                //another permutation is done using P array
                String functionResult = "";
                for (int d : P) {
                    functionResult += binaryTargetResult.charAt(d - 1);
                }
                //System.out.println(functionResult);

                //previous left part and function result is XORed
                sblder = new StringBuilder();
                for (int i = 0; i < LeftInitialPBinary.length(); i++) {
                    sblder.append((LeftInitialPBinary.charAt(i) ^ functionResult.charAt(i)));
                }
                resultXOR = sblder.toString();
                //System.out.println(resultXOR);
                //result is stored into right part
                RightInitialPBinary = resultXOR;
                // System.out.println("RIGHT BLOCK = " + RightInitialPBinary);
                resultXOR = "";

                count++;
                if (count > 16) {
                    // swapping two parts
                    resultXOR = RightInitialPBinary + LeftBlock;

                    finalResult = "";
                    //Final Permutation FP: The Inverse of the Initial permutation IP
                    for (int x : PI_1) {
                        finalResult += resultXOR.charAt(x - 1);
                    }
                    Stringencipher += finalResult;
                    if (idNum == 1 && leftSpacePadding != 0) {
                        textBinaryEncipher += intTostr(finalResult, 8).substring(leftSpacePadding);
                    } else {
                        textBinaryEncipher += intTostr(finalResult, 8);
                    }
                    //textEncipher += id == 1 && leftSpace != 0 ? intTostr(finalResult, 8).substring(leftSpace) : intTostr(finalResult, 8);
                }
                LeftInitialPBinary = LeftBlock;
            }
            endPos += 64;
            startPos += 64;
            System.out.println("CipherText : " + intTostr(finalResult, 8));
        }
        System.out.println("Final Cipher Text : " + textBinaryEncipher);

        //*********************************************************************************************
        //decryption
        plainStringText = Stringencipher;

        int length = Stringencipher.length();
        //number of extra bits
        int leftpaddingStringDe = length % 64;
        //number of chunk
        int loopNum;
        if (leftpaddingStringDe != 0) {
            loopNum = length / 64 + 1;
        } else {
            loopNum = length / 64;
        }
        //int loopNum = leftpadString != 0 ? length / 64 + 1 : length / 64;
        //position of starting and ending position
        int startPosDe = 0;
        int endPosDe = 64;

        for (int idNum = loopNum, wordCount = 1; idNum > 0; idNum--, wordCount++) {
            int leftpadDe = 64 - leftpaddingStringDe;
            //setting ending position
            if (length - startPosDe < 64) {
                endPosDe = length;
            }
            //endPosDe = length - startPosDe < 64 ? length : endPosDe;
            //chunk
            String cipherTextString = plainStringText.substring(startPosDe, endPosDe);

            //initial permuation using PI array
            String InitialPermutate = "";
            for (int i : pi) {
                InitialPermutate += cipherTextString.charAt(i - 1);
            }
            //System.out.println(InitialPermutate);

            //partitioning into two parts of 32 bits
            String LeftInitialPBin = InitialPermutate.substring(0, 32);
            String RightInitialPBin = InitialPermutate.substring(32, 64);

            int count = 1;
            String str;

            for (int pin = 15; pin >= 0; pin--) {
                //System.out.println("");
//                System.out.println("Iteration : " + count);
//                System.out.println("Key : " + k);
                str = Keys_in_Round[pin];
                //Left block becomes right block of previous round
                String LeftBlock = RightInitialPBin;
                //System.out.println("LEFT BLOCK  = " + LeftBlock);

                //Right block is previous left block XOR F(previous left block, round key)
                String expandBits = "";
                for (int i : E) {
                    expandBits += RightInitialPBin.charAt(i - 1);
                }

                //XOR 'expand' and the key since they are now both 48 bits long
                StringBuilder sbilder = new StringBuilder();
                for (int i = 0; i < str.length(); i++) {
                    sbilder.append((str.charAt(i) ^ expandBits.charAt(i)));
                }
                String resultXOR = sbilder.toString();

                String binaryTargetDe = new String("");
                for (int de : PI_2) {
                    binaryTargetDe += resultXOR.charAt(de - 1);
                }

                //Lastly,to get f we permutate the output of the S-box using table P 
                String functionDe = "";
                for (int de : P) {
                    functionDe += binaryTargetDe.charAt(de - 1);
                }

                //Finally, Previous Left block XOR function
                sbilder = new StringBuilder();
                for (int i = 0; i < LeftInitialPBin.length(); i++) {
                    sbilder.append((LeftInitialPBin.charAt(i) ^ functionDe.charAt(i)));
                }
                resultXOR = sbilder.toString();

                RightInitialPBin = resultXOR;
                //System.out.println("RIGHT BLOCK = " + RightInitialPBIn);
                resultXOR = "";

                count++;

                if (count > 16) {
                    //Reverse the order of the two blocks to form a 64-bit block
                    resultXOR = RightInitialPBin + LeftBlock;

                    //Final Permutation: The inverse of the initial permutation
                    String finalResult = "";
                    for (int x : PI_1) {
                        finalResult += resultXOR.charAt(x - 1);
                    }

                    textbinaryDecipher += finalResult;
                    if (idNum == 1 && leftSpacePadding != 0) {
                        Stringdecipher += intTostr(finalResult, 8).substring(leftSpacePadding);
                    } else {
                        Stringdecipher += intTostr(finalResult, 8);
                    }
                    //decipher += idNum == 1 && leftSpacePadding != 0 ? intTostr(finalResult, 8).substring(leftSpacePadding) : intTostr(finalResult, 8);
                    System.out.println("Decrypted = " + intTostr(finalResult, 8));
                }
                LeftInitialPBin = LeftBlock;
            }
            endPosDe += 64;
            startPosDe += 64;
        }
        if(Stringdecipher.length()==8 && Stringdecipher.contains("-"))
            Stringdecipher=Stringdecipher.replace("-", "");
        System.out.println("Decrypted Text : " + Stringdecipher);
    }

}
